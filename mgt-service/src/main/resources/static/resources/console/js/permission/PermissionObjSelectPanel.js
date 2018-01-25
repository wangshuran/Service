Ext.define('SystemConsole.permission.PermissionObjSelectPanel', {
	extend : 'Ext.Panel',
	alias : 'widget.permissionobjselectpanel',
	initComponent : function() {
		this.layout = {
			type : 'accordion',
			titleCollapse : true,
			animate : true,
			activeOnTop : false
		};
		this.items = [ Ext.create('SystemConsole.permission.OrgSelectPanel', {
			title : '组织机构选择'
		}), Ext.create('SystemConsole.permission.UserSelectPanel', {
			title : '用户选择'
		}), Ext.create('SystemConsole.permission.RoleSelectPanel', {
			title : '角色选择'
		}) ];
		this.callParent(arguments);
	}
})