package com.eauction.application.domain.common;

public enum ResponseMessage {
    SUCCESS_LOGIN("message", "message"),
    SUCCESS_LOGOUT("message", "message"),
    USER_NOT_VERIFIED("message", "message"),
    WRONG_PASSWORD("message", "message"),
    USERNAME_DUPLICATE("message", "message"),
    SUCCESS_REGISTER("message", "message"),
    SESSION_NOT_VALID("message", "message"),
    SUCCESS_UPDATE_USER("message", "message"),
    SUCCESS_DELETE_USER("message", "message"),
    USER_INVALID("message", "message");
    private final String messageDetail;
    private final String messageCode;

    ResponseMessage(String messageDetail, String messageCode) {
        this.messageDetail = messageDetail;
        this.messageCode = messageCode;
    }

    public String getMessageDetail() {
        return messageDetail;
    }

    public String getMessageCode() {
        return messageCode;
    }
}
