/**
 * 资料分类树
 */
Ext.define('SystemConsole.datacategory.DataCategoryTreeGridPanel', {
	extend: 'Ext.tree.Panel',
	alias: 'widget.datacategorytreegridview',
	useArrows: true,
	rootVisible: true,
	initComponent: function () {
		this.store = Ext.create('SystemConsole.datacategory.DataCategoryTreeGridStore');
		this.columns = [{
			xtype: 'treecolumn',
			text: '分类标识',
			width: 350,
			dataIndex: 'flag',
			locked: true
		}, {
				text: '分类描述',
				flex: 1,
				dataIndex: 'remark'
			}];
		this.listeners = {
			itemcontextmenu: this.showDataCategoryTreeContextMenu
		};
		this.callParent(arguments);
	},
	/**
	 * 资料分类树右键事件处理
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
	showDataCategoryTreeContextMenu: function (view, record, item, index, e) {
		var me = this;
		e.preventDefault();
		var contextMenu = Ext.create('Ext.menu.Menu', {
			items: [{
				text: '增加',
				handler: function () {
					me.dataCategoryAdd(record);
				}
			}]
		});
		if (!record.isRoot()) {
			contextMenu.add({
				text: '编辑',
				handler: function () {
					me.dataCategoryEdit(record);
				}
			})
		}
		if (!record.hasChildNodes() && !record.isRoot()) {
			contextMenu.add({
				text: '删除',
				handler: function () {
					me.dataCategoryDel(record);
				}
			});
		}

		contextMenu.showAt(e.getXY());
	},
	dataCategoryAdd: function (record) {
		var me = this;
		var addView = Ext.create("SystemConsole.datacategory.DataCategoryEditWindow", {
			title: '新增资料分类',
			width: 400,
			height: 320,
			modal: true,
			renderTo: Ext.getBody(),
			buttons: [{
				text: '保存',
				handler: me.doDataCategorySave
			}, {
					text: '取消',
					handler: me.doCancle
				}]
		});
		addView.show();
		// 加载一些数据
		var tempR = Ext.create('SystemConsole.datacategory.DataCategoryModel');
		tempR.set('dcParent', record.get('dcId'));
		tempR.set('dcParentName', record.get('flag')+'-'+record.get('remark'));
		tempR.set('parentNode', record);
		addView.down('form').loadRecord(tempR);

	},
	dataCategoryEdit: function (record) {
		var me = this;
		var editView = Ext.create("SystemConsole.datacategory.DataCategoryEditWindow", {
			title: '编辑资料分类',
			width: 400,
			height: 320,
			modal: true,
			renderTo: Ext.getBody(),
			buttons: [{
				text: '保存',
				handler: me.doDataCategoryUpdate
			}, {
					text: '取消',
					handler: me.doCancle
				}]
		});
		editView.show();
		// 加载一些数据
		record.set('dcParentName',record.parentNode.get('flag')+'-'+record.parentNode.get('remark'));
		editView.down('form').loadRecord(record);

	},
	dataCategoryDel: function (record) {
		Ext.MessageBox.confirm('提示', '确定删除资料分类?', function (btn) {
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
	doDataCategorySave: function (button) {
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
			success: function (batch, oparations) {
				win.close();
				Ext.MessageBox.hide();
				record.commit();
			}
		});

	},
	doDataCategoryUpdate: function (button) {
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