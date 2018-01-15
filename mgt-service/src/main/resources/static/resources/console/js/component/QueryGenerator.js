/**
 * 查询生成工具
 */

Ext.define('SystemConsole.component.QueryGenerator', {
	queryKey : null,
	query : null,
	constructor : function(_queryKey) {
		var me = this;
		this.queryKey = _queryKey;
		var result = Ext.decode(sendRequest(context
				+ '/query/find/key?queryKey=' + this.queryKey));
		if (result.success) {
			this.query = result.query;
			if (this.query.config.loadJS) {
				loadScriptFile(context + this.query.config.loadJS);
			}
		}
	},
	/**
	 * 创建查询主面板
	 */
	createMainPanel : function(options) {
		var me = this;
		var mainPanel;
		var items = [];

		if ("border" == me.query.config.layout) {
			if (me.query.queryItems != null && me.query.queryItems.length > 0) {
				items.push({
					region : 'north',
					height : me.query.config.queryHeight * 1,
//					border : false,
					frame : false,
					split : true,
					collapseMode : 'mini',
					collapsible : true,
					header : false,
					layout : {
						type : 'vbox',
						align : 'center'
					},
					items : [ me.createQueryPanel({}) ]

				});
			}
			items.push(me.createGridPanel({
				region : 'center'
			}));
		} else {
			items.push(me.createGridPanel({}));
		}

		mainPanel = Ext.create('Ext.Panel', {
			id : options.id,
			layout : me.query.config.layout ? me.query.config.layout : 'fit',
			items : items
		});

		Ext.apply(mainPanel, options);

		return mainPanel;
	},
	/**
	 * 创建查询面板
	 */
	createQueryPanel : function(options) {
		var me = this;
		var formPanel = me.createQueryFormPanel(options);
		formPanel.add({
			columnWidth : 1 / me.query.config.queryColumns,
			border : false,
			frame : false,
			layout : {
				type : 'hbox',
				pack : 'end'
			},
			defaults : {
				margin : '4px 0px 0px 10px',
				width : 80
			},
			items : [ {
				xtype : 'button',
				text : '查询',
				handler : function(btn) {
					me.doQuery(btn, me);
				}
			}, {
				xtype : 'button',
				text : '重置',
				handler : function(btn) {
					me.doReset(btn, me);
				}
			} ]
		});
		return formPanel;
	},
	/**
	 * 创建查询窗口
	 */
	createQueryWindow : function(options) {
		var me = this;
		var formPanel = me.createQueryFormPanel(options);
		var queryWin = Ext.create('Ext.window.Window', {
			id : me.query.queryKey + '_query_window',
			title : '数据查询',
			modal : true,
			layout : 'fit',
			width : me.query.config.queryWidth * 1,
			height : me.query.config.queryHeight * 1,
			items : [ formPanel ],
			buttons : [ {
				text : '查询',
				handler : function(btn) {
					me.doQuery(btn, me);
				}
			}, {
				text : '重置',
				handler : function(btn) {
					me.doReset(btn, me);
				}
			}, {
				text : '关闭',
				handler : function() {
					queryWin.hide();
				}
			} ]
		});
		return queryWin;
	},
	/**
	 * 查询按钮点击
	 */
	doQuery : function(btn, mainPanel) {
		var form = Ext.getCmp(mainPanel.query.queryKey + '_query_form');
		var grid = Ext.getCmp(mainPanel.query.queryKey + '_grid');
		Ext.apply(grid.store.proxy.extraParams, form.getForm().getValues());
		grid.store.loadPage(1);
		var win = form.up('window');
		if (win) {
			win.hide();
		}
	},
	/**
	 * 重置按钮点击
	 */
	doReset : function(btn, mainPanel) {
		var form = Ext.getCmp(mainPanel.query.queryKey + '_query_form');
		form.getForm().reset();
	},
	/**
	 * 创建查询表单面板
	 */
	createQueryFormPanel : function(options) {
		var me = this;
		var queryPanel;
		var items = [];

		if (me.query.queryItems != null && me.query.queryItems.length > 0) {
			Ext.each(me.query.queryItems, function(item) {
				var config = Ext.decode(item.config ? item.config : "{}");
				if(config.wordbook){
					items.push({
						columnWidth : 1 / me.query.config.queryColumns,
						layout : 'form',
						border : false,
						frame : false,
						items : [Ext.create(
								'SystemConsole.component.WordBookComboBox',
								{
									fieldLabel : item.label,
									labelAlign:'right',
									name : item.field,
									wordBook : config.wordbook,
									labelWidth : 70
								})]
					});
				}else{
					items.push({
						columnWidth : 1 / me.query.config.queryColumns,
						layout : 'form',
						border : false,
						frame : false,
						items : [ {
							xtype : 'textfield',
							name : item.field,
							fieldLabel : item.label,
							labelAlign : 'right',
							labelWidth : 70
						} ]
					});
				}
			});
		}

		queryPanel = Ext.create('Ext.form.Panel', {
			id : me.query.queryKey + '_query_form',
			layout : 'column',
			width : me.query.config.queryWidth * 1,
			style : 'padding-top:10px;',
			border:false,
			items : items
		});

		return queryPanel;
	},
	/**
	 * 创建表格面板
	 */
	createGridPanel : function(options) {
		var me = this;
		var columns = [];
		var fields = [];
		
		columns.push({
			xtype : 'rownumberer',
			text : '序号',
			width : 50,
			align : 'center'
		});
		

		if (me.query.resultItems != null && me.query.resultItems.length > 0) {
			Ext.each(me.query.resultItems, function(item) {
				fields.push({
					name : item.field
				});

				var column = {
					text : item.label,
					dataIndex : item.field
				};
				var config = Ext.decode(item.config ? item.config : "{}");
				if (config)
					Ext.apply(column, config);
				columns.push(column);
			});
		}

		Ext.define('model', {
			extend : 'Ext.data.Model',
			fields : fields
		});
		
		var extraParams={queryKey : me.query.queryKey};
		
		if(options.extraParams){
			Ext.apply(extraParams, options.extraParams);
		}
		
		var dataUrl = context + '/query/data';
		if(me.query.config.fetchDataUrl){
			dataUrl = context + me.query.config.fetchDataUrl;
		}

		var store = Ext.create('Ext.data.Store', {
			autoDestroy : true,
			model : 'model',
			autoLoad : true,
			pageSize : me.query.config.pageSize,
			proxy : {
				type : 'ajax',
				actionMethods : 'POST',
				batchActions : false,
				extraParams : extraParams,
				api : {
					read : dataUrl
				},
				reader : {
					type : 'json',
					successProperty : 'success',
					root : 'resultList',
					totalProperty : 'totalCount'
				},
				writer : {
					type : 'json',
					writeAllFields : true
				},
				listeners : {
					exception : function(proxy, response, operation) {
						var json = Ext.decode(response.responseText);
						Ext.MessageBox.show({
							title : 'REMOTE EXCEPTION',
							msg : json.message,
							icon : Ext.MessageBox.ERROR,
							buttons : Ext.Msg.OK
						});
					}
				}
			}
		});

		var gridConfig = {
			id : me.query.queryKey + '_grid',
			columns : columns,
			store : store,
			viewConfig:{  
                enableTextSelection:true  
            }
		};
		
		if(me.query.config.isCheckBox=='Y'){
			var sm = Ext.create('Ext.selection.CheckboxModel',{});
			gridConfig.selModel = sm;
		}

		// 操作按钮
		var tbar = [];
		if (me.query.config.layout != 'border' && me.query.queryItems != null && me.query.queryItems.length > 0) {
			tbar.push({
				text : '数据查询',
				iconCls : 'search',
				handler : function() {
					var queryWin = Ext.getCmp(me.query.queryKey
							+ '_query_window');
					if (!queryWin)
						queryWin = me.createQueryWindow();
					queryWin.show();
				}
			});
		}
		// 是否导出Excel
		if (me.query.config.isExportExcel == 'Y') {
			if (tbar.length > 0) {
				tbar.push('-');
			}
			tbar.push({
				text : '导出设置',
				iconCls : 'layout-edit',
				handler : function() {
					Ext.create('SystemConsole.query.ExportScheme', {
						query : me.query,
						title:'导出方案',
						width:700,
						height:400,
						modal:true
					}).show();
				}
			}, '-', {
				text : '导出Excel',
				iconCls : 'excel',
				handler : function(btn) {
					me.doDownloadExcel(btn, me);
				}
			});

		}

		if (canExecuteFunction(me.query.queryKey + '_initTbar')) {
			try {
				eval(me.query.queryKey + '_initTbar(tbar,me.query);');
			} catch (error) {
				alert(error.message);
			}
		}

		if (tbar.length > 0) {
			gridConfig.tbar = tbar;
		}
		// 是否分页
		if (me.query.config.isPage == 'Y') {
			var bbar = Ext.create('Ext.PagingToolbar', {
				store : store,
				displayInfo : true,
				displayMsg : '当前显示  第{0} - {1} of {2}',
				emptyMsg : "没有数据"
			});
			gridConfig.bbar = bbar;
		}
		
		var gridPanel = Ext.create('Ext.grid.Panel', gridConfig);
		
		if (canExecuteFunction(me.query.queryKey + '_initContextMenu')){
			gridPanel.on('itemcontextmenu',function(view, record, item, index, e){
				try {
					eval(me.query.queryKey + '_initContextMenu(view, record, item, index, e);');
				} catch (error) {
					alert(error.message);
				}
			});
		}

		Ext.apply(gridPanel, options);

		return gridPanel;
	},
	doDownloadExcel : function(btn, mainPanel) {
		var me = this;
		var queryForm = Ext.getCmp(mainPanel.query.queryKey + '_query_form');
		var url = context + '/query/download/excel?queryId='
				+ mainPanel.query.id;

		if (queryForm) {
			url = url + '&' + queryForm.getValues(true);
		}
		
		//判断是否需要选择导出方案
		var exitScheme = sendRequest(context+'/query/exportscheme/exits?queryId='+mainPanel.query.id);
		if(exitScheme=='true'){
			var grid = Ext.create('SystemConsole.query.ExportSchemeSelectGridPanel',
					{
						query : mainPanel.query
					});
			var win = Ext.create('Ext.window.Window',{
				title:'选择导出方案',
				width:700,
				height:400,
				modal:true,
				items:[grid],
				layout:'fit',
				buttons:[{
					text:'选择导出',
					handler:function(){
						var selection = grid.getSelectionModel();
						if(!selection.hasSelection()){
							Ext.MessageBox.alert('提示','请先选择要使用的导出方案');
							return;
						}
						
						var selectRecords = selection.getSelection();
						url = url + '&schemeId=' + selectRecords[0].data.id;
						me.downloadUrl(url);
						win.close();
						win=null;
					}
				},{
					text:'默认导出',
					handler:function(){
						me.downloadUrl(url);
						win.close();
						win=null;
					}
				},{
					text:'取消',
					handler:function(){
						win.close();
						win=null;
					}
				}]
			});
			win.show();
		}else{
			me.downloadUrl(url);
		}
	},
	downloadUrl:function(url){
		var tempForm = document.createElement("form");
		tempForm.id = "downloadExcelForm";
		tempForm.method = "post";
		tempForm.action = url;
		tempForm.target = '_self';

		document.body.appendChild(tempForm);
		tempForm.submit();
		document.body.removeChild(tempForm);
	}
});