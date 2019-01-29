package org.spring.springboot.domain;
/**
 *
 *
 * Created by wangyanlai on 20181225.
 * @author wangyanlai
 */
public class PerCourseIntegration {
    /**
     * 课程id
     */
    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 当前积分分值
     */
    private String score;

    /**
     * 是否分享（0未分享，1已分享）
     */
    private String ifShare;

    /**
     * 是否学完（0未学完，1已学完）
     */
    private String ifLearn;

    /**
     * 是否评论（0未评论，1已评论）
     */
    private String ifComment;

    /**
     * 是否评价（0未评价，1已评价）
     */
    private String ifAssess;

    /**
     * 评论次数
     */
    private String commentNum;

    /**
     * 学习时间
     */
    private String learningTm;

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
     * 课程id
     * @return ID 课程id
     */
    public String getId() {
        return id;
    }

    /**
     * 课程id
     * @param id 课程id
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
     * 当前积分分值
     * @return SCORE 当前积分分值
     */
    public String getScore() {
        return score;
    }

    /**
     * 当前积分分值
     * @param score 当前积分分值
     */
    public void setScore(String score) {
        this.score = score == null ? null : score.trim();
    }

    /**
     * 是否分享（0未分享，1已分享）
     * @return IF_SHARE 是否分享（0未分享，1已分享）
     */
    public String getIfShare() {
        return ifShare;
    }

    /**
     * 是否分享（0未分享，1已分享）
     * @param ifShare 是否分享（0未分享，1已分享）
     */
    public void setIfShare(String ifShare) {
        this.ifShare = ifShare == null ? null : ifShare.trim();
    }

    /**
     * 是否学完（0未学完，1已学完）
     * @return IF_LEARN 是否学完（0未学完，1已学完）
     */
    public String getIfLearn() {
        return ifLearn;
    }

    /**
     * 是否学完（0未学完，1已学完）
     * @param ifLearn 是否学完（0未学完，1已学完）
     */
    public void setIfLearn(String ifLearn) {
        this.ifLearn = ifLearn == null ? null : ifLearn.trim();
    }

    /**
     * 是否评论（0未评论，1已评论）
     * @return IF_COMMENT 是否评论（0未评论，1已评论）
     */
    public String getIfComment() {
        return ifComment;
    }

    /**
     * 是否评论（0未评论，1已评论）
     * @param ifComment 是否评论（0未评论，1已评论）
     */
    public void setIfComment(String ifComment) {
        this.ifComment = ifComment == null ? null : ifComment.trim();
    }

    /**
     * 是否评价（0未评价，1已评价）
     * @return IF_ASSESS 是否评价（0未评价，1已评价）
     */
    public String getIfAssess() {
        return ifAssess;
    }

    /**
     * 是否评价（0未评价，1已评价）
     * @param ifAssess 是否评价（0未评价，1已评价）
     */
    public void setIfAssess(String ifAssess) {
        this.ifAssess = ifAssess == null ? null : ifAssess.trim();
    }

    /**
     * 评论次数
     * @return COMMENT_NUM 评论次数
     */
    public String getCommentNum() {
        return commentNum;
    }

    /**
     * 评论次数
     * @param commentNum 评论次数
     */
    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum == null ? null : commentNum.trim();
    }

    /**
     * 学习时间
     * @return LEARNING_TM 学习时间
     */
    public String getLearningTm() {
        return learningTm;
    }

    /**
     * 学习时间
     * @param learningTm 学习时间
     */
    public void setLearningTm(String learningTm) {
        this.learningTm = learningTm == null ? null : learningTm.trim();
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