/**
 * 查询配置查询面板
 */

Ext.define('SystemConsole.query.QueryQueryPanel', {
			extend : 'Ext.form.Panel',
			layout : 'column',
			columnCount : 1,
			border : false,
			initComponent : function() {
				this.items = [{
							columnWidth : 1 / this.columnCount,
							layout : 'form',
							border : false,
							frame : false,
							items : [{
										xtype : 'textfield',
										name : 'key',
										fieldLabel : '查询标识',
										labelAlign:'right',
										labelWidth : 70
									}]
						}, {
							columnWidth : 1 / this.columnCount,
							layout : 'form',
							border : false,
							frame : false,
							items : [{
										xtype : 'textfield',
										name : 'title',
										fieldLabel : '查询标题',
										labelAlign:'right',
										anchor:'90%',
										labelWidth : 70
									}]
						}, {
							columnWidth : 1/this.columnCount,
							border : false,
							frame : false,
							layout : {
								type : 'hbox',
								pack : 'end'
							},
							defaults : {
								margin : '4px 0px 0px 10px',
								width:80
							},
							items : [{
										xtype : 'button',
										text : '查询',
										handler : this.doQuery
									}, {
										xtype : 'button',
										text : '重置',
										handler:this.doReset
									}]
						}];

				this.callParent(arguments);
			},
			doQuery : function(btn) {
				var form = btn.up('form');
				var userGrid = btn.up('querypanel').down('querygridpanel');
				Ext.apply(userGrid.store.proxy.extraParams, form.getForm().getValues());
				userGrid.store.load({params:{start:0,limit:50,page:1}});
			},
			doReset:function(btn){
				var form = btn.up('form');
				form.getForm().reset();
			}

		});