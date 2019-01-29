/**
 * <p>Description: </p>
 * <p>Title: TrainingCourse.java</p>  
 * <p>Copyright: Copyright (c) 2018</p>  
 * <p>Company: cei</p>  
 * <p>@author zhaowei</p>
 * <p>@version 2018年5月2日 下午5:09:32</p>
 */
package org.spring.springboot.domain;

import java.util.List;

/**
 * Title: TrainingCourse  
 * Description: 
 * @author zhaowei 
 * @version 2018年5月2日 下午5:09:32
 */
public class TrainingCourse {
    	private String id; // 培训班ID
	private String name; // 培训班名称
	private String starttime;// 培训班开始时间
	private String endtime;// 培训班结束时间
	private String credit; // 培训班总学分(通过培训可获得学分)
	private String state; // 培训班是否已经学完，0已学完，1未学完
	private String schedule;// 培训班学习总进度
	private String pic; // 培训班图片地址
	private String info;// 培训班简介
	private String studystate;//0可报名，1已报名未学完，2已通过，3未通过且已截止
	private String isjoined;//(是否已加入该培训班1已加入，0未加入)
	private String enrollment;//選班人數
	private List<Course> albums;
	/**
	 * @return the id
	 */
	public String getId() {
	    return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
	    this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
	    return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
	    this.name = name;
	}
	/**
	 * @return the starttime
	 */
	public String getStarttime() {
	    return starttime;
	}
	/**
	 * @param starttime the starttime to set
	 */
	public void setStarttime(String starttime) {
	    this.starttime = starttime;
	}
	/**
	 * @return the endtime
	 */
	public String getEndtime() {
	    return endtime;
	}
	/**
	 * @param endtime the endtime to set
	 */
	public void setEndtime(String endtime) {
	    this.endtime = endtime;
	}
	/**
	 * @return the credit
	 */
	public String getCredit() {
	    return credit;
	}
	/**
	 * @param credit the credit to set
	 */
	public void setCredit(String credit) {
	    this.credit = credit;
	}
	/**
	 * @return the state
	 */
	public String getState() {
	    return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
	    this.state = state;
	}
	/**
	 * @return the schedule
	 */
	public String getSchedule() {
	    return schedule;
	}
	/**
	 * @param schedule the schedule to set
	 */
	public void setSchedule(String schedule) {
	    this.schedule = schedule;
	}
	/**
	 * @return the pic
	 */
	public String getPic() {
	    return pic;
	}
	/**
	 * @param pic the pic to set
	 */
	public void setPic(String pic) {
	    this.pic = pic;
	}
	/**
	 * @return the info
	 */
	public String getInfo() {
	    return info;
	}
	/**
	 * @param info the info to set
	 */
	public void setInfo(String info) {
	    this.info = info;
	}
	/**
	 * @return the studystate
	 */
	public String getStudystate() {
	    return studystate;
	}
	/**
	 * @param studystate the studystate to set
	 */
	public void setStudystate(String studystate) {
	    this.studystate = studystate;
	}
	/**
	 * @return the isjoined
	 */
	public String getIsjoined() {
	    return isjoined;
	}
	/**
	 * @param isjoined the isjoined to set
	 */
	public void setIsjoined(String isjoined) {
	    this.isjoined = isjoined;
	}
	/**
	 * @return the albums
	 */
	public List<Course> getAlbums() {
	    return albums;
	}
	/**
	 * @param albums the albums to set
	 */
	public void setAlbums(List<Course> albums) {
	    this.albums = albums;
	}
	/**
	 * @return the enrollment
	 */
	public String getEnrollment() {
	    return enrollment;
	}
	/**
	 * @param enrollment the enrollment to set
	 */
	public void setEnrollment(String enrollment) {
	    this.enrollment = enrollment;
	}
	
}
