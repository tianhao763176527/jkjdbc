package main.java.com.yh.test.controller.test;

/**
 * Created by tianhao on 2018/5/29.
 */
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ExcelToHtml {

    public String translateToHtml(List<Map<String,String >> list){
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
          html.append(getChildList(list,map.get("cid")));
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
        return list;
    }

    public String getChiHtmlStr(List<Map<String,String>> list ){
        StringBuilder strBuilder= new StringBuilder();
        strBuilder.append("<tr><td>");
        for (Map<String,String> map : list){
            strBuilder.append("<a href=\"");
            strBuilder.append(map.get("url")+"\"");
            strBuilder.append("target=\"_blank\"");
            strBuilder.append("isactivity=\"");
            strBuilder.append(map.get("isactivity")+"\"");
            strBuilder.append(map.get("name"));
            strBuilder.append(">");
        }
        strBuilder.append("</td></tr>");
        return strBuilder.toString();
    }

    public String getChiHtmlStr(Map<String,String> map ){
        StringBuilder strBuilder= new StringBuilder();
        strBuilder.append("<td>");
            strBuilder.append("<a href=\"");
            strBuilder.append(map.get("url")+"\"");
            strBuilder.append("target=\"_blank\"");
            strBuilder.append("isactivity=\"");
            strBuilder.append(map.get("isactivity")+"\"");
            strBuilder.append(map.get("name"));
            strBuilder.append(">");
        strBuilder.append("</td>");
        return strBuilder.toString();
    }

    public static void main(String[] args){
        String filePath = "E:\\url.xlsx";
        String columns[] = {"name","url",};
       // List<Map<String,String>> list= redExcelFile(filePath,columns);
    }
}
