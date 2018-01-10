Ext.define('SystemConsole.wordbook.data.${wordbook}', {
	data : null,
	constructor : function() {
		var me = this;
		this.data = Ext.decode('${wordbookJson}');
	}
});