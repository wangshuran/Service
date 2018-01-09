/**
 * 机构用户选择树
 */
Ext.define('SystemConsole.component.OrgUserSelectTree', {
	extend : 'Ext.tree.Panel',
	alias : 'widget.roleobjaddwindow',
	rootId : null,
	checkMode:null,
	initData:[],
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
		this.listeners = {
				load : this.loadEvent
			};
		this.store.load();
		this.callParent(arguments);
	},
	loadEvent:function(){
		var me = this;
		var store = me.getStore();
		if(me.initData.length>0){
			for(var i=0;i<me.initData.length;i++){
				var node = store.getNodeById(me.initData[i]);
				node.checked = true;  
				node.set('checked', true);  
			}
		}
	}
})