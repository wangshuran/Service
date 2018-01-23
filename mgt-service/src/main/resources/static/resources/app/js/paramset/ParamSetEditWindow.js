Ext.define('App.paramset.ParamSetEditWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.paramseteditwindow',
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
						columnWidth : 1,
						layout : 'anchor',
						items : [ {
							xtype : 'textfield',
							name : 'paramKey',
							anchor : '95%',
							fieldLabel : '参数标识'
						} ]
					},
					{
						columnWidth : 1,
						layout : 'anchor',
						items : [ {
							xtype : 'textfield',
							name : 'paramValue',
							anchor : '95%',
							fieldLabel : '参数值'
						} ]
					},
					{
						columnWidth : 1,
						layout : 'anchor',
						items : [ {
							xtype : 'textarea',
							name : 'remark',
							anchor : '95%',
							fieldLabel : '参数说明'
						} ]
					}]
		} ];
		this.callParent(arguments);
	}
})