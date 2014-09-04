<%@ page language="java" contentType="text/html;charset=utf-8" %>
<%@ include file="header.jsp" %>
						<div id="grid-hold"><table id="prjgrid" style="display: none"></table></div>						
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
									项目评估项审批
								</h3>
							</div>
							<form id="form_evaluate" name="form_evaluate" action="#" method="post">
								<div id="evaluate-form" class="modal-body">
									<div class="row-fluid">
										<div class="span12">项目编号
											<input type="text" id="proj_no" name="proj_no" readonly/>
										</div>
									</div>
									<div class="row-fluid">
										<div class="span12">项目名称
											<input type="text" id="proj_name" name="proj_name" readonly/>
										</div>
									</div>
									<div class="row-fluid">
										<div class="span12">
											项目经验教训
											<br />
											<textarea id="experience" name="experience" class="span12" rows="2" readonly></textarea>
										</div>
									</div>
									<div class="row-fluid">
										<div class="span12">
											P级事件及分析
											<br />
											<textarea id="problem_analysis" name="problem_analysis" class="span12" rows="2" readonly></textarea>
										</div>
									</div>
									<div class="row-fluid">
										<div class="span12">
											备注
											<br />
											<textarea id="remark" name="remark" class="span12" rows="2" readonly></textarea>
										</div>
									</div>
								</div>
							</form>
							<div class="modal-footer">
								<button id="evaluate-pass" class="btn btn-primary">通过</button>
								<button id="evaluate-reject" class="btn">不通过</button>
							</div>
						</div>
						<script src="js/prjgrid.js"></script>
<%@ include file="modals.jsp" %>
<%@ include file="footer.jsp" %>