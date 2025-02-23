package vn.hoidanit.jobhunter.domain.formResponse;

public class RestResponse<T> {
    private int sttErr;
    private String err;
    private Object message;
    private T data;

    public int getSttErr() {
        return sttErr;
    }

    public void setSttErr(int sttErr) {
        this.sttErr = sttErr;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
