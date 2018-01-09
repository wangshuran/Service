/**
 * 视图列表
 */

Ext.define('SystemConsole.view.ViewPanel', {
	extend : 'Ext.Panel',
	alias : 'widget.viewpanel',
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
			items : [ Ext.create('SystemConsole.view.ViewQueryPanel', {
				region : 'north',
				width : 800,
				style : 'padding-top:10px;',
				columnCount : 3
			}) ]

		}, Ext.create('SystemConsole.view.ViewGridPanel', {
			region : 'center',
			border : false
		}) ];

		this.callParent(arguments);
	}
});