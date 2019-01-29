package org.spring.springboot.domain;
/**
 *
 *
 * Created by wangyanlai on 20181225.
 * @author wangyanlai
 */
public class PerIntegrationConfigure {
    /**
     * 规则id 主键
     */
    private String id;

    /**
     * 是否有效（0有效1无效2待审核）
     */
    private String status;

    /**
     * 积分变更规则类型   用户登陆00001，课程学习00002，课程评论00003（备注1值为最大评价次数）,恶意评论00004，问卷调查00005,分享课程00006,精华课程00007（备注1值为课程类型），问题点赞"00008"
            ，最大兑换人数"00009"，个人最大兑换金额"00010"，奖励金额过期时间（分值为月数）"00011"，课程评价分享"00012"，培训班分享"00013"
     */
    private String scoreType;

    /**
     * 更新操作员
     */
    private String creationPer;

    /**
     * 分数值
     */
    private String score;

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
     * 规则id
     * @return ID 规则id
     */
    public String getId() {
        return id;
    }

    /**
     * 规则id
     * @param id 规则id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 是否有效（0有效1无效2待审核）
     * @return STATUS 是否有效（0有效1无效2待审核）
     */
    public String getStatus() {
        return status;
    }

    /**
     * 是否有效（0有效1无效2待审核）
     * @param status 是否有效（0有效1无效2待审核）
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 积分变更规则类型   用户登陆00001，课程学习00002，课程评论00003（备注1值为最大评价次数）,恶意评论00004，问卷调查00005,分享课程00006,精华课程00007（备注1值为课程类型），问题点赞"00008"
            ，最大兑换人数"00009"，个人最大兑换金额"00010"，奖励金额过期时间（分值为月数）"00011"，课程评价分享"00012"，培训班分享"00013"
     * @return SCORE_TYPE 积分变更规则类型   用户登陆00001，课程学习00002，课程评论00003（备注1值为最大评价次数）,恶意评论00004，问卷调查00005,分享课程00006,精华课程00007（备注1值为课程类型），问题点赞"00008"
            ，最大兑换人数"00009"，个人最大兑换金额"00010"，奖励金额过期时间（分值为月数）"00011"，课程评价分享"00012"，培训班分享"00013"
     */
    public String getScoreType() {
        return scoreType;
    }

    /**
     * 积分变更规则类型   用户登陆00001，课程学习00002，课程评论00003（备注1值为最大评价次数）,恶意评论00004，问卷调查00005,分享课程00006,精华课程00007（备注1值为课程类型），问题点赞"00008"
            ，最大兑换人数"00009"，个人最大兑换金额"00010"，奖励金额过期时间（分值为月数）"00011"，课程评价分享"00012"，培训班分享"00013"
     * @param scoreType 积分变更规则类型   用户登陆00001，课程学习00002，课程评论00003（备注1值为最大评价次数）,恶意评论00004，问卷调查00005,分享课程00006,精华课程00007（备注1值为课程类型），问题点赞"00008"
            ，最大兑换人数"00009"，个人最大兑换金额"00010"，奖励金额过期时间（分值为月数）"00011"，课程评价分享"00012"，培训班分享"00013"
     */
    public void setScoreType(String scoreType) {
        this.scoreType = scoreType == null ? null : scoreType.trim();
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
     * 分数值
     * @return SCORE 分数值
     */
    public String getScore() {
        return score;
    }

    /**
     * 分数值
     * @param score 分数值
     */
    public void setScore(String score) {
        this.score = score == null ? null : score.trim();
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