<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="col-md-9">


	<div class="data_list">
		<div class="data_list_title">
			<span class="glyphicon glyphicon-edit"></span>&nbsp;个人中心
		</div>
		<div class="container-fluid">
			<div class="row" style="padding-top: 20px;">
				<div class="col-md-8">
					<form class="form-horizontal" method="post"
						action="userServlet"
						enctype="multipart/form-data" onsubmit="return checkUser();">
						<div class="form-group">
							<input type="hidden" name="actionName" value="updateUser"> <label
								for="nickName" class="col-sm-2 control-label">昵称:</label>
							<div class="col-sm-3">
								<input class="form-control" name="nick" id="nickName"
									placeholder="昵称" value="${user.nick }">
							</div>
							<label for="img" class="col-sm-2 control-label">头像:</label>
							<div class="col-sm-5">
								<input type="file" id="img" name="img">
							</div>
						</div>
						<div class="form-group">
							<label for="mood" class="col-sm-2 control-label">心情:</label>
							<div class="col-sm-10">
								<textarea class="form-control" name="mood" id="mood" rows="3">${user.mood }</textarea>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="submit" id="btn" class="btn btn-success">修改</button>
								&nbsp;&nbsp;<span style="color: red" id="msg"></span>
							</div>
						</div>
					</form>
				</div>
				<div class="col-md-4">
					<img style="width: 260px; height: 200px" src="userServlet?actionName=userHead&imageName=${user.head }">
				</div>
			</div>
		</div>
	</div>
	<script>
		$("#nickName").blur(function(){
			// 1、获取昵称文本框的值
			var nickName = $("#nickName").val();
			// 2、判断值是否为空
			if (isEmpty(nickName)) {
				// 提示用户
				$("#msg").html("用户昵称不能为空！");
				// 禁用按钮
				$("#btn").prop("disabled",true);
				return;
			}
			// 3、判断昵称是否做了修改
			// 获取session作用域中的用户昵称（只能在jsp页面获取，无法在js页面中获取）
			var nick = '${user.nick}';
			// 如果获取到昵称与session中的昵称一致，则直接return
			if (nickName == nick) {
				console.log("一样,没改！");
				return;
			}
			// 4、如果昵称做了修改，则发送ajax请求，验证昵称是否可用
			$.ajax({
				type:"get",
				url:"userServlet",
				data:{
					actionName:"checkNick",
					nick:nickName
				},
				success:function(code){
					// 返回code，1=可用，0=不可用
					if (code == 1) {
						// 如果可用，清空提示信息，按钮可用
						$("#msg").html("");
						$("#btn").prop("disabled",false);
					} else {
						// 如果不可用，提示用户，并禁用按钮
						$("#msg").html("该昵称已存在，请重新输入！");
						$("#btn").prop("disabled",true);
					}
				}
			});
		}).focus(function(){
			// 1、清空提示信息
			$("#msg").html("");
			// 2、按钮可用
			$("#btn").prop("disabled",false);
		});
	</script>
</div>
