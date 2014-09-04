/*
 * 创新登记表
 */
$('#index-li').addClass('active');
$('#inno-li').addClass('active');
$('#top-container').html('<h4>' + INNOTABLENAME + '</h4>');

$("#innogrid").flexigrid( {
	url : 'slctinno',
	dataType : 'json',
	colModel : [{
		display : '编号',
		name : 'inno_no',
		width: 60,
		hide: true		
	},{
		display : '提出日期',
		name : 'reg_date',
		sortable : true,
		width: 72
	}, {
		display : '类型',
		name : 'inno_type',
		sortable : true,
		width: 64
	}, {
		display : '提出人员',
		name : 'proposers',
		sortable : true,
		width: 100
	}, {
		display : '所属系统',
		name : 'system',
		sortable : true,
		width: 120
	}, {
		display : '功能模块',
		name : 'module',
		sortable : true,
		width: 100
	}, {
		display : '优化建议',
		name : 'suggestion',
		width: 420
	}, {
		display : '优化理由',
		name : 'reason',
		sortable : true,
		width: 420
	}, {
		display : '补充意见',
		name : 'add_suggestion',
		sortable : true,
		width: 240
	}, {
		display : '结论',
		name : 'conclusion',
		sortable : true,
		width: 240
	}, {
		display : '实现人员',
		name : 'executors',
		sortable : true,
		width: 120
	}, {
		display : '实施效果',
		name : 'effect',
		sortable : true,
		width: 240
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
		display : '类型',
		name : 'inno_type'
	}, {
		display : '提出人员',
		name : 'proposers',
		isdefault : true
	} ],
	sortname : 'inno_no',
	sortorder : 'asc',
	usepager : true,
	rp : 20,
	rpOptions: [20, 30, 50, 100],
	title : INNOTABLENAME,
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
				$.post('delinno', {
					emp_yst_id : getCookie('emp_yst_id'),
					emp_pwd : getCookie('emp_pwd'),
					inno_no : $(value).children().eq(0).text()
				}, function(data) {
					if (data == 'true') {
						$('#glob-info').html('<h3>删除成功</h3>').removeClass('text-error').addClass('text-success');
						$("#innogrid").flexReload();
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
			var inno_no = $(value).children().eq(0).text();
			if (inno_no == null) return;
//			if ($(value).children().eq(2).text().indexOf(getCookie('emp_name')) == -1) {
//				$('#glob-info').html('<h3>没有权限</h3>');
//				$('#infoModal').modal('show');
//				setTimeout(function() {
//					$('#infoModal').modal('hide');
//					$('#glob-info').empty().removeClass('text-success').addClass('text-error');
//				}, 1200);
//				return;
//			}
			window.open('addinno.jsp?inno_no=' + inno_no, '_blank');
		});
	} else if (com == '添加') {
		window.open('addinno.jsp', '_blank');
	} else if (com == '导出Excel') {
		$('#innogrid').flexExport('exportexcel', 'innotable');
	} 
}

$('#evaluate-pass').click(function() {
	evaluate(2);
});

$('#evaluate-reject').click(function() {
	evaluate(3);
});