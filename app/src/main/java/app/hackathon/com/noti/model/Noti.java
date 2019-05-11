package app.hackathon.com.noti.model;

public class Noti {
    String msg;

    public Noti() {

    }

    public Noti(String msg) {
        this.msg = msg;
    }

    private void setMsg() {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
