package com.yh.monitor.util;

/**
 * Created by tianhao on 2018/5/29.
 */
import java.util.*;

import main.java.com.yh.test.controller.test.ReadExcel;

public class ExcelToHtml {
    public  ExcelToHtml(){};
    public  String translateToHtml(List<Map<String,String >> list){
        StringBuilder html = new StringBuilder();
        /*存放父类节点ID*/
        List<Map<String,String>> pidList;
        html.append("<table>");
        //找到树的父节点
        pidList=getChildList(list,"1");
        //根据父节点ID找到相应的子节点ID
        for (Map<String,String> map : pidList){
            html.append("<tr>");
            html.append(getChiHtmlStr(map));
            Map<String,List> leftMap = getLefList(getChildList(list,map.get("cid")));
            Iterator iterator = leftMap.keySet().iterator();
            while (iterator.hasNext()){
                html.append(getChiHtmlStr(leftMap.get(iterator.next().toString())));
            }
            html.append("</tr>");
        }
        html.append("</table>");
        return html.toString();
    }

    public List getChildList(List<Map<String,String>> list,String pid){
        List resultlist = new ArrayList();
        for(Map<String,String> pmap : list){
            if(pid.equals(pmap.get("pid"))){
                resultlist.add(pmap);
            }
        }
        return resultlist;
    }

    public Map<String,List> getLefList(List<Map<String,String>> list){
        Map<String,List> resultlist = new HashMap<String,List>();
        Map cidmap = new HashMap();
        for(Map<String,String> cmap : list){
            cidmap.put(cmap.get("cid"),"cid");
        }
        Iterator iterator= cidmap.keySet().iterator();
        List temlist;
        String cid="";
        while (iterator.hasNext()){
            temlist = new ArrayList();
            cid=iterator.next().toString();
            for(Map<String,String> map : list){
                if (cid.equals(map.get("cid"))){
                    temlist.add(map);
                }
            }
            resultlist.put(cid,temlist);
        }
        return resultlist;
    }

    public String getChiHtmlStr(List<Map<String,String>> list ){
        StringBuilder strBuilder= new StringBuilder();
        strBuilder.append("<td>");
        for (Map<String,String> map : list){
            strBuilder.append("<p>");
            strBuilder.append("<a href=\"");
            strBuilder.append(map.get("url")+"\"");
            strBuilder.append(" target=\"_blank\"");
            strBuilder.append(" isactivity=\"");
            strBuilder.append(map.get("isactivity")+"\"");
            strBuilder.append(">");
            strBuilder.append(map.get("name"));
            strBuilder.append("</a></p>");
        }
        strBuilder.append("</td>");
        return strBuilder.toString();
    }

    public String getChiHtmlStr(Map<String,String> map ){
        StringBuilder strBuilder= new StringBuilder();
        strBuilder.append("<td>");
        strBuilder.append("<p>");
        strBuilder.append("<a href=\"");
        strBuilder.append(map.get("url")+"\"");
        strBuilder.append(" target=\"_blank\"");
        strBuilder.append(" isactivity=\"");
        strBuilder.append(map.get("isactivity")+"\"");
        strBuilder.append(">");
        strBuilder.append(map.get("name"));
        strBuilder.append("</a></p></td>");
        return strBuilder.toString();
    }

    public static void main(String[] args){
        String filePath = "E:\\url.xlsx";
        String columns[] = {"name","url","isactivity","cid","pid"};
        List<Map<String,String>> list= ReadExcel.redExcelFile(filePath,columns);

        System.out.print(new ExcelToHtml().translateToHtml(list));
    }
}
