/**
 * 查询配置管理
 */

Ext.define('SystemConsole.query.QueryGridPanel', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.querygridpanel',
	initComponent: function () {
		var me = this;
		this.store = Ext.create('SystemConsole.query.QueryGridStore', { pageSize: 50 });
		this.store.load();
		this.columns = [{
			text: '查询标识',
			flex: 1,
			dataIndex: 'queryKey'
		}, {
				header: '查询标题',
				flex: 1,
				dataIndex: 'title'
			}, {
				text: '查询描述',
				flex: 2,
				dataIndex: 'remark'
			}];
		this.tbar = [{
			text: '增加查询',
			iconCls: 'add',
			handler: me.showAddQueryView
		}, {
				itemId: 'removeQueryBtn',
				text: '删除查询',
				iconCls: 'delete',
				disabled: true,
				handler: function () {
					Ext.MessageBox.confirm('提示', '确定删除查询?', function (btn) {
						if (btn == 'yes') {
							var sm = me.getSelectionModel();
							me.store.remove(sm.getSelection()[0]);
							me.store.sync();
						}
					});

				}
			}, {
				itemId: 'copyQueryBtn',
				text: '复制查询',
				iconCls: 'copy',
				disabled: true,
				handler: function () {
					Ext.MessageBox.confirm('提示', '确定复制查询?', function (btn) {
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
			selectionchange: this.queryGridSelectionChange,
			itemcontextmenu: this.showQueryGridContextMenu
		};
		this.callParent(arguments);
	},
	showAddQueryView: function (btn) {
		Ext.create("SystemConsole.query.QueryEditWindow", {
			title: '新增查询',
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
						url: context + '/query/add',
						success: function (form, action) {
							win.close();
							Ext.MessageBox.hide();
							btn.up('querygridpanel').getStore().load();
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
										action.result.msg);
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
	queryGridSelectionChange: function (sm, records) {
		this.down('#removeQueryBtn').setDisabled(!records.length);
		this.down('#copyQueryBtn').setDisabled(!records.length);
	},
	showQueryGridContextMenu: function (view, record, item, index, e) {
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
						me.showEditUserWin(record);
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
		queryConfigPanel.getForm().findField('id').setValue(null);
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
						Ext.Msg.alert('失败', action.result.msg);
				}
			}
		});
	},
	showEditUserWin: function (record) {
		var me = this;
		var editView = Ext.create("SystemConsole.query.QueryEditWindow", {
			title: '编辑查询',
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
						url: context + '/query/update',
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
										action.result.msg);
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
