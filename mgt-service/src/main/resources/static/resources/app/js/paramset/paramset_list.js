
function paramset_list_render_SP(value, metaData, record, rowIndex, colIndex,
		store, view) {

	var str = '<a href="javascript:void(0);" onclick="editParamSet('
			+ rowIndex + ')">编辑</a>&nbsp;&nbsp;';
	str = str + '<a href="javascript:void(0);" onclick="delParamSet(\''
			+ record.data.paramKey + '\')">删除</a>&nbsp;&nbsp;';

	return str;

}

function editParamSet(rowIndex) {
	var grid = Ext.getCmp('paramset_list_grid');
	var record = grid.getStore().getAt(rowIndex);
	var editView = Ext.create("App.paramset.ParamSetEditWindow", {
		title : '编辑参数',
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
					url : context + '/investor/paramSetUpdate',
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
}


function delParamSet(id) {
	Ext.MessageBox.confirm('提示', '确定删除参数?', function(btn) {
		if (btn == 'yes') {
			Ext.MessageBox.wait('数据删除中...');
			Ext.Ajax.request({
				url : context + '/investor/paramSetDel?paramKey=' + id,
				method : 'POST',
				success : function(response) {
					var text = response.responseText;
					var json = Ext.JSON.decode(text);
					if (json.success) {
						Ext.MessageBox.alert('提示', '删除成功');
						Ext.getCmp('paramset_list_grid').getStore().load();
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
function paramset_list_initTbar(tbar, query) {
	if (tbar.length > 0) {
		tbar.push('-');
	}

	tbar.push({
		text : '新增',
		iconCls : 'add',
		handler : function(btn) {
			addParamSet(btn, query);
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
function addParamSet(btn, query) {

	Ext.create("App.paramset.ParamSetEditWindow", {
		title : '新增欢迎页',
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
					url : context + '/investor/paramSetAdd',
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