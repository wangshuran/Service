/**
 * 视图列表查询面板
 */

Ext.define('SystemConsole.view.ViewQueryPanel', {
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
										name : 'viewKey',
										fieldLabel : '视图标识',
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
										fieldLabel : '视图标题',
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
				var viewGrid = btn.up('viewpanel').down('viewgridpanel');
				Ext.apply(viewGrid.store.proxy.extraParams, form.getForm().getValues());
				viewGrid.store.load({params:{start:0,limit:50,page:1}});
			},
			doReset:function(btn){
				var form = btn.up('form');
				form.getForm().reset();
			}

		});