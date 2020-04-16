	//登录页图片切换
	var currentindex=1;
	
	$(function(){
		$("#flash").css("background-image","url("+$("#flash1").attr("bgUrl")+")");//设置banner背景颜色名称 这里的flash就是div的ID
	});		
/**
 * 验证表单
 * @returns
 */
	function checkLogin() {	
//		var name = $("#userName").val();
//		var pwd = $("#userPwd").val();
//		
//		if(isEmpty(name)){
//			$("#msg").html("用户姓名不可为空哦！");
//			return;
//		}
//		if(isEmpty(pwd)){
//			$("#msg").html("密码不可为空哦！");
//			return;
//		}
		
		$("#loginFrom").submit();
	
	}
	
	
	
	function changeflash(i) {	
		currentindex=i;
		for (j=1;j<=2;j++){//此处的2代表你想要添加的幻灯片的数量与下面的5相呼应
			if (j==i){
				$("#flash"+j).fadeIn("normal");
				$("#flash"+j).css("display","block");
				$("#f"+j).removeClass();
				$("#f"+j).attr("class","dq");
				//alert("#f"+j+$("#f"+j).attr("class"))
				$("#flash").css("background-image","url("+$("#flash"+j).attr("bgUrl")+")");
			}else{
				$("#flash"+j).css("display","none");
				$("#f"+j).removeClass();
				$("#f"+j).attr("class","no");
			}
		}
	}
	function startAm(){
	timerID = setInterval("timer_tick()",2000);//8000代表间隔时间设置
	}
	function stopAm(){
	clearInterval(timerID);
	}
	function timer_tick() {
		currentindex=currentindex>=2?1:currentindex+1;//此处的5代表幻灯片循环遍历的次数
		changeflash(currentindex);}
	$(document).ready(function(){
	$(".flash_bar div").mouseover(function(){stopAm();}).mouseout(function(){startAm();});
	startAm();
	});
	
	//input
	$(function(){
		//鼠标焦点
		$(":input.user").focus(function(){
			$(this).addClass("userhover");                          
		}).blur(function(){
			$(this).removeClass("userhover")
		});
		$(":input.pwd").focus(function(){
			$(this).addClass("pwdhover");                          
		}).blur(function(){
			$(this).removeClass("pwdhover")
		});
		
		//输入用户名
		$(".user").focus(function(){
			var username = $(this).val();
			if(username == ''){
			   $(this).val('');
			}
		 });
		 $(".user").blur(function(){
			var username = $(this).val();
			if(username == ''){
			   $(this).val('');
			}
		 });
		 
		 //输入密码
		 $(".pwd").focus(function(){
			var password = $(this).val();
			if(password == ''){
			   $(this).val('');
			}
		 });
		 $(".pwd").blur(function(){
			var password = $(this).val();
			if(password == ''){
			   $(this).val('');
			}
		 });
	
	});
	