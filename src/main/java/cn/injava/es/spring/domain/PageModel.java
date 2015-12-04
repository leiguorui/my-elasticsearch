package cn.injava.es.spring.domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 分页信息的封装
 *
 * User: Administrator Date: 15-4-1 Time: 上午11:50
 */
public class PageModel<E> {

	// 结果集
	private List<E> list;

	// 查询记录数
	private int totalRecords;

	// 每页多少条数据
	private int pageSize;

	// 第几页
	private int pageNo;

	/**
	 * 总页数
	 * 
	 * @return
	 */
	public int getTotalPages() {
		return (totalRecords + pageSize - 1) / pageSize;
	}

	/**
	 * 取得首页
	 * 
	 * @return
	 */
	public int getTopPageNo() {
		return 1;
	}

	/**
	 * 上一页
	 * 
	 * @return
	 */
	public int getPreviousPageNo() {
		if (pageNo <= 1) {
			return 1;
		}
		return pageNo - 1;
	}

	/**
	 * 下一页
	 * 
	 * @return
	 */
	public int getNextPageNo() {
		if (pageNo >= getBottomPageNo()) {
			return getBottomPageNo();
		}
		return pageNo + 1;
	}

	/**
	 * 取得尾页
	 * 
	 * @return
	 */
	public int getBottomPageNo() {
		return getTotalPages();
	}

	/**
	 * 取得所有的分页信息,如:上一页 1 ... 31 32 33 34 35 ... 40 下一页
	 * 
	 * @return
	 */
	public List<String> getPageList() {
		List<String> pages = new LinkedList<>();
		if (pageNo != 1) {
			pages.add("上一页");
		}

		if (pageNo == 4) {
			pages.add("1");
		} else if (pageNo > 4) {
			pages.add("1");
			pages.add("...");
		}

		// 设置当前页相邻的4个页码
		for (int noSize = -2; noSize < 3; noSize++) {
			int closedPageNo = pageNo + noSize;
			if (closedPageNo > 0 && closedPageNo < getTotalPages() + 1) {
				pages.add(String.valueOf(closedPageNo));
			}
		}

		if (getTotalPages() > 3 && (getTotalPages() - pageNo) > 3) {
			pages.add("...");
			pages.add(String.valueOf(getTotalPages()));
		} else if (getTotalPages() > 3 && (getTotalPages() - pageNo) > 2) {
			pages.add(String.valueOf(getTotalPages()));
		}

		if (pageNo != getTotalPages()) {
			pages.add("下一页");
		}

		return pages;
	}

	public List<E> getList() {
		return list;
	}

	public void setList(List<E> list) {
		this.list = list;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getMinPage() {
		int min_page = getMaxPage() - 4;
		if (min_page < 0) {
			min_page = 1;
		}
		return min_page;
	}

	public int getMaxPage() {
		int max_page = 0;
		for (int i = 0; i < 5; i++) {
			max_page = pageNo + i;
			if (max_page % 5 == 0) {
				break;
			}
		}
		if (max_page > getTotalPages()) {
			max_page = getTotalPages();
		}
		return max_page;
	}

	public static void main(String[] args) {
		// 创建一个分页
		PageModel<String> pageModel = new PageModel<String>();
		pageModel.setPageNo(1);
		pageModel.setPageSize(5);
		pageModel.setTotalRecords(10);
		pageModel.setList(new ArrayList<String>());
	}
}
