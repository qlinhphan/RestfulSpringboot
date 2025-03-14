package vn.hoidanit.jobhunter.domain.dto;

public class ResultOfPagination {

    private Meta meta;

    private Object resultOfPagina;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Object getResultOfPagina() {
        return resultOfPagina;
    }

    public void setResultOfPagina(Object resultOfPagina) {
        this.resultOfPagina = resultOfPagina;
    }

}
