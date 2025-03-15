package vn.hoidanit.jobhunter.domain.dto;

public class AccessToken {
    private String token;
    private AddInforForAccessToken addInforForAccessToken;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AddInforForAccessToken getAddInforForAccessToken() {
        return addInforForAccessToken;
    }

    public void setAddInforForAccessToken(AddInforForAccessToken addInforForAccessToken) {
        this.addInforForAccessToken = addInforForAccessToken;
    }

}
