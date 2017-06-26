package com.ginkgocap.ywxt.video.utils;

import java.io.Serializable;


/**
 * 分页帮助工具
 * @author Administrator
 *
 */
public class PageUtil implements Serializable {
    private int count;//记录总数
    private int currentPage;//当前页数  *
    private int nextPage;//下一页
    private int prevPage;//上一页
    private int pageSize = 20;//每页大小        *
    private int totalPage;//总页数
    private int pageStartRow;
    private int pageEndRow;
    private boolean isProcessed;

    private int pageCount = 5;//页面上显示的页数总数
    
    /**
     * @param count 记录总数
     */
    public PageUtil(int count) {
        this.isProcessed = false;
        this.count = count;
    }
    /**
     * @param count 记录总数
     * @param currentPage 当前页
     * @param pageSize 页大小
     */
    public PageUtil(int count, int currentPage, int pageSize) {
        this.count = count;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        process();
    }
    /**
     * @param count 记录总数
     * @param currentPage 当前页
     */
    public PageUtil(int count, int currentPage) {
        this.count = count;
        this.currentPage = currentPage;
    }
    private void process() {
        if (!isProcessed) {
            totalPage = count / pageSize + (count % pageSize != 0 ? 1 : 0);
            if (currentPage > 1)
                prevPage = currentPage - 1;
            else{
                prevPage = 1;
                currentPage = 1;
            }
            if (currentPage < totalPage)
                nextPage = currentPage + 1;
            else{
                nextPage = totalPage;
                currentPage =   totalPage;
            }
            if (currentPage > 0 && currentPage <= totalPage) {
                pageStartRow = (currentPage - 1) * pageSize;
                pageEndRow = currentPage * pageSize;
                if (pageEndRow > count)
                    pageEndRow = count;
            } else if (currentPage > totalPage) {
                pageStartRow = count;
                pageEndRow = count;
            } else {
                pageStartRow = 0;
                pageEndRow = 0;
            }
            isProcessed = true;
        }
    }

    public static void main(String args[]) {
        PageUtil pageutil = new PageUtil(30);
        pageutil.setCurrentPage(2);
        pageutil.setPageSize(9);
    }
    
    public int getCount() {
        process();
        return count;
    }

    public int getCurrentPage() {
        process();
        return currentPage;
    }

    public int getNextPage() {
        process();
        return nextPage;
    }

    public int getPageSize() {
        process();
        return pageSize;
    }

    public int getPrevPage() {
        process();
        return prevPage;
    }

    public int getPageStartRow() {
        process();
        return pageStartRow;
    }

    public int getPageEndRow() {
        process();
        return pageEndRow;
    }

    public int getTotalPage() {
        process();
        return totalPage;
    }

    public void setCount(int i) {
        isProcessed = false;
        count = i;
    }

    public void setCurrentPage(int i) {
        isProcessed = false;
        currentPage = i;
    }

    public void setPageSize(int i) {
        isProcessed = false;
        pageSize = i;
    }

    public boolean isLast() {
        return getCurrentPage() >= getTotalPage();
    }

    public boolean isFirst() {
        return getCurrentPage() <= 1;
    }
    public int getPageCount() {
        return pageCount;
    }
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

}
