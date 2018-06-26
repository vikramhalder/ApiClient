package ml.vikisoft.ApiClient;

import ml.vikisoft.ApiClient.Data.MultiPart;
import ml.vikisoft.ApiClient.Entity.*;
import ml.vikisoft.ApiClient.Entity.Interface.GetResponse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PostFormData {
    private String url="";
    private Params params=new Params();
    private Headers headers =new Headers();
    private List<String[]> filepart=new ArrayList<>();
    public PostFormData setUrl(String url) {
        this.url = url;
        return this;
    }
    public PostFormData setParams(String name, String value){
        this.params.add(name,value);
        return this;
    }
    public PostFormData setHeaders(String name, String value){
        this.headers.add(name,value);
        return this;
    }
    public PostFormData addFilePart(String param_name, String file_path){
        filepart.add(new String[]{param_name,file_path});
        return this;
    }
    public void execute(GetResponse callback){
        try {
            MultiPart multipart = new MultiPart(url,headers);
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
                        System.out.println(files[0]);
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
