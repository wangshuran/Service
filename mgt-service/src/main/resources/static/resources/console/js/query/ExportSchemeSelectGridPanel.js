/**
 * 个人导出方案选择
 */

Ext.define('SystemConsole.query.ExportSchemeSelectGridPanel', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.exportschemeselectgridpanel',
	requires : [ 'Ext.ux.form.ItemSelector' ],
	query : null,
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
		
		var sm = Ext.create('Ext.selection.CheckboxModel',{
			mode:'SINGLE'
		});
		this.selModel = sm;
		this.columns = [{
			text : '方案描述',
			flex : 3,
			dataIndex : 'remark'
		}, {
			header : '操作',
			flex : 1,
			dataIndex : 'SP',
			renderer:function(v, c, r, row, col, s){
				return '<a href="javascript:void(0);" onclick="var grid =Ext.getCmp(\''+me.id+'\');grid.viewExportScheme('+row+');">查看详情</a>&nbsp;&nbsp;';
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
	viewExportScheme:function(row){
		var grid = this;
		var record = grid.getStore().getAt(row);
		var form = grid.initEditForm();
		form.loadRecord(record);
		var win = Ext.create("Ext.window.Window", {
			title : '查看方案',
			width : 700,
			height : 530,
			modal : true,
			layout : 'fit',
			items : [ form ],
			buttons : [{
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


