/*
 * 个人排期管理
 */
// global vars
col = -1;
proj_no = '';
proj_name = '';
schd_phase = 0;
phase_time = 0;

proj_schd = '';

last_col = -1;
last_proj_no = '';

var weeks;
var selfSchdColModels = [{
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
		width : 60,
		align : 'center'
	}, {
		display : '项目名称',
		name : 'proj_name',
		width : 280
	} ];

function getProjInfo(id) {
	var row = document.getElementById('selfschdgrid').rows[id-1];
    var td = row.cells;
    proj_no = $(td[2]).text();
    proj_name = $(td[3]).text();
    proj_schd = $(td[col + 4]).text();
}

function getColumnId(tdDiv, id) { // start from 0 of schedule data
	var row = document.getElementById('selfschdgrid').rows[id-1];
    var td = row.cells;
    for (j = 0; j < td.length; j++) {
    	if ($(td[j]).children(0).is($(tdDiv)))
    		return j - 4;       
    }
    return str;
}

function editCell(tdDiv, id) {
	if (getCookie('emp_yst_id') == '') {
		$('#loginModal').modal('show');
	}
	var value = $(tdDiv).html();
	$(tdDiv).click(function () {
		col = getColumnId(tdDiv, id);
		getProjInfo(id);
		
		document.form_cell_schd.proj_no.value = proj_no;
		document.form_cell_schd.proj_name.value = proj_name;
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
$('#selfschd-li').addClass('active');
$('#top-container').html('<h4>个人排期管理</h4>');

if (getCookie('emp_yst_id') == '') {
	$('#loginModal').modal('show');
} else {
	$.post('getprojschd', function (data) {
		if (data == '')
			return;
		weeks = data.split('|');
		var len = selfSchdColModels.length;
		for (var i = 0; i < weeks.length; i++) {
			selfSchdColModels[len+i] = new Object();
			selfSchdColModels[len+i].display = weeks[i];
			selfSchdColModels[len+i].name = 'week' + (i+1);
			selfSchdColModels[len+i].width = 80;
			selfSchdColModels[len+i].process = editCell;
		}
		var Handle = function(com, grid) {
			
		};
		$("#selfschdgrid").flexigrid( {
			url : 'slctempschd',
			dataType : 'json',
			colModel : selfSchdColModels,
			qtype : 'self',
			query : getCookie('emp_yst_id'),
			usepager : true,
			rp : 20,
			rpOptions: [20, 30, 50, 100],
			title : '个人排期表',
			nomsg : '没有项目',
			showTableToggleBtn : false,
			singleSelect : true,
			width : 'auto',
			height : document.documentElement.clientHeight - 280
		});
	});
}

$('#dropdown-chg').click(function() {
	$('.my-dropdown').hide();
	document.form_cell_schd_chg.proj_no.value = proj_no;
	document.form_cell_schd_chg.proj_name.value = proj_name;
	
	if (proj_schd.indexOf('/') < 0) {
		$('#glob-info').html('<h3>没有排期信息</h3>');
		$('#infoModal').modal('show');
		setTimeout(function() {
			$('#infoModal').modal('hide');
			$('#glob-info').empty().removeClass('text-success').addClass('text-error');
		}, 1200);
		return;
	}
//	var schds = proj_schd.split(',');
	$('#schd-chg-form-phases').empty();
//	for (var i = 0; i < schds.length; i++) {
//		var schd_info = schds[i].split('/');
//		$('#schd-chg-form-phases').append(
//			'<p>项目阶段 <select id="phase_select_' + i + '" class="span3">' +
//			'<option>需求</option><option>开发</option><option>UT</option><option>ST</option>' +
//			'<option>UAT</option><option>上线</option><option>运维</option><option>评审</option>' +
//			'</select>　投入时间 <input type="text" class="span2" value="' + schd_info[1] + 
//			'" /><a class="btn btn-small schd-del-btn" href="javascript: schd_del(' + i + ');"><i class="icon-remove"></i></a></p>');
//		$('#phase_select_' + i).val(schd_info[0]);
//	}
	$.post('showchgempschd', {
		emp_yst_id : getCookie('emp_yst_id'),
		col_id : col,
		proj_no : proj_no
	}, function(data) {
		console.log(data);
		var schdJson = JSON.parse(data);
		for (var i = 0; schdJson.rows[i] != null; i++) {			
			$('#schd-chg-form-phases').append(
				'<p>项目阶段 <select id="phase_select_' + i + '" class="span3">' +
				'<option>需求</option><option>设计</option><option>开发</option><option>UT</option><option>ST</option>' +
				'<option>UAT</option><option>上线</option><option>运维</option><option>评审</option>' +
				'</select>　投入时间 <input type="text" class="span2" value="' + schdJson.rows[i].devoteHours + 
				'" /><a class="btn btn-small schd-del-btn" href="javascript: schd_del(' + i + ');"><i class="icon-remove"></i></a></p>');
			$('#phase_select_' + i).val(schdJson.rows[i].phaseName);
		}
	});
	
	$('#cellSchdChgModal').modal('show');
});

$('#dropdown-add').click(function() {
	$('.my-dropdown').hide();
	$('#cellSchdModal').modal('show');
});

function schd_del(i) {
	$('#schd-chg-form-phases').children('p').eq(i).remove();
}

$('#cell-schd-ok').click(function() {
	schd_phase = form_cell_schd.proj_phase.selectedIndex;
	phase_time = form_cell_schd.phase_time.value;

	if (phase_time == '') {
		$('#cell-schd-info').text('请填写投入时间');
		return false;
	} else if (isNaN(parseFloat(phase_time))) {
		$('#cell-schd-info').text('时间请输入数字');
		return false;
	}
	
	$.post('addempschd', {
		proj_no : proj_no,
		col_id : col,
		phase_id : schd_phase + 1,
		devote_hours : phase_time,
		emp_yst_id : getCookie('emp_yst_id')
	}, function(data) {
		if (data == 'true') {
			form_cell_schd.phase_time.value = '';
			$('#cellSchdModal').modal('hide');
			$("#selfschdgrid").flexReload();
		} else if (data == 'duplicateProjPhase') {
			$('#cell-schd-info').text('排期已存在');
		} else {
			$('#cell-schd-info').text('添加失败');
		}
	});	
	
	return false;
});

$('#cell-schd-chg-ok').click(function() {
	var phase_json = '[';
	var selects = $('#schd-chg-form-phases').find('select');
	var inputs = $('#schd-chg-form-phases').find('input');
	
	for (var i = 0; i < selects.length; i++) {
		var devote_hours = inputs[i].value == '' ? 0 : inputs[i].value;
		phase_json += '{"phase_id":' + (selects[i].selectedIndex + 1) + ',"devote_hours":' + devote_hours + '},';
	}
	phase_json = phase_json.substr(0, phase_json.length - 1);
	phase_json += ']';
	if (selects.length == 0) {
		phase_json = '[]';
	}
	$.post('chgempschd', {
		proj_no : proj_no,
		col_id : col,
		emp_yst_id : getCookie('emp_yst_id'),
		phase_json: phase_json
	}, function(data) {
		if (data == 'true') {
			$('#cellSchdChgModal').modal('hide');
			$("#selfschdgrid").flexReload();
			$('#cell-schd-chg-info').text('');
		} else {
			$('#cell-schd-chg-info').text('修改失败');
		}
	});
});

$('#phase_time').focus(function() {
	$('#cell-schd-info').text('');
});
