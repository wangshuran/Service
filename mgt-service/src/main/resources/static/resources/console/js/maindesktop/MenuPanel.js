/**
 * 系统菜单显示窗体
 */
Ext.define('SystemConsole.maindesktop.MenuPanel', {
			extend : 'Ext.panel.Panel',

			animCollapse : true,
			layout : 'accordion',

			initComponent : function() {

				this.listeners = {
					beforerender : this.menuPanelBeforeRender
				};

				this.callParent(arguments);
			},
			/**
			 * 初始化用户菜单
			 * 
			 * @param {}
			 *            menuPanel
			 */
			menuPanelBeforeRender : function(menuPanel) {
				var me = this;
//				Ext.MessageBox.wait('数据加载中,请稍后...','提示')
				Ext.Ajax.request({
							url : context + '/sysmenu/menusessiontree',
							method : 'post',
							params : {
								rootMenuId : 1
							},
							success : function(response, options) {
								var o = Ext.JSON.decode(response.responseText);
								if (o.success) {
									Ext.each(o.data, function(item) {
												menuPanel.add(me
														.initMenuTree(item));
											});
									menuPanel.doLayout();
//									Ext.MessageBox.hide();
								}
							}
						});

			},
			/**
			 * 初始化菜单树
			 * 
			 * @param {}
			 *            item
			 * @return {}
			 */
			initMenuTree : function(item) {
				var me = this;
				var menuTree = Ext.create('Ext.tree.Panel', {
							title : item.menuName,
							iconCls : item.menuIconCls,
							border : false,
							root : item,
							rootVisible : false,
							listeners : {
								itemclick : me.handleMenuClick
							}
						});

				return menuTree;

			},
			/**
			 * 菜单点击事件处理
			 * 
			 * @param {}
			 *            menu
			 * @param {}
			 *            record
			 * @param {}
			 *            item
			 * @param {}
			 *            index
			 * @param {}
			 *            e
			 */
			handleMenuClick : function(menu, record, item, index, e) {

				var me = this;
				var menuUrl = record.raw.menuUrl;
				var menuId = record.raw.id;
				var menuName = record.raw.menuName;
				var displayMode = record.raw.displayMode;
				var tabId = 'tab_menu_' + menuId;
				var tabs = me.up('viewport').down('maintabpanel');
				var isExitTab = Ext.getCmp(tabId);
				if (!Ext.isEmpty(isExitTab)) {
					tabs.setActiveTab(isExitTab);
					return;
				}
				if (!Ext.isEmpty(menuUrl)) {
					var splitIndex = menuUrl.indexOf(':');
					if (splitIndex != -1) {
						var menuType = menuUrl.substring(0, splitIndex);
						switch (menuType) {
							case 'emvc' :// 链接模式为ext4 mvc 的controller
								var controller = menuUrl.substring(splitIndex
												+ 1, menuUrl.length);
								Ext.require(controller, function() {
											var c = me.application
													.getController(controller);
											c.init();
											var view = c.getShowView({
														id : tabId,
														title : menuName,
														iconCls : 'tabcss',
														closable : true,
														closeAction : 'destory'
													});
											var tab = tabs.add(view);
											tabs.setActiveTab(tab);
										});
								break;
							case 'extscript' :
								var extClass = menuUrl.substring(
										splitIndex + 1, menuUrl.length);
								if("window"==displayMode){
									var view = Ext.create(extClass,{
										title : menuName,
										modal:true
									});
									view.show();
								}else{
									var view = Ext.create(extClass, {
												id : tabId,
												title : menuName,
												iconCls : 'tabcss',
												closable : true,
												closeAction : 'destory'
											});
									var tab = tabs.add(view);
									tabs.setActiveTab(tab);
								}
								break;
							case 'extloader' :// 链接模式为自定义loader
								var loaderClass = menuUrl.substring(splitIndex
												+ 1, menuUrl.length);
								var loader = Ext.create(loaderClass);
								var view = loader.getLoaderView({
											id : tabId,
											title : menuName,
											iconCls : 'tabcss',
											closable : true,
											closeAction : 'destory'
										})
								var tab = tabs.add(view);
								tabs.setActiveTab(tab);
								break;
							case 'query' :
								var queryKey = menuUrl.substring(
										splitIndex + 1, menuUrl.length);
								var queryGenerator = Ext.create('SystemConsole.component.QueryGenerator', queryKey);
								var query = queryGenerator.createMainPanel({
									id : tabId,
									border:false,
									title : menuName,
									iconCls : 'tabcss',
									closable : true,
									closeAction : 'destory'
								});
								var tab = tabs.add(query);
								tabs.setActiveTab(tab);
								break;
							default :
								break;
						}
					}
				}

			}
		});
