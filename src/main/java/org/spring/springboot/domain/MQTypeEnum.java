/**
 * <p>Description: </p>
 * <p>Title: MQTypeEnum.java</p>  
 * <p>Copyright: Copyright (c) 2018</p>  
 * <p>Company: cei</p>  
 * <p>@author zhaowei</p>
 * <p>@version 2018年5月22日 下午3:03:01</p>
 */
package org.spring.springboot.domain;

/**
 * Title: MQTypeEnum Description:
 * 
 * @author zhaowei
 * @version 2018年5月22日 下午3:03:01
 */
public enum MQTypeEnum {
    EXMA("考试");
    // 成员变量
    private String value;

    // 构造方法
    private MQTypeEnum(String value) {
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
