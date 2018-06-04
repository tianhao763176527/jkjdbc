package com.yh.monitor.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by tianhao on 2018/5/16.
 * 功能：用于读取properties文件
 */
public class RedProperties {
    private Properties properties;

    public RedProperties(String path){
        properties = new Properties();
        InputStreamReader is = null;
        try{
           is = new InputStreamReader(this.getClass().getResourceAsStream(path),"UTF-8");
           properties.load(is);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(is !=null){
                    is.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public Map getPropertiesContext(){
        Map resMap =new HashMap();
        Iterator iterator = properties.stringPropertyNames().iterator();
        String name;
        while (iterator.hasNext()){
            name = iterator.next().toString();
            resMap.put(name,properties.getProperty(name));
        }
        return resMap;
    }

    public static void main(String[] args){
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath()+ "/com/yh/monitor/util/url.properties";
        RedProperties redProperties = new RedProperties(path);
        Map map = redProperties.getPropertiesContext();
        System.out.println(map.get("url1"));
    }
}
