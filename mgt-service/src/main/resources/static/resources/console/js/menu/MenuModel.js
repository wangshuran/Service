Ext.define('SystemConsole.menu.MenuModel', {
			extend : 'Ext.data.Model',

			fields : [{
						name : 'menuId'
					}, {
						name : 'menuName'
					}, {
						name : 'menuUrl'
					}, {
						name : 'menuIconCls'
					}, {
						name : 'menuDesc'
					}, {
						name : 'menuWeight'
					}, {
						name : 'menuParent'
					}, {
						name : 'menuParentName'
					}, {
						name : 'displayMode'
					}]

		});