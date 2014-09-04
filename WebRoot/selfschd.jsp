<%@ page language="java" contentType="text/html;charset=utf-8" %>
<%@ include file="header.jsp" %>
						<!-- Modal-cell-schd-add -->
						<div id="cellSchdModal" class="modal customModal hide fade"
							tabindex="-1" role="dialog" aria-labelledby="cellSchdModalLabel"
							aria-hidden="true">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">
									×
								</button>
								<h3 id="cellSchdModalLabel">
									添加排期信息
								</h3>
							</div>
							<form id="form_cell_schd" name="form_cell_schd" action="#" method="post">
								<div id="schd-form" class="modal-body cell-schd">
								<p>项目编号 <input type="text" id="proj_no" name="proj_no" readonly /></p>
								<p>项目名称 <input type="text" id="proj_name" name="proj_name" readonly /></p>
								项目阶段 <select id="proj_phase" name="proj_phase" class="span3">
											<option>需求</option><option>设计</option><option>开发</option><option>UT</option>
											<option>ST</option><option>UAT</option><option>上线</option>
											<option>运维</option><option>评审</option>
										</select>
								　 投入时间 <input type="text" id="phase_time" name="phase_time" value="0" class="span3" />
								<span id="cell-schd-info" class="text-error pull-right"></span>
								</div>
							</form>
							<div class="modal-footer">
								<button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
								<button id="cell-schd-ok" class="btn btn-primary" type="submit">确定</button>
							</div>
						</div>
						<!-- Modal-cell-schd-chg -->
						<div id="cellSchdChgModal" class="modal customModal hide fade"
							tabindex="-1" role="dialog" aria-labelledby="cellSchdChgModalLabel"
							aria-hidden="true">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">
									×
								</button>
								<h3 id="cellSchdChgModalLabel">
									修改排期信息
								</h3>
							</div>
							<form id="form_cell_schd_chg" name="form_cell_schd_chg" action="#" method="post">
								<div id="schd-chg-form" class="modal-body">
								<p>项目编号 <input type="text" name="proj_no" readonly /></p>
								<p>项目名称 <input type="text" name="proj_name" readonly /></p>
								<div id="schd-chg-form-phases"></div>
								<span id="cell-schd-chg-info" class="text-error pull-right"></span>
								</div>
							</form>
							<div class="modal-footer">
								<button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
								<button id="cell-schd-chg-ok" class="btn btn-primary" type="submit">确定</button>
							</div>
						</div>
						
						<ul class="my-dropdown" >
							<li><a tabindex="1" href="#" id="dropdown-add">添加</a></li>
							<li><a tabindex="2" href="#" id="dropdown-chg">修改</a></li>
						</ul>
						
						<table id="selfschdgrid" style="display: none"></table>						
<%@ include file="modals.jsp" %>
<script src="js/selfschd.js"></script>
<%@ include file="footer.jsp" %>