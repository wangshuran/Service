/**
 * 菜单管理列表数据管理
 */

 
 Ext.define('SystemConsole.menu.MenuTreeGridStore', {
	extend : 'Ext.data.TreeStore',
	model : 'SystemConsole.menu.MenuModel',
	proxy : {
		waitMsg : '正在加载数据...',
		type : 'ajax',
		actionMethods : 'POST',
		batchActions : false,
		autoDestroy : true,
		api : {
			read : context + '/sysmenu/menutree',
			create : context + '/sysmenu/add',
			update : context + '/sysmenu/update',
			destroy : context + '/sysmenu/delete'
		},
		writer : {
			type : 'json',
			writeAllFields : true
		},
		listeners : {
			exception : function(proxy, response, operation) {
				Ext.MessageBox.show({
					title : 'REMOTE EXCEPTION',
					msg : operation.getError().statusText,
					icon : Ext.MessageBox.ERROR,
					buttons : Ext.Msg.OK
				});
			}
		}
	},
	listeners : {
		write : function(proxy, operation) {
			if (operation.action == 'destroy') {
			}
			if (operation.action == 'create') {
				var o = Ext.decode(operation.response.responseText);
				if (o.success) {
					operation.records[0].set('menuId', o.menuId);
				}
			}
		}
	}
});