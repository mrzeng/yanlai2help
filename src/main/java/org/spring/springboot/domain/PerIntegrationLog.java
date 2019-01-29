package org.spring.springboot.domain;
/**
 *
 *
 * Created by wangyanlai on 20181225.
 * @author wangyanlai
 */
public class PerIntegrationLog {
    /**
     * 流水id 主键
     */
    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 课程id
     */
    private String courseId;

    /**
     * 问题id
     */
    private String questionId;

    /**
     * 作业id
     */
    private String taskId;

    /**
     * 培训班id
     */
    private String trainingId;

    /**
     * 兑换余额id
     */
    private String exchangeId;

    /**
     * 问卷调查id
     */
    private String questionnaireId;

    /**
     * 变更后积分
     */
    private String score;

    /**
     * 变更前积分
     */
    private String lastTimeScore;

    /**
     * 变更值
     */
    private String updScore;

    /**
     * 积分变更规则类型  登陆00001，学习00002（备注1值为课程类型），评价00003（备注1值为最大评价次数）,恶意评价00004，问卷调查00005,分享课程00006,精华作业00007，点赞"00008"
            ，最大兑换人数"00009"，个人最大兑换金额"00010"，奖励金额过期时间（分值为月数）"00011"
     */
    private String scoreType;

    /**
     * 详细描述
     */
    private String logname;

    /**
     * 操作用户IP
     */
    private String ip;

    /**
     * 更新操作员
     */
    private String creationPer;

    /**
     * 创建日期
     */
    private String creationDt;

    /**
     * 创建时间
     */
    private String creationTm;

    /**
     * 更新时间
     */
    private String updateTm;

    /**
     * 时间戳
     */
    private String tmSmp;

    /**
     * 备用字段1
     */
    private String remark1;

    /**
     * 备用字段2
     */
    private String remark2;

    /**
     * 备用字段3
     */
    private String remark3;

    /**
     * 备用字段4
     */
    private String remark4;

    /**
     * 备用字段5
     */
    private String remark5;

    /**
     * 流水id
     * @return ID 流水id
     */
    public String getId() {
        return id;
    }

    /**
     * 流水id
     * @param id 流水id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 用户id
     * @return USER_ID 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 用户id
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 课程id
     * @return COURSE_ID 课程id
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * 课程id
     * @param courseId 课程id
     */
    public void setCourseId(String courseId) {
        this.courseId = courseId == null ? null : courseId.trim();
    }

    /**
     * 问题id
     * @return QUESTION_ID 问题id
     */
    public String getQuestionId() {
        return questionId;
    }

    /**
     * 问题id
     * @param questionId 问题id
     */
    public void setQuestionId(String questionId) {
        this.questionId = questionId == null ? null : questionId.trim();
    }

    /**
     * 作业id
     * @return TASK_ID 作业id
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * 作业id
     * @param taskId 作业id
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    /**
     * 培训班id
     * @return TRAINING_ID 培训班id
     */
    public String getTrainingId() {
        return trainingId;
    }

    /**
     * 培训班id
     * @param trainingId 培训班id
     */
    public void setTrainingId(String trainingId) {
        this.trainingId = trainingId == null ? null : trainingId.trim();
    }

    /**
     * 兑换余额id
     * @return EXCHANGE_ID 兑换余额id
     */
    public String getExchangeId() {
        return exchangeId;
    }

