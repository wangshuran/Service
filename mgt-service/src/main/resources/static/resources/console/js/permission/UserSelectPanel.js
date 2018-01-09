/**
 * 用户管理
 */

Ext.define('SystemConsole.permission.UserSelectPanel', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.userselectpanel',
	initComponent : function() {
		var me = this;
		this.store = Ext.create('SystemConsole.user.UserGridStore');
		this.store.load();
		this.columns = [{
					text : '用户编号',
					flex : 1,
					dataIndex : 'userCode'
				}, {
					header : '用户姓名',
					flex : 1,
					dataIndex : 'userName'
				}, {
					text : '登录名',
					flex : 1,
					dataIndex : 'loginName'
				}, {
					text : '所属机构',
					flex : 1,
					dataIndex : 'orgNamePath'
				}, {
					text : '用户状态',
					flex : 1,
					dataIndex : 'status',
					renderer : function(v) {
						var wbu = Ext.create(
								'SystemConsole.wordbook.WordBookUtil',
								'userState');
						return wbu.getWordBookName(v);
					}
				}];
		this.bbar = Ext.create('Ext.PagingToolbar', {
					store : me.store,
					displayInfo : true,
					displayMsg : '当前显示  第{0} - {1} of {2}',
					emptyMsg : "没有数据"
				});
		this.listeners = {
				itemcontextmenu : this.showUserGridContextMenu
		};
		this.callParent(arguments);
	},
	showUserGridContextMenu : function(view, record, item, index, e) {
		var me = this;
		e.preventDefault();
		var contextMenu = Ext.create('Ext.menu.Menu', {
			items : [ {
				text : '可以访问',
				handler : function() {
					me.loadCheckedMenuTree('U',record.data.userId,'A0','可访问',record.data.userName);
				}
			}, {
				text : '不可访问',
				handler : function() {
					me.loadCheckedMenuTree('U',record.data.userId,'A1','不可访问',record.data.userName);
				}
			} ]
		});

		contextMenu.showAt(e.getXY());
	},
	loadCheckedMenuTree : function(pObjType,pObjId,pType,pTypeName,userName) {
		var me = this;

		var menuSelectTree = me.up('permissionobjselectpanel').up(
				'permissionwindow').down('menuselectpanel');
		menuSelectTree.setTitle('授权菜单【用户：'+userName+' '+pTypeName+'】');
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
