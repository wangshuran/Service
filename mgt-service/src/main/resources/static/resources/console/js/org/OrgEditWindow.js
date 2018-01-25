Ext.define('SystemConsole.org.OrgEditWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.orgeditview',
	layout : 'fit',
	initComponent : function() {
		this.items = [ {
			xtype : 'form',
			border : false,
			frame : false,
			defaults : {
				xtype : 'textfield',
				anchor : '95%'
			},
			bodyStyle : 'padding:10px 0px 0px 10px',
			items : [ {
				xtype : 'hidden',
				name : 'orgId'
			}, {
				xtype : 'hidden',
				name : 'orgParent'
			}, {
				xtype : 'hidden',
				name : 'orgCode'
			}, {
				name : 'orgParentName',
				fieldLabel : '父机构名称',
				readOnly : true
			}, {
				name : 'orgNumber',
				fieldLabel : '机构编码'
			}, {
				name : 'orgName',
				fieldLabel : '机构名称'
			}, {
				xtype : 'textarea',
				name : 'orgDesc',
				fieldLabel : '机构描述'
			}, {
				name : 'orgWeight',
				fieldLabel : '排序'
			} ]
		} ];
		this.callParent(arguments);
	}
})