package com.itheima.shop.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/*
 * 用来判断用户是否激活的类
 */
public class CommonUtil {
		
	
		
		/**
		 * 会话域中存储的订单
		 */
		public static final String ORDER_SESSION = "order";
		/**
		 * 登录是勾选了记住用户名和密码
		 */
		public static final String REMBER_INFO = "remember";
		/**
		 * session 中的cart
		 */
		public static final String CART_SESSION = "cart";
		/**
		 * 服务器中的验证码字符串
		 */
		public static final String CHECKCODE_SERVER = "CHECKCODE_SERVER";
		/**
		 * 用户登录的完整信息
		 */
		public static final String USER_LOGINUSER = "loginUser";
		/**
		 * 用户模块，已激活
		 */
		public static final String USER_STATE_YES="Y";
		/**
		 * 用户模块，未激活
		 */
		public static final String USER_STATE_NO="N";
		/**
		 * 产品的分类列表
		 */
		public static final String CATEGORY_LIST="categories";
		
		public static void sendAjaxErrorMsg(HttpServletResponse response, String msg) throws IOException{
			Map<String,Object> map = new HashMap<>();
			map.put("errMsg", msg);
			String jsonData = new Gson().toJson(map);
			response.getWriter().write(jsonData);
		}
	}
	

