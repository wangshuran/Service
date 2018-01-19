function fund_plan_list_render_iconUrl(value, metaData, record, rowIndex,
		colIndex, store, view) {

	return '<img src="' + value + '" width="100%" style="cursor:pointer;"/>';

}

function fund_plan_list_render_status(value, metaData, record, rowIndex,
		colIndex, store, view) {
	if (value == 'ENABLED') {
		return '<font color="green">'
				+ rendereredFromWordBook(value, "recordStatus") + '</font>';
	}
	if (value == 'DISABLED') {
		return '<font color="red">'
				+ rendereredFromWordBook(value, "recordStatus") + '</font>';
	}

	return value;
}

function fund_plan_list_render_term(value, metaData, record, rowIndex,
		colIndex, store, view) {
	return value+' '+rendereredFromWordBook(record.data.termUnit, "dateUnit");
}

function fund_plan_list_render_SP(value, metaData, record, rowIndex, colIndex,
		store, view) {

	var str = '<a href="javascript:void(0);" onclick="editFundPlan('
			+ rowIndex + ')">编辑</a>&nbsp;&nbsp;';
	str = str + '<a href="javascript:void(0);" onclick="delFundPlan('
			+ record.data.id + ')">删除</a>&nbsp;&nbsp;';

	if (record.data.status == 'ENABLED') {
		str = str
				+ '<a href="javascript:void(0);" onclick="updateFundPlanStatus('
				+ record.data.id + ',\'DISABLED\')">禁用</a>';
	}

	if (record.data.status == 'DISABLED') {
		str = str
				+ '<a href="javascript:void(0);" onclick="updateFundPlanStatus('
				+ record.data.id + ',\'ENABLED\')">启用</a>';
	}

	return str;

}

function fund_plan_list_initTbar(tbar, query) {
	if (tbar.length > 0) {
		tbar.push('-');
	}

	tbar.push({
		text : '新增',
		iconCls : 'add',
		handler : function(btn) {
			addFundPlan(btn, query);
		}
	});
}

function addFundPlan(btn, query) {

	var addView = Ext.create("App.fundplan.FundPlanEditWindow", {
		title : '新增计划',
		width : 600,
		height : 500,
		modal : true,
		renderTo : Ext.getBody(),
		buttons : [ {
			text : '保存',
			handler : function() {

				Ext.MessageBox.wait('数据保存中...');

				var win = this.up('window'), form = win.down('form'),grid=form.down('fundplanlanggrid');
				
				var params = {};
				
				grid.store.each(function(record,i){
					params['fundPlanLangs['+i+'].lang']=record.data.lang;
					params['fundPlanLangs['+i+'].title']=record.data.title;
					params['fundPlanLangs['+i+'].remark']=record.data.remark;
				});
				

				form.getForm().submit({
					clientValidation : true,
					url : context + '/investor/fundPlanAdd',
					params:params,
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
	});
	
	
	addView.show();
	

}

function editFundPlan(rowIndex) {
	var grid = Ext.getCmp('fund_plan_list_grid');
	var record = grid.getStore().getAt(rowIndex);
	var editView = Ext.create("App.fundplan.FundPlanEditWindow", {
		title : '编辑计划',
		width : 600,
		height : 500,
		modal : true,
		renderTo : Ext.getBody(),
		buttons : [ {
			text : '保存',
			handler : function() {

				Ext.MessageBox.wait('数据保存中...');

				var win = this.up('window'), form = win.down('form'),grid=form.down('fundplanlanggrid');
				
				var params = {};
				
				grid.store.each(function(record,i){
					params['fundPlanLangs['+i+'].lang']=record.data.lang;
					params['fundPlanLangs['+i+'].title']=record.data.title;
					params['fundPlanLangs['+i+'].remark']=record.data.remark;
				});
				

				form.getForm().submit({
					clientValidation : true,
					url : context + '/investor/fundPlanUpdate',
					params:params,
					success : function(form, action) {
						win.close();
						Ext.MessageBox.hide();
						Ext.getCmp('fund_plan_list_grid').getStore().load();
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
	
	var form = editView.down('form'),grid = form.down('fundplanlanggrid');
	// 加载数据
	form.loadRecord(record);
	
	grid.store.on('beforeload', function(thiz, options) {
		Ext.apply(grid.store.proxy.extraParams, {
			fundPlanId : record.data.id
		});
	});
	
	grid.store.load();
	

}

function delFundPlan(id) {
	Ext.MessageBox.confirm('提示', '确定删除计划?', function(btn) {
		if (btn == 'yes') {
			Ext.MessageBox.wait('数据删除中...');
			Ext.Ajax.request({
				url : context + '/investor/fundPlanDel?id=' + id,
				method : 'POST',
				success : function(response) {
					var text = response.responseText;
					var json = Ext.JSON.decode(text);
					if (json.success) {
						Ext.MessageBox.alert('提示', '删除成功');
						Ext.getCmp('fund_plan_list_grid').getStore().load();
					} else {
						Ext.MessageBox.alert('提示', '删除失败:' + json.message);
					}
				}
			});
		}
	});
}

function updateFundPlanStatus(id, status) {
	var title = '';
	if (status == 'ENABLED') {
		title = '启用'
	} else if (status == 'DISABLED') {
		title = '禁用'
	}
	Ext.MessageBox.confirm('提示', '确定' + title + '资金计划?', function(btn) {
		if (btn == 'yes') {
			Ext.MessageBox.wait('数据处理中...');
			Ext.Ajax.request({
				url : context + '/investor/fundPlanUpdateStatus',
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
						Ext.getCmp('fund_plan_list_grid').getStore().load();
					} else {
						Ext.MessageBox
								.alert('提示', title + '失败:' + json.message);
					}
				}
			});
		}
	});
}