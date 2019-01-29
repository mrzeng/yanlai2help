package org.spring.springboot.domain;
/**
 *
 *
 * Created by wangyanlai on 20181225.
 * @author wangyanlai
 */
public class ZwQuestionnaire {
    /**
     * 
     */
    private String id;

    /**
     * 主题，用于汇总某类问卷
     */
    private String subject;

    /**
     * 问卷描述
     */
    private String description;

    /**
     * 问卷名称
     */
    private String name;

    /**
     * 问卷大类型：0个人，1课程，2培训班
     */
    private String type;

    /**
     * 关键词
     */
    private String keyword;

    /**
     * 问卷分类
     */
    private String typeid;

    /**
     * 
     */
    private String createtime;

    /**
     * 
     */
    private String updatetime;

    /**
     * 判断题数量
     */
    private String type0count;

    /**
     * 单选题数量
     */
    private String type1count;

    /**
     * 多选题数量
     */
    private String type2count;

    /**
     * 开放题数量
     */
    private String type3count;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否有效0已发布1未发布2删除
     */
    private String status;

    /**
     * 是否限时:0不限制1限制
     */
    private String isLimitTime;

    /**
     * 限时大小
     */
    private String limitTime;

    /**
     * 是否个人作答次数:0不限制1限制
     */
    private String isLimitPerson;

    /**
     * 个人作答次数
     */
    private String limitPerson;

    /**
     * 是否限制总作答人数:0不限制1限制
     */
    private String isLimitTotal;

    /**
     * 允许作答总人数
     */
    private String limitTotal;

    /**
     * 总调查次数
     */
    private String environment;

    /**
     * 总调查人数
     */
    private String userNum;

    /**
     * 是否分栏：0不分；1分
     */
    private String isSubfield;

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
     * 主题，用于汇总某类问卷
     * @return SUBJECT 主题，用于汇总某类问卷
     */
    public String getSubject() {
        return subject;
    }

    /**
     * 主题，用于汇总某类问卷
     * @param subject 主题，用于汇总某类问卷
     */
    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    /**
     * 问卷描述
     * @return DESCRIPTION 问卷描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 问卷描述
     * @param description 问卷描述
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * 问卷名称
     * @return NAME 问卷名称
     */
    public String getName() {
        return name;
    }

    /**
     * 问卷名称
     * @param name 问卷名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 问卷大类型：0个人，1课程，2培训班
     * @return TYPE 问卷大类型：0个人，1课程，2培训班
     */
    public String getType() {
        return type;
    }

    /**
     * 问卷大类型：0个人，1课程，2培训班
     * @param type 问卷大类型：0个人，1课程，2培训班
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 关键词
     * @return KEYWORD 关键词
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * 关键词
     * @param keyword 关键词
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

    /**
     * 问卷分类
     * @return TYPEID 问卷分类
     */
    public String getTypeid() {
        return typeid;
    }

