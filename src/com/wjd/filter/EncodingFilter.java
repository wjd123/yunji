package com.wjd.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求乱码解决
  		乱码原因：
  			服务器默认解析编码为ISO-8859-1，不支持中文。
  			
 		乱码情况：
 						Tomcat8及以上版本								Tomcat7及以下版本
 			GET请求		不乱码										乱码
 						不需要处理										new String(request.getParamater(name),"UTF-8");
 			
 			POST请求		乱码											乱码
 						request.setCharacterEncoding("UTF-8");		request.setCharacterEncoding("UTF-8");
 		
 		解决方案：
 			POST请求：
 				无论什么版本的服务器，POST请求都会出现乱码问题，需要通过request.setCharacterEncoding("UTF-8")设置编码格式。
 			GET请求：
 				Tomcat8及以上版本不需要做处理，Tomcat7及以下版本需要单独处理。
 					new String(request.getParamater(name).getBytes("ISO-8859-1"),"UTF-8");
 			
 				
 				
 */

@WebFilter("/*")
public class EncodingFilter implements Filter {

  
    public EncodingFilter() {
        
    }

    public void init(FilterConfig fConfig) throws ServletException {
		
	}
	
	public void destroy() {
		
	}

	
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain) throws IOException, ServletException {
		
		// 基于HTTP
		HttpServletRequest request = (HttpServletRequest)arg0;
		HttpServletResponse response = (HttpServletResponse)arg1; 
		
		// 处理POST请求  （只针对于POST请求有效，GET请求不受影响）
		request.setCharacterEncoding("UTF-8");
		
		// 得到请求类型  （GET|POST）
		String method = request.getMethod();
		
		// 如果是GET请求，则判断服务器的版本
		if ("GET".equalsIgnoreCase(method)) { // 忽略大小写比较
			// 得到服务器的版本
			String serverInfo = request.getServletContext().getServerInfo(); // Apache Tomcat/8.0.45
			// 通过截取字符串，得到具体的版本号
			String version = serverInfo.substring(serverInfo.lastIndexOf("/")+1, serverInfo.indexOf("."));
			// 判断服务器版本是否是Tomcat8以下版本（不包含Tomcat8）
			if (version != null && Integer.parseInt(version) < 8) {
				// Tomcat7及以下版本的GET请求
				MyWapper myRequest = new MyWapper(request);
				// 放行资源
				chain.doFilter(myRequest, response);
				return;
			}
		}
		
		// 放行资源
		chain.doFilter(request, response);
	}

	
	
	
	/**
	 * 1、定义内部类 （本质是request对象）
	 * 2、继承request对象的包装类HttpServletRequestWrapper
	 * 3、重写getParameter()方法
	 * @author Lisa Li
	 *
	 */
	class MyWapper extends HttpServletRequestWrapper {
		
		// 定义成员变量  HttpServletRequest对象（提升request对象的作用域）
		private HttpServletRequest request;

		/**
		 * 带参构造
		 * @param request
		 */
		public MyWapper(HttpServletRequest request) {
			super(request);
			this.request = request;
		}

		/**
		 * 处理乱码问题
		 */
		@Override
		public String getParameter(String name) { // name是参数名称
			// 获取参数 （乱码的参数值）
			String value = request.getParameter(name); 
			// 判断参数值是否为空
			if (value == null || "".equals(value)) {
				return value;
			}
			// 通过new String()处理乱码
			try {
				value = new String(value.getBytes("ISO-8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return value;
		}
		
	}
	
	

}
