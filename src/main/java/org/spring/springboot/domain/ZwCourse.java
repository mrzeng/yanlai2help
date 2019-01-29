package org.spring.springboot.domain;
/**
 *
 *
 * Created by wangyanlai on 20181225.
 * @author wangyanlai
 */
public class ZwCourse {
    /**
     * 
     */
    private String id;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String info;

    /**
     * 
     */
    private String teachername;

    /**
     * 讲师简介
     */
    private String teacherinfo;

    /**
     * 职务
     */
    private String post;

    /**
     * 头衔
     */
    private String honor;

    /**
     * 
     */
    private String keyword;

    /**
     * 标签
     */
    private String tag;

    /**
     * 
     */
    private String length;

    /**
     * 
     */
    private String isfree;

    /**
     * 
     */
    private String ppath;

    /**
     * 0有效，1已删除
     */
    private String status;

    /**
     * 
     */
    private String fld1;

    /**
     * 
     */
    private String fld2;

    /**
     * 
     */
    private String creattime;

    /**
     * 
     */
    private String updatetime;

    /**
     * 包含几个课件
     */
    private String setnum;

    /**
     * 课件id 以;分隔
     */
    private String cswid;

    /**
     * 下载次数
     */
    private String downsum;

    /**
     * 
     */
    private String typeid;

    /**
     * 学分
     */
    private String credit;

    /**
     * 0推荐1热点2最新3无
     */
    private String istop;

    /**
     * 推荐时间
     */
    private String recommendtime;

    /**
     * 热点时间
     */
    private String hottime;

    /**
     * 课程图片地址
     */
    private String pic;

    /**
     * 选课人数
     */
    private String enrollment;

    /**
     * 是否考试 0是1否
     */
    private String isexam;

    /**
     * 考试id
     */
    private String examid;

    /**
     * 考试所占百分比
     */
    private String examprecent;

    /**
     * 是否练习
     */
    private String isexercise;

    /**
     * 练习所占百分比
     */
    private String exercisepercent;

    /**
     * 是否心得
     */
    private String isreview;

    /**
     * 新的所占百分比
     */
    private String reviewpercent;

    /**
     * 是否调查问卷
     */
    private String isquestionnaire;

    /**
     * 调查问卷占比
     */
    private String questionnairepercent;

    /**
     * 课件学习占比
     */
    private String coursewarerepercent;

    /**
     * 资源id
     */
    private String fileid;

    /**
     * 备注
     */
    private String remark;

    /**
     * 审核时间
     */
    private String checktime;

    /**
     * 适用平台1pc端2移动端3通用4其他
     */
    private String platform;

    /**
     * 课件ID
     */
    private String coursewareid;

    /**
     * 课程描述
     */
    private String description;

    /**
     * 评价平均分
     */
    private String commentscore;

    /**
     * 
     */
    private String publishtime;

    /**
     * 小图地址
     */
    private String picSmall;

    /**
     * 
     */
    private String format;

    /**
     * 讲师图片位置
     */
    private String teacherPic;

    /**
     * 讲师单位
     */
    private String company;

    /**
     * 课件来源0中经视频1自筹2其他
     */
    private String sourcetype;

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
     * 
     * @return NAME 
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name 
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 
     * @return INFO 
     */
    public String getInfo() {
        return info;
    }

