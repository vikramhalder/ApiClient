package ml.vikisoft.ApiClient;

import ml.vikisoft.ApiClient.Data.Execute;
import ml.vikisoft.ApiClient.Entity.*;
import ml.vikisoft.ApiClient.Entity.Interface.GetResponse;

public class PostApiMethod {
    private String url="";
    private Params params=new Params();
    private Headers headers =new Headers();

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

    public void execute(GetResponse callback){
         Response res=new Execute().executePostMethod(url,params, headers);
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
