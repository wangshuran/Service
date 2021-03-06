/**
 * 查询结果列表
 */

Ext.define('SystemConsole.query.QueryItemGridPanel', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.queryitemgridpanel',
	queryId: null,
	initComponent: function () {
		var me = this;
		this.store = Ext.create('SystemConsole.query.QueryItemGridStore', { pageSize: 50 });
		this.store.on('beforeload', function (thiz, options) {

			Ext.apply(me.store.proxy.extraParams, {
				queryId: me.queryId
			});

		});
		this.store.load();
		this.columns = [{
			text: '字段名称',
			flex: 1,
			dataIndex: 'field'
		}, {
				header: '显示标题',
				flex: 1,
				dataIndex: 'label'
			}, {
				text: '配置',
				flex: 2,
				dataIndex: 'config'
			}];
		this.tbar = [{
			text: '新增',
			iconCls: 'add',
			handler: me.showAddQueryItemView
		}];
		this.viewConfig={  
	            plugins: {  
	                ptype: "gridviewdragdrop",  
	                dragText: "可用鼠标拖拽进行上下排序"  
	            },
	            listeners:{
	    			drop:me.drop
	            }
	        };
		this.listeners = {
			itemcontextmenu: this.showQueryItemGridContextMenu
		};
		this.callParent(arguments);
	},
	showAddQueryItemView: function (btn) {
		var me = this;
		var addView = Ext.create("SystemConsole.query.QueryItemEditWindow", {
			title: '新增查询项',
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
						url: context + '/query/item/add',
						success: function (form, action) {
							win.close();
							Ext.MessageBox.hide();
							btn.up('queryitemgridpanel').getStore().load();
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
		addView.show();
		var form = addView.down('form');
		form.getForm().findField('queryId').setValue(me.up('queryitemgridpanel').queryId);

	},
	showQueryItemGridContextMenu: function (view, record, item, index, e) {
		var me = this;
		e.preventDefault();
		var contextMenu = Ext.create('Ext.menu.Menu', {
			items: [{
				text: '编辑',
				handler: function () {
					me.showEditQueryItemWin(record);
				}
			}, {
					text: '删除',
					handler: function () {
						Ext.MessageBox.confirm('提示', '确定删除查询项?', function (btn) {
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
	showEditQueryItemWin: function (record) {
		var me = this;
		var editView = Ext.create("SystemConsole.query.QueryItemEditWindow", {
			title: '编辑查询项',
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
						url: context + '/query/item/update',
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

	},
	drop:function( node, data, overModel, dropPosition, eOpts ){
		Ext.MessageBox.wait('数据处理中...');
		var me  = this;
		var data=[];
		me.store.each(function(item,i){
			console.log(item);
			var record = {};
			record['id']=item.data.id;
			record['weight']=i;
			data.push(record);
		});
		
		Ext.Ajax.request({
			url : context + '/query/item/sort',
			params : Ext.encode(data),
			headers: {
                 'Content-Type': 'application/json'
            },
			method : 'POST',
			success : function(response) {
				var text = response.responseText;
				var json = Ext.JSON.decode(text);
				if (json.success) {
					Ext.MessageBox.hide();
				} else {
					Ext.MessageBox
							.alert('提示', '排序失败:' + json.message);
				}
			}
		});
	}
});
