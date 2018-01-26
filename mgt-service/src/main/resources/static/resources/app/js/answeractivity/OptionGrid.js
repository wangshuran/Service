/**
 * 选项列表
 */

Ext.define('App.answeractivity.OptionGrid', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.aaoptiongrid',
	initComponent : function() {
		var me = this;

		Ext.define('aaOptionModel', {
			extend : 'Ext.data.Model',
			fields : [ 'id', 'questionId', 'optionCode', 'optionContent','explainContent','explainTime']
		});

		this.store = Ext.create('Ext.data.Store', {
			autoDestroy : true,
			model : 'aaOptionModel',
			autoLoad : false,
			proxy : {
				type : 'ajax',
				actionMethods : 'POST',
				batchActions : false,
				api : {
					read : context + '/investor/aaOptionList'
				},
				reader : {
					type : 'json',
					successProperty : 'success',
					root : 'resultList',
					totalProperty : 'totalCount'
				},
				writer : {
					type : 'json',
					writeAllFields : true
				},
				listeners : {
					exception : function(proxy, response, operation) {
						var json = Ext.decode(response.responseText);
						Ext.MessageBox.show({
							title : 'REMOTE EXCEPTION',
							msg : json.message,
							icon : Ext.MessageBox.ERROR,
							buttons : Ext.Msg.OK
						});
					}
				}
			}
		});
		this.columns = [
				{
					text : '选项编号',
					flex : 1,
					dataIndex : 'optionCode'
				}, {
					header : '选项',
					flex : 3,
					dataIndex : 'optionContent'
				}, {
					header : '解答',
					flex : 3,
					dataIndex : 'explainContent'
				}, {
					header : '解答时间',
					flex : 1,
					dataIndex : 'explainTime'
				}];
		this.tbar = [ {
			text : '新增',
			iconCls : 'add',
			handler : me.showAddOptionWin
		} ];
		this.listeners = {
			itemcontextmenu : this.showOptionGridContextMenu
		};
		this.callParent(arguments);
	},
	showAddOptionWin : function(btn) {
		Ext.create("App.answeractivity.OptionEditWindow", {
			title : '新增选项',
			width : 400,
			height : 320,
			modal : true,
			renderTo : Ext.getBody(),
			buttons : [ {
				text : '保存',
				handler : function() {

					var win = this.up('window'), form = win.down('form');
					
					var store = btn.up('aaoptiongrid').getStore();
					var r = Ext.create(
							'aaOptionModel',form.getValues());
					store.insert(0, r);
					win.close();
					win=null;
				}
			}, {
				text : '关闭',
				handler : function() {
					this.up('window').close();
				}
			} ]
		}).show();

	},
	showOptionGridContextMenu : function(view, record, item, index, e) {
		var me = this;
		e.preventDefault();
		
		var contextMenu = Ext.create('Ext.menu.Menu', {
			items : [ {
				text : '编辑',
				handler : function() {
					me.showEditOptionWin(record);
				}
			}, {
				text : '删除',
				handler : function() {
					Ext.MessageBox.confirm('提示', '确定删除选项?', function(btn) {
						if (btn == 'yes') {
							me.store.remove(record);
						}
					});
				}
			} ]
		});

		contextMenu.showAt(e.getXY());
	},
	showEditOptionWin : function(record) {
		var me = this;
		var editView = Ext.create("App.answeractivity.OptionEditWindow", {
			title : '编辑选项',
			width : 400,
			height : 320,
			modal : true,
			renderTo : Ext.getBody(),
			buttons : [ {
				text : '保存',
				handler : function() {

					var win = this.up('window'), form = win.down('form');

					var values = form.getValues();
					record.set(values);
					
					win.close();
					win=null;

				}
			}, {
				text : '关闭',
				handler : function() {
					this.up('window').close();
				}
			} ]
		});
		editView.show();
		var form = editView.down('form');
		// 加载数据
		form.loadRecord(record);

	}
});
