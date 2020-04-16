package com.wjd.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

/**
 * 将对象转换成json格式的字符串，响应给ajax的回调函数
 * @author Lisa Li
 *
 */
public class JsonUtil {
	
	public static void toJson(HttpServletResponse response, Object object) {
		
		try {
			// 1、设置响应类型及编码格式 （json格式）
			response.setContentType("application/json;charset=UTF-8");
			// 2、得到字符输出流
			PrintWriter out = response.getWriter();
			// 3、通过fastjson的方法将resultInfo对象转换成json字符串
			String json = JSON.toJSONString(object);
			// 4、通过输出流输出json格式的字符串
			out.write(json);
			// 5、关闭资源
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
