package com.paradox.yourchat.bean;

public class MsgBean {
    public static final int TYPE_RECEIVE = 1;
    public static final int TYPE_SEND = 2;
    private String content;
    private int type;

    private String time;

    public MsgBean() {
    }

    public MsgBean(String content, int type, String time) {
        this.content = content;
        this.type = type;
        this.time = time;
    }

    public MsgBean(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    @Override
    public String toString() {
        return "MsgBean{" +
                "content='" + content + '\'' +
                ", type=" + type +
                ", time='" + time + '\'' +
                '}';
    }
}
