/**
 * <p>Description: </p>
 * <p>Title: CourseTotalInfo.java</p>  
 * <p>Copyright: Copyright (c) 2018</p>  
 * <p>Company: cei</p>  
 * <p>@author zhaowei</p>
 * <p>@version 2018年4月30日 上午10:45:30</p>
 */
package org.spring.springboot.domain;

import java.util.List;

/**
 * Title: CourseTotalInfo Description:
 * 
 * @author zhaowei
 * @version 2018年4月30日 上午10:45:30
 */
public class CourseTotalInfo {
    private String code; // 接口返回状态
    private List<Course> contentlist;

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
    public List<Course> getContentlist() {
        return contentlist;
    }

    /**
     * @param contentlist the contentlist to set
     */
    public void setContentlist(List<Course> contentlist) {
        this.contentlist = contentlist;
    }
    
}
