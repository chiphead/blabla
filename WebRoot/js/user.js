$(document).ready(
		function() {
			checkCookie();
			$('.tooltip-link').tooltip('hide');
			$('.customModal').draggable({
				handle: ".modal-header"
			});

			$('#form_signin').submit(function() {
				var emp_yst_id = document.form_signin.emp_yst_id.value;
				var emp_pwd = document.form_signin.emp_pwd.value;
				var remember = document.form_signin.remember.checked;
				$('#login-info').text('');
				if (isNaN(emp_yst_id) || emp_yst_id == '') {
					$('#login-info').text('一事通ID输入错误');
					return false;
				} else if (emp_pwd == '') {
					$('#login-info').text('请输入密码');
					return false;
				}
				$.post("login", {
					emp_yst_id : emp_yst_id,
					emp_pwd : emp_pwd,
					remember : remember
				}, function(data) {
					if (data == 1 || data == 2 || data == 3) {
						$('#div-login').hide();
						$('#div-name').show();
						$('#loginModal').modal('hide');
						$('#logged-user').html(getCookie('emp_yst_id') + '/' + getCookie('emp_name'));
						window.location.reload();
					} else {
						$('#login-info').text('登录失败');
					}
				});
				return false;
			});
			$('#signout').click(function() {
				setCookie('emp_yst_id', '', 0);
				setCookie('emp_name', '', 0);
				setCookie('emp_pwd', '', 0);
				$('#div-name').hide();
				$('#div-login').show();
				$('#reg-succ').hide();
				$('#register-modal-body').show();
			});

			$('#form_register').submit(function() {
				var emp_yst_id = document.form_register.emp_yst_id.value;
				var emp_name = document.form_register.emp_name.value;
				var emp_pwd = document.form_register.emp_pwd.value;
				var emp_pwd2 = document.form_register.emp_pwd2.value;
				$('#register-info').text('');
				if (isNaN(emp_yst_id) || emp_yst_id == '') {
					$('#login-info').text('一事通ID输入错误');
					return false;
				}
				if (emp_pwd != emp_pwd2) {
					$('#register-info').text('两次密码输入不一致');
					return false;
				} else if (emp_pwd == '') {
					$('#login-info').text('请输入密码');
					return false;
				}

				$.post("register", {
					emp_yst_id : emp_yst_id,
					emp_name : emp_name,
					emp_pwd : emp_pwd
				}, function(data) {
					if (data == 'true') {
						$('#register-modal-body').hide();
						$('#reg-succ').show();
						setTimeout(function() {
							$('#registerModal').modal('hide');
							$('#div-login').hide();
							$('#div-name').show();
							$('#logged-user').html(getCookie('emp_yst_id') + '/' + getCookie('emp_name'));
						}, 600);
					} else {
						$('#register-info').text('注册失败');
					}
				});
				return false;
			});
			
			$('.modal').on('show', function() {
			    $(this).css({
			        'margin-left': function () {
			        	return -($(this).width() / 2);
			        }
			    });
			});
		});