package cn.lqdev.java.scheduler.job;

import java.util.List;

import javax.annotation.PostConstruct;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

import cn.lqdev.java.scheduler.biz.entity.SchedConfig;
import cn.lqdev.java.scheduler.biz.service.ISchedConfigService;
import lombok.extern.slf4j.Slf4j;


import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/** 
*
* @ClassName   类名：InitJob 
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
@Component
@Slf4j
public class InitJob {

	@Autowired
	ISchedConfigService schedConfigService;
	
    @Autowired
	Scheduler scheduler;
	
	/**
	 * 
	 * <p>函数名称：  initJob      </p>
	 * <p>功能说明： 启动时，进行任务初始化操作，即启动相应的任务定时器
	 *
	 * </p>
	 *<p>参数说明：</p>
	 *
	 * @date   创建时间：2019年3月4日
	 * @author 作者：oKong
	 */
	@PostConstruct
	public void initJob() {
		log.info("初始化任务开始......");
		//获取所有启用任务
		EntityWrapper<SchedConfig> qryWrapper = new EntityWrapper<>();
		qryWrapper.eq(SchedConfig.STATUS, "1");
		List<SchedConfig> schedConfigList = schedConfigService.selectList(qryWrapper);
		if(schedConfigList == null || schedConfigList.isEmpty()) {
			log.warn("暂无定时任务");
			return;
		}
		for(SchedConfig config : schedConfigList) {
			String code = config.getCode();//任务编码
			JobDetail jobDetail = newJob(TaskJob.class).withIdentity(code, "okongJobGroup").build();
			//设置运行时参数
			JobDataMap jobDataMap = jobDetail.getJobDataMap();
			jobDataMap.put("config", config);
			//创建trigger触发器
			Trigger trigger = newTrigger()
					.withIdentity(code, "okongTriggerGroup")
					.withSchedule(cronSchedule(config.getCronConfig())).build();
			
			//启动定时器
			try {
				scheduler.scheduleJob(jobDetail, trigger);
				log.info("任务[{}]启动成功", code);
			} catch (SchedulerException e) {
				log.error("任务[{}]启动失败,{}", code, e.getMessage());
			}
		}
		log.info("初始化任务结束......");
	}
}
