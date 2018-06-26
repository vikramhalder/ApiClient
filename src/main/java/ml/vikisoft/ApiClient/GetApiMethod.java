package ml.vikisoft.ApiClient;

import ml.vikisoft.ApiClient.Data.Execute;
import ml.vikisoft.ApiClient.Entity.*;
import ml.vikisoft.ApiClient.Entity.Interface.GetResponse;

public class GetApiMethod {
    private String url="";
    private Params params=new Params();
    private Headers headers =new Headers();

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

    public void execute(GetResponse callback){
         Response response=new Execute().executeGetMethod(url,params, headers);
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
