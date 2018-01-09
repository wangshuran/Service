Ext.define('SystemConsole.wordbook.WordBookItemsGridPanel', {
			extend : 'Ext.grid.Panel',
			alias : 'widget.wordbookitemsgridpanel',
			initComponent : function() {
				var me = this;
				this.store = Ext
						.create('SystemConsole.wordbook.WordBookItemsGridStore');
				this.columns = [{
							header : '字典项标识',
							flex : 1,
							dataIndex : 'wbiKey',
							editor : {}
						}, {
							text : '字典项值',
							flex : 1,
							dataIndex : 'wbiValue',
							editor : {}
						}, {
							text : '字典项描述',
							flex : 1,
							dataIndex : 'wbiDesc',
							editor : {}
						}, {
							text : '字典项排序',
							flex : 1,
							dataIndex : 'wbiWeight',
							editor : {
								xtype : 'numberfield'
							}
						}];
				var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
							clicksToMoveEditor : 1,
							autoCancel : false
						});
				this.plugins = [rowEditing];
				this.tbar = [{
					itemId : 'addWordBookItemBtn',
					text : '增加字典项',
					iconCls : 'noteadd',
					handler : function() {
						rowEditing.cancelEdit();
						var sm = me.up('wordbookpanel')
								.down('wordbookgridpanel').getSelectionModel();
						var r = Ext.create(
								'SystemConsole.wordbook.WordBookItemsModel', {
									wbId : sm.getSelection()[0].get('wbId')
								});
						me.store.insert(0, r);
						rowEditing.startEdit(0, 0);
					},
					disabled : true
				}, {
					itemId : 'removeWordBookItemBtn',
					text : '删除字典项',
					iconCls : 'notedel',
					handler : function() {
						var sm = me.getSelectionModel();
						rowEditing.cancelEdit();
						me.store.remove(sm.getSelection()[0]);
						me.store.sync();
						if (me.store.getCount() > 0) {
							sm.select(0);
						}
					},
					disabled : true
				}];
				this.listeners = {
					selectionchange : this.wbiGridSelectionChange,
					edit : this.doEditWbItem
				};
				this.callParent(arguments);
			},
			wbiGridSelectionChange : function(sm, records) {
				this.down('#removeWordBookItemBtn')
						.setDisabled(!records.length);
			},
			doEditWbItem : function(editor, e) {
				e.grid.plugins[0].completeEdit();
				e.store.sync();
				e.record.commit();
			}
		});