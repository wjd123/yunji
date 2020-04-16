package com.wjd.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wjd.po.User;

public class UserDao {

	// 引入日志文件

	private Logger logger = LoggerFactory.getLogger(UserDao.class);

	public User findUserByUname(String uname) {

		// 1、定义sql语句
		String sql = "select * from tb_user where uname = ?";
		logger.info("数据库语句为" + sql);
		//logger.info("数据库语句为：{}", sql);
		// 2、设置参数集合
		List<Object> params = new ArrayList<Object>();
		params.add(uname);
		// 3、调用BaseDao的查询方法
		User user = (User) BaseDao.queryRow(sql, params, User.class);
		
		// 4、返回用户对象
		return user;

	}

	public User findUserByNickAndId(String nick, Integer userId) {
		// 1、定义sql语句
		
		String sql = "select * from tb_user where nick = ? and userId != ?";
		
		logger.info("数据库语句为：{}", sql);
		// 2、设置参数集合
		
		List<Object> params = new ArrayList<Object>();
		params.add(nick);
		params.add(userId);
		// 3、调用BaseDao的查询方法
		
		User user = (User)BaseDao.queryRow(sql, params, User.class);
		
		
		return user;
	}

	public Integer updateUser(User user) {
		// 1、定义sql语句
		String sql = "update tb_user set nick = ?,mood = ?,head = ? where userId = ?";
		
		// 2、设置参数集合
		List<Object> params = new ArrayList<Object>();
		
		params.add(user.getNick());
		params.add(user.getMood());
		params.add(user.getHead());
		params.add(user.getUserId());
		
		// 3、调用BaseDao的查询方法
		int row = BaseDao.executeUpdate(sql, params);
		
		
		return row;
	}
}
