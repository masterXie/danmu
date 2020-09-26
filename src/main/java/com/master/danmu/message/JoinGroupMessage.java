package com.master.danmu.message;

public class JoinGroupMessage extends BaseMessage {

    private String rid;
    private String token;

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
