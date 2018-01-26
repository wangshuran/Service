Ext.define('App.answeractivity.AnswerActivityEditWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.answeractivityeditwindow',
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
							xtype : 'hidden',
							name : 'id'
						},{
							xtype : 'hidden',
							name : 'stage'
						}, {
							xtype : 'textfield',
							name : 'title',
							anchor : '95%',
							fieldLabel : '活动主题'
						} ]
					},
					{
						columnWidth : 1,
						layout : 'anchor',
						items : [Ext.create('Ext.ux.DateTimeField',{
							name:'startTime_show',
			                fieldLabel:'开始时间',  
			                anchor : '95%',
			                format:'Y-m-d H:i:s'  
			            })]
					},
					{
						columnWidth : 1,
						layout : 'anchor',
						items : [ {
							xtype : 'numberfield',
							name : 'reward',
							fieldLabel : '活动奖励',
							anchor : '95%'
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
									wordBook : 'bannerStatus'
								}) ]
					} ]
		} ];
		this.callParent(arguments);
	}
})