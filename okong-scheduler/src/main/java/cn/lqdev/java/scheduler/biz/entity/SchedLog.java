package cn.lqdev.java.scheduler.biz.entity;

import java.util.Date;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

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
public class SchedLog extends Model<SchedLog> {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 任务id
     */
    private Long schedId;
    /**
     * 任务名称
     */
    private String schedName;
    /**
     * 开始执行时间
     */
    private String startTime;
    /**
     * 执行结束时间
     */
    private String endTime;
    /**
     * 执行结果说明
     */
    private String result;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 修改时间
     */
    private Date gmtModified;


    public static final String ID = "id";

    public static final String SCHED_ID = "sched_id";

    public static final String SCHED_NAME = "sched_name";

    public static final String START_TIME = "start_time";

    public static final String END_TIME = "end_time";

    public static final String RESULT = "result";

    public static final String GMT_CREATE = "gmt_create";

    public static final String GMT_MODIFIED = "gmt_modified";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
