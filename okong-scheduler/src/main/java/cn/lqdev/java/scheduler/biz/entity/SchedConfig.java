package cn.lqdev.java.scheduler.biz.entity;

import java.util.Date;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author oKong
 * @since 2019-03-04
 */
@Data
@Accessors(chain = true)
public class SchedConfig extends Model<SchedConfig> {

    private static final long serialVersionUID = 1L;

    @TableId
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
    private String targetServiceType;
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
    private Integer status;
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


    public static final String ID = "id";
    
    public static final String CODE = "code";

    public static final String NAME = "name";

    public static final String TARGET_SERVICE_TYPE = "target_service_type";

    public static final String TARGER_SERVICE = "targer_service";

    public static final String CRON_CONFIG = "cron_config";

    public static final String STATUS = "status";

    public static final String REMARK = "remark";

    public static final String EXTRA_DUBBO_GROUP = "extra_dubbo_group";

    public static final String EXTRA_DUBBO_VERSION = "extra_dubbo_version";

    public static final String GMT_CREATE = "gmt_create";

    public static final String GMT_MODIFIED = "gmt_modified";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
