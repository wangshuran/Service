Ext.define('SystemConsole.query.ExportSchemeGridStore', {
	extend : 'Ext.data.Store',
	model : 'SystemConsole.query.ExportSchemeModel',
	autoDestroy:true,
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		batchActions : false,
		api : {
			read : context + '/query/exportscheme/list'
		},
		reader : {
			type : 'json',
			successProperty : 'success',
			root : 'exportSchemeList',
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
		
	}
});