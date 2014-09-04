/*
 * 项目情况表
 */
$('#index-li').addClass('active');
$('#all-proj-li').addClass('active');
$('#top-container').html('<h4>'+ PROJTABLENAME + '</h4>');

$("#prjgrid").flexigrid( {
	url : 'slctproj',
	dataType : 'json',
	colModel : [ {
		display : '审批状态',
		name : 'eva_state',
		width: 60,
	}, {
		display : '更新时间',
		name : 'update_time',
		sortable : true,
		width: 60
	}, {
		display : '需求编号',
		name : 'req_no',
		sortable : true,
		width: 60
	}, {
		display : '项目编号',
		name : 'proj_no',
		sortable : true,
		width: 60,
		align: 'center'
	}, {
		display : '项目名称',
		name : 'proj_name',
		sortable : true,
		width: 280
	}, {
		display : '类型',
		name : 'proj_type',
		width: 30
	}, {
		display : '主辅办',
		name : 'master_slave',
		width: 42
	}, {
		display : '开发负责人',
		name : 'proj_manager',
		width: 60
	}, {
		display : '当前阶段',
		name : 'cur_phase',
		width: 60
	}, {
		display : '当前状态',
		name : 'cur_state',
		width: 60
	}, {
		display : '风险和问题',
		name : 'risk_question',
		width: 70
	}, {
		display : '设计人员',
		name : 'designer',
		width: 120
	}, {
		display : '开发人员',
		name : 'developer',
		width: 120
	}, {
		display : '评审人员',
		name : 'auditor',
		width: 120
	}, {
		display : '立项日期',
		name : 'start_date',
		sortable : true,
		width: 64
	}, {
		display : '实际上线日期',
		name : 'actual_install_date',
		sortable : true,
		width: 64
	}, {
		display : '计划结项日期',
		name : 'plan_end_date',
		sortable : true,
		width: 80
	}, {
		display : '实际结项日期',
		name : 'actual_end_date',
		sortable : true,
		width: 80
	}, {
		display : '实际天数',
		name : 'actual_days',
		sortable : true,
		width: 60,
		align: 'right'
	}, {
		display : '超期原因',
		name : 'overdue_reason',
		sortable : true,
		width: 80
	}, {
		display : '计划工作量',
		name : 'plan_work_amount',
		sortable : true,
		width: 64,
		align: 'right'
	}, {
		display : '实际工作量',
		name : 'actual_work_amount',
		sortable : true,
		width: 64,
		align: 'right'
	}, {
		display : '结项功能点数',
		name : 'function_amount',
		sortable : true,
		width: 76,
		align: 'right'
	}, {
		display : '生产率',
		name : 'productivity',
		sortable : true,
		width: 40,
		align: 'right'
	}, {
		display : '冒烟测试不通过次数',
		name : 'smoke_num',
		sortable : true,
		width: 116,
		align: 'right'
	}, {
		display : 'ST测试类型',
		name : 'st_type',
		width: 136
	}, {
		display : 'ST缺陷密度',
		name : 'st_bug_density',
		sortable : true,
		width: 72,
		align: 'right'
	}, {
		display : 'NC',
		name : 'nc',
		width: 60
	}, {
		display : '设计审计',
		name : 'design_audit',
		width: 60
	}, {
		display : '代码审计',
		name : 'code_audit',
		width: 60
	}, {
		display : '上线审计',
		name : 'install_audit',
		width: 60
	}, {
		display : '项目经验教训',
		name : 'experience',
		width: 136
	}, {
		display : 'P级事件及分析',
		name : 'problem_analysis',
		width: 136
	}, {
		display : '备注',
		name : 'remark',
		width: 136
	}],
	buttons : [ {
		name : '添加',
		bclass : 'add',
		onpress : Handle
	}, {
		name : '修改',
		bclass : 'edit',
		onpress : Handle
	}, {
		name : '审批',
		bclass : 'evaluate',
		onpress : Handle
	}, {
		separator : true
	}, {
		name : '归档',
		bclass : 'archive',
		onpress : Handle
	}, {
		name : '删除',
		bclass : 'delete',
		onpress : Handle
	}, {
		separator : true
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
//	onChangeSort : function(sortname, sortorder) {
//		$('#prjgrid').flexOptions({params: [{name : 'sortBy', value : sortname + sortorder}]}).flexReload();  
//	},
	sortname : 'proj_no',
	sortorder : 'asc',
	usepager : true,
	rp : 20,
	rpOptions: [20, 30, 50, 100],
	title : PROJTABLENAME,
	nomsg: '没有项目',
	showTableToggleBtn : false,
	singleSelect : true,
	width : 'auto',
	height : document.documentElement.clientHeight - BOTTOMMARGIN
});

function Handle(com, grid) {
	if (com == '删除') {
		if (getCookie('emp_yst_id') == '') {
			$('#loginModal').modal('show');
			return;
		}
		var conf = confirm('删除选定项目?');
		if (conf) {
			$.each($('.trSelected', grid), function(key, value) {
				$.post('delproj', {
					emp_yst_id : getCookie('emp_yst_id'),
					emp_pwd : getCookie('emp_pwd'),
					emp_name : getCookie('emp_name'),
					//proj_no : value.firstChild.innerText
					//proj_no : $(value.firstChild).text()
					proj_no : $(value).children().eq(3).text()
				}, function(data) {
					if (data == 'true') {
						$('#glob-info').html('<h3>删除成功</h3>').removeClass('text-error').addClass('text-success');
						$("#prjgrid").flexReload();
					} else if (data == 'false') {
						$('#glob-info').html('<h3>删除失败</h3>');
					} else if (data == 'noauth') {
						$('#glob-info').html('<h3>没有权限</h3>');
					} else if (data == 'notlogged') {
						$('#glob-info').html('<h3>请先登录</h3>');
					}
					$('#infoModal').modal('show');
					setTimeout(function() {
						$('#infoModal').modal('hide');
						$('#glob-info').empty().removeClass('text-success').addClass('text-error');
					}, 1200);
				});
			});
		}
	} else if (com == '修改') {
//		var conf = confirm('Edit ' + $('.trSelected', grid).length + ' items?');
		$.each($('.trSelected', grid), function(key, value) {
			// var proj_no = value.children[0].innerText;
			//var proj_no = $(value).children().first().text();
			var proj_no = $(value).children().eq(3).text();
			if (proj_no == null) return;
			window.open('addproj.jsp?proj_no=' + proj_no, '_blank');
		});

	} else if (com == '添加') {
		window.open('addproj.jsp', '_blank');
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
//					proj_no : value.firstChild.innerText,
//					cur_phase : value.children[4].innerText
					//proj_no : $(value.firstChild).text(),
					proj_no : $(value).children().eq(3).text(),
					cur_phase : $(value.children[8]).text()
				}, function(data) {
					if (data == 'noauth') {
						$('#glob-info').html('<h3>没有权限</h3>');
					} else if (data == 'notended') {
						$('#glob-info').html('<h3>尚未结项</h3>');
					} else if (data == 'notpassed') {
						$('#glob-info').html('<h3>尚未审批</h3>');
					}else if (data == 'true') {
						$('#glob-info').html('<h3>归档成功</h3>').removeClass('text-error').addClass('text-success');
						$('#allschdgrid').flexReload();
					} else if (data == 'notlogged') {
						$('#glob-info').html('<h3>请先登录</h3>');
					} else {
						$('#glob-info').html('<h3>归档失败</h3>');
					}
					$('#infoModal').modal('show');
					setTimeout(function() {
						$('#infoModal').modal('hide');
						$('#glob-info').empty().removeClass('text-success').addClass('text-error');
					}, 1200);
				});
			});
		}
	} else if (com == '审批') {
		if (getCookie('emp_yst_id') == '') {
			$('#loginModal').modal('show');
			return;
		}
		
		$.each($('.trSelected', grid), function(key, value) {
			$('#evaluateModal').modal('show');
			form_evaluate.proj_no.value = $(value).children().eq(3).text();
			form_evaluate.proj_name.value = $(value).children().eq(4).text();
			form_evaluate.experience.value = $(value).children().eq(31).text();
			form_evaluate.problem_analysis.value = $(value).children().eq(32).text();
			form_evaluate.remark.value = $(value).children().eq(33).text();
		});
	} else if (com == '导出Excel') {
		$('#prjgrid').flexExport('exportexcel', 'projtable');
	} 
}

$('#evaluate-pass').click(function() {
	evaluate(2);
});

$('#evaluate-reject').click(function() {
	evaluate(3);
});

function evaluate(state) {
	$.post('chkeva', {
		emp_yst_id : getCookie('emp_yst_id'),
		emp_pwd : getCookie('emp_pwd'),
		proj_no : form_evaluate.proj_no.value,
		evaluate_state : state
	}, function(data) {
		$('#evaluateModal').modal('hide');
		if (data == 'noauth') {
			$('#glob-info').html('<h3>没有权限</h3>');
		} else if (data == 'true') {
			$('#glob-info').html('<h3>审批成功</h3>').removeClass('text-error').addClass('text-success');
			$('#prjgrid').flexReload();
		} else if (data == 'notlogged') {
			$('#glob-info').html('<h3>请先登录</h3>');
		} else {
			$('#glob-info').html('<h3>审批失败</h3>');
		}
		$('#infoModal').modal('show');
		setTimeout(function() {
			$('#infoModal').modal('hide');
			$('#glob-info').empty().removeClass('text-success').addClass('text-error');
		}, 1000);
	});
}