package ml.vikisoft.ApiClient.Entity;

import java.util.ArrayList;

public class Params {
    private String name;
    private String value;
    private ArrayList<String[]> params=new ArrayList<>();

    public Params add(String name,String value){
        params.add(new String[]{name,value});
        return this;
    }
    public ArrayList<String[]> getParams() {
        return this.params;
    }
}