    /**
     * 
     * @param info 
     */
    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }

    /**
     * 
     * @return TEACHERNAME 
     */
    public String getTeachername() {
        return teachername;
    }

    /**
     * 
     * @param teachername 
     */
    public void setTeachername(String teachername) {
        this.teachername = teachername == null ? null : teachername.trim();
    }

    /**
     * 讲师简介
     * @return TEACHERINFO 讲师简介
     */
    public String getTeacherinfo() {
        return teacherinfo;
    }

    /**
     * 讲师简介
     * @param teacherinfo 讲师简介
     */
    public void setTeacherinfo(String teacherinfo) {
        this.teacherinfo = teacherinfo == null ? null : teacherinfo.trim();
    }

    /**
     * 职务
     * @return POST 职务
     */
    public String getPost() {
        return post;
    }

    /**
     * 职务
     * @param post 职务
     */
    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    /**
     * 头衔
     * @return HONOR 头衔
     */
    public String getHonor() {
        return honor;
    }

    /**
     * 头衔
     * @param honor 头衔
     */
    public void setHonor(String honor) {
        this.honor = honor == null ? null : honor.trim();
    }

    /**
     * 
     * @return KEYWORD 
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * 
     * @param keyword 
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

    /**
     * 标签
     * @return TAG 标签
     */
    public String getTag() {
        return tag;
    }

    /**
     * 标签
     * @param tag 标签
     */
    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    /**
     * 
     * @return LENGTH 
     */
    public String getLength() {
        return length;
    }

    /**
     * 
     * @param length 
     */
    public void setLength(String length) {
        this.length = length == null ? null : length.trim();
    }

    /**
     * 
     * @return ISFREE 
     */
    public String getIsfree() {
        return isfree;
    }

    /**
     * 
     * @param isfree 
     */
    public void setIsfree(String isfree) {
        this.isfree = isfree == null ? null : isfree.trim();
    }

    /**
     * 
     * @return PPATH 
     */
    public String getPpath() {
        return ppath;
    }

    /**
     * 
     * @param ppath 
     */
    public void setPpath(String ppath) {
        this.ppath = ppath == null ? null : ppath.trim();
    }

    /**
     * 0有效，1已删除
     * @return STATUS 0有效，1已删除
     */
    public String getStatus() {
        return status;
    }

    /**
     * 0有效，1已删除
     * @param status 0有效，1已删除
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 
     * @return FLD1 
     */
    public String getFld1() {
        return fld1;
    }

    /**
     * 
     * @param fld1 
     */
    public void setFld1(String fld1) {
        this.fld1 = fld1 == null ? null : fld1.trim();
    }

    /**
     * 
     * @return FLD2 
     */
    public String getFld2() {
        return fld2;
    }

    /**
     * 
     * @param fld2 
     */
    public void setFld2(String fld2) {
        this.fld2 = fld2 == null ? null : fld2.trim();
    }

    /**
     * 
     * @return CREATTIME 
     */
    public String getCreattime() {
        return creattime;
    }

    /**
     * 
     * @param creattime 
     */
    public void setCreattime(String creattime) {
        this.creattime = creattime == null ? null : creattime.trim();
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
     * 包含几个课件
     * @return SETNUM 包含几个课件
     */
    public String getSetnum() {
        return setnum;
    }

    /**
     * 包含几个课件
     * @param setnum 包含几个课件
     */
    public void setSetnum(String setnum) {
        this.setnum = setnum == null ? null : setnum.trim();
    }

    /**
     * 课件id 以;分隔
     * @return CSWID 课件id 以;分隔
     */
    public String getCswid() {
        return cswid;
    }

    /**
     * 课件id 以;分隔
     * @param cswid 课件id 以;分隔
     */
    public void setCswid(String cswid) {
        this.cswid = cswid == null ? null : cswid.trim();
    }

    /**
     * 下载次数
     * @return DOWNSUM 下载次数
     */
    public String getDownsum() {
        return downsum;
    }

    /**
     * 下载次数
     * @param downsum 下载次数
     */
    public void setDownsum(String downsum) {
        this.downsum = downsum == null ? null : downsum.trim();
    }

    /**
     * 
     * @return TYPEID 
     */
    public String getTypeid() {
        return typeid;
    }

    /**
     * 
     * @param typeid 
     */
    public void setTypeid(String typeid) {
        this.typeid = typeid == null ? null : typeid.trim();
    }

    /**
     * 学分
     * @return CREDIT 学分
     */
    public String getCredit() {
        return credit;
    }

    /**
     * 学分
     * @param credit 学分
     */
    public void setCredit(String credit) {
        this.credit = credit == null ? null : credit.trim();
    }

    /**
     * 0推荐1热点2最新3无
     * @return ISTOP 0推荐1热点2最新3无
     */
    public String getIstop() {
        return istop;
    }

    /**
     * 0推荐1热点2最新3无
     * @param istop 0推荐1热点2最新3无
     */
    public void setIstop(String istop) {
        this.istop = istop == null ? null : istop.trim();
    }

    /**
     * 推荐时间
     * @return RECOMMENDTIME 推荐时间
     */
    public String getRecommendtime() {
        return recommendtime;
    }

    /**
     * 推荐时间
     * @param recommendtime 推荐时间
     */
    public void setRecommendtime(String recommendtime) {
        this.recommendtime = recommendtime == null ? null : recommendtime.trim();
    }

    /**
     * 热点时间
     * @return HOTTIME 热点时间
     */
    public String getHottime() {
        return hottime;
    }

    /**
     * 热点时间
     * @param hottime 热点时间
     */
    public void setHottime(String hottime) {
        this.hottime = hottime == null ? null : hottime.trim();
    }

    /**
     * 课程图片地址
     * @return PIC 课程图片地址
     */
    public String getPic() {
        return pic;
    }

    /**
     * 课程图片地址
     * @param pic 课程图片地址
     */
    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    /**
     * 选课人数
     * @return ENROLLMENT 选课人数
     */
    public String getEnrollment() {
        return enrollment;
    }

    /**
     * 选课人数
     * @param enrollment 选课人数
     */
    public void setEnrollment(String enrollment) {
        this.enrollment = enrollment == null ? null : enrollment.trim();
    }

    /**
     * 是否考试 0是1否
     * @return ISEXAM 是否考试 0是1否
     */
    public String getIsexam() {
        return isexam;
    }

    /**
     * 是否考试 0是1否
     * @param isexam 是否考试 0是1否
     */
    public void setIsexam(String isexam) {
        this.isexam = isexam == null ? null : isexam.trim();
    }

    /**
     * 考试id
     * @return EXAMID 考试id
     */
    public String getExamid() {
        return examid;
    }

    /**
     * 考试id
     * @param examid 考试id
     */
    public void setExamid(String examid) {
        this.examid = examid == null ? null : examid.trim();
    }

    /**
     * 考试所占百分比
     * @return EXAMPRECENT 考试所占百分比
     */
    public String getExamprecent() {
        return examprecent;
    }

    /**
     * 考试所占百分比
     * @param examprecent 考试所占百分比
     */
    public void setExamprecent(String examprecent) {
        this.examprecent = examprecent == null ? null : examprecent.trim();
    }

    /**
     * 是否练习
     * @return ISEXERCISE 是否练习
     */
    public String getIsexercise() {
        return isexercise;
    }

    /**
     * 是否练习
     * @param isexercise 是否练习
     */
    public void setIsexercise(String isexercise) {
        this.isexercise = isexercise == null ? null : isexercise.trim();
    }

    /**
     * 练习所占百分比
     * @return EXERCISEPERCENT 练习所占百分比
     */
    public String getExercisepercent() {
        return exercisepercent;
    }

    /**
     * 练习所占百分比
     * @param exercisepercent 练习所占百分比
     */
    public void setExercisepercent(String exercisepercent) {
        this.exercisepercent = exercisepercent == null ? null : exercisepercent.trim();
    }

    /**
     * 是否心得
     * @return ISREVIEW 是否心得
     */
    public String getIsreview() {
        return isreview;
    }

    /**
     * 是否心得
     * @param isreview 是否心得
     */
    public void setIsreview(String isreview) {
        this.isreview = isreview == null ? null : isreview.trim();
    }

    /**
     * 新的所占百分比
     * @return REVIEWPERCENT 新的所占百分比
     */
    public String getReviewpercent() {
        return reviewpercent;
    }

    /**
     * 新的所占百分比
     * @param reviewpercent 新的所占百分比
     */
    public void setReviewpercent(String reviewpercent) {
        this.reviewpercent = reviewpercent == null ? null : reviewpercent.trim();
    }

    /**
     * 是否调查问卷
     * @return ISQUESTIONNAIRE 是否调查问卷
     */
    public String getIsquestionnaire() {
        return isquestionnaire;
    }

    /**
     * 是否调查问卷
     * @param isquestionnaire 是否调查问卷
     */
    public void setIsquestionnaire(String isquestionnaire) {
        this.isquestionnaire = isquestionnaire == null ? null : isquestionnaire.trim();
    }

    /**
     * 调查问卷占比
     * @return QUESTIONNAIREPERCENT 调查问卷占比
     */
    public String getQuestionnairepercent() {
        return questionnairepercent;
    }

    /**
     * 调查问卷占比
     * @param questionnairepercent 调查问卷占比
     */
    public void setQuestionnairepercent(String questionnairepercent) {
        this.questionnairepercent = questionnairepercent == null ? null : questionnairepercent.trim();
    }

    /**
     * 课件学习占比
     * @return COURSEWAREREPERCENT 课件学习占比
     */
    public String getCoursewarerepercent() {
        return coursewarerepercent;
    }

    /**
     * 课件学习占比
     * @param coursewarerepercent 课件学习占比
     */
    public void setCoursewarerepercent(String coursewarerepercent) {
        this.coursewarerepercent = coursewarerepercent == null ? null : coursewarerepercent.trim();
    }

    /**
     * 资源id
     * @return FILEID 资源id
     */
    public String getFileid() {
        return fileid;
    }

    /**
     * 资源id
     * @param fileid 资源id
     */
    public void setFileid(String fileid) {
        this.fileid = fileid == null ? null : fileid.trim();
    }

    /**
     * 备注
     * @return REMARK 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 审核时间
     * @return CHECKTIME 审核时间
     */
    public String getChecktime() {
        return checktime;
    }

    /**
     * 审核时间
     * @param checktime 审核时间
     */
    public void setChecktime(String checktime) {
        this.checktime = checktime == null ? null : checktime.trim();
    }

    /**
     * 适用平台1pc端2移动端3通用4其他
     * @return PLATFORM 适用平台1pc端2移动端3通用4其他
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * 适用平台1pc端2移动端3通用4其他
     * @param platform 适用平台1pc端2移动端3通用4其他
     */
    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    /**
     * 课件ID
     * @return COURSEWAREID 课件ID
     */
    public String getCoursewareid() {
        return coursewareid;
    }

    /**
     * 课件ID
     * @param coursewareid 课件ID
     */
    public void setCoursewareid(String coursewareid) {
        this.coursewareid = coursewareid == null ? null : coursewareid.trim();
    }

    /**
     * 课程描述
     * @return DESCRIPTION 课程描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 课程描述
     * @param description 课程描述
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * 评价平均分
     * @return COMMENTSCORE 评价平均分
     */
    public String getCommentscore() {
        return commentscore;
    }

    /**
     * 评价平均分
     * @param commentscore 评价平均分
     */
    public void setCommentscore(String commentscore) {
        this.commentscore = commentscore == null ? null : commentscore.trim();
    }

    /**
     * 
     * @return PUBLISHTIME 
     */
    public String getPublishtime() {
        return publishtime;
    }

    /**
     * 
     * @param publishtime 
     */
    public void setPublishtime(String publishtime) {
        this.publishtime = publishtime == null ? null : publishtime.trim();
    }

    /**
     * 小图地址
     * @return PIC_SMALL 小图地址
     */
    public String getPicSmall() {
        return picSmall;
    }

    /**
     * 小图地址
     * @param picSmall 小图地址
     */
    public void setPicSmall(String picSmall) {
        this.picSmall = picSmall == null ? null : picSmall.trim();
    }

    /**
     * 
     * @return FORMAT 
     */
    public String getFormat() {
        return format;
    }

    /**
     * 
     * @param format 
     */
    public void setFormat(String format) {
        this.format = format == null ? null : format.trim();
    }

    /**
     * 讲师图片位置
     * @return TEACHER_PIC 讲师图片位置
     */
    public String getTeacherPic() {
        return teacherPic;
    }

    /**
     * 讲师图片位置
     * @param teacherPic 讲师图片位置
     */
    public void setTeacherPic(String teacherPic) {
        this.teacherPic = teacherPic == null ? null : teacherPic.trim();
    }

    /**
     * 讲师单位
     * @return COMPANY 讲师单位
     */
    public String getCompany() {
        return company;
    }

    /**
     * 讲师单位
     * @param company 讲师单位
     */
    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    /**
     * 课件来源0中经视频1自筹2其他
     * @return SOURCETYPE 课件来源0中经视频1自筹2其他
     */
    public String getSourcetype() {
        return sourcetype;
    }

    /**
     * 课件来源0中经视频1自筹2其他
     * @param sourcetype 课件来源0中经视频1自筹2其他
     */
    public void setSourcetype(String sourcetype) {
        this.sourcetype = sourcetype == null ? null : sourcetype.trim();
    }
}