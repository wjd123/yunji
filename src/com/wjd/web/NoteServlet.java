package com.wjd.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wjd.po.Note;
import com.wjd.po.NoteType;
import com.wjd.po.User;
import com.wjd.po.vo.ResultInfo;
import com.wjd.service.NoteService;
import com.wjd.service.TypeService;

/**
 * Servlet implementation class NoteServlet
 */
@WebServlet("/noteServlet")
public class NoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NoteService noteService = new NoteService();
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		request.setAttribute("menu_page", "note");
		String actionName = request.getParameter("actionName");
		
		if("view".equals(actionName)) {
			
			viewNote(request,response);
		}else if("addOrUpdate".equals(actionName)) {
			
			addOrUpdate(request,response);
		}else if("detail".equals(actionName)) {
			
			noteDetail(request,response);
		}else if("delete".equals(actionName)) {
			
			noteDelete(request,response);
		}
	}
	
	private void noteDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 
		String noteId = request.getParameter("noteId");
		
		int code = noteService.deleteNote(noteId);
		
		// 3、通过流将结果响应给ajax的回调函数
		response.getWriter().write(code + "");
		response.getWriter().close();
		
	}

	private void noteDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 
		String noteId = request.getParameter("noteId");
		
		Note note = noteService.findNoteById(noteId);
		
		request.setAttribute("note", note);
		
		request.setAttribute("changePage", "note/detail.jsp");
		
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	//添加或修改
	private void addOrUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		// TODO Auto-generated method stub
		String typeId = request.getParameter("typeId");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		
		String noteId = request.getParameter("noteId");
		
		ResultInfo<Note> resultInfo = noteService.addOrUpdate(typeId, title, content,noteId);
		
		
		if(resultInfo.getCode() == 1) {
			response.sendRedirect("indexServlet");
		}else {
			request.setAttribute("resultInfo", resultInfo);
			request.getRequestDispatcher("noteServlet?actionName=view").forward(request, response);
		}
	}
	
	
	private void viewNote(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 
		/* 修改操作 */
		// 得到要修改的云记ID
		String noteId = request.getParameter("noteId");
		// 查询note对象
		Note note = noteService.findNoteById(noteId);
		// 将对象存到request域对象中
		request.setAttribute("noteInfo", note);
		/* 修改操作 */
		
		
		
		
		//获取用户，此用户的（因为要用到它的列表）
		User user = (User)request.getSession().getAttribute("user");
		
		Integer userId = user.getUserId();
		
		List<NoteType> typeList = new TypeService().findTypeListByUserId(userId);
		
		request.setAttribute("typeList", typeList);
		
		// 设置首页动态包含的页面值
		request.setAttribute("changePage", "note/view.jsp");
		// 请求转发跳转到index.jsp
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

}
