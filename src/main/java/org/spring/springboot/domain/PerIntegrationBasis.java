package org.spring.springboot.domain;
/**
 *
 *
 * Created by wangyanlai on 20181225.
 * @author wangyanlai
 */
public class PerIntegrationBasis {
    /**
     * 个人id  主键
     */
    private String id;

    /**
     * 当前积分值
     */
    private String score;

    /**
     * 原始积分
     */
    private String originalScore;

    /**
     * 上次积分
     */
    private String lastTimeScore;

    /**
     * 上日积分
     */
    private String yesterdayScore;

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
     * 个人id
     * @return ID 个人id
     */
    public String getId() {
        return id;
    }

    /**
     * 个人id
     * @param id 个人id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 当前积分值
     * @return SCORE 当前积分值
     */
    public String getScore() {
        return score;
    }

    /**
     * 当前积分值
     * @param score 当前积分值
     */
    public void setScore(String score) {
        this.score = score == null ? null : score.trim();
    }

    /**
     * 原始积分
     * @return ORIGINAL_SCORE 原始积分
     */
    public String getOriginalScore() {
        return originalScore;
    }

    /**
     * 原始积分
     * @param originalScore 原始积分
     */
    public void setOriginalScore(String originalScore) {
        this.originalScore = originalScore == null ? null : originalScore.trim();
    }

    /**
     * 上次积分
     * @return LAST_TIME_SCORE 上次积分
     */
    public String getLastTimeScore() {
        return lastTimeScore;
    }

    /**
     * 上次积分
     * @param lastTimeScore 上次积分
     */
    public void setLastTimeScore(String lastTimeScore) {
        this.lastTimeScore = lastTimeScore == null ? null : lastTimeScore.trim();
    }

    /**
     * 上日积分
     * @return YESTERDAY_SCORE 上日积分
     */
    public String getYesterdayScore() {
        return yesterdayScore;
    }

    /**
     * 上日积分
     * @param yesterdayScore 上日积分
     */
    public void setYesterdayScore(String yesterdayScore) {
        this.yesterdayScore = yesterdayScore == null ? null : yesterdayScore.trim();
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