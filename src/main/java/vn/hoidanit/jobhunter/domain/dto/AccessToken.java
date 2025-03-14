package vn.hoidanit.jobhunter.domain.dto;

public class AccessToken {
    private String token;

    private AddInforForToken addInforForToken;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AddInforForToken getAddInforForToken() {
        return addInforForToken;
    }

    public void setAddInforForToken(AddInforForToken addInforForToken) {
        this.addInforForToken = addInforForToken;
    }

}
