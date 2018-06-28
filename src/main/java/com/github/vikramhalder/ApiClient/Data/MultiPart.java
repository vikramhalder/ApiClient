package com.github.vikramhalder.ApiClient.Data;

import com.github.vikramhalder.ApiClient.Entity.Config;
import com.github.vikramhalder.ApiClient.Entity.Header;
import com.github.vikramhalder.ApiClient.Entity.Headers;
import com.github.vikramhalder.ApiClient.Entity.Response;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MultiPart {
    private HttpURLConnection httpConn;
    private DataOutputStream request;
    private final String boundary =  "*****";
    private final String crlf = "\r\n";
    private final String twoHyphens = "--";
    private PrintWriter writer;
    private boolean printStackTrace;
    /**
     *
     * @param requestURL
     * @param headers
     * @throws IOException
     */
    public MultiPart(String requestURL,Headers headers, int connectTimeout,int readTimeout,boolean printStackTrace) throws IOException {
        this.printStackTrace=printStackTrace;
        URL url = new URL(requestURL);
        httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setUseCaches(false);
        httpConn.setDoOutput(true); // indicates POST method
        httpConn.setDoInput(true);
        httpConn.setConnectTimeout(connectTimeout);
        httpConn.setReadTimeout(readTimeout);

        httpConn.setRequestMethod("POST");
        if(headers !=null) {
            for (String[] head : headers.getHeaders()) {
                if (head != null)
                    if (head.length == 2)
                        if (head[0] != null && head[1] != null)
                            httpConn.setRequestProperty(head[0], head[1]);
                        else if (head[0] != null && head[1] == null)
                            httpConn.setRequestProperty(head[0],"");
            }
        }
        httpConn.setRequestProperty("Connection", "Keep-Alive");
        httpConn.setRequestProperty("Cache-Control", "no-cache");
        httpConn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + this.boundary);
        request =  new DataOutputStream(httpConn.getOutputStream());
        writer = new PrintWriter(new OutputStreamWriter(request, "UTF-8"),true);
    }


    public void addFormField(String name, String value)throws IOException  {
        request.writeBytes(this.twoHyphens + this.boundary + this.crlf);
        request.writeBytes("Content-Disposition: form-data; name=\"" + name + "\""+ this.crlf);
        request.writeBytes("Content-Type: text/plain; charset=UTF-8" + this.crlf);
        request.writeBytes(this.crlf);
        request.writeBytes(value + this.crlf);
        request.flush();
    }


    public void addFilePart(String fieldName, File uploadFile)  throws IOException {
        /**
         * On Upload
         */
        /*String fileName = uploadFile.getName();
        request.writeBytes(this.twoHyphens + this.boundary + this.crlf);
        request.writeBytes("Content-Disposition: form-data; name=\"" +
                fieldName + "\";filename=\"" +
                fileName + "\"" + this.crlf);
        request.writeBytes(this.crlf);

        byte[] bytes = Files.readFile(uploadFile);
        request.write(bytes);*/

        /**
         * Multiple upload
         */
        String fileName = uploadFile.getName();
        writer.append(this.twoHyphens + this.boundary + this.crlf);
        writer.append("Content-Disposition: form-data; name=\"" + fieldName+ "\"; filename=\"" + fileName + "\"").append(this.crlf);
        writer.append("Content-Type: "+ httpConn.guessContentTypeFromName(fileName)).append(this.crlf);
        writer.append("Content-Transfer-Encoding: binary").append(this.crlf);
        writer.append(this.crlf);
        writer.flush();

        byte[] bytes = Files.readFile(uploadFile);
        request.write(bytes);
        writer.append(this.crlf);
        writer.flush();
    }


    public Response finish(){
        Response response=new Response();
        try {
            request.writeBytes(this.crlf);
            request.writeBytes(this.twoHyphens + this.boundary +this.twoHyphens + this.crlf);
            request.flush();
            request.close();

            int status = httpConn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                InputStream responseStream = new
                        BufferedInputStream(httpConn.getInputStream());

                BufferedReader responseStreamReader =
                        new BufferedReader(new InputStreamReader(responseStream));

                String line = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((line = responseStreamReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }

                List<Header> headerList=new ArrayList<>();
                Map<String, List<String>> map = httpConn.getHeaderFields();
                for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                    Header header=new Header();
                    header.setName(entry.getKey());
                    header.setValue(""+entry.getValue());
                    headerList.add(header);
                }

                responseStreamReader.close();

                response.setCode(status);
                response.setError(false);
                response.setData(stringBuilder.toString());
                httpConn.disconnect();
            } else {
                response.setCode(httpConn.getResponseCode());
                response.setError(true);
                response.setMessage("Server Error");
                for(Map.Entry m:Config.errorList().entrySet()){
                    if(httpConn.getResponseCode()==(int)m.getKey())
                        response.setMessage(""+m.getValue());
                }
                httpConn.disconnect();
            }
        }catch (IOException io){
            if(printStackTrace)
                io.printStackTrace();
            response.setCode(0);
            response.setError(true);
            response.setMessage(io.toString());
        }catch (Exception ex){
            if(printStackTrace)
                ex.printStackTrace();
            response.setCode(0);
            response.setError(true);
            response.setMessage(ex.toString());
        }
        return response;
    }
}
