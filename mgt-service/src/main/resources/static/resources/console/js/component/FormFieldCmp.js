function createFormField(type, options) {
	if (type == 'input') {
		return Ext.create('Ext.form.Text', options);
	} else if (type == 'wordbook') {
		return Ext.create('SystemConsole.component.WordBookComboBox', options)
	} else if (type == 'selectorg') {
		if (!options.displayField) {
			options.displayField = 'text';
		}
		if (!options.rootId) {
			options.rootId = 'G_0'
		}
		return Ext.create('SystemConsole.component.OrgComboBox', options);
	}
}

function createTableQueryCheckBox(options,returnItemId,returnItemName,selectItemId,selectItemName) {
	
	Ext.create('Ext.grid.Panel', {
	    store: Ext.data.StoreManager.lookup('simpsonsStore'),
	    columns: [
	        { text: 'Name',  dataIndex: 'name' },
	        { text: 'Email', dataIndex: 'email', flex: 1 },
	        { text: 'Phone', dataIndex: 'phone' }
	    ],
	    height: 200,
	    width: 400
	});
	
	var checkBox = Ext.create('Ext.form.field.ComboBox', {
		store : Ext.create('Ext.data.Store', {
			fields : [],
			data : [ {} ]
		})
	});
}