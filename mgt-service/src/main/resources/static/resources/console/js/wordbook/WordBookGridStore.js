Ext.define('SystemConsole.wordbook.WordBookGridStore', {
	extend : 'Ext.data.Store',
	model : 'SystemConsole.wordbook.WordBookModel',
	// autoLoad : true,
	autoDestroy:true,
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		batchActions : false,
		api : {
			read : context + '/wordbook/list',
			create : context + '/wordbook/add',
			update : context + '/wordbook/update',
			destroy : context + '/wordbook/delete'
		},
		reader : {
			type : 'json',
			successProperty : 'success',
			root : 'wbList',
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
					operation.records[0].set('wbId', o.wbId);
				}
			}
		}
	}
});