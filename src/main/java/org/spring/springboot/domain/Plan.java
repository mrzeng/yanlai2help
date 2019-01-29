/**
 * <p>Description: </p>
 * <p>Title: Plan.java</p>  
 * <p>Copyright: Copyright (c) 2018</p>  
 * <p>Company: cei</p>  
 * <p>@author zhaowei</p>
 * <p>@version 2018年5月25日 下午5:33:07</p>
 */
package org.spring.springboot.domain;

/**
 * Title: Plan Description:
 * 
 * @author zhaowei
 * @version 2018年5月25日 下午5:33:07
 */
public class Plan {
    private static final long serialVersionUID = 1L;

    private String id;
    private String planid;
    private String planname;
    private String begintime;
    private String endtime;

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getPlanid() {
	return planid;
    }

    public void setPlanid(String planid) {
	this.planid = planid;
    }

    public String getPlanname() {
	return planname;
    }

    public void setPlanname(String planname) {
	this.planname = planname;
    }

    public String getBegintime() {
	return begintime;
    }

    public void setBegintime(String begintime) {
	this.begintime = begintime;
    }

    public String getEndtime() {
	return endtime;
    }

    public void setEndtime(String endtime) {
	this.endtime = endtime;
    }
}
