package com.lambdaschool.school.model;

import java.io.Serializable;

public class MessageDetail implements Serializable {
    private String text;


    public MessageDetail() {}

    public MessageDetail(String text, int priority, boolean secret) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    @Override
    public String toString() {
        return "MessageDetail{" + "text='" + text + '}';
    }
}
