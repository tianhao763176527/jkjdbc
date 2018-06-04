package com.yh.monitor.util;

import com.yh.monitor.domain.UrlInforVo;

import java.net.URL;
import java.net.URLConnection;
import java.util.*;
/**
 * Created by tianhao on 2018/5/16.
 */
public class HttpUrlResponse {
    public HttpUrlResponse(){

    }
    public UrlInforVo getUrlResponseInfor(UrlInforVo urlInforVo){
        long starTime =  System.currentTimeMillis();
        String state = "NO";
        String urlstr = urlInforVo.getUrl();
        try {
            URL url=new URL(urlstr);
            URLConnection conn=url.openConnection();
            conn.setConnectTimeout(8000);
            conn.setReadTimeout(8000);
            String str=conn.getHeaderField(0);
            if (!str.isEmpty() && str.indexOf("OK")> 0){
                //网址正常
               // System.out.println("YES");
                state = "YES";
            }else{
                //网址不正常
                //System.out.println("NO");
                state = "NO";
            }
        } catch (Exception ex) {
            //网址不正常
            System.out.println("访问异常"+ex+";url:"+urlstr);
            //System.out.println("NO");
            state = "TIMEOUT";
        }
        long endTime =  System.currentTimeMillis();
       // System.out.println(endTime-starTime);
        urlInforVo.setState(state);
        urlInforVo.setRespondTime(endTime-starTime);
        return urlInforVo;
    }

    public static UrlInforVo getUrlResponseInforByUrl(String urlstr){
        long starTime =  System.currentTimeMillis();
        String state = "NO";
        try {
            URL url=new URL(urlstr);
            URLConnection conn=url.openConnection();
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            String str=conn.getHeaderField(0);
            if (!str.isEmpty() && str.indexOf("OK")> 0){
                //网址正常
                // System.out.println("YES");
                state = "YES";
            }else{
                //网址不正常
                //System.out.println("NO");
                state = "NO";
            }
        } catch (Exception ex) {
            //网址不正常
            System.out.println("访问异常"+ex+";url:"+urlstr);
            //System.out.println("NO");
            //超时
            state = "TIMEOUT";
        }
        long endTime =  System.currentTimeMillis();
        // System.out.println(endTime-starTime);
        UrlInforVo urlInforVo = new UrlInforVo();
         urlInforVo.setState(state);
        urlInforVo.setRespondTime(endTime-starTime);
        return urlInforVo;
    }

    public List<UrlInforVo> getBatchUrlResponseInfor(List<UrlInforVo> list){
        List<UrlInforVo> urlInforVoList = new ArrayList<UrlInforVo>();
        for (UrlInforVo url : list){
            urlInforVoList.add(getUrlResponseInfor(url));
        }
        return urlInforVoList;
    }
}
