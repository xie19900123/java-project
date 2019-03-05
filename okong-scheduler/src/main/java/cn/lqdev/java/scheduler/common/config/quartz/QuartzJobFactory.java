package cn.lqdev.java.scheduler.common.config.quartz;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;

/**
 *
 * @ClassName 类名：QuartzJobFactory
 * @Description 功能说明：对执行的job字段进行自动注入
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
public class QuartzJobFactory extends AdaptableJobFactory {
	@Autowired
	private AutowireCapableBeanFactory capableBeanFactory;

	protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
		// 调用父类的方法
		Object jobInstance = super.createJobInstance(bundle);
		//主动注入
		capableBeanFactory.autowireBean(jobInstance);
		return jobInstance;
	}
}
