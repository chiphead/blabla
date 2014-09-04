<%@ page language="java" contentType="text/html;charset=utf-8" %>
<%@ include file="header.jsp"%>
<div id="add-emer-div" class="well">
	<form id="add-emer-form" name="add_emer_form" action="#" method="post">
		<div class="row-fluid" style="display:none;">
			<div class="span6">编　　号　
				<input type="text" id="emer_no" name="emer_no" readonly/>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span4">
				事件日期　
				<div id="emer_date" class="input-append date"
				 data-date-format="yyyy/mm/dd" >
					<input name="emer_date" size="10" class="span10" type="text"
						 readonly>
					<span class="add-on"><i class="icon-th"></i> </span>
				</div>
			</div>
			<div class="span8">
				事件等级　
				<select id="level" name="level">
					<option>
						无
					</option>
					<option>
						P1
					</option>
					<option>
						P2
					</option>
					<option>
						P3
					</option>
					<option>
						P4
					</option>
					<option>
						P5
					</option>
				</select>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span10">
				事件内容　
				<textarea id="emer_content" name="emer_content" class="span10" rows="4"></textarea>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span10">
				影响业务　
				<textarea id="influence" name="influence" class="span10" rows="2"></textarea>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span10">
				问题分析　
				<textarea id="analytics" name="analytics" class="span10" rows="3"></textarea>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				代码修改　
				<textarea id="code" name="before_code" class="span10" rows="12"></textarea>
				<button id="example-btn" class="btn">示例</button>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span10">
				维护人员　
				<div id="operators" class="edit-div input-prepend"></div>
				<a href="#addPersonModal" id="operators-add" class="btn person-add-btn" data-toggle="modal"><i class="icon-user"></i> 选择</a>
			</div>
		</div>		
		<div class="row-fluid">
			<div class="span10">
				责任人　　
				<div id="responsible" class="edit-div input-prepend"></div>
				<a href="#addPersonModal" id="responsible-add" class="btn person-add-btn" data-toggle="modal"><i class="icon-user"></i> 选择</a>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				项目编号　
				<input type="text" id="proj_no" name="proj_no" class="custom-center" size="10" />
				<button id="search_prj" class="btn">查找</button>
			</div>
			<div class="span6">
				项目名称　
				<input type="text" id="proj_name" name="proj_name" class="custom-center" disabled />
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				项目负责人
				<input type="text" id="proj_manager" name="proj_manager" class="custom-center" disabled />
			</div>
			<div class="span6">
				所属系统　
				<select id="system" name="system">
					<option>LG50－收单平台</option>
					<option>LG51－交换平台</option>
					<option>LG52－信用卡收单平台</option>
					<option>LH53－信用卡柜台及390转400</option>
					<option>LU57－收付易平台</option>
					<option>LU47-结算系统</option>
				</select>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span10">
				备　　注　
				<textarea id="remark" name="remark" class="span10" rows="3"></textarea>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span4">
				<button type="submit" class="btn btn-primary">
					提交
				</button>
			</div>
			<div class="span8">
				<span id="addemer-info" class="text-error"></span>
			</div>
		</div>
	</form>
	<div id="add-info" class="text-center"></div>
</div>
<script type="text/javascript" src="js/bootstrap-datepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="js/locales/bootstrap-datepicker.zh-CN.js" charset="UTF-8"></script>

