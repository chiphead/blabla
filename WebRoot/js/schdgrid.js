/*
 * 员工开发情况表
 */
// global vars
col = -1;
proj_no = '';
proj_user = '';

last_col = -1;
last_proj_no = '';

var weeks;
var schdColModels = [ {
		display : '人员',
		name : 'emp_name',
		width : 54,
		align : 'center'
	}, {
		display : '状态',
		name : 'proj_status',
		width : 36
	}, {
		display : '类型',
		name : 'proj_type',
		width : 36
	}, {
		display : '项目编号',
		name : 'proj_no',
		sortable : true,
		width : 60,
		align : 'center'
	}, {
		display : '项目名称',
		name : 'proj_name',
		width : 280
	} ];

function getProjInfo(id) {
	var row = document.getElementById('schdgrid').rows[id-1];
    var td = row.cells;
    proj_no = $(td[3]).text();
    // proj_name = $(td[4]).text();
    // proj_schd = $(td[col + 5]).text();
    proj_user = $(td[0]).text();
}

function getColumnId(tdDiv, id) { // start from 0 of schedule data
	var row = document.getElementById('schdgrid').rows[id-1];
    var td = row.cells;
    for (j = 0; j < td.length; j++) {
    	if ($(td[j]).children(0).is($(tdDiv)))
    		return j - 5;       
    }
    return str;
}

function editCell(tdDiv, id) {
	var value = $(tdDiv).html();
	$(tdDiv).click(function () {
		col = getColumnId(tdDiv, id);
		getProjInfo(id);
		
		if ($(this).parent().attr('style') == null) {
			return false;
		}
		if (getCookie('emp_yst_id') == '') {
			$('#loginModal').modal('show');
			return false;
		}
		
		if (last_col == col && last_proj_no == proj_no
				&& $('.my-dropdown').css('display') != 'none') {
			$('.my-dropdown').hide();
		} else {
			$('.my-dropdown').hide();
			$('.my-dropdown').css("top", $(this).offset().top + 16).css("left", $(this).offset().left + 30).show();
		}
		
		last_col = col;
		last_proj_no = proj_no;
		return false;
	});
}

$('#index-li').addClass('active');
$('#schd-li').addClass('active');
$('#top-container').html('<h4>' + SCHDTABLENAME + '</h4>');

