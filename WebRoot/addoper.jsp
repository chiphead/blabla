<%@ page language="java" contentType="text/html;charset=utf-8" %>
<%@ include file="header.jsp"%>
<div id="add-oper-div" class="well">
	<form id="add-oper-form" name="add_oper_form" action="#" method="post">
		<div class="row-fluid" style="display:none;">
			<div class="span6">编　　号　
				<input type="text" id="oper_no" name="oper_no" readonly/>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span4">
				开始日期　
				<div id="oper_start_date" class="input-append date"
				 data-date-format="yyyy/mm/dd" >
					<input name="oper_start_date" size="10" class="span10" type="text"
						 readonly>
					<span class="add-on"><i class="icon-th"></i> </span>
				</div>
			</div>
			<div class="span8">
				开始时间　
				<div class="input-append bootstrap-timepicker">
		            <input id="oper_start_time" name="oper_start_time" type="text" class="input-small timepicker">
		            <span class="add-on"><i class="icon-time"></i></span>
		        </div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span4">
				结束日期　
				<div id="oper_end_date" class="input-append date"
				 data-date-format="yyyy/mm/dd" >
					<input name="oper_end_date" size="10" class="span10" type="text"
						 readonly>
					<span class="add-on"><i class="icon-th"></i> </span>
				</div>
			</div>
			<div class="span8">
				结束时间　
				<div class="input-append bootstrap-timepicker">
		            <input id="oper_end_time" name="oper_end_time" type="text" class="input-small timepicker">
		            <span class="add-on"><i class="icon-time"></i></span>
		        </div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span10">
				运维人员　
				<div id="operators" class="edit-div input-prepend"></div>
				<a href="#addPersonModal" id="operators-add" class="btn person-add-btn" data-toggle="modal"><i class="icon-user"></i> 选择</a>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				所属人员　
<!-- 				<div id="belong_persons" class="edit-div input-prepend"></div>
				<a href="#addPersonModal" id="belong-persons-add" class="btn person-add-btn" data-toggle="modal"><i class="icon-user"></i> 选择</a>
				-->
				<input type="text" id="belong_persons" name="belong_persons"/>
			</div>
			<div class="span6">
				所属系统　
				<input type="text" id="system" name="system"/>
				<!-- <select id="system" name="system">
					<option>LG50－收单平台</option>
					<option>LG51－交换平台</option>
					<option>LG52－信用卡收单平台</option>
					<option>LH53－信用卡柜台及390转400</option>
					<option>LU57－收付易平台</option>
				</select> -->
			</div>
		</div>
		<!-- <div class="row-fluid">
				
			
			<div class="span8">
				所属模块　
				<select id="module" name="module">
					<option>通讯</option>
					<option>交易</option>
					<option>清算</option>
					<option>对账</option>
					<option>调账</option>
				</select>
			</div>
		</div> -->
		<div class="row-fluid">
			<div class="span10">影响业务　
				<input type="text" id="affected_business" name="affected_business"/>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				事件类型　
				<select id="oper_type" name="oper_type">
					<option>
						紧急运维
					</option>
					<option>
						电话报障
					</option>
					<option>
						项目上线
					</option>
					<option>
						现场值班
					</option>
					<option>
						电话值班
					</option>
					<option>
						其他
					</option>
				</select>
			</div>
			<div class="span6">
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
			<div class="span6">
				事件内容　
				<textarea id="operation" name="operation" class="span10" rows="4"></textarea>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				问题分析　
				<textarea id="reason" name="reason" class="span10" rows="3"></textarea>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				改进建议　
				<textarea id="suggestion" name="suggestion" class="span10" rows="3"></textarea>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
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
				<span id="addoper-info" class="text-error"></span>
			</div>
		</div>
	</form>
	<div id="add-info" class="text-center"></div>
</div>
<script type="text/javascript" src="js/bootstrap-datepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="js/bootstrap-timepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="js/locales/bootstrap-datepicker.zh-CN.js" charset="UTF-8"></script>

