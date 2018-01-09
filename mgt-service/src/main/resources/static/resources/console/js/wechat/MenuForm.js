/**
 * 微信菜单编辑表单
 */
Ext.define('SystemConsole.wechat.MenuForm', {
	extend : 'Ext.form.Panel',
	alias : 'widget.wechat_menu_form',
	border : false,
	frame : false,
	bodyStyle : 'padding:10px;',
	fieldDefaults : {
		labelAlign : 'right',
		labelWidth : 65,
		msgTarget : 'side'
	},
	initComponent : function() {
		var me = this;
		this.items = [ {
			xtype : 'hidden',
			name : 'WM_ID'
		}, {
			xtype : 'hidden',
			name : 'PARENT_ID'
		}, {
			xtype : 'hidden',
			name : 'MEDIA_ID'
		}, {
			xtype : 'hidden',
			name : 'MESSAGE_TYPE'
		}, {
			xtype : 'textfield',
			fieldLabel : '菜单名称',
			name : 'menuName',
			allowBlank : false
		}, {
			xtype : 'textfield',
			fieldLabel : '排序',
			name : 'MENU_NAME',
			allowBlank : false
		},{
			xtype : 'radiogroup',
			width : 400,
			fieldLabel : '菜单内容',
			id : 'MENU_TYPE_RADIOI_GROUP',
			name : 'MENU_TYPE',
			items : [ {
				xtype : 'radio',
				boxLabel : '发送消息',
				name : 'MENU_TYPE',
				inputValue : '0'
			}, {
				xtype : 'radio',
				boxLabel : '跳转网页',
				name : 'MENU_TYPE',
				inputValue : '1'
			}, {
				xtype : 'radio',
				boxLabel : '自定义按钮',
				name : 'MENU_TYPE',
				inputValue : '2'
			} ],
			listeners : {
				'change' : function(thiz,newValue,oldValue) {
					var value = thiz.getValue().MENU_TYPE;
					var menuUrlField = me.getForm()
							.findField('MENU_URL');
					var menuBtnField = me.getForm()
							.findField('MENU_BTN');
					var sendMessageTabPanel = Ext
							.getCmp('sendMessageTabPanel');
					var urlAuthRadioGroup = Ext.getCmp("URL_AUTH_RADIOI_GROUP");
					if (value == '0') {
						if (menuUrlField) {
							menuUrlField.destroy();
						}
						if (menuBtnField) {
							menuBtnField.destroy();
						}
						if(urlAuthRadioGroup){
							urlAuthRadioGroup.destroy();
						}

						var html = [];
						for (var i = 0; i <= 3; i++) {
							var mediaId = null;
							html[i] = me.initMessageTab(me.id,
									null, i);
						}
						me.add(Ext.create('Ext.tab.Panel',{
							id : 'sendMessageTabPanel',
							margins : '3 3 3 0',
							activeTab : 0,
							width : 430,
							border:false,
							frame:true,
							defaults : {
								autoScroll : true,
								autoHeight : true
							},
							items : [ {
								title : '图文消息',
								id : 'sendMessage_Tab_0',
								html : html[0]
							}, {
								title : '图片',
								id : 'sendMessage_Tab_1',
								html : html[1]
							}, {
								title : '语音',
								id : 'sendMessage_Tab_2',
								html : html[2]
							}, {
								title : '视频',
								id : 'sendMessage_Tab_3',
								html : html[3]
							} ]
						}));
					} else if (value == '1') {
						if (sendMessageTabPanel) {
							sendMessageTabPanel.destroy();
						}
						if (menuBtnField) {
							menuBtnField.destroy()
						}
						me.add({
							xtype : 'radiogroup',
							width : 266,
							fieldLabel : '网页授权',
							id : 'URL_AUTH_RADIOI_GROUP',
							name : 'URL_AUTH',
							items : [ {
								xtype : 'radio',
								boxLabel : '是',
								name : 'URL_AUTH',
								inputValue : '0'
							}, {
								xtype : 'radio',
								boxLabel : '否',
								name : 'URL_AUTH',
								inputValue : '1'
							} ]
						},{
							xtype : 'textfield',
							fieldLabel : '跳转链接',
							width : 350,
							name : 'MENU_URL'
						});
					} else if (value == '2') {
						if (sendMessageTabPanel) {
							sendMessageTabPanel.destroy();
						}
						if (menuUrlField) {
							menuUrlField.destroy()
						}
						if(urlAuthRadioGroup){
							urlAuthRadioGroup.destroy();
						}
						me.add({
							xtype : 'textfield',
							fieldLabel : '按钮标识',
							name : 'MENU_BTN'
						});
					}

					me.doLayout();
				}

			}
		} ];
		this.callParent(arguments);
	},
	initMessageTab:function(formId, mediaId, messageType) {
		if (mediaId) {
			if ('0' == messageType)
				return sendRequest(context + '/views/wechat/menu/news/' + mediaId
						+ '/' + formId);
			else if ('1' == messageType)
				return sendRequest(context + '/views/wechat/menu/image/' + mediaId
						+ '/' + formId);
			else if ('2' == messageType)
				return sendRequest(context + '/views/wechat/menu/audio/' + mediaId
						+ '/' + formId);
		} else {
			if (!messageType) {
				messageType = '0';
			}
			if ('0' == messageType)
				return '<center><div style="width:80%;height:250px;line-height:250px;"><a href="javascript:void(0);" onclick="javascript:showNewsSelectWindow(\''
						+ formId + '\');">从素材库中选择图文</a></div></center>';
			else if ('1' == messageType)
				return '<center><div style="width:80%;height:250px;line-height:250px;"><a href="javascript:void(0);" onclick="javascript:showImageSelectWindow(\''
						+ formId + '\');">从素材库中选择图片</a></div></center>';
			else if ('2' == messageType)
				return '<center><div style="width:80%;height:250px;line-height:250px;"><a href="javascript:void(0);" onclick="javascript:showAudioSelectWindow(\''
						+ formId + '\');">从素材库中选择语音</a></div></center>';
			else if ('3' == messageType)
				return '<center><div style="width:80%;height:250px;line-height:250px;"><a href="javascript:void(0);" onclick="javascript:showVideoSelectWindow(\''
						+ formId + '\');">从素材库中选择视频</a></div></center>';
		}
	}
})