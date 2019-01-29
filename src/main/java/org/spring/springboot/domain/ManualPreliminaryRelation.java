package org.spring.springboot.domain;

public class ManualPreliminaryRelation {
    /**
     * 手册id
     */
    private String id;

    /**
     * 入门id
     */
    private String preliminaryId;

    /**
     * 手册路径
     */
    private String manualPath;

    /**
     * 手册格式
     */
    private String manualType;

    /**
     * 手册大小(M)
     */
    private String manualSize;

    /**
     * 有效标志（0无效 1有效）
     */
    private String status;

    /**
     * 是否发布（0未发布 1已发布）
     */
    private String ifRelease;

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
     * 发布时间
     */
    private String releaseTm;

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
     * 手册id
     * @return ID 手册id
     */
    public String getId() {
        return id;
    }

    /**
     * 手册id
     * @param id 手册id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 入门id
     * @return PRELIMINARY_ID 入门id
     */
    public String getPreliminaryId() {
        return preliminaryId;
    }

    /**
     * 入门id
     * @param preliminaryId 入门id
     */
    public void setPreliminaryId(String preliminaryId) {
        this.preliminaryId = preliminaryId == null ? null : preliminaryId.trim();
    }

    /**
     * 手册路径
     * @return MANUAL_PATH 手册路径
     */
    public String getManualPath() {
        return manualPath;
    }

    /**
     * 手册路径
     * @param manualPath 手册路径
     */
    public void setManualPath(String manualPath) {
        this.manualPath = manualPath == null ? null : manualPath.trim();
    }

    /**
     * 手册格式
     * @return MANUAL_TYPE 手册格式
     */
    public String getManualType() {
        return manualType;
    }

    /**
     * 手册格式
     * @param manualType 手册格式
     */
    public void setManualType(String manualType) {
        this.manualType = manualType == null ? null : manualType.trim();
    }

    /**
     * 手册大小(M)
     * @return MANUAL_SIZE 手册大小(M)
     */
    public String getManualSize() {
        return manualSize;
    }

    /**
     * 手册大小(M)
     * @param manualSize 手册大小(M)
     */
    public void setManualSize(String manualSize) {
        this.manualSize = manualSize == null ? null : manualSize.trim();
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