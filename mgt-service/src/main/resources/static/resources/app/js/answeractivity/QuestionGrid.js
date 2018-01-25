/**
 * 问题列表
 */

Ext.define('App.answeractivity.QuestionGrid', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.aaquestiongrid',
	aaId:null,
	initComponent : function() {
		var me = this;

		Ext.define('aaQuestionModel', {
			extend : 'Ext.data.Model',
			fields : [ 'id', 'aaId', 'number', 'question', 'answer' ]
		});

		this.store = Ext.create('Ext.data.Store', {
			autoDestroy : true,
			model : 'aaQuestionModel',
			autoLoad : false,
			proxy : {
				type : 'ajax',
				actionMethods : 'POST',
				batchActions : false,
				api : {
					read : context + '/investor/aaQuestionList'
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
					text : '题号',
					flex : 1,
					dataIndex : 'number'
				}, {
					header : '题目',
					flex : 3,
					dataIndex : 'question'
				}, {
					text : '答案',
					flex : 1,
					dataIndex : 'answer'
				} ];
		this.tbar = [ {
			text : '新增',
			iconCls : 'add',
			handler : me.showAddQuestionWin
		} ];
		this.listeners = {
			itemcontextmenu : this.showQuestionGridContextMenu
		};
		this.callParent(arguments);
	},
	showAddQuestionWin : function(btn) {
		
		Ext.create("App.answeractivity.QuestionEditWindow", {
			title : '新增题目',
			width : 700,
			height : 500,
			modal : true,
			renderTo : Ext.getBody(),
			buttons : [ {
				text : '保存',
				handler : function() {

					Ext.MessageBox.wait('数据保存中...');

					var win = this.up('window'), form = win.down('form'),grid=form.down('aaoptiongrid');
					
					var params = {
							aaId:btn.up('aaquestiongrid').aaId
					};
					
					grid.store.each(function(record,i){
						params['options['+i+'].optionCode']=record.data.optionCode;
						params['options['+i+'].optionContent']=record.data.optionContent;
					});
					

					form.getForm().submit({
						clientValidation : true,
						url : context + '/investor/aaQuestionAdd',
						params:params,
						success : function(form, action) {
							win.close();
							Ext.MessageBox.hide();
							btn.up('aaquestiongrid').getStore().load();
						},
						failure : function(form, action) {
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
				}
			}, {
				text : '关闭',
				handler : function() {
					this.up('window').close();
				}
			} ]
		}).show();

	},
	showQuestionGridContextMenu : function(view, record, item, index, e) {
		var me = this;
		e.preventDefault();
		
		var contextMenu = Ext.create('Ext.menu.Menu', {
			items : [ {
				text : '编辑',
				handler : function() {
					me.showEditQuestionWin(record);
				}
			}, {
				text : '删除',
				handler : function() {
					Ext.MessageBox.confirm('提示', '确定删除问题?', function(btn) {
						if (btn == 'yes') {
							Ext.MessageBox.wait('数据删除中...');
							Ext.Ajax.request({
								url : context + '/investor/aaQuestionDel?id=' + record.data.id,
								method : 'POST',
								success : function(response) {
									var text = response.responseText;
									var json = Ext.JSON.decode(text);
									if (json.success) {
										Ext.MessageBox.alert('提示', '删除成功');
										me.getStore()
												.load();
									} else {
										Ext.MessageBox.alert('提示', '删除失败:' + json.message);
									}
								}
							});
						}
					});
				}
			} ]
		});

		contextMenu.showAt(e.getXY());
	},
	showEditQuestionWin : function(record) {
		var me = this;
		var editView = Ext.create("App.answeractivity.QuestionEditWindow", {
			title : '编辑题目',
			width : 700,
			height : 500,
			modal : true,
			renderTo : Ext.getBody(),
			buttons : [ {
				text : '保存',
				handler : function() {

					Ext.MessageBox.wait('数据保存中...');

					var win = this.up('window'), form = win.down('form'),grid=form.down('aaoptiongrid');
					
					var params = {
							aaId:me.aaId
					};
					
					grid.store.each(function(record,i){
						params['options['+i+'].optionCode']=record.data.optionCode;
						params['options['+i+'].optionContent']=record.data.optionContent;
					});
					

					form.getForm().submit({
						clientValidation : true,
						url : context + '/investor/aaQuestionUpdate',
						params:params,
						success : function(form, action) {
							win.close();
							Ext.MessageBox.hide();
							me.getStore().load();
						},
						failure : function(form, action) {
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
				}
			}, {
				text : '关闭',
				handler : function() {
					this.up('window').close();
				}
			} ]
		});
		editView.show();
		var form = editView.down('form'),grid=form.down('aaoptiongrid');
		// 加载数据
		form.loadRecord(record);
		
		grid.store.on('beforeload', function(thiz, options) {
			Ext.apply(grid.store.proxy.extraParams, {
				questionId : record.data.id
			});
		});
		
		grid.store.load();

	}
});
