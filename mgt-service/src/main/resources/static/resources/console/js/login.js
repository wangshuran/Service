$(function(){  
	$("input[name=username]").focus(); 
});  

function checkForm(form) {
	var account = form.username.value;
	if (account == "") {
		alert("登录名必填");
		form.username.focus();
		return false;
	}
	if (form.password.value == "") {
		alert("登录密码必填");
		form.password.focus();
		return false;
	}
	if (form.verifyCode.value == "") {
		alert("验证码必填");
		form.verifyCode.focus();
		return false;
	}

	return true;
}