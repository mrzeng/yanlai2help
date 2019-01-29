/**
 * <p>Description: </p>
 * <p>Title: OperateTypeEnum.java</p>  
 * <p>Copyright: Copyright (c) 2018</p>  
 * <p>Company: cei</p>  
 * <p>@author zhaowei</p>
 * <p>@version 2018年5月9日 上午10:25:49</p>
 */
package org.spring.springboot.domain;

/**
 * Title: OperateTypeEnum  
 * Description: 
 * @author zhaowei 
 * @version 2018年5月9日 上午10:25:49
 */
public enum OperateTypeEnum {
	ADD("增加"), DELETE("删除"), UPDATE("修改"), QUERY("查询"), LOGININ("登录"), LOGINOUT(
			"登出"), AUDIT("审核"), STUDY("学习"), ENABLE("启用"), UNABLE("禁用"), IMPORT(
			"导入"), EXPORT("导出"), PREVIEW("预览"), COMPRESS("压缩"), EXMA("考试"), STUDYCOURSE(
			"学习课程"), CHOOSECOURSE("选课"), COLLECTCOURSE("收藏课程"), COMMENTCOURSE(
			"评价课程"), STUDYTCOURSE("学习培训班"), CHOOSETCOURSE("申请加入培训班");
	// 成员变量
	private String value;

	// 构造方法
	private OperateTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	// 覆盖方法--toString
	@Override
	public String toString() {
		return this.name() + "---" + this.value;
	}
}
