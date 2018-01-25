/**
 * 
 */

Ext.define('SystemConsole.role.RolePanel', {
	extend : 'Ext.Panel',
	alias : 'widget.rolepanel',
	layout : 'border',
	initComponent : function() {
		this.items = [ Ext.create('SystemConsole.role.RoleGridPanel', {
			region : 'center',
			border : false
		}), Ext.create('SystemConsole.role.RoleObjGridPanel', {
			region : 'east',
			width : 500,
			split : true,
			collapseMode : 'mini',
			collapsed : true,
			title : '角色对象列表',
			border : false
		}) ];

		this.callParent(arguments);
	}
});