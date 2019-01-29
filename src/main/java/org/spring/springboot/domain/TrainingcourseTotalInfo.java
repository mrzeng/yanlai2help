/**
 * <p>Description: </p>
 * <p>Title: TrainingcourseTotalInfo.java</p>  
 * <p>Copyright: Copyright (c) 2018</p>  
 * <p>Company: cei</p>  
 * <p>@author zhaowei</p>
 * <p>@version 2018年5月2日 下午5:09:14</p>
 */
package org.spring.springboot.domain;

import java.util.List;

/**
 * Title: TrainingcourseTotalInfo  
 * Description: 
 * @author zhaowei 
 * @version 2018年5月2日 下午5:09:14
 */
public class TrainingcourseTotalInfo {
    private String code;	//接口返回状态
    private List<TrainingCourse> contentlist;
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
    public List<TrainingCourse> getContentlist() {
        return contentlist;
    }
    /**
     * @param contentlist the contentlist to set
     */
    public void setContentlist(List<TrainingCourse> contentlist) {
        this.contentlist = contentlist;
    }
    
}
