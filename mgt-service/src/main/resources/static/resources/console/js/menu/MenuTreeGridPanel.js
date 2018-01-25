/**
 * 菜单管理
 */

Ext.define('SystemConsole.menu.MenuTreeGridPanel', {
			extend : 'Ext.tree.Panel',
			alias : 'widget.menutreegridview',
			useArrows : true,
			rootVisible : false,
			initComponent : function() {
				this.store = Ext.create('SystemConsole.menu.MenuTreeGridStore');
				this.columns = [{
							xtype : 'treecolumn',
							text : '菜单名称',
							width:350,
							dataIndex : 'menuName',
							locked:true
						}, {
							text : '菜单链接',
							flex : 3,
							dataIndex : 'menuUrl'
						}, {
							text : '菜单样式',
							flex : 1,
							dataIndex : 'menuIconCls'
						}, {
							text : '显示模式',
							flex : 1,
							dataIndex : 'displayMode',
							renderer : function(v) {
								var wbu = Ext.create(
										'SystemConsole.wordbook.WordBookUtil',
										'displayMode');
								return wbu.getWordBookName(v);
							}
						}, {
							text : '菜单描述',
							flex : 3,
							dataIndex : 'menuDesc'
						}];
				this.listeners = {
					itemcontextmenu : this.showItemContextMenu
				};
				this.callParent(arguments);
			},
			/**
			 * 右键事件
			 * @param {} view
			 * @param {} record
			 * @param {} item
			 * @param {} index
			 * @param {} e
			 */
			showItemContextMenu : function(view, record, item, index, e) {
				var me = this;
				e.preventDefault();
				var contextMenu = Ext.create('Ext.menu.Menu', {
							items : [{
										text : '增加',
										handler : function() {
											me.menuAdd(record);
										}
									}, {
										text : '编辑',
										handler : function() {
											me.menuEdit(record);
										}
									}]
						});
				if (record.get('leaf')) {
					contextMenu.add({
								text : '删除',
								handler : function() {
									me.menuDel(record);
								}
							});
				}

				contextMenu.showAt(e.getXY());
			},
			/**
			 * 菜单新增窗体
			 * @param {} record
			 */
			menuAdd : function(record) {
				var me = this;
				var addView = Ext.create("SystemConsole.menu.MenuEditWindow", {
							title : '新增菜单',
							width : 400,
							height : 390,
							modal : true,
							renderTo : Ext.getBody(),
							buttons : [{
										text : '保存',
										handler : me.doMenuSave
									}, {
										text : '取消',
										handler : me.doCancle
									}]
						});
				addView.show();
				// 加载一些数据
				var tempR = Ext.create('SystemConsole.menu.MenuModel');
				tempR.set('menuParent', record.get('menuId'));
				tempR.set('menuParentName', record.get('menuName'));
				tempR.set('parentNode', record);
				addView.down('form').loadRecord(tempR);
			},
			/**
			 * 菜单编辑窗体
			 * @param {} record
			 */
			menuEdit : function(record) {
				var me = this;
				var editView = Ext.create("SystemConsole.menu.MenuEditWindow",
						{
							title : '编辑菜单',
							width : 400,
							height : 390,
							modal : true,
							renderTo : Ext.getBody(),
							buttons : [{
										text : '保存',
										handler : me.doMenuEdit
									}, {
										text : '取消',
										handler : me.doCancle
									}]
						});
				editView.show();
				record.set('menuParentName', record.parentNode.get('menuName'));
				editView.down('form').loadRecord(record);
			},
			doMenuSave : function(button) {

				Ext.MessageBox.wait('数据保存中...');

				var win = button.up('window'), form = win.down('form');
				var record = form.getRecord();
				var values = form.getValues();
				record.set(values);
				record.set('leaf', true);
				record.get('parentNode').appendChild(record);
				record.get('parentNode').set('leaf', false);
				record.get('parentNode').set('expanded', true);
				record.get('parentNode').collapse(true);
				record.get('parentNode').expand(true);
				record.get('parentNode').store.sync({
							success : function(batch, oparations) {
								win.close();
								Ext.MessageBox.hide();
								record.commit();
							}
						});
			},
			doMenuEdit : function(button) {

				Ext.MessageBox.wait('数据保存中...');
				var win = button.up('window'), form = win.down('form');
				var record = form.getRecord();
				var values = form.getValues();
				record.set(values);
				

				record.store.sync({
							success : function(batch, oparations) {
								win.close();
								Ext.MessageBox.hide();
							}
						});
				record.commit();

			},
			doCancle : function(button) {
				var win = button.up('window');
				win.close();
			},
			menuDel : function(record) {
				Ext.MessageBox.confirm('提示', '确定删除菜单?', function(btn) {
							if (btn == 'yes') {

								Ext.MessageBox.wait('数据删除中...');
								var store = record.parentNode.store;
								record.parentNode.removeChild(record);
								store.sync({
											success : function(batch,
													oparations) {
												Ext.MessageBox.hide();
											}
										});
								record.commit();
							}
						});

			}
		});