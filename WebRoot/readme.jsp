<%@ page language="java" contentType="text/html;charset=utf-8" %>
<%@ include file="header.jsp" %>
						<script type="text/javascript">
							$(document).ready(function(){
								$('#readme-li').addClass('active');
								//$('#top-container').html('<h2>说明</h2><p class="lead">使用说明</p>');
								$('#top-container').html('<h4>说明</h4>');
								$('#sidebar > ul').empty().html('<li class="nav-header">说明</li><li class="active"><a href="#">使用说明</a></li>');
							});
						</script>
						<div id="readme-div" class="">
							<h1 class="text-info text-center">说明页</h1>
						</div>
<%@ include file="modals.jsp" %>
<%@ include file="footer.jsp" %>		