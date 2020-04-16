package com.wjd.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wjd.po.User;

/**
 * 1非法访问拦截;
 * 2免登录
 * 
 * 
 * 1、非法访问拦截：
 * 		1）拦截资源：
 * 			所有资源：
 *			1、
 * 			2、
 * 			3、
 * 			4、
 * 		2）放行指定页面：
 * 			1、指定页面：登录、注册等
 * 			2、静态资源：js、css、images
 * 			3、指定行为放行：用户无需登录即可指定的操作，例如：登录userServlet？actionName=login、
 * 			4、登陆状态放行（判断session作用域中是否存在用户信息；）
 * 
 * 
 * 2、免登录Cookie对象（注：请求转发不会被拦截器 拦截的！请求转发是不会被拦截器拦截的！）
 * 		当用户处于未登录状态，且请求需要登录才能访问的资源时
 * 		目的：
 * 			让用户处于登录状态
 * 		实现方法：
 * 			1、从cookie中获取uname和 密码，自动登录
 * 			2、从cookie中获取uname和 密码，自动登录
 * 			3、从cookie中获取uname和 密码，自动登录
 * 			4、从cookie中获取uname和 密码，自动登录
 * 			
 */
@WebFilter("/*")
public class LoginAccessFilter implements Filter {

    /**
     * Default constructor. 
     */
    public LoginAccessFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		//基于Http
		HttpServletRequest request = (HttpServletRequest)arg0;
		HttpServletResponse response = (HttpServletResponse)arg1;
		
		//得到访问的路径
		String path = request.getRequestURI();//站点名加/资源路径
		
		//判断是否是需要方形的页面
		if(path.contains("/login.jsp")) {
			chain.doFilter(request, response);
			return;
		}
			
		if(path.contains("/statics")) {
			chain.doFilter(request, response);
			return;
		}
		
		//指定行为放行,请求登录行为
		if(path.contains("/userServlet")) {
			String actionName = request.getParameter("actionName");
			if("login".equals(actionName)) {
				chain.doFilter(request, response);
				return;
			}
		}
		
		//登录状态，放行
		User user = (User)request.getSession().getAttribute("user");
		if(user != null) {
			chain.doFilter(request, response);
			return;
		}
		
		//判断上回是否记住了密码（cookie是否存在）
		Cookie[] cookies = request.getCookies();
		
		//判断获取到的cookie是否为空
		if(cookies != null && cookies.length > 0) {
			//判断获取到的cookie是否是user
			for(Cookie cookie : cookies) {
				if("user".equals(cookie.getName())) {
					String value = cookie.getValue();
					//拆分用户名和密码
					String[] val = value.split("-");
					
					String uname = val[0];
					
					String upwd = val[1];
					
					//请求转发跳转到登录操作
					String url = "userServlet?actionName=login&rem=1&userName="+uname+"&userPwd="+upwd;
					request.getRequestDispatcher(url).forward(request, response);
					return;
				}
			}
		}
		//否则跳转到登录页面
		response.sendRedirect("login.jsp");
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
