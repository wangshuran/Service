Ext.define('SystemConsole.component.ToastWin', {
	extend : 'Ext.window.Window',
	alias : 'widget.toastwin',
	autoScroll : true,
	autoDestroy : true,
	plain : false,
	shadow : false,
	positions:[],
	initComponent : function() {
		this.task = new Ext.util.DelayedTask(this.hide, this); 
		this.callParent(arguments);
	},
	afterShow : function() {
		this.superclass.afterShow.call(this);
		this.on('move', function() {
			this.positions.remove(this.pos);
			this.task.cancel();
		}, this);
		this.task.delay(4000);
	},
	animShow : function(document) {
		this.pos = 0;
		while (this.positions.indexOf(this.pos) > -1)
			this.pos++;
		this.positions.push(this.pos);
		this.setSize(250, 150);
		this.el.alignTo(document, "br-br", [ -20,
				-20 - ((this.getSize().height + 10) * this.pos)

		]);
		this.el.slideIn('b', {
			duration : 2,
			callback : this.afterShow,
			scope : this
		});
	},
	animHide : function() {
		this.positions.remove(this.pos);
		this.el.ghost("b", {
			duration : 2,
			remove : true,
			scope : this,
			callback : this.destroy
		});
	}
});