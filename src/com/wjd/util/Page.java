package com.wjd.util;

import java.util.List;

/**
 * 分页工具类
 * @author wnagjd
 * @param <T>
 *
 */
public class Page<T> {

	private Integer pageNum; // 当前页     （前台传递；当前台未传递参数，默认第一页）
	private Integer pageSize; // 每页显示的数量		（前台传递或后台设定；）
	private long totalCount; // 总记录数	（后台数据库中查询得到；count()函数）
	
	private Integer totalPages; // 总页数		（总记录数/每页显示的数量；将参数转为浮点型，执行除法元素，向上取整）
	private Integer prePage; // 上一页	（当前页-1；如果当前页-1小于1，则上一页为1）
	private Integer nextPage; // 下一页	（当前页+1；如果当前页+1大于总页数，则下一页为总页数）
	
	private Integer startNavPage; // 导航开始数	（当前页-5；如果当前页-5小于1，则导航开始数为1，此时导航结束数为导航开始数+9；如果导航开始数+9大于总页数，则导航结束数为总页数）
	private Integer endNavPage; // 导航结束数	 	（当前页+4；如果当前页+4大于总页数，则导航结束数为总页数，此时导航开始数为导航结束数-9；如果导航结束数-9小于1，则导航开始数为1）
	
	// （数据库中limit关键字实现分页   limit 参数1,参数2		参数1：开始查询的下标数，通过(当前页-1)*每页显示的数量； 参数2：每页显示的数量；    limit关键字写在所有sql语句（where条件查询、group by分组、order by排序等）之后）
	private List<T> datas; // 当前页的数据集合 （查询数据库中指定页的数据列表  ）
	
	
	
	/*
	 * 带参构造
	 * 	通过指定参数，得到其他字段的值
	 */
	public Page(Integer pageNum, Integer pageSize, long totalCount) {
		super();
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		
		// 总页数		（总记录数/每页显示的数量；将参数转为浮点型，执行除法元素，向上取整）
		this.totalPages = (int) Math.ceil(totalCount/(pageSize*1.0));
		
		// 上一页	（当前页-1；如果当前页-1小于1，则上一页为1）
		this.prePage = pageNum - 1 < 1 ? 1 : pageNum - 1;
		
		// 下一页	（当前页+1；如果当前页+1大于总页数，则下一页为总页数）
		this.nextPage = pageNum + 1 > totalPages ? totalPages : pageNum + 1;
		
		
		this.startNavPage = pageNum - 5; // 导航开始数	（当前页-5；）
		this.endNavPage = pageNum + 4; // 导航结束数	（当前页+4；）
		// 导航开始数	（当前页-5；如果当前页-5小于1，则导航开始数为1，此时导航结束数为导航开始数+9；如果导航开始数+9大于总页数，则导航结束数为总页数）
		if (this.startNavPage < 1) {
			this.startNavPage = 1; // 如果当前页-5小于1，则导航开始数为1
			this.endNavPage = startNavPage + 9 > totalPages ? totalPages : startNavPage + 9; // 此时导航结束数为导航开始数+9；如果导航开始数+9大于总页数，则导航结束数为总页数
		}
		// 导航结束数	（当前页+4；如果当前页+4大于总页数，则导航结束数为总页数，此时导航开始数为导航结束数-9；如果导航结束数-9小于1，则导航开始数为1）
		if (this.endNavPage > totalPages) {
			this.endNavPage = totalPages; // 如果当前页+4大于总页数，则导航结束数为总页数
			this.startNavPage = endNavPage - 9 < 1 ? 1 : endNavPage - 9; // 则导航结束数为总页数，此时导航开始数为导航结束数-9；如果导航结束数-9小于1，则导航开始数为1
		} 


	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Integer getPrePage() {
		return prePage;
	}

	public void setPrePage(Integer prePage) {
		this.prePage = prePage;
	}

	public Integer getNextPage() {
		return nextPage;
	}

	public void setNextPage(Integer nextPage) {
		this.nextPage = nextPage;
	}

	public Integer getStartNavPage() {
		return startNavPage;
	}

	public void setStartNavPage(Integer startNavPage) {
		this.startNavPage = startNavPage;
	}

	public Integer getEndNavPage() {
		return endNavPage;
	}

	public void setEndNavPage(Integer endNavPage) {
		this.endNavPage = endNavPage;
	}

	public List<T> getDatas() {
		return datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
	
}
