package org.spring.springboot.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.spring.springboot.dao.DaoSupport;
import org.spring.springboot.domain.*;
import org.spring.springboot.service.IntegrationUpdService;
import org.spring.springboot.service.QuestionUpdService;
import org.spring.springboot.zw.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.apache.commons.lang3.StringUtils.isNumeric;
import static org.spring.springboot.zw.util.Const.FILE_PATH;
import static org.spring.springboot.zw.util.Const.PAGE;
import static org.spring.springboot.zw.util.DESUtil.aesDecrypt;
import static org.spring.springboot.zw.util.DESUtil.decryptBasedDes;
import static org.spring.springboot.zw.util.DESUtil.encryptBasedDes;
import static org.spring.springboot.zw.util.DateUtil.getAfterDayDate;
import static org.spring.springboot.zw.util.UuidUtil.get32UUID;

/**
 * Title: APPServiceImpl Description:
 *
 * @author wangyanlai
 * @version 2019年1月26日 上午8:31:56
 */

@Service
@Transactional()
public class QuestionUpdServiceImpl implements QuestionUpdService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;
    @Autowired
    private DataSourceTransactionManager transactionManager;
    private String updTypeField = "updtype";
    private String userIdField = "userid";

    //变更问题类别信息表信息
    @Override
    public String updTreeGrid(Map<String, Object> reqMap, Logger logger, HttpServletRequest request) {
        ReturnData map = new ReturnData();
        //初始化事务参数配置
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //隔离级别为 PROPAGATION_NESTED--如果当前存在事务，则在嵌套事务内执行。
        //如果当前没有事务，则进行与PROPAGATION_REQUIRED(新创事务)类似的操作。
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
        TransactionStatus transStatus = transactionManager.getTransaction(def);
        QuestionTypeInfo questionTypeInfo = new QuestionTypeInfo();
        Object status = reqMap.get("status");
        if (StringUtil.isEmpty(status)) {
            questionTypeInfo.setStatus(((Object) reqMap.get("status")).toString());
        } else {
            questionTypeInfo.setStatus(Const.STATUS_YES);
        }
        Object pid = reqMap.get("pid");
        Object name = reqMap.get("name");
        if (StringUtil.isEmpty(name)) {
            questionTypeInfo.setQuestionTypeName(((Object) reqMap.get("name")).toString());
        }
        questionTypeInfo.setIp(getIpAddress(request));
        questionTypeInfo.setUpdateTm(DateUtil.getTime());
        questionTypeInfo.setTmSmp(DateUtil.getTime());
        try {
            if (StringUtil.isEmpty(pid)) {
                questionTypeInfo.setFatherId(DESUtil.aesDecrypt(((Object) reqMap.get("pid")).toString(), Const.ALLENCRYPTCODE));
            }
            //注意：因为id前文已经判断是否为空，所以这里直接使用
            questionTypeInfo.setId(DESUtil.aesDecrypt(((Object) reqMap.get("id")).toString(), Const.ALLENCRYPTCODE));
        } catch (Exception e) {
            logger.info("验证id失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(transStatus);
            //json格式没有被打乱不需要格式化
            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            logger.info("updQuestionType---jsonObject.toString()---" + jsonReturn + "---");
            return jsonReturn;
        }

        logger.info("变更问题类别信息表开始");
        Object updNum = null;
        map.setCode(Const.FAILURE_CODE);
        try {
            updNum = dao.update("QuestionTypeInfoMapper.updateByPrimaryKeySelective", questionTypeInfo);
            if (null == updNum) {
                //json格式没有被打乱不需要格式化
                logger.info("更新问题类别信息表失败");
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("updQuestionType---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
            String newUserId = null;
            Object useridObj = reqMap.get("userid");
            if (StringUtil.isEmpty(useridObj)) {
                newUserId = aesDecrypt(useridObj.toString(), Const.ALLENCRYPTCODE);
            } else {
                newUserId = aesDecrypt((String) request.getSession().getAttribute("sessionUserId"), Const.ALLENCRYPTCODE);
            }
            insertSysOperatelog(newUserId, Const.UPDATA_QUESTION_TYPE, logger, request);
        } catch (Exception e) {
            logger.info("更新问题类别信息表失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(transStatus);
            //json格式没有被打乱不需要格式化
            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            logger.info("updQuestionType---jsonObject.toString()---" + jsonReturn + "---");
            return jsonReturn;
        }
        logger.info("更新问题类别信息表成功");
        map.setCode(Const.SUCCESS_CODE);
        //json格式没有被打乱不需要格式化
        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        logger.info("updConfig---jsonObject.toString()---" + jsonReturn + "---");
        return jsonReturn;
    }

    //新增问题类别信息表信息
    @Override
    public String addTreeGrid(Map<String, Object> reqMap, Logger logger, HttpServletRequest request) {
        ReturnData map = new ReturnData();
        //初始化事务参数配置
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //隔离级别为 PROPAGATION_NESTED--如果当前存在事务，则在嵌套事务内执行。
        //如果当前没有事务，则进行与PROPAGATION_REQUIRED(新创事务)类似的操作。
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
        TransactionStatus transStatus = transactionManager.getTransaction(def);
        QuestionTypeInfo questionTypeInfo = new QuestionTypeInfo();
        Object status = reqMap.get("status");
        if (StringUtil.isEmpty(status)) {
            questionTypeInfo.setStatus(((Object) reqMap.get("status")).toString());
        } else {
            questionTypeInfo.setStatus(Const.STATUS_YES);
        }
        Object pid = reqMap.get("pid");
        //注意：因为id前文已经判断是否为空，所以这里直接使用
        questionTypeInfo.setIp(getIpAddress(request));
        questionTypeInfo.setUpdateTm(DateUtil.getTime());
        questionTypeInfo.setTmSmp(DateUtil.getTime());
        questionTypeInfo.setCreationDt(DateUtil.getDay());
        Object name = reqMap.get("name");
        if (StringUtil.isEmpty(name)) {
            questionTypeInfo.setQuestionTypeName(((Object) reqMap.get("name")).toString());
        }
        questionTypeInfo.setCreationDt(DateUtil.getDay());
        Object creationPer = reqMap.get("creation_per");
        questionTypeInfo.setCreationTm(DateUtil.getTime());
        questionTypeInfo.setId(get32UUID());
        logger.info("添加问题类别信息表信息开始");
        Object updNum = null;
        map.setCode(Const.FAILURE_CODE);
        try {
            if (StringUtil.isEmpty(creationPer)) {
                questionTypeInfo.setCreationPer(DESUtil.aesDecrypt(creationPer.toString(),
                        Const.ALLENCRYPTCODE));
            } else {
                questionTypeInfo.setCreationPer("SYSTEM_PER");
            }
            if (StringUtil.isEmpty(pid)) {
                questionTypeInfo.setFatherId(DESUtil.aesDecrypt(((Object) reqMap.get("pid")).toString(), Const.ALLENCRYPTCODE));
            }
            updNum = dao.update("QuestionTypeInfoMapper.insertSelective", questionTypeInfo);
            if (null == updNum) {
                //json格式没有被打乱不需要格式化
                logger.info("添加问题类别配置信息表失败");
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("updQuestionType---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
            String newUserId = null;
            Object useridObj = reqMap.get("userid");
            if (StringUtil.isEmpty(useridObj)) {
                newUserId = aesDecrypt(useridObj.toString(), Const.ALLENCRYPTCODE);
            } else {
                newUserId = aesDecrypt((String) request.getSession().getAttribute("sessionUserId"), Const.ALLENCRYPTCODE);
            }
            insertSysOperatelog(newUserId, Const.ADD_QUESTION_TYPE, logger, request);
        } catch (Exception e) {
            logger.info("新增问题类别配置信息表失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(transStatus);
            //json格式没有被打乱不需要格式化
            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            logger.info("addTreeGrad---jsonObject.toString()---" + jsonReturn + "---");
            return jsonReturn;
        }
        logger.info("新增问题类别配置信息表成功");
        map.setCode(Const.SUCCESS_CODE);
        //json格式没有被打乱不需要格式化
        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        logger.info("addTreeGrad---jsonObject.toString()---" + jsonReturn + "---");
        return jsonReturn;
    }

    //删除问题类别信息表信息
    @Override
    public String delTreeGrid(Map<String, Object> reqMap, Logger logger, HttpServletRequest request) {
        ReturnData map = new ReturnData();
        //初始化事务参数配置
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //隔离级别为 PROPAGATION_NESTED--如果当前存在事务，则在嵌套事务内执行。
        //如果当前没有事务，则进行与PROPAGATION_REQUIRED(新创事务)类似的操作。
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
        TransactionStatus transStatus = transactionManager.getTransaction(def);
        QuestionTypeInfo questionTypeInfo = new QuestionTypeInfo();
        map.setCode(Const.EMPTY_CODE);
        Object id = reqMap.get("id");
        if (null != id) {
            try {
                questionTypeInfo.setId(DESUtil.aesDecrypt(((Object) reqMap.get("id")).toString(),
                        Const.ALLENCRYPTCODE));
            } catch (Exception e) {
                logger.info("验证id失败");
                //事务回滚
                logger.info("=====================事务回滚======================");
                transactionManager.rollback(transStatus);
                //json格式没有被打乱不需要格式化
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("updQuestionType---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
        }
        logger.info("删除问题类别信息表相应数据开始");
        Object updNum = null;
        map.setCode(Const.FAILURE_CODE);
        try {
            updNum = dao.update("QuestionTypeInfoMapper.deleteByPrimaryKeyAndPid", questionTypeInfo);
            if (null == updNum || "0".equals(updNum.toString())) {
                //json格式没有被打乱不需要格式化
                logger.info("删除问题类别配置信息表失败");
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("updQuestionType---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
            String newUserId = null;
            Object useridObj = reqMap.get("userid");
            if (StringUtil.isEmpty(useridObj)) {
                newUserId = aesDecrypt(useridObj.toString(), Const.ALLENCRYPTCODE);
            } else {
                newUserId = aesDecrypt((String) request.getSession().getAttribute("sessionUserId"), Const.ALLENCRYPTCODE);
            }
            insertSysOperatelog(newUserId, Const.DELETE_QUESTION_TYPE, logger, request);
        } catch (Exception e) {
            logger.info("删除问题类别配置信息表失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(transStatus);
            //json格式没有被打乱不需要格式化
            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            logger.info("updQuestionType---jsonObject.toString()---" + jsonReturn + "---");
            return jsonReturn;
        }
        logger.info("删除问题类别配置信息表成功");
        map.setCode(Const.SUCCESS_CODE);
        //json格式没有被打乱不需要格式化
        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        logger.info("updConfig---jsonObject.toString()---" + jsonReturn + "---");
        return jsonReturn;
    }


    //删除问题基本信息
    @Override
    public String delQuestion(Map<String, Object> reqMap, Logger logger, HttpServletRequest request) {
        ReturnData map = new ReturnData();
        //初始化事务参数配置
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //隔离级别为 PROPAGATION_NESTED--如果当前存在事务，则在嵌套事务内执行。
        //如果当前没有事务，则进行与PROPAGATION_REQUIRED(新创事务)类似的操作。
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
        TransactionStatus transStatus = transactionManager.getTransaction(def);
        HelpQuestionInformation helpQuestionInformation = new HelpQuestionInformation();
        map.setCode(Const.EMPTY_CODE);
        Object id = reqMap.get("id");
        Object useridObj = reqMap.get("userid");
        if (null != id) {
            try {
                if(StringUtil.isEmpty(useridObj)){
                helpQuestionInformation.setUserId(DESUtil.aesDecrypt(useridObj.toString(),
                        Const.ALLENCRYPTCODE));
                }
                helpQuestionInformation.setId(DESUtil.aesDecrypt(id.toString(),
                        Const.ALLENCRYPTCODE));
            } catch (Exception e) {
                logger.info("验证id失败");
                //事务回滚
                logger.info("=====================事务回滚======================");
                transactionManager.rollback(transStatus);
                //json格式没有被打乱不需要格式化
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("updQuestionType---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
        }
        logger.info("删除问题信息表相应数据开始");
        Object updNum = null;
        map.setCode(Const.FAILURE_CODE);
        try {
            updNum = dao.update("HelpQuestionInformationMapper.deleteByPrimaryKey", helpQuestionInformation);
            if (null == updNum || "0".equals(updNum.toString())) {
                //json格式没有被打乱不需要格式化
                logger.info("删除问题信息表失败");
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("updQuestionType---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
            String newUserId = null;
            useridObj = reqMap.get("userid");
            if (StringUtil.isEmpty(useridObj)) {
                newUserId = aesDecrypt(useridObj.toString(), Const.ALLENCRYPTCODE);
            } else {
                newUserId = aesDecrypt((String) request.getSession().getAttribute("sessionUserId"), Const.ALLENCRYPTCODE);
            }
            insertSysOperatelog(newUserId, Const.DELETE_QUESTION, logger, request);
        } catch (Exception e) {
            logger.info("删除问题信息表失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(transStatus);
            //json格式没有被打乱不需要格式化
            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            logger.info("updQuestionType---jsonObject.toString()---" + jsonReturn + "---");
            return jsonReturn;
        }
        logger.info("删除问题信息表成功");
        map.setCode(Const.SUCCESS_CODE);
        //json格式没有被打乱不需要格式化
        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        logger.info("updConfig---jsonObject.toString()---" + jsonReturn + "---");
        return jsonReturn;
    }


    //更新问题基本信息
    @Override
    public String updQuestion(Map<String, Object> reqMap, Logger logger, HttpServletRequest request) {
        ReturnData map = new ReturnData();
        //初始化事务参数配置
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //隔离级别为 PROPAGATION_NESTED--如果当前存在事务，则在嵌套事务内执行。
        //如果当前没有事务，则进行与PROPAGATION_REQUIRED(新创事务)类似的操作。
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
        TransactionStatus transStatus = transactionManager.getTransaction(def);
        HelpQuestionInformation helpQuestionInformation = new HelpQuestionInformation();
        helpQuestionInformation.setIp(getIpAddress(request));
        helpQuestionInformation.setUpdateTm(DateUtil.getTime());
        helpQuestionInformation.setTmSmp(DateUtil.getTime());
        map.setCode(Const.EMPTY_CODE);
        Object creationPer = reqMap.get("creation_per");
        Object numberPoints = reqMap.get("number_points");
        if (StringUtil.isEmpty(numberPoints)) {
            helpQuestionInformation.setNumberPoints(numberPoints.toString());
        }
        Object numberBricks = reqMap.get("number_bricks");
        if (StringUtil.isEmpty(numberBricks)) {
            helpQuestionInformation.setNumberBricks(numberBricks.toString());
        }
        Object visualRange = reqMap.get("visual_range");
        if (StringUtil.isEmpty(visualRange)) {
            helpQuestionInformation.setVisualRange(visualRange.toString());
        }
        Object clickNumber = reqMap.get("click_number");
        if (StringUtil.isEmpty(clickNumber)) {
            helpQuestionInformation.setClickNumber(clickNumber.toString());
        }
        Object questionTypeId = reqMap.get("question_type_id");
        Object ifRelease = reqMap.get("if_release");
        //默认发布时间为空
        helpQuestionInformation.setReleaseTm("");
        if (StringUtil.isEmpty(ifRelease)) {
            if (Const.IF_RELEASE_YES.equals(ifRelease)) {
                helpQuestionInformation.setReleaseTm(DateUtil.getTime());
            }
            helpQuestionInformation.setIfRelease(ifRelease.toString());
        } else {
            helpQuestionInformation.setIfRelease(Const.IF_RELEASE_NO);
        }

        Object ifCommon = reqMap.get("if_common");
        if (StringUtil.isEmpty(ifCommon)) {
            helpQuestionInformation.setIfCommon(ifCommon.toString());
        }
        Object questionKeywords = reqMap.get("question_keywords");
        if (StringUtil.isEmpty(questionKeywords)) {
            helpQuestionInformation.setQuestionKeywords(questionKeywords.toString());
        }
        Object status = reqMap.get("status");
        if (StringUtil.isEmpty(status)) {
            helpQuestionInformation.setStatus(status.toString());
        }
        Object questionContent = reqMap.get("question_content");
        if (StringUtil.isEmpty(questionContent)) {
            helpQuestionInformation.setQuestionContent(questionContent.toString());
        }
        Object remark1 = reqMap.get("remark1");
        if (StringUtil.isEmpty(remark1)) {
            helpQuestionInformation.setRemark1(remark1.toString());
        }


        Object id = reqMap.get("id");
        Object userId = reqMap.get("userid");
        if (null != id) {
            try {
                helpQuestionInformation.setId(DESUtil.aesDecrypt(id.toString(),
                        Const.ALLENCRYPTCODE));
                if (StringUtil.isEmpty(userId)) {
                    //如果传入了userid,说明是外面的接口送进来的，必须通过用户id校验才能够修改问题信息。
                    helpQuestionInformation.setUserId(DESUtil.aesDecrypt(userId.toString(),
                            Const.ALLENCRYPTCODE));
                }
                if (StringUtil.isEmpty(questionTypeId)) {
                    helpQuestionInformation.setQuestionTypeId(DESUtil.aesDecrypt(questionTypeId.toString(),
                            Const.ALLENCRYPTCODE));
                }
                if (StringUtil.isEmpty(creationPer)) {
                    helpQuestionInformation.setCreationPer(DESUtil.aesDecrypt(creationPer.toString(),
                            Const.ALLENCRYPTCODE));
                }
            } catch (Exception e) {
                logger.info("验证id失败");
                //事务回滚
                logger.info("=====================事务回滚======================");
                transactionManager.rollback(transStatus);
                //json格式没有被打乱不需要格式化
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("updQuestionType---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
        }
        logger.info("修改问题信息表相应数据开始");
        Object updNum = null;
        map.setCode(Const.FAILURE_CODE);
        try {
            helpQuestionInformation.setCreationTm(null);
            helpQuestionInformation.setCreationDt(null);
            helpQuestionInformation.setCreationPer(null);
            updNum = dao.update("HelpQuestionInformationMapper.updateByPrimaryKeySelective", helpQuestionInformation);
            if (null == updNum || "0".equals(updNum.toString())) {
                //json格式没有被打乱不需要格式化
                logger.info("修改问题信息表失败");
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("updQuestionType---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
            String newUserId = null;
            Object useridObj = reqMap.get("userid");
            if (StringUtil.isEmpty(useridObj)) {
                newUserId = aesDecrypt(useridObj.toString(), Const.ALLENCRYPTCODE);
            } else {
                newUserId = aesDecrypt((String) request.getSession().getAttribute("sessionUserId"), Const.ALLENCRYPTCODE);
            }
            insertSysOperatelog(newUserId, Const.UPDATA_QUESTION, logger, request);
        } catch (Exception e) {
            logger.info("修改问题信息表失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(transStatus);
            //json格式没有被打乱不需要格式化
            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            logger.info("updQuestionType---jsonObject.toString()---" + jsonReturn + "---");
            return jsonReturn;
        }
        logger.info("修改问题信息表成功");
        map.setCode(Const.SUCCESS_CODE);
        //json格式没有被打乱不需要格式化
        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        logger.info("updConfig---jsonObject.toString()---" + jsonReturn + "---");
        return jsonReturn;
    }

    //更新问题基本信息其他(点赞，点击，拍砖)
    @Override
    public String updQuestionOther(Map<String, Object> reqMap, Logger logger, HttpServletRequest request) {
        ReturnData map = new ReturnData();
        //初始化事务参数配置
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //隔离级别为 PROPAGATION_NESTED--如果当前存在事务，则在嵌套事务内执行。
        //如果当前没有事务，则进行与PROPAGATION_REQUIRED(新创事务)类似的操作。
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
        TransactionStatus transStatus = transactionManager.getTransaction(def);
        HelpQuestionInformation helpQuestionInformation = new HelpQuestionInformation();
        helpQuestionInformation.setIp(getIpAddress(request));
        helpQuestionInformation.setUpdateTm(DateUtil.getTime());
        helpQuestionInformation.setTmSmp(DateUtil.getTime());
        map.setCode(Const.EMPTY_CODE);
        PageData qureyData = new PageData();
        Page page = new Page();
        Object questionId = reqMap.get("questionid");
        String clickNumber = "";
        String numberPoints = "";
        String numberBricks = "";
        if (StringUtil.isEmpty(questionId)) {
            try {
                qureyData.put("id", DESUtil.aesDecrypt(questionId.toString(), Const.ALLENCRYPTCODE));
                String pageSizeStr = "1";
                String pageNumStr = "1";
                qureyData = getPage(qureyData, pageSizeStr, pageNumStr);
                page.setPd(qureyData);
                /*查询问题汇总信息表*/
                List<PageData> classTypeList0 = (List<PageData>) dao.findForList("HelpQuestionInformationMapper.selectByexamplePage", page);
                if (classTypeList0 != null && classTypeList0.size() != 0) {
                    HashMap tempmap = (HashMap) classTypeList0.get(0);
                    clickNumber = tempmap.get("CLICK_NUMBER") == null ? "" : (String) tempmap.get("CLICK_NUMBER");
                    numberPoints = tempmap.get("NUMBER_POINTS") == null ? "" : (String) tempmap.get("NUMBER_POINTS");
                    numberBricks = tempmap.get("NUMBER_BRICKS") == null ? "" : (String) tempmap.get("NUMBER_BRICKS");
                }
                Object updType = reqMap.get("updtype");
                helpQuestionInformation.setId(DESUtil.aesDecrypt(questionId.toString(), Const.ALLENCRYPTCODE));
                if (StringUtil.isEmpty(updType)) {
                    if (Const.TYPE1.equals(updType.toString())) {
                        helpQuestionInformation.setNumberPoints(String.valueOf(Integer.valueOf(numberPoints) + 1));
                    } else if (Const.TYPE2.equals(updType.toString())) {
                        helpQuestionInformation.setClickNumber(String.valueOf(Integer.valueOf(clickNumber) + 1));
                    } else if (Const.TYPE3.equals(updType.toString())) {
                        helpQuestionInformation.setNumberBricks(String.valueOf(Integer.valueOf(numberBricks) + 1));
                    }
                }
                Object updNum = null;
                map.setCode(Const.FAILURE_CODE);
                updNum = dao.update("HelpQuestionInformationMapper.updateByPrimaryKeySelective", helpQuestionInformation);
                if (null == updNum || "0".equals(updNum.toString())) {
                    //json格式没有被打乱不需要格式化
                    logger.info("修改问题信息表失败(点赞点击拍砖)");
                    String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                    logger.info("updQuestionType---jsonObject.toString()---" + jsonReturn + "---");
                    return jsonReturn;
                }
                String newUserId = null;
                Object useridObj = reqMap.get("userid");
                if (StringUtil.isEmpty(useridObj)) {
                    newUserId = aesDecrypt(useridObj.toString(), Const.ALLENCRYPTCODE);
                } else {
                    newUserId = aesDecrypt((String) request.getSession().getAttribute("sessionUserId"), Const.ALLENCRYPTCODE);
                }
                insertSysOperatelog(newUserId, Const.UPDATA_QUESTION, logger, request);
            } catch (Exception e) {
                logger.info("验证id失败");
                //事务回滚
                logger.info("=====================事务回滚======================");
                transactionManager.rollback(transStatus);
                //json格式没有被打乱不需要格式化
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("updQuestionOther---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
        }
        logger.info("修改问题信息表成功(点赞点击拍砖)");
        map.setCode(Const.SUCCESS_CODE);
        //json格式没有被打乱不需要格式化
        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        logger.info("updConfig---jsonObject.toString()---" + jsonReturn + "---");
        return jsonReturn;
    }

    //新增问题基本信息
    @Override
    public String addQuestion(Map<String, Object> reqMap, Logger logger, HttpServletRequest request) {
        ReturnData map = new ReturnData();
        //初始化事务参数配置
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //隔离级别为 PROPAGATION_NESTED--如果当前存在事务，则在嵌套事务内执行。
        //如果当前没有事务，则进行与PROPAGATION_REQUIRED(新创事务)类似的操作。
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
        TransactionStatus transStatus = transactionManager.getTransaction(def);
        HelpQuestionInformation helpQuestionInformation = new HelpQuestionInformation();
        map.setCode(Const.EMPTY_CODE);
        helpQuestionInformation.setCreationDt(DateUtil.getTime());
        helpQuestionInformation.setTmSmp(DateUtil.getTime());
        helpQuestionInformation.setCreationDt(DateUtil.getDay());
        helpQuestionInformation.setCreationTm(DateUtil.getTime());
        Object creationPer = reqMap.get("creation_per");
        Object numberPoints = reqMap.get("number_points");
        if (StringUtil.isEmpty(numberPoints)) {
            helpQuestionInformation.setNumberPoints(numberPoints.toString());
        }
        Object numberBricks = reqMap.get("number_bricks");
        if (StringUtil.isEmpty(numberBricks)) {
            helpQuestionInformation.setNumberBricks(numberBricks.toString());
        }
        Object visualRange = reqMap.get("visual_range");
        if (StringUtil.isEmpty(visualRange)) {
            helpQuestionInformation.setVisualRange(visualRange.toString());
        }
        Object clickNumber = reqMap.get("click_number");
        if (StringUtil.isEmpty(clickNumber)) {
            helpQuestionInformation.setClickNumber(clickNumber.toString());
        }
        Object questionTypeId = reqMap.get("question_type_id");
        Object ifRelease = reqMap.get("if_release");
        //默认发布时间为空
        helpQuestionInformation.setReleaseTm("");
        if (StringUtil.isEmpty(ifRelease)) {
            if (Const.IF_RELEASE_YES.equals(ifRelease)) {
                helpQuestionInformation.setReleaseTm(DateUtil.getTime());
            }
            helpQuestionInformation.setIfRelease(ifRelease.toString());
        } else {
            helpQuestionInformation.setIfRelease(Const.IF_RELEASE_NO);
        }

        Object ifCommon = reqMap.get("if_common");
        if (StringUtil.isEmpty(ifCommon)) {
            helpQuestionInformation.setIfCommon(ifCommon.toString());
        }
        Object questionKeywords = reqMap.get("question_keywords");
        if (StringUtil.isEmpty(questionKeywords)) {
            helpQuestionInformation.setQuestionKeywords(questionKeywords.toString());
        }
        Object status = reqMap.get("status");
        if (StringUtil.isEmpty(status)) {
            helpQuestionInformation.setStatus(status.toString());
        }
        Object questionContent = reqMap.get("question_content");
        if (StringUtil.isEmpty(questionContent)) {
            helpQuestionInformation.setQuestionContent(questionContent.toString());
        }
        Object remark1 = reqMap.get("remark1");
        if (StringUtil.isEmpty(remark1)) {
            helpQuestionInformation.setRemark1(remark1.toString());
        }
        Object userId = reqMap.get("userid");
        helpQuestionInformation.setId(get32UUID());
        helpQuestionInformation.setIp(getIpAddress(request));
        helpQuestionInformation.setUpdateTm(DateUtil.getTime());
        String newUserId = (String) request.getSession().getAttribute("sessionUserId");
        try {
            if (StringUtil.isEmpty(creationPer)) {
                helpQuestionInformation.setCreationPer(DESUtil.aesDecrypt(creationPer.toString(),
                        Const.ALLENCRYPTCODE));
            } else if (StringUtil.isEmpty(userId)) {
                helpQuestionInformation.setCreationPer(DESUtil.aesDecrypt(userId.toString(),
                        Const.ALLENCRYPTCODE));
            } else {
                helpQuestionInformation.setCreationPer(DESUtil.aesDecrypt(newUserId,
                        Const.ALLENCRYPTCODE));
            }
            if (StringUtil.isEmpty(questionTypeId)) {
                helpQuestionInformation.setQuestionTypeId(DESUtil.aesDecrypt(questionTypeId.toString(),
                        Const.ALLENCRYPTCODE));
            }
            if (StringUtil.isEmpty(newUserId) || "".equals(newUserId)) {
                if (StringUtil.isEmpty(userId)) {
                    helpQuestionInformation.setUserId(DESUtil.aesDecrypt(userId.toString(),
                            Const.ALLENCRYPTCODE));
                } else {
                    //系统管理员录入相关信息使用session 的userid
                    helpQuestionInformation.setUserId(DESUtil.aesDecrypt(newUserId.toString(),
                            Const.ALLENCRYPTCODE));
                }
            } else if (StringUtil.isEmpty(userId)) {
                helpQuestionInformation.setUserId(DESUtil.aesDecrypt(userId.toString(),
                        Const.ALLENCRYPTCODE));
            }
        } catch (Exception e) {
            logger.info("验证id失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(transStatus);
            //json格式没有被打乱不需要格式化
            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            logger.info("addQuestionType---jsonObject.toString()---" + jsonReturn + "---");
            return jsonReturn;
        }
        logger.info("新增问题信息表相应数据开始");
        Object updNum = null;
        map.setCode(Const.FAILURE_CODE);
        try {
            updNum = dao.update("HelpQuestionInformationMapper.insert", helpQuestionInformation);
            if (null == updNum || "0".equals(updNum.toString())) {
                //json格式没有被打乱不需要格式化
                logger.info("新增问题信息表失败");
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("addQuestionType---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
            if (!((null != userId && Const.SPECIAL_USERID.equals(userId)) || (null == userId))) {
                newUserId = aesDecrypt(userId.toString(), Const.ALLENCRYPTCODE);
            }
            insertSysOperatelog(newUserId, Const.UPDATA_QUESTION, logger, request);
        } catch (Exception e) {
            logger.info("新增问题信息表失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(transStatus);
            //json格式没有被打乱不需要格式化
            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            logger.info("addQuestionType---jsonObject.toString()---" + jsonReturn + "---");
            return jsonReturn;
        }
        logger.info("新增问题信息表成功");
        map.setCode(Const.SUCCESS_CODE);
        //json格式没有被打乱不需要格式化
        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        logger.info("addConfig---jsonObject.toString()---" + jsonReturn + "---");
        return jsonReturn;
    }


    //删除入门手册信息
    @Override
    public String delBooks(Map<String, Object> reqMap, Logger logger, HttpServletRequest request) {
        ReturnData map = new ReturnData();
        //初始化事务参数配置
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //隔离级别为 PROPAGATION_NESTED--如果当前存在事务，则在嵌套事务内执行。
        //如果当前没有事务，则进行与PROPAGATION_REQUIRED(新创事务)类似的操作。
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
        TransactionStatus transStatus = transactionManager.getTransaction(def);
        ManualPreliminaryRelation manualPreliminaryRelation = new ManualPreliminaryRelation();
        map.setCode(Const.EMPTY_CODE);
        Object preliminaryId = reqMap.get("preliminaryid");
        Object id = reqMap.get("id");
        if (null != id) {
            try {
                logger.info("调用变更数量方法");
                if (!updatePreliminaryByselect(String.valueOf(preliminaryId), transStatus,
                        Const.BOOKS, "-1", logger)) {
                    logger.info("变更手册数量数据失败");
                    //事务回滚
                    logger.info("=====================事务回滚======================");
                    transactionManager.rollback(transStatus);
                    //json格式没有被打乱不需要格式化
                    String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                    logger.info("delBooks---jsonObject.toString()---" + jsonReturn + "---");
                    return jsonReturn;
                }
                manualPreliminaryRelation.setId(DESUtil.aesDecrypt(((Object) reqMap.get("id")).toString(),
                        Const.ALLENCRYPTCODE));
            } catch (Exception e) {
                logger.info("验证id失败");
                //事务回滚
                logger.info("=====================事务回滚======================");
                transactionManager.rollback(transStatus);
                //json格式没有被打乱不需要格式化
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("delBooks---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
        }
        logger.info("删除入门手册信息开始");
        Object updNum = null;
        map.setCode(Const.FAILURE_CODE);
        try {
            updNum = dao.update("ManualPreliminaryRelationMapper.deleteByPrimaryKey", manualPreliminaryRelation);
            if (null == updNum || "0".equals(updNum.toString())) {
                //json格式没有被打乱不需要格式化
                logger.info("删除入门手册信息失败");
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("delBooks---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
            String newUserId = null;
            Object useridObj = reqMap.get("userid");
            if (StringUtil.isEmpty(useridObj)) {
                newUserId = aesDecrypt(useridObj.toString(), Const.ALLENCRYPTCODE);
            } else {
                newUserId = aesDecrypt((String) request.getSession().getAttribute("sessionUserId"), Const.ALLENCRYPTCODE);
            }
            insertSysOperatelog(newUserId, Const.DELETE_BOOKS, logger, request);
        } catch (Exception e) {
            logger.info("删除入门手册信息失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(transStatus);
            //json格式没有被打乱不需要格式化
            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            logger.info("delBooks---jsonObject.toString()---" + jsonReturn + "---");
            return jsonReturn;
        }
        logger.info("删除入门手册信息成功");
        map.setCode(Const.SUCCESS_CODE);
        //json格式没有被打乱不需要格式化
        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        logger.info("delBooks---jsonObject.toString()---" + jsonReturn + "---");
        return jsonReturn;
    }


    //更新入门手册信息
    @Override
    public String updBooks(Map<String, Object> reqMap, Logger logger, HttpServletRequest request) {
        ReturnData map = new ReturnData();
        //初始化事务参数配置
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //隔离级别为 PROPAGATION_NESTED--如果当前存在事务，则在嵌套事务内执行。
        //如果当前没有事务，则进行与PROPAGATION_REQUIRED(新创事务)类似的操作。
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
        TransactionStatus transStatus = transactionManager.getTransaction(def);
        ManualPreliminaryRelation manualPreliminaryRelation = new ManualPreliminaryRelation();
        manualPreliminaryRelation.setIp(getIpAddress(request));
        manualPreliminaryRelation.setUpdateTm(DateUtil.getTime());
        manualPreliminaryRelation.setTmSmp(DateUtil.getTime());
        map.setCode(Const.EMPTY_CODE);
        Object status = reqMap.get("status");
        if (StringUtil.isEmpty(status)) {
            manualPreliminaryRelation.setStatus(status.toString());
        }
        Object manualSize = reqMap.get("file_size");
        if (StringUtil.isEmpty(manualSize)) {
            manualPreliminaryRelation.setManualSize(manualSize.toString());
        }
        Object manualType = reqMap.get("file_type");
        if (StringUtil.isEmpty(manualType)) {
            manualPreliminaryRelation.setManualType(manualType.toString());
        }
        Object preliminaryId = reqMap.get("preliminaryid");
        Object manualPath = reqMap.get("file_path");
        if (StringUtil.isEmpty(manualPath)) {
            manualPreliminaryRelation.setManualPath(manualPath.toString());
        }
        Object remark1 = reqMap.get("file_name");
        if (StringUtil.isEmpty(remark1)) {
            manualPreliminaryRelation.setRemark1(remark1.toString());
        }
        Object id = reqMap.get("id");
        if (null != id) {
            try {
                if (StringUtil.isEmpty(preliminaryId)) {
                    manualPreliminaryRelation.setPreliminaryId(DESUtil.aesDecrypt(preliminaryId.toString(),
                            Const.ALLENCRYPTCODE));
                }
                //前文已经验证过id是否存在，所以这里不需要再次验证
                manualPreliminaryRelation.setId(DESUtil.aesDecrypt(id.toString(),
                        Const.ALLENCRYPTCODE));
            } catch (Exception e) {
                logger.info("验证id失败");
                //事务回滚
                logger.info("=====================事务回滚======================");
                transactionManager.rollback(transStatus);
                //json格式没有被打乱不需要格式化
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("updBooks---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
        }
        logger.info("修改手册信息开始");
        Object updNum = null;
        map.setCode(Const.FAILURE_CODE);
        try {
            updNum = dao.update("ManualPreliminaryRelationMapper.updateByPrimaryKeySelective", manualPreliminaryRelation);
            if (null == updNum || "0".equals(updNum.toString())) {
                //json格式没有被打乱不需要格式化
                logger.info("更新手册信息失败");
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("updBooks---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
            String newUserId = null;
            Object useridObj = reqMap.get("userid");
            if (StringUtil.isEmpty(useridObj)) {
                newUserId = aesDecrypt(useridObj.toString(), Const.ALLENCRYPTCODE);
            } else {
                newUserId = aesDecrypt((String) request.getSession().getAttribute("sessionUserId"), Const.ALLENCRYPTCODE);
            }
            insertSysOperatelog(newUserId, Const.UPDATA_BOOKS, logger, request);
        } catch (Exception e) {
            logger.info("修改手册信息失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(transStatus);
            //json格式没有被打乱不需要格式化
            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            logger.info("updBooks---jsonObject.toString()---" + jsonReturn + "---");
            return jsonReturn;
        }
        logger.info("修改手册信息成功");
        map.setCode(Const.SUCCESS_CODE);
        //json格式没有被打乱不需要格式化
        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        logger.info("updBooks---jsonObject.toString()---" + jsonReturn + "---");
        return jsonReturn;
    }


    //新增入门手册信息
    @Override
    public String addBooks(Map<String, Object> reqMap, Logger logger, HttpServletRequest request) {
        ReturnData map = new ReturnData();
        //初始化事务参数配置
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //隔离级别为 PROPAGATION_NESTED--如果当前存在事务，则在嵌套事务内执行。
        //如果当前没有事务，则进行与PROPAGATION_REQUIRED(新创事务)类似的操作。
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
        TransactionStatus transStatus = transactionManager.getTransaction(def);
        ManualPreliminaryRelation manualPreliminaryRelation = new ManualPreliminaryRelation();
        map.setCode(Const.EMPTY_CODE);
        manualPreliminaryRelation.setCreationDt(DateUtil.getTime());
        manualPreliminaryRelation.setTmSmp(DateUtil.getTime());
        manualPreliminaryRelation.setCreationDt(DateUtil.getDay());
        manualPreliminaryRelation.setCreationTm(DateUtil.getTime());
        //try里面进行赋值
        Object creationPer = reqMap.get("user_id");
        Object preliminaryId = reqMap.get("preliminaryid");
        Object manualSize = reqMap.get("file_size");
        if (StringUtil.isEmpty(manualSize)) {
            manualPreliminaryRelation.setManualSize(manualSize.toString());
        }
        Object ifRelease = reqMap.get("if_release");
        if (StringUtil.isEmpty(ifRelease)) {
            manualPreliminaryRelation.setIfRelease(ifRelease.toString());
        } else {
            manualPreliminaryRelation.setIfRelease(Const.IF_RELEASE_YES);
        }
        Object manualType = reqMap.get("file_type");
        if (StringUtil.isEmpty(manualType)) {
            manualPreliminaryRelation.setManualType(manualType.toString());
        }
        Object manualPath = reqMap.get("file_path");
        if (StringUtil.isEmpty(manualPath)) {
            manualPreliminaryRelation.setManualPath(manualPath.toString());
        }
        Object remark1 = reqMap.get("file_name");
        if (StringUtil.isEmpty(remark1)) {
            manualPreliminaryRelation.setRemark1(remark1.toString());
        }

        Object filePath = reqMap.get("file_path");
        if (StringUtil.isEmpty(filePath)) {
            manualPreliminaryRelation.setManualPath(filePath.toString());
        } else {
            manualPreliminaryRelation.setManualPath(Const.FILE_PATH);
        }
        Object id = reqMap.get("id");
        if (StringUtil.isEmpty(id)) {
            manualPreliminaryRelation.setId(id.toString());
        } else {
            manualPreliminaryRelation.setId(get32UUID());
        }
        Object status = reqMap.get("status");
        if (StringUtil.isEmpty(status)) {
            manualPreliminaryRelation.setStatus(status.toString());
        } else {
            //默认为有效
            manualPreliminaryRelation.setStatus(Const.STATUS_YES);
        }
        Object userId = reqMap.get("userid");
        manualPreliminaryRelation.setIp(getIpAddress(request));
        manualPreliminaryRelation.setUpdateTm(DateUtil.getTime());
        String newUserId = (String) request.getSession().getAttribute("sessionUserId");
        try {
            if (StringUtil.isEmpty(preliminaryId)) {
                manualPreliminaryRelation.setPreliminaryId(DESUtil.aesDecrypt(preliminaryId.toString(),
                        Const.ALLENCRYPTCODE));
            }
            logger.info("调用变更数量方法");
            if (!updatePreliminaryByselect(String.valueOf(preliminaryId), transStatus,
                    Const.BOOKS, "1", logger)) {
                logger.info("变更手册数量数据失败");
                //事务回滚
                logger.info("=====================事务回滚======================");
                transactionManager.rollback(transStatus);
                //json格式没有被打乱不需要格式化
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("addBooks---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }


            if (StringUtil.isEmpty(creationPer)) {
                manualPreliminaryRelation.setCreationPer(DESUtil.aesDecrypt(creationPer.toString(),
                        Const.ALLENCRYPTCODE));
            } else {
                manualPreliminaryRelation.setCreationPer(DESUtil.aesDecrypt(newUserId,
                        Const.ALLENCRYPTCODE));
            }
        } catch (Exception e) {
            logger.info("验证id失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(transStatus);
            //json格式没有被打乱不需要格式化
            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            logger.info("addBooks---jsonObject.toString()---" + jsonReturn + "---");
            return jsonReturn;
        }
        logger.info("新增手册数据开始");
        Object updNum = null;
        map.setCode(Const.FAILURE_CODE);
        try {
            updNum = dao.update("ManualPreliminaryRelationMapper.insert", manualPreliminaryRelation);
            if (null == updNum || "0".equals(updNum.toString())) {
                //json格式没有被打乱不需要格式化
                logger.info("新增问题回复信息表失败");
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("addBooks---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
            if (!((null != userId && Const.SPECIAL_USERID.equals(userId)) || (null == userId))) {
                newUserId = aesDecrypt(userId.toString(), Const.ALLENCRYPTCODE);
            }
            insertSysOperatelog(newUserId, Const.ADD_BOOKS, logger, request);
        } catch (Exception e) {
            logger.info("新增手册数据失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(transStatus);
            //json格式没有被打乱不需要格式化
            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            logger.info("addBooks---jsonObject.toString()---" + jsonReturn + "---");
            return jsonReturn;
        }
        logger.info("新增手册数据成功");
        map.setCode(Const.SUCCESS_CODE);
        //json格式没有被打乱不需要格式化
        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        logger.info("addBooks---jsonObject.toString()---" + jsonReturn + "---");
        return jsonReturn;
    }


    //删除入门视频信息
    @Override
    public String delVideos(Map<String, Object> reqMap, Logger logger, HttpServletRequest request) {
        ReturnData map = new ReturnData();
        //初始化事务参数配置
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //隔离级别为 PROPAGATION_NESTED--如果当前存在事务，则在嵌套事务内执行。
        //如果当前没有事务，则进行与PROPAGATION_REQUIRED(新创事务)类似的操作。
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
        TransactionStatus transStatus = transactionManager.getTransaction(def);
        VideoPreliminaryRelation videoPreliminaryRelation = new VideoPreliminaryRelation();
        map.setCode(Const.EMPTY_CODE);
        Object preliminaryId = reqMap.get("preliminaryid");
        Object id = reqMap.get("id");
        if (null != id) {
            try {

                logger.info("调用变更数量方法");
                if (!updatePreliminaryByselect(String.valueOf(preliminaryId), transStatus,
                        Const.VIDEOS, "-1", logger)) {
                    logger.info("变更视频数量数据失败");
                    //事务回滚
                    logger.info("=====================事务回滚======================");
                    transactionManager.rollback(transStatus);
                    //json格式没有被打乱不需要格式化
                    String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                    logger.info("delVideos---jsonObject.toString()---" + jsonReturn + "---");
                    return jsonReturn;
                }
                videoPreliminaryRelation.setId(DESUtil.aesDecrypt(((Object) reqMap.get("id")).toString(),
                        Const.ALLENCRYPTCODE));
            } catch (Exception e) {
                logger.info("验证id失败");
                //事务回滚
                logger.info("=====================事务回滚======================");
                transactionManager.rollback(transStatus);
                //json格式没有被打乱不需要格式化
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("delVideos---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
        }
        logger.info("删除入门视频数据开始");
        Object updNum = null;
        map.setCode(Const.FAILURE_CODE);
        try {
            updNum = dao.update("VideoPreliminaryRelationMapper.deleteByPrimaryKey", videoPreliminaryRelation);
            if (null == updNum || "0".equals(updNum.toString())) {
                //json格式没有被打乱不需要格式化
                logger.info("删除入门视频失败");
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("delVideos---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
            String newUserId = null;
            Object useridObj = reqMap.get("userid");
            if (StringUtil.isEmpty(useridObj)) {
                newUserId = aesDecrypt(useridObj.toString(), Const.ALLENCRYPTCODE);
            } else {
                newUserId = aesDecrypt((String) request.getSession().getAttribute("sessionUserId"), Const.ALLENCRYPTCODE);
            }
            insertSysOperatelog(newUserId, Const.DELETE_VIDEOS, logger, request);
        } catch (Exception e) {
            logger.info("删除入门视频失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(transStatus);
            //json格式没有被打乱不需要格式化
            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            logger.info("delVideos---jsonObject.toString()---" + jsonReturn + "---");
            return jsonReturn;
        }
        logger.info("删除入门视频成功");
        map.setCode(Const.SUCCESS_CODE);
        //json格式没有被打乱不需要格式化
        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        logger.info("delVideos---jsonObject.toString()---" + jsonReturn + "---");
        return jsonReturn;
    }


    //更新入门视频信息
    @Override
    public String updVideos(Map<String, Object> reqMap, Logger logger, HttpServletRequest request) {
        ReturnData map = new ReturnData();
        //初始化事务参数配置
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //隔离级别为 PROPAGATION_NESTED--如果当前存在事务，则在嵌套事务内执行。
        //如果当前没有事务，则进行与PROPAGATION_REQUIRED(新创事务)类似的操作。
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
        TransactionStatus transStatus = transactionManager.getTransaction(def);
        VideoPreliminaryRelation videoPreliminaryRelation = new VideoPreliminaryRelation();
        videoPreliminaryRelation.setIp(getIpAddress(request));
        videoPreliminaryRelation.setUpdateTm(DateUtil.getTime());
        videoPreliminaryRelation.setTmSmp(DateUtil.getTime());
        map.setCode(Const.EMPTY_CODE);
        Object status = reqMap.get("status");
        if (StringUtil.isEmpty(status)) {
            videoPreliminaryRelation.setStatus(status.toString());
        }
        Object videoSize = reqMap.get("file_size");
        if (StringUtil.isEmpty(videoSize)) {
            videoPreliminaryRelation.setVideoSize(videoSize.toString());
        }
        Object videoType = reqMap.get("file_type");
        if (StringUtil.isEmpty(videoType)) {
            videoPreliminaryRelation.setVideoType(videoType.toString());
        }
        Object preliminaryId = reqMap.get("preliminaryid");

        Object videoPath = reqMap.get("file_path");
        if (StringUtil.isEmpty(videoPath)) {
            videoPreliminaryRelation.setVideoPath(videoPath.toString());
        }
        Object remark1 = reqMap.get("file_name");
        if (StringUtil.isEmpty(remark1)) {
            videoPreliminaryRelation.setRemark1(remark1.toString());
        }
        Object id = reqMap.get("id");
        Object userId = reqMap.get("userid");
        if (null != id) {
            try {
                //前文已经验证过id是否存在，所以这里不需要再次验证
                videoPreliminaryRelation.setId(DESUtil.aesDecrypt(id.toString(),
                        Const.ALLENCRYPTCODE));

            } catch (Exception e) {
                logger.info("验证id失败");
                //事务回滚
                logger.info("=====================事务回滚======================");
                transactionManager.rollback(transStatus);
                //json格式没有被打乱不需要格式化
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("updVideos---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
        }
        logger.info("修改入门视频信息开始");
        Object updNum = null;
        map.setCode(Const.FAILURE_CODE);
        try {
            if (StringUtil.isEmpty(preliminaryId)) {
                videoPreliminaryRelation.setPreliminaryId(DESUtil.aesDecrypt(preliminaryId.toString(),
                        Const.ALLENCRYPTCODE));
            }
            updNum = dao.update("VideoPreliminaryRelationMapper.updateByPrimaryKeySelective", videoPreliminaryRelation);
            if (null == updNum || "0".equals(updNum.toString())) {
                //json格式没有被打乱不需要格式化
                logger.info("更新入门视频信息失败");
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("updVideos---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
            String newUserId = null;
            Object useridObj = reqMap.get("userid");
            if (StringUtil.isEmpty(useridObj)) {
                newUserId = aesDecrypt(useridObj.toString(), Const.ALLENCRYPTCODE);
            } else {
                newUserId = aesDecrypt((String) request.getSession().getAttribute("sessionUserId"), Const.ALLENCRYPTCODE);
            }
            insertSysOperatelog(newUserId, Const.UPDATA_VIDEOS, logger, request);
        } catch (Exception e) {
            logger.info("修改入门视频信息失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(transStatus);
            //json格式没有被打乱不需要格式化
            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            logger.info("updVideos---jsonObject.toString()---" + jsonReturn + "---");
            return jsonReturn;
        }
        logger.info("修改入门视频信息成功");
        map.setCode(Const.SUCCESS_CODE);
        //json格式没有被打乱不需要格式化
        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        logger.info("updVideos---jsonObject.toString()---" + jsonReturn + "---");
        return jsonReturn;
    }


    //新增入门视频信息
    @Override
    public String addVideos(Map<String, Object> reqMap, Logger logger, HttpServletRequest request) {
        ReturnData map = new ReturnData();
        //初始化事务参数配置
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //隔离级别为 PROPAGATION_NESTED--如果当前存在事务，则在嵌套事务内执行。
        //如果当前没有事务，则进行与PROPAGATION_REQUIRED(新创事务)类似的操作。
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
        TransactionStatus transStatus = transactionManager.getTransaction(def);
        VideoPreliminaryRelation videoPreliminaryRelation = new VideoPreliminaryRelation();
        map.setCode(Const.EMPTY_CODE);
        videoPreliminaryRelation.setCreationDt(DateUtil.getTime());
        videoPreliminaryRelation.setTmSmp(DateUtil.getTime());
        videoPreliminaryRelation.setCreationDt(DateUtil.getDay());
        videoPreliminaryRelation.setCreationTm(DateUtil.getTime());
        //try里面进行赋值
        Object creationPer = reqMap.get("user_id");
        Object preliminaryId = reqMap.get("preliminaryid");

        Object videoSize = reqMap.get("file_size");
        if (StringUtil.isEmpty(videoSize)) {
            videoPreliminaryRelation.setVideoSize(videoSize.toString());
        }
        Object ifRelease = reqMap.get("if_release");
        if (StringUtil.isEmpty(ifRelease)) {
            videoPreliminaryRelation.setIfRelease(ifRelease.toString());
        } else {
            videoPreliminaryRelation.setIfRelease(Const.IF_RELEASE_YES);
        }
        Object videoType = reqMap.get("file_type");
        if (StringUtil.isEmpty(videoType)) {
            videoPreliminaryRelation.setVideoType(videoType.toString());
        }

        Object remark1 = reqMap.get("file_name");
        if (StringUtil.isEmpty(remark1)) {
            videoPreliminaryRelation.setRemark1(remark1.toString());
        }

        Object filePath = reqMap.get("file_path");
        if (StringUtil.isEmpty(filePath)) {
            videoPreliminaryRelation.setVideoPath(filePath.toString());
        } else {
            videoPreliminaryRelation.setVideoPath(Const.FILE_PATH);
        }
        Object id = reqMap.get("id");
        if (StringUtil.isEmpty(id)) {
            videoPreliminaryRelation.setId(id.toString());
        } else {
            logger.info("验证id失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(transStatus);
            //json格式没有被打乱不需要格式化
            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            logger.info("addVideos---jsonObject.toString()---" + jsonReturn + "---");
            return jsonReturn;
        }

        Object status = reqMap.get("status");
        if (StringUtil.isEmpty(status)) {
            videoPreliminaryRelation.setStatus(status.toString());
        } else {
            //默认为有效
            videoPreliminaryRelation.setStatus(Const.STATUS_YES);
        }
        Object userId = reqMap.get("userid");
        videoPreliminaryRelation.setIp(getIpAddress(request));
        videoPreliminaryRelation.setUpdateTm(DateUtil.getTime());
        String newUserId = (String) request.getSession().getAttribute("sessionUserId");
        try {
            if (StringUtil.isEmpty(preliminaryId)) {
                videoPreliminaryRelation.setPreliminaryId(DESUtil.aesDecrypt(preliminaryId.toString(),
                        Const.ALLENCRYPTCODE));
            }
            logger.info("调用变更数量方法");
            if (!updatePreliminaryByselect(String.valueOf(preliminaryId), transStatus,
                    Const.VIDEOS, "1", logger)) {
                logger.info("变更视频数量数据失败");
                //事务回滚
                logger.info("=====================事务回滚======================");
                transactionManager.rollback(transStatus);
                //json格式没有被打乱不需要格式化
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("addVideos---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
            if (StringUtil.isEmpty(creationPer)) {
                videoPreliminaryRelation.setCreationPer(DESUtil.aesDecrypt(creationPer.toString(),
                        Const.ALLENCRYPTCODE));
            } else {
                videoPreliminaryRelation.setCreationPer(DESUtil.aesDecrypt(newUserId,
                        Const.ALLENCRYPTCODE));
            }
        } catch (Exception e) {
            logger.info("验证id失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(transStatus);
            //json格式没有被打乱不需要格式化
            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            logger.info("addVideos---jsonObject.toString()---" + jsonReturn + "---");
            return jsonReturn;
        }
        logger.info("新增入门视频开始");
        Object updNum = null;
        map.setCode(Const.FAILURE_CODE);
        try {
            updNum = dao.update("VideoPreliminaryRelationMapper.insert", videoPreliminaryRelation);
            if (null == updNum || "0".equals(updNum.toString())) {
                //json格式没有被打乱不需要格式化
                logger.info("新增入门视频失败");
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("addVideos---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
            if (!((null != userId && Const.SPECIAL_USERID.equals(userId)) || (null == userId))) {
                newUserId = aesDecrypt(userId.toString(), Const.ALLENCRYPTCODE);
            }
            insertSysOperatelog(newUserId, Const.ADD_VIDEOS, logger, request);
        } catch (Exception e) {
            logger.info("新增入门视频失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(transStatus);
            //json格式没有被打乱不需要格式化
            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            logger.info("addVideos---jsonObject.toString()---" + jsonReturn + "---");
            return jsonReturn;
        }
        logger.info("新增入门视频成功");
        map.setCode(Const.SUCCESS_CODE);
        //json格式没有被打乱不需要格式化
        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        logger.info("addVideos---jsonObject.toString()---" + jsonReturn + "---");
        return jsonReturn;
    }


    //删除问题回复信息
    @Override
    public String delQuestionReply(Map<String, Object> reqMap, Logger logger, HttpServletRequest request) {
        ReturnData map = new ReturnData();
        //初始化事务参数配置
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //隔离级别为 PROPAGATION_NESTED--如果当前存在事务，则在嵌套事务内执行。
        //如果当前没有事务，则进行与PROPAGATION_REQUIRED(新创事务)类似的操作。
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
        TransactionStatus transStatus = transactionManager.getTransaction(def);
        HelpQuestionReplyInformation helpQuestionReplyInformation = new HelpQuestionReplyInformation();
        map.setCode(Const.EMPTY_CODE);
        Object id = reqMap.get("id");
        if (null != id) {
            try {
                helpQuestionReplyInformation.setId(DESUtil.aesDecrypt(((Object) reqMap.get("id")).toString(),
                        Const.ALLENCRYPTCODE));
            } catch (Exception e) {
                logger.info("验证id失败");
                //事务回滚
                logger.info("=====================事务回滚======================");
                transactionManager.rollback(transStatus);
                //json格式没有被打乱不需要格式化
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("updQuestionType---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
        }
        logger.info("删除问题信息表相应数据开始");
        Object updNum = null;
        map.setCode(Const.FAILURE_CODE);
        try {
            updNum = dao.update("HelpQuestionReplyInformationMapper.deleteByPrimaryKey", helpQuestionReplyInformation);
            if (null == updNum || "0".equals(updNum.toString())) {
                //json格式没有被打乱不需要格式化
                logger.info("删除问题回复信息表失败");
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("updQuestionType---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
            String newUserId = null;
            Object useridObj = reqMap.get("userid");
            if (StringUtil.isEmpty(useridObj)) {
                newUserId = aesDecrypt(useridObj.toString(), Const.ALLENCRYPTCODE);
            } else {
                newUserId = aesDecrypt((String) request.getSession().getAttribute("sessionUserId"), Const.ALLENCRYPTCODE);
            }
            insertSysOperatelog(newUserId, Const.DELETE_QUESTION_REPLY, logger, request);
        } catch (Exception e) {
            logger.info("删除问题回复信息表失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(transStatus);
            //json格式没有被打乱不需要格式化
            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            logger.info("updQuestionType---jsonObject.toString()---" + jsonReturn + "---");
            return jsonReturn;
        }
        logger.info("删除问题回复信息表成功");
        map.setCode(Const.SUCCESS_CODE);
        //json格式没有被打乱不需要格式化
        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        logger.info("updConfig---jsonObject.toString()---" + jsonReturn + "---");
        return jsonReturn;
    }


    //更新问题回复信息
    @Override
    public String updQuestionReply(Map<String, Object> reqMap, Logger logger, HttpServletRequest request) {
        ReturnData map = new ReturnData();
        //初始化事务参数配置
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //隔离级别为 PROPAGATION_NESTED--如果当前存在事务，则在嵌套事务内执行。
        //如果当前没有事务，则进行与PROPAGATION_REQUIRED(新创事务)类似的操作。
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
        TransactionStatus transStatus = transactionManager.getTransaction(def);
        HelpQuestionReplyInformation helpQuestionReplyInformation = new HelpQuestionReplyInformation();
        helpQuestionReplyInformation.setIp(getIpAddress(request));
        helpQuestionReplyInformation.setUpdateTm(DateUtil.getTime());
        helpQuestionReplyInformation.setTmSmp(DateUtil.getTime());
        map.setCode(Const.EMPTY_CODE);
        Object status = reqMap.get("status");
        if (StringUtil.isEmpty(status)) {
            helpQuestionReplyInformation.setStatus(status.toString());
        }
        Object replyContent = reqMap.get("reply_content");
        if (StringUtil.isEmpty(replyContent)) {
            helpQuestionReplyInformation.setReplyContent(replyContent.toString());
        }
        Object remark1 = reqMap.get("remark1");
        if (StringUtil.isEmpty(remark1)) {
            helpQuestionReplyInformation.setRemark1(remark1.toString());
        }
        Object id = reqMap.get("id");
        Object userId = reqMap.get("userid");
        if (null != id) {
            try {
                //前文已经验证过id是否存在，所以这里不需要再次验证
                helpQuestionReplyInformation.setId(DESUtil.aesDecrypt(id.toString(),
                        Const.ALLENCRYPTCODE));
                if (StringUtil.isEmpty(userId)) {
                    helpQuestionReplyInformation.setUserId(DESUtil.aesDecrypt(userId.toString(),
                            Const.ALLENCRYPTCODE));
                }
            } catch (Exception e) {
                logger.info("验证id失败");
                //事务回滚
                logger.info("=====================事务回滚======================");
                transactionManager.rollback(transStatus);
                //json格式没有被打乱不需要格式化
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("updQuestionType---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
        }
        logger.info("修改问题信息表相应数据开始");
        Object updNum = null;
        map.setCode(Const.FAILURE_CODE);
        try {
            updNum = dao.update("HelpQuestionReplyInformationMapper.updateByPrimaryKeySelective", helpQuestionReplyInformation);
            if (null == updNum || "0".equals(updNum.toString())) {
                //json格式没有被打乱不需要格式化
                logger.info("更新问题回复信息表失败");
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("updQuestionReplyType---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
            String newUserId = null;
            Object useridObj = reqMap.get("userid");
            if (StringUtil.isEmpty(useridObj)) {
                newUserId = aesDecrypt(useridObj.toString(), Const.ALLENCRYPTCODE);
            } else {
                newUserId = aesDecrypt((String) request.getSession().getAttribute("sessionUserId"), Const.ALLENCRYPTCODE);
            }
            insertSysOperatelog(newUserId, Const.UPDATA_QUESTION_REPLY, logger, request);
        } catch (Exception e) {
            logger.info("修改问题回复信息表失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(transStatus);
            //json格式没有被打乱不需要格式化
            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            logger.info("updQuestionReplyType---jsonObject.toString()---" + jsonReturn + "---");
            return jsonReturn;
        }
        logger.info("修改问题回复信息表成功");
        map.setCode(Const.SUCCESS_CODE);
        //json格式没有被打乱不需要格式化
        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        logger.info("updConfig---jsonObject.toString()---" + jsonReturn + "---");
        return jsonReturn;
    }

    //新增问题回复信息
    @Override
    public String addQuestionReply(Map<String, Object> reqMap, Logger logger, HttpServletRequest request) {
        ReturnData map = new ReturnData();
        //初始化事务参数配置
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //隔离级别为 PROPAGATION_NESTED--如果当前存在事务，则在嵌套事务内执行。
        //如果当前没有事务，则进行与PROPAGATION_REQUIRED(新创事务)类似的操作。
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
        TransactionStatus transStatus = transactionManager.getTransaction(def);
        HelpQuestionReplyInformation helpQuestionReplyInformation = new HelpQuestionReplyInformation();
        map.setCode(Const.EMPTY_CODE);
        helpQuestionReplyInformation.setCreationDt(DateUtil.getTime());
        helpQuestionReplyInformation.setTmSmp(DateUtil.getTime());
        helpQuestionReplyInformation.setCreationDt(DateUtil.getDay());
        helpQuestionReplyInformation.setCreationTm(DateUtil.getTime());
        //try里面进行赋值
        Object questionId = reqMap.get("questionid");
        Object creationPer = reqMap.get("userid");
        Object replyContent = reqMap.get("reply_content");
        if (StringUtil.isEmpty(replyContent)) {
            helpQuestionReplyInformation.setReplyContent(replyContent.toString());
        }
        helpQuestionReplyInformation.setId(get32UUID());
        Object status = reqMap.get("status");
        if (StringUtil.isEmpty(status)) {
            helpQuestionReplyInformation.setStatus(status.toString());
        } else {
            //默认为有效
            helpQuestionReplyInformation.setStatus(Const.STATUS_YES);
        }
        Object remark1 = reqMap.get("remark1");
        if (StringUtil.isEmpty(remark1)) {
            helpQuestionReplyInformation.setRemark1(remark1.toString());
        }
        Object userId = reqMap.get("userid");
        helpQuestionReplyInformation.setIp(getIpAddress(request));
        helpQuestionReplyInformation.setUpdateTm(DateUtil.getTime());
        String newUserId = (String) request.getSession().getAttribute("sessionUserId");
        try {
            if (StringUtil.isEmpty(questionId)) {
                helpQuestionReplyInformation.setQuestionId(DESUtil.aesDecrypt(questionId.toString(),
                        Const.ALLENCRYPTCODE));
            }
            if (StringUtil.isEmpty(creationPer)) {
                helpQuestionReplyInformation.setCreationPer(DESUtil.aesDecrypt(creationPer.toString(),
                        Const.ALLENCRYPTCODE));
            } else {
                helpQuestionReplyInformation.setCreationPer(DESUtil.aesDecrypt(newUserId,
                        Const.ALLENCRYPTCODE));
            }
            if (StringUtil.isEmpty(userId)) {
                helpQuestionReplyInformation.setUserId(DESUtil.aesDecrypt(userId.toString(),
                        Const.ALLENCRYPTCODE));
            } else {
                //系统管理员录入相关信息使用session 的userid
                helpQuestionReplyInformation.setUserId(DESUtil.aesDecrypt(newUserId.toString(),
                        Const.ALLENCRYPTCODE));
            }
        } catch (Exception e) {
            logger.info("验证id失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(transStatus);
            //json格式没有被打乱不需要格式化
            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            logger.info("addQuestionType---jsonObject.toString()---" + jsonReturn + "---");
            return jsonReturn;
        }
        logger.info("新增问题信息表相应数据开始");
        Object updNum = null;
        map.setCode(Const.FAILURE_CODE);
        try {
            updNum = dao.update("HelpQuestionReplyInformationMapper.insert", helpQuestionReplyInformation);
            if (null == updNum || "0".equals(updNum.toString())) {
                //json格式没有被打乱不需要格式化
                logger.info("新增问题回复信息表失败");
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("addQuestionReplyType---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
            if (!((null != userId && Const.SPECIAL_USERID.equals(userId)) || (null == userId))) {
                newUserId = aesDecrypt(userId.toString(), Const.ALLENCRYPTCODE);
            }
            insertSysOperatelog(newUserId, Const.ADD_QUESTION_REPLY, logger, request);
        } catch (Exception e) {
            logger.info("新增问题回复信息表失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(transStatus);
            //json格式没有被打乱不需要格式化
            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            logger.info("addQuestionRplyType---jsonObject.toString()---" + jsonReturn + "---");
            return jsonReturn;
        }
        logger.info("新增问题回复信息表成功");
        map.setCode(Const.SUCCESS_CODE);
        //json格式没有被打乱不需要格式化
        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        logger.info("addConfig---jsonObject.toString()---" + jsonReturn + "---");
        return jsonReturn;
    }


    //删除入门信息
    @Override
    public String delPreliminary(Map<String, Object> reqMap, Logger logger, HttpServletRequest request) {
        ReturnData map = new ReturnData();
        //初始化事务参数配置
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //隔离级别为 PROPAGATION_NESTED--如果当前存在事务，则在嵌套事务内执行。
        //如果当前没有事务，则进行与PROPAGATION_REQUIRED(新创事务)类似的操作。
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
        TransactionStatus transStatus = transactionManager.getTransaction(def);
        PreliminaryInformation preliminaryInformation = new PreliminaryInformation();
        map.setCode(Const.EMPTY_CODE);
        Object id = reqMap.get("id");
        if (null != id) {
            try {
                preliminaryInformation.setId(DESUtil.aesDecrypt(((Object) reqMap.get("id")).toString(),
                        Const.ALLENCRYPTCODE));
            } catch (Exception e) {
                logger.info("验证id失败");
                //事务回滚
                logger.info("=====================事务回滚======================");
                transactionManager.rollback(transStatus);
                //json格式没有被打乱不需要格式化
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("delPreliminary---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
        }
        logger.info("删除入门数据开始");
        Object updNum = null;
        map.setCode(Const.FAILURE_CODE);
        try {
            updNum = dao.update("PreliminaryInformationMapper.deleteByPrimaryKey", preliminaryInformation);
            if (null == updNum || "0".equals(updNum.toString())) {
                //json格式没有被打乱不需要格式化
                logger.info("删除入门数据失败");
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("delPreliminary---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
            String newUserId = null;
            Object useridObj = reqMap.get("userid");
            if (StringUtil.isEmpty(useridObj)) {
                newUserId = aesDecrypt(useridObj.toString(), Const.ALLENCRYPTCODE);
            } else {
                newUserId = aesDecrypt((String) request.getSession().getAttribute("sessionUserId"), Const.ALLENCRYPTCODE);
            }
            insertSysOperatelog(newUserId, Const.DELETE_QUESTION_REPLY, logger, request);
        } catch (Exception e) {
            logger.info("删除入门数据失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(transStatus);
            //json格式没有被打乱不需要格式化
            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            logger.info("delPreliminary---jsonObject.toString()---" + jsonReturn + "---");
            return jsonReturn;
        }
        logger.info("删除入门数据成功");
        map.setCode(Const.SUCCESS_CODE);
        //json格式没有被打乱不需要格式化
        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        logger.info("delPreliminary---jsonObject.toString()---" + jsonReturn + "---");
        return jsonReturn;
    }


    //更新入门信息
    @Override
    public String updPreliminary(Map<String, Object> reqMap, Logger logger, HttpServletRequest request) {
        ReturnData map = new ReturnData();
        //初始化事务参数配置
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //隔离级别为 PROPAGATION_NESTED--如果当前存在事务，则在嵌套事务内执行。
        //如果当前没有事务，则进行与PROPAGATION_REQUIRED(新创事务)类似的操作。
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
        TransactionStatus transStatus = transactionManager.getTransaction(def);
        PreliminaryInformation preliminaryInformation = new PreliminaryInformation();
        preliminaryInformation.setIp(getIpAddress(request));
        preliminaryInformation.setUpdateTm(DateUtil.getTime());
        preliminaryInformation.setTmSmp(DateUtil.getTime());
        Object preliminaryName = reqMap.get("preliminary_name");
        if (StringUtil.isEmpty(preliminaryName)) {
            preliminaryInformation.setPreliminaryName(preliminaryName.toString());
        }
        Object videoNum = reqMap.get("video_num");
        if (StringUtil.isEmpty(videoNum)) {
            preliminaryInformation.setVideoNum(videoNum.toString());
        }

        Object manualNum = reqMap.get("manual_num");
        if (StringUtil.isEmpty(manualNum)) {
            preliminaryInformation.setManualNum(manualNum.toString());
        }

        map.setCode(Const.EMPTY_CODE);
        Object status = reqMap.get("status");
        if (StringUtil.isEmpty(status)) {
            preliminaryInformation.setStatus(status.toString());
        }
        Object remark1 = reqMap.get("remark1");
        if (StringUtil.isEmpty(remark1)) {
            preliminaryInformation.setRemark1(remark1.toString());
        }
        Object id = reqMap.get("id");
        Object userId = reqMap.get("userid");
        if (null != id) {
            try {
                //前文已经验证过id是否存在，所以这里不需要再次验证
                preliminaryInformation.setId(DESUtil.aesDecrypt(id.toString(),
                        Const.ALLENCRYPTCODE));
            } catch (Exception e) {
                logger.info("验证id失败");
                //事务回滚
                logger.info("=====================事务回滚======================");
                transactionManager.rollback(transStatus);
                //json格式没有被打乱不需要格式化
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("updPreliminary---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
        }
        logger.info("修改入门信息开始");
        Object updNum = null;
        map.setCode(Const.FAILURE_CODE);
        try {
            updNum = dao.update("PreliminaryInformationMapper.updateByPrimaryKeySelective", preliminaryInformation);
            if (null == updNum || "0".equals(updNum.toString())) {
                //json格式没有被打乱不需要格式化
                logger.info("更新入门信息失败");
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("updPreliminary---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
            String newUserId = null;
            Object useridObj = reqMap.get("userid");
            if (StringUtil.isEmpty(useridObj)) {
                newUserId = aesDecrypt(useridObj.toString(), Const.ALLENCRYPTCODE);
            } else {
                newUserId = aesDecrypt((String) request.getSession().getAttribute("sessionUserId"), Const.ALLENCRYPTCODE);
            }
            insertSysOperatelog(newUserId, Const.UPDATA_PRELIMINARY, logger, request);
        } catch (Exception e) {
            logger.info("修改入门信息失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(transStatus);
            //json格式没有被打乱不需要格式化
            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            logger.info("updPreliminary---jsonObject.toString()---" + jsonReturn + "---");
            return jsonReturn;
        }
        logger.info("修改入门信息成功");
        map.setCode(Const.SUCCESS_CODE);
        //json格式没有被打乱不需要格式化
        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        logger.info("updPreliminary---jsonObject.toString()---" + jsonReturn + "---");
        return jsonReturn;
    }


    //新增入门信息
    @Override
    public String addPreliminary(Map<String, Object> reqMap, Logger logger, HttpServletRequest request) {
        ReturnData map = new ReturnData();
        //初始化事务参数配置
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //隔离级别为 PROPAGATION_NESTED--如果当前存在事务，则在嵌套事务内执行。
        //如果当前没有事务，则进行与PROPAGATION_REQUIRED(新创事务)类似的操作。
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
        TransactionStatus transStatus = transactionManager.getTransaction(def);
        PreliminaryInformation preliminaryInformation = new PreliminaryInformation();
        map.setCode(Const.EMPTY_CODE);
        preliminaryInformation.setCreationDt(DateUtil.getTime());
        preliminaryInformation.setTmSmp(DateUtil.getTime());
        preliminaryInformation.setCreationDt(DateUtil.getDay());
        preliminaryInformation.setCreationTm(DateUtil.getTime());
        //try里面进行赋值
        Object creationPer = reqMap.get("user_id");
        Object preliminaryName = reqMap.get("preliminary_name");
        if (StringUtil.isEmpty(preliminaryName)) {
            preliminaryInformation.setPreliminaryName(preliminaryName.toString());
        }
        Object videoNum = reqMap.get("video_num");
        if (StringUtil.isEmpty(videoNum)) {
            preliminaryInformation.setVideoNum(videoNum.toString());
        }
        Object manualNum = reqMap.get("manual_num");
        if (StringUtil.isEmpty(manualNum)) {
            preliminaryInformation.setManualNum(manualNum.toString());
        }
        preliminaryInformation.setId(get32UUID());
        Object status = reqMap.get("status");
        if (StringUtil.isEmpty(status)) {
            preliminaryInformation.setStatus(status.toString());
        } else {
            //默认为有效
            preliminaryInformation.setStatus(Const.STATUS_YES);
        }
        Object remark1 = reqMap.get("remark1");
        if (StringUtil.isEmpty(remark1)) {
            preliminaryInformation.setRemark1(remark1.toString());
        }
        Object userId = reqMap.get("userid");
        preliminaryInformation.setIp(getIpAddress(request));
        preliminaryInformation.setUpdateTm(DateUtil.getTime());
        String newUserId = (String) request.getSession().getAttribute("sessionUserId");
        try {

            if (StringUtil.isEmpty(creationPer)) {
                preliminaryInformation.setCreationPer(DESUtil.aesDecrypt(creationPer.toString(),
                        Const.ALLENCRYPTCODE));
            } else {
                preliminaryInformation.setCreationPer(DESUtil.aesDecrypt(newUserId,
                        Const.ALLENCRYPTCODE));
            }
        } catch (Exception e) {
            logger.info("验证id失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(transStatus);
            //json格式没有被打乱不需要格式化
            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            logger.info("addPreliminary---jsonObject.toString()---" + jsonReturn + "---");
            return jsonReturn;
        }
        logger.info("新增入门信息开始");
        Object updNum = null;
        map.setCode(Const.FAILURE_CODE);
        try {
            updNum = dao.update("PreliminaryInformationMapper.insert", preliminaryInformation);
            if (null == updNum || "0".equals(updNum.toString())) {
                //json格式没有被打乱不需要格式化
                logger.info("新增入门信息失败");
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("addPreliminary---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
            if (!((null != userId && Const.SPECIAL_USERID.equals(userId)) || (null == userId))) {
                newUserId = aesDecrypt(userId.toString(), Const.ALLENCRYPTCODE);
            }
            insertSysOperatelog(newUserId, Const.UPDATA_QUESTION, logger, request);
        } catch (Exception e) {
            logger.info("新增入门信息失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(transStatus);
            //json格式没有被打乱不需要格式化
            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            logger.info("addPreliminary---jsonObject.toString()---" + jsonReturn + "---");
            return jsonReturn;
        }
        logger.info("新增入门信息成功");
        map.setCode(Const.SUCCESS_CODE);
        //json格式没有被打乱不需要格式化
        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        logger.info("addPreliminary---jsonObject.toString()---" + jsonReturn + "---");
        return jsonReturn;
    }


    //获取ip地址
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
     * Title: 用户操作日志新增:
     *
     * @author wangyanlai
     * @version 2019年1月3日 下午4:29:44
     * wangyanlai@cei.gov.cn
     */
    //插入用户操作日志
    public void insertSysOperatelog(String userId, String type, Logger logger, HttpServletRequest request) throws Exception {
        SysOperatelog sysOperatelog = new SysOperatelog();
        sysOperatelog.setCreatetime(DateUtil.getTime());
        sysOperatelog.setIp((getIpAddress(request)));
        sysOperatelog.setLogid(get32UUID());
        if (Const.QUERY_QUESTION_LIST.equals(type)) {
            sysOperatelog.setLogname("学员[" + userId + "]表示查询问题汇总列表");
        } else if (Const.QUERY_QUESTION_TYPE_LIST.equals(type)) {
            sysOperatelog.setLogname("学员[" + userId + "]表示查询问题类别列表");
        } else if (Const.QUERY_VIDEOS_LIST.equals(type)) {
            sysOperatelog.setLogname("学员[" + userId + "]表示查询视频列表");
        } else if (Const.QUERY_BOOKS_LIST.equals(type)) {
            sysOperatelog.setLogname("学员[" + userId + "]表示查询手册列表");
        } else if (Const.UPDATA_QUESTION.equals(type)) {
            sysOperatelog.setLogname("学员[" + userId + "]变更问题信息");
        } else if (Const.UPDATA_QUESTION_TYPE.equals(type)) {
            sysOperatelog.setLogname("学员[" + userId + "]变更问题类别信息");
        } else if (Const.UPDATA_VIDEOS.equals(type)) {
            sysOperatelog.setLogname("学员[" + userId + "]变更视频信息");
        } else if (Const.UPDATA_BOOKS.equals(type)) {
            sysOperatelog.setLogname("学员[" + userId + "]变更手册信息");
        } else if (Const.DELETE_QUESTION.equals(type)) {
            sysOperatelog.setLogname("学员[" + userId + "]删除问题信息");
        } else if (Const.DELETE_QUESTION_TYPE.equals(type)) {
            sysOperatelog.setLogname("学员[" + userId + "]删除问题类别信息");
        } else if (Const.DELETE_VIDEOS.equals(type)) {
            sysOperatelog.setLogname("学员[" + userId + "]删除视频信息");
        } else if (Const.DELETE_BOOKS.equals(type)) {
            sysOperatelog.setLogname("学员[" + userId + "]删除手册信息");
        } else if (Const.ADD_QUESTION_TYPE.equals(type)) {
            sysOperatelog.setLogname("学员[" + userId + "]增加问题类别信息");
        } else if (Const.ADD_QUESTION.equals(type)) {
            sysOperatelog.setLogname("学员[" + userId + "]增加问题信息");
        } else if (Const.ADD_VIDEOS.equals(type)) {
            sysOperatelog.setLogname("学员[" + userId + "]增加视频信息");
        } else if (Const.ADD_BOOKS.equals(type)) {
            sysOperatelog.setLogname("学员[" + userId + "]增加手册信息");
        } else if (Const.QUERY_PRELIMINARY.equals(type)) {
            sysOperatelog.setLogname("学员[" + userId + "]查询入门信息");
        } else if (Const.UPDATA_PRELIMINARY.equals(type)) {
            sysOperatelog.setLogname("学员[" + userId + "]更改入门信息");
        } else if (Const.DELETE_PRELIMINARY.equals(type)) {
            sysOperatelog.setLogname("学员[" + userId + "]删除入门信息");
        } else if (Const.ADD_PRELIMINARY.equals(type)) {
            sysOperatelog.setLogname("学员[" + userId + "]增加入门信息");
        } else if (Const.ADD_QUESTION_REPLY.equals(type)) {
            sysOperatelog.setLogname("学员[" + userId + "]增加回复信息");
        } else if (Const.DELETE_QUESTION_REPLY.equals(type)) {
            sysOperatelog.setLogname("学员[" + userId + "]删除回复信息");
        } else if (Const.UPDATA_QUESTION_REPLY.equals(type)) {
            sysOperatelog.setLogname("学员[" + userId + "]更改回复信息");
        } else if (Const.QUERY_QUESTION_REPLY_LIST.equals(type)) {
            sysOperatelog.setLogname("学员[" + userId + "]查询回复汇总列表");
        }

        sysOperatelog.setLogtype("0");
        sysOperatelog.setOperatetype("QUERY");
        sysOperatelog.setModuletype("INTEGRATION");
        sysOperatelog.setOperater(userId);
        sysOperatelog.setLoggertype("OPERATE");
        Object updOperaLogNum = null;
        try {
            updOperaLogNum = dao.update("SysOperatelogMapper.insert", sysOperatelog);
        } catch (Exception e) {
            logger.info("插入操作日志表失败");
        }
        if (updOperaLogNum == null) {
            logger.info("插入操作日志表失败");
        }
    }


    public boolean updatePreliminaryByselect(String id, TransactionStatus status,
                                             String updtype, String updNum, Logger logger) throws Exception {
        logger.info("更新入门信息，视频或手册数量信息开始");
        int num = 0;
        Page page = new Page();
        PageData qureyData = new PageData();
        //添加分页信息（0-20000个视频或者手册）
        qureyData = getPage(qureyData, Const.MAX_PAGESIZE, "1");
        try {
            qureyData.put("questionTypeId", aesDecrypt(id, Const.ALLENCRYPTCODE));
            //添加分页信息进入储值域，用于传参
            page.setPd(qureyData);
            List<PageData> classTypeList0 = null;
            if (updtype.equals(Const.VIDEOS)) {
                /*查询入门视频表*/
                classTypeList0 = (List<PageData>) dao.findForList("VideoPreliminaryRelationMapper.selectByexamplePage", page);
            } else if (updtype.equals(Const.BOOKS)) {
                /*查询入门手册表*/
                classTypeList0 = (List<PageData>) dao.findForList("ManualPreliminaryRelationMapper.selectByexamplePage", page);
            }
            if (classTypeList0 != null) {
                num = classTypeList0.size() + Integer.valueOf(updNum);
            }
            PreliminaryInformation preliminaryInformation = new PreliminaryInformation();
            if (0 > num) {
                //存在脏数据，计算失误，本次不在更新
                return true;
            } else {
                if (updtype.equals(Const.VIDEOS)) {
                    preliminaryInformation.setVideoNum(String.valueOf(num));
                } else if (updtype.equals(Const.BOOKS)) {
                    preliminaryInformation.setManualNum(String.valueOf(num));
                }
                preliminaryInformation.setId(aesDecrypt(id, Const.ALLENCRYPTCODE));
                Object newUpdNum = dao.update("PreliminaryInformationMapper.updateByPrimaryKeySelective", preliminaryInformation);
                if (null == newUpdNum) {
                    return false;
                }else if("0".equals(newUpdNum.toString())){
                    logger.info("入门信息已经不存在");
                    return true;
                }
            }
        } catch (Exception e) {
            logger.info("更新入门信息，视频或手册数量信息出错");
            return false;
        }
        return true;
    }


    /**
     * 返回类型PageData data, 出入得每页条数String pageSize, 传入的当前页数String pageNum 添加分页信息公共方法
     * 王燕来 2018.12。21
     * Created by wangyanlai on 20180425.
     *
     * @author wangyanlai
     * wangyanlai@cei.gov.cn
     */

    public PageData getPage(PageData data, String pageSize, String pageNum) {
        if (StringUtils.isBlank(pageSize) || pageSize.length() >= 10) {
            pageSize = PAGE;
        }
        if (StringUtils.isBlank(pageNum) || "0".equals(pageNum) || pageNum.length() >= 10) {
            pageNum = "1";
        }
        Integer limitbegin = (Integer.valueOf(pageNum) - 1) * Integer.valueOf(pageSize);
        Integer limitend = Integer.valueOf(pageSize);
        data.put("limitbegin", limitbegin);
        data.put("limitend", limitend);
        return data;
    }
}
