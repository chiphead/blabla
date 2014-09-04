<%@ page language="java" contentType="text/html;charset=utf-8" %>
<%@ include file="header.jsp" %>
						<table id="schdgrid" style="display: none"></table>
						<ul class="my-dropdown" >
							<li><a tabindex="1" href="#" id="dropdown-audit-pass">通过</a></li>
							<li><a tabindex="1" href="#" id="dropdown-audit-reject">驳回</a></li>
						</ul>
						<script src="js/schdgrid.js"></script>
<%@ include file="modals.jsp" %>
<%@ include file="footer.jsp" %>