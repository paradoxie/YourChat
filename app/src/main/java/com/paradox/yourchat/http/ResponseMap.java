package com.paradox.yourchat.http;

import java.io.Serializable;

public class ResponseMap<T> implements Serializable {

    public static final int STATUS_SUCCESS = 0;

    private int status = -1;

    private String message = "";

    private T result = null;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
