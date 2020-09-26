package com.master.danmu.message;

public class LoginMessage extends BaseMessage {

    private String roomid;
    private String aid;
    private String token;
    private String time;
    private String auth;

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }
}
