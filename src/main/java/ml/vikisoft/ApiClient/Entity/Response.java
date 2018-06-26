package ml.vikisoft.ApiClient.Entity;

import java.util.List;
import java.util.Map;

public class Response {
    private int code;
    private String data;
    private boolean error;
    private String message;
    private String url;
    private List<Header> headers;
    public void setCode(int code) {
        this.code = code;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    public int getCode() {
        return code;
    }

    public String getData() {
        return data;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getUrl() {
        return url;
    }

    public List<Header> getHeaders() {
        return headers;
    }
}
