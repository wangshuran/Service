Ext.define('SystemConsole.datacategory.DataCategoryTreeGridStore', {
	extend : 'Ext.data.TreeStore',
	model : 'SystemConsole.datacategory.DataCategoryModel',
	root: {
	    expanded: true,
	    text: "资料分类",
	    flag:"资料分类"
	},
	proxy : {
		waitMsg : '正在加载数据...',
		type : 'ajax',
		actionMethods : 'POST',
		batchActions : false,
		autoDestroy : true,
		api : {
			read : context + '/datacategory/tree',
			create : context + '/datacategory/add',
			update : context + '/datacategory/update',
			destroy : context + '/datacategory/delete'
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
					operation.records[0].set('dcId', o.dcId);
				}
			}
		}
	}
});