package com.paradox.yourchat.bean;

public class JsonMsg {
    private String role;
    private String content;
    private String id;

    public JsonMsg(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public JsonMsg(String role, String content, String id) {
        this.role = role;
        this.content = content;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "JsonMsg{" +
                "role='" + role + '\'' +
                ", content='" + content + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
