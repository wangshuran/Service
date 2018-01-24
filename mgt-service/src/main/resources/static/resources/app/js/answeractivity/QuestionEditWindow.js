/**
 * 编辑窗口
 */
Ext.define('App.answeractivity.QuestionEditWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.aaquesitoneditwindow',
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
							name : 'aaId'
						}, {
							xtype : 'numberfield',
							name : 'number',
							anchor : '95%',
							fieldLabel : '题号',
							allowBlank : false
						} ]
					},
					{
						columnWidth : 1,
						layout : 'anchor',
						items : [ {
							xtype : 'textfield',
							name : 'question',
							anchor : '95%',
							fieldLabel : '题目',
							allowBlank : false
						} ]
					},
					{
						columnWidth : 1,
						layout : 'anchor',
						items : [ {
							xtype : 'textfield',
							name : 'answer',
							anchor : '95%',
							fieldLabel : '答案',
							allowBlank : false
						} ]
					}]
		} ];
		this.callParent(arguments);
	}
})