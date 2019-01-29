/**
 * <p>Description: </p>
 * <p>Title: ReturnMutillevelData.java</p>  
 * <p>Copyright: Copyright (c) 2018</p>  
 * <p>Company: cei</p>  
 * <p>@author zhaowei</p>
 * <p>@version 2018年5月28日 上午7:40:54</p>
 */
package org.spring.springboot.domain;

import java.util.List;

/**
 * Title: ReturnMutillevelData  
 * Description: 
 * @author zhaowei 
 * @version 2018年5月28日 上午7:40:54
 */
public class ReturnMutillevelData {
    private String code;
    private List<MutillevelData> contentlist; //接口返回数据
    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }
    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }
    /**
     * @return the contentlist
     */
    public List<MutillevelData> getContentlist() {
        return contentlist;
    }
    /**
     * @param contentlist the contentlist to set
     */
    public void setContentlist(List<MutillevelData> contentlist) {
        this.contentlist = contentlist;
    }
    
}
