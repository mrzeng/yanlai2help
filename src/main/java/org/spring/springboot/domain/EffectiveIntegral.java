package org.spring.springboot.domain;

/*
 * @author wangyanlai
 * @date 2016/10/31
 */

public class EffectiveIntegral {
    /**
     * 流水id
     */
    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 分值
     */
    private String score;

    /**
     * 是否有效(0有效,1无效)
     */
    private String isEnabled;

    /**
     * 有效截止日期
     */
    private String endDt;

    /**
     * 有效期天数
     */
    private String days;

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
     *
     * @return ID 流水id
     */
    public String getId() {
        return id;
    }

    /**
     * 流水id
     *
     * @param id 流水id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 用户id
     *
     * @return USER_ID 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 用户id
     *
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 分值
     *
     * @return SCORE 分值
     */
    public String getScore() {
        return score;
    }

    /**
     * 分值
     *
     * @param score 分值
     */
    public void setScore(String score) {
        this.score = score == null ? null : score.trim();
    }

    /**
     * 是否有效(0有效,1无效)
     *
     * @return IS_ENABLED 是否有效(0有效,1无效)
     */
    public String getIsEnabled() {
        return isEnabled;
    }

    /**
     * 是否有效(0有效,1无效)
     *
     * @param isEnabled 是否有效(0有效,1无效)
     */
    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled == null ? null : isEnabled.trim();
    }

    /**
     * 有效截止日期
     *
     * @return END_DT 有效截止日期
     */
    public String getEndDt() {
        return endDt;
    }

    /**
     * 有效截止日期
     *
     * @param endDt 有效截止日期
     */
    public void setEndDt(String endDt) {
        this.endDt = endDt == null ? null : endDt.trim();
    }

    /**
     * 有效期天数
     *
     * @return DAYS 有效期天数
     */
    public String getDays() {
        return days;
    }

    /**
     * 有效期天数
     *
     * @param days 有效期天数
     */
    public void setDays(String days) {
        this.days = days == null ? null : days.trim();
    }

    /**
     * 创建日期
     *
     * @return CREATION_DT 创建日期
     */
    public String getCreationDt() {
        return creationDt;
    }

    /**
     * 创建日期
     *
     * @param creationDt 创建日期
     */
    public void setCreationDt(String creationDt) {
        this.creationDt = creationDt == null ? null : creationDt.trim();
    }

    /**
     * 创建时间
     *
     * @return CREATION_TM 创建时间
     */
    public String getCreationTm() {
        return creationTm;
    }

    /**
     * 创建时间
     *
     * @param creationTm 创建时间
     */
    public void setCreationTm(String creationTm) {
        this.creationTm = creationTm == null ? null : creationTm.trim();
    }

    /**
     * 更新时间
     *
     * @return UPDATE_TM 更新时间
     */
    public String getUpdateTm() {
        return updateTm;
    }

    /**
     * 更新时间
     *
     * @param updateTm 更新时间
     */
    public void setUpdateTm(String updateTm) {
        this.updateTm = updateTm == null ? null : updateTm.trim();
    }

    /**
     * 时间戳
     *
     * @return TM_SMP 时间戳
     */
    public String getTmSmp() {
        return tmSmp;
    }

    /**
     * 时间戳
     *
     * @param tmSmp 时间戳
     */
    public void setTmSmp(String tmSmp) {
        this.tmSmp = tmSmp == null ? null : tmSmp.trim();
    }

    /**
     * 备用字段1
     *
     * @return REMARK1 备用字段1
     */
    public String getRemark1() {
        return remark1;
    }

    /**
     * 备用字段1
     *
     * @param remark1 备用字段1
     */
    public void setRemark1(String remark1) {
        this.remark1 = remark1 == null ? null : remark1.trim();
    }

    /**
     * 备用字段2
     *
     * @return REMARK2 备用字段2
     */
    public String getRemark2() {
        return remark2;
    }

    /**
     * 备用字段2
     *
     * @param remark2 备用字段2
     */
    public void setRemark2(String remark2) {
        this.remark2 = remark2 == null ? null : remark2.trim();
    }

    /**
     * 备用字段3
     *
     * @return REMARK3 备用字段3
     */
    public String getRemark3() {
        return remark3;
    }

    /**
     * 备用字段3
     *
     * @param remark3 备用字段3
     */
    public void setRemark3(String remark3) {
        this.remark3 = remark3 == null ? null : remark3.trim();
    }

    /**
     * 备用字段4
     *
     * @return REMARK4 备用字段4
     */
    public String getRemark4() {
        return remark4;
    }

    /**
     * 备用字段4
     *
     * @param remark4 备用字段4
     */
    public void setRemark4(String remark4) {
        this.remark4 = remark4 == null ? null : remark4.trim();
    }

    /**
     * 备用字段5
     *
     * @return REMARK5 备用字段5
     */
    public String getRemark5() {
        return remark5;
    }

    /**
     * 备用字段5
     *
     * @param remark5 备用字段5
     */
    public void setRemark5(String remark5) {
        this.remark5 = remark5 == null ? null : remark5.trim();
    }
}