$.post('getprojschd', function (data) {
	if (data == '')
		return;
	weeks = data.split('|');
	var len = schdColModels.length;
	for (var i = 0; i < weeks.length; i++) {
		schdColModels[len+i] = new Object();
		schdColModels[len+i].display = weeks[i];
		schdColModels[len+i].name = 'week' + (i+1);
		schdColModels[len+i].width = 80;
		schdColModels[len+i].process = editCell;
	}
	var Handle = function(com, grid) {
		if (com == '导出Excel') {
			$('#schdgrid').flexExport('exportexcel', 'schdtable');
		} else if (com == '批量通过') {
			if (getCookie('emp_yst_id') == '') {
				$('#loginModal').modal('show');
				return false;
			}
			$.each($('.trSelected', grid), function(key, value) {
				for (var i = 5; i < schdColModels.length; i++) {
					var cell = value.children[i];
					if ($(cell).attr('style') == null) {
						continue;
					}

					$.post('checkstate', {
						emp_yst_id: getCookie('emp_yst_id'),
						emp_pwd: getCookie('emp_pwd'),
						emp_name: $(value.children[0]).text(),
						proj_no: $(value.children[3]).text(),
						col_id: i - 5,
						check_state: 2
					}, function(data) {
						if (data == 'true') {							
							$('#schdgrid').flexReload();
							return;
						}
						
						if ($('#infoModal').is(':hidden')) {
							if (data == 'noauth') {
								$('#glob-info').html('<h3>没有权限</h3>');
							} else if (data == 'false') {
								$('#glob-info').html('<h3>操作失败</h3>');
							} else if (data == 'notlogged') {
								$('#glob-info').html('<h3>请先登录</h3>');
							}
							$('#infoModal').modal('show');
							setTimeout(function() {
								$('#infoModal').modal('hide');
								$('#glob-info').empty().removeClass('text-success').addClass('text-error');
							}, 1200);
						}
					});
				}
			});
		} else if (com == '显示本组') {
			$('#schdgrid').flexOptions({params: [{name : 'team_name', value : getCookie('emp_yst_id')}]}).flexReload();
		} else if (com == '显示全部') {
			$('#schdgrid').flexOptions({params: [{name : 'team_name', value : 'all'}]}).flexReload();
		}
	};
	$("#schdgrid").flexigrid( {
		url : 'slctempschd',
		dataType : 'json',
		colModel : schdColModels,
		buttons : [ {
			name : '批量通过',
			bclass : 'allpass',
			onpress : Handle
		}, {
			name : '导出Excel',
			bclass : 'export',
			onpress : Handle
		}, {
			separator : true
		}, {
			name : '显示本组',
			bclass : 'mygrp',
			onpress : Handle
		}, {
			name : '显示全部',
			bclass : 'allgrp',
			onpress : Handle
		}],
		searchitems : [ {
			display : '员工姓名',
			name : 'emp_name'
		}, {
			display : '项目编号',
			name : 'proj_no'
		}, {
			display : '项目名称',
			name : 'proj_name',
			isdefault : true
		} ],
		sortname : 'emp_name',
		sortorder : 'asc',
		usepager : true,
		rp : 20,
		rpOptions: [20, 30, 50, 100],
		title : SCHDTABLENAME,
		nomsg : '没有项目',
		showTableToggleBtn : false,
		//singleSelect : true,
		width : 'auto',
		height : document.documentElement.clientHeight - BOTTOMMARGIN
	});
});

$('#dropdown-audit-pass').click(function() {
	$('.my-dropdown').hide();
	$.post('checkstate', {
		emp_yst_id: getCookie('emp_yst_id'),
		emp_pwd: getCookie('emp_pwd'),
		emp_name: proj_user,
		proj_no: proj_no,
		col_id: col,
		check_state: 2
	}, function(data) {
		if (data == 'true') {							
			$('#schdgrid').flexReload();
			return;
		}
		
		if ($('#infoModal').is(':hidden')) {
			if (data == 'noauth') {
				$('#glob-info').html('<h3>没有权限</h3>');
			} else if (data == 'false') {
				$('#glob-info').html('<h3>操作失败</h3>');
			} else if (data == 'notlogged') {
				$('#glob-info').html('<h3>请先登录</h3>');
			}
			$('#infoModal').modal('show');
			setTimeout(function() {
				$('#infoModal').modal('hide');
				$('#glob-info').empty().removeClass('text-success').addClass('text-error');
			}, 1200);
		}
	});
});

$('#dropdown-audit-reject').click(function() {
	$('.my-dropdown').hide();
	$.post('checkstate', {
		emp_yst_id: getCookie('emp_yst_id'),
		emp_pwd: getCookie('emp_pwd'),
		emp_name: proj_user,
		proj_no: proj_no,
		col_id: col,
		check_state: 3
	}, function(data) {
		if (data == 'true') {							
			$('#schdgrid').flexReload();
			return;
		}
		
		if ($('#infoModal').is(':hidden')) {
			if (data == 'noauth') {
				$('#glob-info').html('<h3>没有权限</h3>');
			} else if (data == 'false') {
				$('#glob-info').html('<h3>操作失败</h3>');
			} else if (data == 'notlogged') {
				$('#glob-info').html('<h3>请先登录</h3>');
			}
			$('#infoModal').modal('show');
			setTimeout(function() {
				$('#infoModal').modal('hide');
				$('#glob-info').empty().removeClass('text-success').addClass('text-error');
			}, 1200);
		}
	});
});