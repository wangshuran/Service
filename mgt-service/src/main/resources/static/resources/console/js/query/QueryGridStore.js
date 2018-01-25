Ext.define('SystemConsole.query.QueryGridStore', {
	extend : 'Ext.data.Store',
	model : 'SystemConsole.query.QueryModel',
	autoDestroy:true,
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		batchActions : false,
		api : {
			read : context + '/query/list',
			create : context + '/query/add',
			update : context + '/query/update',
			destroy : context + '/query/delete'
		},
		reader : {
			type : 'json',
			successProperty : 'success',
			root : 'queryList',
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
					operation.records[0].set('userId', o.userId);
				}
			}
		}
	}
});