    /**
     * 问卷分类
     * @param typeid 问卷分类
     */
    public void setTypeid(String typeid) {
        this.typeid = typeid == null ? null : typeid.trim();
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
     * 判断题数量
     * @return TYPE0COUNT 判断题数量
     */
    public String getType0count() {
        return type0count;
    }

    /**
     * 判断题数量
     * @param type0count 判断题数量
     */
    public void setType0count(String type0count) {
        this.type0count = type0count == null ? null : type0count.trim();
    }

    /**
     * 单选题数量
     * @return TYPE1COUNT 单选题数量
     */
    public String getType1count() {
        return type1count;
    }

    /**
     * 单选题数量
     * @param type1count 单选题数量
     */
    public void setType1count(String type1count) {
        this.type1count = type1count == null ? null : type1count.trim();
    }

    /**
     * 多选题数量
     * @return TYPE2COUNT 多选题数量
     */
    public String getType2count() {
        return type2count;
    }

    /**
     * 多选题数量
     * @param type2count 多选题数量
     */
    public void setType2count(String type2count) {
        this.type2count = type2count == null ? null : type2count.trim();
    }

    /**
     * 开放题数量
     * @return TYPE3COUNT 开放题数量
     */
    public String getType3count() {
        return type3count;
    }

    /**
     * 开放题数量
     * @param type3count 开放题数量
     */
    public void setType3count(String type3count) {
        this.type3count = type3count == null ? null : type3count.trim();
    }

    /**
     * 操作人
     * @return OPERATOR 操作人
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 操作人
     * @param operator 操作人
     */
    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    /**
     * 备注
     * @return REMARK 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 是否有效0已发布1未发布2删除
     * @return STATUS 是否有效0已发布1未发布2删除
     */
    public String getStatus() {
        return status;
    }

    /**
     * 是否有效0已发布1未发布2删除
     * @param status 是否有效0已发布1未发布2删除
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 是否限时:0不限制1限制
     * @return IS_LIMIT_TIME 是否限时:0不限制1限制
     */
    public String getIsLimitTime() {
        return isLimitTime;
    }

    /**
     * 是否限时:0不限制1限制
     * @param isLimitTime 是否限时:0不限制1限制
     */
    public void setIsLimitTime(String isLimitTime) {
        this.isLimitTime = isLimitTime == null ? null : isLimitTime.trim();
    }

    /**
     * 限时大小
     * @return LIMIT_TIME 限时大小
     */
    public String getLimitTime() {
        return limitTime;
    }

    /**
     * 限时大小
     * @param limitTime 限时大小
     */
    public void setLimitTime(String limitTime) {
        this.limitTime = limitTime == null ? null : limitTime.trim();
    }

    /**
     * 是否个人作答次数:0不限制1限制
     * @return IS_LIMIT_PERSON 是否个人作答次数:0不限制1限制
     */
    public String getIsLimitPerson() {
        return isLimitPerson;
    }

    /**
     * 是否个人作答次数:0不限制1限制
     * @param isLimitPerson 是否个人作答次数:0不限制1限制
     */
    public void setIsLimitPerson(String isLimitPerson) {
        this.isLimitPerson = isLimitPerson == null ? null : isLimitPerson.trim();
    }

    /**
     * 个人作答次数
     * @return LIMIT_PERSON 个人作答次数
     */
    public String getLimitPerson() {
        return limitPerson;
    }

    /**
     * 个人作答次数
     * @param limitPerson 个人作答次数
     */
    public void setLimitPerson(String limitPerson) {
        this.limitPerson = limitPerson == null ? null : limitPerson.trim();
    }

    /**
     * 是否限制总作答人数:0不限制1限制
     * @return IS_LIMIT_TOTAL 是否限制总作答人数:0不限制1限制
     */
    public String getIsLimitTotal() {
        return isLimitTotal;
    }

    /**
     * 是否限制总作答人数:0不限制1限制
     * @param isLimitTotal 是否限制总作答人数:0不限制1限制
     */
    public void setIsLimitTotal(String isLimitTotal) {
        this.isLimitTotal = isLimitTotal == null ? null : isLimitTotal.trim();
    }

    /**
     * 允许作答总人数
     * @return LIMIT_TOTAL 允许作答总人数
     */
    public String getLimitTotal() {
        return limitTotal;
    }

    /**
     * 允许作答总人数
     * @param limitTotal 允许作答总人数
     */
    public void setLimitTotal(String limitTotal) {
        this.limitTotal = limitTotal == null ? null : limitTotal.trim();
    }

    /**
     * 总调查次数
     * @return ENVIRONMENT 总调查次数
     */
    public String getEnvironment() {
        return environment;
    }

    /**
     * 总调查次数
     * @param environment 总调查次数
     */
    public void setEnvironment(String environment) {
        this.environment = environment == null ? null : environment.trim();
    }

    /**
     * 总调查人数
     * @return USER_NUM 总调查人数
     */
    public String getUserNum() {
        return userNum;
    }

    /**
     * 总调查人数
     * @param userNum 总调查人数
     */
    public void setUserNum(String userNum) {
        this.userNum = userNum == null ? null : userNum.trim();
    }

    /**
     * 是否分栏：0不分；1分
     * @return IS_SUBFIELD 是否分栏：0不分；1分
     */
    public String getIsSubfield() {
        return isSubfield;
    }

    /**
     * 是否分栏：0不分；1分
     * @param isSubfield 是否分栏：0不分；1分
     */
    public void setIsSubfield(String isSubfield) {
        this.isSubfield = isSubfield == null ? null : isSubfield.trim();
    }
}