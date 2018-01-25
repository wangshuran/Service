/**
 * 微信菜单编辑窗口
 */
Ext.define('SystemConsole.wechat.MenuTree', {
	extend : 'Ext.tree.Panel',
	alias : 'widget.menutree',
	buttonAlign : 'center',
	initComponent : function() {
		var me = this;
		this.store = Ext.create('Ext.data.TreeStore', {
			proxy : {
				waitMsg : '正在加载数据...',
				type : 'ajax',
				actionMethods : 'POST',
				url : context + '/wechat/menu/tree'
			},
			root : {
				text : '微信菜单',
				id : '0',
				expanded : true
			}
		});
		this.buttons = [ {
			text : '发布',
			handler : function() {
				alert('ere');
			}
		} ];

		this.listeners = {
			itemcontextmenu : this.showItemContextMenu
		};

		this.callParent(arguments);
	},
	/**
	 * 右键事件
	 * 
	 * @param {}
	 *            view
	 * @param {}
	 *            record
	 * @param {}
	 *            item
	 * @param {}
	 *            index
	 * @param {}
	 *            e
	 */
	showItemContextMenu : function(view, record, item, index, e) {
		var me = this;
		e.preventDefault();
		var contextMenu = Ext.create('Ext.menu.Menu', {
			items : [ {
				text : '增加',
				handler : function() {
					me.menuAdd(record);
				}
			} ]
		});

		if (!record.get('root')) {
			contextMenu.add({
				text : '编辑',
				handler : function() {
					me.menuEdit(record);
				}
			});
		}

		if (record.get('leaf')) {
			contextMenu.add({
				text : '删除',
				handler : function() {
					me.menuDel(record);
				}
			});
		}

		contextMenu.showAt(e.getXY());
	},
	/**
	 * 菜单新增窗体
	 * @param {} record
	 */
	menuAdd : function(record) {
		var me = this;
		var addView = Ext.create("SystemConsole.menu.MenuEditWindow", {
					title : '新增菜单',
					width : 400,
					height : 390,
					modal : true,
					renderTo : Ext.getBody(),
					buttons : [{
								text : '保存',
								handler : me.doMenuSave
							}, {
								text : '取消',
								handler : me.doCancle
							}]
				});
		addView.show();
		// 加载一些数据
		var tempR = Ext.create('SystemConsole.menu.MenuModel');
		tempR.set('menuParent', record.get('menuId'));
		tempR.set('menuParentName', record.get('menuName'));
		tempR.set('parentNode', record);
		addView.down('form').loadRecord(tempR);
	}
})