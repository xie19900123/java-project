package cn.lqdev.java.scheduler.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.reactive.function.client.WebClient;

import cn.lqdev.java.scheduler.biz.entity.SchedConfig;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/** 
*
* @ClassName   类名：TaskJob 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2019年3月4日
* @author      创建人：oKong
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2019年3月4日   oKong   创建该类功能。
*
***********************************************************************
*</p>
*/
//@DisallowConcurrentExecution 说明在一个任务执行时，另一个定时点来临时不会执行任务，比如一个定时是间隔3分钟一次，但任务执行了5分钟，此时会等上个任务完成后再执行下一次定时任务
@DisallowConcurrentExecution
@Slf4j
public class TaskJob implements org.quartz.Job{
	
	/**
	 * spring5中 异步restTemplate已被标记位作废了
	 * 这里尝试使用webClient
	 */ 
	@Autowired
	@Qualifier("balanceWebClient")
	private WebClient.Builder balanceWebClientBuilder;
	
	@Autowired
	@Qualifier("webClient")
	private WebClient.Builder webClientBuilder;
	
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		//执行方法
		//获取任务实体对象
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		SchedConfig schedConfig = (SchedConfig) jobDataMap.get("config");
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
	}

}
