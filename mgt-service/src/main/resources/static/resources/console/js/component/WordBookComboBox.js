/**
 * 字典下拉框定义
 */

Ext.define('SystemConsole.component.WordBookComboBox', {
	extend : 'Ext.form.field.ComboBox',
	alias : 'widget.wordbookfield',
	queryMode : 'local',
	displayField : 'wbiValue',
	valueField : 'wbiKey',
	wordBook : null,
	initComponent : function() {
		var wordBookSource={data:[]};
		try{
			wordBookSource = Ext.create('SystemConsole.wordbook.data.'+this.wordBook);
		}catch(e){
			
		}
		this.store = Ext.create('Ext.data.Store', {
			fields : [ 'wbiKey', 'wbiValue' ],
			autoLoad : true,
			data:wordBookSource.data/*,
			proxy : {
				type : 'ajax',
				noCache : false,
				url : context + '/wordbook/data/' + this.wordBook,
				// url : context + '/resources/console/js/wordbook/data/'
				// + this.wordBook + '.json',
				reader : {
					type : 'json',
					root : 'wbItems'
				}
			}*/
		});
		this.callParent();
	}
});