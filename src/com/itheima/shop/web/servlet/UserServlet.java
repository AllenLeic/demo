package com.itheima.shop.web.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.itheima.shop.entity.User;
import com.itheima.shop.exception.EmailCanNotNullException;
import com.itheima.shop.exception.LoginInfoIncorrectException;
import com.itheima.shop.exception.PasswordCanNotNullException;
import com.itheima.shop.exception.TelephoneCanNotNullException;
import com.itheima.shop.exception.UserExistsException;
import com.itheima.shop.exception.UserNameCanNotNullException;
import com.itheima.shop.service.UserService;
import com.itheima.shop.util.CommonUtil;
import com.itheima.shop.util.Md5Util;

/**
 * 这里继承baseservlet,方便baseservlet调用方法
 * 调用这个类时,会先实例这个类父类构造方法
 * @author AllenLei
 * @date 2018年3月17日 下午9:19:09
 */
@WebServlet("/userServlet")
public class UserServlet extends BaseServlet {

	// 实例化业务逻辑层
	private UserService userService = new UserService();
	
	/**
	 * 处理注销的请求
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void loginOut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.将会话域里面的东西销毁,就是注销了
		request.getSession().setAttribute(CommonUtil.USER_LOGINUSER, null);
		// 放回首页
		response.sendRedirect(request.getContextPath() + "/default.jsp");
	}
	/**
	 * 处理用户登录请求
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 1. 判断验证码是否正确
			// 先获取传入进来的验证码 如果不为空的话才验证
			// 判断如果传入的用户名不为空,且勾选了记住用户名的话,把原始的用户名和密码写入cookies
			
			if ("true".equals(request.getAttribute("remember"))) {
				
			}
			String checkCode = request.getParameter("checkCode");
			// 获取servlet生成的验证码
			String sysCheckCode = (String) request.getSession().getAttribute(CommonUtil.CHECKCODE_SERVER);
			// 1.1 如果错误,直接返回错误信息
			if (checkCode == null || "".equals(checkCode.trim()) || !sysCheckCode.equalsIgnoreCase(checkCode)) {
				request.setAttribute("errorMsg", "验证码不正确");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}
			// 2. 封装传入进来的数据
			User user = new User();
			
			Map<String, String[]> map = request.getParameterMap();
			BeanUtils.populate(user, map);
			// 密码加密
			user.setPassword(Md5Util.encodeByMd5(user.getPassword()));
			// 3. 调用业务层获取登录用户状态的全部信息
			User loginUser = userService.login(user);
			// 将信息存到回话域中
			request.getSession().setAttribute(CommonUtil.USER_LOGINUSER, loginUser);
			// 再重定向到,登录页面,登录页面从会话域中获取账户的全部信息
			response.sendRedirect(request.getContextPath() + "/default.jsp");
			
		// 对异常信息的处理
			
		} catch (PasswordCanNotNullException e) {
			request.setAttribute("errorMsg", e.getMessage());
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		} catch (UserNameCanNotNullException e) {
			request.setAttribute("errorMsg", e.getMessage());
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		} catch (LoginInfoIncorrectException e) {
			request.setAttribute("errorMsg", e.getMessage());
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
	}
	
	/**
	 * 处理用户邮箱传过来的激活信息
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void active(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 1. 获取传入进来的邮箱验证码
			String code = request.getParameter("code");
			// 2. 调用业务层完成激活业务
			userService.active(code);
			System.out.println(code);
			// 3. 判断激活的结果
			// 3.1 如果激活成功,则重定向到登录页面
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			// 3.2 如果激活不成功,则是发生了异常,给出提示: 激活失败 
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("激活失败,激活码错误,或请稍后再试");
		}
	}
	
	/**
	 * 处理注册的请求,使用有刷新模式,不用ajax
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			// 1. 判断一下验证码是否正确 
			// 先获取传入进来的验证码 如果不为空的话才验证
			String checkCode = request.getParameter("checkCode");
			// 获取servlet生成的验证码
			String sysCheckCode = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
			// 如果验证码不为空
			if (checkCode != null && !"".equals(checkCode)) {
				// 如果验证码正确,则调用业务层处理注册业务
				if (sysCheckCode.equalsIgnoreCase(checkCode)) {
					// 1.1 封装传入进来的数据
					Map<String, String[]> map = request.getParameterMap();
					User user = new User();
					BeanUtils.populate(user, map);
					// 2. 调用业务层处理注册的业务逻辑
					String basePath = request.getContextPath();
					boolean flag = userService.register(user, basePath);
					// 3. 根据业务层放回的结果来调整页面
					if (flag) {
						// 3.1 如果注册成功,则跳转到注册成功页面
						response.sendRedirect(basePath + "/register_ok.jsp");
					} else {
						// 3.2 注册失败,给出提示并重新跳转到注册页面
						response.getWriter().write("<script>alert('服务器异常,请稍后再试');location=history.back();</script>");
					}
					
				} else {
				// 验证码不正确,则给出提示,并返回注册页面
					request.setAttribute("errorMsg", "验证码不正确,请从新输入");
					request.getRequestDispatcher("/register.jsp").forward(request, response);
					return;
				}
			} else {
				// 这里是验证码为空的情况
				request.setAttribute("errorMsg", "验证码不能为空,请输入验证码再提交");
				request.getRequestDispatcher("/register.jsp").forward(request, response);
			}
		} catch (UserNameCanNotNullException e) {
			// 捕获到异常后,将异常信息写入域中,再转发到页面去,给用户提示
			e.printStackTrace();
			request.setAttribute("errorMsg", e.getMessage());
			request.getRequestDispatcher("/register.jsp").forward(request, response);
		} catch (UserExistsException e) {
			// 捕获到异常后,将异常信息写入域中,再转发到页面去,给用户提示
			e.printStackTrace();
			request.setAttribute("errorMsg", e.getMessage());
			request.getRequestDispatcher("/register.jsp").forward(request, response);
		} catch (TelephoneCanNotNullException e) {
			// 捕获到异常后,将异常信息写入域中,再转发到页面去,给用户提示
			e.printStackTrace();
			request.setAttribute("errorMsg", e.getMessage());
			request.getRequestDispatcher("/register.jsp").forward(request, response);
		} catch (EmailCanNotNullException e) {
			// 捕获到异常后,将异常信息写入域中,再转发到页面去,给用户提示
			e.printStackTrace();
			request.setAttribute("errorMsg", e.getMessage());
			request.getRequestDispatcher("/register.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
}
