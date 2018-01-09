/**
 * 用户查询面板
 */

Ext.define('SystemConsole.user.UserQueryPanel', {
			extend : 'Ext.form.Panel',
			layout : 'column',
			columnCount : 1,
			border : false,
			initComponent : function() {
				this.items = [{
							columnWidth : 1 / this.columnCount,
							layout : 'form',
							border : false,
							frame : false,
							items : [{
										xtype : 'textfield',
										name : 'userCode',
										fieldLabel : '用户编号',
										labelAlign:'right',
										labelWidth : 70
									}]
						}, {
							columnWidth : 1 / this.columnCount,
							layout : 'form',
							border : false,
							frame : false,
							items : [{
										xtype : 'textfield',
										name : 'userName',
										fieldLabel : '用户姓名',
										labelAlign:'right',
										anchor:'90%',
										labelWidth : 70
									}]
						}, {
							columnWidth : 1 / this.columnCount,
							layout : 'form',
							border : false,
							frame : false,
							items : [{
										xtype : 'textfield',
										name : 'loginName',
										fieldLabel : '登录名',
										labelAlign:'right',
										labelWidth : 70
									}]
						}, {
							columnWidth : 1 / this.columnCount,
							layout : 'form',
							border : false,
							frame : false,
							items : [Ext.create(
									'SystemConsole.component.OrgComboBox', {
										fieldLabel : '所属机构',
										labelAlign:'right',
										name : 'orgId',
										displayField : 'text',
										rootId : '1',
										labelWidth : 70
									})]
						}, {
							columnWidth : 1 / this.columnCount,
							layout : 'form',
							border : false,
							frame : false,
							items : [Ext.create(
									'SystemConsole.component.WordBookComboBox',
									{
										fieldLabel : '用户状态',
										labelAlign:'right',
										name : 'status',
										wordBook : 'userState',
										labelWidth : 70
									})]
						}, {
							columnWidth : 1/this.columnCount,
							border : false,
							frame : false,
							layout : {
								type : 'hbox',
								pack : 'end'
							},
							defaults : {
								margin : '4px 0px 0px 10px',
								width:80
							},
							items : [{
										xtype : 'button',
										text : '查询',
										handler : this.doQuery
									}, {
										xtype : 'button',
										text : '重置',
										handler:this.doReset
									}]
						}];
			/*	this.buttons=[{
					xtype : 'button',
					text : '查询',
					handler : this.doQuery
				}, {
					xtype : 'button',
					text : '重置',
					handler:this.doReset
				}];*/

				this.callParent(arguments);
			},
			doQuery : function(btn) {
				var form = btn.up('form');
				var userGrid = btn.up('userpanel').down('usergridpanel');
				Ext.apply(userGrid.store.proxy.extraParams, form.getForm().getValues());
				userGrid.store.load({params:{start:0,limit:50,page:1}});
			},
			doReset:function(btn){
				var form = btn.up('form');
				form.getForm().reset();
				form.getForm().findField('orgId').setValue('');
			}

		});