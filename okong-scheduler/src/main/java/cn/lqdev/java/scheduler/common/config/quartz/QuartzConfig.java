package cn.lqdev.java.scheduler.common.config.quartz;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/** 
*
* @ClassName   类名：QuartzConfig 
* @Description 功能说明：quartz配置，主要设置SchedulerFactoryBean
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2019年3月5日
* @author      创建人：oKong
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2019年3月5日   oKong   创建该类功能。
*
***********************************************************************
*</p>
*/
@Configuration
public class QuartzConfig {
	
	@Bean
    public SchedulerFactoryBean schedulerFactoryBean(){
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setOverwriteExistingJobs(true);
        // 延时启动
        factory.setStartupDelay(20);
        // 自定义Job Factory，用于Spring注入
        factory.setJobFactory(quartzJobFactory());
        return factory;
    }
	
	@Bean
	public QuartzJobFactory quartzJobFactory() {
		return new QuartzJobFactory();
	}
}
