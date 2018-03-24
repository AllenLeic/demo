package com.itheima.shop.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.itheima.shop.entity.User;
import com.itheima.shop.util.CommonUtil;
import com.itheima.shop.util.JdbcUtil;

/**
 * 用户数据访问类
 * @author AllenLei
 * @date 2018年3月19日 下午6:03:24
 */
public class UserDao {
	
	// 实例化queryrunner
	private QueryRunner runner = new QueryRunner(JdbcUtil.getDataSource());
	
	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return User
	 */
	public User queryUserByUsername(String username) throws SQLException {
		String sql = "select * from tab_user where username=?";
		
		return runner.query(sql, new BeanHandler<User>(User.class),username);
	}
	
	/**
	 * 用户注册
	 * @param user
	 */
	public void register(User user) throws SQLException {
		String sql = "insert into tab_user values(null,?,?,?,?,?,?,?,?,?)";
		runner.update(sql, user.getUsername(),
						user.getPassword(),
						user.getName(),
						user.getEmail(),
						user.getTelephone(),
						user.getBirthday(),
						user.getSex(),
						user.getState(),
						user.getCode());
	}

	
	/**
	 * 根据激活码,修改用户的状态
	 * @param code
	 */
	public void active(String code) throws SQLException {
		String sql = "update tab_user set state=? where code=?";
		runner.update(sql,CommonUtil.USER_STATE_YES,code);
	}
	
	/**
	 * 根据用户名和密码获取用户的全部数据
	 * @param user
	 * @return
	 */
	public User queryUserDataByUser(User user) throws SQLException {
		String sql = "select * from tab_user where username=? and password=?";
		return runner.query(sql, new BeanHandler<User>(User.class),user.getUsername(),user.getPassword());
	}
	
}
