package org.spring.springboot.domain;

public class HelpQuestionInformation {
    /**
     * 问题id
     */
    private String id;

    /**
     * 问题内容
     */
    private String questionContent;

    /**
     * 问题关键字（使用，号隔开最多5个）
     */
    private String questionKeywords;

    /**
     * 有效标志（0无效 1有效）
     */
    private String status;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 问题类别id
     */
    private String questionTypeId;

    /**
     * 是否发布（0未发布 1已发布）
     */
    private String ifRelease;

    /**
     * 是否是常见问题（0不常见 1常见）
     */
    private String ifCommon;

    /**
     * 可视范围（0平台所有，1提交学员自己）
     */
    private String visualRange;

    /**
     * 点击数量
     */
    private String clickNumber;

    /**
     * 点赞次数
     */
    private String numberPoints;

    /**
     * 拍砖次数
     */
    private String numberBricks;

    /**
     * 更新操作员
     */
    private String creationPer;

    /**
     * 操作用户IP
     */
    private String ip;

    /**
     * 创建日期
     */
    private String creationDt;

    /**
     * 发布时间
     */
    private String releaseTm;

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
     * 问题id
     * @return ID 问题id
     */
    public String getId() {
        return id;
    }

    /**
     * 问题id
     * @param id 问题id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 问题内容
     * @return QUESTION_CONTENT 问题内容
     */
    public String getQuestionContent() {
        return questionContent;
    }

    /**
     * 问题内容
     * @param questionContent 问题内容
     */
    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent == null ? null : questionContent.trim();
    }

    /**
     * 问题关键字（使用，号隔开最多5个）
     * @return QUESTION_KEYWORDS 问题关键字（使用，号隔开最多5个）
     */
    public String getQuestionKeywords() {
        return questionKeywords;
    }

    /**
     * 问题关键字（使用，号隔开最多5个）
     * @param questionKeywords 问题关键字（使用，号隔开最多5个）
     */
    public void setQuestionKeywords(String questionKeywords) {
        this.questionKeywords = questionKeywords == null ? null : questionKeywords.trim();
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
     * 问题类别id
     * @return QUESTION_TYPE_ID 问题类别id
     */
    public String getQuestionTypeId() {
        return questionTypeId;
    }

    /**
     * 问题类别id
     * @param questionTypeId 问题类别id
     */
    public void setQuestionTypeId(String questionTypeId) {
        this.questionTypeId = questionTypeId == null ? null : questionTypeId.trim();
    }

    /**
     * 是否发布（0未发布 1已发布）
     * @return IF_RELEASE 是否发布（0未发布 1已发布）
     */
    public String getIfRelease() {
        return ifRelease;
    }

    /**
     * 是否发布（0未发布 1已发布）
     * @param ifRelease 是否发布（0未发布 1已发布）
     */
    public void setIfRelease(String ifRelease) {
        this.ifRelease = ifRelease == null ? null : ifRelease.trim();
    }

    /**
     * 是否是常见问题（0不常见 1常见）
     * @return IF_COMMON 是否是常见问题（0不常见 1常见）
     */
    public String getIfCommon() {
        return ifCommon;
    }

    /**
     * 是否是常见问题（0不常见 1常见）
     * @param ifCommon 是否是常见问题（0不常见 1常见）
     */
    public void setIfCommon(String ifCommon) {
        this.ifCommon = ifCommon == null ? null : ifCommon.trim();
    }

    /**
     * 可视范围（0平台所有，1提交学员自己）
     * @return VISUAL_RANGE 可视范围（0平台所有，1提交学员自己）
     */
    public String getVisualRange() {
        return visualRange;
    }

    /**
     * 可视范围（0平台所有，1提交学员自己）
     * @param visualRange 可视范围（0平台所有，1提交学员自己）
     */
    public void setVisualRange(String visualRange) {
        this.visualRange = visualRange == null ? null : visualRange.trim();
    }

    /**
     * 点击数量
     * @return CLICK_NUMBER 点击数量
     */
    public String getClickNumber() {
        return clickNumber;
    }

    /**
     * 点击数量
     * @param clickNumber 点击数量
     */
    public void setClickNumber(String clickNumber) {
        this.clickNumber = clickNumber == null ? null : clickNumber.trim();
    }

    /**
     * 点赞次数
     * @return NUMBER_POINTS 点赞次数
     */
    public String getNumberPoints() {
        return numberPoints;
    }

    /**
     * 点赞次数
     * @param numberPoints 点赞次数
     */
    public void setNumberPoints(String numberPoints) {
        this.numberPoints = numberPoints == null ? null : numberPoints.trim();
    }

    /**
     * 拍砖次数
     * @return NUMBER_BRICKS 拍砖次数
     */
    public String getNumberBricks() {
        return numberBricks;
    }

    /**
     * 拍砖次数
     * @param numberBricks 拍砖次数
     */
    public void setNumberBricks(String numberBricks) {
        this.numberBricks = numberBricks == null ? null : numberBricks.trim();
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
     * 发布时间
     * @return RELEASE_TM 发布时间
     */
    public String getReleaseTm() {
        return releaseTm;
    }

    /**
     * 发布时间
     * @param releaseTm 发布时间
     */
    public void setReleaseTm(String releaseTm) {
        this.releaseTm = releaseTm == null ? null : releaseTm.trim();
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