package com.wjd.web;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

import com.wjd.po.User;
import com.wjd.po.vo.ResultInfo;
import com.wjd.service.UserService;

/**
 * Servlet implementation class UserServlet
 */

@MultipartConfig                   // 一定要加注解！！！！！
@WebServlet("/userServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserService userService = new UserService();
	/**
	 * 用户模块
		通过判断用户行为的值调用不同的方法
		用户行为名称			actionName
		用户登录				actionName="login"
		用户列表				actionName="list"
		用户删除				actionName="delete"
		用户修改				actionName="update"
		用户添加				actionName="add"
		用户退出				actionName="logout"
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		// 设置首页高亮值
		request.setAttribute("menu_page", "user");
		//判断接受表单的活动名字
		
		String actionName = request.getParameter("actionName");
		
		if("login".equals(actionName)) {
			//用户登录
			userLogin(request,response);
			
		}else if("userCenter".equals(actionName)) {
			//进入个人中心
			userCenter(request,response);
		}else if("userHead".equals(actionName)) {
			//加载头像
			userHead(request,response);
		}else if("checkNick".equals(actionName)) {
			// 验证昵称的唯一性
			checkNick(request, response);
		}else if("updateUser".equals(actionName)) {
			// 修改用户信息
			updateUser(request, response);
		}else if("delete".equals(actionName)) {
			
		}else if("update".equals(actionName)) {
			
		}else if("add".equals(actionName)) {
			
		}else if("logout".equals(actionName)) {
			logout(request,response);
		}
		
		
	}
	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		request.getSession().setAttribute("user", null);
		
		Cookie cookie = new Cookie("user", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		
		response.sendRedirect("login.jsp");
	}
	
	
	private void updateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 修改用户信息
		request.setCharacterEncoding("UTF-8");
		ResultInfo<User> resultInfo = userService.updateUser(request);
		request.setAttribute("resultInfo", resultInfo);
		request.getRequestDispatcher("userServlet?actionName=userCenter").forward(request, response);
	}
	
	
	
	
	private void checkNick(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 1、获取参数（昵称）
		String nick = request.getParameter("nick");
		// 2、从session作用域中获取用户Id
		User user = (User) request.getSession().getAttribute("user");
		
		// 3、调用Service层的方法，得到返回的结果
		Integer code = userService.checkNike(nick,user.getUserId());
		
		// 4、通过字符输出流将结果响应给前台的ajax的回调函数
		response.getWriter().write(code + "");
		// 5、关闭资源
		response.getWriter().close();
		
	}
	private void userHead(HttpServletRequest request, HttpServletResponse response) throws IOException {

		//加载头像
		//1、获取参数的值（图片名）
		String imageName = request.getParameter("imageName");
		//2、得到图片存放的路径	request.getServletContext().getRealPath("/");
		String realPath = request.getServletContext().getRealPath("/WEB-INF/upload/");
		//3、通过图片的完整路径得到file对象
		File file = new File(realPath+imageName);
		//4、通过截取，得到图片的后缀名
		String pic = imageName.substring(imageName.lastIndexOf(".")+1);
		
		//5、通过不同的后缀名设置不同的响应类型
		
		if("PNG".equalsIgnoreCase(pic)) {
			response.setContentType("image/png");
		}if("JPG".equalsIgnoreCase(pic) || "JPEG".equalsIgnoreCase(pic) ) {
			response.setContentType("image/jpeg");
		}if("GIF".equalsIgnoreCase(pic)) {
			response.setContentType("image/gif");
		}
		
		//6、利用FileUtils的copyFile()方法，将图片拷贝给客户端
		FileUtils.copyFile(file, response.getOutputStream());
		 
	}
	
	private void userCenter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 设置首页动态包含的页面值
		request.setAttribute("changePage", "user/info.jsp");
		
		request.getRequestDispatcher("index.jsp").forward(request, response);
		
	}
	private void userLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String uname = request.getParameter("userName");
		String upwd = request.getParameter("userPwd");
		
		//调用userService的userLogin(uname, upwd)方法
		ResultInfo<User> resultInfo = userService.userLogin(uname, upwd);
		
		//判断是否登录成功
		//如果失败，把失败的resultInfo设置到request域对象中
		if(resultInfo.getCode() == 0) {
			request.setAttribute("resultInfo", resultInfo);
			//请求转发到login.jsp
			request.getRequestDispatcher("login.jsp").forward(request, response);
			
		}else {   //否则成功
			
			//将对象存到Session作用域中
			request.getSession().setAttribute("user", resultInfo.getResult());
			
			request.setAttribute("resultInfo", resultInfo);
			//判断是否记住密码
			String rem = request.getParameter("rem");
			if("1".equals(rem)) {
				//若是
				Cookie cookie = new Cookie("user", uname+"-"+upwd);
				//设置响应时间并响应给客户端
				cookie.setMaxAge(3*24*60*60);
				response.addCookie(cookie);
			}else{
				//否则，清空cookie
				Cookie cookie = new Cookie("user",null);
				
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
			
			//login成功重定向跳转到index页面
			response.sendRedirect("indexServlet");
			
		}
	}
}
