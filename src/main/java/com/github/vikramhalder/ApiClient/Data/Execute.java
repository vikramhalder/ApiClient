package com.github.vikramhalder.ApiClient.Data;

import com.github.vikramhalder.ApiClient.Entity.Headers;
import com.github.vikramhalder.ApiClient.Entity.Params;
import com.github.vikramhalder.ApiClient.Entity.Response;
import com.github.vikramhalder.ApiClient.Entity.Header;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Execute {
    public Response executeGetMethod(String base_url, Params params, Headers headers){
        Response response=new Response();
        if(params!=null) {
            try {
                int i = 0;
                for (String[] param : params.getParams()) {
                    if (param != null)
                        if (param.length == 2)
                            if (param[0] != null && param[1] != null)
                                if (i == 0)
                                    base_url += "?" + param[0] + "=" + URLEncoder.encode(param[1], "UTF-8");
                                else
                                    base_url += "&" + param[0] + "=" + URLEncoder.encode(param[1], "UTF-8");
                    i++;
                }
            }catch (Exception ex){
                response.setCode(0);
                response.setError(true);
                response.setMessage(ex.toString());
                return response;
            }
        }

        response.setUrl(base_url);
        try{
            URL url = new URL(base_url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            if(headers !=null) {
                for (String[] head : headers.getHeaders()) {
                    if (head != null)
                        if (head.length == 2)
                            if (head[0] != null && head[1] != null)
                                urlConnection.setRequestProperty(head[0], head[1]);
                            else if (head[0] != null && head[1] == null)
                                urlConnection.setRequestProperty(head[0],"");
                }
            }
            if (urlConnection.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String inputLine;
                String newLine = System.getProperty("line.separator");
                while ((inputLine = in.readLine()) != null) {
                    sb.append(inputLine + newLine);
                }

                List<Header> headerList=new ArrayList<>();
                Map<String, List<String>> map = urlConnection.getHeaderFields();
                for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                    Header header=new Header();
                    header.setName(entry.getKey());
                    header.setValue(""+entry.getValue());
                    headerList.add(header);
                }
                response.setHeaders(headerList);
                response.setData(sb.toString());
                response.setCode(urlConnection.getResponseCode());
                response.setError(false);
            }else {
                response.setCode(urlConnection.getResponseCode());
                response.setError(true);
                response.setMessage("server error");
            }
        }catch (Exception ex){
            response.setCode(0);
            response.setError(true);
            response.setMessage(ex.toString());
        }
        return response;
    }

    public Response executePostMethod(String base_url, Params params, Headers headers){
        Response response=new Response();
        try {
            URL url = new URL(base_url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            if(headers !=null) {
                for (String[] head : headers.getHeaders()) {
                    if (head != null)
                        if (head.length == 2)
                            if (head[0] != null && head[1] != null)
                                urlConnection.setRequestProperty(head[0], head[1]);
                            else if (head[0] != null && head[1] == null)
                                urlConnection.setRequestProperty(head[0],"");
                }
            }
            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            Object[] objects=postQuery(params.getParams());
            if((boolean)objects[0]) {
                writer.write((String) objects[1]);
                writer.flush();
                writer.close();
                os.close();
                if (urlConnection.getResponseCode() == 200) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String inputLine;
                    String newLine = System.getProperty("line.separator");
                    while ((inputLine = in.readLine()) != null) {
                        sb.append(inputLine + newLine);
                    }

                    List<Header> headerList=new ArrayList<>();
                    Map<String, List<String>> map = urlConnection.getHeaderFields();
                    for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                        Header header=new Header();
                        header.setName(entry.getKey());
                        header.setValue(""+entry.getValue());
                        headerList.add(header);
                    }
                    response.setHeaders(headerList);
                    response.setData(sb.toString());
                    response.setCode(urlConnection.getResponseCode());
                    response.setError(false);
                } else {
                    response.setCode(urlConnection.getResponseCode());
                    response.setError(true);
                    response.setMessage("server error");
                }
            }else {
                response.setCode(0);
                response.setError(true);
                response.setMessage((String) objects[1]);
            }
        } catch (Exception ex) {
            response.setCode(0);
            response.setError(true);
            response.setMessage(ex.toString());
        }
        response.setUrl(base_url);
        return response;
    }

    private Object[] postQuery(List<String[]> params)
    {
        try {
            StringBuilder result = new StringBuilder();
            boolean first = true;
            for (String[] pair : params) {
                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(pair[0], "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(pair[1], "UTF-8"));
            }
            return new Object[]{true,result.toString()};
        }catch (Exception ex){
            return new Object[]{false,ex.toString()};
        }
    }
}
