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

	Ext.create("App.fundplan.FundPlanEditWindow", {
		title : '新增计划',
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
					url : context + '/investor/fundPlanAdd',
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
							Ext.Msg.alert('失败', action.result.msg);
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