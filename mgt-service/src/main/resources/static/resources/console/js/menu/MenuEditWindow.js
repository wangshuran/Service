Ext.define('SystemConsole.menu.MenuEditWindow', {
			extend : 'Ext.window.Window',
			alias : 'widget.menueditview',
			layout : 'fit',
			initComponent : function() {
				this.items = [{
							xtype : 'form',
							border : false,
							frame : false,
							defaults: {
								xtype : 'textfield',
								anchor : '95%'
							},
							bodyStyle:'padding:10px 0px 0px 10px',
							items : [{
										xtype : 'hidden',
										name : 'menuId'
									}, {
										xtype : 'hidden',
										name : 'menuParent'
									}, {
										name : 'menuParentName',
										fieldLabel : '父菜单名称',
										readOnly:true
									}, {
										name : 'menuName',
										fieldLabel : '菜单名称'
									}, {
										name : 'menuIconCls',
										fieldLabel : '菜单样式'
									},
										Ext
										.create(
												'SystemConsole.component.WordBookComboBox',
												{
													fieldLabel : '显示模式',
													name : 'displayMode',
													anchor : '95%',
													wordBook : 'displayMode'
												}) 
									,{
										xtype : 'textarea',
										name : 'menuUrl',
										fieldLabel : '菜单链接'
									}, {
										xtype : 'textarea',
										name : 'menuDesc',
										fieldLabel : '菜单描述'
									}, {
										name : 'menuWeight',
										fieldLabel : '排序'
									}]
						}];
				this.callParent(arguments);
			}
		})
