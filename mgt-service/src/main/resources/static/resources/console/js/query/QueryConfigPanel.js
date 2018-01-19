/**
 * 查询配置页面
 */

Ext.define('SystemConsole.query.QueryConfigPanel', {
	extend: 'Ext.form.Panel',
	border: false,
	bodyStyle: 'padding:10px',
	autoScroll: true,
	queryId: null,
	initComponent: function () {
		var me = this;
		this.items = [{
            xtype: 'fieldset',
            title: '基本信息',
			collapsed: true,
            collapsible: true,
            items: [{
				layout: 'column',
				border: false,
				items: [
					{
						columnWidth: .5,
						layout: 'anchor',
						border: false,
						items: [{
							name: 'queryKey',
							xtype: 'textfield',
							fieldLabel: '查询标识',
							anchor: '95%',
							disabled: true
						}]
					},
					{
						columnWidth: .5,
						layout: 'anchor',
						border: false,
						items: [{
							xtype: 'textfield',
							name: 'title',
							anchor: '95%',
							fieldLabel: '查询标题',
							disabled: true
						}]

					},
					{
						columnWidth: 1,
						layout: 'anchor',
						border: false,
						items: [{
							xtype: 'textarea',
							name: 'remark',
							anchor: '97.5%',
							fieldLabel: '查询描述',
							disabled: true
						}]
					}]
			}]
        }, { xtype: 'hidden', name: 'id' }, { xtype: 'hidden', name: 'queryId', value: me.queryId }, {
				xtype: 'fieldset',
				title: '配置信息',
				collapsible: true,
				items: [{
					layout: 'column',
					border: false,
					items: [
						{
							columnWidth: .25,
							layout: 'anchor',
							border: false,
							items: [{
								name: 'loadJS',
								xtype: 'textfield',
								fieldLabel: '加载JS',
								anchor: '90%'
							}]
						},
						{
							columnWidth: .25,
							layout: 'anchor',
							border: false,
							items: [{
								xtype: 'textfield',
								name: 'loadCSS',
								anchor: '90%',
								fieldLabel: '加载CSS'
							}]

						},
						{
							columnWidth: .25,
							layout: 'anchor',
							border: false,
							items: [{
								xtype: 'textfield',
								name: 'width',
								anchor: '90%',
								fieldLabel: '宽度'
							}]

						},
						{
							columnWidth: .25,
							layout: 'anchor',
							border: false,
							items: [{
								xtype: 'textfield',
								name: 'height',
								anchor: '90%',
								fieldLabel: '高度'
							}]

						},
						{
							columnWidth: .25,
							layout: 'anchor',
							border: false,
							items: [ Ext
										.create(
												'SystemConsole.component.WordBookComboBox',
												{
													fieldLabel : '是否分页',
													name : 'isPage',
													anchor : '90%',
													wordBook : 'yon'
												}) ]

						},
						{
							columnWidth: .25,
							layout: 'anchor',
							border: false,
							items: [{
								xtype: 'textfield',
								name: 'pageSize',
								anchor: '90%',
								fieldLabel: '分页大小'
							}]

						},
						{
							columnWidth: .25,
							layout: 'anchor',
							border: false,
							items: [ Ext
										.create(
												'SystemConsole.component.WordBookComboBox',
												{
													fieldLabel : '页面布局',
													name : 'layout',
													anchor : '90%',
													wordBook : 'layout'
												}) ]
						
						},
						{
							columnWidth: .25,
							layout: 'anchor',
							border: false,
							items: [ Ext
										.create(
												'SystemConsole.component.WordBookComboBox',
												{
													fieldLabel : '是否导出Excel',
													name : 'isExportExcel',
													anchor : '90%',
													wordBook : 'yon'
												}) ]

						},
						{
							columnWidth: .25,
							layout: 'anchor',
							border: false,
							items: [ Ext
										.create(
												'SystemConsole.component.WordBookComboBox',
												{
													fieldLabel : '是否复选框',
													name : 'isCheckBox',
													anchor : '90%',
													wordBook : 'yon'
												}) ]

						},
						{
							columnWidth: .25,
							layout: 'anchor',
							border: false,
							items: [{
								xtype: 'textfield',
								name: 'queryWidth',
								anchor: '90%',
								fieldLabel: '查询框宽度'
							}]

						},
						{
							columnWidth: .25,
							layout: 'anchor',
							border: false,
							items: [{
								xtype: 'textfield',
								name: 'queryHeight',
								anchor: '90%',
								fieldLabel: '查询框高度'
							}]

						},
						{
							columnWidth: .25,
							layout: 'anchor',
							border: false,
							items: [{
								xtype: 'textfield',
								name: 'queryColumns',
								anchor: '90%',
								fieldLabel: '查询项列数'
							}]

						},{
							columnWidth: 1,
							layout: 'anchor',
							border: false,
							items: [{
								xtype: 'textfield',
								name: 'fetchDataUrl',
								anchor: '95%',
								fieldLabel: '数据链接'
							}]

						},
						{
							columnWidth: .5,
							layout: 'anchor',
							border: false,
							items: [{
								xtype: 'textarea',
								name: 'fetchSql',
								anchor: '95%',
								height: 250,
								fieldLabel: '数据SQL'
							}]
						},
						{
							columnWidth: .5,
							layout: 'anchor',
							border: false,
							items: [{
								xtype: 'textarea',
								name: 'countSql',
								anchor: '95%',
								height: 250,
								fieldLabel: '总数SQL'
							}]
						}
					]
				}]
			}, Ext.create('SystemConsole.query.QueryItemGridPanel', {
				title: '查询项列表',
				height: 200,
				border: true,
				queryId: me.queryId
			}), Ext.create('SystemConsole.query.QueryResultGridPanel', {
				title: '显示字段列表',
				height: 400,
				border: true,
				queryId: me.queryId,
				style:'margin-top:10px;'
			})];
		this.buttonAlign = 'center'
		this.buttons = [
			{
				text: '保存', handler: function () {
					me.getForm().submit({
						clientValidation: true,
						url: context + '/query/config/save',
						success: function (form, action) {
							me.destroy();
							Ext.Msg.alert('提示', '查询配置保存成功');
						},
						failure: function (form, action) {
							switch (action.failureType) {
								case Ext.form.action.Action.CLIENT_INVALID:
									Ext.Msg.alert('提示', '请正确填写页面信息');
									break;
								case Ext.form.action.Action.CONNECT_FAILURE:
									Ext.Msg.alert('失败', '服务器连接超时');
									break;
								case Ext.form.action.Action.SERVER_INVALID:
									Ext.Msg.alert('失败',
										action.result.message);
							}
						}
					});
				}
			}, {
				text: '关闭', handler: function () {
					me.destroy();
				}
			}
		]

		this.callParent(arguments);
	}

});