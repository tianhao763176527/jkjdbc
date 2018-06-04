package com.yh.weixinkaf.action;

import com.yh.weixinkaf.aes.AesException;
import com.yh.weixinkaf.aes.WXBizMsgCrypt;
import com.yh.weixinkaf.pojo.message.send.Text;
import com.yh.weixinkaf.pojo.message.send.TextMessage;
import com.yh.weixinkaf.service.MessageService;
import com.yh.weixinkaf.service.SendMessageService;
import com.yh.weixinkaf.util.WeiXinParamesUtil;
import com.yh.weixinkaf.util.WeiXinUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by tianhao on 2018/5/31.
 */
@Controller
public class CoreAction {
    @RequestMapping(value="/coreAction.do",produces="text/html;charset=UTF-8")
    @ResponseBody
    public void excute(HttpServletResponse response, HttpServletRequest request) throws Exception{
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        // 微信加密签名
        String msg_signature = request.getParameter("msg_signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        System.out.println("request=" + request.getRequestURL());

        PrintWriter out = response.getWriter();
        // 通过检验msg_signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        String result = null;
        try {
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(WeiXinParamesUtil.token, WeiXinParamesUtil.encodingAESKey, WeiXinParamesUtil.corpId);
            result = wxcpt.VerifyURL(msg_signature, timestamp, nonce, echostr);
        } catch (AesException e) {
            e.printStackTrace();
        }
        if (result == null) {
            result = WeiXinParamesUtil.token;
        }
        out.print(result);
        out.close();
        out = null;
    }

   /* public void excute(HttpServletResponse response,HttpServletRequest request) throws Exception{
        //1.将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //2.调用消息业务类接收消息、处理消息
        MessageService msgsv=new MessageService();
        String respMessage = msgsv.getEncryptRespMessage(request);

        //处理表情
        // String respMessage = CoreService.processRequest_emoj(request);
        //处理图文消息
        //String respMessage = Test_NewsService.processRequest(request);


        //3.响应消息
        PrintWriter out = response.getWriter();
        out.print(respMessage);
        out.close();
    }*/

    @RequestMapping(value="/sendErrorMsg.do",produces="text/html;charset=UTF-8")
    @ResponseBody
    public void sendErrorMsg(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //获取提交的数据
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        //0.设置消息内容
        String msg = request.getParameter("msg").replace(" ","");
        //获取当前时间
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        String currdate = dateFormat.format(date);

        String content ="<div class=\"highlight\">警告！警告！</div>"
                +"<div class=\"gray\">"+currdate+"</div>\n\n"
                +"<div class=\"normal\">" +msg+"；异常</div>"
                +"<div class=\"highlight\">请尽快处理！</div>";
        //1.创建文本消息对象
        TextMessage message=new TextMessage();
        //1.1非必需
        message.setTouser("tianhao");  //不区分大小写
        //1.2必需
        message.setMsgtype("text");
        message.setAgentid(WeiXinParamesUtil.agentId);

        Text text=new Text();
        text.setContent(content);
        message.setText(text);

        //2.获取access_token:根据企业id和通讯录密钥获取access_token,并拼接请求url
        String accessToken= WeiXinUtil.getAccessToken(WeiXinParamesUtil.corpId, WeiXinParamesUtil.agentSecret).getToken();
        System.out.println("accessToken:"+accessToken);

        //3.发送消息：调用业务类，发送消息
        SendMessageService sms=new SendMessageService();
        sms.sendMessage(accessToken, message);
    }
}
