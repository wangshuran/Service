/**
 * fund plan 编辑窗口
 */
Ext.define('App.fundplan.FundPlanEditWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.fundplaneditwindow',
	layout : 'fit',
	initComponent : function() {
		var me = this;
		this.items = [ {
			xtype : 'form',
			defaults : {
				frame : false,
				border : false
			},
			layout : 'column',
			bodyStyle : 'padding:10px',
			items : [
				{
					columnWidth : .845,
					layout : 'anchor',
					items : [ {
						xtype : 'textfield',
						readOnly : true,
						name : 'iconUrl',
						fieldLabel : '图标链接',
						anchor : '99.8%',
						buttonText : '选择'
					} ]
				},
				{
					columnWidth : .155,
					layout : 'anchor',
					items : [ {
						xtype : 'button',
						text : '查看',
						style : 'margin-left:2px;',
						handler : function(btn) {
							var form = btn.up('form').getForm();
							var url = form.findField('iconUrl').getValue();
							showAppBannerImg(url);
						}
					} ]
				},{
					columnWidth : 1,
					layout : 'anchor',
					items : [ {
						xtype : 'filefield',
						name : 'iconFile',
						fieldLabel : '图标',
						anchor : '95%',
						buttonText : '选择'
					} ]
				},
					{
						columnWidth : 1,
						layout : 'anchor',
						items : [ {
							xtype : 'hidden',
							name : 'id'
						}, {
							xtype : 'numberfield',
							name : 'annualRate',
							anchor : '95%',
							fieldLabel : '收益率'
						} ]
					},
					{
						columnWidth : 1,
						layout : 'anchor',
						items : [ {
							xtype : 'fieldcontainer',
							anchor : '95%',
							fieldLabel : '期限',
							layout: 'hbox',
							items:[{
								xtype:'numberfield',
								name:'term',
								flex:1
							},Ext.create(
									'SystemConsole.component.WordBookComboBox', {
										name : 'status',
										wordBook : 'dateUnit',
										flex:1
									})]
						} ]
					},
					{
						columnWidth : 1,
						layout : 'anchor',
						items : [ {
							xtype : 'numberfield',
							name : 'weight',
							anchor : '95%',
							fieldLabel : '排序权重',
							allowBlank : false
						} ]
					},
					{
						columnWidth : 1,
						layout : 'anchor',
						items : [ Ext.create(
								'SystemConsole.component.WordBookComboBox', {
									fieldLabel : '状态',
									name : 'status',
									anchor : '95%',
									wordBook : 'recordStatus'
								}) ]
					} ]
		} ];
		this.callParent(arguments);
	},
	createLangGrid:function(){
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
				columns : columns,
				store : store,
				viewConfig:{  
	                enableTextSelection:true  
	            }
			};
	}
})