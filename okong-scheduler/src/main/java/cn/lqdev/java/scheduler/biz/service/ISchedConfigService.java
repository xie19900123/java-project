package cn.lqdev.java.scheduler.biz.service;

import cn.lqdev.java.scheduler.biz.entity.SchedConfig;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author oKong
 * @since 2019-03-04
 */
public interface ISchedConfigService extends IService<SchedConfig> {
	
	public SchedConfig queryByCode(String code);
	
	public boolean delByCode(String code);

}
