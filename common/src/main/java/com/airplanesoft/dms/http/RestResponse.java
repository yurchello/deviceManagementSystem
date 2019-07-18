package com.airplanesoft.dms.http;

import java.time.LocalDateTime;
import java.util.List;

public class RestResponse<T> {
    private T payload;
    private boolean success = true;
    private List<String> messages;
    private LocalDateTime timestamp = LocalDateTime.now();
    private int status;

    public RestResponse() {
    }

    public RestResponse(T payload, int status) {
        this.payload = payload;
        this.status = status;
    }

    public RestResponse(List<String> messages, int status) {
        this.messages = messages;
        this.status = status;
    }

    public RestResponse(int status) {
        this.status = status;
    }

    public RestResponse(T payload) {
        this.payload = payload;
    }

    public RestResponse(T payload, boolean success, List<String> messages, LocalDateTime timestamp) {
        this.payload = payload;
        this.success = success;
        this.messages = messages;
        this.timestamp = timestamp;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
