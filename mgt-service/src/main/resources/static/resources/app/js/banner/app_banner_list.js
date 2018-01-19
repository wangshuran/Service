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
function app_banner_list_render_imgUrl(value, metaData, record, rowIndex,
		colIndex, store, view) {

	return '<img src="' + value + '" width="100%" style="cursor:pointer;" onclick="javascript:showAppBannerImg(\''+value+'\')"/>';

}

function app_banner_list_render_status(value, metaData, record, rowIndex,
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

function app_banner_list_render_lang(value, metaData, record, rowIndex,
		colIndex, store, view) {

	return rendereredFromWordBook(value, "lang");
}

function app_banner_list_render_SP(value, metaData, record, rowIndex, colIndex,
		store, view) {

	var str = '<a href="javascript:void(0);" onclick="editAppBanner('
			+ rowIndex + ')">编辑</a>&nbsp;&nbsp;';
	str = str + '<a href="javascript:void(0);" onclick="delAppBanner('
			+ record.data.id + ')">删除</a>&nbsp;&nbsp;';

	if (record.data.status == 'ENABLED') {
		str = str
				+ '<a href="javascript:void(0);" onclick="updateAppBannerStatus('
				+ record.data.id + ',\'DISABLED\')">禁用</a>';
	}

	if (record.data.status == 'DISABLED') {
		str = str
				+ '<a href="javascript:void(0);" onclick="updateAppBannerStatus('
				+ record.data.id + ',\'ENABLED\')">启用</a>';
	}

	return str;

}

function showAppBannerImg(url){
	Ext.create('Ext.window.Window', {
	    title: '查看图片',
	    height: 400,
	    width: 400,
	    autoScroll:true,
	    html:'<img src="' + url + '" width="100%"/>'
	}).show();
}

function editAppBanner(rowIndex) {
	var grid = Ext.getCmp('app_banner_list_grid');
	var record = grid.getStore().getAt(rowIndex);
	var editView = Ext.create("App.banner.AppBannerEditWindow", {
		title : '编辑欢迎页',
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
					url : context + '/investor/appBannerUpdate',
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

function updateAppBannerStatus(id, status) {
	var title = '';
	if (status == 'ENABLED') {
		title = '启用'
	} else if (status == 'DISABLED') {
		title = '禁用'
	}
	Ext.MessageBox.confirm('提示', '确定' + title + '欢迎页?', function(btn) {
		if (btn == 'yes') {
			Ext.MessageBox.wait('数据处理中...');
			Ext.Ajax.request({
				url : context + '/investor/appBannerUpdateStatus',
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
						Ext.getCmp('app_banner_list_grid').getStore().load();
					} else {
						Ext.MessageBox
								.alert('提示', title + '失败:' + json.message);
					}
				}
			});
		}
	});
}

function delAppBanner(id) {
	Ext.MessageBox.confirm('提示', '确定删除欢迎页?', function(btn) {
		if (btn == 'yes') {
			Ext.MessageBox.wait('数据删除中...');
			Ext.Ajax.request({
				url : context + '/investor/appBannerDel?id=' + id,
				method : 'POST',
				success : function(response) {
					var text = response.responseText;
					var json = Ext.JSON.decode(text);
					if (json.success) {
						Ext.MessageBox.alert('提示', '删除成功');
						Ext.getCmp('app_banner_list_grid').getStore().load();
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
function app_banner_list_initTbar(tbar, query) {
	if (tbar.length > 0) {
		tbar.push('-');
	}

	tbar.push({
		text : '新增',
		iconCls : 'add',
		handler : function(btn) {
			addAppBanner(btn, query);
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
function addAppBanner(btn, query) {

	Ext.create("App.banner.AppBannerEditWindow", {
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
					url : context + '/investor/appBannerAdd',
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