package ml.vikisoft.ApiClient.Entity.Interface;

import ml.vikisoft.ApiClient.Entity.ErrorServer;
import ml.vikisoft.ApiClient.Entity.Response;
import ml.vikisoft.ApiClient.Entity.ErrorParse;

public interface GetResponse {
    void onSuccess(Response response);
    void onErrorParse(ErrorParse errorParse);
    void onErrorServer(ErrorServer errorServer);
}