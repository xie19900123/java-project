package cn.lqdev.java.scheduler.common.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/** 
*
* @ClassName   类名：CommonConfig 
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
@Configuration
public class WebClientConfig {

	/**
	 * 
	 * <p>函数名称：  loadBalancedWebClientBuilder     </p>
	 * <p>功能说明：  具有负载均衡的WebClient
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2019年3月5日
	 * @author 作者：oKong
	 */
	@Bean("balanceWebClient")
	@LoadBalanced
	public WebClient.Builder loadBalancedWebClientBuilder() {
		return WebClient.builder();
	}
	
	/**
	 * 
	 * <p>函数名称： webClientBuilder       </p>
	 * <p>功能说明：普通WebClient
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2019年3月5日
	 * @author 作者：oKong
	 */
	@Bean("webClient")
	public WebClient.Builder webClientBuilder() {
		return WebClient.builder();
	}
}
