Ext.define('SystemConsole.wordbook.data.aaStage', {
	data : null,
	constructor : function() {
		var me = this;
		this.data = Ext.decode('[{"wbiId":22,"wbId":9,"wbiKey":"SCHEDULE","wbiValue":"排期中","wbiDesc":"","wbiWeight":1,"wbKey":null},{"wbiId":23,"wbId":9,"wbiKey":"TOSTART","wbiValue":"待开始","wbiDesc":"","wbiWeight":2,"wbKey":null},{"wbiId":24,"wbId":9,"wbiKey":"PREHEATING","wbiValue":"预热中","wbiDesc":"","wbiWeight":3,"wbKey":null},{"wbiId":25,"wbId":9,"wbiKey":"ANSWERING","wbiValue":"答题中","wbiDesc":"","wbiWeight":4,"wbKey":null},{"wbiId":26,"wbId":9,"wbiKey":"FINISHED","wbiValue":"完成","wbiDesc":"","wbiWeight":5,"wbKey":null}]');
	}
});