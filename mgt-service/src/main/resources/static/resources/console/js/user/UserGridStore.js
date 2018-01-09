Ext.define('SystemConsole.user.UserGridStore', {
	extend : 'Ext.data.Store',
	model : 'SystemConsole.user.UserModel',
	autoDestroy:true,
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		batchActions : false,
		api : {
			read : context + '/sysuser/list',
			create : context + '/sysuser/add',
			update : context + '/sysuser/update',
			destroy : context + '/sysuser/delete'
		},
		reader : {
			type : 'json',
			successProperty : 'success',
			root : 'userList',
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