<script type="text/javascript">
$(document).ready(function() {
	$('#index-li').addClass('active');
	if (getCookie('emp_yst_id') == '') {
		$('#add-emer-div').html('<h3>未登录</h3>');
		$('#loginModal').modal('show');
		return;
	}
	
	operators = '';
	responsible = '';
	cur_add_modal = '';
	
	emer_no = $_GET['emer_no'];
	if (emer_no != null && emer_no != '') {
		$('#top-container').html(
			'<h4>特急登记项修改</h4>');
		if (emer_no == 'null') {
			$('#add-emer-div').html('<h3>请从特急登记表选择项目进行修改</h3>');
			return;
		}
		
		$.post('getemer', {
			emer_no : emer_no,
			emp_yst_id : getCookie('emp_yst_id'),
			emp_pwd : getCookie('emp_pwd')
		}, function(data){
			if (data == 'noauth' || data == 'notlogged') {
				$('#glob-info').html('<h3>没有权限</h3>');
				$('#infoModal').modal('show');
				setTimeout(function() {
					$('#infoModal').modal('hide');
					$('#glob-info').empty().removeClass('text-success').addClass('text-error');
				}, 1200);
			} else {
				var jsonObj = JSON.parse(data);
				var form = document.add_emer_form;
				form.emer_no.value = jsonObj.emer_no;
				//form.reg_date.value = jsonObj.reg_date;
				form.emer_date.value = jsonObj.emer_date;
				form.emer_content.value = jsonObj.emer_content;
				form.influence.value = jsonObj.influence;
				form.analytics.value = jsonObj.analytics;
				form.code.value = jsonObj.code;
				form.level.selectedIndex = jsonObj.level - 1;
				operators = jsonObj.operators;
				form.proj_no.value = jsonObj.proj_no;
				form.proj_name.value = jsonObj.proj_name;
				form.proj_manager.value = jsonObj.proj_manager;
				responsible = jsonObj.responsible;
				form.system.selectedIndex = jsonObj.system - 1;
				form.remark.value = jsonObj.remark;
				
				var operatorsJson = JSON.parse(operators);
				var responsibleJson = JSON.parse(responsible);
				
				var slctedhtml = '';
				for (var i = 0; i < operatorsJson.length; i++) {
					slctedhtml += '<span class="select-tag">' + operatorsJson[i].emp_name + '</span>';
				}
				$('#operators').html(slctedhtml);
				
				slctedhtml = '';
				for (var i = 0; i < responsibleJson.length; i++) {
					slctedhtml += '<span class="select-tag">' + responsibleJson[i].emp_name + '</span>';
				}
				$('#responsible').html(slctedhtml);
			}
		});
	} else {
		$('#add-emer-li').addClass('active');
		$('#top-container').html('<h4>特急修改登记</h4>');		
	}
	$('#add-emer-form').submit(function() {
		var form = document.add_emer_form;
		var emer_no = form.emer_no.value;
		//var reg_date = form.reg_date.value;
		var emer_date = form.emer_date.value;
		var emer_content = form.emer_content.value;
		var influence = form.influence.value;
		var analytics = form.analytics.value;
		var code = form.code.value;
		var level = form.level.selectedIndex + 1;
		var proj_no = form.proj_no.value;
		var proj_name = form.proj_name.value;
		var proj_manager = form.proj_manager.value;
		var system = form.system.selectedIndex + 1;
		var remark = form.remark.value;
		
		if (emer_date == '') {
			$('#addemer-info').text(
					'　　　　请选择事件日期');
			return false;
		} else if (emer_content == '') {
			$('#addemer-info').text(
					'　　　　请填写事件内容');
			return false;
		} else if (influence == '') {
			$('#addemer-info').text(
					'　　　　请填写影响业务');
			return false;
		} else if (analytics == '') {
			$('#addemer-info').text(
					'　　　　请填写问题分析');
			return false;
		}  else if (code == '') {
			$('#addemer-info').text(
					'　　　　请填写代码修改');
			return false;
		}  else if (proj_no == '') {
			$('#addemer-info').text(
					'　　　　请填写项目编号');
			return false;
		} else if (operators == '') {
			$('#addemer-info').text(
					'　　　　请选择维护人员');
			return false;
		} else if (responsible == '') {
			$('#addemer-info').text(
					'　　　　请选择责任人');
			return false;
		} else {
			$('#addemer-info').text('');
		}
		
		var servlet = 'regemer';
		if (form.emer_no.value != '') {
			servlet = 'chgemer';
		}
		$.post(servlet, {
			emp_yst_id : getCookie('emp_yst_id'),
			emp_pwd : getCookie('emp_pwd'),
			emer_no : emer_no == null ? '' : emer_no,
			//reg_date : reg_date,
			emer_date : emer_date,
			emer_content : emer_content,
			influence : influence,
			analytics : analytics,
			code : code,
			operators : operators,
			responsible : responsible,
			level : level,
			proj_no : proj_no,
			proj_name : proj_name,
			proj_manager : proj_manager,
			system : system,
			remark : remark
		},
		function(data) {
			if (data == 'true') {
				$('#add-info').html('<h3>操作成功</h3>').addClass('text-success');
			} else if (data == 'false') {
				$('#add-info').html('<h3>操作失败</h3>').addClass('text-error');
			} else if (data == 'noauth') {
				$('#add-info').html('<h3>没有权限</h3>').addClass('text-error');
			} else if (data == 'notlogged') {
				$('#glob-info').html('<h3>请先登录</h3>');
			}
			setTimeout(function() {
				$('#add-info').html('');
			}, 1500);
		});
		return false;
	});
	$('input,textarea').focus(function() {
		$('#addemer-info').text('');
	});
	
	$('.date').datepicker({
		autoclose: true,
		clearBtn: true,
		language: 'zh-CN',
		todayBtn: 'linked'
	});
	
	$('.person-add-btn').click(function() {
		cur_add_modal = $(this).attr('id');
		var content = '';
		var jsonObj;
		$.post('getperson', function(data) {
			jsonObj = JSON.parse(data);
			for (var i = 0; jsonObj.rows[i] != null; i++) {
				content += '<div class="span4"><input type="checkbox" name="person-checkbox" value="' + jsonObj.rows[i].emp_yst_id + '"/><span>' + jsonObj.rows[i].emp_name + '</span></div>';
			}
			$('#person-selects').html(content);
		});
	});
	
	$('#add-person-ok').click(function() {
		var chkboxs = document.getElementsByName('person-checkbox');
		var slctedhtml = '';
		var jsonYstid = '[';
		var chkedNum = 0;
		if(!isNaN(chkboxs.length)) {
			for(var i = 0; i < chkboxs.length; i++) {
				if(chkboxs[i].checked) {
					chkedNum++;
					slctedhtml += '<span class="select-tag">' + $(chkboxs[i]).next().text() + '</span>';
					jsonYstid += '{"emp_id":0, "emp_yst_id":' + chkboxs[i].value + ', "emp_name":"' + $(chkboxs[i]).next().text() + '", "emp_pwd":"", "emp_aut":2},';
				}
			}
			jsonYstid = jsonYstid.substr(0, jsonYstid.length - 1);
			jsonYstid += ']';
		}
		if (chkedNum == 0) {
			jsonYstid = '';
		}
		if (cur_add_modal == 'operators-add') {
			$('#operators').html(slctedhtml);
			operators = jsonYstid;
		} else if (cur_add_modal == 'responsible-add') {
			$('#responsible').html(slctedhtml);
			responsible = jsonYstid;
		}
		
		$('#addPersonModal').modal('hide');
	});
	
	$('#search_prj').click(function() {
		var form = document.add_emer_form;
		var proj_no = form.proj_no.value;
		if (proj_no == '') {
			$('#addemer-info').text(
					'　　　　请填写项目编号');
			return false;
		}
		$.post('getproj', {
			proj_no : proj_no,
			emp_yst_id : getCookie('emp_yst_id'),
			emp_pwd : getCookie('emp_pwd'),
			emp_name : getCookie('emp_name')
		}, function(data){
			if (data == 'noauth' || data == 'notlogged') {
				$('#glob-info').html('<h3>没有权限</h3>');
				$('#infoModal').modal('show');
				setTimeout(function() {
					$('#infoModal').modal('hide');
					$('#glob-info').empty().removeClass('text-success').addClass('text-error');
				}, 1200);
			} else if(data == 'false') {
				$('#addemer-info').text(
					'　　　　找不到该项目');
				form.proj_no.value = '';
				form.proj_name.value = '';
				form.proj_manager.value = '';
			} else {
				var jsonObj = JSON.parse(data);
				form.proj_name.value = jsonObj.proj_name;
				form.proj_manager.value = jsonObj.proj_manager;
			}
		});
		return false;
	});
	
	$('#proj_no').keyup(function(event){
		if (event.keyCode == 13) {
			// $('#search_prj').focus();
			$('#search_prj').click();
		}
		return false;
	});
	
	$('#example-btn').click(function() {
		$('#codeModal').modal('show');
		return false;
	});
});
</script>

<script type="text/javascript" src="js/shCore.js"></script>
<script type="text/javascript" src="js/shBrushRpgle.js"></script>
<link type="text/css" rel="stylesheet" href="css/shCoreDefault.css"/>
<script type="text/javascript">SyntaxHighlighter.all();</script>

<%@ include file="modals.jsp"%>
<%@ include file="footer.jsp"%>