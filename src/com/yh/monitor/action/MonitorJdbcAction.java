package com.yh.monitor.action;

import com.alibaba.fastjson.JSON;
import com.yh.monitor.domain.JdbcVo;
import com.yh.monitor.domain.UrlInforVo;
import com.yh.monitor.util.HttpUrlResponse;
import com.yh.monitor.util.JdbcConnect;
import com.yh.monitor.util.QEncodeUtil;
import com.yh.monitor.util.RedProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by tianhao on 2018/5/17.
 */
@Controller
public class MonitorJdbcAction {
    @RequestMapping("/monitorJdbcAction.do")
    public String excute() throws Exception{

        return "/monitor/monitor_jdbc";
    }
    @ResponseBody
    @RequestMapping(value="/getJkJdbcInfor.do",produces="text/html;charset=UTF-8")
    public String getJkJdbcInfor(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("text/html; charset=utf-8");

        String jsonStr = JSON.toJSONString(loadUrlInfor());
        System.out.println(jsonStr);
        return jsonStr;
    }
    public List<JdbcVo> loadUrlInfor() throws Exception{
        String path = "jdbc.properties";
        Map proMap = new RedProperties(path).getPropertiesContext();
        List<JdbcVo> jdbcInforVoList = new ArrayList<JdbcVo>();
        JdbcVo jdbcVo;
        Set<String> set = proMap.keySet();
        String driverClassName = proMap.get("jdbc.driverClassName").toString();
        String urlKey,usernameKey,passwordKey;
        for(String key : set){
           if (!(key.endsWith(".url") || key.endsWith(".username") || key.endsWith(".password"))){
               urlKey = key+".url";
               usernameKey = key+".username";
               passwordKey = key+".password";
               if(set.contains(urlKey) && set.contains(usernameKey)&&set.contains(passwordKey)){
                   jdbcVo = new JdbcVo();
                   jdbcVo.setDriverClassName(driverClassName);
                   jdbcVo.setUrl(proMap.get(urlKey).toString());
                   jdbcVo.setUsername(proMap.get(usernameKey).toString());
                   jdbcVo.setPassword(QEncodeUtil.aesDecrypt(proMap.get(passwordKey).toString(),jdbcVo.getUsername()));
                   jdbcVo.setResutData(new JdbcConnect(jdbcVo).getResultData(jdbcVo));
                   jdbcVo.setPassword("************");
                   jdbcVo.setDbaName(proMap.get(key).toString());
                   jdbcInforVoList.add(jdbcVo);
               }
           }
        }
        return jdbcInforVoList;
    };
}
