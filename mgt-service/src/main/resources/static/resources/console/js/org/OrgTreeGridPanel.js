/**
 * 组织树
 */
Ext.define('SystemConsole.org.OrgTreeGridPanel', {
	extend: 'Ext.tree.Panel',
	alias: 'widget.orgtreegridview',
	useArrows: true,
	rootVisible: false,
	initComponent: function () {
		this.store = Ext.create('SystemConsole.org.OrgTreeGridStore');
		this.columns = [{
			xtype: 'treecolumn',
			text: '机构名称',
			width: 350,
			dataIndex: 'orgName',
			locked: true
		}, {
				text: '机构代码',
				flex: 1,
				dataIndex: 'orgNumber'
			}, {
				text: '机构描述',
				flex: 1,
				dataIndex: 'orgDesc'
			}, {
				text: '排序',
				flex: 1,
				dataIndex: 'orgWeight'
			}];
		this.listeners = {
			itemcontextmenu: this.showGroupTreeContextMenu
		};
		this.callParent(arguments);
	},
	/**
	 * 机构树右键事件处理
	 * 
	 * @param {}
	 *            view
	 * @param {}
	 *            record
	 * @param {}
	 *            item
	 * @param {}
	 *            index
	 * @param {}
	 *            e
	 */
	showGroupTreeContextMenu: function (view, record, item, index, e) {
		var me = this;
		e.preventDefault();
		var contextMenu = Ext.create('Ext.menu.Menu', {
			items: [{
				text: '增加',
				handler: function () {
					me.orgAdd(record);
				}
			}]
		});
		if (record.get('orgName') != '根机构') {
			contextMenu.add({
				text: '编辑',
				handler: function () {
					me.orgEdit(record);
				}
			})
		}
		if (!record.hasChildNodes() && record.get('orgName') != '根机构') {
			contextMenu.add({
				text: '删除',
				handler: function () {
					me.orgDel(record);
				}
			});
		}

		contextMenu.showAt(e.getXY());
	},
	orgAdd: function (record) {
		var me = this;
		var addView = Ext.create("SystemConsole.org.OrgEditWindow", {
			title: '新增机构',
			width: 400,
			height: 320,
			modal: true,
			renderTo: Ext.getBody(),
			buttons: [{
				text: '保存',
				handler: me.doOrgSave
			}, {
					text: '取消',
					handler: me.doCancle
				}]
		});
		addView.show();
		// 加载一些数据
		var tempR = Ext.create('SystemConsole.org.OrgModel');
		tempR.set('orgParent', record.get('orgId'));
		tempR.set('orgParentName', record.get('orgName'));
		tempR.set('parentNode', record);
		addView.down('form').loadRecord(tempR);

	},
	orgEdit: function (record) {
		var me = this;
		var editView = Ext.create("SystemConsole.org.OrgEditWindow", {
			title: '编辑机构',
			width: 400,
			height: 320,
			modal: true,
			renderTo: Ext.getBody(),
			buttons: [{
				text: '保存',
				handler: me.doOrgUpdate
			}, {
					text: '取消',
					handler: me.doCancle
				}]
		});
		editView.show();
		// 加载一些数据
		record.set('orgParentName', record.parentNode.get('orgName'));
		editView.down('form').loadRecord(record);

	},
	orgDel: function (record) {
		Ext.MessageBox.confirm('提示', '确定删除机构?', function (btn) {
			if (btn == 'yes') {

				Ext.MessageBox.wait('数据删除中...');
				var store = record.parentNode.store;
				record.parentNode.removeChild(record);
				store.sync({
					success: function (batch,
						oparations) {
						Ext.MessageBox.hide();
					}
				});
				record.commit();
			}
		});

	},
	doOrgSave: function (button) {
		Ext.MessageBox.wait('数据保存中...');

		var win = button.up('window'), form = win.down('form');
		var record = form.getRecord();

		var values = form.getValues();
		record.set(values);
		record.set('leaf', true);
		record.set('iconCls', 'orgtree_group');
		record.get('parentNode').appendChild(record);
		record.get('parentNode').set('leaf', false);
		record.get('parentNode').set('expanded', true);
		record.get('parentNode').collapse(true);
		record.get('parentNode').expand(true);
		record.get('parentNode').store.sync({
			success: function (batch, oparations) {
				win.close();
				Ext.MessageBox.hide();
				record.commit();
			}
		});

	},
	doOrgUpdate: function (button) {
		Ext.MessageBox.wait('数据保存中...');
		var win = button.up('window'), form = win.down('form');
		var record = form.getRecord();
		var values = form.getValues();
		record.set(values);
		record.store.sync({
			success: function (batch, oparations) {
				win.close();
				Ext.MessageBox.hide();
			}
		});
		record.commit();

	},
	doCancle: function (button) {
		var win = button.up('window');
		win.close();
	}
});