package com.itheima.shop.service;

import com.itheima.shop.dao.UserDao;
import com.itheima.shop.entity.User;
import com.itheima.shop.exception.EmailCanNotNullException;
import com.itheima.shop.exception.LoginInfoIncorrectException;
import com.itheima.shop.exception.PasswordCanNotNullException;
import com.itheima.shop.exception.TelephoneCanNotNullException;
import com.itheima.shop.exception.UserExistsException;
import com.itheima.shop.exception.UserNameCanNotNullException;
import com.itheima.shop.exception.UserNotActiveException;
import com.itheima.shop.util.CommonUtil;
import com.itheima.shop.util.MailUtil;
import com.itheima.shop.util.Md5Util;
import com.itheima.shop.util.UuidUtil;

/**
 * 处理用户的业务逻辑类
 * @author AllenLei
 * @date 2018年3月19日 下午4:46:44
 */
public class UserService {
	
	// 实例数据访问层
	private UserDao userDao = new UserDao();
	
	/**
	 * 处理注册的业务
	 * @param user
	 * @return boolean
	 */
	public boolean register(User user, String basePath) throws Exception {
		// 1. 判断用户数据的有效性
		// 用户名
		if (user.getUsername() == null || "".equals(user.getName().trim())) {
			throw new UserNameCanNotNullException("用户名不能为空");
		} else {
			// 调用数据访问层判断用户名是否存在
			User dbUser = userDao.queryUserByUsername(user.getUsername());
			if (dbUser != null) {
				throw new UserExistsException("用户名已存在");
			}
		}
		// 密码加密
		String password = Md5Util.encodeByMd5(user.getPassword());
		user.setPassword(password);
		// 手机号
		if (user.getTelephone() == null || "".equals(user.getTelephone().trim())) {
			throw new TelephoneCanNotNullException("手机号码不能为空");
		}
		// 邮箱号
		if (user.getEmail() == null || "".equals(user.getEmail().trim())) {
			throw new EmailCanNotNullException("邮箱不能为空");
		}
		// 封装两个字段,一个是激活状态,一个是激活码字段
		user.setState(CommonUtil.USER_STATE_NO);
		String code = UuidUtil.getUuid();
		user.setCode(code);
		// 3. 调用数据访问层写入数据
		userDao.register(user);
		// 给用户的邮箱发送激活码.
		String emailMsg = "<a href='http://localhost:8080"+basePath+"/userServlet?action=active&code="+code+"'>点我激活账户</a>";
		MailUtil.sendMail(user.getEmail(), emailMsg);
		return true;
	}

	/**
	 * 处理邮箱激活的业务
	 * @param code
	 */
	public void active(String code) throws Exception {
		// 直接调用数据访问层获取数据
		userDao.active(code);
		
	}
	

	/**
	 * 处理用户登录的业务
	 * @param user
	 * @return
	 */
	public User login(User user) throws Exception {
		// 对传入的数据进行判断
		if (user.getUsername() == null || "".equals(user.getUsername().trim())) {
			throw new UserNameCanNotNullException("用户名不能为空");
		}
		if (user.getPassword() == null || "".equals(user.getPassword().trim())) {
			throw new PasswordCanNotNullException("密码不能为空");
		}
		// 调用数据访问层获取用户的完整数据
		User loginUser = userDao.queryUserDataByUser(user);
		if (loginUser == null) {
			throw new LoginInfoIncorrectException("用户名或密码不正确");
		}
		// 判断用户的激活状态
		if (loginUser.getState().equals(CommonUtil.USER_STATE_NO)) {
			throw new UserNotActiveException("用户未激活,请前往邮箱验证激活");
		}
		
		return loginUser;
	}
	

}
