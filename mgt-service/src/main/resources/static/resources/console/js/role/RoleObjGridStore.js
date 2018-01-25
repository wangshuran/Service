Ext.define('SystemConsole.role.RoleObjGridStore', {
	extend : 'Ext.data.Store',
	model : 'SystemConsole.role.RoleObjModel',
	autoDestroy:true,
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		batchActions : false,
		api : {
			read : context + '/sysroleobj/list',
			create : context + '/sysroleobj/add',
			update : context + '/sysroleobj/update',
			destroy : context + '/sysroleobj/delete'
		},
		reader : {
			type : 'json',
			successProperty : 'success',
			root : 'roleObjList',
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
					operation.records[0].set('roleObjId', o.roleObjId);
				}
			}
		}
	}
});