package cn.lqdev.java.scheduler;

import java.util.function.Consumer;

import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

/** 
*
* @ClassName   类名：WebClientTest 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2019年3月6日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2019年3月6日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class WebClientTest {
   public static void main(String[] args) throws InterruptedException {
	 WebClient.create().post().uri("http://172.18.169.72:8001/api/citycode/authcode");
//	 result.subscribe(new Consumer<String>() {
//
//			@Override
//			public void accept(String t) {
//				System.out.println(t);
//			}
//		});	
//	 System.out.println(result.block());
	 Thread.sleep(10000);
}
}