<script type="text/javascript">
$(document).ready(function() {
	$('#index-li').addClass('active');
	if (getCookie('emp_yst_id') == '') {
		$('#add-oper-div').html('<h3>未登录</h3>');
		$('#loginModal').modal('show');
		return;
	}
	
	cur_add_modal = '';
	operators = '';
	// belong_persons = '';
	
	$('.timepicker').timepicker({
        /*appendWidgetTo: 'body',
        showMeridian: false,
        defaultTime: 'current'*/
        minuteStep: 5,
		showSeconds: false,
		showMeridian: false
    });
    //.on('changeTime.timepicker', function(e) {		
	//})
	
	oper_no = $_GET['oper_no'];
	if (oper_no != null && oper_no != '') {
		$('#top-container').html('<h4>运维项目修改</h4>');
		if (oper_no == 'null') {
			$('#add-oper-div').html('<h3>请从运维登记表选择项目进行修改</h3>');
			return;
		}
		
		$.post('getoper', {
			oper_no : oper_no,
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
				var form = document.add_oper_form;
				form.oper_no.value = jsonObj.oper_no;
				form.oper_start_date.value = jsonObj.oper_start_date;
				form.oper_start_time.value = jsonObj.oper_start_time;
				form.oper_end_date.value = jsonObj.oper_end_date;
				form.oper_end_time.value = jsonObj.oper_end_time;
				
				form.belong_persons.value = jsonObj.belong_persons;
				// form.system.selectedIndex = jsonObj.system - 1;
				form.system.value = jsonObj.system;
				// form.module.selectedIndex = jsonObj.module - 1;
				form.oper_type.selectedIndex = jsonObj.oper_type - 1;
				form.operation.value = jsonObj.operation;
				form.level.selectedIndex= jsonObj.level - 1;
				form.affected_business.value = jsonObj.affected_business;
				form.remark.value = jsonObj.remark;
				form.reason.value = jsonObj.reason;
				form.suggestion.value = jsonObj.suggestion;
				
				var operatorsJson = JSON.parse(jsonObj.operators);
				operators = jsonObj.operators;
				// var belongPersonsJson = JSON.parse(jsonObj.belong_persons);
				// belong_persons = jsonObj.belong_persons;
				
				var slctedhtml = '';
				for (var i = 0; i < operatorsJson.length; i++) {
					slctedhtml += '<span class="select-tag">' + operatorsJson[i].emp_name + '</span>';
				}
				$('#operators').html(slctedhtml);
				
				// slctedhtml = '';
				// for (var i = 0; i < belongPersonsJson.length; i++) {
					// slctedhtml += '<span class="select-tag">' + belongPersonsJson[i].emp_name + '</span>';
				// }
				// $('#belong_persons').html(slctedhtml);
			}
		});
	} else {
		$('#add-oper-li').addClass('active');
		$('#top-container').html('<h4>运维项目添加</h4>');		
	}
	$('#add-oper-form').submit(function() {
		var form = document.add_oper_form;
		var oper_start_date = form.oper_start_date.value;
		var oper_start_time = form.oper_start_time.value;
		var oper_end_date = form.oper_end_date.value == null ? '' : form.oper_end_date.value;
		var oper_end_time = form.oper_end_time.value == null ? '' : form.oper_end_time.value;
		
		// var operators = form.operators.value;
		var belong_persons = form.belong_persons.value;
		// var system = form.system.selectedIndex + 1;
		var system = form.system.value;
		// var module = form.module.selectedIndex + 1;
		var oper_type = form.oper_type.selectedIndex + 1;
		var operation = form.operation.value;
		var level = form.level.selectedIndex + 1;
		var affected_business = form.affected_business.value;
		var reason = form.reason.value;
		var suggestion = form.suggestion.value;
		var remark = form.remark.value;
		
		if (oper_start_date == '') {
			$('#addoper-info').text(
					'　　　　请选择开始日期');
			return false;
		} else if (oper_start_time == '') {
			$('#addoper-info').text(
					'　　　　请选择开始时间');
			return false;
		} else if (oper_end_date == '') {
			$('#addoper-info').text(
					'　　　　请选择结束日期');
			return false;
		}  else if (oper_end_time == '') {
			$('#addoper-info').text(
					'　　　　请选择结束时间');
			return false;
		} else if (operators == '') {
			$('#addoper-info').text(
					'　　　　请选择运维人员');
			return false;
		} else if (belong_persons == '') {
			$('#addoper-info').text(
					'　　　　请填写所属人员');
			return false;
		} else if (affected_business == '') {
			$('#addoper-info').text(
					'　　　　请输入影响业务');
			return false;
		} else if (operation == '') {
			$('#addoper-info').text(
					'　　　　请输入事件内容');
			return false;
		} else {
			$('#addoper-info').text('');
		}
		
		var servlet = 'regoper';
		if (form.oper_no.value != '') {
			servlet = 'chgoper';
		}
		$.post(servlet, {
			emp_yst_id : getCookie('emp_yst_id'),
			emp_pwd : getCookie('emp_pwd'),
			oper_no : oper_no == null ? '' : oper_no,
			oper_start_date : oper_start_date,
			oper_start_time : oper_start_time,
			oper_end_date : oper_end_date,
			oper_end_time : oper_end_time,
			level : level,
			operators : operators,
			belong_persons : belong_persons,
			system : system,
			// module : module,
			affected_business : affected_business,
			oper_type : oper_type,
			operation : operation,
			reason : reason,
			suggestion : suggestion,
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
		$('#addoper-info').text('');
	});
	
	$('.date').datepicker({
		autoclose: true,
		clearBtn: true,
		language: 'zh-CN',
		todayBtn: 'linked'
	}).on('changeDate', function(ev) {
		startDate = new Date(document.add_oper_form.oper_start_date.value);
		endDate = new Date(document.add_oper_form.oper_end_date.value);
		
		if (startDate.valueOf() > endDate.valueOf()) {
			$('#oper_end_date').datepicker('update', document.add_oper_form.oper_start_date.value);
		}		
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
		if(cur_add_modal == 'operators-add') {
			$('#operators').html(slctedhtml);
			operators = jsonYstid;
		} else if(cur_add_modal == 'belong-persons-add') {
			// $('#belong_persons').html(slctedhtml);
			// belong_persons = jsonYstid;
		}
		cur_add_modal = '';
		$('#addPersonModal').modal('hide');
	});
});
</script>

<%@ include file="modals.jsp"%>
<%@ include file="footer.jsp"%>