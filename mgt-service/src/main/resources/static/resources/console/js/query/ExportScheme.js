/**
 * 导出设置管理窗口
 */
Ext.define('SystemConsole.query.ExportScheme', {
	extend : 'Ext.window.Window',
	alias : 'widget.exportscheme',
	layout : 'fit',
	query : null,
	initComponent : function() {
		var me = this;
		this.items = [ Ext.create('SystemConsole.query.ExportSchemeGridPanel',
				{
					query : me.query
				}) ];
		this.callParent(arguments);
	}
})