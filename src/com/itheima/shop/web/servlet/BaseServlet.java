package com.itheima.shop.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class BaseServlet extends HttpServlet {

	/**
	 * 重写父类的service方法
	 */
	@Override
	public void service(ServletRequest req, ServletResponse rep)
			throws ServletException, IOException {
		try {
		// 把父对象转换为子对象
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) rep;
		// 利用反射的技术调用子类的方法
		// 获取方法名
		String action = request.getParameter("action");
		
		// 获取类对象
		Class clazz = this.getClass();
		// 调用方法
		
		Method method = clazz.getMethod(action, HttpServletRequest.class,HttpServletResponse.class);
		method.invoke(this,request,response );
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
