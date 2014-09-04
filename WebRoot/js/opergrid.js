/*
 * 运维登记表
 */
$('#index-li').addClass('active');
$('#oper-li').addClass('active');
$('#top-container').html('<h4>' + OPERTABLENAME + '</h4>');

$("#opergrid").flexigrid( {
	url : 'slctoper',
	dataType : 'json',
	colModel : [ {
		display : '审批状态',
		name : 'check_state',
		width: 60
	},{
		display : '编号',
		name : 'oper_no',
		width: 60,
		hide: true		
	},{
		display : '登记时间',
		name : 'reg_date',
		sortable : true,
		width: 80
	}, {
		display : '类型',
		name : 'oper_type',
		sortable : true,
		width: 60
	}, {
		display : '事件时间',
		name : 'oper_time',
		width: 140
	}, {
		display : '事件内容',
		name : 'operation',
		width: 420
	}, {
		display : '事件等级',
		name : 'level',
		width: 60
	}, {
		display : '运维人员',
		name : 'operators',
		sortable : true,
		width: 120
	}, {
		display : '所属系统',
		name : 'system',
		sortable : true,
		width: 120
//	}, {
//		display : '所属模块',
//		name : 'module',
//		sortable : true,
//		width: 60
	}, {
		display : '所属人员',
		name : 'belong_persons',
		width: 120
	}, {
		display : '影响业务',
		name : 'affected_business',
		width: 120
	}, {
		display : '问题分析',
		name : 'reason',
		sortable : true,
		width: 320
	}, {
		display : '改进建议',
		name : 'suggestion',
		sortable : true,
		width: 320
	}, {
		display : '备注',
		name : 'remark',
		width: 320
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
		display : '事件类型',
		name : 'oper_type'
	}, {
		display : '运维人员',
		name : 'operators',
		isdefault : true
	}, {
		display : '所属人员',
		name : 'belong_persons'
	}],
	sortname : 'oper_no',
	sortorder : 'asc',
	usepager : true,
	rp : 20,
	rpOptions: [20, 30, 50, 100],
	title : OPERTABLENAME,
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
				$.post('deloper', {
					emp_yst_id : getCookie('emp_yst_id'),
					emp_pwd : getCookie('emp_pwd'),
					oper_no : $(value).children().eq(1).text()
				}, function(data) {
					if (data == 'true') {
						$('#glob-info').html('<h3>删除成功</h3>').removeClass('text-error').addClass('text-success');
						$("#opergrid").flexReload();
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
		$.each($('.trSelected', grid), function(key, value) {
			var oper_no = $(value).children().eq(1).text();
			if (oper_no == null) return;
//			if ($(value).children().eq(6).text().indexOf(getCookie('emp_name')) == -1) {
//				$('#glob-info').html('<h3>没有权限</h3>');
//				$('#infoModal').modal('show');
//				setTimeout(function() {
//					$('#infoModal').modal('hide');
//					$('#glob-info').empty().removeClass('text-success').addClass('text-error');
//				}, 1200);
//				return;
//			}
			window.open('addoper.jsp?oper_no=' + oper_no, '_blank');
		});
	} else if (com == '添加') {
		window.open('addoper.jsp', '_blank');
	} else if (com == '审批') {
		if (getCookie('emp_yst_id') == '') {
			$('#loginModal').modal('show');
			return;
		}
		
		$.each($('.trSelected', grid), function(key, value) {
			var eva_form = document.form_evaluate;
			eva_form.oper_no.value = $(value).children().eq(1).text();
			// eva_form.reg_date.value = $(value).children().eq(2).text();
			eva_form.oper_type.value = $(value).children().eq(3).text();
			eva_form.oper_time.value = $(value).children().eq(4).text();
			eva_form.operation.value = $(value).children().eq(5).text();
			eva_form.level.value = $(value).children().eq(6).text();
			eva_form.operators.value = $(value).children().eq(7).text();
			eva_form.system.value = $(value).children().eq(8).text();
			// eva_form.module.value = $(value).children().eq(9).text();
			eva_form.belong_persons.value = $(value).children().eq(9).text();
			eva_form.affected_business.value = $(value).children().eq(10).text();
			eva_form.reason.value = $(value).children().eq(11).text();
			eva_form.suggestion.value = $(value).children().eq(12).text();
			eva_form.remark.value = $(value).children().eq(13).text();
			$('#evaluateModal').modal('show');
		});
	} else if (com == '导出Excel') {
		$('#opergrid').flexExport('exportexcel', 'opertable');
	} 
}

$('#evaluate-pass').click(function() {
	evaluate(2);
});

$('#evaluate-reject').click(function() {
	evaluate(3);
});

function evaluate(state) {
	$.post('chkoper', {
		emp_yst_id : getCookie('emp_yst_id'),
		emp_pwd : getCookie('emp_pwd'),
		oper_no : document.form_evaluate.oper_no.value,
		check_state : state
	}, function(data) {
		$('#evaluateModal').modal('hide');
		if (data == 'true') {
			$('#glob-info').html('<h3>审批成功</h3>').removeClass('text-error').addClass('text-success');
			$("#opergrid").flexReload();
		} else if (data == 'false') {
			$('#glob-info').html('<h3>审批失败</h3>');
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
}