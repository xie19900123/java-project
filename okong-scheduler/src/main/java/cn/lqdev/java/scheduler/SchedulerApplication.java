package cn.lqdev.java.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @ClassName 类名：SchedulerApplication
 * @Description 功能说明：统一调度中心启动类
 *              <p>
 *              TODO
 *              </p>
 ************************************************************************
 * @date 创建日期：2019年3月4日
 * @author 创建人：oKong
 * @version 版本号：V1.0
 *          <p>
 ***************************          修订记录*************************************
 * 
 *          2019年3月4日 oKong 创建该类功能。
 *
 ***********************************************************************
 *          </p>
 */
@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class SchedulerApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SchedulerApplication.class, args);
		log.info("okong-scheduler统一调度中心服务启动!");
	}

}
