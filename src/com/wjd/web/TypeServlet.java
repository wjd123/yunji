package com.wjd.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wjd.po.NoteType;
import com.wjd.po.User;
import com.wjd.po.vo.ResultInfo;
import com.wjd.service.TypeService;
import com.wjd.util.JsonUtil;

/**
 * Servlet implementation class TypeServlet
 */
@WebServlet("/typeServlet")
public class TypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private TypeService typeService = new TypeService();
	
	
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 设置高亮
		request.setAttribute("menu_page", "type");
		String actionName = request.getParameter("actionName");
		
		if("list".equals(actionName)) {
			
			typeList(request,response);
			
		}else if("delete".equals(actionName)) {
			
			deleteType(request,response);

		}else if("addOrUpdate".equals(actionName)) {
			
			addOrUpdateType(request,response);

		}
		
	}


	private void addOrUpdateType(HttpServletRequest request, HttpServletResponse response) {
		// 
		String typeId = request.getParameter("typeId");
		String typeName = request.getParameter("typeName");
		
		User user = (User)request.getSession().getAttribute("user");
		
		ResultInfo<Integer> resultInfo = typeService.addOrUpdate(typeId,typeName,user.getUserId());
		
		
		
		JsonUtil.toJson(response, resultInfo);
		
		
	}


	private void deleteType(HttpServletRequest request, HttpServletResponse response) {
		//接受参数
		String typeId = request.getParameter("typeId");
		
		ResultInfo<NoteType> info = typeService.deleteType(typeId);
		
		JsonUtil.toJson(response, info);
	}


	private void typeList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 得到User
		User user = (User)request.getSession().getAttribute("user");
		
		List<NoteType> typeList = typeService.findTypeListByUserId(user.getUserId());
		
		request.setAttribute("typeList", typeList);
		
		//设置页面动态包含值
		request.setAttribute("changePage", "type/list.jsp");
		
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

}
