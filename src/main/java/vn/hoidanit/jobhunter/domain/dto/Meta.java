package vn.hoidanit.jobhunter.domain.dto;

public class Meta {

    private long currenPage;
    private long pageSize;
    private long totalPage;
    private long totalElement;

    public long getCurrenPage() {
        return currenPage;
    }

    public void setCurrenPage(long currenPage) {
        this.currenPage = currenPage;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalElement() {
        return totalElement;
    }

    public void setTotalElement(long totalElement) {
        this.totalElement = totalElement;
    }

}
