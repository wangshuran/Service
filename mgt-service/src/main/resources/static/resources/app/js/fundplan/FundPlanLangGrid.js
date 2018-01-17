/**
 * 资金计划多语言管理
 */

Ext.define('App.fundplan.FundPlanLangGrid', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.fundplanlanggrid',
	initComponent : function() {
		var me = this;

		Ext.define('fundPlanLangModel', {
			extend : 'Ext.data.Model',
			fields : [ 'id', 'fundPlanId', 'lang', 'title', 'remark' ]
		});

		this.store = Ext.create('Ext.data.Store', {
			autoDestroy : true,
			model : 'fundPlanLangModel',
			autoLoad : false,
			proxy : {
				type : 'ajax',
				actionMethods : 'POST',
				batchActions : false,
				api : {
					read : context + '/investor/fundPlanLangList'
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
					text : '语言',
					flex : 1,
					dataIndex : 'lang',
					renderer : function(v) {
						var wbu = Ext.create(
								'SystemConsole.wordbook.WordBookUtil', 'lang');
						return wbu.getWordBookName(v);
					}
				}, {
					header : '标题',
					flex : 1,
					dataIndex : 'title'
				}, {
					text : '描述',
					flex : 1,
					dataIndex : 'remark'
				} ];
		this.tbar = [ {
			text : '新增',
			iconCls : 'add',
			handler : me.showAddFundPlanLangWin
		} ];
		this.listeners = {
			itemcontextmenu : this.showFundPlanLangGridContextMenu
		};
		this.callParent(arguments);
	},
	showAddFundPlanLangWin : function(btn) {
		Ext.create("App.fundplan.FundPlanLangEditWindow", {
			title : '新增计划多语言',
			width : 400,
			height : 300,
			modal : true,
			renderTo : Ext.getBody(),
			buttons : [ {
				text : '保存',
				handler : function() {

					var win = this.up('window'), form = win.down('form');
					
					var store = btn.up('fundplanlanggrid').getStore();
					var r = Ext.create(
							'fundPlanLangModel',form.getValues());
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
	showFundPlanLangGridContextMenu : function(view, record, item, index, e) {
		var me = this;
		e.preventDefault();
		
		var contextMenu = Ext.create('Ext.menu.Menu', {
			items : [ {
				text : '编辑',
				handler : function() {
					me.showEditFundPlanLangWin(record);
				}
			}, {
				text : '删除',
				handler : function() {
					Ext.MessageBox.confirm('提示', '确定删除多语言?', function(btn) {
						if (btn == 'yes') {
							me.store.remove(record);
						}
					});
				}
			} ]
		});

		contextMenu.showAt(e.getXY());
	},
	showEditFundPlanLangWin : function(record) {
		var me = this;
		var editView = Ext.create("App.fundplan.FundPlanLangEditWindow", {
			title : '编辑计划多语言',
			width : 400,
			height : 300,
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
