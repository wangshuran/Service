function transfer_list_render_createTime(value, metaData, record, rowIndex,
		colIndex, store, view) {

	return formatTime(value);
}

function transfer_list_render_address(value, metaData, record, rowIndex,
		colIndex, store, view) {

	return '<div title="' + value + '">' + value + '</div>';
}

function transfer_list_render_status(value, metaData, record, rowIndex,
		colIndex, store, view) {
	if (value == 'success') {
		return '<font color="green">'
				+ rendereredFromWordBook(value, "withdrawStatus") + '</font>';
	}
	if (value == 'audit') {
		return '<font color="red">'
				+ rendereredFromWordBook(value, "withdrawStatus") + '</font>';
	}
	if (value == 'fail') {
		return '<font color="gray">'
				+ rendereredFromWordBook(value, "withdrawStatus") + '</font>';
	}

	return value;
}

function transfer_list_render_SP(value, metaData, record, rowIndex, colIndex,
		store, view) {
	var str = '';

	if (record.data.status == 'audit') {
		str = str
				+ '<a href="javascript:void(0);" onclick="showAuditWindow('
				+ record.data.id + ')">审批</a>';
	}

	return str;

}


function showAuditWindow(id){
	Ext.create("App.transfer.AuditWindow", {
		title : '审批',
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
					params:{id:id},
					url : context + '/investor/transferAudit',
					success : function(form, action) {
						win.close();
						Ext.MessageBox.hide();
						Ext.getCmp('transfer_list_grid').getStore().load();
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