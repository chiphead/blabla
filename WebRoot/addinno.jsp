<%@ page language="java" contentType="text/html;charset=utf-8" %>
<%@ include file="header.jsp"%>
<div id="add-inno-div" class="well">
	<form id="add-inno-form" name="add_inno_form" action="#" method="post">
		<div class="row-fluid" style="display:none;">
			<div class="span6">编　　号　
				<input type="text" id="inno_no" name="inno_no" readonly/>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span4">
			提出日期　
				<div id="reg_date" class="input-append date"
				 data-date-format="yyyy/mm/dd" >
					<input name="reg_date" size="10" class="span10" type="text"
						 readonly>
					<span class="add-on"><i class="icon-th"></i> </span>
				</div>
			</div>
			类型　
			<select id="inno_type" name="inno_type">
				<option>
					系统设计
				</option>
				<option>
					编码实现
				</option>
				<option>
					系统稳定性
				</option>
				<option>
					系统性能
				</option>
				<option>
					业务成功率
				</option>
				<option>
					客户体验
				</option>
				<option>
					管理
				</option>
				<option>
					其它
				</option>
			</select>
		</div>
		<div class="row-fluid">
			<div class="span10">
				提出人员　
				<div id="proposers" class="edit-div input-prepend"></div>
				<a href="#addPersonModal" id="proposers-add" class="btn person-add-btn" data-toggle="modal"><i class="icon-user"></i> 选择</a>
			</div>
		</div>		
		<div class="row-fluid">
			<div class="span10">
				实现人员　
				<div id="executors" class="edit-div input-prepend"></div>
				<a href="#addPersonModal" id="executors-add" class="btn person-add-btn" data-toggle="modal"><i class="icon-user"></i> 选择</a>
			</div>
		</div>
		<div class="row-fluid">
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
			<div class="span6">
				功能模块　
				<!-- <select id="module" name="module">
					<option>通讯</option>
					<option>交易</option>
					<option>清算</option>
					<option>对账</option>
					<option>调账</option>
				</select> -->
				<input type="text" id="module" name="module" />
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				优化建议　
				<textarea id="suggestion" name="suggestion" class="span10" rows="4"></textarea>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				优化理由　
				<textarea id="reason" name="reason" class="span10" rows="3"></textarea>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				补充意见　
				<textarea id="add_suggestion" name="add_suggestion" class="span10" rows="3"></textarea>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				实施效果　
				<textarea id="effect" name="effect" class="span10" rows="3"></textarea>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				结　　论　
				<textarea id="conclusion" name="conclusion" class="span10" rows="3"></textarea>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span4">
				<button type="submit" class="btn btn-primary">
					提交
				</button>
			</div>
			<div class="span8">
				<span id="addinno-info" class="text-error"></span>
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
		$('#add-inno-div').html('<h3>未登录</h3>');
		$('#loginModal').modal('show');
		return;
	}
	
	proposers = '';
	executors = '';
	cur_add_modal = '';
	
	inno_no = $_GET['inno_no'];
	if (inno_no != null && inno_no != '') {
		$('#top-container').html(
			'<h4>创新项目修改</h4>');
		if (inno_no == 'null') {
			$('#add-inno-div').html('<h3>请从创新登记表选择项目进行修改</h3>');
			return;
		}
		
		$.post('getinno', {
			inno_no : inno_no,
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
				console.log(jsonObj);
				var form = document.add_inno_form;
				form.inno_no.value = jsonObj.inno_no;
				form.reg_date.value = jsonObj.reg_date;
				form.inno_type.selectedIndex = jsonObj.inno_type - 1;
				proposers = jsonObj.proposers;			
				form.system.selectedIndex = jsonObj.system - 1;
				form.module.value = jsonObj.module;
				form.suggestion.value = jsonObj.suggestion;
				form.reason.value = jsonObj.reason;
				form.add_suggestion.value = jsonObj.add_suggestion;
				form.conclusion.value = jsonObj.conclusion;
				executors = jsonObj.executors;
				form.effect.value = jsonObj.effect;
				
				var proposersJson = JSON.parse(proposers);
				var executorsJson = JSON.parse(executors);
				
				var slctedhtml = '';
				for (var i = 0; i < proposersJson.length; i++) {
					slctedhtml += '<span class="select-tag">' + proposersJson[i].emp_name + '</span>';
				}
				$('#proposers').html(slctedhtml);
				
				slctedhtml = '';
				for (var i = 0; i < executorsJson.length; i++) {
					slctedhtml += '<span class="select-tag">' + executorsJson[i].emp_name + '</span>';
				}
				$('#executors').html(slctedhtml);
			}
		});
	} else {
		$('#add-inno-li').addClass('active');
		$('#top-container').html('<h4>创新项目添加</h4>');		
	}
	$('#add-inno-form').submit(function() {
		var form = document.add_inno_form;
		var reg_date = form.reg_date.value;
		var inno_type = form.inno_type.selectedIndex + 1;
		
		var system = form.system.selectedIndex + 1;
		var module = form.module.value;
		var suggestion = form.suggestion.value;
		var reason = form.reason.value;
		var add_suggestion = form.add_suggestion.value;
		var conclusion = form.conclusion.value;
		var effect = form.effect.value;
		
		if (reg_date == '') {
			$('#addinno-info').text(
					'　　　　请选择提出日期');
			return false;
		} else if (proposers == '') {
			$('#addinno-info').text(
					'　　　　请选择提出人员');
			return false;
		} else if (module == '') {
			$('#addinno-info').text(
					'　　　　请填写功能模块');
			return false;
		} else if (suggestion == '') {
			$('#addinno-info').text(
					'　　　　请填写优化建议');
			return false;
		} else if (reason == '') {
			$('#addinno-info').text(
					'　　　　请填写优化理由');
			return false;
		} else {
			$('#addinno-info').text('');
		}
		
		var servlet = 'reginno';
		if (form.inno_no.value != '') {
			servlet = 'chginno';
		}
		$.post(servlet, {
			emp_yst_id : getCookie('emp_yst_id'),
			emp_pwd : getCookie('emp_pwd'),
			inno_no : inno_no == null ? '' : inno_no,
			reg_date : reg_date,
			inno_type : inno_type,
			proposers : proposers,
			system : system,
			module : module,
			suggestion : suggestion,
			reason : reason,
			add_suggestion : add_suggestion,
			conclusion : conclusion,
			executors : executors,
			effect : effect
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
		$('#addinno-info').text('');
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
		if (cur_add_modal == 'proposers-add') {
			$('#proposers').html(slctedhtml);
			proposers = jsonYstid;
		} else if (cur_add_modal == 'executors-add') {
			$('#executors').html(slctedhtml);
			executors = jsonYstid;
		}
		
		$('#addPersonModal').modal('hide');
	});
});
</script>

<%@ include file="modals.jsp"%>
<%@ include file="footer.jsp"%>