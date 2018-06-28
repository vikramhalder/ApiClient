package com.github.vikramhalder.ApiClient;

import com.github.vikramhalder.ApiClient.Entity.*;
import com.github.vikramhalder.ApiClient.Data.Execute;
import com.github.vikramhalder.ApiClient.Entity.*;
import com.github.vikramhalder.ApiClient.Entity.Interface.GetResponse;

public class PostApiMethod {
    private String url="";
    private Params params=new Params();
    private Headers headers =new Headers();
    private int connectTimeout=30000;
    private int readTimeout=35000;
    private boolean printStackTrace=false;

    public PostApiMethod setUrl(String url) {
        this.url = url;
        return this;
    }
    public PostApiMethod setParams(String name, String value){
        this.params.add(name,value);
        return this;
    }
    public PostApiMethod setHeaders(String name, String value){
        this.headers.add(name,value);
        return this;
    }
    public PostApiMethod setConnectTimeout(int connectTimeout){
        this.connectTimeout=connectTimeout*1000;
        return this;
    }
    public PostApiMethod setReadTimeout(int readTimeout){
        this.readTimeout=readTimeout*1000;
        return this;
    }
    public PostApiMethod setPrintStackTrace(boolean printStackTrace){
        this.printStackTrace=printStackTrace;
        return this;
    }

    public void execute(GetResponse callback){
         Response res=new Execute().executePostMethod(url,params, headers,connectTimeout,readTimeout,printStackTrace);
         if(res.isError()){
             if(res.getCode()>201) {
                 ErrorServer errorServer = new ErrorServer();
                 errorServer.setCode(res.getCode());
                 errorServer.setError(true);
                 errorServer.setMessage(res.getMessage());
                 callback.onErrorServer(errorServer);
                 callback.onErrorParse(null);
                 callback.onSuccess(null);
             }else{
                 ErrorParse errorParse = new ErrorParse();
                 errorParse.setCode(res.getCode());
                 errorParse.setError(true);
                 errorParse.setMessage(res.getMessage());
                 callback.onErrorParse(errorParse);
                 callback.onErrorServer(null);
                 callback.onSuccess(null);
             }
         }else {
             callback.onSuccess(res);
             callback.onErrorParse(null);
             callback.onErrorServer(null);
         }
    }
}
