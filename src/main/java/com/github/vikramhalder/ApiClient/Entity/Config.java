package com.github.vikramhalder.ApiClient.Entity;

import java.util.*;

public class Config {
    public static Map<Integer,String> errorList(){
        Map<Integer,String> map=new HashMap<Integer,String>();
        map.put(302,"Found: Resource temporarily located elsewhere according to the Location header.");
        map.put(303,"Media Download Redirect");
        map.put(304,"Not Modified");
        map.put(307,"Temporary Redirect");
        map.put(308,"Resume Incomplete");
        map.put(400,"Bad Request");
        map.put(401,"Unauthorized");
        map.put(403,"Forbidden");
        map.put(404,"Not Found");
        map.put(405,"Method Not Allowed");
        map.put(409,"Conflict");
        map.put(410,"Gone");
        map.put(411,"Length Required");
        map.put(412,"Precondition Failed");
        map.put(413,"Payload Too Large");
        map.put(416,"Requested Range Not Satisfiable");
        map.put(423,"Too Many Requests");
        map.put(500,"Internal Server Error");
        map.put(502,"Bad Gateway");
        map.put(503,"Service Unavailable");

        return map;
    }
}
