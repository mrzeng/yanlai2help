/**
 * <p>Description: </p>
 * <p>Title: ModuleTypeEnum.java</p>  
 * <p>Copyright: Copyright (c) 2018</p>  
 * <p>Company: cei</p>  
 * <p>@author zhaowei</p>
 * <p>@version 2018年5月9日 上午10:26:17</p>
 */
package org.spring.springboot.domain;

/**
 * Title: ModuleTypeEnum  
 * Description: 
 * @author zhaowei 
 * @version 2018年5月9日 上午10:26:17
 */
public enum ModuleTypeEnum {
	LOG_MNG("日志管理"), VERSION_MNG("版本管理"), BACKUP_MNG("回收站管理"), MENU_MNG("菜单管理"), PORTAL_MNG(
			"门户管理"), RESOURCE_MNG("资源管理"), USER_MNG("学员管理"), COURSE_MNG("课程管理"), COURSECW_MNG(
			"课件管理"), TCOURSE_MNG("培训班管理"), EXAM_MNG("考试管理"), ARCHIVE_MNG("档案管理"), STUDYPLAN_MNG(
			"培训计划管理"), COMMENT_MNG("评论管理"), SYSTEM_MNG("系统管理"), TOOLS_MNG(
			"系统工具"), STUDYGROUP_MNG("学习组工具"), CERTIFICATE_MNG("证书管理"), INDEX_MNG(
			"首页"), WEB_STUDY("学员学习"), PROFESSION_MNG("职级管理"), ORG_MNG("机构管理"), SPECIAL_SUBJECT_MNG(
			"专题管理"), FEEDBACK_MNG("需求反馈管理"), NATION_MNG("民族信息维护");
	// 成员变量
	private String value;

	// 构造方法
	private ModuleTypeEnum(String value) {
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
