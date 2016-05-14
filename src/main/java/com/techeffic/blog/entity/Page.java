package com.techeffic.blog.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 表格分页实体
 * 1、需要初始页码与每页条数
 * 2、需要设置总条数
 * 
 * @author xudong_liao<br/>
 * @date 2016年4月25日<br/>
 */
public class Page<E> implements Serializable {
	private static final long serialVersionUID = -5332477098233421672L;
	// 总记录数
	private long total;
	// 每页显示行数
	private int pageSize;
	// 页数
	private int pages;
	// 当前所在页
	private int page;
	// 开始行
	private int startRow;
	// 结束行
	private int endRow;
	// 数据内容
	private List<E> datas;

	public Page(int page, int pageSize) {
		this.page = page;
		this.pageSize = pageSize;
		if (page != 0 && pageSize != 0) {
			if (page <= 1) {
				this.startRow = 1;
				this.endRow = pageSize;
			} else {
				this.startRow = (page - 1) * pageSize + 1;
				this.endRow = page * pageSize;
			}
		}
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
		// 计算总页数
		if (total != 0 && pageSize != 0) {
			if (total % pageSize == 0) {
				pages = (int) (total / pageSize);
			} else {
				pages = (int) (total / pageSize + 1);
			}
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPages() {
		/*if (this.pages != 0) {
			return this.pages;
		}
		// 计算总页数
		if (total != 0 && pageSize != 0) {
			if (total % pageSize == 0) {
				pages = (int) (total / pageSize);
			} else {
				pages = (int) (total / pageSize + 1);
			}
		}*/
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getStartRow() {
		if (this.startRow != 0) {
			return this.startRow;
		}
		// 计算开始页和结束页
		if (page != 0 && pageSize != 0) {
			if (page <= 1) {
				this.startRow = 1;
				this.endRow = pageSize;
			} else {
				this.startRow = (page - 1) * pageSize + 1;
				this.endRow = page * pageSize;
			}
		}
		return this.startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		// 计算开始页和结束页
		if (this.endRow != 0) {
			return this.endRow;
		}
		if (page != 0 && pageSize != 0) {
			if (page <= 1) {
				this.startRow = 1;
				this.endRow = pageSize;
			} else {
				this.startRow = (page - 1) * pageSize + 1;
				this.endRow = page * pageSize;
			}
		}
		return this.endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public List<E> getDatas() {
		return datas;
	}

	public void setDatas(List<E> datas) {
		this.datas = datas;
	}

}
