package org.spring.springboot.domain;

public class HelpQuestionReplyInformation {
    /**
     * 回复id
     */
    private String id;

    /**
     * 问题id
     */
    private String questionId;

    /**
     * 回复内容
     */
    private String replyContent;

    /**
     * 有效标志（0无效 1有效）
     */
    private String status;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 回复人员IP
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
     * 回复id
     * @return ID 回复id
     */
    public String getId() {
        return id;
    }

    /**
     * 回复id
     * @param id 回复id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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
     * 回复内容
     * @return REPLY_CONTENT 回复内容
     */
    public String getReplyContent() {
        return replyContent;
    }

    /**
     * 回复内容
     * @param replyContent 回复内容
     */
    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent == null ? null : replyContent.trim();
    }

    /**
     * 有效标志（0无效 1有效）
     * @return STATUS 有效标志（0无效 1有效）
     */
    public String getStatus() {
        return status;
    }

    /**
     * 有效标志（0无效 1有效）
     * @param status 有效标志（0无效 1有效）
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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
     * 回复人员IP
     * @return IP 回复人员IP
     */
    public String getIp() {
        return ip;
    }

    /**
     * 回复人员IP
     * @param ip 回复人员IP
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