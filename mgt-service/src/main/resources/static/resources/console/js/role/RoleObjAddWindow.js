/**
 * 增加角色对象
 */
Ext.define('SystemConsole.role.RoleObjAddWindow', {
	extend : 'Ext.tree.Panel',
	alias : 'widget.roleobjaddwindow',
	roleId : null,
	rootId : null,
	checkMode:null,
	initComponent : function() {
		this.store = Ext.create('Ext.data.TreeStore',
				{
					fields : [ 'id', 'text', 'expanded', 'iconCls', 'children',
							'leaf' ],
					proxy : {
						type : 'ajax',
						actionMethods : 'POST',
						extraParams : {
							rootOrgId : this.rootId,
							checkMode:this.checkMode
						},
						rootVisiable:false,
						url : context + '/sysorg/orgUserCheckTree',
						reader : {
							type : 'json'
						}
					}
				});
		this.store.load();
		this.callParent(arguments);
	}
})