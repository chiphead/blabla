<%@ page language="java" contentType="text/html;charset=utf-8" %>
<%@ include file="header.jsp"%>
<div id="add-proj-div" class="well">
	<form id="add-proj-form" name="add_proj_form" action="#" method="post">
		<div class="row-fluid">
			<div class="span6">
				项目编号　
				<input type="text" id="proj_no" name="proj_no" />
			</div>
			<div class="span6">
				需求编号　
				<input type="text" id="req_no" name="req_no" />
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				项目名称　
				<input type="text" id="proj_name" name="proj_name" />
			</div>
			<div class="span6">
				项目类型　
				<select id="proj_type" name="proj_type">
					<option>
						流量
					</option>
					<option>
						重点
					</option>
				</select>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				主辅类型　
				<select id="master_slave" name="master_slave">
					<option>
						主办
					</option>
					<option>
						辅办
					</option>
					<option>
						其他
					</option>
				</select>
			</div>
			<div class="span6">
				当前状态　
				<select id="cur_state" name="cur_state">
					<option>
						正常
					</option>
					<option>
						延期
					</option>
					<option>
						暂缓
					</option>
				</select>
			</div>	
		</div>
		<div class="row-fluid">
			<div class="span8">
				开发负责人
				<input type="text" id="proj_manager" name="proj_manager" disabled />
				<a href="#addPersonModal" id="proj_manager_slct" class="btn person-slct-btn" data-toggle="modal"><i class="icon-user"></i> 选择</a>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				风险和问题
				<br />
				<textarea id="risk_question" name="risk_question" class="span8"
					rows="2"></textarea>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				<h4>
					项目成员
				</h4>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				业务负责人
				<input type="text" id="proj_charge" name="proj_charge" />
			</div>
