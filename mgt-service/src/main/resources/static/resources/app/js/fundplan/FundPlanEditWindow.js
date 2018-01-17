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
					columnWidth : .868,
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
					columnWidth : .132,
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
										name : 'termUnit',
										editable:false,
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
									editable:false,
									wordBook : 'recordStatus'
								}) ]
					},
					{
						columnWidth : 1,
						layout : 'anchor',
						items : [ Ext.create(
								'App.fundplan.FundPlanLangGrid', {
									title : '多语言',
									anchor : '95%',
									height:200
								}) ]
					}]
		} ];
		this.callParent(arguments);
	}
})