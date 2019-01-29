/**
 * <p>Description: </p>
 * <p>Title: Courseware.java</p>  
 * <p>Copyright: Copyright (c) 2018</p>  
 * <p>Company: cei</p>  
 * <p>@author zhaowei</p>
 * <p>@version 2018年4月30日 上午10:25:46</p>
 */
package org.spring.springboot.domain;

/**
 * Title: Courseware  
 * Description: 
 * @author zhaowei 
 * @version 2018年4月30日 上午10:25:46
 */
public class Courseware {
    	private String classid;
	private String name;
	private String intro;
	private String userid;
	private String teachername;
	private String price;
	private String classkey;
	private String classlength;
	private String isfree;
	private String path;
	private Integer downsum;
	private String status;
	private String creattime;
	//集数
	private String setnum;
	private String xzclassid;
	private String hours;
	private String classificationid;
	private String xzstatus;
	//课程属性，0必修，1选修
	private String attribute;
	//选课人数
	private String enrollment;
	private String ordernum;
	private String protime;
	//下载地址
	private String downpath;
	//在线播放地址
	private String lookpath;
	//时间点
	private String timepoint;
	//播放进度
	private String schedule;
	//时长
	private String time;
	//密钥
	private String passkey;
	//视频格式
	private String format;
	//文件夹地址
	private String filepath;
	//课件图片
	private String pic;
	//课件来源
	private String sourcetype;
	//
	private String chaptertimepoint;
	public String getClassid() {
		return classid;
	}
	public void setClassid(String classid) {
		this.classid = classid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getTeachername() {
		return teachername;
	}
	public void setTeachername(String teachername) {
		this.teachername = teachername;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getClasskey() {
		return classkey;
	}
	public void setClasskey(String classkey) {
		this.classkey = classkey;
	}
	public String getClasslength() {
		return classlength;
	}
	public void setClasslength(String classlength) {
		this.classlength = classlength;
	}
	public String getIsfree() {
		return isfree;
	}
	public void setIsfree(String isfree) {
		this.isfree = isfree;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Integer getDownsum() {
		return downsum;
	}
	public void setDownsum(Integer downsum) {
		this.downsum = downsum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreattime() {
		return creattime;
	}
	public void setCreattime(String creattime) {
		this.creattime = creattime;
	}
	public String getSetnum() {
		return setnum;
	}
	public void setSetnum(String setnum) {
		this.setnum = setnum;
	}
	public String getXzclassid() {
		return xzclassid;
	}
	public void setXzclassid(String xzclassid) {
		this.xzclassid = xzclassid;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	public String getClassificationid() {
		return classificationid;
	}
	public void setClassificationid(String classificationid) {
		this.classificationid = classificationid;
	}
	public String getXzstatus() {
		return xzstatus;
	}
	public void setXzstatus(String xzstatus) {
		this.xzstatus = xzstatus;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public String getEnrollment() {
		return enrollment;
	}
	public void setEnrollment(String enrollment) {
		this.enrollment = enrollment;
	}
	public String getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}
	public String getProtime() {
		return protime;
	}
	public void setProtime(String protime) {
		this.protime = protime;
	}
	public String getDownpath() {
		return downpath;
	}
	public void setDownpath(String downpath) {
		this.downpath = downpath;
	}
	public String getLookpath() {
		return lookpath;
	}
	public void setLookpath(String lookpath) {
		this.lookpath = lookpath;
	}
	public String getTimepoint() {
		return timepoint;
	}
	public void setTimepoint(String timepoint) {
		this.timepoint = timepoint;
	}
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getPasskey() {
		return passkey;
	}
	public void setPasskey(String passkey) {
		this.passkey = passkey;
	}
	/**
	 * @return the format
	 */
	public String getFormat() {
	    return format;
	}
	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
	    this.format = format;
	}
	/**
	 * @return the filepath
	 */
	public String getFilepath() {
	    return filepath;
	}
	/**
	 * @param filepath the filepath to set
	 */
	public void setFilepath(String filepath) {
	    this.filepath = filepath;
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
	 * @return the sourcetype
	 */
	public String getSourcetype() {
	    return sourcetype;
	}
	/**
	 * @param sourcetype the sourcetype to set
	 */
	public void setSourcetype(String sourcetype) {
	    this.sourcetype = sourcetype;
	}
	/**
	 * @return the chaptertimepoint
	 */
	public String getChaptertimepoint() {
	    return chaptertimepoint;
	}
	/**
	 * @param chaptertimepoint the chaptertimepoint to set
	 */
	public void setChaptertimepoint(String chaptertimepoint) {
	    this.chaptertimepoint = chaptertimepoint;
	}
	
}
