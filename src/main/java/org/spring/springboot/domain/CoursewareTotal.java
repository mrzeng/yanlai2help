/**
 * <p>Description: </p>
 * <p>Title: CoursewareTotal.java</p>  
 * <p>Copyright: Copyright (c) 2018</p>  
 * <p>Company: cei</p>  
 * <p>@author zhaowei</p>
 * <p>@version 2018年4月30日 上午11:36:53</p>
 */
package org.spring.springboot.domain;

import java.util.List;

/**
 * Title: CoursewareTotal  
 * Description: 
 * @author zhaowei 
 * @version 2018年4月30日 上午11:36:53
 */
public class CoursewareTotal {
    	private String code;	//接口返回状态
    	private String albumid;
    	private String albumname;	
    	private String videotype;//0三分屏，1单视频，2微课，3flash动画4音频5纯mp4课件
    	private String albuminfo;
	private String albumpic;
	private String ctime;		//创建时间
	private String teachername;     //讲师名字
	private String credit; 		// 学分
	private String length; 		// 时长
	private String enrollment;      //选课人数
	private String schedule;	//进度
	private String lookpath;	//播放地址
	private String videoapth;	//单个视频地址
	private String downpath;	//下载地址
	private String post;		//講師職位
	private String ischoosed;	//是否已选1已选
	private String iscollected;	//是否已收藏
	private String isscored;	//是否已评分
	private List<Courseware> contentlist;
	/**
	 * @return the albumid
	 */
	public String getAlbumid() {
	    return albumid;
	}
	/**
	 * @param albumid the albumid to set
	 */
	public void setAlbumid(String albumid) {
	    this.albumid = albumid;
	}
	/**
	 * @return the albuminfo
	 */
	public String getAlbuminfo() {
	    return albuminfo;
	}
	/**
	 * @param albuminfo the albuminfo to set
	 */
	public void setAlbuminfo(String albuminfo) {
	    this.albuminfo = albuminfo;
	}
	/**
	 * @return the albumpic
	 */
	public String getAlbumpic() {
	    return albumpic;
	}
	/**
	 * @param albumpic the albumpic to set
	 */
	public void setAlbumpic(String albumpic) {
	    this.albumpic = albumpic;
	}
	/**
	 * @return the ctime
	 */
	public String getCtime() {
	    return ctime;
	}
	/**
	 * @param ctime the ctime to set
	 */
	public void setCtime(String ctime) {
	    this.ctime = ctime;
	}
	/**
	 * @return the teachername
	 */
	public String getTeachername() {
	    return teachername;
	}
	/**
	 * @param teachername the teachername to set
	 */
	public void setTeachername(String teachername) {
	    this.teachername = teachername;
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
	 * @return the length
	 */
	public String getLength() {
	    return length;
	}
	/**
	 * @param length the length to set
	 */
	public void setLength(String length) {
	    this.length = length;
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
	 * @return the albumname
	 */
	public String getAlbumname() {
	    return albumname;
	}
	/**
	 * @param albumname the albumname to set
	 */
	public void setAlbumname(String albumname) {
	    this.albumname = albumname;
	}
	/**
	 * @return the videotype
	 */
	public String getVideotype() {
	    return videotype;
	}
	/**
	 * @param videotype the videotype to set
	 */
	public void setVideotype(String videotype) {
	    this.videotype = videotype;
	}
	/**
	 * @return the contentlist
	 */
	public List<Courseware> getContentlist() {
	    return contentlist;
	}
	/**
	 * @param contentlist the contentlist to set
	 */
	public void setContentlist(List<Courseware> contentlist) {
	    this.contentlist = contentlist;
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
	 * @return the lookpath
	 */
	public String getLookpath() {
	    return lookpath;
	}
	/**
	 * @param lookpath the lookpath to set
	 */
	public void setLookpath(String lookpath) {
	    this.lookpath = lookpath;
	}
	/**
	 * @return the videoapth
	 */
	public String getVideoapth() {
	    return videoapth;
	}
	/**
	 * @param videoapth the videoapth to set
	 */
	public void setVideoapth(String videoapth) {
	    this.videoapth = videoapth;
	}
	/**
	 * @return the downpath
	 */
	public String getDownpath() {
	    return downpath;
	}
	/**
	 * @param downpath the downpath to set
	 */
	public void setDownpath(String downpath) {
	    this.downpath = downpath;
	}
	/**
	 * @return the post
	 */
	public String getPost() {
	    return post;
	}
	/**
	 * @param post the post to set
	 */
	public void setPost(String post) {
	    this.post = post;
	}
	/**
	 * @return the ischoosed
	 */
	public String getIschoosed() {
	    return ischoosed;
	}
	/**
	 * @param ischoosed the ischoosed to set
	 */
	public void setIschoosed(String ischoosed) {
	    this.ischoosed = ischoosed;
	}
	/**
	 * @return the iscollected
	 */
	public String getIscollected() {
	    return iscollected;
	}
	/**
	 * @param iscollected the iscollected to set
	 */
	public void setIscollected(String iscollected) {
	    this.iscollected = iscollected;
	}
	/**
	 * @return the isscored
	 */
	public String getIsscored() {
	    return isscored;
	}
	/**
	 * @param isscored the isscored to set
	 */
	public void setIsscored(String isscored) {
	    this.isscored = isscored;
	}
	
}
