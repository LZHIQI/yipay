package com.example.testmodule.net.core;


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
    private boolean success;//200
    private String message;//success
    private T data;

    public boolean getResult() {
        return success;
    }

    public void setResult(boolean result) {
        this.success = result;
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