    /**
     * 兑换余额id
     * @param exchangeId 兑换余额id
     */
    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId == null ? null : exchangeId.trim();
    }

    /**
     * 问卷调查id
     * @return QUESTIONNAIRE_ID 问卷调查id
     */
    public String getQuestionnaireId() {
        return questionnaireId;
    }

    /**
     * 问卷调查id
     * @param questionnaireId 问卷调查id
     */
    public void setQuestionnaireId(String questionnaireId) {
        this.questionnaireId = questionnaireId == null ? null : questionnaireId.trim();
    }

    /**
     * 变更后积分
     * @return SCORE 变更后积分
     */
    public String getScore() {
        return score;
    }

    /**
     * 变更后积分
     * @param score 变更后积分
     */
    public void setScore(String score) {
        this.score = score == null ? null : score.trim();
    }

    /**
     * 变更前积分
     * @return LAST_TIME_SCORE 变更前积分
     */
    public String getLastTimeScore() {
        return lastTimeScore;
    }

    /**
     * 变更前积分
     * @param lastTimeScore 变更前积分
     */
    public void setLastTimeScore(String lastTimeScore) {
        this.lastTimeScore = lastTimeScore == null ? null : lastTimeScore.trim();
    }

    /**
     * 变更值
     * @return UPD_SCORE 变更值
     */
    public String getUpdScore() {
        return updScore;
    }

    /**
     * 变更值
     * @param updScore 变更值
     */
    public void setUpdScore(String updScore) {
        this.updScore = updScore == null ? null : updScore.trim();
    }

    /**
     * 积分变更规则类型  登陆00001，学习00002（备注1值为课程类型），评价00003（备注1值为最大评价次数）,恶意评价00004，问卷调查00005,分享课程00006,精华作业00007，点赞"00008"
            ，最大兑换人数"00009"，个人最大兑换金额"00010"，奖励金额过期时间（分值为月数）"00011"
     * @return SCORE_TYPE 积分变更规则类型  登陆00001，学习00002（备注1值为课程类型），评价00003（备注1值为最大评价次数）,恶意评价00004，问卷调查00005,分享课程00006,精华作业00007，点赞"00008"
            ，最大兑换人数"00009"，个人最大兑换金额"00010"，奖励金额过期时间（分值为月数）"00011"
     */
    public String getScoreType() {
        return scoreType;
    }

    /**
     * 积分变更规则类型  登陆00001，学习00002（备注1值为课程类型），评价00003（备注1值为最大评价次数）,恶意评价00004，问卷调查00005,分享课程00006,精华作业00007，点赞"00008"
            ，最大兑换人数"00009"，个人最大兑换金额"00010"，奖励金额过期时间（分值为月数）"00011"
     * @param scoreType 积分变更规则类型  登陆00001，学习00002（备注1值为课程类型），评价00003（备注1值为最大评价次数）,恶意评价00004，问卷调查00005,分享课程00006,精华作业00007，点赞"00008"
            ，最大兑换人数"00009"，个人最大兑换金额"00010"，奖励金额过期时间（分值为月数）"00011"
     */
    public void setScoreType(String scoreType) {
        this.scoreType = scoreType == null ? null : scoreType.trim();
    }

    /**
     * 详细描述
     * @return LOGNAME 详细描述
     */
    public String getLogname() {
        return logname;
    }

    /**
     * 详细描述
     * @param logname 详细描述
     */
    public void setLogname(String logname) {
        this.logname = logname == null ? null : logname.trim();
    }

    /**
     * 操作用户IP
     * @return IP 操作用户IP
     */
    public String getIp() {
        return ip;
    }

    /**
     * 操作用户IP
     * @param ip 操作用户IP
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * 更新操作员
     * @return CREATION_PER 更新操作员
     */
    public String getCreationPer() {
        return creationPer;
    }

    /**
     * 更新操作员
     * @param creationPer 更新操作员
     */
    public void setCreationPer(String creationPer) {
        this.creationPer = creationPer == null ? null : creationPer.trim();
    }

    /**
     * 创建日期
     * @return CREATION_DT 创建日期
     */
    public String getCreationDt() {
        return creationDt;
    }

    /**
     * 创建日期
     * @param creationDt 创建日期
     */
    public void setCreationDt(String creationDt) {
        this.creationDt = creationDt == null ? null : creationDt.trim();
    }

    /**
     * 创建时间
     * @return CREATION_TM 创建时间
     */
    public String getCreationTm() {
        return creationTm;
    }

    /**
     * 创建时间
     * @param creationTm 创建时间
     */
    public void setCreationTm(String creationTm) {
        this.creationTm = creationTm == null ? null : creationTm.trim();
    }

    /**
     * 更新时间
     * @return UPDATE_TM 更新时间
     */
    public String getUpdateTm() {
        return updateTm;
    }

    /**
     * 更新时间
     * @param updateTm 更新时间
     */
    public void setUpdateTm(String updateTm) {
        this.updateTm = updateTm == null ? null : updateTm.trim();
    }

    /**
     * 时间戳
     * @return TM_SMP 时间戳
     */
    public String getTmSmp() {
        return tmSmp;
    }

    /**
     * 时间戳
     * @param tmSmp 时间戳
     */
    public void setTmSmp(String tmSmp) {
        this.tmSmp = tmSmp == null ? null : tmSmp.trim();
    }

    /**
     * 备用字段1
     * @return REMARK1 备用字段1
     */
    public String getRemark1() {
        return remark1;
    }

    /**
     * 备用字段1
     * @param remark1 备用字段1
     */
    public void setRemark1(String remark1) {
        this.remark1 = remark1 == null ? null : remark1.trim();
    }

    /**
     * 备用字段2
     * @return REMARK2 备用字段2
     */
    public String getRemark2() {
        return remark2;
    }

    /**
     * 备用字段2
     * @param remark2 备用字段2
     */
    public void setRemark2(String remark2) {
        this.remark2 = remark2 == null ? null : remark2.trim();
    }

    /**
     * 备用字段3
     * @return REMARK3 备用字段3
     */
    public String getRemark3() {
        return remark3;
    }

    /**
     * 备用字段3
     * @param remark3 备用字段3
     */
    public void setRemark3(String remark3) {
        this.remark3 = remark3 == null ? null : remark3.trim();
    }

    /**
     * 备用字段4
     * @return REMARK4 备用字段4
     */
    public String getRemark4() {
        return remark4;
    }

    /**
     * 备用字段4
     * @param remark4 备用字段4
     */
    public void setRemark4(String remark4) {
        this.remark4 = remark4 == null ? null : remark4.trim();
    }

    /**
     * 备用字段5
     * @return REMARK5 备用字段5
     */
    public String getRemark5() {
        return remark5;
    }

    /**
     * 备用字段5
     * @param remark5 备用字段5
     */
    public void setRemark5(String remark5) {
        this.remark5 = remark5 == null ? null : remark5.trim();
    }
}