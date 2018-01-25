/**
 * 用户编辑窗口
 */
Ext.define('SystemConsole.user.ChangePwdWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.changepwdwindow',
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
				name : 'oldPassword',
				fieldLabel : '原密码',
				inputType : 'password',
				allowBlank:false
			}, {
				name : 'newPassword',
				itemId : 'newPassword',
				fieldLabel : '新密码',
				inputType : 'password',
				allowBlank:false
			}, {
				name : 'loginPasswordConfirm',
				fieldLabel : '新密码确认',
				inputType : 'password',
				vtype : 'password',
				initialPassField : 'newPassword',
				allowBlank:false
			} ]
		} ];
		this.callParent(arguments);
	}
})