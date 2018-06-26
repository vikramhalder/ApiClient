package ml.vikisoft.ApiClient;
import ml.vikisoft.ApiClient.Entity.ErrorParse;
import ml.vikisoft.ApiClient.Entity.ErrorServer;
import ml.vikisoft.ApiClient.Entity.Response;
import ml.vikisoft.ApiClient.Entity.Interface.GetResponse;

public class Example {
    public static void main(String[] args){
        new GetApiMethod()
                .setUrl("http://localhost/test.php")
                .setParams("id","1")
                .setParams("language","en-GB")
                .setHeaders("Authorization","Bearer fnsdtikerghvdbjnviksdee")
                .execute(new GetResponse() {
                    @Override
                    public void onSuccess(Response response) {
                        if(response!=null)
                            System.out.println(response.getData());
                    }
                    @Override
                    public void onErrorParse(ErrorParse errorParse) {
                        if(errorParse!=null)
                            System.out.print(errorParse.getMessage());
                    }
                    @Override
                    public void onErrorServer(ErrorServer errorServer) {
                        if(errorServer!=null)
                            System.out.print(errorServer.getCode());
                    }
                });
        new PostApiMethod()
                .setUrl("http://localhost/test.php")
                .setParams("id","1")
                .setParams("language","en-GB")
                .setHeaders("Authorization","Bearer fnsdtikerghvdbjnviksdee")
                .execute(new GetResponse() {
                    @Override
                    public void onSuccess(Response response) {
                        if(response!=null)
                            System.out.println(response.getData());
                    }
                    @Override
                    public void onErrorParse(ErrorParse errorParse) {
                        if(errorParse!=null)
                            System.out.print(errorParse.getMessage());
                    }
                    @Override
                    public void onErrorServer(ErrorServer errorServer) {
                        if(errorServer!=null)
                            System.out.print(errorServer.getCode());
                    }
                });

        new PostFormData()
                .setUrl("http://localhost/test.php")
                .setParams("id","1")
                .setParams("language","en-GB")
                .setHeaders("Authorization","Bearer fnsdtikerghvdbjnviksdee")
                .addFilePart("file1","C:\\a.txt")
                .addFilePart("file12","C:\\a.txt")
                .execute(new GetResponse() {
                    @Override
                    public void onSuccess(Response response) {
                        if(response!=null)
                            System.out.println(response.getData());
                    }
                    @Override
                    public void onErrorParse(ErrorParse errorParse) {
                        if(errorParse!=null)
                            System.out.print(errorParse.getMessage());
                    }
                    @Override
                    public void onErrorServer(ErrorServer errorServer) {
                        if(errorServer!=null)
                            System.out.print(errorServer.getCode());
                    }
                });
    }
}