package com.yh.monitor.action;

import com.yh.monitor.domain.UrlInforVo;
import com.yh.weixinkaf.pojo.message.send.Text;
import com.yh.weixinkaf.pojo.message.send.TextMessage;
import com.yh.weixinkaf.service.SendMessageService;
import com.yh.weixinkaf.util.WeiXinParamesUtil;
import com.yh.weixinkaf.util.WeiXinUtil;
import main.java.com.yh.test.controller.test.ReadExcel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URL;
import java.util.*;

import com.yh.monitor.util.*;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSON;

/**
 * Created by tianhao on 2018/5/16.
 */
@Controller
public class MonitorUrlAction {

    @RequestMapping("/monitorUrlAction.do")
    public String excute() throws Exception{
        /*ModelAndView mv =new ModelAndView();
        //封装显示到试图的数据
        mv.addObject("msg","hello");
        mv.setView("/monitor/monitor_url");*/
        return "/monitor/monitor_url3";
    }
    @RequestMapping(value="/getTableHtml.do",produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getTableHtml() throws Exception{
        String filePath = "E:\\url.xlsx";
        String columns[] = {"name","url","isactivity","cid","pid"};
        List<Map<String,String>> list= ReadExcel.redExcelFile(filePath,columns);

        return new ExcelToHtml().translateToHtml(list);
    }

    @RequestMapping(value="/getOneAllUrlResInfo.do",produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getOneAllUrlResInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //获取提交的数据
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        String jsonStr = JSON.toJSONString(loadUrlInfor());
        return jsonStr;
    }


    @RequestMapping(value="/getUrlResInfo.do",produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getUrlResInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //获取提交的数据
        request.setCharacterEncoding("utf-8");
        String url = request.getParameter("url");
        response.setContentType("text/html; charset=utf-8");
        String jsonStr = JSON.toJSONString(HttpUrlResponse.getUrlResponseInforByUrl(url));
        return jsonStr;
    }

    @RequestMapping(value="/getUrlInfo.do",produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getUrlInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
        response.setContentType("text/html; charset=utf-8");
        //获取文件中所有url信息
        String jsonStr = JSON.toJSONString( getUrlInforInFile());
        return jsonStr;
    }

    public List<UrlInforVo> getUrlInforInFile(){
        String path = "url.properties";
        Map proMap = new RedProperties(path).getPropertiesContext();
        List<UrlInforVo> urlInforVoList = new ArrayList<UrlInforVo>();
        UrlInforVo urlInforVo;
        Set<String> set = proMap.keySet();
        for(String key : set){
            if (!key.endsWith("name")){
                urlInforVo = new UrlInforVo();
                urlInforVo.setUrl(proMap.get(key).toString());
                if(set.contains(key+".name")){
                    urlInforVo.setName(proMap.get(key+".name").toString());
                }
                urlInforVoList.add(urlInforVo);
            }
        }
        return urlInforVoList;
    }

    /**
     * 获取全部的url响应信息
     * @return
     */
    public List<UrlInforVo> loadUrlInfor(){
        List<UrlInforVo> urlInforVoList= getUrlInforInFile();
        List<UrlInforVo> responsList = new HttpUrlResponse().getBatchUrlResponseInfor(urlInforVoList);
        return responsList;
    };

    @RequestMapping(value="/getUrlListResInfo.do",produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getUrlListResInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //获取提交的数据
        request.setCharacterEncoding("utf-8");
        String data = request.getParameter("data");
        UrlInforVo urlInforVo;
        Map map ;
        List<UrlInforVo> urlInforVoList = new ArrayList<UrlInforVo>();
        List list = JSON.parseArray(data);
        for (int i=0;i<list.size();i++){
            map = (Map) list.get(i);
            urlInforVo=new UrlInforVo();
            urlInforVo.setUrl(map.get("url").toString());
            urlInforVo.setId(map.get("id").toString());
            urlInforVoList.add(urlInforVo);
        }
        response.setContentType("text/html; charset=utf-8");
        List<UrlInforVo> responsList = new HttpUrlResponse().getBatchUrlResponseInfor(urlInforVoList);
        String jsonStr = JSON.toJSONString(responsList);
        return jsonStr;
    }
}
