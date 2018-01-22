/**
 * 同步发送数据请求
 * 
 * @param {}
 *            url
 * @return {String}
 */
function sendRequest(url) {
	var xmlhttp = document.all ? new ActiveXObject("Msxml2.XMLHTTP")
			: new XMLHttpRequest();
	xmlhttp.open("POST", url, false);
	xmlhttp.setRequestHeader('x-requested-with', 'XMLHttpRequest');
	xmlhttp.send(null);

	if (xmlhttp.getResponseHeader('sessionstatus') == 'timeout') {
		invalidSession.process();

		return '';
	}
	return xmlhttp.responseText;
}

function sendRequestObject(url,method) {
	var xmlhttp = document.all ? new ActiveXObject("Msxml2.XMLHTTP")
			: new XMLHttpRequest();
	if(method==null)
		method='POST';
	xmlhttp.open(method, url, false);
	var XMLstr = '';
	xmlhttp.setRequestHeader('x-requested-with', 'XMLHttpRequest');
	if (XMLstr != '') {
		var allXMLstr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		allXMLstr += "<root>";
		allXMLstr += XMLstr;
		allXMLstr += "</root>";
		xmlhttp.setRequestHeader("Content-Type", "text/xml");
		xmlhttp.setRequestHeader("Content-Type", "UTF-8");
		xmlhttp.send(allXMLstr);
		XMLstr = "";
	} else {
		xmlhttp.send(null);
	}

	return xmlhttp;
}
/**
 * 渲染字典
 * 
 * @param {}
 *            value
 * @param {}
 *            wordbook
 * @return {}
 */
function rendereredFromWordBook(value, wordbook) {
	var wbu = Ext.create('SystemConsole.wordbook.WordBookUtil', wordbook);
	return wbu.getWordBookName(value);
}
/**
 * 加载JS文件
 * 
 * @param {}
 *            url
 */
function loadScript(url) {
	var ga = document.createElement('script');
	ga.type = 'text/javascript';
	ga.async = false;
	ga.defer = false;
	ga.src = url;
	(document.getElementsByTagName('head')[0] || document
			.getElementsByTagName('body')[0]).appendChild(ga);
}

function loadScriptFile(url) {
	var xmlhttp = sendRequestObject(url,'GET');

	if (xmlhttp.status == 200) {
		IncludeJS(url, xmlhttp.responseText);
	} else if (xmlhttp.status == 404) {
		Ext.Msg.alert('提示', url + '并不存在');
	}
}

function IncludeJS(sId, source) {
	if (source != null) {
		var oHead = document.getElementsByTagName('HEAD').item(0);

		if (document.getElementById(sId)) {
			oHead.removeChild(document.getElementById(sId));
		}

		var oScript = document.createElement("script");

		oScript.language = "javascript";

		oScript.type = "text/javascript";

		oScript.id = sId;

		oScript.defer = true;

		oScript.text = source;

		oHead.appendChild(oScript);
	}
}
/**
 * js方法是否可执行
 * 
 * @param {}
 *            name
 * @return {Boolean}
 */
function canExecuteFunction(functionName) {
	return eval('typeof ' + functionName + ' != \'undefined\'')
			&& eval(functionName + ' instanceof Function')
}
function formatProgressBar(v,m,record) {
	var progressBarTmp = getTplStr(v,record);
	return progressBarTmp;
}
function getTplStr(v,record) {
	var hrBudget = record.data.hrBudget*1;
	var rate = ((record.data.totalNormalH*1+record.data.totalOverH*1)/8)*100;
	if(hrBudget!=null&&hrBudget!=0)
		rate = (rate/hrBudget).toFixed(2);
	else
		rate=0;
	
	var bgColor = "orange";
	var borderColor = "#008000";
	return Ext.String
			.format(
					'<div>'
							+ '<div style="border:1px solid {0};height:15px;width:{1}px;margin:1px 0px 1px 0px;float:left;">'
							+ '<div style="float:left;background:{2};width:{3}%;height:14px;text-align:center;">{3}%<div></div></div>'
							+ '</div>' + '</div>', borderColor, (90), bgColor,
							rate);
}
function formatMoney(v) {
	return Ext.util.Format.currency(v, '￥');
}

function formatTime(v){
	return Ext.util.Format.date(new Date(parseInt(v)),'Y-m-d H:m:s');  
}

function patchZero(n) {
	var str = n;
	if (n < 10) {
		str = '0' + n;
	}

	return str;
}

function renderTips(value){
	return '<div data-qtip="'+value+'">'+value+'</div>';
}

function checkdate(start, end) {
	// 得到日期值并转化成日期格式，replace(/\-/g, "\/")是根据验证表达式把日期转化成长日期格式，这样

	// 再进行判断就好判断了
	var sDate = new Date(start.replace(/\-/g, "\/"));
	var eDate = new Date(end.replace(/\-/g, "\/"));
	if (sDate > eDate) {
		return false;
	}
	return true;
}