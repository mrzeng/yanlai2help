/**
 * <p>Description: </p>
 * <p>Title: Course.java</p>  
 * <p>Copyright: Copyright (c) 2018</p>  
 * <p>Company: cei</p>  
 * <p>@author zhaowei</p>
 * <p>@version 2018年4月30日 上午10:25:32</p>
 */
package org.spring.springboot.domain;

import java.util.List;

/**
 * Title: Course  
 * Description: 
 * @author zhaowei 
 * @version 2018年4月30日 上午10:25:32
 */
public class Course {
	/**
	 * 课程实体类
	 */
	private String albumid;
	private String albumname;
	private String albumnum;	//剧集
	private String albuminfo;
	private String albumpic;
	private String ctime;		//创建时间
	private String utime;		//修改时间
	private String status;		//专辑状态 1为有效0为无效
	private String teachername; //讲师名字
	private String credit; 		// 学分
	private String length; 		// 时长
	private String titile;		//职务
	private String istop;		//1是热点，-1或null是初始状态
	private String albumclasstype;	//专辑课程分类
	private String choosecoursetime;		//加入课程时间
	private String isrecommend;	//1是推荐，-1或null为初始状态
	private String attribute;		//专辑选修必修 0 选修，1必修
	private String enrollment;		//选课人数
	private String teacherinfo;		//讲师简介
	private String albumschedule;	//专辑学习进度
	private String collectid;	//专辑收藏ID
	private String videotype;//0三分屏，1单视频，2微课，3flash动画4音频5纯mp4课件
	private String ischoosed;	//是否已选1已选
	private String iscollected;	//是否已收藏1已收藏
	private List<Courseware> classes;
	
	public String getAlbumid() {
		return albumid;
	}
	public void setAlbumid(String albumid) {
		this.albumid = albumid;
	}
	public String getAlbumname() {
		return albumname;
	}
	public void setAlbumname(String albumname) {
		this.albumname = albumname;
	}
	public String getAlbumnum() {
		return albumnum;
	}
	public void setAlbumnum(String albumnum) {
		this.albumnum = albumnum;
	}
	public String getAlbuminfo() {
		return albuminfo;
	}
	public void setAlbuminfo(String albuminfo) {
		this.albuminfo = albuminfo;
	}
	public String getAlbumpic() {
		return albumpic;
	}
	public void setAlbumpic(String albumpic) {
		this.albumpic = albumpic;
	}
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	public String getUtime() {
		return utime;
	}
	public void setUtime(String utime) {
		this.utime = utime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTeachername() {
		return teachername;
	}
	public void setTeachername(String teachername) {
		this.teachername = teachername;
	}
	
	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getTitile() {
		return titile;
	}
	public void setTitile(String titile) {
		this.titile = titile;
	}
	public String getIstop() {
		return istop;
	}
	public void setIstop(String istop) {
		this.istop = istop;
	}
	public String getAlbumclasstype() {
		return albumclasstype;
	}
	public void setAlbumclasstype(String albumclasstype) {
		this.albumclasstype = albumclasstype;
	}
	public String getChoosecoursetime() {
		return choosecoursetime;
	}
	public void setChoosecoursetime(String choosecoursetime) {
		this.choosecoursetime = choosecoursetime;
	}
	public String getIsrecommend() {
		return isrecommend;
	}
	public void setIsrecommend(String isrecommend) {
		this.isrecommend = isrecommend;
	}
	public List<Courseware> getClasses() {
		return classes;
	}
	public void setClasses(List<Courseware> classes) {
		this.classes = classes;
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
	public String getTeacherinfo() {
		return teacherinfo;
	}
	public void setTeacherinfo(String teacherinfo) {
		this.teacherinfo = teacherinfo;
	}
	public String getAlbumschedule() {
		return albumschedule;
	}
	public void setAlbumschedule(String albumschedule) {
		this.albumschedule = albumschedule;
	}
	public String getCollectid() {
		return collectid;
	}
	public void setCollectid(String collectid) {
		this.collectid = collectid;
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
	
}
