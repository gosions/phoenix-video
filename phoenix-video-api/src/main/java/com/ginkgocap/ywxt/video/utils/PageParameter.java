package com.ginkgocap.ywxt.video.utils;



import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 分页参数类
 * 
 */
public class PageParameter implements Serializable{

    public static final int DEFAULT_PAGE_SIZE = 10;
    @NotNull(message = "当前页数不能为空")
    @Min(value = 1, message = "页数不能小于1")
	private int pageSize;
    @Min(value = 1, message = "页码不能小于1")
	@NotNull(message = "当前页码不能为空")
    private int currentPage;
    private int totalPage;
    private int totalCount;
    private int startRow;
    private int endRow;

    public PageParameter() {
        this.currentPage = 1;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    /**
     * 
     * @param currentPage
     * @param pageSize
     */
    public PageParameter(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.startRow = currentPage > 0 ? (currentPage - 1) * pageSize : 0;
        this.endRow = currentPage * pageSize;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PageParameter [pageSize=");
		builder.append(pageSize);
		builder.append(", currentPage=");
		builder.append(currentPage);
        builder.append(", startRow=");
        builder.append(startRow);
        builder.append(", endRow=");
        builder.append(endRow);
		builder.append(", totalPage=");
		builder.append(totalPage);
		builder.append(", totalCount=");
		builder.append(totalCount);
		builder.append("]");
		return builder.toString();
	}

}
