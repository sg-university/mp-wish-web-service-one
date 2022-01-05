package com.wish.webserviceone.infrastructure.deliveries.contracts;

public class Result<T> {
    private T content;
    private String status;
    private String code;

    public Result() {
    }

    public Result(T content, String status) {
        this.content = content;
        this.status = status;
    }

    public Result(T content, String status, String code) {
        this.content = content;
        this.status = status;
        this.code = code;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
