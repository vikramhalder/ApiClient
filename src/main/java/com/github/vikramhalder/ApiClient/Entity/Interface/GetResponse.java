package com.github.vikramhalder.ApiClient.Entity.Interface;

import com.github.vikramhalder.ApiClient.Entity.Response;
import com.github.vikramhalder.ApiClient.Entity.ErrorServer;
import com.github.vikramhalder.ApiClient.Entity.ErrorParse;

public interface GetResponse {
    void onSuccess(Response response);
    void onErrorParse(ErrorParse errorParse);
    void onErrorServer(ErrorServer errorServer);
}