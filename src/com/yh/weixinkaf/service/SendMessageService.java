package com.yh.weixinkaf.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import net.sf.json.JSONObject;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;

import com.google.gson.Gson;
import com.yh.weixinkaf.pojo.message.send.*;
import com.yh.weixinkaf.util.WeiXinUtil;
import com.yh.weixinkaf.test.*;

/**@desc  : 发送消息
 * 
 * @author: shirayner
 * @date  : 2017-8-18 上午10:06:23
 */
public class SendMessageService {
	private  Logger log = LoggerFactory.getLogger(UserTest.class);

	private  String sendMessage_url="https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN";

    /**
     * @desc ：0.公共方法：发送消息
     *  
     * @param accessToken
     * @param message void
     */
	public void sendMessage(String accessToken,BaseMessage message){

		//1.获取json字符串：将message对象转换为json字符串	
		//Gson gson = new Gson();

		String jsonMessage = JSON.toJSON(message).toString();      //使用gson.toJson(user)即可将user对象顺序转成json
		System.out.println("jsonTextMessage:"+jsonMessage);


		//2.获取请求的url  
		sendMessage_url=sendMessage_url.replace("ACCESS_TOKEN", accessToken);

		//3.调用接口，发送消息
		JSONObject jsonObject = WeiXinUtil.httpRequest(sendMessage_url, "POST", jsonMessage);  
		System.out.println("jsonObject:"+jsonObject.toString());

		//4.错误消息处理
		if (null != jsonObject) {
			if (0 != jsonObject.getInteger("errcode")) {
				log.error("发送失败 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));
			}
		}
	}
	
	
	
	
}
