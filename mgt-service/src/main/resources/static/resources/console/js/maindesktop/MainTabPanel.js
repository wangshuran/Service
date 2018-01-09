/**
 * 主窗体显示窗口
 */
Ext.define('SystemConsole.maindesktop.MainTabPanel', {

	extend : 'Ext.tab.Panel',
	
	alias:'widget.maintabpanel',
	
	maxTabWidth : 230,
	border : false,

	initComponent : function() {
		this.tabBar = {
			border : true
		};
		
	
		this.items=[];

		this.callParent();
	},
	calendarHtml:function(){
		var html = '<table cellpadding="0" cellspacing="0" >'
			+ '	<tr>            '
			+ '		<td>          '
			+ '		<div id="cal">'
			+ '		<div id="top">公元 &nbsp;&nbsp;             '
			+ '		<img src="'
			+ context
			+ '/resources/images/leftE.png" id="leftE" style="cursor:hand;margin: -4 auto;text-align:center;"/>'
			+ '		&nbsp;<select>'
			+ '		</select>&nbsp;     '
			+ '		<img src="'
			+ context
			+ '/resources/images/rightE.png" id="rightE" style="cursor:hand;margin: -4 auto;text-align:center;"/> '
			+ '		&nbsp;年&nbsp;'
			+ '		<img src="'
			+ context
			+ '/resources/images/left.png" id="left" style="cursor:hand;margin: -4 auto;text-align:center;"/>  '
			+ '		&nbsp;<select>'
			+ '		</select>&nbsp;     '
			+ '		<img src="'
			+ context
			+ '/resources/images/right.png" id="right" style="cursor:hand;margin: -4 auto;text-align:center;"/>'
			+ '		月 &nbsp;&nbsp; 农历<span></span>年 [ <span></span>年 ] &nbsp;<img style="cursor:hand;margin: -4 auto;text-align:center;" id="today" src="'
			+ context
			+ '/resources/images/420.png" title="点击后跳转回今天"/>  '
			+ '		&nbsp;<!-- <img style="cursor:hand;margin: -4 auto;text-align:center;" onclick="tt();" src="'
			+ context
			+ '/resources/images/calendar.png" title="工作日管理"/>-->                '
			+ '		</div>        '
			+ '		<ul id="wk">  '
			+ '			<li><b>日</b></li>'
			+ '			<li>一</li> '
			+ '			<li>二</li> '
			+ '			<li>三</li> '
			+ '			<li>四</li> '
			+ '			<li>五</li> '
			+ '			<li><b>六</b></li>'
			+ '		</ul>         '
			+ '		<div id="cm"></div> '
			+ '		</div>        '
			+ '		</td>         '
			+ '	</tr>           '
			+ '	<tr>            '
			+ '		<td height=20px align="center" style="padding-top:5px;">'
			+ '			<div style="width:15px;height:15px;background:#ff3300;border :1px solid #a5b900;float:left;"></div><div style="float:left"><font style="font-size:10"> 未填日报&nbsp;</font></div>        '
			+ '			<div style="width:15px;height:15px;background:#ff9900;border :1px solid #a5b900;float:left;"></div><div style="float:left"><font style="font-size:10"> 已填待提交&nbsp;</font></div>        '
			+ '			<div style="width:15px;height:15px;background:#c1d900;border :1px solid #a5b900;float:left;"></div><div style="float:left"><font style="font-size:10"> 已提交&nbsp;</font></div>        '
			+ '			<div style="width:15px;height:15px;background:#c1d9ff;border :1px solid #a5b9da;float:left;"></div><div style="float:left"><font style="font-size:10"> 当前日期&nbsp;</font></div>        '
			+ '		</td>         ' + '	</tr>           ' + '</table>         '
			+ '                 ' + '<div id="workdaylist"></div>';
		
		return html;
	}
});