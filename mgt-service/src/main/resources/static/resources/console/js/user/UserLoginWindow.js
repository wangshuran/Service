/**
 * 用户登录窗口
 */
Ext.define('SystemConsole.user.UserLoginWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.userloginwindow',
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
			items : [{
				name:'userName',
				fieldLabel:'用户名'
			},{
				name:'password',
				inputType : 'password',
				fieldLabel:'密码'
			}]
		} ];
		this.callParent(arguments);
	}
})