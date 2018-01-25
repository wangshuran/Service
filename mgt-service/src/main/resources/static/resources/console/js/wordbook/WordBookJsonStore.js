/**
 * 数据字典jsonstore
 */

Ext.define('SystemConsole.wordbook.WordBookJsonStore', {
			extend : 'Ext.data.Store',
			autoLoad : true,
			wordBook : null,
			initComponent : function() {
				/*this.data = sendRequest(context
						+ '/system/console/wordbook/data/' + this.wordBook
						+ '.json');*/
				proxy: {
			         type: 'ajax',
			         url: context
						+ '/resources/console/js/wordbook/data/' + this.wordBook
						+ '.json',
			         reader: {
			             type: 'json'
			         }
			     }
				this.fields = ['wbiId', 'wbId', 'wbiKey','wbiValue','wbiDesc','wbiWeight'];
				this.callParent();
			}
		});
