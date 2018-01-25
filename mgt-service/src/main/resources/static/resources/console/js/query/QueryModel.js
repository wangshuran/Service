Ext.define('SystemConsole.query.QueryModel', {
			extend : 'Ext.data.Model',
			fields : ['id', 'queryKey', 'title', 'remark', 'isPage',
					'pageNum', 'fetchSql', 'countSql']
		});