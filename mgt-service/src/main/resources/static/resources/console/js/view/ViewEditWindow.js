/**
 * 视图编辑窗口
 */
Ext.define('SystemConsole.view.ViewEditWindow', {
	extend: 'Ext.window.Window',
	alias: 'widget.vieweditwindow',
	layout: 'fit',
	initComponent: function () {
		var me = this;
		this.items = [{
			xtype: 'form',
			defaults: {

				frame: false,
				border: false
			},
			layout: 'column',
			bodyStyle: 'padding:10px',
			items: [
				{
					columnWidth: .5,
					layout: 'anchor',
					items: [{
						xtype: 'hidden',
						name: 'id'
					}, {
							xtype: 'textfield',
							name: 'viewKey',
							anchor: '95%',
							fieldLabel: '视图标识',
							allowBlank: false,
							listeners: {
								blur: me.checkViewKey
							}
						}]
				},
				{
					columnWidth: .5,
					layout: 'anchor',
					items: [{
						xtype: 'textfield',
						name: 'title',
						anchor: '95%',
						fieldLabel: '视图标题'
					}]
				},
				{
					columnWidth: 1,
					layout: 'anchor',
					items: [{
						xtype: 'textarea',
						name: 'remark',
						anchor: '97.5%',
						fieldLabel: '视图描述'
					}]
				}]
		}];
		this.callParent(arguments);
	},
	checkViewKey: function (thiz, eopts) {
		Ext.Ajax.request({
			url: context + '/view/checkKey',
			method: 'POST',
			params: {
				viewKey: thiz.getValue()
			},
			success: function (response, options) {
				var o = Ext.JSON.decode(response.responseText);
				if (o.success) {
					if (o.isExits) {
						Ext.Msg.alert('提示', '视图标识已经存在！');
						thiz.setValue('');
					}
				}
			}
		});
	}
})