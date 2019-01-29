/**
 * <p>Description: </p>
 * <p>Title: MapUtil.java</p>  
 * <p>Copyright: Copyright (c) 2018</p>  
 * <p>Company: cei</p>  
 * <p>@author zhaowei</p>
 * <p>@version 2018年5月18日 下午2:09:39</p>
 */
package org.spring.springboot.zw.util;

import java.util.HashMap;
import java.util.Map.Entry;

import org.spring.springboot.domain.PageData;

/**
 * Title: MapUtil  
 * Description: 
 * @author zhaowei 
 * @version 2018年5月18日 下午2:09:39
 */
public class MapUtil {
    
    public static PageData getLowerCaseMap(HashMap<String, String> map) {
   	PageData pd = new PageData();
   	for (Entry<String, String> entry : map.entrySet()) {
   	    String key = entry.getKey();
   	    String value = entry.getValue();
   	    if (!key.equals(key.toLowerCase())) { // 此处不判断是否包含大写，而直接全部转为小写亦可
   		String newKey = key.toLowerCase();
   		pd.put(newKey, value);
   	    } else {
   		pd.put(key, value);
   	    }
   	}
   	return pd;
       }
}
