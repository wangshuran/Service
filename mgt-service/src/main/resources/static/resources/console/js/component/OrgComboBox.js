/**
 * 组织机构树选择
 */

Ext.define('SystemConsole.component.OrgComboBox', {
			extend : 'Ext.ux.TreePicker',
			rootId : null,
			matchFieldWidth:false,
			pickerWidth:400,
			initComponent : function() {

				this.store = Ext.create('Ext.data.TreeStore', {
							fields : ['id', 'text'],
							proxy : {
								type : 'ajax',
								actionMethods : 'POST',
								extraParams : {
									rootOrgId : this.rootId
								},
								url : context + '/sysorg/orgselecttree',
								reader : {
									type : 'json'
								}
							}
						});
				this.store.load();
				this.callParent(arguments);
			}
		});