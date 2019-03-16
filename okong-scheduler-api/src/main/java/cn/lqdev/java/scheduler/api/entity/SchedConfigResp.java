package cn.lqdev.java.scheduler.api.entity;

import java.util.Date;

import cn.lqdev.java.scheduler.api.enums.Status;
import cn.lqdev.java.scheduler.api.enums.TargetServiceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 
*
* @ClassName   类名：SchedConfigResp 
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
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchedConfigResp {
	
	private Long id;
	
	/**
	 * 任务编码，区分任务是否一直
	 */
	private String code;
	
    /**
     * 任务名称
     */
    private String name;
    /**
     * 目标任务类型：01 springcloud 02 http 03 dubbo
     */
    private TargetServiceType targetServiceType;
    /**
     * 目标服务：可为服务地址，或者dubbo服务名
     */
    private String targerService;
    /**
     * cron表达式
     */
    private String cronConfig;
    /**
     * 状态：1启用 0 停用
     */
    private Status status;
    /**
     * 备注说明
     */
    private String remark;
    /**
     * dubbo组名
     */
    private String extraDubboGroup;
    /**
     * dubbo服务版本信息
     */
    private String extraDubboVersion;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 修改时间
     */
    private Date gmtModified;
}
