<%@ page language="java" contentType="text/html;charset=utf-8" %>
<%@ include file="header.jsp"%>
<div id="chgpwd-div" class="well">
	<form id="form_chgpwd" name="form_chgpwd" action="#" method="post">
		<input name="emp_yst_id" type="text" maxlength="6"
			class="input-block-level" disabled>
		<input name="emp_name" type="text" maxlength="10"
			class="input-block-level" disabled>
		<input name="emp_pwd1" type="password" maxlength="20"
			class="input-block-level" placeholder="输入新密码">
		<input name="emp_pwd2" type="password" maxlength="20"
			class="input-block-level" placeholder="确认新密码">
		<button class="btn btn-primary" type="submit">
			提交
		</button>
		<span id="chgpwd-info" class="text-error pull-right"></span>
	</form>
	<div id="chg-succ" class="hide text-center text-success">
		<h3>
			修改成功
		</h3>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function() {
	$('#account-li').addClass('active');
	//$('#top-container').html('<h2>账号设置</h2><p class="lead">修改密码</p>');
	$('#top-container').html('<h4>账号设置</h4>');
	$('#sidebar > ul').empty().html('<li class="nav-header">功能</li><li class="active"><a href="#">修改密码</a></li>');
	document.form_chgpwd.emp_pwd1.focus();
	
	var emp_yst_id = getCookie('emp_yst_id');
	var emp_name = getCookie('emp_name');
	var emp_pwd = getCookie('emp_pwd');
	if (emp_yst_id == '' || emp_name == '' || emp_pwd == '') {
		$('#loginModal').modal('show');
	} else {
		document.form_chgpwd.emp_yst_id.value = emp_yst_id;
		document.form_chgpwd.emp_name.value = emp_name;
	}
	$('#form_chgpwd').submit(function() {
		var emp_pwd1 = document.form_chgpwd.emp_pwd1.value;
		var emp_pwd2 = document.form_chgpwd.emp_pwd2.value;
		if (emp_yst_id == '' || emp_name == '') {
			$('#form_chgpwd > #chgpwd-info').text('请先登录');
			return false;
		} else if (emp_pwd1 != emp_pwd2) {
			$('#form_chgpwd > #chgpwd-info').text('密码输入不一致');
			return false;
		} else if (emp_pwd1 == '') {
			$('#form_chgpwd > #chgpwd-info').text('请输入密码');
			return false;
		}

		$.post("chgpwd", {
			emp_yst_id : emp_yst_id,
			emp_name : emp_name,
			emp_pwd : emp_pwd1
		}, function(data) {
			document.form_chgpwd.emp_pwd1.value = '';
			document.form_chgpwd.emp_pwd2.value = '';
			if (data == 'true') {
				$('#form_chgpwd > #chgpwd-info').text('');
				$('#chg-succ').show();
				setTimeout(function() {
					$('#chg-succ').hide();
				}, 600);
			} else {
				$('#form_chgpwd > #chgpwd-info').text('修改失败');
			}
		});

		return false;
	});
});
</script>
<%@ include file="modals.jsp"%>
<%@ include file="footer.jsp"%>