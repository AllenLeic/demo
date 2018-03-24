package com.itheima.shop.web.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/**
 * 全网站请求过滤器 
 */
@WebFilter("/*")
public class EncodingFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,FilterChain chain) throws IOException, ServletException {
		//父子接口强转
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		//获取客户端请求的方式
		String method = request.getMethod();
		//处理输出数据乱码问题
		response.setContentType("text/html;charset=utf-8");
		//如果请求方式为POST
		if("POST".equalsIgnoreCase(method)){
			//用POST专用代码解决乱码问题
			request.setCharacterEncoding("UTF-8");
			//放行请求
			chain.doFilter(request,response);
		//如果请求方式为GET
		}else if("GET".equalsIgnoreCase(method)){
			MyRequest myRequest = new MyRequest(request);
			chain.doFilter(myRequest,response);
		}
	}
	@Override
	public void destroy() {
	}
}
class MyRequest extends HttpServletRequestWrapper{
	private HttpServletRequest request;
	public MyRequest(HttpServletRequest request) {
		super(request);
		this.request = request;
	}
	@Override
	public String getParameter(String name) {//"action"
		String value = request.getParameter(name);
		//如果value非空
		if(value != null && value.trim().length() > 0){
			try {
				//去掉二边的空格
				value = value.trim();
				//还原
				byte[] buf = value.getBytes("ISO8859-1");
				//重构
				value = new String(buf,"UTF-8");
				//将value返回
				return value;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		//如果value为空	
		}else{
			return null;
		}
	}
	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> map = request.getParameterMap();
		for(Entry<String,String[]> entry : map.entrySet()){
			//获取表单项的名
			String key = entry.getKey();
			//获取表单项的值
			String[] values = entry.getValue();
			for(int i=0;i<values.length;i++){
				try {
					String value = values[i];
					byte[] buf = value.getBytes("ISO8859-1");
					value = new String(buf,"UTF-8");
					values[i] = value;
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}//in for end
		}//out for end
		return map;
	}
}







