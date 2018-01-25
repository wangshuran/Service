/**
 * 角色管理
 */

Ext.define('SystemConsole.role.RoleObjGridPanel', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.roleobjgridpanel',
	initComponent : function() {
		var me = this;
		this.store = Ext.create('SystemConsole.role.RoleObjGridStore');
//		this.store.load();
		this.columns = [{
					text : '对象名称',
					flex : 1,
					dataIndex : 'objName'
				}, {
					header : '对象类型',
					flex : 1,
					dataIndex : 'objType',
					renderer : function(v) {
						var wbu = Ext.create(
								'SystemConsole.wordbook.WordBookUtil',
								'roleObjType');
						return wbu.getWordBookName(v);
					}
				}];
		this.tbar = [{
					text : '增加角色对象',
					itemId : 'addRoleObjBtn',
					disabled : true,
					iconCls : 'useradd',
					handler : me.showAddRoleObjWin
				}, {
					itemId : 'removeRoleObjBtn',
					text : '删除角色对象',
					iconCls : 'userdel',
					disabled : true,
					handler : function() {
						Ext.MessageBox.confirm('提示', '确定删除角色对象?', function(btn) {
									if (btn == 'yes') {
										var sm = me.getSelectionModel();
										me.store.remove(sm.getSelection()[0]);
										me.store.sync();
									}
								});

					}
				}];
		this.listeners = {
			selectionchange : this.roleObjGridSelectionChange
		};
		this.callParent(arguments);
	},
	showAddRoleObjWin : function(btn) {},
	roleObjGridSelectionChange : function(sm, records) {
		this.down('#removeRoleObjBtn').setDisabled(!records.length);
	}
});
