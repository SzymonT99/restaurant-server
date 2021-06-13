package com.restaurant.springboot.domain.dto;

public class Message {

    private String code;
    private String codeName;
    private String content;

    public Message() {
    }

    public Message(String code, String codeName, String content) {
        this.code = code;
        this.codeName = codeName;
        this.content = content;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
