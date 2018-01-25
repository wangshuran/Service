/**
 * 用户编辑窗口
 */
Ext
		.define(
				'SystemConsole.user.UserEditWindow',
				{
					extend : 'Ext.window.Window',
					alias : 'widget.usereditwindow',
					layout : 'fit',
					initComponent : function() {
						var me = this;
						this.items = [ {
							xtype : 'form',
							defaults : {

								frame : false,
								border : false
							},
							layout : 'column',
							bodyStyle : 'padding:10px',
							items : [
									{
										columnWidth : .5,
										layout : 'anchor',
										items : [ {
											xtype : 'hidden',
											name : 'userId'
										}, {
											xtype : 'hidden',
											name : 'orgIdPath'
										}, {
											xtype : 'hidden',
											name : 'orgNamePath'
										}, {
											xtype : 'textfield',
											name : 'userCode',
											anchor : '95%',
											fieldLabel : '用户编号'
										} ]
									},
									{
										columnWidth : .5,
										layout : 'anchor',
										items : [ {
											xtype : 'textfield',
											name : 'userName',
											anchor : '95%',
											fieldLabel : '用户名称'
										} ]
									},
									{
										columnWidth : .5,
										layout : 'anchor',
										items : [ Ext
												.create(
														'SystemConsole.component.WordBookComboBox',
														{
															fieldLabel : '用户状态',
															name : 'status',
															anchor : '95%',
															wordBook : 'userState'
														}) ]
									},
									{
										columnWidth : .5,
										layout : 'anchor',
										items : [ {
											xtype : 'textfield',
											name : 'loginName',
											anchor : '95%',
											fieldLabel : '登录账号',
											allowBlank : false,
											listeners : {
												blur : me.checkLoginName
											}
										} ]
									},
									{
										columnWidth : .5,
										layout : 'anchor',
										items : [ {
											xtype : 'textfield',
											name : 'password',
											itemId : 'loginPassword',
											fieldLabel : '登录密码',
											anchor : '95%',
											inputType : 'password'
										} ]
									},
									{
										columnWidth : .5,
										layout : 'anchor',
										items : [ {
											xtype : 'textfield',
											name : 'loginPasswordConfirm',
											fieldLabel : '密码确认',
											inputType : 'password',
											vtype : 'password',
											anchor : '95%',
											initialPassField : 'loginPassword'
										} ]
									},
									{
										columnWidth : .5,
										layout : 'anchor',
										items : [ Ext
												.create(
														'SystemConsole.component.WordBookComboBox',
														{
															fieldLabel : '性别',
															name : 'sex',
															anchor : '95%',
															wordBook : 'sex'
														}) ]
									},
									{
										columnWidth : .5,
										layout : 'anchor',
										items : [ Ext
												.create(
														'SystemConsole.component.OrgComboBox',
														{
															fieldLabel : '所属机构',
															name : 'orgId',
															displayField : 'text',
															rootId : '1',
															anchor : '95%',
															listeners : {
																'select' : function(
																		record,
																		node) {
																	var temp = node;
																	var orgIdPath = '';
																	var orgNamePath = '';
																	while (true) {
																		orgIdPath = temp
																				.get('id')
																				+ '/'
																				+ orgIdPath;
																		orgNamePath = temp
																				.get('text')
																				+ '/'
																				+ orgNamePath;
																		if (temp
																				.get('id') == record.rootId)
																			break;
																		temp = temp.parentNode;
																	}
																	this
																			.up(
																					'form')
																			.getForm()
																			.findField(
																					'orgIdPath')
																			.setValue(
																					orgIdPath);
																	this
																			.up(
																					'form')
																			.getForm()
																			.findField(
																					'orgNamePath')
																			.setValue(
																					orgNamePath);
																}
															}
														}) ]
									},
									{
										columnWidth : .5,
										layout : 'anchor',
										items : [ {
											xtype : 'datefield',
											name : 'entryDate',
											anchor : '95%',
											format : 'Y-m-d',
											fieldLabel : '入职日期'
										} ]
									},
									{
										columnWidth : .5,
										layout : 'anchor',
										items : [ Ext
												.create(
														'SystemConsole.component.WordBookComboBox',
														{
															fieldLabel : '职位级别',
															name : 'level',
															anchor : '95%',
															wordBook : 'level'
														}) ]
									} ]
						} ];
						this.callParent(arguments);
					},
					checkLoginName : function(thiz, eopts) {
						Ext.Ajax.request({
							url : context + '/sysuser/checkLoginName',
							method : 'POST',
							params : {
								loginName : thiz.getValue()
							},
							success : function(response, options) {
								var o = Ext.JSON.decode(response.responseText);
								if (o.success) {
									if (o.isExits) {
										Ext.Msg.alert('提示', '登录名已存在！');
										thiz.setValue('');
									}
								}
							}
						});
					}
				})