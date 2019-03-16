package cn.lqdev.java.scheduler.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import cn.lqdev.java.scheduler.biz.entity.SchedConfig;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/** 
*
* @ClassName   类名：JobService 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2019年3月16日
* @author      创建人：oKong
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2019年3月16日   oKong   创建该类功能。
*
***********************************************************************
*</p>
*/
@Component
@Slf4j
public class JobService {

	@Autowired
	@Qualifier("balanceWebClient")
	private WebClient.Builder balanceWebClientBuilder;
	
	@Autowired
	@Qualifier("webClient")
	private WebClient.Builder webClientBuilder;
	
	public String execJob(SchedConfig schedConfig){
		log.info("执行定时任务：{}", schedConfig);
		//根据不同类型进行不同的处理逻辑
		Mono<String> monoRst = null;
		switch (schedConfig.getTargetServiceType()) {
		case "01":
			//springcloud方式
			//利用loadBalancerClient 获取实际服务地址
			monoRst = balanceWebClientBuilder.build().post().uri(schedConfig.getTargerService()).retrieve().bodyToMono(String.class);
			 break;
		case "02":
			//普通http方式
			monoRst =webClientBuilder.build().post().uri(schedConfig.getTargerService()).retrieve().bodyToMono(String.class);//无参数
			break;
		case "03":
			//dubbo方式
			//TODO 暂时未实现
			break;
		default:

		}
		if(monoRst != null) {
		  log.info("调用服务结果为：{}", monoRst.block());
		}
		
		return monoRst != null ? monoRst.block() : "无返回值";
	}
}
