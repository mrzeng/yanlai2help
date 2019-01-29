/**
 * <p>Description: </p>
 * <p>Title: RecommendResourceTypeEnum.java</p>  
 * <p>Copyright: Copyright (c) 2018</p>  
 * <p>Company: cei</p>  
 * <p>@author zhaowei</p>
 * <p>@version 2018年5月14日 下午6:39:28</p>
 */
package org.spring.springboot.domain;

/**
 * Title: RecommendResourceTypeEnum  
 * Description: 
 * @author zhaowei 
 * @version 2018年5月14日 下午6:39:28
 */
public enum RecommendResourceTypeEnum {
    COURSE("课程管理"), SPECIAL_SUBJECT("专题管理"), TRAINING_COURSE("培训班管理");
	// 成员变量
	private String value;

	// 构造方法
	private RecommendResourceTypeEnum(String value) {
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
