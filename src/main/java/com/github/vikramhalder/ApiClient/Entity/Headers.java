package com.github.vikramhalder.ApiClient.Entity;

import java.util.ArrayList;

public class Headers {
    private String name;
    private String value;
    private ArrayList<String[]> header=new ArrayList<>();

    public Headers add(String name, String value){
        header.add(new String[]{name,value});
        return this;
    }
    public ArrayList<String[]> getHeaders() {
        return this.header;
    }
}
