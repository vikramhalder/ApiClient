package com.github.vikramhalder.ApiClient;

import com.github.vikramhalder.ApiClient.Data.MultiPart;
import com.github.vikramhalder.ApiClient.Entity.*;
import com.github.vikramhalder.ApiClient.Entity.Interface.GetResponse;
import com.github.vikramhalder.ApiClient.Entity.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PostFormData {
    private String url="";
    private Params params=new Params();
    private Headers headers =new Headers();
    private List<String[]> filepart=new ArrayList<>();
    private int connectTimeout=30000;
    private int readTimeout=35000;
    private boolean printStackTrace=false;

    public PostFormData setUrl(String url) {
        this.url = url;
        return this;
    }
    public PostFormData setParams(String param_name, String param_value){
        this.params.add(param_name,param_value);
        return this;
    }
    public PostFormData setHeaders(String param_name, String param_value){
        this.headers.add(param_name,param_value);
        return this;
    }
    public PostFormData addFilePart(String param_name, String file_path){
        filepart.add(new String[]{param_name,file_path});
        return this;
    }
    public PostFormData setConnectTimeout(int connectTimeout){
        this.connectTimeout=connectTimeout*1000;
        return this;
    }
    public PostFormData setReadTimeout(int readTimeout){
        this.readTimeout=readTimeout*1000;
        return this;
    }
    public PostFormData setPrintStackTrace(boolean printStackTrace){
        this.printStackTrace=printStackTrace;
        return this;
    }

    public void execute(GetResponse callback){
        try {
            MultiPart multipart = new MultiPart(url,headers,connectTimeout,readTimeout,printStackTrace);
            /**
             * add File path
             */
            for(String[] files:filepart) {
                if (files != null){
                    if (files.length>1){
                        if (files[0] != null && files[1] != null){
                            multipart.addFilePart(files[0], new File(files[1]));
                        }
                        else{
                            multipart.addFilePart(files[0], new File(files[1]));
                        }
                    }
                }
            }
            /**
             * add param value
             */
            for (String[] param : params.getParams()) {
                if (param != null)
                    if (param.length == 2)
                        multipart.addFormField(param[0],param[1]);

            }
            Response response=multipart.finish();
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
        } catch (IOException e) {
            ErrorParse errorParse = new ErrorParse();
            errorParse.setCode(0);
            errorParse.setError(true);
            errorParse.setMessage(e.toString());
            callback.onErrorParse(errorParse);
            callback.onErrorServer(null);
            callback.onSuccess(null);
        }
    }
}
