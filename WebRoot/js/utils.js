function getCookie(c_name) {
	if (document.cookie.length > 0) {
		c_start = document.cookie.indexOf(c_name + "=");
		if (c_start != -1) {
			c_start = c_start + c_name.length + 1;
			c_end = document.cookie.indexOf(";", c_start);
			if (c_end == -1)
				c_end = document.cookie.length;
			return decodeURI(document.cookie.substring(c_start, c_end));
		}
	}
	return "";
}

function checkCookie() {
	var emp_yst_id = getCookie('emp_yst_id');
	var emp_pwd = getCookie('emp_pwd');
	if (emp_yst_id == '' || emp_pwd == '') {
		$('#div-login').show();
		return;
	}
	$.post("login", {
		emp_yst_id : emp_yst_id,
		emp_pwd : emp_pwd,
		remember : 'cookie'
	}, function(data) {
		if (data == 1 || data == 2 || data == 3) {
			$('#div-name').show();
			$('#logged-user').html(
					getCookie('emp_yst_id') + '/' + getCookie('emp_name'));
			$('#account-li').show();
		} else {
			setCookie('emp_yst_id', '', 0);
			setCookie('emp_name', '', 0);
			setCookie('emp_pwd', '', 0);
			
			$('#div-login').show();
		}
	});
}

function setCookie(c_name, value, expiredays) {
	var exdate = new Date();
	exdate.setDate(exdate.getDate() + expiredays);
	document.cookie = c_name + "=" + escape(value)
			+ ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())
}

function urlDecode(str) {
	var ret = "";
	for ( var i = 0; i < str.length; i++) {
		var chr = str.charAt(i);
		if (chr == "+") {
			ret += " ";
		} else if (chr == "%") {
			var asc = str.substring(i + 1, i + 3);
			if (parseInt("0x" + asc) > 0x7f) {
				ret += asc2str(parseInt("0x" + asc
						+ str.substring(i + 4, i + 6)));
				i += 5;
			} else {
				ret += asc2str(parseInt("0x" + asc));
				i += 2;
			}
		} else {
			ret += chr;
		}
	}
	return ret;
}

/*-----------------实现1--------------------*/
function getPar(par) {
	//获取当前URL
	var local_url = document.location.href;
	//获取要取得的get参数位置
	var get = local_url.indexOf(par + "=");
	if (get == -1) {
		return false;
	}
	//截取字符串
	var get_par = local_url.slice(par.length + get + 1);
	//判断截取后的字符串是否还有其他get参数
	var nextPar = get_par.indexOf("&");
	if (nextPar != -1) {
		get_par = get_par.slice(0, nextPar);
	}
	return get_par;
}

/*--------------------实现2(返回 $_GET 对象, 仿PHP模式)----------------------*/
var $_GET = (function() {
	var url = window.document.location.href.toString();
	var u = url.split("?");
	if (typeof (u[1]) == "string") {
		u = u[1].split("&");
		var get = {};
		for ( var i in u) {
			var j = u[i].split("=");
			get[j[0]] = j[1];
		}
		return get;
	} else {
		return {};
	}
})();

/*第2种方式, 使用时, 可以直接 $_GET['get参数'], 就直接获得GET参数的值*/