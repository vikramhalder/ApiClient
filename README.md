<img src="apiclient.svg" title=""/>

# ApiClient

ApiClient is an HTTP library that makes networking for Java/Android apps easier and faster.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Installing

Step 1. Add the JitPack repository to your build file Add it in your build.gradle at the end of repositories:
```groovy
allprojects {
    repositories { 
        maven { url "https://jitpack.io" }
    }
}
```
Step 2. Add the dependency in the form
```groovy
dependencies { 
    compile 'com.github.vikramhalder:ApiClient:1.0.0'
}
```
 or 
 ```groovy
 dependencies { 
     compile 'com.github.vikramhalder:ApiClient:develop-SNAPSHOT'
 }
 ```
## Running the tests

```java
import com.github.vikramhalder.ApiClient.Entity.Response;
import com.github.vikramhalder.ApiClient.Entity.ErrorParse;
import com.github.vikramhalder.ApiClient.Entity.ErrorServer;
import com.github.vikramhalder.ApiClient.Entity.Interface.GetResponse;

public class Example {
    public static void main(String[] args){
        /**
         * For Normal Get Request with/without params,header,timeout etc
         */
        new GetApiMethod()
                .setUrl("https://google.com")
                .setParams("id","1")
                .setParams("language","en-GB")
                .setHeaders("Authorization","Bearer ansdtikerghvdbjnviksdeev1==")
                .setConnectTimeout(5) /**Optional**/
                .setReadTimeout(6) /**6 seconds , Optional*/
                .setPrintStackTrace(true) /**print all error, Optional*/
                .execute(new GetResponse() {
                    @Override
                    public void onSuccess(Response response) {
                        if(response!=null)
                            System.out.println(response.getData());
                    }
                    @Override
                    public void onErrorParse(ErrorParse errorParse) {
                        if(errorParse!=null)
                            System.out.println(errorParse.getMessage());
                    }
                    @Override
                    public void onErrorServer(ErrorServer errorServer) {
                        if(errorServer!=null)
                            System.out.println(errorServer.getMessage());
                    }
                });

        /**
         * For Post Request with/without params,header,timeout etc
         */
        new PostApiMethod()
                .setUrl("http://unknown.host")
                .setParams("id","1")
                .setParams("language","en-GB")
                .setHeaders("Authorization","Bearer ansdtikerghvdbjnviksdeev1==")
                .setConnectTimeout(5) /**5 seconds , Optional*/
                .setReadTimeout(6)   /**6 seconds , Optional*/
                .setPrintStackTrace(false) /**print all error, Optional*/
                .execute(new GetResponse() {
                    @Override
                    public void onSuccess(Response response) {
                        if(response!=null)
                            System.out.println(response.getData());
                    }
                    @Override
                    public void onErrorParse(ErrorParse errorParse) {
                        if(errorParse!=null)
                            System.out.println(errorParse.getMessage());
                    }
                    @Override
                    public void onErrorServer(ErrorServer errorServer) {
                        if(errorServer!=null)
                            System.out.println(errorServer.getMessage());
                    }
                });

        /**
         * For Multipart Get/Post Request with/without params,header,files,timeout etc
         */
        new PostFormData()
                .setUrl("http://localhost/test.php")
                .setParams("id","1")
                .setParams("language","en-GB")
                .setHeaders("Authorization","Bearer ansdtikerghvdbjnviksdeev1==")
                .addFilePart("file1","C:\\a.txt")
                .addFilePart("file12","C:\\a.txt")
                .setConnectTimeout(5) /**5 seconds , Optional*/
                .setReadTimeout(5)   /**5 seconds , Optional*/
                .setPrintStackTrace(true) /**print all error, Optional*/
                .execute(new GetResponse() {
                    @Override
                    public void onSuccess(Response response) {
                        if(response!=null)
                            System.out.println(response.getData());
                    }
                    @Override
                    public void onErrorParse(ErrorParse errorParse) {
                        if(errorParse!=null)
                            System.out.println(errorParse.getMessage());
                    }
                    @Override
                    public void onErrorServer(ErrorServer errorServer) {
                        if(errorServer!=null)
                            System.out.println(errorServer.getCode());
                    }
                });
    }
}
```

