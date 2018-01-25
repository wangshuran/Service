Ext.define('SystemConsole.wordbook.data.withdrawStatus', {
	data : null,
	constructor : function() {
		var me = this;
		this.data = Ext.decode('[{"wbiId":10,"wbId":5,"wbiKey":"audit","wbiValue":"审核中","wbiDesc":"","wbiWeight":1,"wbKey":null},{"wbiId":11,"wbId":5,"wbiKey":"success","wbiValue":"成功","wbiDesc":"","wbiWeight":2,"wbKey":null},{"wbiId":12,"wbId":5,"wbiKey":"fail","wbiValue":"失败","wbiDesc":"","wbiWeight":3,"wbKey":null}]');
	}
});