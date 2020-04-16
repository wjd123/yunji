/**
 * 	"删除"按钮绑定点击事件（传递参数：类型ID）
		1、弹出提示框询问用户是否确认删除
		2、如果是，发送ajax请求后台（类型ID）
			resultInfo对象
			如果删除失败	code=0
				提示用户失败，msg=xxx	
			如果删除成功	code=1
				移除指定tr记录
					给table元素设置id属性值
					判断是否有多条类型记录
						1、通过id属性值，得到表格对象
						2、得到表格对象的子元素tr的数量
						3、判断tr的数量是否等于
							如果等于2，表示只有一条类型集合，删除整个表格，并设置提示内容
							如果大于2，表示有多条类型记录，删除指定tr对象
 * @param typeId
 */

function deleteType(typeId){
	// 弹出提示框询问用户是否确认删除
	swal({ 
		  title: "",  // 标题
		  text: "<h3>您确认要删除该记录吗？</h3>", // 内容
		  type: "warning", // 图标  error	  success	info  warning
		  showCancelButton: true,  // 是否显示取消按钮
		  confirmButtonColor: "orange", // 确认按钮的颜色
		  confirmButtonText: "确定", // 确认按钮的文本
		  cancelButtonText: "取消" // 取消按钮的文本
	}).then(function(){
		// 如果是，发送ajax请求后台（类型ID）
		$.ajax({
			type:"post",
			url:"typeServlet",
			data:{
				actionName:"delete",
				typeId:typeId
			},
			success:function(result){
				if(result.code == 1){
					// 提示成功
					swal("","<h2>删除成功！</h2>","success");
					// 执行成功的DOM操作
					deleteDom(typeId);
				}else{
					// 提示用户失败   swal("标题","内容","图标")
					swal("","<h3>"+result.msg+"</h3>","error");
				}
			}
				
		});
	});
}
	

function deleteDom(typeId){
	/* 1、移除指定tr记录  */
	// 判断tr的数量是否等于2
	var trlength = $("#myTable tr").length;
	
	if(trlength==2){
		$("#myTable").remove();
		$("#myDiv").html("<h2>暂未查询到类型记录！</h2>");
	}else{
		$("#tr_"+typeId).remove();
	}
	
	/* 2、删除左侧类型分组的导航栏列表项 */
	$("#li_" + typeId).remove();
	
}



//修改的模态框

function openUpdateDialog(typeId){
	// 1、修改模态框的标题
	$("#myModalLabel").html("修改类型");
	
	// 2、将当前要修改的tr记录的id和名称，设置到模态框的隐藏域和文本框中
	// 2.1 通过ID属性值，得到要修改的tr记录
	var tr = $("#tr_"+typeId);
	// 2.2 得到tr的具体单元格的值
	var typeName = tr.children().eq(1).text();
	// 2.3 将类型名称赋值给模态框中的文本框、将类型ID赋值给模态框中的隐藏域
	$("#typename").val(typeName);
	$("#typeId").val(typeId);
	// 3、清空上回提醒，并打开模态框
	$("#msg").html("");
	$("#myModal").modal("show");
}


//添加类别
$("#addBtn").click(function(){
	$("#myModalLabel").html("新增类型");
	
	//清空
	$("#typeId").val("");
	$("#typename").val("");
	// 3、打开模态框
	$("#msg").html("");
	$("#myModal").modal("show");
	
});


//提交更新

$("#btn_submit").click(function(){
	// 1、获取参数（文本框：类型名称、隐藏域：类型ID）
	var typeId = $("#typeId").val();
	var typeName = $("#typename").val();
	
	// 2、判断参数是否为空（类型名称）
	if (isEmpty(typeName)) {
		// 如果为空，提示信息，并return
		$("#msg").html("类型名称不能为空！");
		return;
	}
	
	// 3、发送ajax请求后台，添加或修改类型记录，回调函数返回resultInfo对象
	$.ajax(
	{
		type:"post",
		url:"typeServlet",
		data:{
			actionName:"addOrUpdate",
			typeId:typeId,
			typeName:typeName
	},
	success:function(result){
		if(result.code == 1){
			$("#myModal").modal("hide");
			// 判断typeId是否为空
			if (isEmpty(typeId)) {
					var key = result.result; // 后台添加记录成功后返回的主键
					// 如果为空，执行添加的DOM操作
					addDom(key,typeName);
					
					swal("","<h2>添加成功！</h2>","success");
			} else {
					// 如果不为空，执行修改的DOM操作
					updateDom(typeId,typeName);
					
					swal("","<h2>修改成功！</h2>","success");
			}
		}else {
			$("#msg").html(result.msg);
		}
	}
	
	});
	
	
});


//添加的DOM操作
function addDom(typeId,typeName){
	/* 1、添加tr记录  */
	// 拼接tr元素
	var tr = '<tr id="tr_'+typeId+'"><td>'+typeId+'</td><td>'+typeName+'</td>';
	tr += '<td><button class="btn btn-primary" type="button" onclick="openUpdateDialog('+typeId+')">修改</button>&nbsp;';
	tr += '<button class="btn btn-danger del" type="button" onclick="deleteType('+typeId+')">删除</button></td></tr>';
	
	// a、通过表格的id属性获取表格元素对象
	var myTable = $("#myTable");
	// b、判断表格对象的length是否大于0 （大于0 ，则表示表格存在；否则表格不存在）
	if (myTable.length > 0) {
		// c、如果表格存在，则拼接tr记录，将tr对象追加到table对象中
		myTable.append(tr);
	} else {
		// d、如果表格不存在，则拼接表格及tr对象，将表格设置到div中
		myTable = '<table class="table table-hover table-striped" id="myTable">';
		myTable += '<tbody> <tr> <th>编号</th> <th>类型</th> <th>操作</th> </tr>';
		myTable += tr + '</tbody></table>';
		
		//因为此时还有一个  提示查询不到的标签，所以替换而不是追加！！！
		$("#myDiv").html(myTable);
	}

	
	/* 2、左侧类型分组导航栏的列表项 */
	// 拼接li元素
	var li = '<li id="li_'+typeId+'"><a href=""><span id="sp_'+typeId+'">'+typeName+' </span><span class="badge">0</span></a></li>';
	// 追加到ul元素中
	$("#typeUl").append(li);
}


//执行修改的DOM操作
function updateDom(typeId,typeName){
	/* 1、修改指定tr记录  */
	// a、通过id选择器获取tr记录
	var tr = $("#tr_" + typeId);
	// b、修改指定单元格的文本值
	tr.children().eq(1).text(typeName);
	
	/* 2、左侧类型分组导航栏的列表项  */
	// 给左侧类型名添加span标签，并设置id属性值，修改该span元素的文本值	
	$("#sp_"+typeId).html(typeName);
}
