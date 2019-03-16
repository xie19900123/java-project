package cn.lqdev.java.scheduler.biz.service.impl;

import cn.lqdev.java.scheduler.biz.entity.SchedConfig;
import cn.lqdev.java.scheduler.biz.dao.SchedConfigDao;
import cn.lqdev.java.scheduler.biz.service.ISchedConfigService;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author oKong
 * @since 2019-03-04
 */
@Service
public class SchedConfigServiceImpl extends ServiceImpl<SchedConfigDao, SchedConfig> implements ISchedConfigService {

	@Override
	public SchedConfig queryByCode(String code) {
		EntityWrapper<SchedConfig> qryWrapper = new EntityWrapper<>();
		qryWrapper.eq(SchedConfig.CODE, code);
		return selectOne(qryWrapper);
	}

	@Override
	public boolean delByCode(String code) {
		EntityWrapper<SchedConfig> qryWrapper = new EntityWrapper<>();
		qryWrapper.eq(SchedConfig.CODE, code);
		return delete(qryWrapper);
	}

}
