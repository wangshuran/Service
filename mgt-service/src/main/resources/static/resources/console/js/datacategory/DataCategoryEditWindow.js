Ext.define('SystemConsole.datacategory.DataCategoryEditWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.datacategoryeditview',
	layout : 'fit',
	initComponent : function() {
		this.items = [ {
			xtype : 'form',
			border : false,
			frame : false,
			defaults : {
				xtype : 'textfield',
				anchor : '95%'
			},
			bodyStyle : 'padding:10px 0px 0px 10px',
			items : [ {
				xtype : 'hidden',
				name : 'dcId'
			}, {
				xtype : 'hidden',
				name : 'dcParent'
			}, {
				name : 'dcParentName',
				fieldLabel : '父分类名称',
				readOnly : true
			}, {
				name : 'flag',
				fieldLabel : '分类标识'
			}, {
				name : 'remark',
				fieldLabel : '分类描述'
			}]
		} ];
		this.callParent(arguments);
	}
})