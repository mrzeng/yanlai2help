/**
 * <p>Description: </p>
 * <p>Title: UuidUtil.java</p>  
 * <p>Copyright: Copyright (c) 2018</p>  
 * <p>Company: cei</p>  
 * <p>@author zhaowei</p>
 * <p>@version 2018年4月26日 上午11:33:33</p>
 */
package org.spring.springboot.zw.util;

import java.util.UUID;

/**
 * Title: UuidUtil  
 * Description: 
 * @author zhaowei 
 * @version 2018年4月26日 上午11:33:33
 */
public class UuidUtil {
    public static String get32UUID() {
	String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
	return uuid;
}

    public static void main(String[] args) {
    	System.out.println(get32UUID());
    }
}
