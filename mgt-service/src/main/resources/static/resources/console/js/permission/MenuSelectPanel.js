Ext.define('SystemConsole.permission.MenuSelectPanel', {
	extend : 'Ext.tree.Panel',
	alias : 'widget.menuselectpanel',
	rootMenuId : null,
	checkedIds:null,
	pobjType:null,
	pobjId:null,
	pType:null,
	initComponent : function() {
		this.store = Ext.create('Ext.data.TreeStore',
				{
					autoLoad:false, 
					fields : [ 'id', 'text', 'expanded', 'iconCls', 'children',
							'leaf' ],
					proxy : {
						type : 'ajax',
						actionMethods : 'POST',
						extraParams : {
							rootMenuId : this.rootMenuId,
							checkedIds:this.checkedIds
						},
						url : context + '/sysmenu/menuCheckTree',
						reader : {
							type : 'json'
						}
					}
				});
//		this.store.load();
		this.callParent(arguments);
	}
})