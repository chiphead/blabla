/*
 * 项目排期表
 */
var weeks;

var colModels = [ {
			display : '项目编号',
			name : 'proj_no',
			sortable : true,
			width : 60,
			align : 'center'
		}, {
			display : '项目名称',
			name : 'proj_name',
			sortable : true,
			width : 280
		}, {
			display : '业务负责人',
			name : 'proj_charge',
			width : 70
		}, {
			display : '开发负责人',
			name : 'proj_manager',
			width : 60
		}
//		, {
//			display : '内审员',
//			name : 'inter_auditor',
//			width : 40
//		}
		, {
			display : '当前阶段',
			name : 'cur_phase',
			width : 120
		}];

$('#index-li').addClass('active');
$('#all-schd-li').addClass('active');
$('#top-container').html('<h4>' + PROJSCHDTABLENAME + '</h4>');

$.post('getprojschd', function (data) {
	if (data == '')
		return;
	weeks = data.split('|');
	var len = colModels.length;
	for (var i = 0; i < weeks.length; i++) {
		colModels[len+i] = new Object();
		colModels[len+i].display = weeks[i];
		colModels[len+i].name = 'week' + (i+1);
		colModels[len+i].width = 80;
	}
	var Handle = function(com, grid) {
		if (com == '导出Excel') {
			$('#allschdgrid').flexExport('exportexcel', 'projschdtable');
		} else if (com == '修改') {
			if (getCookie('emp_yst_id') == '') {
				$('#loginModal').modal('show');
				return;
			}
			$.each($('.trSelected', grid), function(key, value) {
				var proj_no = $(value).children().eq(0).text();
				if (proj_no == null) return;
				window.open('addproj.jsp?proj_no=' + proj_no, '_blank');
			});
		} else if (com == '归档') {
			if (getCookie('emp_yst_id') == '') {
				$('#loginModal').modal('show');
				return;
			}
			var conf = confirm('归档选定项目?');
			if (conf) {
				$.each($('.trSelected', grid), function(key, value) {
					$.post('arcproj', {
						emp_yst_id : getCookie('emp_yst_id'),
						emp_pwd : getCookie('emp_pwd'),
						emp_name : getCookie('emp_name'),
						proj_no : $(value).children().eq(0).text(),
						cur_phase : $(value).children().eq(4).text()
					}, function(data) {
						if ($('#infoModal').is(':hidden')) {
							if (data == 'noauth') {
								$('#glob-info').html('<h3>没有权限</h3>');
							} else if (data == 'notlogged') {
								$('#glob-info').html('<h3>请先登录</h3>');
							} else if (data == 'notended') {
								$('#glob-info').html('<h3>尚未结项</h3>');
							} else if (data == 'notpassed') {
								$('#glob-info').html('<h3>尚未审批</h3>');
							} else if (data == 'true') {
								$('#glob-info').html('<h3>归档成功</h3>').removeClass('text-error').addClass('text-success');
								$('#allschdgrid').flexReload();
							} else {
								$('#glob-info').html('<h3>归档失败</h3>');
							}
							$('#infoModal').modal('show');
							setTimeout(function() {
								$('#infoModal').modal('hide');
								$('#glob-info').empty().removeClass('text-success').addClass('text-error');
							}, 1200);
						}
					});
				});
			}
		}
	};
	$("#allschdgrid").flexigrid( {
		url : 'slctprojschd',
		dataType : 'json',
		colModel : colModels,
		buttons : [ {
			name : '修改',
			bclass : 'edit',
			onpress : Handle
		}, {
			name : '归档',
			bclass : 'archive',
			onpress : Handle
		}, {
			name : '导出Excel',
			bclass : 'export',
			onpress : Handle
		}, {
			separator : true
		} ],
		searchitems : [ {
			display : '编号',
			name : 'proj_no'
		}, {
			display : '名称',
			name : 'proj_name',
			isdefault : true
		} ],
		sortname : 'proj_no',
		sortorder : 'asc',
		usepager : true,
		rp : 20,
		rpOptions: [20, 30, 50, 100],
		title : PROJSCHDTABLENAME,
		nomsg : '没有项目',
		showTableToggleBtn : false,
		singleSelect : true,
		width : 'auto',
		height : document.documentElement.clientHeight - BOTTOMMARGIN
	});
});

