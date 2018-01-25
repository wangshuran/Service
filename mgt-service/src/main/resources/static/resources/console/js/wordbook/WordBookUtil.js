/**
 * 字典解析工具
 */

Ext.define('SystemConsole.wordbook.WordBookUtil', {
	wordBook : null,
	wordBookJson : null,
	constructor : function(_wordBook) {
		var me = this;
		this.wordBook = _wordBook;
//		this.wordBookJson = Ext.decode(sendRequest(context + '/wordbook/data/'
//				+ this.wordBook));
		var wordBookSource={data:[]};
		try{
			wordBookSource = Ext.create('SystemConsole.wordbook.data.'+this.wordBook);
		}catch(e){
			
		}
		this.wordBookJson = wordBookSource.data
	},
	getWordBookName : function(code) {
		var me = this;
		var name = '';
		Ext.each(me.wordBookJson, function(item) {
			if (code == item.wbiKey) {
				name = item.wbiValue;
				return name;
			}
		});
		return name;
	}
});
