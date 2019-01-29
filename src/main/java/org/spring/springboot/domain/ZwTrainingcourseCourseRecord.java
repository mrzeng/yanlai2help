package org.spring.springboot.domain;
/**
 *
 *
 * Created by wangyanlai on 20181225.
 * @author wangyanlai
 */
public class ZwTrainingcourseCourseRecord {
    /**
     * 
     */
    private String id;

    /**
     * 用户id
     */
    private String userid;

    /**
     * 培训班id
     */
    private String tcid;

    /**
     * 课程id
     */
    private String courseid;

    /**
     * 0有效1无效2待审核
     */
    private String status;

    /**
     * 配置课程时间
     */
    private String choosecoursetime;

    /**
     * 
     */
    private String createtime;

    /**
     * 
     */
    private String updatetime;

    /**
     * 0必修1选修
     */
    private String attribute;

    /**
     * 学习进度%
     */
    private String schedule;

    /**
     * 学习时间点
     */
    private String timepoint;

    /**
     * 课程所学时长
     */
    private String allstudylength;

    /**
     * 
     * @return ID 
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 用户id
     * @return USERID 用户id
     */
    public String getUserid() {
        return userid;
    }

    /**
     * 用户id
     * @param userid 用户id
     */
    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    /**
     * 培训班id
     * @return TCID 培训班id
     */
    public String getTcid() {
        return tcid;
    }

    /**
     * 培训班id
     * @param tcid 培训班id
     */
    public void setTcid(String tcid) {
        this.tcid = tcid == null ? null : tcid.trim();
    }

    /**
     * 课程id
     * @return COURSEID 课程id
     */
    public String getCourseid() {
        return courseid;
    }

    /**
     * 课程id
     * @param courseid 课程id
     */
    public void setCourseid(String courseid) {
        this.courseid = courseid == null ? null : courseid.trim();
    }

    /**
     * 0有效1无效2待审核
     * @return STATUS 0有效1无效2待审核
     */
    public String getStatus() {
        return status;
    }

    /**
     * 0有效1无效2待审核
     * @param status 0有效1无效2待审核
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 配置课程时间
     * @return CHOOSECOURSETIME 配置课程时间
     */
    public String getChoosecoursetime() {
        return choosecoursetime;
    }

    /**
     * 配置课程时间
     * @param choosecoursetime 配置课程时间
     */
    public void setChoosecoursetime(String choosecoursetime) {
        this.choosecoursetime = choosecoursetime == null ? null : choosecoursetime.trim();
    }

    /**
     * 
     * @return CREATETIME 
     */
    public String getCreatetime() {
        return createtime;
    }

    /**
     * 
     * @param createtime 
     */
    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    /**
     * 
     * @return UPDATETIME 
     */
    public String getUpdatetime() {
        return updatetime;
    }

    /**
     * 
     * @param updatetime 
     */
    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime == null ? null : updatetime.trim();
    }

    /**
     * 0必修1选修
     * @return ATTRIBUTE 0必修1选修
     */
    public String getAttribute() {
        return attribute;
    }

    /**
     * 0必修1选修
     * @param attribute 0必修1选修
     */
    public void setAttribute(String attribute) {
        this.attribute = attribute == null ? null : attribute.trim();
    }

    /**
     * 学习进度%
     * @return SCHEDULE 学习进度%
     */
    public String getSchedule() {
        return schedule;
    }

    /**
     * 学习进度%
     * @param schedule 学习进度%
     */
    public void setSchedule(String schedule) {
        this.schedule = schedule == null ? null : schedule.trim();
    }

    /**
     * 学习时间点
     * @return TIMEPOINT 学习时间点
     */
    public String getTimepoint() {
        return timepoint;
    }

    /**
     * 学习时间点
     * @param timepoint 学习时间点
     */
    public void setTimepoint(String timepoint) {
        this.timepoint = timepoint == null ? null : timepoint.trim();
    }

    /**
     * 课程所学时长
     * @return ALLSTUDYLENGTH 课程所学时长
     */
    public String getAllstudylength() {
        return allstudylength;
    }

    /**
     * 课程所学时长
     * @param allstudylength 课程所学时长
     */
    public void setAllstudylength(String allstudylength) {
        this.allstudylength = allstudylength == null ? null : allstudylength.trim();
    }
}