/**
 * 角色管理
 */

Ext.define('SystemConsole.role.RoleGridPanel', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.rolegridpanel',
	initComponent : function() {
		var me = this;
		this.store = Ext.create('SystemConsole.role.RoleGridStore',{pageSize:20});
		this.store.load();
		this.columns = [ {
			text : '角色标识',
			flex : 1,
			dataIndex : 'roleKey'
		}, {
			header : '角色描述',
			flex : 1,
			dataIndex : 'roleName'
		} ];
		this.tbar = [ {
			text : '增加角色',
			iconCls : 'groupadd',
			handler : me.showAddRoleWin
		}, {
			itemId : 'removeRoleBtn',
			text : '删除角色',
			iconCls : 'groupdel',
			disabled : true,
			handler : function() {
				Ext.MessageBox.confirm('提示', '确定删除角色?', function(btn) {
					if (btn == 'yes') {
						var sm = me.getSelectionModel();
						me.store.remove(sm.getSelection()[0]);
						me.store.sync();
						
						me.up('rolepanel').down('roleobjgridpanel').getStore().load({});
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
			selectionchange : this.roleGridSelectionChange
		};
		this.callParent(arguments);
	},
	showAddRoleWin : function(btn) {

		Ext.create("SystemConsole.role.RoleEditWindow", {
			title : '新增角色',
			width : 400,
			height : 220,
			modal : true,
			renderTo : Ext.getBody(),
			buttons : [ {
				text : '保存',
				handler : function() {

					Ext.MessageBox.wait('数据保存中...');

					var win = this.up('window'), form = win.down('form');

					form.getForm().submit({
						clientValidation : true,
						url : context + '/sysrole/add',
						success : function(form, action) {
							win.close();
							Ext.MessageBox.hide();
							btn.up('rolegridpanel').getStore().load();
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

	},
	roleGridSelectionChange : function(sm, records) {
		if (records.length && records[0].get('roleId') != '') {
			this.down('#removeRoleBtn').setDisabled(!records.length);
			this.up('rolepanel').down('roleobjgridpanel').down(
					'#addRoleObjBtn').setDisabled(!records.length);
			this.up('rolepanel').down('roleobjgridpanel').down(
			'#addRoleObjBtn').on('click',function(btn){this.addRoleObjBtnClick(btn,records[0].get('roleId'))},this);
			this.up('rolepanel').down('roleobjgridpanel').setTitle('【'+records[0].get('roleName')+'】角色对象列表');
			this.up('rolepanel').down('roleobjgridpanel').getStore()
					.load({
						params : {
							roleId : records[0].get('roleId')
						}
					});
			this.up('rolepanel').down('roleobjgridpanel').expand();
		}
	},
	addRoleObjBtnClick:function(btn,roleId){
		Ext.create("Ext.Window",{
			title : '新增角色对象',
			width : 400,
			height : 500,
			modal : true,
			layout:'fit',
			renderTo : Ext.getBody(),
			items:[Ext.create("SystemConsole.role.RoleObjAddWindow", { rootVisible: false,checkMode:'GU'})],
			buttons : [ {
				text : '选择',
				handler : function() {

					var win = this.up('window'), roleObjTree = win.down('roleobjaddwindow');
					var roleObjGrid = btn.up('roleobjgridpanel');

					var checkNodes = roleObjTree.getChecked( );
					Ext.each(checkNodes,function(node){
						var r = Ext.create(
								'SystemConsole.role.RoleObjModel', {
									roleId:roleId,
									objId:node.raw.realId,
									objName:node.raw.text,
									objType:node.raw.type
								});
						roleObjGrid.store.insert(0, r);
						roleObjGrid.store.sync();
					});
					this.up('window').close();
				}
			}, {
				text : '关闭',
				handler : function() {
					this.up('window').close();
				}
			} ]
		
		}).show();
	}
});
