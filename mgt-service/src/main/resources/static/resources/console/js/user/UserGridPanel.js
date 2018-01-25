/**
 * 用户管理
 */

Ext
		.define(
				'SystemConsole.user.UserGridPanel',
				{
					extend : 'Ext.grid.Panel',
					alias : 'widget.usergridpanel',
					initComponent : function() {
						var me = this;
						this.store = Ext.create(
								'SystemConsole.user.UserGridStore', {
									pageSize : 50
								});
						this.store.load();
						this.columns = [
								{
									text : '用户编号',
									flex : 1,
									dataIndex : 'userCode'
								},
								{
									header : '用户姓名',
									flex : 1,
									dataIndex : 'userName'
								},
								{
									text : '登录名',
									flex : 1,
									dataIndex : 'loginName'
								},
								{
									text : '所属机构',
									flex : 2,
									dataIndex : 'orgNamePath'
								},
								{
									text : '用户状态',
									flex : 1,
									dataIndex : 'status',
									renderer : function(v) {
										var wbu = Ext
												.create(
														'SystemConsole.wordbook.WordBookUtil',
														'userState');
										return wbu.getWordBookName(v);
									}
								} ];
						this.tbar = [
								{
									text : '增加用户',
									iconCls : 'useradd',
									handler : me.showAddUserWin
								},
								{
									itemId : 'removeUserBtn',
									text : '删除用户',
									iconCls : 'userdel',
									disabled : true,
									handler : function() {
										Ext.MessageBox
												.confirm(
														'提示',
														'确定删除用户?',
														function(btn) {
															if (btn == 'yes') {
																var sm = me
																		.getSelectionModel();
																me.store
																		.remove(sm
																				.getSelection()[0]);
																me.store.sync();
															}
														});

									}
								} ];
						this.bbar = Ext.create('Ext.PagingToolbar', {
							store : me.store,
							displayInfo : true,
							displayMsg : '当前显示  第{0} - {1} of {2}',
							emptyMsg : "没有数据"
						});
						this.listeners = {
							selectionchange : this.userGridSelectionChange,
							itemcontextmenu : this.showUserGridContextMenu
						};
						this.callParent(arguments);
					},
					showAddUserWin : function(btn) {
						Ext
								.create(
										"SystemConsole.user.UserEditWindow",
										{
											title : '新增用户',
											width : 700,
											height : 320,
											modal : true,
											renderTo : Ext.getBody(),
											buttons : [
													{
														text : '保存',
														handler : function() {

															Ext.MessageBox
																	.wait('数据保存中...');

															var win = this
																	.up('window'), form = win
																	.down('form');

															form
																	.getForm()
																	.submit(
																			{
																				clientValidation : true,
																				url : context
																						+ '/sysuser/add',
																				success : function(
																						form,
																						action) {
																					win
																							.close();
																					Ext.MessageBox
																							.hide();
																					btn
																							.up(
																									'usergridpanel')
																							.getStore()
																							.load();
																				},
																				failure : function(
																						form,
																						action) {
																					switch (action.failureType) {
																					case Ext.form.action.Action.CLIENT_INVALID:
																						Ext.Msg
																								.alert(
																										'提示',
																										'请正确填写页面信息');
																						break;
																					case Ext.form.action.Action.CONNECT_FAILURE:
																						Ext.Msg
																								.alert(
																										'失败',
																										'服务器连接超时');
																						break;
																					case Ext.form.action.Action.SERVER_INVALID:
																						Ext.Msg
																								.alert(
																										'失败',
																										action.result.message);
																					}
																				}
																			});

														}
													},
													{
														text : '关闭',
														handler : function() {
															this.up('window')
																	.close();
														}
													} ]
										}).show();

					},
					userGridSelectionChange : function(sm, records) {
						this.down('#removeUserBtn')
								.setDisabled(!records.length);
					},
					showUserGridContextMenu : function(view, record, item,
							index, e) {
						var me = this;
						e.preventDefault();
						if (record.data.loginName == 'admin') {
							Ext.MessageBox.alert('提示', '超级管理用户不可编辑！');
							return;
						}
						var contextMenu = Ext
								.create(
										'Ext.menu.Menu',
										{
											items : [
													{
														text : '编辑',
														handler : function() {
															me
																	.showEditUserWin(record);
														}
													},
													{
														text : '重置密码',
														handler : function() {
															me
																	.resetPassword(record);
														}
													},
													{
														text : '删除',
														handler : function() {
															Ext.MessageBox
																	.confirm(
																			'提示',
																			'确定删除用户?',
																			function(
																					btn) {
																				if (btn == 'yes') {
																					me.store
																							.remove(record);
																					me.store
																							.sync();
																				}
																			});
														}
													} ]
										});

						contextMenu.showAt(e.getXY());
					},
					resetPassword : function(record) {
						Ext.MessageBox
								.confirm(
										'提示',
										'确定重置用户 密码?',
										function(btn) {
											if (btn == 'yes') {
												Ext.Ajax
														.request({
															url : context
																	+ '/sysuser/resetpwd',
															method : 'POST',
															params : {
																loginName : record.data.loginName
															},
															success : function(
																	response,
																	options) {
																var o = Ext.JSON
																		.decode(response.responseText);
																if (o.success) {
																	Ext.Msg
																			.alert(
																					'提示',
																					'密码重置成功！');
																} else {
																	Ext.Msg
																			.alert(
																					'提示',
																					'重置密码失败！');
																}
															}
														});
											}
										});
					},
					showEditUserWin : function(record) {
						var me = this;
						var editView = Ext
								.create(
										"SystemConsole.user.UserEditWindow",
										{
											title : '编辑用户',
											width : 700,
											height : 320,
											modal : true,
											renderTo : Ext.getBody(),
											buttons : [
													{
														text : '保存',
														handler : function() {

															Ext.MessageBox
																	.wait('数据保存中...');

															var win = this
																	.up('window'), form = win
																	.down('form');

															form
																	.getForm()
																	.submit(
																			{
																				clientValidation : true,
																				url : context
																						+ '/sysuser/update',
																				success : function(
																						form,
																						action) {
																					win
																							.close();
																					Ext.MessageBox
																							.hide();
																					me
																							.getStore()
																							.load();
																				},
																				failure : function(
																						form,
																						action) {
																					switch (action.failureType) {
																					case Ext.form.action.Action.CLIENT_INVALID:
																						Ext.Msg
																								.alert(
																										'提示',
																										'请正确填写页面信息');
																						break;
																					case Ext.form.action.Action.CONNECT_FAILURE:
																						Ext.Msg
																								.alert(
																										'失败',
																										'服务器连接超时');
																						break;
																					case Ext.form.action.Action.SERVER_INVALID:
																						Ext.Msg
																								.alert(
																										'失败',
																										action.result.message);
																					}
																				}
																			});

														}
													},
													{
														text : '关闭',
														handler : function() {
															this.up('window')
																	.close();
														}
													} ]
										});
						editView.show();
						var form = editView.down('form');
						form.getForm().findField('password').hide();
						form.getForm().findField('loginPasswordConfirm').hide();
						// 加载数据
						form.loadRecord(record);

					}
				});
