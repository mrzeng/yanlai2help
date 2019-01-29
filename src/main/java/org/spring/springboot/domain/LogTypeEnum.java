/**
 * <p>Description: </p>
 * <p>Title: LogTypeEnum.java</p>  
 * <p>Copyright: Copyright (c) 2018</p>  
 * <p>Company: cei</p>  
 * <p>@author zhaowei</p>
 * <p>@version 2018年5月9日 上午10:26:39</p>
 */
package org.spring.springboot.domain;

/**
 * Title: LogTypeEnum Description:
 * 
 * @author zhaowei
 * @version 2018年5月9日 上午10:26:39
 */
public enum LogTypeEnum {
    BUSINESS("业务日志"), SYSTEM("系统日志"), OPERATE("操作日志"), MAINTENANCE("运维日志");
    // 成员变量
    private String value;

    // 构造方法
    private LogTypeEnum(String value) {
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
