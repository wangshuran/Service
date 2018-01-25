/**
 * 角色编辑窗口
 */
Ext.define('SystemConsole.role.RoleEditWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.roleeditwindow',
	layout : 'fit',
	initComponent : function() {
		this.items = [ {
			xtype : 'form',
			border : false,
			frame : false,
			defaults : {
				xtype : 'textfield',
				anchor : '95%'
			},
			bodyStyle : 'padding:10px 0px 0px 10px',
			items : [ {
				xtype : 'hidden',
				name : 'roleId'
			},{
				name : 'roleKey',
				fieldLabel : '角色标识'
			}, {
				name : 'roleName',
				xtype:'textarea',
				fieldLabel : '角色描述'
			} ]
		} ];
		this.callParent(arguments);
	}
})