Ext.define('SystemConsole.wordbook.WordBookItemsGridStore', {
	extend : 'Ext.data.Store',
	model : 'SystemConsole.wordbook.WordBookItemsModel',
	autoDestroy:true,
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		batchActions : false,
		api : {
			read : context + '/wordbookitem/list',
			create : context + '/wordbookitem/add',
			update : context + '/wordbookitem/update',
			destroy : context + '/wordbookitem/delete'
		},
		reader : {
			type : 'json',
			successProperty : 'success',
			root : 'wbiList',
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
					operation.records[0].set('wibId', o.wbiId);
				}
			}
		}
	}
});