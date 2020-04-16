package com.wjd.test;

import org.junit.Test;

import com.wjd.dao.UserDao;
import com.wjd.po.User;

public class Test01 {
	@Test
	public void test() {
		long i = 100L;
		Object o = "sss";
		
		long l = (long)o;
		System.out.println(l);
	}
	
}
