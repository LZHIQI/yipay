package com.example.testmodule.net.core;

/**

 */

public class CoreResponse<T> {

    /**
     * 返回结果的消息头
     */
    private CoreHeader header;

    /**
     * 返回结果的消息体
     */
    private T data;

    public CoreResponse() {
        super();
    }

    public CoreResponse(CoreHeader header, T data) {
        super();
        this.header = header;
        this.data = data;
    }

    public CoreHeader getHeader() {
        return header;
    }

    public void setHeader(CoreHeader header) {
        this.header = header;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Optional<T> transform() {
        return new Optional<>(data);
    }
}
