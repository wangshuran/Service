/**
 * 后台系统应用
 */
Ext.Loader.setConfig({
	enabled : true,
	paths : {
		'App':context+'/resources/app/js',
		'SystemConsole' : context+'/resources/console/js'
	}
});
Ext.application({
	name : 'BMS',
	requires : [ 'Ext.container.Viewport' ],
	launch : function() {
		Ext.create('Ext.container.Viewport', {
			layout : {
				type : 'border'
			},
			items : [ {
				xtype : 'panel',
				border : false,
				layout : 'fit',
				height : 65,
				contentEl : 'header',
				region : 'north'
			}, createMenuPanel(), createMainTabPanel() ]
		});

		Ext.get('loading').remove();
		Ext.get('loading-mask').fadeOut({
			remove : true
		});

		Ext.Ajax.on('requestcomplete', checkUserSessionStatus, this);
		
		// 创建websocket
//		connnectWebsocket();

	}
});
// 表单验证
Ext.require('SystemConsole.component.FormFieldVtype');
// 表单构建
Ext.require('SystemConsole.component.FormFieldCmp');
/**
 * 创建websocket连接
 */
function connnectWebsocket() {
	var socket = new SockJS('/bms-websocket');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		console.log('Connected: ' + frame);
		// 订阅通知
		stompClient.subscribe('/app/notify', function(greeting) {
			console.log(JSON.parse(greeting.body).content);
		});
		// 全局通知
		stompClient.subscribe('/topic/notify', function(greeting) {
			console.log(JSON.parse(greeting.body).content);
		});
		// 个人通知
		stompClient.subscribe('/user/notify', function(greeting) {
			console.log(JSON.parse(greeting.body).content);
		});
	});

}

function showToastWin(content) {
	console.log('this is my house!!');
}

/**
 * 创建菜单面板
 * 
 * @return {}
 */
function createMenuPanel() {

	var menuPanel = Ext.create('SystemConsole.maindesktop.MenuPanel', {
		region : 'west',
		collapsible : true,
		width : 225,
		split : true,
		minWidth : 175,
		title : '系统导航'
	});
	return menuPanel;

}

function showChangePwdWin() {
	Ext.create('SystemConsole.user.ChangePwdWindow', {
		title : '修改密码',
		width : 400,
		height : 200,
		modal : true,
		buttons : [ {
			text : '确定',
			handler : function() {

				Ext.MessageBox.wait('数据提交中...');

				var win = this.up('changepwdwindow'), form = win.down('form');

				form.getForm().submit({
					clientValidation : true,
					url : context + '/sysuser/changepwd',
					success : function(form, action) {
						Ext.Msg.alert('提示', '密码修改成功');
						win.close();
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
			text : '取消',
			handler : function() {
				this.up('changepwdwindow').close();
			}
		} ]
	}).show();
}
/**
 * 退出登录
 */
function logout() {
	window.location.href = context + '/console/logout';
}
/**
 * 主显示面板
 * 
 * @return {}
 */
function createMainTabPanel() {
	var mainTabPanel = Ext.create('SystemConsole.maindesktop.MainTabPanel', {
		region : 'center'
	});

	var html = mainTabPanel.calendarHtml();
	var panel = Ext.create('Ext.panel.Panel', {
		title : '我的桌面',
		html : ''
	});
	var tab = mainTabPanel.add(panel);
	mainTabPanel.setActiveTab(tab);
	return mainTabPanel;
}

function checkUserSessionStatus(conn, response, options) {
	// Ext重新封装了response对象
	try {
		if (response.getResponseHeader('sessionstatus')) {
			invalidSession();
			return false;
		}
	} catch (e) {
	}
}

function invalidSession() {
	var win = Ext.create('SystemConsole.user.UserLoginWindow', {
		title : '重新登录',
		modal : true,
		width : 300,
		height : 150,
		buttons : [ {
			text : '登录',
			handler : function() {


				Ext.MessageBox.wait('数据提交中...');

				var win = this.up('userloginwindow'), form = win.down('form');

				form.getForm().submit({
					clientValidation : true,
					url : context + '/sysuser/login',
					success : function(form, action) {
						Ext.Msg.alert('提示', '登录成功');
						win.close();
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
			text : '取消',
			handler : function() {
				win.close();
				win = null;
			}
		} ]
	});
	win.show();
}