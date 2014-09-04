<%@ page language="java" contentType="text/html;charset=utf-8" %>
<%@ include file="header.jsp" %>
						<div id="grid-hold"><table id="opergrid" style="display: none"></table></div>
						<!-- Modal-evaluate -->
						<div id="evaluateModal" class="modal customModal hide fade"
							tabindex="-1" role="dialog" aria-labelledby="addPerson"
							aria-hidden="true">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">
									×
								</button>
								<h3 id="evaluateModalLabel">
									运维项审批
								</h3>
							</div>
							<form id="form_evaluate" name="form_evaluate" action="#" method="post">
								<div id="evaluate-form" class="modal-body">
									<div class="row-fluid" style="display:none;">
										<div class="span12">编　　号　
											<input type="text" id="oper_no" name="oper_no" readonly/>
										</div>
									</div>
									<div class="row-fluid">
										<div class="span12">起止时间　
											<input type="text" id="oper_time" name="oper_time" readonly/>
										</div>
									</div>
									<div class="row-fluid">
										<div class="span12">
											事件等级　
											<input type="text" id="level" name="level" readonly/>
										</div>
									</div>
									<div class="row-fluid">
										<div class="span12">
											运维人员　
											<input type="text" id="operators" name="operators" readonly/>
										</div>
									</div>
									<div class="row-fluid">
										<div class="span12">
											所属系统　
											<input type="text" id="system" name="system" readonly/>
										</div>
									</div>
									<!-- <div class="row-fluid">
										<div class="span12">
											所属模块　
											<input type="text" id="module" name="module" readonly/>
										</div>
									</div> -->
									<div class="row-fluid">
										<div class="span12">
											所属人员　
											<input type="text" id="belong_persons" name="belong_persons" readonly/>
										</div>
									</div>
									<div class="row-fluid">
										<div class="span12">
											影响业务　
											<input type="text" id="affected_business" name="affected_business" readonly/>
										</div>
									</div>
									<div class="row-fluid">
										<div class="span12">
											事件类型　
											<input type="text" id="oper_type" name="oper_type" readonly/>
										</div>
									</div>
									<div class="row-fluid">
										<div class="span12">
											事件内容　
											<textarea id="operation" name="operation" class="span8" rows="2" readonly></textarea>
										</div>
									</div>
									<div class="row-fluid">
										<div class="span12">
											问题分析　
											<textarea id="reason" name="reason" class="span8" rows="2" readonly></textarea>
										</div>
									</div>
									<div class="row-fluid">
										<div class="span12">
											改进建议　
											<textarea id="suggestion" name="suggestion" class="span8" rows="2" readonly></textarea>
										</div>
									</div>
									<div class="row-fluid">
										<div class="span12">
											备　　注　
											<textarea id="remark" name="remark" class="span8" rows="2" readonly></textarea>
										</div>
									</div>
								</div>
							</form>
							<div class="modal-footer">
								<button id="evaluate-pass" class="btn btn-primary">通过</button>
								<button id="evaluate-reject" class="btn">不通过</button>
							</div>
						</div>
						<script src="js/opergrid.js"></script>
<%@ include file="modals.jsp" %>
<%@ include file="footer.jsp" %>