/**
 * 查询配置管理
 */

Ext.define('SystemConsole.query.QueryPanel', {
	extend : 'Ext.Panel',
	alias : 'widget.querypanel',
	layout : 'border',
	initComponent : function() {
		this.items = [ {
			region : 'north',
			height : 60,
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
			items : [ Ext.create('SystemConsole.query.QueryQueryPanel', {
				region : 'north',
				width : 800,
				style : 'padding-top:10px;',
				columnCount : 3
			}) ]

		}, Ext.create('SystemConsole.query.QueryGridPanel', {
			region : 'center',
			border : false
		}) ];

		this.callParent(arguments);
	}
});