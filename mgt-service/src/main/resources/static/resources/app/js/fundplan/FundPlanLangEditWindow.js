/**
 * fund plan 编辑窗口
 */
Ext.define('App.fundplan.FundPlanLangEditWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.fundplanlangeditwindow',
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
								fieldLabel : '选择语言',
								name : 'lang',
								anchor : '95%',
								editable:false,
								wordBook : 'lang'
							}) ]
				},{
						columnWidth : 1,
						layout : 'anchor',
						items : [ {
							xtype : 'hidden',
							name : 'id'
						},{
							xtype : 'hidden',
							name : 'fundPlanId'
						}, {
							xtype : 'textfield',
							name : 'title',
							anchor : '95%',
							fieldLabel : '标题'
						} ]
					},
					{
						columnWidth : 1,
						layout : 'anchor',
						items : [ {
							xtype : 'textfield',
							name : 'remark',
							anchor : '95%',
							fieldLabel : '描述'
						} ]
					}]
		} ];
		this.callParent(arguments);
	}
})