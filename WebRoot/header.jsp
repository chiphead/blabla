<%@ page language="java" contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<html>
	<head>
		<title>项目排期管理</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<!-- Bootstrap -->
		<link href="css/bootstrap.css" rel="stylesheet" media="screen">
		<link href="css/custom.css" rel="stylesheet" media="screen">
		<link href="css/flexigrid.css" rel="stylesheet" media="screen">
		<link href="css/datepicker.css" rel="stylesheet" media="screen">
		<link href="css/bootstrap-timepicker.min.css" rel="stylesheet" media="screen">
		<link href="css/jquery.ui.theme.css" rel="stylesheet" media="screen">
		<link href="css/ui.jqgrid.css" rel="stylesheet" media="screen">
		<script src="js/jquery-2.0.3.js"></script>
		<script src="js/jquery-ui.min.js"></script>
		<script src="js/grid.locale-cn.js" type="text/javascript"></script>
		<script src="js/flexigrid.js"></script>
		<script src="js/constants.js"></script>
		<script src="js/utils.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/user.js"></script>
		<script src="js/jquery-placeholder.js"></script>
		<script src="js/modernizr-latest.js"></script>
	</head>
	<body>
		<div class="navbar navbar-inverse navbar-fixed-top">
			<div class="navbar-inner">
				<div class="container-fluid">
					<a class="brand" href="#">项目排期管理</a>
					<div class="nav-collapse collapse">
						<ul class="nav">
							<li id="index-li">
								<a href="index.jsp">首页</a>
							</li>
							<li id="account-li" class="hide">
								<a href="account.jsp" class="tooltip-link" data-toggle="tooltip"
									data-placement="bottom" title="用户账号管理">账号</a>
							</li><!-- 
							<li id="readme-li">
								<a href="readme.jsp">使用说明</a>
							</li> -->
						</ul>
						<div id="div-name" class="btn-group pull-right hide">
							<a class="btn btn-primary" href="account.jsp"><i
								class="icon-user icon-white"></i><span id="logged-user">姓名</span>
							</a>
							<a id="signout" class="btn btn-primary dropdown-toggle" href="#"><i
								class="icon-off icon-white"></i> </a>
						</div>
						<div id="div-login" class="btn-group pull-right hide">
							<a href="#registerModal" class="btn btn-primary" data-toggle="modal">注册</a>
							<a href="#loginModal" class="btn btn-primary" data-toggle="modal">登录</a>
						</div>
					</div>
					<!--/.nav-collapse -->
				</div>
			</div>
		</div>
		<header class="jumbotron subhead" id="overview">
		<div id="top-container" class="container">
			<h3>　　　　</h3>
			<!-- <p class="lead">
				页面说明
			</p> -->
		</div>
		</header>
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span2">
					<div id="sidebar" class="well sidebar-nav affix-top">
						<ul id="sidebar-ul" class="nav nav-list">
							<li class="nav-header">
								收单项目管理
							</li>
							<li id="all-proj-li">
								<a href="index.jsp">项目情况表</a>
							</li>
							<li id="all-schd-li">
								<a href="allschd.jsp">项目排期表</a>
							</li>
 							<li id="schd-li">
								<a href="schd.jsp">工作排期表</a>
							</li>
							<li class="nav-header">
								员工项目管理
							</li>
							<li id="selfschd-li">
								<a href="selfschd.jsp">个人排期管理</a>
							</li>
							<li id="add-proj-li">
								<a href="addproj.jsp">负责项目添加</a>
							</li>
							<!-- <li id="chg-proj-li">
								<a href="addproj.jsp?proj_no=null">负责项目修改</a>
							</li> -->
							
							<li class="nav-header">
								收单运维管理
							</li>
							<li id="oper-li">
								<a href="oper.jsp">运维登记表</a>
							</li>
							<li id="add-oper-li">
								<a href="addoper.jsp">运维项目登记</a>
							</li>
 							<li class="nav-header">
								特急修改管理
							</li>
							<li id="emer-li">
								<a href="emergency.jsp">特急登记表</a>
							</li>
							<li id="add-emer-li">
								<a href="addemer.jsp">特急修改登记</a>
							</li>
							<li class="nav-header">
								收单创新管理
							</li>
							<li id="inno-li">
								<a href="innovation.jsp">创新登记表</a>
							</li>
							<li id="add-inno-li">
								<a href="addinno.jsp">创新项目登记</a>
							</li>
							<li class="nav-header">
								通知公告
							</li>
							<li>
								<a href="file/prod_code_mod_princ.docx">生产紧急修改代码内部知会原则</a>
							</li>			
						</ul>
					</div>
					<!--/.well -->
					<!-- 
					<form class="search" id="side-search">
						<input type="text" class="search-query" placeholder="搜索">
					</form> -->
				</div>
				<!--/span-->
				<div id="content-div" class="span10">