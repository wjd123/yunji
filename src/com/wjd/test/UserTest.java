package com.wjd.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.wjd.dao.UserDao;
import com.wjd.po.User;
/**
 * 1/不能有父类
 * 2/不能说静态方法
 * 3/不能有参数
 * 4/返回值为void
 * 
 * 红色失败
 * 绿色成功
 * 
 * “debug As”
 * @author Wang
 *
 */
public class UserTest {

	@Test
	public void test() {
		UserDao userDao = new UserDao();
		User user = userDao.findUserByUname("shsxt");
		if(user == null) {
			System.out.println("=========");
		}else {
			System.out.println(user.getUpwd());
		}
	}

}
