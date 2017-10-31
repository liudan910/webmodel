package com.domain;

/**
 * Created by liudan19 on 2017/10/31.
 */
public class Message {
    private String content;
    private long length;
    private long remark;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public long getRemark() {
        return remark;
    }

    public void setRemark(long remark) {
        this.remark = remark;
    }
}
