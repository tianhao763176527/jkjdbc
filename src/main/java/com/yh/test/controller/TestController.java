package main.java.com.yh.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by tianhao on 2018/5/16.
 */
@Controller
public class TestController {
        @RequestMapping("testController.do")
        public String testSpring(){
            return "hello world";
        }
}

