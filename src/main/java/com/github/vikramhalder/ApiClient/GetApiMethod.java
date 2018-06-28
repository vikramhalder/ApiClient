package com.github.vikramhalder.ApiClient;

import com.github.vikramhalder.ApiClient.Entity.*;
import com.github.vikramhalder.ApiClient.Data.Execute;
import com.github.vikramhalder.ApiClient.Entity.*;
import com.github.vikramhalder.ApiClient.Entity.Interface.GetResponse;

public class GetApiMethod {
    private String url="";
    private Params params=new Params();
    private Headers headers =new Headers();
    private int connectTimeout=30000;
    private int readTimeout=35000;
    private boolean printStackTrace=false;

    public GetApiMethod setUrl(String url) {
        this.url = url;
        return this;
    }
    public GetApiMethod setParams(String name,String value){
        this.params.add(name,value);
        return this;
    }
    public GetApiMethod setHeaders(String name,String value){
        this.headers.add(name,value);
        return this;
    }
    public GetApiMethod setConnectTimeout(int connectTimeout){
        this.connectTimeout=connectTimeout*1000;
        return this;
    }
    public GetApiMethod setReadTimeout(int readTimeout){
        this.readTimeout=readTimeout*1000;
        return this;
    }
    public GetApiMethod setPrintStackTrace(boolean printStackTrace){
        this.printStackTrace=printStackTrace;
        return this;
    }
    public void execute(GetResponse callback){
         Response response=new Execute().executeGetMethod(url,params, headers,connectTimeout,readTimeout,printStackTrace);
        if(response.isError()){
            if(response.getCode()>201) {
                ErrorServer errorServer = new ErrorServer();
                errorServer.setCode(response.getCode());
                errorServer.setError(true);
                errorServer.setMessage(response.getMessage());
                callback.onErrorServer(errorServer);
                callback.onErrorParse(null);
            }else{
                ErrorParse errorParse = new ErrorParse();
                errorParse.setCode(response.getCode());
                errorParse.setError(true);
                errorParse.setMessage(response.getMessage());
                callback.onErrorParse(errorParse);
                callback.onErrorServer(null);
            }
            callback.onSuccess(null);
        }else {
            callback.onSuccess(response);
            callback.onErrorParse(null);
            callback.onErrorServer(null);
        }
    }
}
