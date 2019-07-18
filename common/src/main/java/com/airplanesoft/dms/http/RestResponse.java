package com.airplanesoft.dms.http;

import java.time.LocalDateTime;
import java.util.List;

public class RestResponse<T> {
    private T payload;
    private boolean success = true;
    private List<String> messages;
    private LocalDateTime timestamp = LocalDateTime.now();


    public RestResponse() {
    }


    public RestResponse(boolean success, List<String> messages) {
        this.success = success;
        this.messages = messages;
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


}
