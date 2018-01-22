function investor_list_render_createTime(value, metaData, record, rowIndex,
		colIndex, store, view) {

	return formatTime(value);

}


function investor_list_render_status(value, metaData, record, rowIndex,
		colIndex, store, view) {
	var valueShow = rendereredFromWordBook(value, "investorStatus");
	if (value == 'NORMAL') {
		return '<font color="green">' + valueShow + '</font>';
	}
	if (value == 'OFF') {
		return '<font color="red">' + valueShow + '</font>';
	}
	if (value == 'LOCK') {
		return '<font color="gray">' + valueShow + '</font>';
	}

	return value;
}

function investor_list_render_googleAuthStatus(value, metaData, record,
		rowIndex, colIndex, store, view) {
	return rendereredFromWordBook(value, "googleAuthStatus");
}

function investor_list_render_SP(value, metaData, record, rowIndex, colIndex,
		store, view) {

	var str = '';

	if (record.data.status == 'NORMAL') {
		str = str
				+ '<a href="javascript:void(0);" onclick="updateInvestorStatus('
				+ record.data.id + ',\'LOCK\')">锁定</a>&nbsp;&nbsp;';

		str = str
				+ '<a href="javascript:void(0);" onclick="updateInvestorStatus('
				+ record.data.id + ',\'OFF\')">注销</a>';
	}

	if (record.data.status == 'LOCK') {
		str = str
				+ '<a href="javascript:void(0);" onclick="updateInvestorStatus('
				+ record.data.id + ',\'NORMAL\')">解锁</a>';
	}

	return str;

}

function updateInvestorStatus(id,status){
	var title='';
	if(status=='NORMAL'){
		title='解锁';
	}else if(status=='LOCK'){
		title='锁定';
	}else if(status=='OFF'){
		title='注销';
	}
	
	Ext.MessageBox.confirm('提示', '确定' + title + '投资人?', function(btn) {
		if (btn == 'yes') {
			Ext.MessageBox.wait('数据处理中...');
			Ext.Ajax.request({
				url : context + '/investor/updateStatus',
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
						Ext.getCmp('investor_list_grid').getStore().load();
					} else {
						Ext.MessageBox
								.alert('提示', title + '失败:' + json.message);
					}
				}
			});
		}
	});
}

function investor_list_initTbar(tbar, query) {}
