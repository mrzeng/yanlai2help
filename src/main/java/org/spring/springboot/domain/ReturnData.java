/**
 * <p>Description: </p>
 * <p>Title: ReturnData.java</p>  
 * <p>Copyright: Copyright (c) 2018</p>  
 * <p>Company: cei</p>  
 * <p>@author zhaowei</p>
 * <p>@version 2018年4月30日 上午8:00:07</p>
 */
package org.spring.springboot.domain;

import java.util.List;

/**data封装类
 * Title: ReturnData  
 * Description: 
 * @author zhaowei 
 * @version 2018年4月30日 上午8:00:07
 */
public class ReturnData {
    private String code;
    private List<PageData> contentlist; //接口返回数据

    
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
    public List<PageData> getContentlist() {
        return contentlist;
    }

    /**
     * @param contentlist the contentlist to set
     */
    public void setContentlist(List<PageData> contentlist) {
        this.contentlist = contentlist;
    }
    
}
