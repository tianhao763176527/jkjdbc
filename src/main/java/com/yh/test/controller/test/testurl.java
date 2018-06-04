package main.java.com.yh.test.controller.test;

import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xuhen on 2018/3/1.
 */
public class testurl {

	public static void main(String[] args) throws Exception {
		//开始判断链接是否有效
        long starTime =  System.currentTimeMillis();
		try {
			URL url=new URL("http://10.128.7.231:8080/hbcj/login.jsp");
			URLConnection conn=url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			String str=conn.getHeaderField(0);
			if (!str.isEmpty() && str.indexOf("OK")> 0){
				//网址正常
				System.out.println("YES");
			}else{
				//网址不正常
				System.out.println("NO");
			}
		} catch (Exception ex) {
			//网址不正常
			ex.printStackTrace();
			System.out.println("NO");
		}
        long endTime =  System.currentTimeMillis();
		System.out.println(endTime-starTime);
	}
}
