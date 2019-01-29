
package org.spring.springboot.service.impl;

import base.zw.controller.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.cache.TransactionalCacheManager;
import org.slf4j.Logger;
import org.spring.springboot.dao.DaoSupport;
import org.spring.springboot.domain.*;
import org.spring.springboot.service.IntegrationQryService;
import org.spring.springboot.service.IntegrationUpdService;
import org.spring.springboot.zw.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.apache.commons.lang3.StringUtils.isNumeric;
import static org.apache.shiro.web.util.WebUtils.getRequest;
import static org.spring.springboot.zw.util.Const.PAGE;
import static org.spring.springboot.zw.util.DESUtil.decryptBasedDes;
import static org.spring.springboot.zw.util.DESUtil.encryptBasedDes;
import static org.spring.springboot.zw.util.DateUtil.getAfterDayDate;

/**
 * Title: APPServiceImpl Description:
 *
 * @author wangyanlai
 * @version 2019年1月26日 上午8:31:56
 */

@Service
@Transactional()
public class IntegrationUpdServiceImpl implements IntegrationUpdService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;
    @Autowired
    private DataSourceTransactionManager transactionManager;
    private String updTypeField = "updtype";
    private String userIdField = "userid";

    //变更积分配置表信息
    @Override
    public String updConfigure(Map<String, Object> reqMap, Logger logger, HttpServletRequest request) {
        ReturnData map = new ReturnData();
        //初始化事务参数配置
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //隔离级别为 PROPAGATION_NESTED--如果当前存在事务，则在嵌套事务内执行。
        //如果当前没有事务，则进行与PROPAGATION_REQUIRED(新创事务)类似的操作。
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
        TransactionStatus transStatus = transactionManager.getTransaction(def);

        PerIntegrationConfigure perIntegrationConfigure = new PerIntegrationConfigure();
        Object status = reqMap.get("status");
        status = Const.STATUS_YES;
        if (null != status) {
            if (!StringUtils.isBlank(((Object) reqMap.get("status")).toString())) {
                perIntegrationConfigure.setStatus(((Object) reqMap.get("status")).toString());
            }
        }
        String scoreTyle = ((Object) reqMap.get("scoretype")).toString();
        if (StringUtils.isBlank(scoreTyle)) {
            //积分类型不允许为空
            map.setCode(Const.FAILURE_CODE);
            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            logger.info("queryScore---jsonObject.toString()---" + jsonReturn + "---");
            return jsonReturn;
        }
        Object remark1 = reqMap.get("remark1");
        if (null != remark1) {
            perIntegrationConfigure.setRemark1(((Object) reqMap.get("remark1")).toString());
        } else {
            perIntegrationConfigure.setRemark1("");
        }
        //id在前文已经判断过是否为空
        perIntegrationConfigure.setUpdateTm(DateUtil.getTime());
        Object id = reqMap.get("id");
        if (null != id) {
            try {
                perIntegrationConfigure.setId(DESUtil.aesDecrypt(((Object) reqMap.get("id")).toString(),Const.ALLENCRYPTCODE));
            } catch (Exception e) {
                logger.info("验证id失败");
                //事务回滚
                logger.info("=====================事务回滚======================");
                transactionManager.rollback(transStatus);
                //json格式没有被打乱不需要格式化
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("queryScore---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
        } else {
            perIntegrationConfigure.setId("");
        }
        Object score = reqMap.get("score");
        if (null != score) {
            perIntegrationConfigure.setScore(((Object) reqMap.get("score")).toString());
        } else {
            perIntegrationConfigure.setScore("0");
        }
        perIntegrationConfigure.setTmSmp(DateUtil.getTime());
        logger.info("变更积分配置表信息开始");
        Object updNum = null;
        map.setCode(Const.FAILURE_CODE);
        try {
            updNum = dao.update("PerIntegrationConfigureMapper.updateByPrimaryKeySelective", perIntegrationConfigure);
            if (null == updNum){
                //json格式没有被打乱不需要格式化
                logger.info("更新积分配置信息表失败");
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("queryScore---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
        } catch (Exception e) {
            logger.info("更新积分配置信息表失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(transStatus);
            //json格式没有被打乱不需要格式化
            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            logger.info("queryScore---jsonObject.toString()---" + jsonReturn + "---");
            return jsonReturn;
        }
        logger.info("更新积分配置信息表成功");
        map.setCode(Const.SUCCESS_CODE);
        //json格式没有被打乱不需要格式化
        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        logger.info("updConfig---jsonObject.toString()---" + jsonReturn + "---");
        return jsonReturn;
    }

    //新增积分配置表信息
    @Override
    public String addConfigure(Map<String, Object> reqMap, Logger logger, HttpServletRequest request) {
        ReturnData map = new ReturnData();
        //初始化事务参数配置
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //隔离级别为 PROPAGATION_NESTED--如果当前存在事务，则在嵌套事务内执行。
        //如果当前没有事务，则进行与PROPAGATION_REQUIRED(新创事务)类似的操作。
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
        TransactionStatus transStatus = transactionManager.getTransaction(def);
        PerIntegrationConfigure perIntegrationConfigure = new PerIntegrationConfigure();
        Object status = reqMap.get("status");
        perIntegrationConfigure.setStatus(Const.STATUS_YES);
        if (null != status) {
            if (!StringUtils.isBlank(((Object) reqMap.get("status")).toString())) {
                perIntegrationConfigure.setStatus(((Object) reqMap.get("status")).toString());
            }
        }
        String scoreType = ((Object) reqMap.get("scoretype")).toString();
        if (StringUtils.isBlank(scoreType)) {
            //积分类型不允许为空
            map.setCode(Const.FAILURE_CODE);
            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            logger.info("queryScore---jsonObject.toString()---" + jsonReturn + "---");
            return jsonReturn;
        }
        perIntegrationConfigure.setScoreType(scoreType);
        Object remark1 = reqMap.get("remark1");
        if (null != remark1) {
            perIntegrationConfigure.setRemark1(((Object) reqMap.get("remark1")).toString());
        } else {
            perIntegrationConfigure.setRemark1("");
        }
        //id在前文已经判断过是否为空
        perIntegrationConfigure.setUpdateTm(DateUtil.getTime());
        Object score = reqMap.get("score");
        if (null != score) {
            perIntegrationConfigure.setScore(((Object) reqMap.get("score")).toString());
        } else {
            perIntegrationConfigure.setScore("0");
        }
        perIntegrationConfigure.setTmSmp(DateUtil.getTime());
        Object creationPer = reqMap.get("creationper");
        if (null != creationPer) {
            perIntegrationConfigure.setCreationPer(((Object) reqMap.get("creationper")).toString());
        } else {
            perIntegrationConfigure.setCreationPer("");
        }
        perIntegrationConfigure.setCreationPer("SYSTEM_PER");
        perIntegrationConfigure.setCreationTm(DateUtil.getTime());
        perIntegrationConfigure.setId(UuidUtil.get32UUID());
        logger.info("变更积分配置表信息开始");
        Object updNum = null;
        map.setCode(Const.FAILURE_CODE);
        try {
            updNum = dao.update("PerIntegrationConfigureMapper.insertSelective", perIntegrationConfigure);
            if (null == updNum){
                //json格式没有被打乱不需要格式化
                logger.info("新增积分配置信息表失败");
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("queryScore---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
        } catch (Exception e) {
            logger.info("新增积分配置信息表失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(transStatus);
            //json格式没有被打乱不需要格式化
            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            logger.info("queryScore---jsonObject.toString()---" + jsonReturn + "---");
            return jsonReturn;
        }
        logger.info("新增积分配置信息表成功");
        map.setCode(Const.SUCCESS_CODE);
        //json格式没有被打乱不需要格式化
        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        logger.info("updConfig---jsonObject.toString()---" + jsonReturn + "---");
        return jsonReturn;
    }

    //删除积分配置表信息
    @Override
    public String deleteConfigure(Map<String, Object> reqMap, Logger logger, HttpServletRequest request) {
        ReturnData map = new ReturnData();
        //初始化事务参数配置
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //隔离级别为 PROPAGATION_NESTED--如果当前存在事务，则在嵌套事务内执行。
        //如果当前没有事务，则进行与PROPAGATION_REQUIRED(新创事务)类似的操作。
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
        TransactionStatus transStatus = transactionManager.getTransaction(def);
        PerIntegrationConfigure perIntegrationConfigure = new PerIntegrationConfigure();
        map.setCode(Const.EMPTY_CODE);
        Object id = reqMap.get("id");
        if (null != id) {
            try {

//                perIntegrationConfigure.setId(DESUtil.AESDncode(((Object) reqMap.get("id")).toString(),code));
                perIntegrationConfigure.setId(DESUtil.aesDecrypt(((Object) reqMap.get("id")).toString(),
                        Const.ALLENCRYPTCODE));
            }catch (Exception e){
                logger.info("验证id失败");
                //事务回滚
                logger.info("=====================事务回滚======================");
                transactionManager.rollback(transStatus);
                //json格式没有被打乱不需要格式化
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("queryScore---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }

        }
        logger.info("删除积分配置表相应数据开始");
        Object updNum = null;
        map.setCode(Const.FAILURE_CODE);
        try {
            updNum = dao.update("PerIntegrationConfigureMapper.deleteByPrimaryKey", perIntegrationConfigure);
            if (null == updNum||"0".equals(updNum.toString())){
                //json格式没有被打乱不需要格式化
                logger.info("删除积分配置信息表失败");
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("queryScore---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
        } catch (Exception e) {
            logger.info("删除积分配置信息表失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(transStatus);
            //json格式没有被打乱不需要格式化
            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            logger.info("queryScore---jsonObject.toString()---" + jsonReturn + "---");
            return jsonReturn;
        }
        logger.info("删除积分配置信息表成功");
        map.setCode(Const.SUCCESS_CODE);
        //json格式没有被打乱不需要格式化
        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        logger.info("updConfig---jsonObject.toString()---" + jsonReturn + "---");
        return jsonReturn;
    }


    /**
     * Title: Const 变更个人积分信息统一入口:
     *
     * @author wangyanlai
     * @version 2018年12月26日 下午4:29:44
     */
    @Override
    public String updIntegration(Map<String, Object> reqMap, Logger logger, HttpServletRequest request) {
        String nowDateTime = DateUtil.getTime();
        String nowDate = DateUtil.getDay();
        //初始化事务参数配置
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //隔离级别为 PROPAGATION_NESTED--如果当前存在事务，则在嵌套事务内执行。
        //如果当前没有事务，则进行与PROPAGATION_REQUIRED(新创事务)类似的操作。
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
        TransactionStatus status = transactionManager.getTransaction(def);
        //返回json数据初始化
        ReturnData map = new ReturnData();
        //初始化返回码为错误
        map.setCode(Const.FAILURE_CODE);
        //允许进行下面几种类型的积分信息进行变更
        String[] updType = {Const.LOGIN_UPDSCORE, Const.LEARN_UPDSCORE, Const.COMMENT_UPDSCORE,
                Const.HOSTILITY_COMMENT_UPDSCORE, Const.INVESTIGATION_UPDSCORE, Const.SHARE_UPDSCORE,
                Const.TASK_UPDSCORE, Const.APPRECIATE_UPDSCORE, Const.MAX_EXCHANGE_PER,
                Const.ACTUAL_EFFECT_TIME, Const.EXCHANGE_UPDSCORE, Const.TRAINING_UPDSCORE};
        //初始化接外部送进来的值为对象，便于进一步转换，避免因强转造成错误
        Object updTypeObject = null;
        Object userIdObject = null;
        try {
            //判断字段是否为空，判断积分变更必须的变更类型是否为空
            //提前预置错误码，用于必输项校验
            map.setCode(Const.IRREGULAR_PARAMETERS);
            if (!StringUtil.isEmpty(reqMap.get(updTypeField)) || !StringUtil.isEmpty(reqMap.get(userIdField))) {
                logger.info("变更条件不符合规范---------");
                logger.info("queryScore---jsonObject.toString()---" + JSON.toJSONString(map) + "---");
                return JSON.toJSONString(map);
            } else {
                updTypeObject = reqMap.get(updTypeField);
                userIdObject = reqMap.get(userIdField);
                //判断送进来的变更类型字段是否合规
                if (!Arrays.asList(updType).contains(decryptBasedDes(updTypeObject.toString()))) {
                    logger.info("变更条件不符合规范----- 积分变更类型字段不合规----");
                    logger.info("queryScore---jsonObject.toString()---" + JSON.toJSONString(map) + "---");
                    return JSON.toJSONString(map);
                }
            }
            //由于判定条件太多，统一使用一个字段做条件是否为空验证
            String queryCriteria = null;
            //下面为每种变更类型的必输项校验
            if ((Const.LEARN_UPDSCORE.equals(decryptBasedDes(updTypeObject.toString())) || Const.COMMENT_UPDSCORE.equals(decryptBasedDes(updTypeObject.toString())))
                    && !StringUtil.isEmpty(reqMap.get("courseid"))) {
                //课程学习和课程评论时，课程id必传
                logger.info("课程id必传");
                queryCriteria = "变更条件不符合规范---------课程id必传";
            } else if (Const.INVESTIGATION_UPDSCORE.equals(decryptBasedDes(updTypeObject.toString())) && !StringUtil.isEmpty(reqMap.get("questionnaireid"))) {
                //课程学习和课程评论时，课程id必传
                logger.info("问卷调查id必传");
                queryCriteria = "变更条件不符合规范---------问卷调查id必传";
            } else if (Const.APPRECIATE_UPDSCORE.equals(decryptBasedDes(updTypeObject.toString())) && !StringUtil.isEmpty(reqMap.get("questionid"))) {
                //提问问题被点赞超过指定次数，问题id必传
                logger.info("问题id必传");
                queryCriteria = "变更条件不符合规范---------问题id必传";
            } else if (Const.TASK_UPDSCORE.equals(decryptBasedDes(updTypeObject.toString())) && !StringUtil.isEmpty(reqMap.get("taskid"))) {
                //作业被认定为精华或者取消精华，作业id必传
                logger.info("作业id必传");
                queryCriteria = "变更条件不符合规范---------作业id必传";
            } else if (Const.TRAINING_UPDSCORE.equals(decryptBasedDes(updTypeObject.toString())) && !StringUtil.isEmpty(reqMap.get("trainingid"))) {
                //课程学习和课程评论时，课程id必传
                logger.info("培训班id必传");
                queryCriteria = "变更条件不符合规范---------培训班id必传";
            } else if (Const.EXCHANGE_UPDSCORE.equals(decryptBasedDes(updTypeObject.toString())) && !StringUtil.isEmpty(reqMap.get("exchangenum"))) {
                // 兑换积分
                logger.info("兑换积分数额必传");
                queryCriteria = "变更条件不符合规范---------兑换积分数额必传";
            }
            //queryCriteria为空说明没有异常信息,反之如果有值,则代表必输项未输
            if (!StringUtils.isBlank(queryCriteria)) {
                logger.info("变更积分传入条件不符合规范---------");
                logger.info("queryScore---jsonObject.toString()---" + queryCriteria + "---");
                return JSON.toJSONString(map);
            }
            //===========================用户积分信息变更接口必输信息校验完毕================================
            //接下来是具体的变更逻辑，依据变更类型分别判断是否是个人课程登陆变更积分,个人课程学习变更积分，课程评论获取积分等等不同的变更
            PageData data = new PageData();
            List<PageData> contentlist = new ArrayList<PageData>();
            //初始变更积分为0
            String updScore = "0";
            if (Const.LOGIN_UPDSCORE.equals(decryptBasedDes(updTypeObject.toString()))) {
                //用户登录--变更积分分支
                ReturnData returnmap = updTypeLogin(nowDateTime, nowDate, logger, data, updScore, userIdObject,
                        map, contentlist, updTypeObject, status, reqMap, request);
                return getFormatJson(JSON.toJSONString(returnmap));

            } else if (Const.LEARN_UPDSCORE.equals(decryptBasedDes(updTypeObject.toString()))) {
                //课程学习--变更积分分支
                ReturnData returnmap = updTypeLearn(nowDateTime, nowDate, logger, data, updScore, userIdObject,
                        map, contentlist, updTypeObject, status, reqMap, request);
                return getFormatJson(JSON.toJSONString(returnmap));

            } else if (Const.COMMENT_UPDSCORE.equals(decryptBasedDes(updTypeObject.toString()))) {
                //课程评价--变更积分分支
                ReturnData returnmap = updTypeCourseComment(nowDateTime, nowDate, logger, data, updScore, userIdObject,
                        map, contentlist, updTypeObject, status, reqMap, request);
                return getFormatJson(JSON.toJSONString(returnmap));

            } else if (Const.INVESTIGATION_UPDSCORE.equals(decryptBasedDes(updTypeObject.toString()))) {
                //问卷调查--变更积分分支
                ReturnData returnmap = updTypeInvestigation(nowDateTime, nowDate, logger, data, updScore, userIdObject,
                        map, contentlist, updTypeObject, status, reqMap, request);
                return getFormatJson(JSON.toJSONString(returnmap));

            } else if (Const.SHARE_UPDSCORE.equals(decryptBasedDes(updTypeObject.toString()))) {
                //分享课程--变更积分分支,暂不增加此接口
                ReturnData returnmap = null;
                returnmap.setCode(Const.SUCCESS_CODE);
                return getFormatJson(JSON.toJSONString(returnmap));

            } else if (Const.TRAINING_UPDSCORE.equals(decryptBasedDes(updTypeObject.toString()))) {
                //培训班结业--变更积分分支
                ReturnData returnmap = updTypeTraining(nowDateTime, nowDate, logger, data, updScore, userIdObject,
                        map, contentlist, updTypeObject, status, reqMap, request);
                return getFormatJson(JSON.toJSONString(returnmap));

            } else if (Const.EXCHANGE_UPDSCORE.equals(decryptBasedDes(updTypeObject.toString()))) {
                updScore = ((Object) reqMap.get("exchangenum")).toString();
                if (!isNumeric(updScore)) {
                    //判断送进来的金额是否为空或者非数字
                    logger.info("兑换积分数量输入不合规");
                    return getFormatJson(JSON.toJSONString(map));
                }
                //用户兑换积分
                ReturnData returnmap = updTypeExchange(nowDateTime, nowDate, logger, data, updScore, userIdObject,
                        map, contentlist, updTypeObject, status, reqMap, request);
                return getFormatJson(JSON.toJSONString(returnmap));
            } else if (Const.TASK_UPDSCORE.equals(decryptBasedDes(updTypeObject.toString()))) {
                //用户作业被置为精华或非精华变更积分
                ReturnData returnmap = updTypeTask(nowDateTime, nowDate, logger, data, updScore, userIdObject,
                        map, contentlist, updTypeObject, status, reqMap, request);
                return getFormatJson(JSON.toJSONString(returnmap));
            } else if (Const.APPRECIATE_UPDSCORE.equals(decryptBasedDes(updTypeObject.toString()))) {
                //用户问题点赞超过100变更积分
                ReturnData returnmap = updTypeAppreciate(nowDateTime, nowDate, logger, data, updScore, userIdObject,
                        map, contentlist, updTypeObject, status, reqMap, request);
                return getFormatJson(JSON.toJSONString(returnmap));
            }
        } catch (
                Exception e) {
            //事务回滚
            transactionManager.rollback(status);
            // 系统错误
            map.setCode(Const.FAILURE_CODE);
        }
        // 统一输出返回结果
        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        jsonReturn = getFormatJson(jsonReturn);
        logger.info("queryScore---jsonObject.toString()---" + jsonReturn + "---");
        return jsonReturn;
    }

    /**
     * Title: Const 单一因为用户问题点赞之后，变更积分方法:
     *
     * @author wangyanlai
     * @version 2018年12月26日 下午4:29:44
     */
    public ReturnData updTypeAppreciate(String nowDateTime, String nowDate, Logger logger, PageData data, String updScore, Object userIdObject,
                                        ReturnData map, List<PageData> contentlist, Object updTypeObject,
                                        TransactionStatus status, Map<String, Object> reqMap, HttpServletRequest request) throws Exception {
        //该分支为用户问题点赞产生积分变更的分支=====1、校验必输项(上文已结束)，2、检查对应问题是否已经通过被点赞得分，
        // 3、根据问卷调查类型，查询积分配置表对应分数，4、积分有效期信息表，个人积分表分数变更，个人问卷积分信息表状态变更或者初始化记录，添加一条日志信息
        PerQuestionIntegration perQuestionIntegration = new PerQuestionIntegration();
        //查询当前问卷是否已经获得积分
        perQuestionIntegration.setUserId(decryptBasedDes(((Object) reqMap.get(userIdField)).toString()));
        perQuestionIntegration.setId(decryptBasedDes(((Object) reqMap.get("questionid")).toString()));
        logger.info("查询问卷调查积分信息表是否已经存在记录");
        //用于分数汇总
        String score = null;
        //判断课程积分表是否需要原始创建
        String createCourseIntegration = null;

        // TODO 需要添加问题id的校验
        List<PerQuestionIntegration> classTypeListQuestion = (List<PerQuestionIntegration>) dao.findForList("PerQuestionIntegrationMapper.selectByPrimaryKey", perQuestionIntegration);
        if (classTypeListQuestion != null && classTypeListQuestion.size() != 0) {
            perQuestionIntegration = classTypeListQuestion.get(0);
            if (Const.IF_SORCE_YES.equals(perQuestionIntegration.getIfScore())) {
                logger.info("检测到该用户已经通过问题点赞得分,此次问题点赞不再加分");
                logger.info("updateScore---jsonObject.toString()---" + JSON.toJSONString(map) + "--积分未产生变化--");
                score = perQuestionIntegration.getScore() == null ? "0" : perQuestionIntegration.getScore();
                data = new PageData();
                data.put("score", score);
                data.put("upd_score", "0");
                data.put("update_tm", perQuestionIntegration.getUpdateTm() == null ? "" : perQuestionIntegration.getUpdateTm());
                map.setCode(Const.SUCCESS_CODE);
                contentlist.add(data);
                map.setContentlist(contentlist);
                transactionManager.rollback(status);
                return map;
            }
        } else {
            createCourseIntegration = "yes";
        }
        logger.info("允许该课程增加积分=====首先查询用户积分配置表了解问题点赞配置的分数");
        PerIntegrationConfigure perIntegrationConfigure = new PerIntegrationConfigure();
        //4、根据课程性质查询积分配置表对应分数
        //送入指定规则
        perIntegrationConfigure.setScoreType(Const.APPRECIATE_UPDSCORE);
        PageData pd = (PageData) dao.findForObject("PerIntegrationConfigureMapper.selectByexamplePage", perIntegrationConfigure);
        if (null != pd) {
            updScore = pd.getString("SCORE");
        } else {
            //还未曾配置问题点赞超出100个对应的分数值，使用默认分数
            updScore = Const.DEFAULT_APPRECIATE_UPDSCORE;
        }
        logger.info("最终经过计算实际增加的分数=" + updScore);

        if (!updScore.contains("-")) {
            //插入积分有效期信息表
            boolean efffectiveSts = false;
            map.setCode(Const.FAILURE_CODE);
            try {
                //1--查询个人基本信息表------>2--更新或者插入个人基本信息表----->3--插入积变更日志信息表
                efffectiveSts = insertEffectiveScore(DESUtil.aesDecrypt(userIdObject.toString(),Const.ALLENCRYPTCODE), status, map,
                        updScore, logger, contentlist, reqMap);
            } catch (Exception e) {
                transactionManager.rollback(status);
                //json格式没有被打乱不需要格式化
                return map;
            }
            if (!efffectiveSts) {
                //积分有效期表插入失败
                transactionManager.rollback(status);
                //json格式没有被打乱不需要格式化
                return map;
            } else {
                map.setCode(Const.SUCCESS_CODE);
            }
        }


        //5、个人积分分数变更
        //（1）--查询个人基本信息表------>（2）--更新或者插入个人基本信息表----->（3）--插入积变更日志信息表
        ReturnData updBasisAndLogreturn = updScoreBasis(nowDateTime, nowDate, DESUtil.aesDecrypt(userIdObject.toString(),Const.ALLENCRYPTCODE),
                status, map, decryptBasedDes(updTypeObject.toString()), updScore, logger, contentlist, reqMap, request);
        //（1）--变更个人问题点赞积分表（创建或者更新）
        perQuestionIntegration.setIfScore(Const.IF_SORCE_YES);
        perQuestionIntegration.setScore(String.valueOf((Integer.valueOf(perQuestionIntegration.getScore() == null ? "0" : perQuestionIntegration.getScore()) + Integer.valueOf(updScore))));
        perQuestionIntegration.setUpdateTm(nowDateTime);
        perQuestionIntegration.setTmSmp(nowDateTime);
        Object updNum = null;
        try {
            if (StringUtils.isBlank(createCourseIntegration)) {
                updNum = dao.update("PerQuestionIntegrationMapper.updateByPrimaryKeySelective", perQuestionIntegration);
            } else {
                perQuestionIntegration.setCreationTm(nowDateTime);
                updNum = dao.update("PerQuestionIntegrationMapper.insert", perQuestionIntegration);
            }
        } catch (Exception e) {
            logger.info("更新或者插入个人问题点赞积分信息表失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(status);
            map.setCode(Const.FAILURE_CODE);
            //json格式没有被打乱不需要格式化
            return map;
        }
        //updNum代表已经修改的条数，后者新增的条数
        if (null == updNum) {
            logger.info("更新或者插入个人问题点赞积分表失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(status);
            map.setCode(Const.FAILURE_CODE);
            //json格式没有被打乱不需要格式化
            return map;
        }
        logger.info("更新（或插入）个人问题点赞积分信息表完成");
        return updBasisAndLogreturn;
    }

    /**
     * Title: Const 单一用户培训班结业，变更积分方法:
     *
     * @author wangyanlai
     * @version 2018年12月26日 下午4:29:44
     */
    public ReturnData updTypeTraining(String nowDateTime, String nowDate, Logger logger, PageData data, String updScore, Object userIdObject,
                                      ReturnData map, List<PageData> contentlist, Object updTypeObject,
                                      TransactionStatus status, Map<String, Object> reqMap, HttpServletRequest request) throws Exception {
        //该分支为用户培训班结业产生积分变更的分支=====1、校验必输项(上文已结束)，2、检查培训班id是否已经得分，
        // 3、根据培训班结业类型，查询积分配置表对应分数，4、个人积分表分数变更，个人培训班积分信息表状态变更或者初始化记录，添加一条日志信息
        PerTrainingIntegration perTrainingIntegration = new PerTrainingIntegration();
        perTrainingIntegration.setId(decryptBasedDes(((Object) reqMap.get("trainingid")).toString()));
        //查询当前培训班是否已经获得积分
        perTrainingIntegration.setUserId(decryptBasedDes(((Object) reqMap.get(userIdField)).toString()));
        logger.info("查询培训班结业积分信息表是否已经存在记录");
        //用于分数汇总
        String score = null;
        //判断培训班积分表是否需要原始创建
        String createCourseIntegration = null;
        List<PerTrainingIntegration> classTypePerTrainingIntegration = (List<PerTrainingIntegration>) dao.findForList("PerTrainingIntegrationMapper.selectByPrimaryKey", perTrainingIntegration);
        if (classTypePerTrainingIntegration != null && classTypePerTrainingIntegration.size() != 0) {
            perTrainingIntegration = classTypePerTrainingIntegration.get(0);
            if (Const.IF_GRADUATION_YES.equals(perTrainingIntegration.getIfGraduation())) {
                logger.info("检测到该用户已经在当前培训班结业，并且获得积分，不可再次加分");
                logger.info("updateScore---jsonObject.toString()---" + JSON.toJSONString(map) + "--积分未产生变化--");
                score = perTrainingIntegration.getScore() == null ? "0" : perTrainingIntegration.getScore();
                data = new PageData();
                data.put("score", score);
                data.put("upd_score", "0");
                data.put("update_tm", perTrainingIntegration.getUpdateTm() == null ? "" : perTrainingIntegration.getUpdateTm());
                map.setCode(Const.SUCCESS_CODE);
                contentlist.add(data);
                map.setContentlist(contentlist);
                transactionManager.rollback(status);
                return map;
            }
        } else {
            createCourseIntegration = "yes";
        }
        logger.info("根据培训班id检查是否合规，检索出培训班的类别信息。");
        String updType = null;
        ZwTrainingcourse zwTrainingcourse = new ZwTrainingcourse();
        zwTrainingcourse.setId(decryptBasedDes(((Object) reqMap.get("trainingid")).toString()));
        List<ZwTrainingcourse> classTypezwTrainingcourse = (List<ZwTrainingcourse>) dao.findForList("ZwTrainingcourseMapper.selectByPrimaryKey", zwTrainingcourse);
        if (classTypezwTrainingcourse != null && classTypezwTrainingcourse.size() != 0) {
            zwTrainingcourse = classTypezwTrainingcourse.get(0);
            updType = zwTrainingcourse.getType() == null ? "N" : zwTrainingcourse.getType();
            ZwTrainingcourseCourseRecord zwTrainingcourseCourseRecord = new ZwTrainingcourseCourseRecord();
            zwTrainingcourseCourseRecord.setId(decryptBasedDes(((Object) reqMap.get("trainingid")).toString()));
            List<ZwTrainingcourseCourseRecord> zwTrainingcourseList = (List<ZwTrainingcourseCourseRecord>) dao.findForList("ZwTrainingcourseCourseRecordMapper.selectByPrimaryKey", zwTrainingcourseCourseRecord);
            if (zwTrainingcourseCourseRecord != null && zwTrainingcourseList.size() != 0) {
                zwTrainingcourseCourseRecord = zwTrainingcourseList.get(0);
                updType = updType + zwTrainingcourseCourseRecord.getAttribute() == null ? "N" : zwTrainingcourseCourseRecord.getAttribute();
            } else {
                updType = updType + "N";
            }
        } else {
            createCourseIntegration = "yes";
            logger.info("该培训班id不合法请检查后输入");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(status);
            map.setCode(Const.IRREGULAR_PARAMETERS);
            //json格式没有被打乱不需要格式化
            return map;
        }


        logger.info("允许本次培训班结业增加积分=====首先查询用户积分配置表了解培训班结业配置的分数");
        PerIntegrationConfigure perIntegrationConfigure = new PerIntegrationConfigure();
        //4、根据变更类型为调查问卷查询积分配置表对应分数
        //送入指定规则
        perIntegrationConfigure.setScoreType(Const.TRAINING_UPDSCORE);
        perIntegrationConfigure.setRemark1(updType);
        PageData pd = (PageData) dao.findForObject("PerIntegrationConfigureMapper.selectByexamplePage", perIntegrationConfigure);
        if (null != pd) {
            updScore = pd.getString("SCORE");
        } else {
            //还未曾配置培训班结业对应的分数值，使用默认分数
            updScore = Const.DEFAULT_TRAINING_UPDSCORE;
        }
        logger.info("最终经过计算实际增加的分数=" + updScore);
        if (!updScore.contains("-")) {
            //插入积分有效期信息表
            boolean efffectiveSts = false;
            map.setCode(Const.FAILURE_CODE);
            try {
                //1--查询个人基本信息表------>2--更新或者插入个人基本信息表----->3--插入积变更日志信息表
                efffectiveSts = insertEffectiveScore(DESUtil.aesDecrypt(userIdObject.toString(),Const.ALLENCRYPTCODE), status, map,
                        updScore, logger, contentlist, reqMap);
            } catch (Exception e) {
                transactionManager.rollback(status);
                //json格式没有被打乱不需要格式化
                return map;
            }
            if (!efffectiveSts) {
                //积分有效期表插入失败
                transactionManager.rollback(status);
                //json格式没有被打乱不需要格式化
                return map;
            } else {
                map.setCode(Const.SUCCESS_CODE);
            }
        }


        //5、个人积分分数变更
        //（1）--查询个人基本信息表------>（2）--更新或者插入个人基本信息表----->（3）--插入积变更日志信息表
        ReturnData updBasisAndLogreturn = updScoreBasis(nowDateTime, nowDate, DESUtil.aesDecrypt(userIdObject.toString(),Const.ALLENCRYPTCODE),
                status, map, decryptBasedDes(updTypeObject.toString()), updScore, logger, contentlist, reqMap, request);
        //（1）--变更个人培训班结业积分表（创建或者更新）
        perTrainingIntegration.setIfGraduation(Const.IF_GRADUATION_YES);
        perTrainingIntegration.setScore(String.valueOf((Integer.valueOf(perTrainingIntegration.getScore() == null ? "0" : perTrainingIntegration.getScore()) + Integer.valueOf(updScore))));
        perTrainingIntegration.setUpdateTm(nowDateTime);
        perTrainingIntegration.setTmSmp(nowDateTime);
        Object updNum = null;
        try {
            if (StringUtils.isBlank(createCourseIntegration)) {
                updNum = dao.update("PerTrainingIntegrationMapper.updateByPrimaryKeySelective", perTrainingIntegration);
            } else {
                perTrainingIntegration.setCreationTm(nowDateTime);
                updNum = dao.update("PerTrainingIntegrationMapper.insert", perTrainingIntegration);
            }
        } catch (Exception e) {
            logger.info("更新或者插入个人培训班结业积分信息表失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(status);
            map.setCode(Const.FAILURE_CODE);
            //json格式没有被打乱不需要格式化
            return map;
        }
        //updNum代表已经修改的条数，后者新增的条数
        if (null == updNum) {
            logger.info("更新或者插入个人培训班积分表失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(status);
            map.setCode(Const.FAILURE_CODE);
            //json格式没有被打乱不需要格式化
            return map;
        }
        logger.info("更新（或插入）个人培训班结业积分信息表完成");
        return updBasisAndLogreturn;
    }


    /**
     * Title: Const updTypeTask用户作业被置为精华或者非精华变更:
     *
     * @author wangyanlai
     * @version 2018年12月26日 下午4:29:44
     */
    public ReturnData updTypeTask(String nowDateTime, String nowDate, Logger logger, PageData data, String updScore, Object userIdObject,
                                  ReturnData map, List<PageData> contentlist, Object updTypeObject,
                                  TransactionStatus status, Map<String, Object> reqMap, HttpServletRequest request) throws Exception {
        //该分支为用户作业被置为精华或者非精华时候产生的积分变更的分支=====1、校验必输项(上文已结束)，2、作业id是否真实可信（暂时没有表）
        // 3、作业积分信息表是否有数据有的话状态变为相反，并且对其进行加分或者减分，没有的话，直接初始化，并且置为精华4、个人积分表分数变更，个人兑换积分信息表新增记录，积分变更日志信息表新增记录
        //TODO 暂时没有作业表所以不需要校验传进来的作业id的真实性

        logger.info("检查积分配置表中的精华对应分数");
        PerIntegrationConfigure perIntegrationConfigure = new PerIntegrationConfigure();
        //4、根据变更类型为调查问卷查询积分配置表对应分数
        //送入指定规则
        perIntegrationConfigure.setScoreType(Const.TASK_UPDSCORE);
        PageData pdConfigure = (PageData) dao.findForObject("PerIntegrationConfigureMapper.selectByexamplePage", perIntegrationConfigure);
        if (null != pdConfigure) {
            updScore = pdConfigure.getString("SCORE");
        } else {
            //还未曾配置培训班结业对应的分数值，使用默认分数
            updScore = Const.DEFAULT_TASK_UPDSCORE;
        }
        logger.info("检查作业积分信息表是否有数据");
        PerTaskIntegration perTaskIntegration = new PerTaskIntegration();
        perTaskIntegration.setUserId(DESUtil.aesDecrypt(userIdObject.toString(),Const.ALLENCRYPTCODE));
        perTaskIntegration.setId(decryptBasedDes(((Object) reqMap.get("taskid")).toString()));
        //1、查询当前作业积分信息表相关信息
        //默认置为精华
        String ifScore = Const.IF_SORCE_YES;
        Object updNum = null;
        PageData pd = (PageData) dao.findForObject("PerTaskIntegrationMapper.selectByPrimaryKey", perTaskIntegration);
        if (null != pd) {
            //当查询最大兑换人数时SCORE字段表示人数
            if (ifScore.equals(pd.getString("IF_SCORE"))) {
                //取消精华逻辑
                ifScore = Const.IF_SORCE__NO;
                updScore = "-" + pd.getString("SCORE");
                perTaskIntegration.setScore("0");
            } else {
                perTaskIntegration.setScore(updScore);
            }
            logger.info("最终经过计算实际增加的分数=" + updScore);
            perTaskIntegration.setCreationTm(pd.getString("CREATION_TM"));
            perTaskIntegration.setRemark1(pd.getString("REMARK1"));
            perTaskIntegration.setRemark2(pd.getString("REMARK2"));
            perTaskIntegration.setRemark3(pd.getString("REMARK3"));
            perTaskIntegration.setRemark4(pd.getString("REMARK4"));
            perTaskIntegration.setRemark5(pd.getString("REMARK5"));
            perTaskIntegration.setIfScore(ifScore);
            perTaskIntegration.setTmSmp(DateUtil.getTime());
            perTaskIntegration.setUpdateTm(DateUtil.getTime());
            try {
                updNum = dao.update("PerTaskIntegrationMapper.updateByPrimaryKeySelective", perTaskIntegration);
            } catch (Exception e) {
                logger.info("变更作业精华积分信息表失败");
                //事务回滚
                logger.info("=====================事务回滚======================");
                transactionManager.rollback(status);
                map.setCode(Const.FAILURE_CODE);
                //json格式没有被打乱不需要格式化
                return map;
            }
        } else {
            perTaskIntegration.setScore(updScore);
            perTaskIntegration.setIfScore(ifScore);
            perTaskIntegration.setCreationTm(DateUtil.getTime());
            perTaskIntegration.setTmSmp(DateUtil.getTime());
            perTaskIntegration.setUpdateTm(DateUtil.getTime());
            map.setCode(Const.FAILURE_CODE);
            try {
                updNum = dao.update("PerTaskIntegrationMapper.insert", perTaskIntegration);
            } catch (Exception e) {
                logger.info("变更作业精华积分信息表失败");
                //事务回滚
                logger.info("=====================事务回滚======================");
                transactionManager.rollback(status);
                //json格式没有被打乱不需要格式化
                return map;
            }
        }
        if (null == updNum) {
            logger.info("变更作业精华积分信息表失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(status);
            //json格式没有被打乱不需要格式化
            return map;
        } else {
            map.setCode(Const.SUCCESS_CODE);
        }
        //2、变更基本信息表和日志表
        ReturnData updBasisAndLogreturn = updScoreBasis(nowDateTime, nowDate, DESUtil.aesDecrypt(userIdObject.toString(),Const.ALLENCRYPTCODE),
                status, map, decryptBasedDes(updTypeObject.toString()), updScore, logger, contentlist, reqMap, request);
        logger.info("更新（或插入）个人积分兑换信息表完成");
        return updBasisAndLogreturn;
    }

    /**
     * Title: Const 单一用户积分兑换奖品，变更积分方法:
     *
     * @author wangyanlai
     * @version 2018年12月26日 下午4:29:44
     */
    public ReturnData updTypeExchange(String nowDateTime, String nowDate, Logger logger, PageData data, String updScore, Object userIdObject,
                                      ReturnData map, List<PageData> contentlist, Object updTypeObject,
                                      TransactionStatus status, Map<String, Object> reqMap, HttpServletRequest request) throws Exception {
        //该分支为用户积分兑换奖品产生积分变更的分支=====1、校验必输项(上文已结束)，2、检查当日总兑换人数是否已经达标
        // 3、检查个人兑换金额是否超过个人允许最大兑换金额，查询积分配置表对应分数，4、个人积分表分数变更，个人兑换积分信息表新增记录，积分变更日志信息表新增记录
        logger.info("检查当日兑换人数=====首先查询积分配置表最大兑换人数");
        PerIntegrationConfigure perIntegrationConfigure = new PerIntegrationConfigure();
        String maxExchangeScore = null;
        String maxExchangePer = null;
        perIntegrationConfigure.setScoreType(Const.MAX_EXCHANGE_PER);
        //1、查询当前积分配置表最大兑换人数
        PageData pd = (PageData) dao.findForObject("PerIntegrationConfigureMapper.selectByexamplePage", perIntegrationConfigure);
        if (null != pd) {
            //当查询最大兑换人数时SCORE字段表示人数
            maxExchangePer = pd.getString("SCORE");
        } else {
            //还未曾配置最大兑换人数，使用默认最大兑换人数
            maxExchangePer = Const.DEFAULT_MAX_EXCHANGE_PER;
        }
        PerIntegrationLog perIntegrationLog = new PerIntegrationLog();
        perIntegrationLog.setScoreType(Const.EXCHANGE_UPDSCORE);
        String creationDt = nowDate;
        creationDt = creationDt.substring(0, 4) + creationDt.substring(5, 7) + creationDt.substring(8, 10);
        perIntegrationLog.setCreationDt(creationDt);
        //用于存档的当前分数（扣除之后）
        String score = null;
        //2、查询积分变更日志表当日兑换人数
        List<PerIntegrationLog> classTypePerIntegrationLog = (List<PerIntegrationLog>) dao.findForList("PerIntegrationLogMapper.selectByScoreType", perIntegrationLog);
        if (classTypePerIntegrationLog != null && classTypePerIntegrationLog.size() != 0) {
            if (Integer.valueOf(classTypePerIntegrationLog.size()) >= Integer.valueOf(maxExchangePer)) {
                logger.info("当天兑换人数已经超出上限。");
                //事务回滚
                logger.info("=====================事务回滚======================");
                transactionManager.rollback(status);
                map.setCode(Const.EXCEEDING_PER_QUOTA);
                //json格式没有被打乱不需要格式化
                return map;
            }
        }

        //3、查询当前积分配置表单一个人最大兑换金额
        perIntegrationConfigure.setScoreType(Const.MAX_EXCHANGE_SCORE);
        pd = (PageData) dao.findForObject("PerIntegrationConfigureMapper.selectByexamplePage", perIntegrationConfigure);
        if (null != pd) {
            maxExchangeScore = pd.getString("SCORE");
        } else {
            //还未曾配置最大兑换分数，使用默认分数
            maxExchangeScore = Const.DEFAULT_MAX_EXCHANGE_SCORE;
        }
        if (Integer.valueOf(updScore) >= Integer.valueOf(maxExchangeScore)) {
            logger.info("该用户兑换分数已经超出上限。");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(status);
            map.setCode(Const.EXCEEDING_QUOTA);
            //json格式没有被打乱不需要格式化
            return map;
        }
        //4、查询当前个人积分汇总表分值是否足够满足兑换条件
        PerIntegrationBasis perIntegrationBasis = new PerIntegrationBasis();
        perIntegrationBasis.setId(DESUtil.aesDecrypt(userIdObject.toString(),Const.ALLENCRYPTCODE));
        boolean ifCanExchangeTwo = false;
        boolean ifCanExchange = false;
        String originalScore = null;
        //默认部分奖励积分失效
        String loseEfficacy = Const.LOSEEFFICACY_NO;
        List<PerIntegrationBasis> classTypeListBasis = (List<PerIntegrationBasis>) dao.findForList("PerIntegrationBasisMapper.selectByPrimaryKey", perIntegrationBasis);
        if (classTypeListBasis != null && classTypeListBasis.size() != 0) {
            perIntegrationBasis = classTypeListBasis.get(0);
            score = perIntegrationBasis.getScore();
            originalScore = perIntegrationBasis.getOriginalScore();
            //更新参数用于更新或者插入
            if (Integer.valueOf(score) >= Integer.valueOf(updScore)) {
                ifCanExchange = true;
            } else if ((Integer.valueOf(score) + Integer.valueOf(originalScore)) >= Integer.valueOf(updScore)) {
                ifCanExchangeTwo = true;
                loseEfficacy = Const.LOSEEFFICACY_YES;
            }
        }
        if (!ifCanExchange && !ifCanExchangeTwo) {
            logger.info("该用户积分不足。");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(status);
            map.setCode(Const.EXCEEDING_QUOTA);
            //json格式没有被打乱不需要格式化
            return map;
        }
        //5、开始变更个人基本积分表，个人积分变更日志表，个人兑换积分信息表
        logger.info("允许兑换积分操作执行=====");
        updScore = "-" + updScore;
        logger.info("最终经过计算兑换消耗的分数=" + updScore);

        //该分支说明用户的奖励积分余额大于需要兑换的积分，所以优先扣除即将失效的积分。
        //插入积分有效期信息表
        boolean efffectiveStsSub = false;
        try {
            //1--查询个人基本信息表------>2--更新或者插入个人基本信息表----->3--插入积变更日志信息表
            efffectiveStsSub = subEffectiveScore(loseEfficacy, DESUtil.aesDecrypt(userIdObject.toString(),Const.ALLENCRYPTCODE), status, map,
                    updScore, logger, contentlist, reqMap);
        } catch (Exception e) {
            map.setCode(Const.FAILURE_CODE);
            transactionManager.rollback(status);
            //json格式没有被打乱不需要格式化
            return map;
        }
        if (!efffectiveStsSub) {
            map.setCode(Const.FAILURE_CODE);
            transactionManager.rollback(status);
            //json格式没有被打乱不需要格式化
            return map;
        }

        //（1）--查询个人基本信息表------>（2）--更新或者插入个人基本信息表----->（3）--插入积变更日志信息表
        ReturnData updBasisAndLogreturn = updScoreBasis(nowDateTime, nowDate, DESUtil.aesDecrypt(userIdObject.toString(),Const.ALLENCRYPTCODE),
                status, map, decryptBasedDes(updTypeObject.toString()), updScore, logger, contentlist, reqMap, request);
        //（1）--个人兑换积分表（新增）
        PerExchangeIntegration perExchangeIntegration = new PerExchangeIntegration();
        //变动分数去除负号
        perExchangeIntegration.setExchangeScore(updScore.substring(1, updScore.length()));
        perExchangeIntegration.setScore(String.valueOf(Integer.valueOf(score) + Integer.valueOf(updScore)));
        perExchangeIntegration.setUpdateTm(nowDateTime);
        perExchangeIntegration.setTmSmp(nowDateTime);
        perExchangeIntegration.setCreationTm(nowDateTime);
        perExchangeIntegration.setId(UuidUtil.get32UUID());
        perExchangeIntegration.setUserId(decryptBasedDes(((Object) reqMap.get(userIdField)).toString()));
        Object updNum = null;
        map.setCode(Const.FAILURE_CODE);
        try {
            updNum = dao.update("PerExchangeIntegrationMapper.insert", perExchangeIntegration);
        } catch (Exception e) {
            logger.info("插入个人积分兑换信息表失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(status);
            //json格式没有被打乱不需要格式化
            return map;
        }
        //updNum代表已经修改的条数，后者新增的条数
        if (null == updNum) {
            logger.info("插入个人积分兑换信息表失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(status);
            //json格式没有被打乱不需要格式化
            return map;
        } else {
            map.setCode(Const.SUCCESS_CODE);
        }
        logger.info("更新（或插入）个人积分兑换信息表完成");
        return updBasisAndLogreturn;
    }

    /**
     * Title: Const 单一因为用户参与问卷调查之后，变更积分方法:
     *
     * @author wangyanlai
     * @version 2018年12月26日 下午4:29:44
     */
    public ReturnData updTypeInvestigation(String nowDateTime, String nowDate, Logger logger, PageData data, String updScore, Object userIdObject,
                                           ReturnData map, List<PageData> contentlist, Object updTypeObject,
                                           TransactionStatus status, Map<String, Object> reqMap, HttpServletRequest request) throws Exception {
        //该分支为用户参与问卷调查产生积分变更的分支=====1、校验必输项(上文已结束)，2、检查对应问卷是否已经得分，
        // 3、根据问卷调查类型，查询积分配置表对应分数，4、个人积分表分数变更，个人问卷积分信息表状态变更或者初始化记录，添加一条日志信息
        PerQuestionnaireIntegration perQuestionnaireIntegration = new PerQuestionnaireIntegration();
        //查询当前问卷是否已经获得积分
        perQuestionnaireIntegration.setUserId(decryptBasedDes(((Object) reqMap.get(userIdField)).toString()));
        perQuestionnaireIntegration.setId(decryptBasedDes(((Object) reqMap.get("questionnaireid")).toString()));
        logger.info("查询问卷调查积分信息表是否已经存在记录");
        //用于分数汇总
        String score = null;
        //判断课程积分表是否需要原始创建
        String createCourseIntegration = null;
        //检查送进来的问卷调查id, 是否有效 ZwQuestionnaire
        ZwQuestionnaire zwQuestionnaire = new ZwQuestionnaire();
        zwQuestionnaire.setId(decryptBasedDes(((Object) reqMap.get("questionnaireid")).toString()));
        List<ZwQuestionnaire> classTypeZwQuestionnaire = (List<ZwQuestionnaire>) dao.findForList("ZwQuestionnaireMapper.selectByPrimaryKey", zwQuestionnaire);
        if (classTypeZwQuestionnaire == null || classTypeZwQuestionnaire.size() == 0) {
            logger.info("该调查问卷id不合法请检查后输入");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(status);
            map.setCode(Const.IRREGULAR_PARAMETERS);
            //json格式没有被打乱不需要格式化
            return map;
        }
        List<PerQuestionnaireIntegration> classTypeListQuestionnaire = (List<PerQuestionnaireIntegration>) dao.findForList("PerQuestionnaireIntegrationMapper.selectByPrimaryKey", perQuestionnaireIntegration);
        if (classTypeListQuestionnaire != null && classTypeListQuestionnaire.size() != 0) {
            perQuestionnaireIntegration = classTypeListQuestionnaire.get(0);
            if (Const.IF_SORCE_YES.equals(perQuestionnaireIntegration.getIfScore())) {
                logger.info("检测到该用户已经提交过该调查问卷，并且获得积分，此次提交问卷不再加分");
                logger.info("updateScore---jsonObject.toString()---" + JSON.toJSONString(map) + "--积分未产生变化--");
                score = perQuestionnaireIntegration.getScore() == null ? "0" : perQuestionnaireIntegration.getScore();
                data = new PageData();
                data.put("score", score);
                data.put("upd_score", "0");
                data.put("update_tm", perQuestionnaireIntegration.getUpdateTm() == null ? "" : perQuestionnaireIntegration.getUpdateTm());
                map.setCode(Const.SUCCESS_CODE);
                contentlist.add(data);
                map.setContentlist(contentlist);
                transactionManager.rollback(status);
                return map;
            }
        } else {
            createCourseIntegration = "yes";
        }
        logger.info("允许该课程增加积分=====首先查询用户积分配置表了解问卷调查配置的分数");
        PerIntegrationConfigure perIntegrationConfigure = new PerIntegrationConfigure();
        //4、根据课程性质查询积分配置表对应分数
        //送入指定规则
        perIntegrationConfigure.setScoreType(Const.INVESTIGATION_UPDSCORE);
        PageData pd = (PageData) dao.findForObject("PerIntegrationConfigureMapper.selectByexamplePage", perIntegrationConfigure);
        if (null != pd) {
            updScore = pd.getString("SCORE");
        } else {
            //还未曾配置问卷调查对应的分数值，使用默认分数
            updScore = Const.DEFAULT_INVESTIGATION_UPDSCORE;
        }
        logger.info("最终经过计算实际增加的分数=" + updScore);

        if (!updScore.contains("-")) {
            //插入积分有效期信息表
            boolean efffectiveSts = false;
            try {
                //1--查询个人基本信息表------>2--更新或者插入个人基本信息表----->3--插入积变更日志信息表
                efffectiveSts = insertEffectiveScore(DESUtil.aesDecrypt(userIdObject.toString(),Const.ALLENCRYPTCODE), status, map,
                        updScore, logger, contentlist, reqMap);
                if (!efffectiveSts) {
                    //积分有效期表插入失败
                    map.setCode(Const.FAILURE_CODE);
                    transactionManager.rollback(status);
                    //json格式没有被打乱不需要格式化
                    return map;
                }
            } catch (Exception e) {
                map.setCode(Const.FAILURE_CODE);
                transactionManager.rollback(status);
                //json格式没有被打乱不需要格式化
                return map;
            }
        }

        //5、个人积分分数变更
        //（1）--查询个人基本信息表------>（2）--更新或者插入个人基本信息表----->（3）--插入积变更日志信息表
        ReturnData updBasisAndLogreturn = updScoreBasis(nowDateTime, nowDate, DESUtil.aesDecrypt(userIdObject.toString(),Const.ALLENCRYPTCODE),
                status, map, decryptBasedDes(updTypeObject.toString()), updScore, logger, contentlist, reqMap, request);
        //（1）--变更个人问卷调查积分表（创建或者更新）
        perQuestionnaireIntegration.setIfScore(Const.IF_SORCE_YES);
        perQuestionnaireIntegration.setScore(String.valueOf((Integer.valueOf(perQuestionnaireIntegration.getScore() == null ? "0" : perQuestionnaireIntegration.getScore()) + Integer.valueOf(updScore))));
        perQuestionnaireIntegration.setUpdateTm(nowDateTime);
        perQuestionnaireIntegration.setTmSmp(nowDateTime);
        Object updNum = null;
        try {
            if (StringUtils.isBlank(createCourseIntegration)) {
                updNum = dao.update("PerQuestionnaireIntegrationMapper.updateByPrimaryKeySelective", perQuestionnaireIntegration);
            } else {
                perQuestionnaireIntegration.setCreationTm(nowDateTime);
                updNum = dao.update("PerQuestionnaireIntegrationMapper.insert", perQuestionnaireIntegration);
            }
        } catch (Exception e) {
            logger.info("更新或者插入个人问卷调查积分信息表失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(status);
            map.setCode(Const.FAILURE_CODE);
            //json格式没有被打乱不需要格式化
            return map;
        }
        //updNum代表已经修改的条数，后者新增的条数
        if (null == updNum) {
            logger.info("更新或者插入个人问卷调查积分表失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(status);
            map.setCode(Const.FAILURE_CODE);
            //json格式没有被打乱不需要格式化
            return map;
        }
        logger.info("更新（或插入）个人问卷调查积分信息表完成");
        return updBasisAndLogreturn;
    }

    /**
     * Title: Const *单一因为用户课程评价，变更积分方法:
     *
     * @author wangyanlai
     * @version 2018年12月26日 下午4:29:44
     */

    public ReturnData updTypeCourseComment(String nowDateTime, String nowDate, Logger logger, PageData data, String updScore, Object userIdObject,
                                           ReturnData map, List<PageData> contentlist, Object updTypeObject,
                                           TransactionStatus status, Map<String, Object> reqMap, HttpServletRequest request) throws Exception {
        //该分支为课程评论产生积分变更的分支=====1、校验必输项(上文已结束)，2、检查课程是否已经评论过，并且获得积分，
        // 3、根据课程评论类型检查积分配置表对应分数，4、个人积分表分数变更，个人课程积分表分数状态变更或者初始化记录，添加一条日志信息
        PerCourseIntegration perCourseIntegration = new PerCourseIntegration();
        //查询课程是否已经评论获得积分
        List<PerCourseIntegration> qryCourseIntegrationList = qryCourseIntegrationList(perCourseIntegration, logger, reqMap);
        logger.info("检查课程编号是否合理");
        ZwCourse zwCourse = new ZwCourse();
        zwCourse.setId(decryptBasedDes(((Object) reqMap.get("courseid")).toString()));
        List<ZwCourse> classTypeListZwCourse = (List<ZwCourse>) dao.findForList("ZwCourseMapper.selectByPrimaryKey", zwCourse);
        if (!(classTypeListZwCourse != null && classTypeListZwCourse.size() != 0)) {
            logger.info("===============该课程并不存在--事务回滚======================");
            //事务回滚
            transactionManager.rollback(status);
            map.setCode(Const.FAILURE_CODE);
            //json格式没有被打乱不需要格式化
            return map;
        }
        //用于分数汇总
        String score = null;
        //判断课程积分表是否需要原始创建
        String createCourseIntegration = null;
        if (qryCourseIntegrationList != null && qryCourseIntegrationList.size() != 0) {
            perCourseIntegration = qryCourseIntegrationList.get(0);
        } else {
            createCourseIntegration = "yes";
            perCourseIntegration.setCommentNum("0");
        }


        //该分支是，----课程积分表没有数据，----或者课程积分表里面有数据，但是该用户这门课程没有通过课程评论获取到积分
        logger.info("允许该课程增加积分=====首先查询用户积分配置表了解课程评论配置的分数");
        PerIntegrationConfigure perIntegrationConfigure = new PerIntegrationConfigure();
        //4、根据课程性质查询积分配置表对应分数
        //送入指定规则
        perIntegrationConfigure.setScoreType(Const.COMMENT_UPDSCORE);
        PageData pd = (PageData) dao.findForObject("PerIntegrationConfigureMapper.selectByexamplePage", perIntegrationConfigure);
        if (null != pd) {
            if (Const.IF_COMMENT_YES.equals(perCourseIntegration.getIfComment())) {
                //表示该评论已经被置为精华，此次不在加分
                updScore = "0";
            } else {
                //表示该用户还没有评论被置为精华，此次需要加分
                updScore = pd.getString("SCORE");
            }
            if (Integer.valueOf(pd.getString("REMARK1")) <= Integer.valueOf(perCourseIntegration.getCommentNum())) {
                perIntegrationConfigure.setScoreType(Const.HOSTILITY_COMMENT_UPDSCORE);
                PageData pdNext = (PageData) dao.findForObject("PerIntegrationConfigureMapper.selectByexamplePage", perIntegrationConfigure);
                if (null != pdNext) {
                    updScore = pdNext.getString("SCORE");
                } else {
                    //没有配置恶意评论扣分规则，使用默认值
                    updScore = Const.DEFAULT_HOSTILITY_COMMENT_UPDSCORE;
                    //||||注意判断是否属于恶意评论必须配置积分配置信息表的，课程评价的备注1字段，否则视为没有恶意评价||||
                }
            }
        } else {
            //还未曾配置课程评论对应的分数值，使用默认分数
            updScore = Const.DEFAULT_COMMENT_UPDSCORE;
        }
        logger.info("最终经过计算实际增加的分数=" + updScore);
        if (!updScore.contains("-")) {
            map.setCode(Const.FAILURE_CODE);
            //插入积分有效期信息表
            boolean efffectiveSts = false;
            try {
                //1--查询个人基本信息表------>2--更新或者插入个人基本信息表----->3--插入积变更日志信息表
                efffectiveSts = insertEffectiveScore(DESUtil.aesDecrypt(userIdObject.toString(),Const.ALLENCRYPTCODE), status, map,
                        updScore, logger, contentlist, reqMap);
            } catch (Exception e) {
                transactionManager.rollback(status);
                //json格式没有被打乱不需要格式化
                return map;
            }
            if (!efffectiveSts) {
                //积分有效期表插入失败
                transactionManager.rollback(status);
                //json格式没有被打乱不需要格式化
                return map;
            } else {
                map.setCode(Const.SUCCESS_CODE);
            }
        }
        //5、个人积分分数变更
        //（1）--查询个人基本信息表------>（2）--更新或者插入个人基本信息表----->（3）--插入积变更日志信息表
        ReturnData updBasisAndLogreturn = updScoreBasis(nowDateTime, nowDate, DESUtil.aesDecrypt(userIdObject.toString(),Const.ALLENCRYPTCODE),
                status, map, decryptBasedDes(updTypeObject.toString()), updScore, logger, contentlist, reqMap, request);
        //（1）--变更个人课程积分表（创建或者更新）
        perCourseIntegration.setIfComment(Const.IF_COMMENT_YES);
        perCourseIntegration.setScore(String.valueOf((Integer.valueOf(perCourseIntegration.getScore() == null ? "0" : perCourseIntegration.getScore()) + Integer.valueOf(updScore))));
        perCourseIntegration.setUserId(DESUtil.aesDecrypt(userIdObject.toString(),Const.ALLENCRYPTCODE));
        perCourseIntegration.setUpdateTm(nowDateTime);
        perCourseIntegration.setTmSmp(nowDateTime);
        perCourseIntegration.setCommentNum(String.valueOf((Integer.valueOf(perCourseIntegration.getCommentNum() == null ? "0" : perCourseIntegration.getCommentNum()) + 1)));
        Object updNum = null;
        try {
            map.setCode(Const.FAILURE_CODE);
            if (StringUtils.isBlank(createCourseIntegration)) {
                updNum = dao.update("PerCourseIntegrationMapper.updateByPrimaryKeySelective", perCourseIntegration);
            } else {
                perCourseIntegration.setIfLearn(Const.IF_LEARN_YES);
                perCourseIntegration.setCreationTm(nowDateTime);
                perCourseIntegration.setCommentNum(perCourseIntegration.getCommentNum() == null ? Const.COMMENT_UPDSCORE_CREATE_NUM : perCourseIntegration.getCommentNum());
                perCourseIntegration.setIfComment(Const.IF_COMMENT_NO);
                perCourseIntegration.setIfShare(Const.IF_SHARE_NO);
                perCourseIntegration.setIfAssess(Const.IF_ASSESS_NO);
                updNum = dao.update("PerCourseIntegrationMapper.insert", perCourseIntegration);
            }
        } catch (Exception e) {
            logger.info("更新或者插入个人课程积分表失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(status);
            //json格式没有被打乱不需要格式化
            return map;
        }
        //updNum代表已经修改的条数，后者新增的条数
        if (null == updNum) {
            logger.info("=====================更新或者插入个人课程积分表失败---事务回滚=====================");
            //事务回滚
            transactionManager.rollback(status);
            //json格式没有被打乱不需要格式化
            return map;
        } else {
            map.setCode(Const.SUCCESS_CODE);
        }
        logger.info("更新（或插入）个人课程积分表完成");
        return updBasisAndLogreturn;
    }

    /**
     * Title: Const 单一因为用户课程学习，变更积分方法:
     *
     * @author wangyanlai
     * @version 2018年12月26日 下午4:29:44
     */
    public ReturnData updTypeLearn(String nowDateTime, String nowDate, Logger logger, PageData data, String updScore, Object userIdObject,
                                   ReturnData map, List<PageData> contentlist, Object updTypeObject,
                                   TransactionStatus status, Map<String, Object> reqMap, HttpServletRequest request) throws Exception {
        //该分支为课程学习产生积分变更的分支=====1、校验必输项(上文已结束)，2、检查课程是否已经学习完成获得积分，
        // 3、检索课程性质，4、根据课程性质查询积分配置表对应分数，5、个人积分表分数变更，个人课程积分表分数状态变更或者初始化记录，添加一条日志信息
        //查询课程是否已经通过学习获得积分
        PerCourseIntegration perCourseIntegration = new PerCourseIntegration();
        perCourseIntegration.setUserId(DESUtil.aesDecrypt(userIdObject.toString(),Const.ALLENCRYPTCODE));
        perCourseIntegration.setId(decryptBasedDes(((Object) reqMap.get("courseid")).toString()));
        logger.info("查询课程是否已经通过学习获得积分");
        // 课程类型用于下文作为查询条件，进行拼接
        String courseType = null;
        //学习时间一会用来计算变更分数
        String learnTime = null;
        List<PerCourseIntegration> qryCourseIntegrationList = qryCourseIntegrationList(perCourseIntegration, logger, reqMap);
        //没有实际变更积分
        String updateTm = null;
        //判断课程积分表是否需要原始创建
        String createCourseIntegration = null;
        if (qryCourseIntegrationList != null && qryCourseIntegrationList.size() != 0) {
            perCourseIntegration = qryCourseIntegrationList.get(0);
            if (Const.IF_LEARN_YES.equals(perCourseIntegration.getIfLearn())) {
                logger.info("检测到该用户该课程已经通过学习获得积分，此次学习不再加分");
                logger.info("updateScore---jsonObject.toString()---" + JSON.toJSONString(map) + "--积分未产生变化--");
                //没有实际变更积分
                updateTm = perCourseIntegration.getUpdateTm() == null ? "" : (String) perCourseIntegration.getUpdateTm();
                data = new PageData();
                data.put("score", perCourseIntegration.getScore() == null ? "0" : (String) perCourseIntegration.getScore());
                data.put("upd_score", "0");
                data.put("update_tm", updateTm);
                map.setCode(Const.SUCCESS_CODE);
                contentlist.add(data);
                map.setContentlist(contentlist);
                transactionManager.rollback(status);
                return map;
            }
        } else {
            createCourseIntegration = "yes";
        }
        //该分支是，----课程积分表没有数据，----或者课程积分表里面有数据，但是该用户这门课程没有通过学习获取到积分
        logger.info("允许该课程增加积分=====首先查询课程相关表了解当前课程类别属性");
        ZwCourse zwCourse = new ZwCourse();
        zwCourse.setId(decryptBasedDes(((Object) reqMap.get("courseid")).toString()));
        List<ZwCourse> classTypeListZwCourse = (List<ZwCourse>) dao.findForList("ZwCourseMapper.selectByPrimaryKey", zwCourse);
        if (classTypeListZwCourse != null && classTypeListZwCourse.size() != 0) {
            courseType = classTypeListZwCourse.get(0).getIstop() == null ? "N" : (String) classTypeListZwCourse.get(0).getIstop();
        } else {
            logger.info("===============该课程并不存在======================");
            //事务回滚
            logger.info("===============事务回滚======================");
            transactionManager.rollback(status);
            map.setCode(Const.FAILURE_CODE);
            //json格式没有被打乱不需要格式化
            return map;
        }
        //3、检索课程性质
        ZwCourseRecord zwCourseRecord = new ZwCourseRecord();
        zwCourseRecord.setCourseid(decryptBasedDes(((Object) reqMap.get("courseid")).toString()));
        zwCourseRecord.setUserid(decryptBasedDes(((Object) reqMap.get(userIdField)).toString()));
        List<ZwCourseRecord> classTypeListZwCourseRecord = (List<ZwCourseRecord>) dao.findForList("ZwCourseRecordMapper.selectByexamplePage", zwCourseRecord);
        if (classTypeListZwCourseRecord != null && classTypeListZwCourseRecord.size() != 0) {
            courseType = courseType + (classTypeListZwCourseRecord.get(0).getAttribute() == null ? "N" : (String) classTypeListZwCourseRecord.get(0).getAttribute());
            learnTime = classTypeListZwCourseRecord.get(0).getAllstudylength();
        } else {
            logger.info("===============该用户在学习进度表中暂无数据======================");
            //事务回滚
            logger.info("===============事务回滚======================");
            transactionManager.rollback(status);
            map.setCode(Const.FAILURE_CODE);
            //json格式没有被打乱不需要格式化
            return map;
        }
        PerIntegrationConfigure perIntegrationConfigure = new PerIntegrationConfigure();
        //4、根据课程性质查询积分配置表对应分数
        //送入指定规则
        perIntegrationConfigure.setScoreType(Const.LEARN_UPDSCORE);
        perIntegrationConfigure.setRemark1(courseType);
        PageData pd = (PageData) dao.findForObject("PerIntegrationConfigureMapper.selectByexamplePage", perIntegrationConfigure);
        if (null != pd) {
            updScore = pd.getString("SCORE");
        } else {
            //还未曾配置学习课程对应的分数值，使用默认分数
            updScore = Const.DEFAULT_LEARN_UPDSCORE;
        }
        //通过学习时间和单位时间的分数值计算总的变更分数值
        updScore = String.valueOf((Integer.valueOf(learnTime) * (Integer.valueOf(updScore))));
        logger.info("最终经过计算实际增加的分数=" + updScore);

        //插入积分有效期信息表
        boolean efffectiveSts = false;
        try {
            //1--查询个人基本信息表------>2--更新或者插入个人基本信息表----->3--插入积变更日志信息表
            efffectiveSts = insertEffectiveScore(DESUtil.aesDecrypt(userIdObject.toString(),Const.ALLENCRYPTCODE), status, map,
                    updScore, logger, contentlist, reqMap);
        } catch (Exception e) {
            map.setCode(Const.FAILURE_CODE);
            transactionManager.rollback(status);
            //json格式没有被打乱不需要格式化
            return map;
        }

        if (!efffectiveSts) {
            //积分有效期表插入失败
            map.setCode(Const.FAILURE_CODE);
            transactionManager.rollback(status);
            //json格式没有被打乱不需要格式化
            return map;
        }
        //5、个人积分分数变更
        //（1）--查询个人基本信息表------>（2）--更新或者插入个人基本信息表----->（3）--插入积变更日志信息表
        ReturnData updBasisAndLogreturn = updScoreBasis(nowDateTime, nowDate, DESUtil.aesDecrypt(userIdObject.toString(),Const.ALLENCRYPTCODE),
                status, map, decryptBasedDes(updTypeObject.toString()), updScore, logger, contentlist, reqMap, request);
        //（1）--变更个人课程积分表（创建或者更新）
        perCourseIntegration.setIfLearn(Const.IF_LEARN_YES);
        perCourseIntegration.setScore(String.valueOf((Integer.valueOf(perCourseIntegration.getScore() == null ? "0" : perCourseIntegration.getScore()) + Integer.valueOf(updScore))));
        perCourseIntegration.setUserId(DESUtil.aesDecrypt(userIdObject.toString(),Const.ALLENCRYPTCODE));
        perCourseIntegration.setUpdateTm(nowDateTime);
        perCourseIntegration.setTmSmp(nowDateTime);
        perCourseIntegration.setLearningTm(learnTime);
        Object updNum = null;
        try {
            map.setCode(Const.FAILURE_CODE);
            if (StringUtils.isBlank(createCourseIntegration)) {
                updNum = dao.update("PerCourseIntegrationMapper.updateByPrimaryKeySelective", perCourseIntegration);
            } else {
                perCourseIntegration.setCreationTm(nowDateTime);
                perCourseIntegration.setCommentNum(Const.COMMENT_UPDSCORE_CREATE_NUM);
                perCourseIntegration.setIfComment(Const.IF_COMMENT_NO);
                perCourseIntegration.setIfShare(Const.IF_SHARE_NO);
                perCourseIntegration.setIfAssess(Const.IF_ASSESS_NO);
                updNum = dao.update("PerCourseIntegrationMapper.insert", perCourseIntegration);
            }
        } catch (Exception e) {
            logger.info("更新或者插入个人课程积分表失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(status);
            //json格式没有被打乱不需要格式化
            return map;
        }
        //updNum代表已经修改的条数，后者新增的条数
        if (null == updNum) {
            logger.info("更新或者插入个人课程积分表失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(status);
            //json格式没有被打乱不需要格式化
            return map;
        }
        map.setCode(Const.SUCCESS_CODE);
        logger.info("更新（或插入）个人课程积分表完成");
        return updBasisAndLogreturn;
    }

    /**
     * Title: 单一因为用户登录，变更积分方法:
     *
     * @author wangyanlai
     * @version 2018年12月26日 下午4:29:44
     */

    public ReturnData updTypeLogin(String nowDateTime, String nowDate, Logger logger, PageData data, String updScore, Object userIdObject,
                                   ReturnData map, List<PageData> contentlist, Object updTypeObject,
                                   TransactionStatus status, Map<String, Object> reqMap, HttpServletRequest request) throws Exception {
        logger.info("判断当日该用户是否已经产存在积分变更，如果积分汇总信息表积分已经变更则直接跳过本次积分增加");
        PerIntegrationBasis perIntegrationBasis = new PerIntegrationBasis();
        perIntegrationBasis.setId(DESUtil.aesDecrypt(userIdObject.toString(),Const.ALLENCRYPTCODE));
        //注意：因为是判断当日是否存在变更情况所以把当日的日期信息送进去updatetm字段用于like比较，两者不存在完全相等关系
        perIntegrationBasis.setUpdateTm(nowDate);
        List<PerIntegrationBasis> classTypeListBasis = (List<PerIntegrationBasis>) dao.findForList("PerIntegrationBasisMapper.selectByPrimaryKey", perIntegrationBasis);
        if (classTypeListBasis != null && classTypeListBasis.size() != 0) {
            logger.info("检测到当日已经登录过，此次登陆不再加分");
            logger.info("queryScore---jsonObject.toString()---" + JSON.toJSONString(map) + "--积分未产生变化--");
            perIntegrationBasis = classTypeListBasis.get(0);
            String score = perIntegrationBasis.getScore() == null ? "" : (String) perIntegrationBasis.getScore();
            String lastTimeScore = perIntegrationBasis.getLastTimeScore() == null ? "" : (String) perIntegrationBasis.getLastTimeScore();
            //没有实际变更积分
            String updateTm = perIntegrationBasis.getUpdateTm() == null ? "" : (String) perIntegrationBasis.getUpdateTm();
            data.put("score", score);
            data.put("last_time_score", lastTimeScore);
            data.put("upd_score", updScore);
            data.put("update_tm", updateTm);
            map.setCode(Const.SUCCESS_CODE);
            contentlist.add(data);
            map.setContentlist(contentlist);
            return map;
        } else {
            //说明该用户需要进行加分操作，首先查询积分配置信息表，针对登陆分数变更规则的单位分值
            PerIntegrationConfigure perIntegrationConfigure = new PerIntegrationConfigure();
            //送入指定规则
            perIntegrationConfigure.setScoreType(Const.LOGIN_UPDSCORE);
            PageData pd = (PageData) dao.findForObject("PerIntegrationConfigureMapper.selectByexamplePage", perIntegrationConfigure);
            if (pd != null) {
                updScore = pd.getString("SCORE");
            } else {
                //还未曾配置登陆对应的分数值，使用默认分数
                updScore = Const.DEFAULT_LOGIN_UPDSCORE;
            }
            logger.info("最终经过计算实际增加的分数=" + updScore);
            //插入积分有效期信息表
            boolean efffectiveSts = false;
            try {
                map.setCode(Const.FAILURE_CODE);
                //1--查询个人基本信息表------>2--更新或者插入个人基本信息表----->3--插入积变更日志信息表
                efffectiveSts = insertEffectiveScore(DESUtil.aesDecrypt(userIdObject.toString(),Const.ALLENCRYPTCODE), status, map,
                        updScore, logger, contentlist, reqMap);
                if (!efffectiveSts) {
                    //积分有效期表插入失败
                    transactionManager.rollback(status);
                    //json格式没有被打乱不需要格式化
                    return map;
                } else {
                    map.setCode(Const.SUCCESS_CODE);
                }
            } catch (Exception e) {
                transactionManager.rollback(status);
                //json格式没有被打乱不需要格式化
                return map;
            }

            ReturnData updScoreBasisAndLog = null;
            try {
                //1--查询个人基本信息表------>2--更新或者插入个人基本信息表----->3--插入积变更日志信息表
                updScoreBasisAndLog = updScoreBasis(nowDateTime, nowDate, DESUtil.aesDecrypt(userIdObject.toString(),Const.ALLENCRYPTCODE),
                        status, map, decryptBasedDes(updTypeObject.toString()), updScore, logger, contentlist, reqMap, request);
            } catch (Exception e) {
                map.setCode(Const.FAILURE_CODE);
                transactionManager.rollback(status);
                //json格式没有被打乱不需要格式化
                return map;
            }
            return updScoreBasisAndLog;
        }
    }

    /**
     * Title: 获取登录ip:
     *
     * @author wangyanlai
     * @version 2018年12月26日 下午4:29:44
     */

    public String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            ip = request.getHeader("WL-Proxy-Client-IP");
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * Title: JSON字符串去除多余的【】符号:
     *
     * @author wangyanlai
     * @version 2018年12月26日 下午4:29:44
     */
    public String getFormatJson(String jsonReturn) {
        if (jsonReturn.contains("\\\"")) {
            jsonReturn = jsonReturn.replaceAll("\\\"", "\"");
        }
        return jsonReturn;
    }

    /**
     * Title: 查询检索个人积分基本信息表，更新插入个人积分基本信息表，调用插入积分变更日志统一方法:
     *
     * @author wangyanlai
     * @version 2018年12月26日 下午4:29:44
     */

    public ReturnData updScoreBasis(String nowDateTime, String nowDate, String userIdObject, TransactionStatus status, ReturnData map, String updTypeObject,
                                    String updScore, Logger logger, List<PageData> contentlist, Map<String, Object> reqMap, HttpServletRequest request) throws Exception {
        //用户操作日志实体
        SysOperatelog sysOperatelog = new SysOperatelog();
        sysOperatelog.setCreatetime(DateUtil.getTime());
        sysOperatelog.setIp((getIpAddress(request)));
        sysOperatelog.setLogid(UuidUtil.get32UUID());
        //用户基本信息实体
        PerIntegrationBasis perIntegrationBasis = new PerIntegrationBasis();
        perIntegrationBasis.setId(userIdObject.toString());
        //用户积分变更日志实体
        PerIntegrationLog perIntegrationLog = new PerIntegrationLog();
        perIntegrationLog.setUserId(userIdObject);
        perIntegrationLog.setScoreType(updTypeObject);
        // 课程学习，课程评论，恶意评论，分享课程
        if (Const.LOGIN_UPDSCORE.equals(updTypeObject)) {
            sysOperatelog.setLogname("学员[" + userIdObject + "]通过调用" + Const.DETAIL_LOGIN_UPDSCORE + "接口,变更积分");
            perIntegrationLog.setLogname("用户id[" + perIntegrationLog.getUserId() + "]通过," + Const.DETAIL_LOGIN_UPDSCORE + "获取积分[" + updScore + "]分;");
        } else if (Const.LEARN_UPDSCORE.equals(updTypeObject)) {
            sysOperatelog.setLogname("学员[" + userIdObject + "]通过调用" + Const.DETAIL_LEARN_UPDSCORE + "接口,变更积分");
            perIntegrationLog.setCourseId(decryptBasedDes(((Object) reqMap.get("courseid")).toString()));
            perIntegrationLog.setLogname("用户id[" + perIntegrationLog.getUserId() + "]通过," + Const.DETAIL_LEARN_UPDSCORE + "获取积分[" + updScore + "]分;");
        } else if (Const.SHARE_UPDSCORE.equals(updTypeObject)) {
            sysOperatelog.setLogname("学员[" + userIdObject + "]通过调用" + Const.DETAIL_SHARE_UPDSCORE + "接口,变更积分");
            perIntegrationLog.setCourseId(decryptBasedDes(((Object) reqMap.get("courseid")).toString()));
            perIntegrationLog.setLogname("用户id[" + perIntegrationLog.getUserId() + "]通过," + Const.DETAIL_SHARE_UPDSCORE + "获取积分[" + updScore + "]分;");
        } else if (Const.HOSTILITY_COMMENT_UPDSCORE.equals(updTypeObject)) {
            sysOperatelog.setLogname("学员[" + userIdObject + "]通过调用" + Const.DETAIL_HOSTILITY_COMMENT_UPDSCORE + "接口,变更积分");
            perIntegrationLog.setCourseId(decryptBasedDes(((Object) reqMap.get("courseid")).toString()));
            perIntegrationLog.setLogname("用户id[" + perIntegrationLog.getUserId() + "]通过," + Const.DETAIL_HOSTILITY_COMMENT_UPDSCORE + "获取积分[" + updScore + "]分;");
        } else if (Const.COMMENT_UPDSCORE.equals(updTypeObject)) {
            sysOperatelog.setLogname("学员[" + userIdObject + "]通过调用" + Const.DETAIL_COMMENT_UPDSCORE + "接口,变更积分");
            perIntegrationLog.setCourseId(decryptBasedDes(((Object) reqMap.get("courseid")).toString()));
            perIntegrationLog.setLogname("用户id[" + perIntegrationLog.getUserId() + "]通过," + Const.DETAIL_COMMENT_UPDSCORE + "获取积分[" + updScore + "]分;");
        } else if (Const.INVESTIGATION_UPDSCORE.equals(updTypeObject)) {
            // 问卷调查
            sysOperatelog.setLogname("学员[" + userIdObject + "]通过调用" + Const.DETAIL_INVESTIGATION_UPDSCORE + "接口,变更积分");
            perIntegrationLog.setQuestionnaireId(decryptBasedDes(((Object) reqMap.get("questionnaireid")).toString()));
            perIntegrationLog.setLogname("用户id[" + perIntegrationLog.getUserId() + "]通过," + Const.DETAIL_INVESTIGATION_UPDSCORE + "获取积分[" + updScore + "]分;");
        } else if (Const.TASK_UPDSCORE.equals(updTypeObject)) {
            // 作业被设为精华
            sysOperatelog.setLogname("学员[" + userIdObject + "]通过调用" + Const.DETAIL_TASK_UPDSCORE + "接口,变更积分");
            perIntegrationLog.setTaskId(decryptBasedDes(((Object) reqMap.get("taskid")).toString()));
            perIntegrationLog.setLogname("用户id[" + perIntegrationLog.getUserId() + "]通过," + Const.DETAIL_TASK_UPDSCORE + "获取积分[" + updScore + "]分;");
        } else if (Const.APPRECIATE_UPDSCORE.equals(updTypeObject)) {
            // 提问问题获得点赞
            sysOperatelog.setLogname("学员[" + userIdObject + "]通过调用" + Const.DETAIL_APPRECIATE_UPDSCORE + "接口,变更积分");
            perIntegrationLog.setQuestionId(decryptBasedDes(((Object) reqMap.get("questionid")).toString()));
            perIntegrationLog.setLogname("用户id[" + perIntegrationLog.getUserId() + "]通过," + Const.DETAIL_APPRECIATE_UPDSCORE + "获取积分[" + updScore + "]分;");
        } else if (Const.TRAINING_UPDSCORE.equals(updTypeObject)) {
            // 培训班结业
            sysOperatelog.setLogname("学员[" + userIdObject + "]通过调用" + Const.DETAIL_TRAINING_UPDSCORE + "接口,变更积分");
            perIntegrationLog.setTrainingId(decryptBasedDes(((Object) reqMap.get("trainingid")).toString()));
            perIntegrationLog.setLogname("用户id[" + perIntegrationLog.getUserId() + "]通过," + Const.DETAIL_TRAINING_UPDSCORE + "获取积分[" + updScore + "]分;");
        }
        // 添加用户操作日志
        logger.info("插入用户操作日志");
        sysOperatelog.setLogtype("0");
        sysOperatelog.setOperatetype("UPDATE");
        sysOperatelog.setModuletype("INTEGRATION");
        sysOperatelog.setOperater(userIdObject);
        sysOperatelog.setLoggertype("OPERATE");
        Object updOperaLogNum = null;
        try {
            updOperaLogNum = dao.update("SysOperatelogMapper.insert", sysOperatelog);
        } catch (Exception e) {
            logger.info("插入用户操作日志失败");
        }
        if (updOperaLogNum == null) {
            logger.info("插入用户操作日志失败");
        } else {
            logger.info("插入用户操作日志成功");
        }

        perIntegrationLog.setCreationPer(perIntegrationLog.getUserId());
        //检索个人积分表是否存在原始数据======清空，时间查询条件
        perIntegrationBasis.setUpdateTm(null);
        String originalScore = null;
        String nowScore = null;
        List<PerIntegrationBasis> classTypeListBasis = (List<PerIntegrationBasis>) dao.findForList("PerIntegrationBasisMapper.selectByPrimaryKey", perIntegrationBasis);
        if (classTypeListBasis != null && classTypeListBasis.size() != 0) {
            perIntegrationBasis = classTypeListBasis.get(0);
            //更新参数用于更新或者插入
            perIntegrationBasis.setUpdateTm(nowDateTime);
            perIntegrationBasis.setLastTimeScore(perIntegrationBasis.getScore());
            perIntegrationBasis.setTmSmp(nowDateTime);
            //兑换积分时候涉及到可能动用用户积分表原始积分，所以和其他类别略有区别
            if (Const.EXCHANGE_UPDSCORE.equals(updTypeObject)) {
                // 兑换积分
                perIntegrationLog.setExchangeId(UuidUtil.get32UUID());
                perIntegrationLog.setLogname("用户id[" + perIntegrationLog.getUserId() + "]通过," + Const.DETAIL_EXCHANGE_UPDSCORE + "获取积分[" + updScore + "]分;");
                nowScore = String.valueOf(Integer.valueOf(perIntegrationBasis.getScore() == null ? "0" : perIntegrationBasis.getScore()));
                originalScore = String.valueOf(Integer.valueOf(perIntegrationBasis.getOriginalScore() == null ? "0" : perIntegrationBasis.getOriginalScore()));
                //判断用户兑换积分大于用户的奖励积分时候，需要继续去扣除原始积分。
                //（调用该方法之前的大上文已经判断过是否兑换积分大于用户所有积分之和了，这里不再判断）
                if (Integer.valueOf(updScore.substring(1, updScore.length())) > Integer.valueOf(nowScore)) {
                    perIntegrationBasis.setScore("0");
                    perIntegrationBasis.setOriginalScore(String.valueOf(Integer.valueOf(originalScore) - (Integer.valueOf(updScore.substring(1, updScore.length())) - Integer.valueOf(nowScore))));
                } else {
                    perIntegrationBasis.setScore(String.valueOf(Integer.valueOf(perIntegrationBasis.getScore()) - Integer.valueOf(updScore.substring(1, updScore.length()))));
                }
            } else {
                if (updScore.contains("-")) {
                    if (Integer.valueOf(perIntegrationBasis.getScore()) < Integer.valueOf(updScore.substring(1, updScore.length()))) {
                        updScore = "-" + perIntegrationBasis.getScore();
                        perIntegrationBasis.setScore("0");
                        //表示
                    } else {
                        perIntegrationBasis.setScore(String.valueOf(Integer.valueOf(perIntegrationBasis.getScore()) - Integer.valueOf(updScore.substring(1, updScore.length()))));
                    }
                } else {
                    perIntegrationBasis.setScore(String.valueOf(Integer.valueOf(perIntegrationBasis.getScore() == null ? "0" : perIntegrationBasis.getScore()) + (Integer.valueOf(updScore))));
                }
            }
            try {
                //---------------------更新个人积分汇总信息表
                dao.update("PerIntegrationBasisMapper.updateByPrimaryKey", perIntegrationBasis);
            } catch (Exception e) {
                //事务回滚
                transactionManager.rollback(status);
                map.setCode(Const.FAILURE_CODE);
                return map;
            }
        } else {
            //说明该用户不存在于个人积分信息表中，需要直接初始化数据进去
            perIntegrationBasis.setCreationTm(perIntegrationBasis.getUpdateTm());
            perIntegrationBasis.setTmSmp(nowDateTime);
            //用户初始积分50分，尽可以用于日后兑换
            perIntegrationBasis.setOriginalScore(Const.ORIGINALSCORE);
            perIntegrationBasis.setScore(updScore);
            List<PerIntegrationBasis> pdIntegration = (List<PerIntegrationBasis>) dao.update("PerIntegrationBasisMapper.insertOne", perIntegrationBasis);
            if (0 != pdIntegration.size()) {
                logger.info("插入完成个人积分汇总信息表完成");
            } else {
                logger.info("插入个人积分汇总信息表失败");
                //事务回滚
                logger.info("===============事务回滚======================");
                transactionManager.rollback(status);
                map.setCode(Const.FAILURE_CODE);
                //json格式没有被打乱不需要格式化
                return map;
            }
        }
        //---------------------更新个人积分变更日志信息表
        perIntegrationLog.setScore(perIntegrationBasis.getScore());
        perIntegrationLog.setUpdScore(updScore);
        perIntegrationLog.setUserId(perIntegrationBasis.getId());
        perIntegrationLog.setId(UuidUtil.get32UUID());
        perIntegrationLog.setIp(getIpAddress(request));
        perIntegrationLog.setLastTimeScore(perIntegrationBasis.getLastTimeScore());
        String creationDt = nowDate;
        creationDt = creationDt.substring(0, 4) + creationDt.substring(5, 7) + creationDt.substring(8, 10);
        perIntegrationLog.setCreationDt(creationDt);
        perIntegrationLog.setCreationTm(DateUtil.getTime());
        perIntegrationLog.setTmSmp(nowDateTime);
        //生成新积分变更日志开始 ---插入变更日志表
        perIntegrationLog.setUpdateTm(perIntegrationBasis.getCreationTm());
        if (null == perIntegrationLog || StringUtils.isBlank(perIntegrationLog.getUserId()) ||
                StringUtils.isBlank(perIntegrationLog.getUpdScore()) ||
                StringUtils.isBlank(perIntegrationLog.getScoreType())) {
            //以上用户id,变更分数值，变更类型都是必输项
            logger.info("添加积分变更日志，必输项不足，插入失败");
            //事务回滚
            logger.info("===============事务回滚======================");
            transactionManager.rollback(status);
            //json格式没有被打乱不需要格式化
            map.setCode(Const.FAILURE_CODE);
            map.setContentlist(contentlist);
            return map;
        }
        try {
            dao.update("PerIntegrationLogMapper.insertOne", perIntegrationLog);
        } catch (Exception e) {
            logger.info("添加积分变更日志失败");
            //事务回滚
            logger.info("===============事务回滚======================");
            transactionManager.rollback(status);
            //json格式没有被打乱不需要格式化
            e.printStackTrace();
        }
        logger.info("添加积分变更日志完成");
        //返回更新成功参数数据给前台
        PageData data = new PageData();
        data.put(userIdField, encryptBasedDes(userIdObject.toString()));
        data.put("score", perIntegrationBasis.getScore());
        data.put("last_time_score", perIntegrationBasis.getLastTimeScore());
        data.put("upd_score", updScore);
        data.put("update_tm", perIntegrationBasis.getUpdateTm());
        map.setCode(Const.SUCCESS_CODE);
        contentlist.add(data);
        map.setContentlist(contentlist);
        return map;
    }

    public List<PerCourseIntegration> qryCourseIntegrationList(PerCourseIntegration perCourseIntegration, Logger logger, Map<String, Object> reqMap) throws Exception {
        perCourseIntegration.setUserId(decryptBasedDes(((Object) reqMap.get(userIdField)).toString()));
        perCourseIntegration.setId(decryptBasedDes(((Object) reqMap.get("courseid")).toString()));
        logger.info("查询课程积分表是否已经存在记录");
        List<PerCourseIntegration> classTypeListCourseLearn = (List<PerCourseIntegration>) dao.findForList("PerCourseIntegrationMapper.selectByPrimaryKey", perCourseIntegration);
        return classTypeListCourseLearn;
    }

    /**
     * Title: 对奖励积分有效期表进行插入:
     *
     * @author wangyanlai
     * @version 2018年12月26日 下午4:29:44
     * wangyanlai@cei.gov.cn
     */

    public boolean subEffectiveScore(String loseEfficacy, String userIdObject, TransactionStatus status, ReturnData map,
                                     String updScore, Logger logger, List<PageData> contentlist, Map<String, Object> reqMap) throws Exception {
        EffectiveIntegral effectiveIntegral = new EffectiveIntegral();
        // 查询所有有效日期内的奖励积分，计算奖励积分是否于兑换积分的关系，当奖励积分大于等于兑换积分时候，
        // 将当前奖励积分所在日期之前的积分全部置为失效，对当前日期的奖励积分进行update
        String endDt = DateUtil.getDay();
        endDt = endDt.substring(0, 4) + endDt.substring(5, 7) + endDt.substring(8, 10);
        effectiveIntegral.setEndDt(endDt);
        effectiveIntegral.setUserId(userIdObject);
        Map<String, Object> updMap = new HashMap();
        logger.info("有效积分表的分数值汇总数据tableScore,进行扣除");
        //有效积分表的分数值汇总数据tableScore
        int tableScore = 0;
        updScore = updScore.substring(1, updScore.length());
        EffectiveIntegral effectiveIntegralHistory = new EffectiveIntegral();
        if (Const.LOSEEFFICACY_YES.equals(loseEfficacy)) {
            effectiveIntegralHistory.setUserId(userIdObject);
            effectiveIntegralHistory.setIsEnabled(Const.ISENABLED_NO);
            try {
                //---------------------更新个人有效积分信息表
                dao.update("EffectiveIntegralMapper.updateByPrimaryKeyForLose", effectiveIntegralHistory);
            } catch (Exception e) {
                //事务回滚
                map.setCode(Const.FAILURE_CODE);
                return false;
            }
        } else {
            List<EffectiveIntegral> classTypeListBasis = (List<EffectiveIntegral>) dao.findForList("EffectiveIntegralMapper.selectByexamplePage", effectiveIntegral);
            if (classTypeListBasis != null && classTypeListBasis.size() != 0) {
                for (int i = 0; i < classTypeListBasis.size(); i++) {
                    effectiveIntegralHistory = classTypeListBasis.get(0);
                    tableScore = tableScore + Integer.valueOf(effectiveIntegralHistory.getScore());
                    if (tableScore > Integer.valueOf(updScore)) {
                        effectiveIntegralHistory.setScore(String.valueOf(tableScore - Integer.valueOf(updScore)));
                        try {
                            //---------------------更新个人有效积分信息表
                            dao.update("EffectiveIntegralMapper.updateByPrimaryKeySelective", effectiveIntegralHistory);
                        } catch (Exception e) {
                            //事务回滚
                            map.setCode(Const.FAILURE_CODE);
                            return false;
                        }
                        //当表中金额加起来之和大于兑换金额时候直接跳出循环
                        break;
                    } else {
                        updMap.put("id" + i, effectiveIntegralHistory.getId());
                    }
                }
            }
            if (updMap.size() > 0) {
                for (int i = 0; i < updMap.size(); i++) {
                    effectiveIntegralHistory.setId((String) updMap.get("id" + i));
                    effectiveIntegralHistory.setIsEnabled(Const.ISENABLED_NO);
                    //去除用户号作为条件，否则不兼容
                    effectiveIntegralHistory.setUserId("");
                    try {
                        //---------------------更新个人有效积分信息表
                        dao.update("EffectiveIntegralMapper.updateByPrimaryKeyForLose", effectiveIntegralHistory);
                    } catch (Exception e) {
                        //事务回滚
                        map.setCode(Const.FAILURE_CODE);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Title: 对奖励积分有效期表进行插入:
     *
     * @author wangyanlai
     * @version 2018年12月26日 下午4:29:44
     * wangyanlai@cei.gov.cn
     */
    public boolean insertEffectiveScore(String userIdObject, TransactionStatus status, ReturnData map,
                                        String updScore, Logger logger, List<PageData> contentlist, Map<String, Object> reqMap) throws Exception {
        PerIntegrationConfigure perIntegrationConfigure = new PerIntegrationConfigure();
        EffectiveIntegral effectiveIntegral = new EffectiveIntegral();
        perIntegrationConfigure.setScoreType(Const.ACTUAL_EFFECT_TIME);
        //如果奖励积分为0或者空直接返回成功
        if (StringUtils.isBlank(updScore) || "0".equals(updScore)) {
            return true;
        }
        //系统默认有效期天数30天
        String days = Const.DEFAULT_ACTUAL_EFFECT_TIME;
        PageData pd = (PageData) dao.findForObject("PerIntegrationConfigureMapper.selectByexamplePage", perIntegrationConfigure);
        if (pd != null) {
            days = pd.getString("SCORE");
        }
        //这里获得有效期截止日期
        String effectiveDay = getAfterDayDate(days);
        Date effectiveDayDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            effectiveDayDate = sdf.parse(effectiveDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf = new SimpleDateFormat("yyyyMMdd");
        effectiveIntegral.setEndDt(sdf.format(effectiveDayDate));
        effectiveIntegral.setCreationDt(DateUtil.getDay());
        effectiveIntegral.setCreationTm(DateUtil.getTime());
        effectiveIntegral.setId(UuidUtil.get32UUID());
        effectiveIntegral.setUpdateTm(DateUtil.getTime());
        effectiveIntegral.setUserId(userIdObject);
        effectiveIntegral.setIsEnabled(Const.ISENABLED_YES);
        effectiveIntegral.setDays(days);
        effectiveIntegral.setScore(updScore);
        effectiveIntegral.setTmSmp(DateUtil.getTime());
        List<EffectiveIntegral> classTypeListBasis = (List<EffectiveIntegral>) dao.findForList("EffectiveIntegralMapper.selectByPrimaryKey", effectiveIntegral);
        if (classTypeListBasis != null && classTypeListBasis.size() != 0) {
            EffectiveIntegral effectiveIntegralHistory = new EffectiveIntegral();
            effectiveIntegralHistory = classTypeListBasis.get(0);
            updScore = String.valueOf(Integer.valueOf(updScore) + Integer.valueOf(effectiveIntegralHistory.getScore()));
            effectiveIntegralHistory.setScore(updScore);
            try {
                //---------------------更新个人有效积分信息表
                dao.update("EffectiveIntegralMapper.updateByPrimaryKeySelective", effectiveIntegralHistory);
            } catch (Exception e) {
                //事务回滚
                map.setCode(Const.FAILURE_CODE);
                return false;
            }
        } else {
            try {
                //---------------------更新个人有效积分信息表
                dao.update("EffectiveIntegralMapper.insert", effectiveIntegral);
            } catch (Exception e) {
                //事务回滚
                map.setCode(Const.FAILURE_CODE);
                return false;
            }
        }
        return true;
    }
}
