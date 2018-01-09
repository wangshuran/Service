/**
 * 用户管理
 */

Ext.define('SystemConsole.user.UserPanel', {
	extend : 'Ext.Panel',
	alias : 'widget.userpanel',
	layout : 'border',
	initComponent : function() {
		this.items = [ {
			region : 'north',
			height : 100,
			border : false,
			frame : false,
			split : true,
			collapseMode : 'mini',
			collapsible : true,
			header:false,
			layout : {
				type : 'vbox',
				align : 'center'
			},
			items : [ Ext.create('SystemConsole.user.UserQueryPanel', {
				region : 'north',
				width : 800,
				style : 'padding-top:10px;',
				columnCount : 3
			}) ]

		}, Ext.create('SystemConsole.user.UserGridPanel', {
			region : 'center',
			border : false
		}) ];

		this.callParent(arguments);
	}
});