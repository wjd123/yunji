package com.wjd.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wjd.po.Note;
import com.wjd.po.User;
import com.wjd.po.vo.NoteVo;
import com.wjd.service.NoteService;
import com.wjd.util.Page;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/indexServlet")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//高亮
		request.setAttribute("menu_page", "index");

		//接受用户行为
		String actionName = request.getParameter("actionName");

		// 将用户行为存到request作用域中 （分页导航栏）
		request.setAttribute("action", actionName);
		
		if("searchTitle".equals(actionName)) {
			// 接收参数
			String title = request.getParameter("title");
			
			// 将条件值存到request作用域中
			request.setAttribute("title", title);
			
		    // 标题搜索   分页查询云记列表
			noteList(request,response,title,null,null);
						
		}else if("searchDate".equals(actionName)) {
			// 接收参数
			String date = request.getParameter("date");
			
			// 将条件值存到request作用域中
			request.setAttribute("date", date);
			
		    // 日期搜索   分页查询云记列表
			noteList(request,response,null,date,null);
		}else if("searchType".equals(actionName)) {
			// 接收参数
			String typeId = request.getParameter("typeId");
			
			// 将条件值存到request作用域中
			request.setAttribute("typeId", typeId);
						
		    // 类型搜索   分页查询云记列表
			noteList(request,response,null,null,typeId);
		}else {
			// TODO 分页查询云记列表
			noteList(request, response,null,null,null);

		}

		
		// 设置首页动态包含的页面
		request.setAttribute("changePage", "note/list.jsp");
		// 请求转发跳转到index.jsp
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	private void noteList(HttpServletRequest request, HttpServletResponse response, String title, String date, String typeId) {
		//
		// 1、接收参数（当前页、每页显示的数量）
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		
		// 2、获取session作用域中的用户对象，得到用户ID
		User user = (User) request.getSession().getAttribute("user");

		Page<Note> page = new NoteService().findNoteListBypage(pageNum,pageSize,user.getUserId(),title,date,typeId);
		
		request.setAttribute("page", page);
		
		
		
		
		
		// TODO 云记日期分组
		List<NoteVo> dateInfo = new NoteService().findNoteCountByDate(user.getUserId());
		// 将云记日期分组设置到session作用域
		request.getSession().setAttribute("dateInfo", dateInfo);
		
		
		// TODO 云记类型分组
		List<NoteVo> typeInfo = new NoteService().findNoteCountByType(user.getUserId());
		// 将云记类型分组设置到session作用域
		request.getSession().setAttribute("typeInfo", typeInfo);
		
	}

}
