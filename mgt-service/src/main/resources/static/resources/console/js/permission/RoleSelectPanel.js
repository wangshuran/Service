/**
 * 用户管理
 */

Ext.define('SystemConsole.permission.RoleSelectPanel', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.roleselectpanel',
	initComponent : function() {
		var me = this;
		this.store = Ext.create('SystemConsole.role.RoleGridStore');
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
		this.bbar = Ext.create('Ext.PagingToolbar', {
					store : me.store,
					displayInfo : true,
					displayMsg : '当前显示  第{0} - {1} of {2}',
					emptyMsg : "没有数据"
				});
		this.listeners = {
				itemcontextmenu : this.showRoleGridContextMenu
		};
		this.callParent(arguments);
	},
	showRoleGridContextMenu : function(view, record, item, index, e) {
		var me = this;
		e.preventDefault();
		var contextMenu = Ext.create('Ext.menu.Menu', {
			items : [ {
				text : '可以访问',
				handler : function() {
					me.loadCheckedMenuTree('R',record.data.roleId,'A0','可访问',record.data.roleName);
				}
			}, {
				text : '不可访问',
				handler : function() {
					me.loadCheckedMenuTree('R',record.data.roleId,'A1','不可访问',record.data.roleName);
				}
			} ]
		});

		contextMenu.showAt(e.getXY());
	},
	loadCheckedMenuTree : function(pObjType,pObjId,pType,pTypeName,roleName) {
		var me = this;

		var menuSelectTree = me.up('permissionobjselectpanel').up(
				'permissionwindow').down('menuselectpanel');
		menuSelectTree.setTitle('授权菜单【角色：'+roleName+' '+pTypeName+'】');
		menuSelectTree.pObjId = pObjId;
		menuSelectTree.pObjType = pObjType;
		menuSelectTree.pType =pType;
		Ext.Ajax.request({
			url : context + '/permission/checkedMenu',
			method : 'POST',
			params : {
				pObjType : menuSelectTree.pObjType,
				pObjId : menuSelectTree.pObjId,
				pType : menuSelectTree.pType
			},
			success : function(response, options) {
				var o = Ext.JSON.decode(response.responseText);
				if (o.success) {
					menuSelectTree.getStore().reload({
						params : {
							checkedIds : o.checkedIds
						}
					});
				} else {
					Ext.MessageBox.alert("提示", "加载数据失败！");
				}
			}
		});

	}
});
