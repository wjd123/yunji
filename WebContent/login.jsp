<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>云记登录</title>
<link href="statics/css/login.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="statics/login/css/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="statics/login/css/style.css">
<script src="statics/js/jquery-1.11.3.js" type=text/javascript></script>
<script src="statics/js/config.js" type=text/javascript></script>
<script type="text/javascript" src="statics/js/util.js"></script>

 
</head>
<body>


<div class="materialContainer">
	<div class="box">
		<div class="title">登录</div>
		
		<form action="userServlet" method="post" id="loginFrom">
			<input type="hidden" name="actionName" value="login" />
			<div class="input">
				<label for="name">用户名</label>
				<input type="text" name="userName" id="name" value="${resultInfo.result.uname }">
				<span class="spin"></span>
			</div>
			<div class="input">
				<label for="pass">密码</label>
				<input type="password" name="userPwd" id="pass" value="${resultInfo.result.upwd }">
				<span class="spin"></span>
			</div>
		</form>
		<br><br>
		
		
		<div style="margin:auto">
			<span style="text-align: center;display:block;color:red;font-size:12px" id="msg" >${resultInfo.msg }</span>
		</div>
		<div class="button login">
			<button onclick="checkLogin()">
				<span>登录</span>
				<i class="fa fa-check"></i>
			</button>
		</div>
		<a href="javascript:" class="pass-forgot">忘记密码？</a>
	</div>

	<div class="overbox">
		<div class="material-button alt-2">
			<span class="shape"></span>
		</div>
		<div class="title">注册</div>
		<div class="input">
			<label for="regname">用户名</label>
			<input type="text" name="regname" id="regname">
			<span class="spin"></span>
		</div>
		<div class="input">
			<label for="regpass">密码</label>
			<input type="password" name="regpass" id="regpass">
			<span class="spin"></span>
		</div>
		<div class="input">
			<label for="reregpass">确认密码</label>
			<input type="password" name="reregpass" id="reregpass">
			<span class="spin"></span>
		</div>
		<div class="button">
			<button>
				<span>注册</span>
			</button>
		</div>
	</div>

</div>

<script src="statics/login/js/jquery.min.js"></script>
<script src="statics/login/js/index.js"></script>

</body>

</html>
