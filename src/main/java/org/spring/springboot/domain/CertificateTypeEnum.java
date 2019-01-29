/**
 * <p>Description: </p>
 * <p>Title: CertificateTypeEnum.java</p>  
 * <p>Copyright: Copyright (c) 2018</p>  
 * <p>Company: cei</p>  
 * <p>@author zhaowei</p>
 * <p>@version 2018年5月24日 下午4:35:06</p>
 */
package org.spring.springboot.domain;

/**
 * Title: CertificateTypeEnum  
 * Description: 
 * @author zhaowei 
 * @version 2018年5月24日 下午4:35:06
 */
public enum CertificateTypeEnum {
	TRAININGCOURSE("培训班");
	// 成员变量
	private String value;

	// 构造方法
	private CertificateTypeEnum(String value) {
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