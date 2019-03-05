package cn.lqdev.java.scheduler.common.config;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;

/** 
*
* @ClassName   类名：MybatisObjectHandler 
* @Description 功能说明：公用字段自动填充 比如 更新时间 创建时间等
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
public class MybatisObjectHandler extends MetaObjectHandler{

	@Override
	public void insertFill(MetaObject metaObject) {
		 //新增时需要填充字段
		setFieldValByName("gmtcreate", new Date(), metaObject);
		setFieldValByName("gmtModified", new Date(), metaObject);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		setFieldValByName("gmtModified", new Date(), metaObject);
	}

}
