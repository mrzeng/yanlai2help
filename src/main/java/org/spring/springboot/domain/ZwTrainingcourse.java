package org.spring.springboot.domain;
/**
 *
 *
 * Created by wangyanlai on 20181225.
 * @author wangyanlai
 */
public class ZwTrainingcourse {
    /**
     * 
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 培训班详情
     */
    private String info;

    /**
     * 类型0线上培训班1面授培训班2上级选调3域外培训
     */
    private String typeid;

    /**
     * 学分
     */
    private String credit;

    /**
     * 总学时
     */
    private String length;

    /**
     * 图片地址
     */
    private String pic;

    /**
     * 报名开始时间
     */
    private String enrolltime;

    /**
     * 报名截止日期
     */
    private String deadlinetime;

    /**
     * 开班时间
     */
    private String strattime;

    /**
     * 结束时间
     */
    private String endtime;

    /**
     * 
     */
    private String createtime;

    /**
     * 
     */
    private String updatetime;

    /**
     * 选课人数
     */
    private String enrollment;

    /**
     * 0有效1删除
     */
    private String status;

    /**
     * 生成培训班的userid
     */
    private String operator;

    /**
     * 主讲人
     */
    private String teachername;

    /**
     * 最大培训人数
     */
    private String maxenrollment;

    /**
     * 培训大地点
     */
    private String trainingplace;

    /**
     * 培训详细地点
     */
    private String trainingclass;

    /**
     * 培训要求
     */
    private String requirement;

    /**
     * 类型0线上培训班1面授培训班2上级选调3域外培训
     */
    private String type;

    /**
     * 是否继承课程学习进度,0不继承，1继承
     */
    private String isinherit;

    /**
     * 
     */
    private String courseRate;

    /**
     * 
     */
    private String paperRate;

    /**
     * 培训班来源
     */
    private String source;

    /**
     * 发布时间
     */
    private String publishtime;

    /**
     * 管理员代报名是否发送短信0发送1不发送
     */
    private String adminSingupSms;

    /**
     * 培训班结束前发送短信:0发送，1不发送
     */
    private String overRemindSms;

    /**
     * 培训班开班前多少天发送短信
     */
    private String overRemindDays;

    /**
     * 班主任
     */
    private String headmaster;

    /**
     * 联系方式
     */
    private String contactInfo;

    /**
     * 承办单位
     */
    private String organizer;

    /**
     * 按日程计算学分：0计算1不计算
     */
    private String isCalCredit;

    /**
     * 是否发送短信：0提醒1不提醒
     */
    private String isSendMsg;

    /**
     * 是否发送开班短信：0提醒1不提醒
     */
    private String openClassSms;

    /**
     * 开班短信提醒天数：0提醒1不提醒
     */
    private String openClassDays;

    /**
     * 是否发送报名审批提醒短信：0提醒1不提醒
     */
    private String singupCheckSms;

    /**
     * 报名审批
     */
    private String signCheck;

    /**
     * 考勤签到
     */
    private String signIn;

    /**
     * 是否开启通讯录
     */
    private String openMail;

    /**
     * 通讯录是否展示手机号
     */
    private String openMailPhone;

    /**
     * 是否设置必修课
     */
    private String mustCourse;

    /**
     * 必修课数量
     */
    private String mustCourseNum;

    /**
     * 必修课所占比重
     */
    private String mustCourseRate;

    /**
     * 是否设置选修课
     */
    private String selectCourse;

    /**
     * 选修课数量
     */
    private String selectCourseNum;

    /**
     * 选修课比重
     */
    private String selectCourseRate;

    /**
     * 是否设置结业考试
     */
    private String finalExam;

    /**
     * 设置结业考试数量
     */
    private String finalExamNum;

    /**
     * 结业考试所占比重
     */
    private String finalExamRate;

    /**
     * 是否设置练习
     */
    private String excercise;

    /**
     * 练习数量
     */
    private String excerciseNum;

