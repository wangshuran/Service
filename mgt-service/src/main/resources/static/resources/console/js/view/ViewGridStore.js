Ext.define('SystemConsole.view.ViewGridStore', {
	extend : 'Ext.data.Store',
	model : 'SystemConsole.view.ViewModel',
	autoDestroy:true,
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		batchActions : false,
		api : {
			read : context + '/view/list',
			create : context + '/view/add',
			update : context + '/view/update',
			destroy : context + '/view/delete'
		},
		reader : {
			type : 'json',
			successProperty : 'success',
			root : 'viewList',
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
				
			}
		}
	}
});