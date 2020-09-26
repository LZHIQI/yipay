package com.mican.myapplication.net.core;


/**
 * @name lzq
 * @class name：com.mican.network.configure
 * @class describe
 * @time 2020/6/1 1:49 PM
 * @change
 * @chang
 * @class describe
 */
public  class Response<T > {
    private int result;//200
    private String message;//success
    private T data;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