    /**
     * 练习所占比重
     */
    private String excerciseRate;

    /**
     * 社否设置在线交流
     */
    private String onlineTalk;

    /**
     * 在线交流数量
     */
    private String onlineTalkNum;

    /**
     * 在线交流所占比例
     */
    private String onlineTalkRate;

    /**
     * 是否设置调查问卷
     */
    private String questionnaire;

    /**
     * 设置调查问卷数量
     */
    private String questionnaireNum;

    /**
     * 调查问卷所占比例
     */
    private String questionnaireRate;

    /**
     * 是否设置阅读资料
     */
    private String readDoc;

    /**
     * 阅读资料数量
     */
    private String readDocNum;

    /**
     * 阅读资料所占比例
     */
    private String readDocRate;

    /**
     * 需完成的选修课数量
     */
    private String electiveCourse;

    /**
     * 主办单位
     */
    private String host;

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
     * 名称
     * @return NAME 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 名称
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 培训班详情
     * @return INFO 培训班详情
     */
    public String getInfo() {
        return info;
    }

    /**
     * 培训班详情
     * @param info 培训班详情
     */
    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }

    /**
     * 类型0线上培训班1面授培训班2上级选调3域外培训
     * @return TYPEID 类型0线上培训班1面授培训班2上级选调3域外培训
     */
    public String getTypeid() {
        return typeid;
    }

    /**
     * 类型0线上培训班1面授培训班2上级选调3域外培训
     * @param typeid 类型0线上培训班1面授培训班2上级选调3域外培训
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
     * 总学时
     * @return LENGTH 总学时
     */
    public String getLength() {
        return length;
    }

    /**
     * 总学时
     * @param length 总学时
     */
    public void setLength(String length) {
        this.length = length == null ? null : length.trim();
    }

    /**
     * 图片地址
     * @return PIC 图片地址
     */
    public String getPic() {
        return pic;
    }

    /**
     * 图片地址
     * @param pic 图片地址
     */
    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    /**
     * 报名开始时间
     * @return ENROLLTIME 报名开始时间
     */
    public String getEnrolltime() {
        return enrolltime;
    }

    /**
     * 报名开始时间
     * @param enrolltime 报名开始时间
     */
    public void setEnrolltime(String enrolltime) {
        this.enrolltime = enrolltime == null ? null : enrolltime.trim();
    }

    /**
     * 报名截止日期
     * @return DEADLINETIME 报名截止日期
     */
    public String getDeadlinetime() {
        return deadlinetime;
    }

    /**
     * 报名截止日期
     * @param deadlinetime 报名截止日期
     */
    public void setDeadlinetime(String deadlinetime) {
        this.deadlinetime = deadlinetime == null ? null : deadlinetime.trim();
    }

    /**
     * 开班时间
     * @return STRATTIME 开班时间
     */
    public String getStrattime() {
        return strattime;
    }

    /**
     * 开班时间
     * @param strattime 开班时间
     */
    public void setStrattime(String strattime) {
        this.strattime = strattime == null ? null : strattime.trim();
    }

    /**
     * 结束时间
     * @return ENDTIME 结束时间
     */
    public String getEndtime() {
        return endtime;
    }

    /**
     * 结束时间
     * @param endtime 结束时间
     */
    public void setEndtime(String endtime) {
        this.endtime = endtime == null ? null : endtime.trim();
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
     * 0有效1删除
     * @return STATUS 0有效1删除
     */
    public String getStatus() {
        return status;
    }

    /**
     * 0有效1删除
     * @param status 0有效1删除
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 生成培训班的userid
     * @return OPERATOR 生成培训班的userid
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 生成培训班的userid
     * @param operator 生成培训班的userid
     */
    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    /**
     * 主讲人
     * @return TEACHERNAME 主讲人
     */
    public String getTeachername() {
        return teachername;
    }

    /**
     * 主讲人
     * @param teachername 主讲人
     */
    public void setTeachername(String teachername) {
        this.teachername = teachername == null ? null : teachername.trim();
    }

    /**
     * 最大培训人数
     * @return MAXENROLLMENT 最大培训人数
     */
    public String getMaxenrollment() {
        return maxenrollment;
    }

    /**
     * 最大培训人数
     * @param maxenrollment 最大培训人数
     */
    public void setMaxenrollment(String maxenrollment) {
        this.maxenrollment = maxenrollment == null ? null : maxenrollment.trim();
    }

    /**
     * 培训大地点
     * @return TRAININGPLACE 培训大地点
     */
    public String getTrainingplace() {
        return trainingplace;
    }

    /**
     * 培训大地点
     * @param trainingplace 培训大地点
     */
    public void setTrainingplace(String trainingplace) {
        this.trainingplace = trainingplace == null ? null : trainingplace.trim();
    }

    /**
     * 培训详细地点
     * @return TRAININGCLASS 培训详细地点
     */
    public String getTrainingclass() {
        return trainingclass;
    }

    /**
     * 培训详细地点
     * @param trainingclass 培训详细地点
     */
    public void setTrainingclass(String trainingclass) {
        this.trainingclass = trainingclass == null ? null : trainingclass.trim();
    }

    /**
     * 培训要求
     * @return REQUIREMENT 培训要求
     */
    public String getRequirement() {
        return requirement;
    }

    /**
     * 培训要求
     * @param requirement 培训要求
     */
    public void setRequirement(String requirement) {
        this.requirement = requirement == null ? null : requirement.trim();
    }

    /**
     * 类型0线上培训班1面授培训班2上级选调3域外培训
     * @return TYPE 类型0线上培训班1面授培训班2上级选调3域外培训
     */
    public String getType() {
        return type;
    }

    /**
     * 类型0线上培训班1面授培训班2上级选调3域外培训
     * @param type 类型0线上培训班1面授培训班2上级选调3域外培训
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 是否继承课程学习进度,0不继承，1继承
     * @return ISINHERIT 是否继承课程学习进度,0不继承，1继承
     */
    public String getIsinherit() {
        return isinherit;
    }

    /**
     * 是否继承课程学习进度,0不继承，1继承
     * @param isinherit 是否继承课程学习进度,0不继承，1继承
     */
    public void setIsinherit(String isinherit) {
        this.isinherit = isinherit == null ? null : isinherit.trim();
    }

    /**
     * 
     * @return COURSE_RATE 
     */
    public String getCourseRate() {
        return courseRate;
    }

    /**
     * 
     * @param courseRate 
     */
    public void setCourseRate(String courseRate) {
        this.courseRate = courseRate == null ? null : courseRate.trim();
    }

    /**
     * 
     * @return PAPER_RATE 
     */
    public String getPaperRate() {
        return paperRate;
    }

    /**
     * 
     * @param paperRate 
     */
    public void setPaperRate(String paperRate) {
        this.paperRate = paperRate == null ? null : paperRate.trim();
    }

    /**
     * 培训班来源
     * @return SOURCE 培训班来源
     */
    public String getSource() {
        return source;
    }

    /**
     * 培训班来源
     * @param source 培训班来源
     */
    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    /**
     * 发布时间
     * @return PUBLISHTIME 发布时间
     */
    public String getPublishtime() {
        return publishtime;
    }

    /**
     * 发布时间
     * @param publishtime 发布时间
     */
    public void setPublishtime(String publishtime) {
        this.publishtime = publishtime == null ? null : publishtime.trim();
    }

    /**
     * 管理员代报名是否发送短信0发送1不发送
     * @return ADMIN_SINGUP_SMS 管理员代报名是否发送短信0发送1不发送
     */
    public String getAdminSingupSms() {
        return adminSingupSms;
    }

    /**
     * 管理员代报名是否发送短信0发送1不发送
     * @param adminSingupSms 管理员代报名是否发送短信0发送1不发送
     */
    public void setAdminSingupSms(String adminSingupSms) {
        this.adminSingupSms = adminSingupSms == null ? null : adminSingupSms.trim();
    }

    /**
     * 培训班结束前发送短信:0发送，1不发送
     * @return OVER_REMIND_SMS 培训班结束前发送短信:0发送，1不发送
     */
    public String getOverRemindSms() {
        return overRemindSms;
    }

    /**
     * 培训班结束前发送短信:0发送，1不发送
     * @param overRemindSms 培训班结束前发送短信:0发送，1不发送
     */
    public void setOverRemindSms(String overRemindSms) {
        this.overRemindSms = overRemindSms == null ? null : overRemindSms.trim();
    }

    /**
     * 培训班开班前多少天发送短信
     * @return OVER_REMIND_DAYS 培训班开班前多少天发送短信
     */
    public String getOverRemindDays() {
        return overRemindDays;
    }

    /**
     * 培训班开班前多少天发送短信
     * @param overRemindDays 培训班开班前多少天发送短信
     */
    public void setOverRemindDays(String overRemindDays) {
        this.overRemindDays = overRemindDays == null ? null : overRemindDays.trim();
    }

    /**
     * 班主任
     * @return HEADMASTER 班主任
     */
    public String getHeadmaster() {
        return headmaster;
    }

    /**
     * 班主任
     * @param headmaster 班主任
     */
    public void setHeadmaster(String headmaster) {
        this.headmaster = headmaster == null ? null : headmaster.trim();
    }

    /**
     * 联系方式
     * @return CONTACT_INFO 联系方式
     */
    public String getContactInfo() {
        return contactInfo;
    }

    /**
     * 联系方式
     * @param contactInfo 联系方式
     */
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo == null ? null : contactInfo.trim();
    }

    /**
     * 承办单位
     * @return ORGANIZER 承办单位
     */
    public String getOrganizer() {
        return organizer;
    }

    /**
     * 承办单位
     * @param organizer 承办单位
     */
    public void setOrganizer(String organizer) {
        this.organizer = organizer == null ? null : organizer.trim();
    }

    /**
     * 按日程计算学分：0计算1不计算
     * @return IS_CAL_CREDIT 按日程计算学分：0计算1不计算
     */
    public String getIsCalCredit() {
        return isCalCredit;
    }

    /**
     * 按日程计算学分：0计算1不计算
     * @param isCalCredit 按日程计算学分：0计算1不计算
     */
    public void setIsCalCredit(String isCalCredit) {
        this.isCalCredit = isCalCredit == null ? null : isCalCredit.trim();
    }

    /**
     * 是否发送短信：0提醒1不提醒
     * @return IS_SEND_MSG 是否发送短信：0提醒1不提醒
     */
    public String getIsSendMsg() {
        return isSendMsg;
    }

    /**
     * 是否发送短信：0提醒1不提醒
     * @param isSendMsg 是否发送短信：0提醒1不提醒
     */
    public void setIsSendMsg(String isSendMsg) {
        this.isSendMsg = isSendMsg == null ? null : isSendMsg.trim();
    }

    /**
     * 是否发送开班短信：0提醒1不提醒
     * @return OPEN_CLASS_SMS 是否发送开班短信：0提醒1不提醒
     */
    public String getOpenClassSms() {
        return openClassSms;
    }

    /**
     * 是否发送开班短信：0提醒1不提醒
     * @param openClassSms 是否发送开班短信：0提醒1不提醒
     */
    public void setOpenClassSms(String openClassSms) {
        this.openClassSms = openClassSms == null ? null : openClassSms.trim();
    }

    /**
     * 开班短信提醒天数：0提醒1不提醒
     * @return OPEN_CLASS_DAYS 开班短信提醒天数：0提醒1不提醒
     */
    public String getOpenClassDays() {
        return openClassDays;
    }

    /**
     * 开班短信提醒天数：0提醒1不提醒
     * @param openClassDays 开班短信提醒天数：0提醒1不提醒
     */
    public void setOpenClassDays(String openClassDays) {
        this.openClassDays = openClassDays == null ? null : openClassDays.trim();
    }

    /**
     * 是否发送报名审批提醒短信：0提醒1不提醒
     * @return SINGUP_CHECK_SMS 是否发送报名审批提醒短信：0提醒1不提醒
     */
    public String getSingupCheckSms() {
        return singupCheckSms;
    }

    /**
     * 是否发送报名审批提醒短信：0提醒1不提醒
     * @param singupCheckSms 是否发送报名审批提醒短信：0提醒1不提醒
     */
    public void setSingupCheckSms(String singupCheckSms) {
        this.singupCheckSms = singupCheckSms == null ? null : singupCheckSms.trim();
    }

    /**
     * 报名审批
     * @return SIGN_CHECK 报名审批
     */
    public String getSignCheck() {
        return signCheck;
    }

    /**
     * 报名审批
     * @param signCheck 报名审批
     */
    public void setSignCheck(String signCheck) {
        this.signCheck = signCheck == null ? null : signCheck.trim();
    }

    /**
     * 考勤签到
     * @return SIGN_IN 考勤签到
     */
    public String getSignIn() {
        return signIn;
    }

    /**
     * 考勤签到
     * @param signIn 考勤签到
     */
    public void setSignIn(String signIn) {
        this.signIn = signIn == null ? null : signIn.trim();
    }

    /**
     * 是否开启通讯录
     * @return OPEN_MAIL 是否开启通讯录
     */
    public String getOpenMail() {
        return openMail;
    }

    /**
     * 是否开启通讯录
     * @param openMail 是否开启通讯录
     */
    public void setOpenMail(String openMail) {
        this.openMail = openMail == null ? null : openMail.trim();
    }

    /**
     * 通讯录是否展示手机号
     * @return OPEN_MAIL_PHONE 通讯录是否展示手机号
     */
    public String getOpenMailPhone() {
        return openMailPhone;
    }

    /**
     * 通讯录是否展示手机号
     * @param openMailPhone 通讯录是否展示手机号
     */
    public void setOpenMailPhone(String openMailPhone) {
        this.openMailPhone = openMailPhone == null ? null : openMailPhone.trim();
    }

    /**
     * 是否设置必修课
     * @return MUST_COURSE 是否设置必修课
     */
    public String getMustCourse() {
        return mustCourse;
    }

    /**
     * 是否设置必修课
     * @param mustCourse 是否设置必修课
     */
    public void setMustCourse(String mustCourse) {
        this.mustCourse = mustCourse == null ? null : mustCourse.trim();
    }

    /**
     * 必修课数量
     * @return MUST_COURSE_NUM 必修课数量
     */
    public String getMustCourseNum() {
        return mustCourseNum;
    }

    /**
     * 必修课数量
     * @param mustCourseNum 必修课数量
     */
    public void setMustCourseNum(String mustCourseNum) {
        this.mustCourseNum = mustCourseNum == null ? null : mustCourseNum.trim();
    }

    /**
     * 必修课所占比重
     * @return MUST_COURSE_RATE 必修课所占比重
     */
    public String getMustCourseRate() {
        return mustCourseRate;
    }

    /**
     * 必修课所占比重
     * @param mustCourseRate 必修课所占比重
     */
    public void setMustCourseRate(String mustCourseRate) {
        this.mustCourseRate = mustCourseRate == null ? null : mustCourseRate.trim();
    }

    /**
     * 是否设置选修课
     * @return SELECT_COURSE 是否设置选修课
     */
    public String getSelectCourse() {
        return selectCourse;
    }

    /**
     * 是否设置选修课
     * @param selectCourse 是否设置选修课
     */
    public void setSelectCourse(String selectCourse) {
        this.selectCourse = selectCourse == null ? null : selectCourse.trim();
    }

    /**
     * 选修课数量
     * @return SELECT_COURSE_NUM 选修课数量
     */
    public String getSelectCourseNum() {
        return selectCourseNum;
    }

    /**
     * 选修课数量
     * @param selectCourseNum 选修课数量
     */
    public void setSelectCourseNum(String selectCourseNum) {
        this.selectCourseNum = selectCourseNum == null ? null : selectCourseNum.trim();
    }

    /**
     * 选修课比重
     * @return SELECT_COURSE_RATE 选修课比重
     */
    public String getSelectCourseRate() {
        return selectCourseRate;
    }

    /**
     * 选修课比重
     * @param selectCourseRate 选修课比重
     */
    public void setSelectCourseRate(String selectCourseRate) {
        this.selectCourseRate = selectCourseRate == null ? null : selectCourseRate.trim();
    }

    /**
     * 是否设置结业考试
     * @return FINAL_EXAM 是否设置结业考试
     */
    public String getFinalExam() {
        return finalExam;
    }

    /**
     * 是否设置结业考试
     * @param finalExam 是否设置结业考试
     */
    public void setFinalExam(String finalExam) {
        this.finalExam = finalExam == null ? null : finalExam.trim();
    }

    /**
     * 设置结业考试数量
     * @return FINAL_EXAM_NUM 设置结业考试数量
     */
    public String getFinalExamNum() {
        return finalExamNum;
    }

    /**
     * 设置结业考试数量
     * @param finalExamNum 设置结业考试数量
     */
    public void setFinalExamNum(String finalExamNum) {
        this.finalExamNum = finalExamNum == null ? null : finalExamNum.trim();
    }

    /**
     * 结业考试所占比重
     * @return FINAL_EXAM_RATE 结业考试所占比重
     */
    public String getFinalExamRate() {
        return finalExamRate;
    }

    /**
     * 结业考试所占比重
     * @param finalExamRate 结业考试所占比重
     */
    public void setFinalExamRate(String finalExamRate) {
        this.finalExamRate = finalExamRate == null ? null : finalExamRate.trim();
    }

    /**
     * 是否设置练习
     * @return EXCERCISE 是否设置练习
     */
    public String getExcercise() {
        return excercise;
    }

    /**
     * 是否设置练习
     * @param excercise 是否设置练习
     */
    public void setExcercise(String excercise) {
        this.excercise = excercise == null ? null : excercise.trim();
    }

    /**
     * 练习数量
     * @return EXCERCISE_NUM 练习数量
     */
    public String getExcerciseNum() {
        return excerciseNum;
    }

    /**
     * 练习数量
     * @param excerciseNum 练习数量
     */
    public void setExcerciseNum(String excerciseNum) {
        this.excerciseNum = excerciseNum == null ? null : excerciseNum.trim();
    }

    /**
     * 练习所占比重
     * @return EXCERCISE_RATE 练习所占比重
     */
    public String getExcerciseRate() {
        return excerciseRate;
    }

    /**
     * 练习所占比重
     * @param excerciseRate 练习所占比重
     */
    public void setExcerciseRate(String excerciseRate) {
        this.excerciseRate = excerciseRate == null ? null : excerciseRate.trim();
    }

    /**
     * 社否设置在线交流
     * @return ONLINE_TALK 社否设置在线交流
     */
    public String getOnlineTalk() {
        return onlineTalk;
    }

    /**
     * 社否设置在线交流
     * @param onlineTalk 社否设置在线交流
     */
    public void setOnlineTalk(String onlineTalk) {
        this.onlineTalk = onlineTalk == null ? null : onlineTalk.trim();
    }

    /**
     * 在线交流数量
     * @return ONLINE_TALK_NUM 在线交流数量
     */
    public String getOnlineTalkNum() {
        return onlineTalkNum;
    }

    /**
     * 在线交流数量
     * @param onlineTalkNum 在线交流数量
     */
    public void setOnlineTalkNum(String onlineTalkNum) {
        this.onlineTalkNum = onlineTalkNum == null ? null : onlineTalkNum.trim();
    }

    /**
     * 在线交流所占比例
     * @return ONLINE_TALK_RATE 在线交流所占比例
     */
    public String getOnlineTalkRate() {
        return onlineTalkRate;
    }

    /**
     * 在线交流所占比例
     * @param onlineTalkRate 在线交流所占比例
     */
    public void setOnlineTalkRate(String onlineTalkRate) {
        this.onlineTalkRate = onlineTalkRate == null ? null : onlineTalkRate.trim();
    }

    /**
     * 是否设置调查问卷
     * @return QUESTIONNAIRE 是否设置调查问卷
     */
    public String getQuestionnaire() {
        return questionnaire;
    }

    /**
     * 是否设置调查问卷
     * @param questionnaire 是否设置调查问卷
     */
    public void setQuestionnaire(String questionnaire) {
        this.questionnaire = questionnaire == null ? null : questionnaire.trim();
    }

    /**
     * 设置调查问卷数量
     * @return QUESTIONNAIRE_NUM 设置调查问卷数量
     */
    public String getQuestionnaireNum() {
        return questionnaireNum;
    }

    /**
     * 设置调查问卷数量
     * @param questionnaireNum 设置调查问卷数量
     */
    public void setQuestionnaireNum(String questionnaireNum) {
        this.questionnaireNum = questionnaireNum == null ? null : questionnaireNum.trim();
    }

    /**
     * 调查问卷所占比例
     * @return QUESTIONNAIRE_RATE 调查问卷所占比例
     */
    public String getQuestionnaireRate() {
        return questionnaireRate;
    }

    /**
     * 调查问卷所占比例
     * @param questionnaireRate 调查问卷所占比例
     */
    public void setQuestionnaireRate(String questionnaireRate) {
        this.questionnaireRate = questionnaireRate == null ? null : questionnaireRate.trim();
    }

    /**
     * 是否设置阅读资料
     * @return READ_DOC 是否设置阅读资料
     */
    public String getReadDoc() {
        return readDoc;
    }

    /**
     * 是否设置阅读资料
     * @param readDoc 是否设置阅读资料
     */
    public void setReadDoc(String readDoc) {
        this.readDoc = readDoc == null ? null : readDoc.trim();
    }

    /**
     * 阅读资料数量
     * @return READ_DOC_NUM 阅读资料数量
     */
    public String getReadDocNum() {
        return readDocNum;
    }

    /**
     * 阅读资料数量
     * @param readDocNum 阅读资料数量
     */
    public void setReadDocNum(String readDocNum) {
        this.readDocNum = readDocNum == null ? null : readDocNum.trim();
    }

    /**
     * 阅读资料所占比例
     * @return READ_DOC_RATE 阅读资料所占比例
     */
    public String getReadDocRate() {
        return readDocRate;
    }

    /**
     * 阅读资料所占比例
     * @param readDocRate 阅读资料所占比例
     */
    public void setReadDocRate(String readDocRate) {
        this.readDocRate = readDocRate == null ? null : readDocRate.trim();
    }

    /**
     * 需完成的选修课数量
     * @return ELECTIVE_COURSE 需完成的选修课数量
     */
    public String getElectiveCourse() {
        return electiveCourse;
    }

    /**
     * 需完成的选修课数量
     * @param electiveCourse 需完成的选修课数量
     */
    public void setElectiveCourse(String electiveCourse) {
        this.electiveCourse = electiveCourse == null ? null : electiveCourse.trim();
    }

    /**
     * 主办单位
     * @return HOST 主办单位
     */
    public String getHost() {
        return host;
    }

    /**
     * 主办单位
     * @param host 主办单位
     */
    public void setHost(String host) {
        this.host = host == null ? null : host.trim();
    }
}