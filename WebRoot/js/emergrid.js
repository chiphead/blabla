/*
 * 创新登记表
 */
$('#index-li').addClass('active');
$('#emer-li').addClass('active');
$('#top-container').html('<h4>' + EMERTABLENAME + '</h4>');

$("#emergrid").flexigrid( {
	url : 'slctemer',
	dataType : 'json',
	colModel : [{
		display : '编号',
		name : 'emer_no',
		width: 60,
		hide: true		
	}, {
		display : '登记日期',
		name : 'reg_date',
		width: 72
	}, {
		display : '事件日期',
		name : 'emer_date',
		sortable : true,
		width: 72
	}, {
		display : '事件内容',
		name : 'emer_content',
		width: 180
	}, {
		display : '影响业务',
		name : 'influence',
		width: 120
	}, {
		display : '问题分析',
		name : 'analytics',
		width: 180
	}, /*{
		display : '修改前后源码',
		name : 'code',
		sortable : true,
		width: 120
	}, */{
		display : '事件等级',
		name : 'level',
		width: 48
	}, {
		display : '维护人员',
		name : 'operators',
		width: 100
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
		width: 320
	}, {
		display : '项目负责人',
		name : 'proj_manager',
		width: 60
	}, {
		display : '责任人',
		name : 'responsible',
		width: 60
	}, {
		display : '所属系统',
		name : 'system',
		width: 160
	}, {
		display : '备注',
		name : 'remark',
		width: 360
	}],
	buttons : [ {
		name : '查看',
		bclass : 'view',
		onpress : Handle
	}, {
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
		display : '项目编号',
		name : 'proj_no',
		isdefault : true
	}, {
		display : '项目名称',
		name : 'proj_name'
	}, {
		display : '事件日期',
		name : 'emer_date'
	} ],
	sortname : 'emer_no',
	sortorder : 'asc',
	usepager : true,
	rp : 20,
	rpOptions: [20, 30, 50, 100],
	title : EMERTABLENAME,
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
				$.post('delemer', {
					emp_yst_id : getCookie('emp_yst_id'),
					emp_pwd : getCookie('emp_pwd'),
					emer_no : $(value).children().eq(0).text()
				}, function(data) {
					if (data == 'true') {
						$('#glob-info').html('<h3>删除成功</h3>').removeClass('text-error').addClass('text-success');
						$("#emergrid").flexReload();
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
			var emer_no = $(value).children().eq(0).text();
			if (emer_no == null) return;
//			if ($(value).children().eq(2).text().indexOf(getCookie('emp_name')) == -1) {
//				$('#glob-info').html('<h3>没有权限</h3>');
//				$('#infoModal').modal('show');
//				setTimeout(function() {
//					$('#infoModal').modal('hide');
//					$('#glob-info').empty().removeClass('text-success').addClass('text-error');
//				}, 1200);
//				return;
//			}
			window.open('addemer.jsp?emer_no=' + emer_no, '_blank');
		});
	} else if (com == '添加') {
		window.open('addemer.jsp', '_blank');
	} else if (com == '查看') {
		$.each($('.trSelected', grid), function(key, value) {
			var emer_no = $(value).children().eq(0).text();
			if (emer_no == null) return;
			window.open('viewemer.jsp?emer_no=' + emer_no, '_blank');
		});
	} else if (com == '导出Excel') {
		$('#emergrid').flexExport('exportexcel', 'emertable');
	} 
}

//$('#evaluate-pass').click(function() {
//	evaluate(2);
//});
//
//$('#evaluate-reject').click(function() {
//	evaluate(3);
//});