Ext.define('SystemConsole.permission.OrgSelectPanel', {
	extend : 'Ext.tree.Panel',
	alias : 'widget.orgselectpanel',
	rootId : null,
	useArrows : true,
	rootVisible : false,
	initComponent : function() {
		this.store = Ext.create('SystemConsole.org.OrgTreeGridStore');
		this.columns = [ {
			xtype : 'treecolumn',
			text : '机构名称',
			flex : 1,
			dataIndex : 'orgName'
		}, {
			text : '机构代码',
			flex : 1,
			dataIndex : 'orgCode'
		} ];
		this.listeners = {
			itemcontextmenu : this.showGroupTreeContextMenu
		};
		this.callParent(arguments);
	},
	showGroupTreeContextMenu : function(view, record, item, index, e) {
		var me = this;
		e.preventDefault();
		var contextMenu = Ext.create('Ext.menu.Menu',
				{
					items : [
							{
								text : '可以访问',
								handler : function() {
									me.loadCheckedMenuTree('G',record.data.orgId,'A0','可访问',record.data.orgName);
								}
							}, {
								text : '不可访问',
								handler : function() {
									me.loadCheckedMenuTree('G',record.data.orgId,'A1','不可访问',record.data.orgName);
								}
							} ]
				});

		contextMenu.showAt(e.getXY());
	},
	loadCheckedMenuTree : function(pObjType,pObjId,pType,pTypeName,orgName) {
		var me = this;

		var menuSelectTree = me.up('permissionobjselectpanel').up(
				'permissionwindow').down('menuselectpanel');
		menuSelectTree.setTitle('授权菜单【组：'+orgName+' '+pTypeName+'】');
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
})