Ext.define('SystemConsole.wordbook.WordBookGridPanel', {
			extend : 'Ext.grid.Panel',
			alias : 'widget.wordbookgridpanel',
			initComponent : function() {
				var me = this;
				this.store = Ext
						.create('SystemConsole.wordbook.WordBookGridStore',{pageSize:20});
				this.store.load();
				this.columns = [{
							header : '字典编码',
							flex : 1,
							dataIndex : 'wbKey',
							editor : {}
						}, {
							text : '字典名称',
							flex : 1,
							dataIndex : 'wbName',
							editor : {}
						}, {
							text : '字典描述',
							flex : 1,
							dataIndex : 'wbDesc',
							editor : {}
						}];
				var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
							clicksToMoveEditor : 1,
							autoCancel : false
						});
				this.plugins = [rowEditing];
				this.tbar = [{
					text : '增加字典',
					iconCls : 'bookadd',
					handler : function() {
						rowEditing.cancelEdit();
						var r = Ext.create(
								'SystemConsole.wordbook.WordBookModel', {});
						me.store.insert(0, r);
						rowEditing.startEdit(0, 0);
					}
				}, {
					itemId : 'removeWordBookBtn',
					text : '删除字典',
					iconCls : 'bookdel',
					disabled : true,
					handler : function() {
						var sm = me.getSelectionModel();
						rowEditing.cancelEdit();
						me.store.remove(sm.getSelection()[0]);
						me.store.sync();
						if (me.store.getCount() > 0) {
							sm.select(0);
						}
					}
				}, {
					itemId : 'publishWordBookBtn',
					text : '发布字典',
					iconCls : 'bookgo',
					disabled : true,
					handler : function() {
						var sm = me.getSelectionModel();
						me.publishWordBook(sm.getSelection()[0].get("wbId"));
					}
				}];
				this.bbar = Ext.create('Ext.PagingToolbar', {
							store : me.store,
							displayInfo : true,
							displayMsg : '当前显示 {0} - {1} of {2}',
							emptyMsg : "没有数据"
						});
				this.listeners = {
					selectionchange : this.wbGridSelectionChange,
					edit : this.doEditWb
				};
				this.callParent(arguments);
			},
			wbGridSelectionChange : function(sm, records) {
				this.down('#removeWordBookBtn').setDisabled(!records.length);
				this.down('#publishWordBookBtn').setDisabled(!records.length);
				this.up('wordbookpanel').down('wordbookitemsgridpanel')
						.down('#addWordBookItemBtn')
						.setDisabled(!records.length);
				if (records.length && records[0].get('wbId') != '') {
					this.up('wordbookpanel').down('wordbookitemsgridpanel')
							.getStore().load({
										params : {
											wbId : records[0].get('wbId')
										}
									});
				}
			},
			doEditWb : function(editor, e) {
				e.grid.plugins[0].completeEdit();
				e.store.sync();
				e.record.commit();
			},
			publishWordBook : function(wbId) {
				Ext.Ajax.request({
							url : context + '/wordbook/publish/'+wbId,
							success : function(response) {
								var o = Ext.decode(response.responseText);
								if (o.success) {
									Ext.MessageBox.alert('提示', '字典发布成功');
								}
							}
						});
			}
		});