<%@ page language="java" contentType="text/html;charset=utf-8" %>
<%@ include file="header.jsp"%>
<div id="view-emer-div" class="well">
<!-- 	<p id="emer_no" style="display: none;">编　　号</p> -->
	<p id="emer_date"><strong>事件日期　　</strong></p>
	<p id="level"><strong>事件等级　　</strong></p>
	<p id="emer_content"><strong>事件内容　　</strong></p>	
	<p id="influence"><strong>影响业务　　</strong></p>
	<p id="analytics"><strong>问题分析　　</strong></p>
	<p id="code"><strong>代码修改　　</strong></p>
	<p id="operators"><strong>维护人员　　</strong></p>	
	<p id="responsible"><strong>责任人　　　</strong></p>
	<p id="proj_no"><strong>项目编号　　</strong></p>
	<p id="proj_name"><strong>项目名称　　</strong></p>
	<p id="proj_manager"><strong>项目负责人　</strong></p>
	<p id="system"><strong>所属系统　　</strong></p>
	<p id="remark"><strong>备　　注　　</strong></p>
</div>

<script type="text/javascript">
$(document).ready(function() {
	$('#index-li').addClass('active');
	if (getCookie('emp_yst_id') == '') {
		$('#view-emer-div').html('<h3>未登录</h3>');
		$('#loginModal').modal('show');
		return;
	}
	
	operators = '';
	responsible = '';
	
	emer_no = $_GET['emer_no'];
	$('#top-container').html(
		'<h4>特急登记项查看</h4>');
	if (emer_no == null || emer_no == 'null' || emer_no == '') {
		$('#view-emer-div').html('<h3>请从特急登记表选择项目进行查看</h3>');
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
			var spanhead = '<span>';
			var spantail = '</span>';
			var jsonObj = JSON.parse(data);
			//$('#emer_no').append(jsonObj.emer_no);
			$('#emer_date').append(jsonObj.emer_date);
			$('#emer_content').append(spanhead + jsonObj.emer_content + spantail);
			$('#influence').append(spanhead + jsonObj.influence + spantail);
			$('#analytics').append(spanhead + jsonObj.analytics + spantail);
			
			var brush = new SyntaxHighlighter.brushes.Rpgle();
            brush.init({ toolbar: false });
            html = brush.getHtml(jsonObj.code); 
			$('#code').append(html);
			
			var level = jsonObj.level - 1;
			if (level <= 0 || level > 5) {
				$('#level').append("无");
			} else {
				$('#level').append('P' + level);
			}
			operators = jsonObj.operators;
			responsible = jsonObj.responsible;
			$('#proj_no').append(spanhead + jsonObj.proj_no + spantail);
			$('#proj_name').append(spanhead + jsonObj.proj_name + spantail);
			$('#proj_manager').append(spanhead + jsonObj.proj_manager + spantail);
			systems = new Array('LG50－收单平台', 'LG51－交换平台', 'LG52－信用卡收单平台', 
				'LH53－信用卡柜台及390转400', 'LU57－收付易平台', 'LU47-结算系统');
			$('#system').append(systems[jsonObj.system - 1]);
			$('#remark').append(spanhead + jsonObj.remark + spantail);
			
			var operatorsJson = JSON.parse(operators);
			var responsibleJson = JSON.parse(responsible);
			
			var slctedhtml = '';
			for (var i = 0; i < operatorsJson.length; i++) {
				slctedhtml += '<span>' + operatorsJson[i].emp_name + '　</span>';
			}
			$('#operators').append(slctedhtml);
			
			slctedhtml = '';
			for (var i = 0; i < responsibleJson.length; i++) {
				slctedhtml += '<span>' + responsibleJson[i].emp_name + '　</span>';
			}
			$('#responsible').append(slctedhtml);
		}
	});	
});
</script>

<script type="text/javascript" src="js/shCore.js"></script>
<script type="text/javascript" src="js/shBrushRpgle.js"></script>
<link type="text/css" rel="stylesheet" href="css/shCoreDefault.css"/>

<%@ include file="footer.jsp"%>