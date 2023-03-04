package com.paradox.yourchat.bean;

public class JsonMsg {
    private String role;
    private String content;

    public JsonMsg(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public JsonMsg() {
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "{" +
                "role='" + role + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
