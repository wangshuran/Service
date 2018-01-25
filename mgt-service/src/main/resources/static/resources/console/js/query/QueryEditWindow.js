/**
 * 查询编辑窗口
 */
Ext.define('SystemConsole.query.QueryEditWindow', {
	extend: 'Ext.window.Window',
	alias: 'widget.queryeditwindow',
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
							name: 'queryKey',
							anchor: '95%',
							fieldLabel: '查询标识',
							allowBlank: false,
							listeners: {
								blur: me.checkQueryKey
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
						fieldLabel: '查询标题'
					}]
				},
				{
					columnWidth: 1,
					layout: 'anchor',
					items: [{
						xtype: 'textarea',
						name: 'remark',
						anchor: '97.5%',
						fieldLabel: '查询描述'
					}]
				}]
		}];
		this.callParent(arguments);
	},
	checkQueryKey: function (thiz, eopts) {
		Ext.Ajax.request({
			url: context + '/query/checkKey',
			method: 'POST',
			params: {
				key: thiz.getValue()
			},
			success: function (response, options) {
				var o = Ext.JSON.decode(response.responseText);
				if (o.success) {
					if (o.isExits) {
						Ext.Msg.alert('提示', '查询标识已经存在！');
						thiz.setValue('');
					}
				}
			}
		});
	}
})