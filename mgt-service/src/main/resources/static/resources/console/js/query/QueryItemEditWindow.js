/**
 * 查询结果编辑窗口
 */
Ext.define('SystemConsole.query.QueryItemEditWindow', {
	extend: 'Ext.window.Window',
	alias: 'widget.queryitemeditwindow',
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
					},{
						xtype:'hidden',
						name:'queryId'
					}, {
							xtype: 'textfield',
							name: 'field',
							anchor: '95%',
							fieldLabel: '字段名称',
							allowBlank: false
						}]
				},
				{
					columnWidth: .5,
					layout: 'anchor',
					items: [{
						xtype: 'textfield',
						name: 'label',
						anchor: '95%',
						fieldLabel: '显示标题'
					}]
				},
				{
					columnWidth: 1,
					layout: 'anchor',
					items: [{
						xtype: 'textarea',
						name: 'config',
						anchor: '97.5%',
						fieldLabel: '字段配置'
					}]
				}]
		}];
		this.callParent(arguments);
	}
})