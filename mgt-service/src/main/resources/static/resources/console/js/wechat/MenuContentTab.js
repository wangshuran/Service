/**
 * 发送消息TAB内容
 */
Ext.define('SystemConsole.wechat.MenuContentTab', {
	extend : 'Ext.tab.Panel',
	alias : 'widget.wechat_menu_content_tab',
	defaults : {
		autoScroll : true,
		autoHeight : true
	},
	initComponent : function() {
		var me = this;
		this.items = [ {
			title : '图文消息'
		}, {
			title : '图片'
		}, {
			title : '语音'
		}, {
			title : '视频'
		} ];
		this.callParent(arguments);
	}
})