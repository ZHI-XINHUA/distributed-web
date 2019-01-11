package zxh.mongodb.spring_data_mongodb.util;

import java.io.Serializable;
import java.util.List;

/**
 * 分页模型类
 */
public class PageModel<T> implements Serializable{

    private static final long serialVersionUID = 4998820981795903425L;

    /** 默认页码 */
    public static final int DEFAULT_PAGENO = 1;
    /** 默认页容量 */
    public static final int DEFAULT_PAGESIZE = 20;


    /**当前页码**/
    private int pageNo;
    /** 页容量**/
    private int pageSize;
    /**总记录**/
    private int totalCount;
    /**总页数**/
    private int totalPage;
    /**数据集合**/
    private List<T> result;

    /**
     * 初始化
     */
    public PageModel(){
        this.pageNo = DEFAULT_PAGENO;
        this.pageSize = DEFAULT_PAGESIZE;
    }

    /**
     * 带有页码，页容量参数的构造函数
     * @param pageNo
     * @param pageSize
     */
    public PageModel(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    /**
     * 带有页码，页容量，总记录，数据集合参数的构造函数
     *
     * @param pageNo
     *            The current pageNo
     * @param pageSize
     *            The page's size
     * @param totalCount
     *            The total record count
     * @param list
     *            The record list
     */
    public PageModel(int pageNo, int pageSize, int totalCount, List<T> list) {
        // 一定要先设置页容量
        this.setPageSize(pageSize);
        this.setPageNo(pageNo);
        this.setTotalCount(totalCount);
        this.setResult(list);
    }


    /**
     * 是否第一页
     * @return
     */
    public boolean firstPage(){
        return this.pageNo<=1;
    }

    /**
     * 是否最后一页
     * @return
     */
    public boolean lastPage(){
        return  this.pageNo==getTotalPage();
    }

    /**
     * 获取下一页
     * @return
     */
    public int getNextPage(){
        if(lastPage()){
            return  getTotalPage();
        }
        return  this.pageNo+1;
    }

    /**
     * 获取上一页
     * @return
     */
    public int getPrePage(){
        if (this.pageNo <= 1) {
            return 1;
        }
        return this.pageNo - 1;
    }



    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        if(pageNo<0){
            this.pageNo = DEFAULT_PAGENO;
        }else{
            this.pageNo = pageNo;
        }

    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if(pageSize<0){
            this.pageSize = DEFAULT_PAGESIZE;
        }else{
            this.pageSize = pageSize;
        }
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        if (totalCount <= 0) {
            this.totalCount = 0;
        } else {
            this.totalCount = totalCount;
        }
    }

    public int getTotalPage() {
        this.totalPage = (getTotalCount() - 1) / getPageSize() + 1;
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        if (totalPage < 0) {
            this.totalPage = 0;
        } else {
            this.totalPage = totalPage;
        }
    }

    /**
     * 获取第一条记录位置，用于数据库查询时设置的第一条记录
     *
     * @return
     */
    public int getFirstResult() {
        return (this.getPageNo() - 1) * this.getPageSize();
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
