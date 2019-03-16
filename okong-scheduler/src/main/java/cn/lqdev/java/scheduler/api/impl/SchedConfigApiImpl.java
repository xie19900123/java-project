package cn.lqdev.java.scheduler.api.impl;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.ArrayList;
import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

import cn.lqdev.java.scheduler.api.ISchedConfigApi;
import cn.lqdev.java.scheduler.api.entity.SchedConfigReq;
import cn.lqdev.java.scheduler.api.entity.SchedConfigResp;
import cn.lqdev.java.scheduler.api.enums.Status;
import cn.lqdev.java.scheduler.api.enums.TargetServiceType;
import cn.lqdev.java.scheduler.biz.entity.SchedConfig;
import cn.lqdev.java.scheduler.biz.service.ISchedConfigService;
import cn.lqdev.java.scheduler.job.JobService;
import cn.lqdev.java.scheduler.job.TaskJob;
import lombok.extern.slf4j.Slf4j;

/** 
*
* @ClassName   类名：SchedConfigApiImpl 
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
@RestController
@Slf4j
public class SchedConfigApiImpl implements ISchedConfigApi{

	@Autowired
	ISchedConfigService configService;
	
	@Autowired
	Scheduler scheduler;	
	
	@Autowired
	JobService jobService;
	
	@Override
	public String executeScheduler(@RequestParam("configCode")String configCode) {
		SchedConfig schedConfig = configService.queryByCode(configCode);
		if(schedConfig == null) {
			throw new RuntimeException("编码" + configCode + "不存在!");
		}		
		return jobService.execJob(schedConfig);
	}

	@Override
	public Boolean addScheduler(SchedConfigReq configReq) {
		log.info("新增定时配置：{}", configReq);
		if(configService.queryByCode(configReq.getCode()) != null) {
			throw new RuntimeException("编码" + configReq.getCode() + "已经存在!");
		}
		return configService.insert(toConfig(configReq));
	}

	@Override
	public Boolean delScheduler(@RequestParam("configCode")String configCode) {	
		log.info("删除编码：{}", configCode);
		return configService.delByCode(configCode);
	}

	@Override
	public SchedConfigResp queryByCode(@RequestParam("configCode")String configCode) {		
		return fromConfig(configService.queryByCode(configCode));
	}

	@Override
	public List<SchedConfigResp> queryAll() {
		EntityWrapper<SchedConfig> qryWrapper = new EntityWrapper<>();
		List<SchedConfig> list = configService.selectList(qryWrapper);
		if(list == null || list.isEmpty()) {
			return new ArrayList<>();
		} 
		List<SchedConfigResp> resultList = new ArrayList<>();
		for(SchedConfig config : list) {
			resultList.add(fromConfig(config));
		}
		return resultList;
	}

	@Override
	public Boolean updScheduler(@RequestBody SchedConfigReq configReq) {
		if(configReq.getId() == null) {
			throw new RuntimeException("配置id不能为空!");
		}
		//判断编码是否存在 需要排除自身
		SchedConfig config = configService.queryByCode(configReq.getCode());
		if(config != null && !config.getId().equals(configReq.getId())) {
			throw new RuntimeException("编码" + configReq.getCode() + "已经存在!");
		}
		//更新
		return configService.updateById(toConfig(configReq));
	}

	@Override
	public Boolean stopScheduler(@RequestParam("configCode")String configCode) {
		log.info("停止任务：configCode={}", configCode);
		SchedConfig config = new SchedConfig();
		config.setCode(configCode);
		config.setStatus(Status.DISABLE.ordinal());
		EntityWrapper<SchedConfig> qryWrapper = new EntityWrapper<>();
		qryWrapper.eq(SchedConfig.CODE, configCode);
		configService.update(config,qryWrapper);
		
		//停止任务
	    removeJob(scheduler, config.getCode());
	    
	    return Boolean.TRUE;
	}

	@Override
	public Boolean startScheduler(@RequestParam("configCode")String configCode) {
		log.info("开始任务：configCode={}", configCode);
		SchedConfig config = configService.queryByCode(configCode);
		if(config == null) {
			throw new RuntimeException("编码" + configCode + "不存在!");
		} else if(Status.DISABLE.ordinal() == (config.getStatus().intValue())) {
			//更新为启用
			config.setStatus(Status.ENABLE.ordinal());
			configService.updateById(config);
		}
		//先移除任务在启动任务
		removeJob(scheduler, config.getCode());
		startJob(scheduler, config);
	    return Boolean.TRUE;
	}

	@Override
	public Boolean pauseScheduler(@RequestParam("configCode")String configCode) {
		log.info("暂停任务：{}", configCode);
        TriggerKey triggerKey = new TriggerKey(configCode,"okongTriggerGroup");
        try {
        	//暂停触发器
			scheduler.pauseTrigger(triggerKey);
		} catch (SchedulerException e) {
			log.error("移除任务异常，SchedulerException:{}", e);
			throw new RuntimeException("暂停任务发生异常：" +  e.getMessage());
		}
        
        return Boolean.TRUE;
	}

	@Override
	public Boolean pauseAllScheduler() {
		log.info("暂停所有任务");
		try {
			scheduler.pauseAll();
		} catch (SchedulerException e) {
			log.error("暂停任务异常，SchedulerException:{}", e);
			throw new RuntimeException("暂停所有任务发生异常：" +  e.getMessage());
		}

	    return Boolean.TRUE;
		
	}

	@Override
	public Boolean resetScheduler() {
		log.info("重置所有已启动任务!");
		//获取所有启用任务
		EntityWrapper<SchedConfig> qryWrapper = new EntityWrapper<>();
		qryWrapper.eq(SchedConfig.STATUS, "1");
		List<SchedConfig> schedConfigList = configService.selectList(qryWrapper);
		if (schedConfigList == null || schedConfigList.isEmpty()) {
			log.warn("暂无定时任务");
			return Boolean.TRUE;
		}
		for (SchedConfig config : schedConfigList) {
			String code = config.getCode();// 任务编码
			JobDetail jobDetail = newJob(TaskJob.class).withIdentity(code, "okongJobGroup").build();
			// 设置运行时参数
			JobDataMap jobDataMap = jobDetail.getJobDataMap();
			jobDataMap.put("config", config);
			// 创建trigger触发器
			Trigger trigger = newTrigger().withIdentity(code, "okongTriggerGroup")
					.withSchedule(cronSchedule(config.getCronConfig())).build();
			// 启动定时器
			try {
				scheduler.scheduleJob(jobDetail, trigger);
				log.info("任务[{}]重置成功", code);
			} catch (SchedulerException e) {
				log.error("任务[{}]重置启动失败,{}", code, e.getMessage());
				throw new RuntimeException("重置任务[" + code + "]发生异常：" +  e.getMessage());
			}
		}
		return Boolean.TRUE;
	}
	
	private SchedConfig toConfig(SchedConfigReq configReq) {
		SchedConfig config = new SchedConfig();
		config.setCode(configReq.getCode());
		config.setName(configReq.getName());
		config.setTargetServiceType(configReq.getTargetServiceType().name());
		config.setTargerService(configReq.getTargerService());
		config.setCronConfig(configReq.getCronConfig());
		config.setStatus(configReq.getStatus().ordinal());
		config.setRemark(configReq.getRemark());
		config.setExtraDubboGroup(configReq.getExtraDubboGroup());
		config.setExtraDubboVersion(configReq.getExtraDubboVersion());
		return config;
	}
	
	private SchedConfigResp fromConfig(SchedConfig config) {
		SchedConfigResp resp = SchedConfigResp.builder()
				.id(config.getId())
				.code(config.getCode())
				.name(config.getName())
				.targetServiceType(TargetServiceType.valueOf(config.getTargetServiceType()))
				.targerService(config.getTargerService())
				.cronConfig(config.getCronConfig())
				.status(Status.valueOf(config.getStatus() + ""))//TODO 可以创建个code 和name 以示区分 这里就写了
				.remark(config.getRemark())
				.extraDubboGroup(config.getExtraDubboGroup())
				.extraDubboVersion(config.getExtraDubboVersion())
				.build();
		
		return resp;
	}
	
	/**
     * 
     * <p>函数名称： removeJob       </p>
     * <p>功能说明： 移除任务
     *
     * </p>
     *<p>参数说明：</p>
     * @param scheduler
     * @param jobName
     * @throws SchedulerException
     *
     * @date   创建时间：2019年3月8日
     * @author 作者：xds
     */
    private void removeJob(Scheduler scheduler ,String jobName){
        TriggerKey triggerKey = new TriggerKey(jobName,"okongTriggerGroup");
        JobKey jobKey = new JobKey(jobName, "okongJobGroup");
        
        try {
        	//暂停触发器
			scheduler.pauseTrigger(triggerKey);
			//删除触发器
	        scheduler.unscheduleJob(triggerKey);
	        //删除任务
	        scheduler.deleteJob(jobKey);
		} catch (SchedulerException e) {
			log.error("移除任务异常，SchedulerException:{}", e);
			throw new RuntimeException("移除任务发生异常：" +  e.getMessage());
		}        
    }	
    
    private void startJob(Scheduler scheduler ,SchedConfig config) {
    	String code = config.getCode();//任务名称
		JobDetail jobDetail = JobBuilder.newJob(TaskJob.class).withIdentity(code, "okongJobGroup").build();
		//设置运行时参数
		JobDataMap jobDataMap = jobDetail.getJobDataMap();
		jobDataMap.put("config", config);
		//创建trigger触发器
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity(code, "okongTriggerGroup")
				.withSchedule(CronScheduleBuilder.cronSchedule(config.getCronConfig())).build();
		
		//启动定时器
		try {
			scheduler.scheduleJob(jobDetail, trigger);
			log.info("任务[{}]启动成功", code);
		} catch (SchedulerException e) {
			log.error("任务[{}]启动失败,{}", code, e.getMessage());
			throw new RuntimeException("启动任务发生异常：" +  e.getMessage());	
		}
    }	

}
