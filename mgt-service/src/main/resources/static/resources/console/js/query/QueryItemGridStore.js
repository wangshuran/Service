Ext.define('SystemConsole.query.QueryItemGridStore', {
	extend : 'Ext.data.Store',
	model : 'SystemConsole.query.QueryItemModel',
	autoDestroy:true,
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		batchActions : false,
		api : {
			read : context + '/query/item/list',
			create : context + '/query/item/add',
			update : context + '/query/item/update',
			destroy : context + '/query/item/delete'
		},
		reader : {
			type : 'json',
			successProperty : 'success',
			root : 'queryItemList',
			totalProperty : 'totalCount'
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
					operation.records[0].set('queryItemId', o.queryItemId);
				}
			}
		}
	}
});