<!-- 			<div class="span6">
				内审员
				<input type="text" id="inter_auditor" name="inter_auditor" disabled />
				<a href="#addPersonModal" id="inter_auditor_slct" class="btn person-slct-btn" data-toggle="modal"><i class="icon-user"></i> 选择</a>
			</div> -->
		</div>
		<div class="row-fluid">
			<div class="span12">
				设计人员　
				<div id="designer" class="edit-div input-prepend"></div>
				<a href="#addPersonModal" id="designer-add" class="btn person-add-btn" data-toggle="modal"><i class="icon-user"></i> 选择</a>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				开发人员　
				<div id="developer" class="edit-div input-prepend"></div>
				<a href="#addPersonModal" id="developer-add" class="btn person-add-btn" data-toggle="modal"><i class="icon-user"></i> 选择</a>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				评审人员　
				<div id="auditor" class="edit-div input-prepend"></div>
				<a href="#addPersonModal" id="auditor-add" class="btn person-add-btn" data-toggle="modal"><i class="icon-user"></i> 选择</a>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				<h4>
					开发周期
				</h4>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span4">
				立项日期　　
				<div id="start_date" class="input-append date"
				 data-date-format="yyyy/mm/dd" >
					<input name="start_date" size="10" class="span10" type="text"
						 readonly>
					<span class="add-on"><i class="icon-th"></i> </span>
				</div>
			</div>
			<div class="span8">
				计划结项日期
				<div id="plan_end_date" class="input-append date"
					data-date-format="yyyy/mm/dd">
					<input name="plan_end_date" size="10" class="span10" type="text"
						readonly>
					<span class="add-on"><i class="icon-th"></i> </span>
					<!-- <span class="add-on date-clear"><i class="icon-remove"></i></span> -->
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span4">
				实际结项日期
				<div id="actual_end_date" class="input-append date"
					data-date-format="yyyy/mm/dd">
					<input name="actual_end_date" size="10" class="span10" type="text"
						readonly>
					<span class="add-on"><i class="icon-th"></i> </span>
				</div>
			</div>
			<div class="span8">
				实际天数　　
				<input type="text" id="actual_days" name="actual_days" value="0" class="span2" disabled />
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				实际上线日期
				<div id="actual-install-date" class="edit-div input-prepend"></div>
				<a href="#addDateModal" id="install-date-add" class="btn date-add-btn" data-toggle="modal">
					<i class="icon-plus"></i> 添加
				</a>
				<a href="#chgDateModal" id="install-date-chg" class="btn date-chg-btn" data-toggle="modal">
					<i class="icon-edit"></i> 修改
				</a>
			</div>
		</div>
		
		<div class="row-fluid">
			<div class="span12">
				<h4>
					项目阶段
				</h4>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span2">
				需求
			</div>
			<div class="span5 week-slct">
				<span class="label label-info">开始</span><select id="start_1_year" name="start_1_year" class="slct-year">
					<option>2013</option><option selected="selected">2014</option><option>2015</option><option>2016</option><option>2017</option>
				</select>年
				<select id="start_1_month" name="start_1_month" class="slct-month">
					<option>1</option><option>2</option><option>3</option><option>4</option>
					<option>5</option><option>6</option><option>7</option><option>8</option>
					<option>9</option><option>10</option><option>11</option><option>12</option>
				</select>月
				<select id="start_1_week" name="start_1_week" class="slct-week">
				</select>周
			</div>
			<div class="span5 week-slct">
				<span class="label label-info">截止</span><select id="end_1_year" name="end_1_year" class="slct-year">
					<option>2013</option><option selected="selected">2014</option><option>2015</option><option>2016</option><option>2017</option>
				</select>年
				<select id="end_1_month" name="end_1_month" class="slct-month">
					<option>1</option><option>2</option><option>3</option><option>4</option>
					<option>5</option><option>6</option><option>7</option><option>8</option>
					<option>9</option><option>10</option><option>11</option><option>12</option>
				</select>月
				<select id="end_1_week" name="end_1_week" class="slct-week">
				</select>周
			</div>
		</div>
		<div class="row-fluid">
			<div class="span2">
				设计
			</div>
			<div class="span5 week-slct">
				<span class="label label-info">开始</span><select id="start_2_year" name="start_2_year" class="slct-year">
					<option>2013</option><option selected="selected">2014</option><option>2015</option><option>2016</option><option>2017</option>
				</select>年
				<select id="start_2_month" name="start_2_month" class="slct-month">
					<option>1</option><option>2</option><option>3</option><option>4</option>
					<option>5</option><option>6</option><option>7</option><option>8</option>
					<option>9</option><option>10</option><option>11</option><option>12</option>
				</select>月
				<select id="start_2_week" name="start_2_week" class="slct-week">
				</select>周
			</div>
			<div class="span5 week-slct">
				<span class="label label-info">截止</span><select id="end_2_year" name="end_2_year" class="slct-year">
					<option>2013</option><option selected="selected">2014</option><option>2015</option><option>2016</option><option>2017</option>
				</select>年
				<select id="end_2_month" name="end_2_month" class="slct-month">
					<option>1</option><option>2</option><option>3</option><option>4</option>
					<option>5</option><option>6</option><option>7</option><option>8</option>
					<option>9</option><option>10</option><option>11</option><option>12</option>
				</select>月
				<select id="end_2_week" name="end_2_week" class="slct-week">
				</select>周
			</div>
		</div>
		<div class="row-fluid">
			<div class="span2">
				开发
			</div>
			<div class="span5 week-slct">
				<span class="label label-info">开始</span><select id="start_3_year" name="start_3_year" class="slct-year">
					<option>2013</option><option selected="selected">2014</option><option>2015</option><option>2016</option><option>2017</option>
				</select>年
				<select id="start_3_month" name="start_3_month" class="slct-month">
					<option>1</option><option>2</option><option>3</option><option>4</option>
					<option>5</option><option>6</option><option>7</option><option>8</option>
					<option>9</option><option>10</option><option>11</option><option>12</option>
				</select>月
				<select id="start_3_week" name="start_3_week" class="slct-week">
				</select>周
			</div>
			<div class="span5 week-slct">
				<span class="label label-info">截止</span><select id="end_3_year" name="end_3_year" class="slct-year">
					<option>2013</option><option selected="selected">2014</option><option>2015</option><option>2016</option><option>2017</option>
				</select>年
				<select id="end_3_month" name="end_3_month" class="slct-month">
					<option>1</option><option>2</option><option>3</option><option>4</option>
					<option>5</option><option>6</option><option>7</option><option>8</option>
					<option>9</option><option>10</option><option>11</option><option>12</option>
				</select>月
				<select id="end_3_week" name="end_3_week" class="slct-week">
				</select>周
			</div>
		</div>
		<div class="row-fluid">
			<div class="span2">
				UT
			</div>
			<div class="span5 week-slct">
				<span class="label label-info">开始</span><select id="start_4_year" name="start_4_year" class="slct-year">
					<option>2013</option><option selected="selected">2014</option><option>2015</option><option>2016</option><option>2017</option>
				</select>年
				<select id="start_4_month" name="start_4_month" class="slct-month">
					<option>1</option><option>2</option><option>3</option><option>4</option>
					<option>5</option><option>6</option><option>7</option><option>8</option>
					<option>9</option><option>10</option><option>11</option><option>12</option>
				</select>月
				<select id="start_4_week" name="start_4_week" class="slct-week">
				</select>周
			</div>
			<div class="span5 week-slct">
				<span class="label label-info">截止</span><select id="end_4_year" name="end_4_year" class="slct-year">
					<option>2013</option><option selected="selected">2014</option><option>2015</option><option>2016</option><option>2017</option>
				</select>年
				<select id="end_4_month" name="end_4_month" class="slct-month">
					<option>1</option><option>2</option><option>3</option><option>4</option>
					<option>5</option><option>6</option><option>7</option><option>8</option>
					<option>9</option><option>10</option><option>11</option><option>12</option>
				</select>月
				<select id="end_4_week" name="end_4_week" class="slct-week">
				</select>周
			</div>
		</div>
		<div class="row-fluid">
			<div class="span2">
				ST
			</div>
			<div class="span5 week-slct">
				<span class="label label-info">开始</span><select id="start_5_year" name="start_5_year" class="slct-year">
					<option>2013</option><option selected="selected">2014</option><option>2015</option><option>2016</option><option>2017</option>
				</select>年
				<select id="start_5_month" name="start_5_month" class="slct-month">
					<option>1</option><option>2</option><option>3</option><option>4</option>
					<option>5</option><option>6</option><option>7</option><option>8</option>
					<option>9</option><option>10</option><option>11</option><option>12</option>
				</select>月
				<select id="start_5_week" name="start_5_week" class="slct-week">
				</select>周
			</div>
			<div class="span5 week-slct">
				<span class="label label-info">截止</span><select id="end_5_year" name="end_5_year" class="slct-year">
					<option>2013</option><option selected="selected">2014</option><option>2015</option><option>2016</option><option>2017</option>
				</select>年
				<select id="end_5_month" name="end_5_month" class="slct-month">
					<option>1</option><option>2</option><option>3</option><option>4</option>
					<option>5</option><option>6</option><option>7</option><option>8</option>
					<option>9</option><option>10</option><option>11</option><option>12</option>
				</select>月
				<select id="end_5_week" name="end_5_week" class="slct-week">
				</select>周
			</div>
		</div>
		<div class="row-fluid">
			<div class="span2">
				UAT
			</div>
			<div class="span5 week-slct">
				<span class="label label-info">开始</span><select id="start_6_year" name="start_6_year" class="slct-year">
					<option>2013</option><option selected="selected">2014</option><option>2015</option><option>2016</option><option>2017</option>
				</select>年
				<select id="start_6_month" name="start_6_month" class="slct-month">
					<option>1</option><option>2</option><option>3</option><option>4</option>
					<option>5</option><option>6</option><option>7</option><option>8</option>
					<option>9</option><option>10</option><option>11</option><option>12</option>
				</select>月
				<select id="start_6_week" name="start_6_week" class="slct-week">
				</select>周
			</div>
			<div class="span5 week-slct">
				<span class="label label-info">截止</span><select id="end_6_year" name="end_6_year" class="slct-year">
					<option>2013</option><option selected="selected">2014</option><option>2015</option><option>2016</option><option>2017</option>
				</select>年
				<select id="end_6_month" name="end_6_month" class="slct-month">
					<option>1</option><option>2</option><option>3</option><option>4</option>
					<option>5</option><option>6</option><option>7</option><option>8</option>
					<option>9</option><option>10</option><option>11</option><option>12</option>
				</select>月
				<select id="end_6_week" name="end_6_week" class="slct-week">
				</select>周
			</div>
		</div>
		<div class="row-fluid">
			<div class="span2">
				上线
			</div>
			<div class="span5 week-slct">
				<span class="label label-info">开始</span><select id="start_7_year" name="start_7_year" class="slct-year">
					<option>2013</option><option selected="selected">2014</option><option>2015</option><option>2016</option><option>2017</option>
				</select>年
				<select id="start_7_month" name="start_7_month" class="slct-month">
					<option>1</option><option>2</option><option>3</option><option>4</option>
					<option>5</option><option>6</option><option>7</option><option>8</option>
					<option>9</option><option>10</option><option>11</option><option>12</option>
				</select>月
				<select id="start_7_week" name="start_7_week" class="slct-week">
				</select>周
			</div>
			<div class="span5 week-slct">
				<span class="label label-info">截止</span><select id="end_7_year" name="end_7_year" class="slct-year">
					<option>2013</option><option selected="selected">2014</option><option>2015</option><option>2016</option><option>2017</option>
				</select>年
				<select id="end_7_month" name="end_7_month" class="slct-month">
					<option>1</option><option>2</option><option>3</option><option>4</option>
					<option>5</option><option>6</option><option>7</option><option>8</option>
					<option>9</option><option>10</option><option>11</option><option>12</option>
				</select>月
				<select id="end_7_week" name="end_7_week" class="slct-week">
				</select>周
			</div>
		</div>
		<div class="row-fluid">
			<div class="span2">
				结项
			</div>
			<div class="span5 week-slct">
				<span class="label label-info">开始</span><select id="start_8_year" name="start_8_year" class="slct-year">
					<option>2013</option><option selected="selected">2014</option><option>2015</option><option>2016</option><option>2017</option>
				</select>年
				<select id="start_8_month" name="start_8_month" class="slct-month">
					<option>1</option><option>2</option><option>3</option><option>4</option>
					<option>5</option><option>6</option><option>7</option><option>8</option>
					<option>9</option><option>10</option><option>11</option><option>12</option>
				</select>月
				<select id="start_8_week" name="start_8_week" class="slct-week">
				</select>周
			</div>
			<div class="span5 week-slct">
				<span class="label label-info">截止</span><select id="end_8_year" name="end_8_year" class="slct-year">
					<option>2013</option><option selected="selected">2014</option><option>2015</option><option>2016</option><option>2017</option>
				</select>年
				<select id="end_8_month" name="end_8_month" class="slct-month">
					<option>1</option><option>2</option><option>3</option><option>4</option>
					<option>5</option><option>6</option><option>7</option><option>8</option>
					<option>9</option><option>10</option><option>11</option><option>12</option>
				</select>月
				<select id="end_8_week" name="end_8_week" class="slct-week">
				</select>周
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				<h4>
					产能数据
				</h4>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				计划工作量　
				<input type="text" id="plan_work_amount" name="plan_work_amount" maxlength="8" />
			</div>
			<div class="span6">
				实际工作量
				<input type="text" id="actual_work_amount" name="actual_work_amount" maxlength="8" />
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				结项功能点数
				<input type="text" id="function_amount" name="function_amount" />
			</div>
			<div class="span6">
				生产率　　
				<input type="text" id="productivity" name="productivity" readonly/>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				<h4>
					质量数据
				</h4>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				冒烟测试不通过次数
				<input type="text" id="smoke_num" name="smoke_num" />
			</div>
			<div class="span6">
				ST测试类型
				<select id="st_type" name="st_type">
					<option>
						A类：测试全负责
					</option>
					<option>
						B类：测试牵头开发执行
					</option>
					<option>
						C类：开发全负责
					</option>
					<option>
						D类：免测
					</option>
				</select>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				ST缺陷密度　　　　
				<input type="text" id="st_bug_density" name="st_bug_density" />
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				<h4>
					QA过程审计
				</h4>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				NC
				<input type="text" id="nc" name="nc" />
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				<h4>
					QC过程审计
				</h4>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				设计审计（文档预检）
				<input type="text" id="design_audit" name="design_audit" />
			</div>
			<div class="span6">
				代码审计（检查项表）
				<input type="text" id="code_audit" name="code_audit" />
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				上线审计（检查项表）
				<input type="text" id="install_audit" name="install_audit" />
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				<h4>
					项目后评估
				</h4>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				项目经验教训
				<br />

				<textarea id="experience" name="experience" class="span8" rows="2"></textarea>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				P级事件及分析
				<br />

				<textarea id="problem_analysis" name="problem_analysis"
					class="span8" rows="2"></textarea>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				备注
				<br />

				<textarea id="remark" name="remark" class="span8" rows="3"></textarea>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span4">
				<button type="button" id="addproj-submit" class="btn btn-primary">
					提交
				</button>
			</div>
			<div class="span8">
				<span id="addproj-info" class="text-error"></span>
			</div>
		</div>
	</form>
	<div id="add-info" class="text-center"></div>
	
	<!-- Modal-add-date -->
	<div id="addDateModal" class="modal customModal hide fade"
		tabindex="-1" role="dialog" aria-labelledby="addDate"
		aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">
				×
			</button>
			<h3 id="addDateModalLabel">
				添加日期
			</h3>
		</div>
		<div class="modal-body"><div id="date-add-div" class="row-fluid">
			上线日期　
			<div class="input-append date"
				data-date-format="yyyy/mm/dd">
				<input id="actual_install_date" name="actual_install_date" class="span10" type="text"
					readonly>
				<span class="add-on"><i class="icon-th"></i> </span>
			</div>
		</div></div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
			<button id="add-date-ok" class="btn btn-primary">确定</button>
		</div>
	</div>
	<!-- Modal-chg-date -->
	<div id="chgDateModal" class="modal customModal hide fade"
		tabindex="-1" role="dialog" aria-labelledby="chgDate"
		aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">
				×
			</button>
			<h3 id="chgDateModalLabel">
				修改日期
			</h3>
		</div>
		<div class="modal-body"><div id="date-chg-div" class="row-fluid">			
			<div class="alert alert-error"><h4>没有日期项</h4></div>
		</div></div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
			<button id="chg-date-ok" class="btn btn-primary">确定</button>
		</div>
	</div>
