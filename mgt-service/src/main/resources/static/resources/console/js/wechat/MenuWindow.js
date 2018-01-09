/**
 * 微信菜单编辑窗口
 */
Ext.define('SystemConsole.wechat.MenuWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.menuwindow',
	layout : 'border',
	width : 800,
	height : 600,
	border : false,
	frame : false,
	initComponent : function() {
		var me = this;
		this.items = [ Ext.create('SystemConsole.wechat.MenuTree', {
			title : '微信菜单',
			region : 'center',
			frame : true,
			border : false
		}), Ext.create('Ext.panel.Panel', {
			title : '编辑区域',
			region : 'east',
			width : 500,
			split : true,
			collapsible : true,
			frame : true,
			border : false,
			layout:'fit',
			items:[Ext.create('SystemConsole.wechat.MenuForm',{})],
			buttons:[{
				text:'保存',
				handler:function(){
					
				}
			},{
				text:'取消',
				handler:function(){
					
				}
			}],
			buttonAlign:'center'
		}) ];
		this.callParent(arguments);
	}
})