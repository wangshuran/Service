Ext.define('SystemConsole.wordbook.data.sex', {
	data : null,
	constructor : function() {
		var me = this;
		this.data = Ext.decode('[{"wbiId":2,"wbId":1,"wbiKey":"M","wbiValue":"男","wbiDesc":"","wbiWeight":0,"wbKey":"sex"},{"wbiId":1,"wbId":1,"wbiKey":"F","wbiValue":"女","wbiDesc":"","wbiWeight":1,"wbKey":"sex"}]');
	}
});