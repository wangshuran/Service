/**
 * 列表渲染
 * 
 * @param value
 * @param metaData
 * @param record
 * @param rowIndex
 * @param colIndex
 * @param store
 * @param view
 * @returns
 */
function answer_activity_list_render_startTime(value, metaData, record,
		rowIndex, colIndex, store, view) {

	return formatTime(value);

}

function answer_activity_list_render_status(value, metaData, record, rowIndex,
		colIndex, store, view) {
	if (value == 'ENABLED') {
		return '<font color="green">'
				+ rendereredFromWordBook(value, "bannerStatus") + '</font>';
	}
	if (value == 'DISABLED') {
		return '<font color="red">'
				+ rendereredFromWordBook(value, "bannerStatus") + '</font>';
	}

	return value;
}

function answer_activity_list_render_SP(value, metaData, record, rowIndex,
		colIndex, store, view) {

	var str = '<a href="javascript:void(0);" onclick="editAnswerActivity('
			+ rowIndex + ')">编辑</a>&nbsp;&nbsp;';
	str = str + '<a href="javascript:void(0);" onclick="delAnswerActivity('
			+ record.data.id + ')">删除</a>&nbsp;&nbsp;';

	if (record.data.status == 'ENABLED') {
		str = str
				+ '<a href="javascript:void(0);" onclick="updateAnswerActivityStatus('
				+ record.data.id + ',\'DISABLED\')">禁用</a>';
	}

	if (record.data.status == 'DISABLED') {
		str = str
				+ '<a href="javascript:void(0);" onclick="updateAnswerActivityStatus('
				+ record.data.id + ',\'ENABLED\')">启用</a>';
	}

	str = str
			+ '&nbsp;&nbsp;<a href="javascript:void(0);" onclick="showQuestionList('
			+ record.data.id + ')">题目管理</a>';

	return str;

}

function showQuestionList(id) {
	var grid = Ext.create('App.answeractivity.QuestionGrid', {

	})

	var win = Ext.create('Ext.window.Window', {
		title : '活动题库',
		width : 700,
		height : 500,
		layout : 'fit',
		modal : true,
		items : [ grid ],
		button : [ {
			text : '关闭',
			handler : function() {
				win.close();
				win = null;
			}
		} ]
	});
	
	win.show();
}

function editAnswerActivity(rowIndex) {
	var grid = Ext.getCmp('answer_activity_list_grid');
	var record = grid.getStore().getAt(rowIndex);
	var editView = Ext.create("App.answeractivity.AnswerActivityEditWindow", {
		title : '编辑答题活动',
		width : 450,
		height : 350,
		modal : true,
		renderTo : Ext.getBody(),
		buttons : [ {
			text : '保存',
			handler : function() {

				Ext.MessageBox.wait('数据保存中...');

				var win = this.up('window'), form = win.down('form');

				form.getForm().submit({
					clientValidation : true,
					url : context + '/investor/answerActivityUpdate',
					success : function(form, action) {
						win.close();
						Ext.MessageBox.hide();
						grid.getStore().load();
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

	var form = editView.down('form');
	// 加载数据
	form.loadRecord(record);
	form.getForm().findField('startTime_show').setValue(
			formatTime(record.data.startTime));
}

function updateAnswerActivityStatus(id, status) {
	var title = '';
	if (status == 'ENABLED') {
		title = '启用'
	} else if (status == 'DISABLED') {
		title = '禁用'
	}
	Ext.MessageBox.confirm('提示', '确定' + title + '答题活动?', function(btn) {
		if (btn == 'yes') {
			Ext.MessageBox.wait('数据处理中...');
			Ext.Ajax.request({
				url : context + '/investor/answerActivityUpdateStatus',
				params : {
					id : id,
					status : status
				},
				method : 'POST',
				success : function(response) {
					var text = response.responseText;
					var json = Ext.JSON.decode(text);
					if (json.success) {
						Ext.MessageBox.alert('提示', title + '成功');
						Ext.getCmp('answer_activity_list_grid').getStore()
								.load();
					} else {
						Ext.MessageBox
								.alert('提示', title + '失败:' + json.message);
					}
				}
			});
		}
	});
}

function delAnswerActivity(id) {
	Ext.MessageBox.confirm('提示', '确定删除答题活动?', function(btn) {
		if (btn == 'yes') {
			Ext.MessageBox.wait('数据删除中...');
			Ext.Ajax.request({
				url : context + '/investor/answerActivityDel?id=' + id,
				method : 'POST',
				success : function(response) {
					var text = response.responseText;
					var json = Ext.JSON.decode(text);
					if (json.success) {
						Ext.MessageBox.alert('提示', '删除成功');
						Ext.getCmp('answer_activity_list_grid').getStore()
								.load();
					} else {
						Ext.MessageBox.alert('提示', '删除失败:' + json.message);
					}
				}
			});
		}
	});
}
/**
 * 初始化列表操作按钮
 * 
 * @param tbar
 * @param query
 * @returns
 */
function answer_activity_list_initTbar(tbar, query) {
	if (tbar.length > 0) {
		tbar.push('-');
	}

	tbar.push({
		text : '新增',
		iconCls : 'add',
		handler : function(btn) {
			addAnswerActivity(btn, query);
		}
	});
}
/**
 * 增加app banner
 * 
 * @param btn
 * @param query
 * @returns
 */
function addAnswerActivity(btn, query) {

	Ext.create("App.answeractivity.AnswerActivityEditWindow", {
		title : '新增答题活动',
		width : 450,
		height : 350,
		modal : true,
		renderTo : Ext.getBody(),
		buttons : [ {
			text : '保存',
			handler : function() {

				Ext.MessageBox.wait('数据保存中...');

				var win = this.up('window'), form = win.down('form');

				form.getForm().submit({
					clientValidation : true,
					url : context + '/investor/answerActivityAdd',
					success : function(form, action) {
						win.close();
						Ext.MessageBox.hide();
						Ext.getCmp(query.queryKey + '_grid').getStore().load();
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

}