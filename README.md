<img src="apiclient.svg" title=""/>

# ApiClient

ApiClient is an HTTP library that makes networking for Java/Android apps easier and faster.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Installing

Step 1. Add the JitPack repository to your build file Add it in your build.gradle at the end of repositories:
```
allprojectsgroovy {
    repositories {
        ......
        maven { url "https://jitpack.io" }
    }
}
```
Step 2. Add the dependency in the form
```groovy
dependencies { 
    compile 'com.github.vikramhalder:ApiClient:master-SNAPSHOT'
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
import ErrorParse;
import ErrorServer;
import Response;
import GetResponse;

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
```

