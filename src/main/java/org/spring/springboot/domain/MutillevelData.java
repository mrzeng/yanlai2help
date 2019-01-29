/**
 * <p>Description: </p>
 * <p>Title: MutillevelData.java</p>  
 * <p>Copyright: Copyright (c) 2018</p>  
 * <p>Company: cei</p>  
 * <p>@author zhaowei</p>
 * <p>@version 2018年5月28日 上午7:41:47</p>
 */
package org.spring.springboot.domain;

import java.util.List;

/**
 * Title: MutillevelData  
 * Description: 
 * @author zhaowei 
 * @version 2018年5月28日 上午7:41:47
 */
public class MutillevelData {
    private List<PageData> firsttypelist; //接口返回数据
    private List<PageData> secondtypelist; //接口返回数据
    private List<PageData> thirdtypelist; //接口返回数据
    /**
     * @return the firsttypelist
     */
    public List<PageData> getFirsttypelist() {
        return firsttypelist;
    }
    /**
     * @param firsttypelist the firsttypelist to set
     */
    public void setFirsttypelist(List<PageData> firsttypelist) {
        this.firsttypelist = firsttypelist;
    }
    /**
     * @return the secondtypelist
     */
    public List<PageData> getSecondtypelist() {
        return secondtypelist;
    }
    /**
     * @param secondtypelist the secondtypelist to set
     */
    public void setSecondtypelist(List<PageData> secondtypelist) {
        this.secondtypelist = secondtypelist;
    }
    /**
     * @return the thirdtypelist
     */
    public List<PageData> getThirdtypelist() {
        return thirdtypelist;
    }
    /**
     * @param thirdtypelist the thirdtypelist to set
     */
    public void setThirdtypelist(List<PageData> thirdtypelist) {
        this.thirdtypelist = thirdtypelist;
    }
    
}
