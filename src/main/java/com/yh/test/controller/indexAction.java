package main.java.com.yh.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by tianhao on 2018/5/16.
 */
@Controller
public class indexAction {

    @RequestMapping("indexAction.do")
    public String index(HttpServletRequest request, HttpServletResponse response){

        return "/index";
    }
}
