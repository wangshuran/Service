/**
 * 个人导出设置方案
 */

Ext.define('SystemConsole.query.ExportSchemeGridPanel', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.exportschemegridpanel',
	requires : [ 'Ext.ux.form.ItemSelector' ],
	query : null,
	border:false,
	initComponent : function() {
		var me = this;
		this.store = Ext.create('SystemConsole.query.ExportSchemeGridStore', {
			pageSize : 50
		});
		
		this.store.on('beforeload', function (thiz, options) {

			Ext.apply(me.store.proxy.extraParams, {
				queryId: me.query.id
			});

		});
		
		this.store.load();
		this.columns = [ {
			text : '方案描述',
			flex : 3,
			dataIndex : 'remark'
		}, {
			header : '操作',
			flex : 1,
			dataIndex : 'SP',
			renderer:function(v, c, r, row, col, s){
				return '<a href="javascript:void(0);" onclick="var grid =Ext.getCmp(\''+me.id+'\');grid.editExportScheme('+row+');">编辑</a>&nbsp;&nbsp;'+
				       '<a href="javascript:void(0);" onclick="var grid =Ext.getCmp(\''+me.id+'\');grid.delExportScheme('+row+');">删除</a>';
			}
		} ];
		this.tbar = [ {
			text : '增加方案',
			iconCls : 'add',
			handler : function(btn) {
				me.showAddExportSchemeWin(btn, me.query)
			}
		} ];
		this.bbar = Ext.create('Ext.PagingToolbar', {
			store : me.store,
			displayInfo : true,
			displayMsg : '当前显示  第{0} - {1} of {2}',
			emptyMsg : "没有数据"
		});
		this.callParent(arguments);
	},
	editExportScheme:function(row){
		var grid = this;
		var record = grid.getStore().getAt(row);
		var form = grid.initEditForm();
		form.loadRecord(record);
		
		var win = Ext.create("Ext.window.Window", {
			title : '更新方案',
			width : 700,
			height : 530,
			modal : true,
			layout : 'fit',
			items : [ form ],
			buttons : [ {
				text : '确定',
				handler : function() {
					form.getForm().submit({
					    clientValidation: true,
					    method:'post',
					    url: context+'/query/exportscheme/save',
					    success: function(form, action) {
					       if(action.result.success){
					    	   Ext.MessageBox.alert('提示','更新方案成功');
					    	   grid.getStore().load();
					    	   win.close();
					    	   win=null;
					       }else{
					    	   Ext.Msg.alert('提示', '更新方案失败：'+action.result.message); 
					       }
					    },
					    failure: function(form, action) {
					        switch (action.failureType) {
					            case Ext.form.action.Action.CLIENT_INVALID:
					                Ext.Msg.alert('Failure', 'Form fields may not be submitted with invalid values');
					                break;
					            case Ext.form.action.Action.CONNECT_FAILURE:
					                Ext.Msg.alert('Failure', '服务连接失败');
					                break;
					            case Ext.form.action.Action.SERVER_INVALID:
					               Ext.Msg.alert('Failure', action.result.message);
					       }
					    }});
				}
			}, {
				text : '关闭',
				handler : function() {
					win.close();
					win = null;
				}
			} ]
		});

		win.show();
	},
	delExportScheme:function(row){
		var grid=this;
		var record = grid.getStore().getAt(row);
		Ext.Ajax.request({
		    url: context+'/query/exportscheme/remove',
		    method:'POST',
		    params: {
		        id: record.data.id
		    },
		    success: function(response){
		        var text = response.responseText;
		        var json = Ext.JSON.decode(text);
		        if(json.success){
		        	Ext.MessageBox.alert('提示','方案删除成功');
		        	grid.getStore().load();
		        }else{
		        	Ext.MessageBox.alert('提示','方案删除失败:'+json.msg);
		        }
		    }
		});
	},
	showAddExportSchemeWin : function(btn, query) {
		var me = this;
		var form = me.initEditForm();
		var win = Ext.create("Ext.window.Window", {
			title : '增加方案',
			width : 700,
			height : 530,
			modal : true,
			layout : 'fit',
			items : [ form ],
			buttons : [ {
				text : '确定',
				handler : function() {
					form.getForm().submit({
					    clientValidation: true,
					    method:'post',
					    url: context+'/query/exportscheme/save',
					    success: function(form, action) {
					       if(action.result.success){
					    	   Ext.MessageBox.alert('提示','增加方案成功');
					    	   me.getStore().load();
					    	   win.close();
					    	   win=null;
					       }else{
					    	   Ext.Msg.alert('提示', '增加方案失败：'+action.result.message); 
					       }
					    },
					    failure: function(form, action) {
					        switch (action.failureType) {
					            case Ext.form.action.Action.CLIENT_INVALID:
					                Ext.Msg.alert('Failure', 'Form fields may not be submitted with invalid values');
					                break;
					            case Ext.form.action.Action.CONNECT_FAILURE:
					                Ext.Msg.alert('Failure', '服务连接失败');
					                break;
					            case Ext.form.action.Action.SERVER_INVALID:
					               Ext.Msg.alert('Failure', action.result.message);
					       }
					    }});
				}
			}, {
				text : '关闭',
				handler : function() {
					win.close();
					win = null;
				}
			} ]
		});

		win.show();

	},
	initEditForm : function() {
		var me = this;

		var ds = Ext.create('Ext.data.JsonStore', {
			data : me.query.resultItems,
			autoLoad : true,
			fields : [ 'id','field', 'label', 'weight' ],
			sortInfo : {
				field : 'weight',
				direction : 'ASC'
			}
		});

		var form = Ext.widget('form', {
			bodyPadding : 10,
			border:false,
			items : [ {
				xtype : 'hidden',
				name : 'id'
			},{
				xtype : 'hidden',
				name : 'userId'
			},{
				xtype : 'hidden',
				name : 'queryId',
				value:me.query.id
			},{
				xtype : 'textfield',
				name : 'remark',
				anchor : '100%',
				fieldLabel : '方案描述',
				allowBlank:false
			}, {
				xtype : 'itemselector',
				name : 'fields',
				anchor : '100%',
				height : 400,
				fieldLabel : '导出字段',
				imagePath : '../ux/images/',
				store : ds,
				displayField : 'label',
				valueField : 'id',
				msgTarget : 'side'
			} ]
		});

		return form;
	}
});


