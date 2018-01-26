/**
 * 编辑窗口
 */
Ext.define('App.answeractivity.OptionEditWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.aaoptioneditwindow',
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
			items : [{
						columnWidth : 1,
						layout : 'anchor',
						items : [ {
							xtype : 'hidden',
							name : 'id'
						},{
							xtype : 'hidden',
							name : 'questionId'
						}, {
							xtype : 'textfield',
							name : 'optionCode',
							anchor : '100%',
							fieldLabel : '选项号',
							allowBlank : false
						} ]
					},
					{
						columnWidth : 1,
						layout : 'anchor',
						items : [ {
							xtype : 'textarea',
							name : 'optionContent',
							anchor : '100%',
							fieldLabel : '选项',
							allowBlank : false
						} ]
					},
					{
						columnWidth : 1,
						layout : 'anchor',
						items : [ {
							xtype : 'textarea',
							name : 'explainContent',
							anchor : '100%',
							fieldLabel : '解答',
							allowBlank : false
						} ]
					},
					{
						columnWidth : 1,
						layout : 'anchor',
						items : [ {
							xtype : 'numberfield',
							name : 'explainTime',
							anchor : '100%',
							fieldLabel : '解答时间',
							allowBlank : false
						} ]
					}]
		} ];
		this.callParent(arguments);
	}
})