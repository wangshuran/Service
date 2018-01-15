function fund_plan_list_initTbar(tbar, query) {
	if (tbar.length > 0) {
		tbar.push('-');
	}

	tbar.push({
		text : '新增',
		iconCls : 'add',
		handler : function(btn) {
			addFundPlan(btn, query);
		}
	});
}

function addFundPlan(btn, query) {

}