package cn.lqdev.java.scheduler.api;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import cn.lqdev.java.scheduler.api.entity.SchedConfigReq;
import cn.lqdev.java.scheduler.api.entity.SchedConfigResp;

/** 
*
* @ClassName   类名：ISchedConfigApi 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2019年3月9日
* @author      创建人：oKong
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2019年3月9日   oKong   创建该类功能。
*
***********************************************************************
*</p>
*/
public interface ISchedConfigApi {
	
	/**
	 * 
	 * <p>函数名称：   executeScheduler     </p>
	 * <p>功能说明：  执行某编码任务
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param configCode
	 * @return
	 *
	 * @date   创建时间：2019年3月16日
	 * @author 作者：oKong
	 */
	@PostMapping("sched/execute/bycode")
	public String executeScheduler(@RequestParam("configCode")String configCode);
	
	/**
	 * 
	 * <p>函数名称： addScheduler       </p>
	 * <p>功能说明： 新增定时配置
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param configReq
	 * @return
	 *
	 * @date   创建时间：2019年3月16日
	 * @author 作者：oKong
	 */
	@PostMapping("sched")	
	public Boolean addScheduler(@RequestBody SchedConfigReq configReq);
	
	/**
	 * 
	 * <p>函数名称：  delScheduler      </p>
	 * <p>功能说明： 删除定时配置，同时停止任务
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param configCode
	 * @return
	 *
	 * @date   创建时间：2019年3月16日
	 * @author 作者：oKong
	 */
	@DeleteMapping("sched")
	public Boolean delScheduler(@RequestParam("configCode") String configCode);
	
	/**
	 * 
	 * <p>函数名称：  queryByCode      </p>
	 * <p>功能说明： 根据编码查询定时配置
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param configCode
	 * @return
	 *
	 * @date   创建时间：2019年3月16日
	 * @author 作者：oKong
	 */
	@GetMapping("sched/query/bycode")
	public SchedConfigResp queryByCode(@RequestParam("configCode") String configCode);
	
	/**
	 * 
	 * <p>函数名称：  queryAll      </p>
	 * <p>功能说明： 查询所有定时配置
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2019年3月16日
	 * @author 作者：oKong
	 */
	public List<SchedConfigResp> queryAll();
	
	/**
	 * 
	 * <p>函数名称：updScheduler        </p>
	 * <p>功能说明：更新配置
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param configReq
	 * @return
	 *
	 * @date   创建时间：2019年3月16日
	 * @author 作者：oKong
	 */
	@PutMapping("sched")
	public Boolean updScheduler(@RequestBody SchedConfigReq configReq);
	
	/**
	 * 
	 * <p>函数名称： stopScheduluer       </p>
	 * <p>功能说明：停止某编码任务，同时将配置设置为停用
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param configCode
	 * @return
	 *
	 * @date   创建时间：2019年3月16日
	 * @author 作者：oKong
	 */
	@PostMapping("sched/stop/bycode")
	public Boolean stopScheduler(@RequestParam("configCode") String configCode);
	
	/**
	 * 
	 * <p>函数名称：  startScheduler      </p>
	 * <p>功能说明： 启动某编码任务
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param configCode
	 * @return
	 *
	 * @date   创建时间：2019年3月16日
	 * @author 作者：oKong
	 */
	@PostMapping("sched/start/bycode")	
	public Boolean startScheduler(@RequestParam("configCode") String configCode);
	
	/**
	 * 
	 * <p>函数名称： pauseScheduler       </p>
	 * <p>功能说明：根据编码暂停某任务
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param configCode
	 * @return
	 *
	 * @date   创建时间：2019年3月16日
	 * @author 作者：oKong
	 */
	@PostMapping("sched/pause/bycode")
	public Boolean pauseScheduler(@RequestParam("configCode") String configCode);
	
	/**
	 * 
	 * <p>函数名称：   pauseAllScheduler     </p>
	 * <p>功能说明： 暂停所有任务
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2019年3月16日
	 * @author 作者：oKong
	 */
	@PostMapping("sched/pause")
	public Boolean pauseAllScheduler();
	
	/**
	 * 
	 * <p>函数名称：resetScheduler        </p>
	 * <p>功能说明：重置任务
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2019年3月16日
	 * @author 作者：oKong
	 */
	@PostMapping("sched/reset")	
	public Boolean resetScheduler();

}
