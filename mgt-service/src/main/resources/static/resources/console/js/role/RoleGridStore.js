Ext.define('SystemConsole.role.RoleGridStore', {
	extend : 'Ext.data.Store',
	model : 'SystemConsole.role.RoleModel',
	autoDestroy:true,
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		batchActions : false,
		api : {
			read : context + '/sysrole/list',
			create : context + '/sysrole/add',
			update : context + '/sysrole/update',
			destroy : context + '/sysrole/delete'
		},
		reader : {
			type : 'json',
			successProperty : 'success',
			root : 'roleList',
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
					operation.records[0].set('roleId', o.roleId);
				}
			}
		}
	}
});