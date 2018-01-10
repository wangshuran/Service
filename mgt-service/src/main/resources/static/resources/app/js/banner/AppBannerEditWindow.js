/**
 * app banner 编辑窗口
 */
Ext.define('App.banner.AppBannerEditWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.appbannereditwindow',
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
									wordBook : 'lang'
								}) ]
					},
					{
						columnWidth : 1,
						layout : 'anchor',
						items : [ {
							xtype : 'hidden',
							name : 'id'
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
							name : 'subTitle',
							anchor : '95%',
							fieldLabel : '子标题'
						} ]
					},
					{
						columnWidth : .83,
						layout : 'anchor',
						items : [ {
							xtype : 'textfield',
							readOnly : true,
							name : 'imgUrl',
							fieldLabel : '图片链接',
							anchor : '99.8%',
							buttonText : '选择'
						} ]
					},
					{
						columnWidth : .17,
						layout : 'anchor',
						items : [ {
							xtype : 'button',
							text : '查看',
							style : 'margin-left:2px;',
							handler : function(btn) {
								var form = btn.up('form').getForm();
								var url = form.findField('imgUrl').getValue();
								showAppBannerImg(url);
							}
						} ]
					},
					{
						columnWidth : 1,
						layout : 'anchor',
						items : [ {
							xtype : 'filefield',
							name : 'imgFile',
							fieldLabel : '图片',
							anchor : '95%',
							buttonText : '选择'
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
									wordBook : 'bannerStatus'
								}) ]
					} ]
		} ];
		this.callParent(arguments);
	}
})