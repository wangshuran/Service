Ext.define('SystemConsole.permission.PermissionWindow', {
	extend : 'Ext.Panel',
	alias : 'widget.permissionwindow',
	layout : 'border',
	initComponent : function() {
		this.items = [
				Ext.create('SystemConsole.permission.PermissionObjSelectPanel',
						{
							region : 'center'
						}),
				Ext.create('SystemConsole.permission.MenuSelectPanel', {
					region : 'east',
					width : 500,
					split : true,
					title : '授权菜单',
					rootVisible : false,
					buttonAlign : 'center',
					buttons : [ {
						text : '确定',
						handler : this.savePermission
					} ]
				}) ];
		this.callParent(arguments);
	},
	savePermission : function(btn) {

		var menuSelectTree = btn.up("menuselectpanel");
		var checkNodes = menuSelectTree.getChecked();
		var checkedIds = '';
		Ext.each(checkNodes, function(node) {
			if (checkedIds == '') {
				checkedIds = node.raw.id;
			} else {
				checkedIds = checkedIds + ',' + node.raw.id;
			}
		});

		Ext.Ajax.request({
			url : context + '/permission/add',
			method : 'POST',
			params : {
				pObjType : menuSelectTree.pObjType,
				pObjId : menuSelectTree.pObjId,
				pType : menuSelectTree.pType,
				checkedIds : checkedIds
			},
			success : function(response, options) {
				var o = Ext.JSON.decode(response.responseText);
				if (o.success) {
					Ext.MessageBox.alert("提示", "保存权限成功！");
				} else {
					Ext.MessageBox.alert("提示", "保存权限失败！");
				}
			}
		});

	}
})