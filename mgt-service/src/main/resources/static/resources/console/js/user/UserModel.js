Ext.define('SystemConsole.user.UserModel', {
			extend : 'Ext.data.Model',
			fields : ['userId', 'userCode', 'userName', 'orgId', 'orgNamePath',
					'orgIdPath', 'loginName', 'password', 'status','sex','email','level','entryDate']
		});