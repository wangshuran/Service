/**
 * 审核窗口
 */
Ext.define('App.transfer.AuditWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.auditwindow',
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
						items : [ Ext.create(
								'SystemConsole.component.WordBookComboBox', {
									fieldLabel : '选择结果',
									name : 'status',
									anchor : '95%',
									wordBook : 'withdrawStatus'
								}) ]
					},
					{
						columnWidth : 1,
						layout : 'anchor',
						items : [{
							xtype : 'textfield',
							name : 'txId',
							anchor : '95%',
							fieldLabel : '交易流水'
						} ]
					},
					{
						columnWidth : 1,
						layout : 'anchor',
						items : [ {
							xtype : 'textarea',
							name : 'remark',
							anchor : '95%',
							fieldLabel : '备注'
						} ]
					}]
		} ];
		this.callParent(arguments);
	}
})