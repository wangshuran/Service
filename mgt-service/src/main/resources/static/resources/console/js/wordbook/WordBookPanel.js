Ext.define('SystemConsole.wordbook.WordBookPanel', {
	extend: 'Ext.Panel',
	alias: 'widget.wordbookpanel',
	layout: 'border',
	border: false,
	initComponent: function () {
		this.items = [this.createWordBookGrid(), this.createWordBookItemsGrid()];

		this.callParent(arguments);
	},
	createWordBookGrid: function () {
		return Ext.create('SystemConsole.wordbook.WordBookGridPanel', {
			region: 'center'
		});
	},
	createWordBookItemsGrid: function () {
		return Ext.create(
			'SystemConsole.wordbook.WordBookItemsGridPanel', {
				region: 'south',
				split: true,
				collapseMode: 'mini',
				collapsible: true,
				header: false,
				height: 200
			});
	}
});