package com.itheima.shop.entity;

import java.io.Serializable;
import java.util.List;

public class PageBean<T> implements Serializable {
    private List<T> list;//当前页数据列表
    private int firstPage=1; //首页
    private int prePage;//上一页
    private int nextPage;//下一页
    private int totalPage;//末页或总页数
    private int curPage;//当前页
    private int count;//总记录数
    private int pageSize=10;//每页显示多少条记录数

    
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public int getFirstPage() {
		return firstPage;
	}
	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}
	public int getPrePage() {
		prePage = this.curPage-1;
		return prePage;
	}
	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}
	public int getNextPage() {
		this.nextPage=this.curPage+1;
		return nextPage;
	}
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}
	public int getTotalPage() {
		this.totalPage =  count%pageSize==0?count/pageSize:count/pageSize+1;
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public PageBean(List<T> productList, int firstPage, int prePage,
			int nextPage, int totalPage, int curPage, int count, int pageSize) {
		super();
		this.list = productList;
		this.firstPage = firstPage;
		this.prePage = prePage;
		this.nextPage = nextPage;
		this.totalPage = totalPage;
		this.curPage = curPage;
		this.count = count;
		this.pageSize = pageSize;
	}
	public PageBean() {
		super();
		// TODO Auto-generated constructor stub
	}
    
}
