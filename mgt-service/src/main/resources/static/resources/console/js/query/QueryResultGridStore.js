Ext.define('SystemConsole.query.QueryResultGridStore', {
	extend : 'Ext.data.Store',
	model : 'SystemConsole.query.QueryResultModel',
	autoDestroy:true,
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		batchActions : false,
		api : {
			read : context + '/query/result/list',
			create : context + '/query/result/add',
			update : context + '/query/result/update',
			destroy : context + '/query/result/delete'
		},
		reader : {
			type : 'json',
			successProperty : 'success',
			root : 'resultItemList',
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
					operation.records[0].set('resultItemId', o.resultItemId);
				}
			}
		}
	}
});