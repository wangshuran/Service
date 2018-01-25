/**
 * 列表下拉框定义
 */

Ext.define('SystemConsole.component.TableQueryCmp', {
	extend : 'Ext.form.field.ComboBox',
	alias : 'widget.tablequerycmp',
	queryMode : 'local',
	editable : false,
	emptyText : '请选择...',
	treeDivId : null,
	grid : null,
	initComponent : function() {
		var me = this;
		this.store = Ext.create('Ext.data.Store', {
			fields : [],
			data : [ {} ]
		});
		this.tpl = '<div id="' + this.treeDivId + '"></div>';

		this.listeners = {
			'expand' : function() {
				if (!me.grid.renderTo) {
					me.grid.renderTo = me.treeDivId;
					me.grid.render(me.treeDivId);
					me.grid.store.load({params:me.grid.params});
				}
			}
		}
		this.callParent();
	}
});