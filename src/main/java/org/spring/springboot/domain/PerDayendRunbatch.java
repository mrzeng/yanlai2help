package org.spring.springboot.domain;
/**
 *
 *
 * Created by wangyanlai on 20181225.
 * @author wangyanlai
 */
public class PerDayendRunbatch {
    /**
     * 流水id 主键
     */
    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 批次类型 日终过期积分清理:00014
     */
    private String batchType;

    /**
     * 是否成功(0成功,1失败)
     */
    private String isSucc;

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
     * 批次类型 日终过期积分清理:00014
     * @return BATCH_TYPE 批次类型 日终过期积分清理:00014
     */
    public String getBatchType() {
        return batchType;
    }

    /**
     * 批次类型 日终过期积分清理:00014
     * @param batchType 批次类型 日终过期积分清理:00014
     */
    public void setBatchType(String batchType) {
        this.batchType = batchType == null ? null : batchType.trim();
    }

    /**
     * 是否成功(0成功,1失败)
     * @return IS_SUCC 是否成功(0成功,1失败)
     */
    public String getIsSucc() {
        return isSucc;
    }

    /**
     * 是否成功(0成功,1失败)
     * @param isSucc 是否成功(0成功,1失败)
     */
    public void setIsSucc(String isSucc) {
        this.isSucc = isSucc == null ? null : isSucc.trim();
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