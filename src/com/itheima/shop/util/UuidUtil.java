package com.itheima.shop.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 产生UUID随机字符串工具类
 */
public final class UuidUtil {
	private UuidUtil(){}
	public static String getUuid(){
		return UUID.randomUUID().toString();
	}
	
	/**
	 * 获取订单的oid订单号
	 * @return  int
	 */
	public static int getOid() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMSSSS");
		String oid = dateFormat.format(new Date());
		return Integer.parseInt(oid);
	}
	/**
	 * 测试
	 */
	public static void main(String[] args) {
		System.out.println(UuidUtil.getUuid());
		System.out.println(UuidUtil.getUuid());
		System.out.println(UuidUtil.getUuid());
		System.out.println(UuidUtil.getUuid());
	}
}
