package com.wjd.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.wjd.dao.UserDao;
import com.wjd.po.User;
import com.wjd.po.vo.ResultInfo;
import com.wjd.util.MD5Util;
import com.wjd.util.StringUtil;

import sun.reflect.generics.visitor.Reifier;

public class UserService {
	private UserDao userDao = new UserDao(); 
	
	public ResultInfo<User> userLogin(String uname,String upwd){
		ResultInfo<User> resultInfo = new ResultInfo<User>();
		
		//首先回显对象
		User u = new User();
		u.setUname(uname);
		u.setUpwd(upwd);
		resultInfo.setResult(u);
		
		//判断是否为空
		if(StringUtil.isEmpty(uname) || StringUtil.isEmpty(upwd)) {
			//若为空，将状态码及原因设置到结果对象中
			resultInfo.setCode(0);
			resultInfo.setMsg("name和密码不能为空哦！");
			
			return resultInfo;
		}
		
		
		//若不为空，则查询;
		//
		User u1 = userDao.findUserByUname(uname);
		
		if(u1 == null) {	//如果未查到对象，失败回显！
			resultInfo.setCode(0);
			resultInfo.setMsg("该用户不存在哈！");
			return resultInfo;
		}
		
		//若查询到用户，则比较密码
		
		upwd = MD5Util.encode(MD5Util.encode(upwd));
		
		if(upwd.equals(u1.getUpwd())) {
			resultInfo.setCode(1);
			resultInfo.setResult(u1);
			
			
		}else {
			resultInfo.setCode(0);
			resultInfo.setMsg("密码不对哦！");
		}
		
			
		
		return resultInfo;
	}



	public Integer checkNike(String nick, Integer userId) {
		// 1、判断昵称是否为空
		if (StringUtil.isEmpty(nick)) {
			return 0;
		}
		
		User user = userDao.findUserByNickAndId(nick,userId);
		
		if(user == null) {
			
			return 1;
		}
		return 0;
	}



	public ResultInfo<User> updateUser(HttpServletRequest request) {
		ResultInfo<User> resultInfo = new ResultInfo<User>();
		// 获取参数
		String nick = request.getParameter("nick");
		String mood = request.getParameter("mood");
		
		//1判断参数是否为空
		if(StringUtil.isEmpty(nick)) {
			resultInfo.setCode(0);
			resultInfo.setMsg("用户昵称不可为空！");
			return resultInfo;
		}
		
		//获取session中的用户对象
		User user = (User)request.getSession().getAttribute("user");
		
		//设置心情和昵称
		user.setMood(mood);
		user.setNick(nick);

		//上传头像
		
		try {
			Part part = request.getPart("img");
			//获取文件名
			String fileName = part.getSubmittedFileName();
			//判断文件名是否为空
			if(!StringUtil.isEmpty(fileName)) {
				
				// head = fileName; // 如果传了头像，则使用新上传的头像名
				user.setHead(fileName);
				
				String filePath = request.getServletContext().getRealPath("/WEB-INF/upload/");
				
				//上传文件
				part.write(filePath+fileName);
			}
			
		} catch (IOException | ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//调用dao层的更新方法
		Integer row = userDao.updateUser(user);
		
		if(row > 0) {
			resultInfo.setCode(1);
			request.getSession().setAttribute("user",user);
		}else {
			resultInfo.setCode(0);
			resultInfo.setMsg("更新失败！");
		}
		
		
		
		
		return resultInfo;
	}
}