</div>
<script type="text/javascript" src="js/bootstrap-datepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="js/locales/bootstrap-datepicker.zh-CN.js" charset="UTF-8"></script>

<script type="text/javascript">
function date_p_del(i) {
	$('#date-chg-div').children('.date-p').eq(i).remove();
}

$(document).ready(function() {
	$('#index-li').addClass('active');
	
	//global vars
	cur_add_modal = '';
	designer = '';
	developer = '';
	auditor = '';
	
	actual_install_date = '';
	install_dates = [];
	
	var post_serv = 'addproj';
		
	var proj_no = $_GET['proj_no'];
	if (proj_no != null) {
		// $('#chg-proj-li').addClass('active');
		$('#top-container').html(
			'<h4>负责项目修改</h4>');
		if (proj_no == 'null') {
			$('#add-proj-div').html('<h3>请从项目列表选择项目进行修改</h3>');
			return;
		}
		
		if (getCookie('emp_yst_id') == '') {
			$('#add-proj-div').html('<h3>未登录</h3>');
			$('#loginModal').modal('show');
			$('#add-proj-div').show();
			return;
		}
			
		post_serv = 'chgproj';
		$('#add-proj-div').hide();
		$.post('autcheck', {			
			emp_yst_id : getCookie('emp_yst_id'),
			emp_pwd : getCookie('emp_pwd'),
			emp_name : getCookie('emp_name'),
			proj_no : proj_no
		}, function(data) {
			//console.log(data);
			if (data == 'false') {
				$('#add-proj-div').html('<h3>没有权限</h3>');
				$('#add-proj-div').show();
				return;
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
				} else {		
					$('#add-proj-div').show();
					var jsonObj = JSON.parse(data);
					var form = document.add_proj_form;
					form.proj_no.value = jsonObj.proj_no;
					form.proj_no.readOnly = true;
					form.req_no.value = jsonObj.req_no;
					form.proj_name.value = jsonObj.proj_name;
					form.proj_type.selectedIndex = jsonObj.proj_type - 1;
					form.master_slave.selectedIndex = jsonObj.master_slave - 1;
					form.proj_manager.value = jsonObj.proj_manager;
					form.cur_state.selectedIndex = jsonObj.cur_state - 1;
					form.risk_question.value = jsonObj.risk_question;
					form.proj_charge.value = jsonObj.proj_charge;
					// form.inter_auditor.value = jsonObj.inter_auditor;
					designerObj = JSON.parse(jsonObj.designer);
					developerObj = JSON.parse(jsonObj.developer);
					auditorObj = JSON.parse(jsonObj.auditor);
					
					var jsonYstid = '[';
					var slctedhtml = '';
					for (var i = 0; i < designerObj.length; i++) {
						slctedhtml += '<span class="select-tag">' + designerObj[i].emp_name + '</span>';
						jsonYstid += '{"emp_id":0, "emp_yst_id":' + designerObj[i].emp_yst_id + ', "emp_name":"' + designerObj[i].emp_name + '", "emp_pwd":"", "emp_aut":2},';
					}
					jsonYstid = jsonYstid.substr(0, jsonYstid.length - 1);
					jsonYstid += ']';
					designer = jsonYstid;
					$('#designer').html(slctedhtml);
					
					jsonYstid = '[';
					slctedhtml = '';
					for (var i = 0; i < developerObj.length; i++) {
						slctedhtml += '<span class="select-tag">' + developerObj[i].emp_name + '</span>';
						jsonYstid += '{"emp_id":0, "emp_yst_id":' + developerObj[i].emp_yst_id + ', "emp_name":"' + developerObj[i].emp_name + '", "emp_pwd":"", "emp_aut":2},';
					}
					jsonYstid = jsonYstid.substr(0, jsonYstid.length - 1);
					jsonYstid += ']';
					developer = jsonYstid;
					$('#developer').html(slctedhtml);
					
					jsonYstid = '[';
					slctedhtml = '';
					for (var i = 0; i < auditorObj.length; i++) {
						slctedhtml += '<span class="select-tag">' + auditorObj[i].emp_name + '</span>';
						jsonYstid += '{"emp_id":0, "emp_yst_id":' + auditorObj[i].emp_yst_id + ', "emp_name":"' + auditorObj[i].emp_name + '", "emp_pwd":"", "emp_aut":2},';
					}
					jsonYstid = jsonYstid.substr(0, jsonYstid.length - 1);
					jsonYstid += ']';
					auditor = jsonYstid;
					$('#auditor').html(slctedhtml);
					
					// $('#start_date').datepicker('update', jsonObj.start_date);
					// $('#plan_end_date').datepicker('update', jsonObj.plan_end_date);
					// $('#actual_end_date').datepicker('update', jsonObj.actual_end_date);
					form.start_date.value = jsonObj.start_date;
					form.plan_end_date.value = jsonObj.plan_end_date;
					form.actual_end_date.value = jsonObj.actual_end_date;
					form.actual_days.value = jsonObj.actual_days;
					
					console.log('jsonObj.actual_install_date : ' + jsonObj.actual_install_date);
					if (jsonObj.actual_install_date != '') {
						install_dates = jsonObj.actual_install_date;
						actual_install_date = JSON.stringify(install_dates);
						console.log('JSON.stringify: ' + actual_install_date);
						
						for (var i = 0; i < install_dates.length; i++) {
							var datehtml = '<span class="select-tag">' + install_dates[i].date + '</span>';
							$('#actual-install-date').append(datehtml);
						}
					}
					
					for (var i = 1; i <= 8; i++) {
						var phase_arr = jsonObj['phase_' + i].split('-');
						if (phase_arr == '')
							continue;
						$('#start_' + i + '_year').val(phase_arr[0]);
						$('#start_' + i + '_month').val(phase_arr[1]);
						$('#start_' + i + '_week').val(phase_arr[2]);
						$('#end_' + i + '_year').val(phase_arr[3]);
						$('#end_' + i + '_month').val(phase_arr[4]);
						$('#end_' + i + '_week').val(phase_arr[5]);
					}
					
					form.plan_work_amount.value = jsonObj.plan_work_amount;
					form.actual_work_amount.value = jsonObj.actual_work_amount;
					form.function_amount.value = jsonObj.function_amount;
					form.productivity.value = jsonObj.productivity;
					form.smoke_num.value = jsonObj.smoke_num;
					form.st_type.selectedIndex = jsonObj.st_type - 1;
					form.st_bug_density.value = jsonObj.st_bug_density;
					form.nc.value = jsonObj.nc;
					form.design_audit.value = jsonObj.design_audit;
					form.code_audit.value = jsonObj.code_audit;
					form.install_audit.value = jsonObj.install_audit;
					form.experience.value = jsonObj.experience;
					form.problem_analysis.value = jsonObj.problem_analysis;
					form.remark.value = jsonObj.remark;
				}
			});
		});
	} else {
		$('#add-proj-li').addClass('active');
		$('#top-container').html(
		//	'<h2>负责项目添加</h2><p class="lead">项目负责人添加项目</p>');
			'<h4>负责项目添加</h4>');
		
		if (getCookie('emp_yst_id') == '') {
			$('#add-proj-div').html('<h3>未登录</h3>');
			$('#loginModal').modal('show');
			$('#add-proj-div').show();
			return;
		}
	}

	$('#addproj-submit').click(function() {
		var form = document.add_proj_form;
		var proj_no = form.proj_no.value;
		var req_no = form.req_no.value;
		var proj_name = form.proj_name.value;
		var proj_type = form.proj_type.selectedIndex + 1;
		var master_slave = form.master_slave.selectedIndex + 1;
		var proj_manager = form.proj_manager.value;
		var cur_state = form.cur_state.selectedIndex + 1;
		var risk_question = form.risk_question.value;
		var proj_charge = form.proj_charge.value;
		// var inter_auditor = form.inter_auditor.value;
		
		var start_date = form.start_date.value;
		var plan_end_date = form.plan_end_date.value;
		var actual_days = form.actual_days.value;
		var actual_end_date = (actual_days == 0) ? '' : form.actual_end_date.value;
		var plan_work_amount = form.plan_work_amount.value;
		var actual_work_amount = form.actual_work_amount.value;
		var function_amount = form.function_amount.value;
		var productivity = form.productivity.value;
		var smoke_num = form.smoke_num.value;
		var st_type = form.st_type.selectedIndex + 1;
		var st_bug_density = form.st_bug_density.value;
		var nc = form.nc.value;
		var design_audit = form.design_audit.value;
		var code_audit = form.code_audit.value;
		var install_audit = form.install_audit.value;
		var experience = form.experience.value;
		var problem_analysis = form.problem_analysis.value;
		var remark = form.remark.value;

		var phase_1  =	form.start_1_year.value + '-' + form.start_1_month.value + '-' + form.start_1_week.value + '-' +
						form.end_1_year.value + '-' + form.end_1_month.value + '-' + form.end_1_week.value;
		var phase_2  =	form.start_2_year.value + '-' + form.start_2_month.value + '-' + form.start_2_week.value + '-' +
						form.end_2_year.value + '-' + form.end_2_month.value + '-' + form.end_2_week.value;
		var phase_3  =	form.start_3_year.value + '-' + form.start_3_month.value + '-' + form.start_3_week.value + '-' +
						form.end_3_year.value + '-' + form.end_3_month.value + '-' + form.end_3_week.value;
		var phase_4  =	form.start_4_year.value + '-' + form.start_4_month.value + '-' + form.start_4_week.value + '-' +
						form.end_4_year.value + '-' + form.end_4_month.value + '-' + form.end_4_week.value;
		var phase_5  =	form.start_5_year.value + '-' + form.start_5_month.value + '-' + form.start_5_week.value + '-' +
						form.end_5_year.value + '-' + form.end_5_month.value + '-' + form.end_5_week.value;
		var phase_6  =	form.start_6_year.value + '-' + form.start_6_month.value + '-' + form.start_6_week.value + '-' +
						form.end_6_year.value + '-' + form.end_6_month.value + '-' + form.end_6_week.value;
		var phase_7  =	form.start_7_year.value + '-' + form.start_7_month.value + '-' + form.start_7_week.value + '-' +
						form.end_7_year.value + '-' + form.end_7_month.value + '-' + form.end_7_week.value;
		var phase_8  =	form.start_8_year.value + '-' + form.start_8_month.value + '-' + form.start_8_week.value + '-' +
						form.end_8_year.value + '-' + form.end_8_month.value + '-' + form.end_8_week.value;
		
		if (proj_no == '') {
			$('#addproj-info').text(
					'　　　　请填写项目编号');
			return false;
		} else if (proj_name == '') {
			$('#addproj-info').text(
					'　　　　请填写项目名称');
			return false;
		} else if (proj_manager == '') {
			$('#addproj-info').text(
					'　　　　请填写开发负责人');
			return false;
		} else if (proj_charge == '') {
			$('#addproj-info').text(
					'　　　　请填写业务负责人');
			return false;
		//} else if (inter_auditor == '') {
		//	$('#addproj-info').text(
		//			'　　　　请填写内审员');
		//	return false;
		} else if (designer == '') {
			$('#addproj-info').text(
					'　　　　请选择设计人员');
			return false;
		} else if (developer == '') {
			$('#addproj-info').text(
					'　　　　请选择开发人员');
			return false;
		} else if (auditor == '') {
			$('#addproj-info').text(
					'　　　　请选择评审人员');
			return false;
		} else if (start_date == '') {
			$('#addproj-info').text(
					'　　　　请填写立项日期');
			return false;
		} else if (plan_end_date == '') {
			$('#addproj-info').text(
					'　　　　请填写计划结项日期');
			return false;
		} else if (actual_days != '' && isNaN(parseFloat(actual_days))) {
			$('#addproj-info').text(
					'　　　　实际天数请填写数字');
			return false;
		} else if (plan_work_amount == '') {
			$('#addproj-info').text(
					'　　　　请填写计划工作量');
			return false;
		} else if (isNaN(parseFloat(plan_work_amount))) {
			$('#addproj-info').text(
					'　　　　计划工作量请填写数字');
			return false;
		} else if (actual_work_amount != '' && 
				isNaN(parseFloat(actual_work_amount))) {
			$('#addproj-info').text(
					'　　　　实际工作量请填写数字');
			return false;
		} else if (function_amount != '' &&
				isNaN(parseFloat(function_amount))) {
			$('#addproj-info').text(
					'　　　　结项功能点数请填写数字');
			return false;
		} else if (productivity != '' && 
				isNaN(parseFloat(productivity))) {
			$('#addproj-info').text(
					'　　　　生产率请填写数字');
			return false;
		} else if (smoke_num != '' &&
				isNaN(parseFloat(smoke_num))) {
			$('#addproj-info').text(
					'　　　　冒烟测试不通过次数请填写数字');
			return false;
		} else if (st_bug_density != '' &&
				isNaN(parseFloat(st_bug_density))) {
			$('#addproj-info').text(
					'　　　　ST缺陷密度请填写数字');
			return false;
		} else {
			$('#addproj-info').text('');
		}
		
		if (actual_days == 0) {
			actual_end_date = '';
		}
		
//		dates_json = '[';
//		for (var i = 0; i < install_dates.length - 1; i++) {
//			dates_json += '{"date": "' + install_dates[i].date + '"},';
//		}
//		dates_json += '{"date": "' + install_dates[i].date + '"}]';
		//console.log('submit: ' + JSON.stringify(install_dates));
		$.post(post_serv, {
			emp_yst_id : getCookie('emp_yst_id'),
			emp_pwd : getCookie('emp_pwd'),
			emp_name : getCookie('emp_name'),
			proj_no : proj_no,
			req_no : req_no,
			proj_name : proj_name,
			proj_type : proj_type,
			master_slave : master_slave,
			proj_manager : proj_manager,
			cur_state : cur_state,
			risk_question : risk_question,
			proj_charge : proj_charge,
			// inter_auditor : inter_auditor,
			designer : designer,
			developer : developer,
			auditor : auditor,
			start_date : start_date,
			plan_end_date : plan_end_date,
			actual_end_date : actual_end_date,
			actual_days : actual_days,
			plan_work_amount : plan_work_amount,
			actual_work_amount : actual_work_amount,
			// actual_install_date : dates_json,
			actual_install_date : JSON.stringify(install_dates),
			function_amount : function_amount,
			productivity : productivity,
			smoke_num : smoke_num,
			st_type : st_type,
			st_bug_density : st_bug_density,
			nc : nc,
			design_audit : design_audit,
			code_audit : code_audit,
			install_audit : install_audit,
			experience : experience,
			problem_analysis : problem_analysis,
			remark : remark,
			phase_1 : phase_1,
			phase_2 : phase_2,
			phase_3 : phase_3,
			phase_4 : phase_4,
			phase_5 : phase_5,
			phase_6 : phase_6,
			phase_7 : phase_7,
			phase_8 : phase_8
		},
		function(data) {
			if (data == 'true') {
				$('#add-info').html('<h3>操作成功</h3>').addClass('text-success');
			} else if (data == 'noauth') {
				$('#add-info').html('<h3>没有权限</h3>').addClass('text-error');
			} else if (data == 'notlogged') {
				$('#add-info').html('<h3>请先登录</h3>').addClass('text-error');
			} else if (data == 'false') {
				$('#add-info').html('<h3>操作失败</h3>').addClass('text-error');
			} else if (data == 'duplicateProjNo') {
				$('#add-info').html('<h3>项目已存在</h3>').addClass('text-error');
			} else if (data == 'notProjManagerOrAdmin') {
				$('#add-info').html('<h3>无权限添加非本人负责项目</h3>').addClass('text-error');
			} else {
				$('#add-info').html('<h3>操作失败</h3>').addClass('text-error');
				console.log(data);
			}
			setTimeout(function() {
				$('#add-info').html('');
			}, 1500);
		});
		return false;
	});
	$('input,textarea').focus(function() {
		$('#addproj-info').text('');
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
	$('.person-slct-btn').click(function() {
		cur_add_modal = $(this).attr('id');
		var content = '';
		var jsonObj;
		$.post('getperson', function(data) {
			jsonObj = JSON.parse(data);
			for (var i = 0; jsonObj.rows[i] != null; i++) {
				content += '<div class="span4"><input type="radio" name="person-radio" value="' + jsonObj.rows[i].emp_yst_id + '"/><span>' + jsonObj.rows[i].emp_name + '</span></div>';
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
		if(cur_add_modal == 'designer-add') {
			$('#designer').html(slctedhtml);
			designer = jsonYstid;
		} else if(cur_add_modal == 'developer-add') {
			$('#developer').html(slctedhtml);
			developer = jsonYstid;
		} else if(cur_add_modal == 'auditor-add') {
			$('#auditor').html(slctedhtml);
			auditor = jsonYstid;
		} else if(cur_add_modal == 'proj_manager_slct' || cur_add_modal == 'inter_auditor_slct') {
			var input_slct;
			if (cur_add_modal == 'proj_manager_slct')
				input_slct = $('#proj_manager');
			// else if (cur_add_modal == 'inter_auditor_slct')
			// 	input_slct = $('#inter_auditor');
			var radios = document.getElementsByName('person-radio');
			for(var i = 0; i < radios.length; i++) {
				if(radios[i].checked) {
					$(input_slct).val($(radios[i]).next().text());
					break;
				}
				$(input_slct).val('');
			}
		}
		$('#addPersonModal').modal('hide');
		cur_add_modal = '';
	});
	
	function setActualDays() {
		var actual = document.add_proj_form.actual_end_date.value;
		var start = document.add_proj_form.start_date.value;
		if (actual <= start || actual == '' || start == '') {
			$('#actual_days').val(0);
		}
		else {
			$('#actual_days').val((Date.parse(actual) - Date.parse(start)) / 1000 / 3600 / 24);
		}
	}
	setActualDays();
	$('.date').datepicker({
		autoclose: true,
		clearBtn: true,
		language: 'zh-CN'
	}).on('changeDate', function(ev) {
		if($(this).attr('id') != 'plan_end_date') {
			setActualDays();
		}
	});
	
	$.post('getweeknum', {year : $('#start_1_year').val(), month : $('#start_1_month').val()}, function(data) {
		var week_num = parseInt(data);
		for (var i = 1; i <= 8; i++) {
			for (var j = 1; j <= week_num; j++) {
				$('#start_' + i + '_week').append('<option value="' + j + '">' + j + '</option>');
				$('#end_' + i + '_week').append('<option value="' + j + '">' + j + '</option>');
			}
		}
	});
	
	$('#actual_work_amount, #function_amount').keyup(function() {
		var awa = document.add_proj_form.actual_work_amount.value;
		var fa = document.add_proj_form.function_amount.value;
		if (isNaN(parseFloat(awa)) || isNaN(parseFloat(fa)) || awa == '' || fa == '' ||
				awa == '0' || fa == '0') {
			$('#productivity').val('');
		} else {
			$('#productivity').val(parseInt(fa) / parseInt(awa));
		}
	});
	
	$('.slct-year, .slct-month').change(function() {		
		for(var i = 1; i <= 8; i++) {
			var start_year = $('#start_' + i + '_year');
			var end_year = $('#end_' + i + '_year');
			var start_month = $('#start_' + i + '_month');
			var end_month = $('#end_' + i + '_month');
			var end_week = $('#end_' + i + '_week');
			if (parseInt(start_year.val()) > parseInt(end_year.val()))
				end_year.val(start_year.val());
			if (	parseInt(start_year.val()) == parseInt(end_year.val()) &&
					parseInt(start_month.val()) > parseInt(end_month.val())) {
				end_month.val(start_month.val());
				
				var mod_week = end_week;
				$.post('getweeknum', {year : end_year.val(), month : end_month.val()}, function(data) {
					var week_num = parseInt(data);					
					mod_week.empty();
					for (var j = 1; j <= week_num; j++) {
						mod_week.append('<option value="' + j + '">' + j + '</option>');
					}
				});
			}
			
/*			var next_start_year = $('#start_' + (i + 1) + '_year');
			var next_start_month = $('#start_' + (i + 1) + '_month');
			if (parseInt(end_year.val()) > parseInt(next_start_year.val()))
				next_start_year.val(end_year.val());
			if (parseInt(end_year.val()) == parseInt(next_start_year.val()) && 
					parseInt(end_month.val()) > parseInt(next_start_month.val()))
				next_start_month.val(end_month.val());*/
		}
	});
		
	$('#add-date-ok').click(function() {
		var new_date = $('#actual_install_date').val();
		if (new_date == '')
			return;
		var datehtml = '<span class="select-tag">' + new_date + '</span>';
		$('#actual-install-date').append(datehtml);
		console.log('add-date-ok: ' + new_date);
		
		var tags = $('#actual-install-date').children('.select-tag');
		var i = 0;
		actual_install_date = '[';
		for (; i < tags.length - 1; i++) {
			actual_install_date += '{"date":"' + $(tags[i]).text() + '"},';
		}
		actual_install_date += '{"date":"' + $(tags[i]).text() + '"}]';
		console.log('add-date-ok : ' + actual_install_date);
		install_dates = JSON.parse(actual_install_date);
		$('#addDateModal').modal('hide');
	});
	
	$('#install-date-chg').click(function() {
		if (actual_install_date == '') {
			$('#date-chg-div').html('<div class="alert alert-error"><h4>没有日期项</h4></div>');
			return;
		}
		install_dates = JSON.parse(actual_install_date);
		console.log("install-date-chg: " + actual_install_date);
		$('#date-chg-div').empty();
		for (var i = 0; i < install_dates.length; i++) {
			$('#date-chg-div').append('<div class="date-p">上线日期' + (i + 1) + '　<div class="input-append date" data-date-format="yyyy/mm/dd">' + 
				'<input id="install_date' + i + '" size="10" class="span10" type="text" readonly/>' + 
				'<span class="add-on"><i class="icon-th"></i> </span></div>' +
				'<a class="btn btn-small date-del-btn" href="javascript: date_p_del(' + i + ');"><i class="icon-remove"></i></a></div>');
			$('.date').datepicker({ autoclose: true, clearBtn: true, language: 'zh-CN' });
			$('#install_date' + i).val(install_dates[i].date);
			console.log("install-date-chg: " + install_dates[i].date);
		}
	});
	
	$('#chg-date-ok').click(function() {
		$('#chgDateModal').modal('hide');
		if (actual_install_date == '')
			return;
		var date_divs = $('#date-chg-div').children('.date-p');
		var i = 0;
		actual_install_date = '[';
		$('#actual-install-date').empty();
		for (; i < date_divs.length; i++) {
			var value = $(date_divs[i]).find('input').val();
			if (value == null || value == '')
				continue;
			actual_install_date += '{"date":"' + value + '"},';
			var datehtml = '<span class="select-tag">' + value + '</span>';
			$('#actual-install-date').append(datehtml);
			console.log('chg-date-ok: ' + value);
		}
		var len = actual_install_date.length;
		actual_install_date = actual_install_date.substr(0, len - 1);
		actual_install_date += ']';
		if (actual_install_date.length <= 2) {
			actual_install_date = '';
			install_dates = [];
			return;
		}
		console.log("chg-date-ok: " + actual_install_date);
		install_dates = JSON.parse(actual_install_date);	
	});
	
	$('.slct-year, .slct-month').click(function() {
		var slct_week = $(this).siblings('.slct-week');
		var slct_year = slct_week.siblings('.slct-year');
		var slct_month = slct_week.siblings('.slct-month');

		$.post('getweeknum', {year : slct_year.val(), month : slct_month.val()}, function(data) {
			var week_num = parseInt(data);
			slct_week.empty();
			for (var j = 1; j <= week_num; j++) {
				slct_week.append('<option value="' + j + '">' + j + '</option>');
			}
		});
	});
	$('.slct-week').change(function() {
		for(var i = 1; i <= 8; i++) {
			var start_year = $('#start_' + i + '_year');
			var start_month = $('#start_' + i + '_month');
			var end_year = $('#end_' + i + '_year');
			var end_month = $('#end_' + i + '_month');
			var start_week = $('#start_' + i + '_week');
			var end_week = $('#end_' + i + '_week');
			if (	parseInt(start_week.val()) > parseInt(end_week.val()) && 
					parseInt(start_year.val()) == parseInt(end_year.val()) &&
					parseInt(start_month.val()) == parseInt(end_month.val())) {
				end_week.val(start_week.val());
			}
			
/*			var next_start_year = $('#start_' + (i + 1) + '_year');
			var next_start_month = $('#start_' + (i + 1) + '_month');
			var next_start_week = $('#start_' + (i + 1) + '_week');
			if (	parseInt(end_year.val()) == parseInt(next_start_year.val()) && 
					parseInt(end_month.val()) == parseInt(next_start_month.val()) &&
					parseInt(end_week.val()) > parseInt(next_start_week.val()))
				next_start_week.val(end_week.val());*/
		}
	});
	/*
	$('.popover-btn').popover({ html : true, placement : 'bottom', selector : true, content : function() {
		var btn_id = $(this).attr('id');
		var popover_div;
		
		if (btn_id == 'designer-add') {
			popover_div = $('#designer-add-popover');
		} else if(btn_id == 'developer-add') {
			popover_div = $('#developer-add-popover');
		} else if(btn_id == 'auditor-add') {
			popover_div = $('#auditor-add-popover');
		}
		popover_div.children('div:first').html($(this).attr('id'));
		popover_div.find('.popover-ok').attr('id', btn_id + '-ok');
		popover_div.find('.popover-cancel').attr('id', btn_id + '-cancel');
		return popover_div.html();
	}});*/
/*	$('.date-clear').click(function() {
		$(this).parent().parent().find('input').val('');
		if (document.add_proj_form.start_date.value == '' || 
			document.add_proj_form.actual_end_date.value == '' ) {
			document.add_proj_form.actual_days.value = 0;
		}
		return false;
	});*/
});
</script>

<%@ include file="modals.jsp"%>
<%@ include file="footer.jsp"%>