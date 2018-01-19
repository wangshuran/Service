/**
 * 视图列表
 */

Ext.define('SystemConsole.view.ViewGridPanel', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.viewgridpanel',
	initComponent: function () {
		var me = this;
		this.store = Ext.create('SystemConsole.view.ViewGridStore', { pageSize: 50 });
		this.store.load();
		this.columns = [{
			text: '视图标识',
			flex: 1,
			dataIndex: 'viewKey'
		}, {
				header: '视图标题',
				flex: 1,
				dataIndex: 'title'
			}, {
				text: '视图描述',
				flex: 2,
				dataIndex: 'remark'
			}];
		this.tbar = [{
			text: '增加视图',
			iconCls: 'add',
			handler: me.showAddViewWin
		}, {
				itemId: 'removeViewBtn',
				text: '删除视图',
				iconCls: 'delete',
				disabled: true,
				handler: function () {
					Ext.MessageBox.confirm('提示', '确定删除视图?', function (btn) {
						if (btn == 'yes') {
							var sm = me.getSelectionModel();
							me.store.remove(sm.getSelection()[0]);
							me.store.sync();
						}
					});

				}
			}, {
				itemId: 'copyViewBtn',
				text: '复制视图',
				iconCls: 'copy',
				disabled: true,
				handler: function () {
					Ext.MessageBox.confirm('提示', '确定复制视图?', function (btn) {
						if (btn == 'yes') {
							var sm = me.getSelectionModel();
							var record = sm.getSelection()[0];
							Ext.Ajax.request({
							    url: context+'/query/copy/'+record.data.id,
							    method:'POST',
							    success: function(response){
							        var text = response.responseText;
							        var json = Ext.JSON.decode(text);
							        if(json.success){
							        	Ext.MessageBox.alert('提示','复制成功');
							        	me.getStore().load();
							        }else{
							        	Ext.MessageBox.alert('提示','复制失败'+json.msg);
							        }
							    }
							});
						}
					});

				}
			}];
		this.bbar = Ext.create('Ext.PagingToolbar', {
			store: me.store,
			displayInfo: true,
			displayMsg: '当前显示  第{0} - {1} of {2}',
			emptyMsg: "没有数据"
		});
		this.listeners = {
			selectionchange: this.viewGridSelectionChange,
			itemcontextmenu: this.showViewGridContextMenu
		};
		this.callParent(arguments);
	},
	showAddViewWin: function (btn) {
		Ext.create("SystemConsole.view.ViewEditWindow", {
			title: '新增视图',
			width: 700,
			height: 250,
			modal: true,
			renderTo: Ext.getBody(),
			buttons: [{
				text: '保存',
				handler: function () {

					Ext.MessageBox.wait('数据保存中...');

					var win = this.up('window'), form = win.down('form');

					form.getForm().submit({
						clientValidation: true,
						url: context + '/view/add',
						success: function (form, action) {
							win.close();
							Ext.MessageBox.hide();
							btn.up('viewgridpanel').getStore().load();
						},
						failure: function (form, action) {
							switch (action.failureType) {
								case Ext.form.action.Action.CLIENT_INVALID:
									Ext.Msg.alert('提示', '请正确填写页面信息');
									break;
								case Ext.form.action.Action.CONNECT_FAILURE:
									Ext.Msg.alert('失败', '服务器连接超时');
									break;
								case Ext.form.action.Action.SERVER_INVALID:
									Ext.Msg.alert('失败',
										action.result.message);
							}
						}
					});

				}
			}, {
					text: '关闭',
					handler: function () {
						this.up('window').close();
					}
				}]
		}).show();

	},
	viewGridSelectionChange: function (sm, records) {
		this.down('#removeViewBtn').setDisabled(!records.length);
		this.down('#copyViewBtn').setDisabled(!records.length);
	},
	showViewGridContextMenu: function (view, record, item, index, e) {
		var me = this;
		e.preventDefault();
		var contextMenu = Ext.create('Ext.menu.Menu', {
			items: [{
				text: '配置',
				handler: function () {
					me.showQueryConfig(record);
				}
			}, {
					text: '编辑',
					handler: function () {
						me.showEditViewWin(record);
					}
				}, {
					text: '删除',
					handler: function () {
						Ext.MessageBox.confirm('提示', '确定删除查询?', function (btn) {
							if (btn == 'yes') {
								me.store.remove(record);
								me.store.sync();
							}
						});
					}
				}]
		});

		contextMenu.showAt(e.getXY());
	},
	showQueryConfig: function (record) {
		var me = this;
		var tabId = 'tab_query_' + record.data.queryKey;
		var tabs = me.up('viewport').down('maintabpanel');
		var isExitTab = Ext.getCmp(tabId);
		if (!Ext.isEmpty(isExitTab)) {
			tabs.setActiveTab(isExitTab);
			return;
		}
		var queryConfigPanel = Ext.create('SystemConsole.query.QueryConfigPanel', {
			id: tabId,
			title: '查询[' + record.data.queryKey + ']配置',
			queryId: record.data.id,
			iconCls: 'tabcss',
			closable: true,
			closeAction: 'destory'
		});

		var tab = tabs.add(queryConfigPanel);
		tabs.setActiveTab(tab);

		queryConfigPanel.loadRecord(record);
		queryConfigPanel.load({
			url: context + '/query/config/find',
			params: { queryId: record.data.id },
			success: function (form, action) {
				
			},
			failure: function (form, action) {
				switch (action.failureType) {
					case Ext.form.action.Action.CLIENT_INVALID:
						Ext.Msg.alert('提示', '请正确填写页面信息');
						break;
					case Ext.form.action.Action.CONNECT_FAILURE:
						Ext.Msg.alert('失败', '服务器连接超时');
						break;
					case Ext.form.action.Action.SERVER_INVALID:
						Ext.Msg.alert('失败', action.result.message);
				}
			}
		});
	},
	showEditViewWin: function (record) {
		var me = this;
		var editView = Ext.create("SystemConsole.view.ViewEditWindow", {
			title: '编辑视图',
			width: 700,
			height: 320,
			modal: true,
			renderTo: Ext.getBody(),
			buttons: [{
				text: '保存',
				handler: function () {

					Ext.MessageBox.wait('数据保存中...');

					var win = this.up('window'), form = win.down('form');

					form.getForm().submit({
						clientValidation: true,
						url: context + '/view/update',
						success: function (form, action) {
							win.close();
							Ext.MessageBox.hide();
							me.getStore().load();
						},
						failure: function (form, action) {
							switch (action.failureType) {
								case Ext.form.action.Action.CLIENT_INVALID:
									Ext.Msg.alert('提示', '请正确填写页面信息');
									break;
								case Ext.form.action.Action.CONNECT_FAILURE:
									Ext.Msg.alert('失败', '服务器连接超时');
									break;
								case Ext.form.action.Action.SERVER_INVALID:
									Ext.Msg.alert('失败',
										action.result.message);
							}
						}
					});

				}
			}, {
					text: '关闭',
					handler: function () {
						this.up('window').close();
					}
				}]
		});
		editView.show();
		var form = editView.down('form');
		//加载数据
		form.loadRecord(record);

	}
});
