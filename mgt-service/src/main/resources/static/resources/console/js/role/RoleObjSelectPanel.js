/**
 * 角色管理
 */

Ext.define('SystemConsole.role.RoleObjSelectPanel', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.roleobjselectpanel',
	selType: 'checkboxmodel',
	selModel:{mode:'SINGLE'},
	roleKey : null,
	win:null,
	initComponent : function() {
		var me = this;
		this.store = Ext.create('SystemConsole.role.RoleObjSelectStore');
		this.store.on('beforeload', function(thiz, options) {
			Ext.apply(me.store.proxy.extraParams, {
				roleKey : me.roleKey
			});
		});
		this.columns = [
				{
					text : '对象名称',
					flex : 1,
					dataIndex : 'objName'
				},
				{
					header : '对象类型',
					flex : 1,
					dataIndex : 'objType',
					renderer : function(v) {
						var wbu = Ext.create(
								'SystemConsole.wordbook.WordBookUtil',
								'roleObjType');
						return wbu.getWordBookName(v);
					}
				} ];
		this.tbar = [ {
			text : '选择',
			itemId : 'addRoleObjBtn',
			iconCls : 'useradd',
			handler : function() {
				var sm = me.getSelectionModel();
				var record = sm.getSelection()[0];
				me.win.down('form').getForm().findField('pmId').setValue(record.data.objId);
				me.win.down('form').getForm().findField('pmName').setValue(record.data.objName);
				me.win.down('form').getForm().findField('pmNameCheckbox').setValue(record.data.objName);
				me.win.down('form').getForm().findField('pmNameCheckbox').collapse();
			}
		} ];
		this.callParent(arguments);
	}
});
