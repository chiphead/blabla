<%@ page language="java" contentType="text/html;charset=utf-8" %>
<!-- Modal-login -->
<div id="loginModal" class="modal customModal hide fade" tabindex="-1"
	role="dialog" aria-labelledby="loginModalLabel" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">
			×
		</button>
		<h3 id="loginModalLabel">
			用户登录
		</h3>
	</div>
	<form id="form_signin" name="form_signin" action="#" method="post">
		<div class="modal-body">
			<input name="emp_yst_id" type="text" maxlength="6"
				class="input-block-level" placeholder="一事通ID">
			<input name="emp_pwd" type="password" maxlength="20"
				class="input-block-level" placeholder="密码">
			<label class="checkbox">
				<input type="checkbox" value="remember-me" name="remember"
					checked="true">
				记住账号
			</label>
			<button class="btn btn-primary" type="submit">
				登录
			</button>
			<span id="login-info" class="text-error pull-right"></span>
		</div>
	</form>
</div>

<!-- Modal-register -->
<div id="registerModal" class="modal customModal hide fade"
	tabindex="-1" role="dialog" aria-labelledby="registerModalLabel"
	aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">
			×
		</button>
		<h3 id="registerModalLabel">
			用户注册
		</h3>
	</div>
	<form id="form_register" name="form_register" action="#" method="post">
		<div id="reg-succ" class="hide text-center text-success">
			<h3>
				注册成功
			</h3>
		</div>
		<div id="register-modal-body" class="modal-body">
			<input name="emp_yst_id" type="text" maxlength="6"
				class="input-block-level" placeholder="输入一事通ID">
			<input name="emp_name" type="text" maxlength="10"
				class="input-block-level" placeholder="输入姓名">
			<input name="emp_pwd" type="password" maxlength="20"
				class="input-block-level" placeholder="输入密码">
			<input name="emp_pwd2" type="password" maxlength="20"
				class="input-block-level" placeholder="再次输入密码">
			<button class="btn btn-primary" type="submit">
				注册
			</button>
			<span id="register-info" class="text-error pull-right"></span>
		</div>
	</form>
</div>

<!-- Modal-add-person -->
<div id="addPersonModal" class="modal customModal hide fade"
	tabindex="-1" role="dialog" aria-labelledby="addPerson"
	aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">
			×
		</button>
		<h3 id="addPersonModalLabel">
			添加人员
		</h3>
	</div>
	<div class="modal-body"><div id="person-selects" class="row-fluid"></div></div>
	<div class="modal-footer">
		<button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
		<button id="add-person-ok" class="btn btn-primary">确定</button>
	</div>
</div>

<!-- Modal-info -->
<div id="infoModal" class="modal customModal hide fade"
	tabindex="-1" role="dialog" aria-labelledby="infoModalLabel"
	aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">
			×
		</button>
		<h3 id="infoModalLabel">
			提示
		</h3>
	</div>
	<div class="modal-body"><span id="glob-info" class="text-center text-error"><h3>测试</h3></span></div>
	<div class="modal-footer"></div>
</div>

<div id="codeModal" class="modal customModal hide fade"
	tabindex="-1" role="dialog" aria-labelledby="infoModalLabel"
	aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">
			×
		</button>
		<h3 id="infoModalLabel">
			示例
		</h3>
	</div>
	<div class="modal-body" id="code_div">
		<pre class="brush: rpgle;">
			需要修改的源代码库、源代码文件、程序名、目标库列表：SSPSRC/SSTRXPUB,SSCHKRNGR-> SSPEXC
			1、将程序名SSCHKRNGR中的#Init子过程的第1602.00～1605.00行的以下信息注释：
			C*                   Call      'SSBINCHKR'
			C*                   Parm                    iSpInf
			C*                   Parm                    wBinErrCod        7
			C*                   Parm                    wBinTblDs
			2、在程序名SSCHKRNGR的#Init子过程的第1605.00行后增加以下内容：
			C                   MoveL     iSpInf       wSpInf
			C                   Call      'SSBINCHKR'
			C                   Parm                    wSpInf
			C                   Parm                    wBinErrCod        7
			C                   Parm                    wBinTblDs
		</pre>
	</div>
	<div class="modal-footer"></div>
</div>
