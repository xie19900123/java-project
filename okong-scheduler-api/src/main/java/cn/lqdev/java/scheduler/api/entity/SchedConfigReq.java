package cn.lqdev.java.scheduler.api.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import cn.lqdev.java.scheduler.api.enums.Status;
import cn.lqdev.java.scheduler.api.enums.TargetServiceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @ClassName 类名：SchedConfigReq
 * @Description 功能说明：
 *              <p>
 *              TODO
 *              </p>
 ************************************************************************
 * @date 创建日期：2019年3月9日
 * @author 创建人：oKong
 * @version 版本号：V1.0
 *          <p>
 ***************************          修订记录*************************************
 * 
 *          2019年3月9日 oKong 创建该类功能。
 *
 ***********************************************************************
 *          </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchedConfigReq {
	
	private Long id;
	/**
	 * 任务编码，区分任务是否一直
	 */
	@NotBlank(message = "编码不能为空")
	private String code;	
	/**
	 * 任务名称
	 */
	@NotBlank(message = "名称不能为空")
	private String name;
	/**
	 * 目标任务类型：01 springcloud 02 http 03 dubbo
	 */
	@NotNull(message = "任务类型不能为空")
	private TargetServiceType targetServiceType;
	/**
	 * 目标服务：可为服务地址，或者dubbo服务名
	 */
	@NotBlank(message = "任务配置不能为空")
	private String targerService;
	/**
	 * cron表达式
	 */
	@NotBlank(message = "定时表达式不能为空")
	private String cronConfig;
	/**
	 * 状态：1启用 0 停用
	 */
	@NotNull(message = "状态不能为空")
	private Status status;
	/**
	 * 备注说明
	 */
	@Length(max = 200, message = "备注字段最大长度不能超过200")
	private String remark;
	/**
	 * dubbo组名
	 */
	private String extraDubboGroup;
	/**
	 * dubbo服务版本信息
	 */
	private String extraDubboVersion;

}
