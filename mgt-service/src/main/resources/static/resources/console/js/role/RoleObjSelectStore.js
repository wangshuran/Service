Ext.define('SystemConsole.role.RoleObjSelectStore', {
	extend : 'Ext.data.Store',
	model : 'SystemConsole.role.RoleObjModel',
	autoDestroy:true,
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		batchActions : false,
		api : {
			read : context + '/sysroleobj/listbykey'
		},
		reader : {
			type : 'json',
			successProperty : 'success',
			root : 'roleObjList',
			totalProperty : 'totalCount'
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
	}
});