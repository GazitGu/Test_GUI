package com.em.model;

/**
 * Created by guchong on 5/22/2018.
 */
public class RestResponse {
    private boolean success;
    private String result;

    public RestResponse() {
        super();
    }
    public RestResponse(boolean success, String result) {
        super();
        this.success = success;
        this.result = result;
    }
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    @Override
    public String toString() {
        return "RestResponse [success=" + success + ", result=" + result + "]";
    }
}
