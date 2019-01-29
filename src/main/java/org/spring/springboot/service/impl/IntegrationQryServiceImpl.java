
package org.spring.springboot.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.spring.springboot.dao.DaoSupport;
import org.spring.springboot.domain.*;
import org.spring.springboot.service.IntegrationQryService;
import org.spring.springboot.zw.util.*;
import org.springframework.beans.propertyeditors.UUIDEditor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.spring.springboot.zw.util.Const.PAGE;
import static org.spring.springboot.zw.util.DESUtil.aesEncrypt;
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
public class IntegrationQryServiceImpl implements IntegrationQryService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;
    private String qryTypeField = "qrytype";
    private String userIdField = "userid";

    @Override
    public String queryConfigure(Map<String, Object> reqMap, Logger logger, HttpServletRequest request) {
        ReturnData map = new ReturnData();
        PageData data = new PageData();
        Page page = new Page();
        //用于作为返回数据的二级节点
        List<PageData> contentlist = new ArrayList<PageData>();
        Object status = reqMap.get("status");
        PageData qureyData = new PageData();
        if (null != status) {
            qureyData.put("status", status.toString());
        }
        Object scoreType = reqMap.get("scoretype");
        if (null != scoreType) {
            qureyData.put("scoreType", scoreType.toString());
        }
        //添加分页信息进入储值域，用于传参
        page.setPd(qureyData);
        logger.info("查询个人积分配置信息开始");
        try {
            /*查询个人积分配置表*/
            List<PageData> classTypeList0 = (List<PageData>) dao.findForList("PerIntegrationConfigureMapper.selectAllListPage", page);
            if (classTypeList0 != null && classTypeList0.size() != 0) {
                HashMap totalMap = new HashMap();
                totalMap.put("total", String.valueOf(classTypeList0.size()));
                for (int i = 0; i < classTypeList0.size(); i++) {
                    data = new PageData();
                    HashMap tempmap = (HashMap) classTypeList0.get(i);
                    String id = tempmap.get("ID") == null ? "" : (String) tempmap.get("ID");
                    String score = tempmap.get("SCORE") == null ? "" : (String) tempmap.get("SCORE");
                    status = tempmap.get("STATUS") == null ? "" : (String) tempmap.get("STATUS");
                    scoreType = tempmap.get("SCORE_TYPE") == null ? "" : (String) tempmap.get("SCORE_TYPE");
                    String updateTm = tempmap.get("UPDATE_TM") == null ? "" : (String) tempmap.get("UPDATE_TM");
                    String remark1 = tempmap.get("REMARK1") == null ? "" : (String) tempmap.get("REMARK1");
                    //暂时没有这个字段
                    data.put("id", id);
                    data.put("score", score);
                    data.put("status", status);
                    data.put("score_type", scoreType);
                    data.put("update_tm", updateTm);
                    data.put("remark1", remark1);
                    contentlist.add(data);
                }
                map.setCode(Const.SUCCESS_CODE);
                map.setContentlist(contentlist);
                String totalMapJson = JSON.toJSONString(totalMap).replaceAll("\\{", "").replaceAll("}", ",");
                // 输出返回结果(不走最终的统一输出结果的原因是，这里的返回报文需要重新拼接一下，把总页数拼接到前边方便用户的使用)
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                //拼接总页数便于用户使用
                jsonReturn = jsonReturn.replaceAll("\"code", totalMapJson + "\"code");
                jsonReturn = jsonReturn.replaceAll("contentlist", "rows");
                logger.info("updConfig---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            } else {
                map.setCode(Const.NODATA);
                logger.info("updConfig---jsonObject.toString()---" + 1 + "---");
            }
        } catch (Exception e) {
            map.setCode(Const.FAILURE_CODE);
            logger.info("queryScore---returnStatus---1---");
        }
        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        logger.info("updConfig---jsonObject.toString()---" + jsonReturn + "---");
        return jsonReturn;
    }


    /**
     * 查询个人积分信息统一入口
     * 王燕来 2018.12。21
     * Created by wangyanlai on 20180425.
     *
     * @author wangyanlai
     * wangyanlai@cei.gov.cn
     */
    @Override
    public String qryIntegration(Map<String, Object> reqMap, Logger logger, HttpServletRequest request) {
        ReturnData map = new ReturnData();
        //初始化返回码为错误
        map.setCode(Const.FAILURE_CODE);
        //允许进行下面几种类型的积分信息查询
        String[] qryType = {Const.QUERYSCORE_PER, Const.QUERYSCORE_LOG, Const.QUERYSCORE_COURSE, Const.QUERYSCORE_QUESTION,
                Const.QUERYSCORE_TRAINING, Const.QUERYSCORE_EXCHANGE, Const.QUERYSCORE_QUESTIONNAIRE, Const.QUERYSCORE_TASK, Const.QUERYSCORE_DAYEND};
        //初始化接外部送进来的值为对象，便于进一步转换，避免因强转造成错误
        Object qryTypeObject = null;
        Object userIdObject = null;
        //初始化sql送值实体
        Page page = new Page();
        try {
            //判断字段是否为空，判断查询必须的查询类型是否为空
            //提前预置错误码，用于必输项校验
            map.setCode(Const.IRREGULAR_PARAMETERS);
            qryTypeObject = reqMap.get(qryTypeField);
            userIdObject = reqMap.get(userIdField);
            //判断送进来的查询类型字段是否合规
            if (!Arrays.asList(qryType).contains(decryptBasedDes(qryTypeObject.toString()))) {
                logger.info("查询条件不符合规范----- 积分查询类型字段不合规----");
                logger.info("queryScore---jsonObject.toString()---" + JSON.toJSONString(map) + "---");
                return JSON.toJSONString(map);
            }
            String qryTypeString = decryptBasedDes(qryTypeObject.toString());
            //下面为具体的查询逻辑，依据查询类型分别判断是否是个人积分汇总数据查询---个人积分变更日志查询---个人课程积分查询---个人其他积分信息查询
            if (!Const.QUERYSCORE_PER.equals(qryTypeString) &&
                    ((!StringUtil.isEmpty(reqMap.get("pageSize")) ||
                            !StringUtil.isEmpty(reqMap.get("pageNumber"))) ||
                            (!StringUtil.isNumeric(((Object) reqMap.get("pageSize")).toString()) ||
                                    !StringUtil.isNumeric(((Object) reqMap.get("pageNumber")).toString())))) {
                logger.info("判断如果是查询多条情况，是否传入了页码和条数，页码和条数是否符合规范---------");
                //上述判断，判断如果在查询多条情况，是否传入了页码和条数，页码和条数是否符合规范
                logger.info("查询条件不符合规范---------");
                logger.info("queryScore---jsonObject.toString()---" + JSON.toJSONString(map) + "---");
                return JSON.toJSONString(map);
            }
            //个人用户id加密密文
            String userId = userIdObject.toString();
            //积分查询类型加密密文
            String perQryType = qryTypeObject.toString();
            //用于作为返回数据的二级节点
            List<PageData> contentlist = new ArrayList<PageData>();
            //临时数据节点（用于临时保存信息便于汇总后传出）
            PageData data = new PageData();
            if (decryptBasedDes(perQryType).equals(Const.QUERYSCORE_PER)) {
                //查询码QUERYSCORE_PER【00000】查询个人积分汇总信息=====本分支仅用于查询个人积分汇总信息
                PerIntegrationBasis perIntegrationBasis = new PerIntegrationBasis();
                logger.info("查询个人积分汇总信息开始");
                //添加分页信息
                PageData qureyData = new PageData();
                Object pageSize = reqMap.get("pageSize");
                Object pageNum = reqMap.get("pageNumber");
                String pageSizeStr;
                if (null != pageSize) {
                    pageSizeStr = ((Object) reqMap.get("pageSize")).toString();
                } else {
                    pageSizeStr = Const.PAGE;
                }
                String pageNumStr;
                if (null != pageNum) {
                    pageNumStr = ((Object) reqMap.get("pageNumber")).toString();
                } else {
                    pageNumStr = "1";
                }
                //添加分页信息
                qureyData = getPage(qureyData, pageSizeStr, pageNumStr);
                if (Const.SPECIAL_USERID.equals(userId)) {
                    qureyData.put("id", null);
                } else {
                    qureyData.put("id", DESUtil.aesDecrypt(userId, Const.ALLENCRYPTCODE));
                }
                /*创建时间更新时间初始化*/
                String qryTmBegin = "";
                String qryTmEnd = "";
                String qryTmBeginUpd = "";
                String qryTmEndUpd = "";
                if (StringUtil.isEmpty(reqMap.get("qrytmbegin")) && (10 == ((Object) reqMap.get("qrytmbegin")).toString().length())) {
                    qryTmBegin = ((Object) reqMap.get("qrytmbegin")).toString();
                    qryTmBegin = qryTmBegin;
                } else {
                    qryTmBegin = Const.EARLIEST_TIME;
                    //默认最早时间1000-01-01 00:00:01
                }
                if (StringUtil.isEmpty(reqMap.get("qrytmend")) && (10 == ((Object) reqMap.get("qrytmend")).toString().length())) {
                    qryTmEnd = ((Object) reqMap.get("qrytmend")).toString();
                    qryTmEnd = qryTmEnd;
                } else {
                    qryTmEnd = Const.LATE_ARRIVAL_TIME;
                    //默认最晚时间"5000-01-01 00:00:01"
                }
                if (StringUtil.isEmpty(reqMap.get("qrytmbeginupd")) && (10 == ((Object) reqMap.get("qrytmbeginupd")).toString().length())) {
                    qryTmBeginUpd = ((Object) reqMap.get("qrytmbeginupd")).toString();
                    qryTmBeginUpd = qryTmBeginUpd + " 00:00:00";
                } else {
                    qryTmBeginUpd = Const.EARLIEST_TIME;
                    //默认最早时间1000-01-01 00:00:01
                }
                if (StringUtil.isEmpty(reqMap.get("qrytmendupd")) && (10 == ((Object) reqMap.get("qrytmendupd")).toString().length())) {
                    qryTmEndUpd = ((Object) reqMap.get("qrytmendupd")).toString();
                    qryTmEndUpd = qryTmEndUpd + " 23:59:59";
                } else {
                    qryTmEndUpd = Const.LATE_ARRIVAL_TIME;
                    //默认最晚时间"5000-01-01 00:00:01"
                }
                qureyData.put("qryTmBegin", qryTmBegin);
                qureyData.put("qryTmEnd", qryTmEnd);
                qureyData.put("qryTmBeginUpd", qryTmBeginUpd);
                qureyData.put("qryTmEndUpd", qryTmEndUpd);
                //添加分页信息进入储值域，用于传参
                page.setPd(qureyData);
                // 查询个人积分基本信息表
                List<PageData> classTypeListBasis = (List<PageData>) dao.findForList("PerIntegrationBasisMapper.selectByexamplePage", page);
                if (classTypeListBasis != null && classTypeListBasis.size() != 0) {
                    HashMap totalMap = new HashMap();
                    totalMap.put("num", classTypeListBasis.size() + "");
                    for (int i = 0; i < classTypeListBasis.size(); i++) {
                        data = new PageData();
                        HashMap tempmap = (HashMap) classTypeListBasis.get(i);
                        totalMap.put("total", null == tempmap.get("total") ? "0" : tempmap.get("total").toString());
                        data.put("score", tempmap.get("SCORE") == null ? "" : tempmap.get("SCORE").toString());
                        data.put("last_time_score", tempmap.get("LAST_TIME_SCORE") == null ? "" : tempmap.get("LAST_TIME_SCORE").toString());
                        data.put("yesterday_score", tempmap.get("YESTERDAY_SCORE") == null ? "" : tempmap.get("YESTERDAY_SCORE").toString());
                        data.put("update_tm", tempmap.get("UPDATE_TM") == null ? "" : tempmap.get("UPDATE_TM").toString());
                        data.put("creation_tm", tempmap.get("CREATION_TM") == null ? "" : tempmap.get("CREATION_TM").toString());
                        data.put("original_score", tempmap.get("ORIGINAL_SCORE") == null ? "" : tempmap.get("ORIGINAL_SCORE").toString());
                        data.put("id", tempmap.get("ID") == null ? "" : aesEncrypt(tempmap.get("ID").toString(), Const.ALLENCRYPTCODE));
                        userId = tempmap.get("ID") == null ? "" : tempmap.get("ID").toString();
                        data = findAllOtherData(userId, logger, data);
                        if (null == data) {
                            map.setCode(Const.NODATA);
                            logger.info("queryScore---returnStatus---1---");
                            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                            //拼接总页数便于用户使用
                            return jsonReturn;
                        }
                        contentlist.add(data);
                    }
                    map.setCode(Const.SUCCESS_CODE);
                    map.setContentlist(contentlist);
                    String totalMapJson = JSON.toJSONString(totalMap).replaceAll("\\{", "").replaceAll("}", ",");
                    // 输出返回结果(不走最终的统一输出结果的原因是，这里的返回报文需要重新拼接一下，把总页数拼接到前边方便用户的使用)
                    String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                    //拼接总页数便于用户使用
                    jsonReturn = jsonReturn.replaceAll("\"code", totalMapJson + "\"code");
                    jsonReturn = jsonReturn.replaceAll("contentlist", "rows");
                    logger.info("queryScore---jsonObject.toString()---" + totalMapJson + "---");
                    logger.info("插入操作日志");
                    insertSysOperatelog(userId, Const.QUERYSCORE_LOG, logger, request);
                    return jsonReturn;
                } else {
                    map.setCode(Const.NODATA);
                    logger.info("queryScore---returnStatus---1---");
                }
            } else if (decryptBasedDes(perQryType).equals(Const.QUERYSCORE_LOG)) {
                //QUERYSCORE_LOG【10000】查询个人积分汇总信息=====本分支仅用于查询个人积分变更日志列表
                PageData qureyData = new PageData();
                String pageSize = ((Object) reqMap.get("pageSize")).toString();
                String pageNum = ((Object) reqMap.get("pageNumber")).toString();
                //添加分页信息
                qureyData = getPage(qureyData, pageSize, pageNum);
                //添加查询时间作为条件

                if (Const.SPECIAL_USERID.equals(userId)) {
                    qureyData.put("id", "");
                } else {
                    qureyData.put("id", DESUtil.aesDecrypt(userId, Const.ALLENCRYPTCODE));
                }
                /*创建时间更新时间初始化*/
                String qryTmBegin = "";
                String qryTmEnd = "";
                String qryTmBeginUpd = "";
                String qryTmEndUpd = "";
                if (StringUtil.isEmpty(reqMap.get("qrytmbegin")) && (10 == ((Object) reqMap.get("qrytmbegin")).toString().length())) {
                    qryTmBegin = ((Object) reqMap.get("qrytmbegin")).toString();
                    qryTmBegin = qryTmBegin + " 00:00:00";
                } else {
                    qryTmBegin = Const.EARLIEST_TIME;
                    //默认最早时间1000-01-01 00:00:01
                }
                if (StringUtil.isEmpty(reqMap.get("qrytmend")) && (10 == ((Object) reqMap.get("qrytmend")).toString().length())) {
                    qryTmEnd = ((Object) reqMap.get("qrytmend")).toString();
                    qryTmEnd = qryTmEnd + " 23:59:59";
                } else {
                    qryTmEnd = Const.LATE_ARRIVAL_TIME;
                    //默认最晚时间"5000-01-01 00:00:01"
                }
                if (StringUtil.isEmpty(reqMap.get("qrytmbeginupd")) && (10 == ((Object) reqMap.get("qrytmbeginupd")).toString().length())) {
                    qryTmBeginUpd = ((Object) reqMap.get("qrytmbeginupd")).toString();
                    qryTmBeginUpd = qryTmBeginUpd + " 00:00:00";
                } else {
                    qryTmBeginUpd = Const.EARLIEST_TIME;
                    //默认最早时间1000-01-01 00:00:01
                }
                if (StringUtil.isEmpty(reqMap.get("qrytmendupd")) && (10 == ((Object) reqMap.get("qrytmendupd")).toString().length())) {
                    qryTmEndUpd = ((Object) reqMap.get("qrytmendupd")).toString();
                    qryTmEndUpd = qryTmEndUpd + " 23:59:59";
                } else {
                    qryTmEndUpd = Const.LATE_ARRIVAL_TIME;
                    //默认最晚时间"5000-01-01 00:00:01"
                }
                qureyData.put("qryTmBegin", qryTmBegin);
                qureyData.put("qryTmEnd", qryTmEnd);
                qureyData.put("qryTmBeginUpd", qryTmBeginUpd);
                qureyData.put("qryTmEndUpd", qryTmEndUpd);
                //添加分页信息进入储值域，用于传参
                page.setPd(qureyData);
                // 查询积分日志
                HashMap totalMap = new HashMap();
                List<PageData> classTypeList0 = (List<PageData>) dao.findForList("PerIntegrationLogMapper.selectByexamplePage", page);
                if (classTypeList0 != null && classTypeList0.size() != 0) {
                    totalMap.put("num", classTypeList0.size() + "");
                    for (int i = 0; i < classTypeList0.size(); i++) {
                        data = new PageData();
                        HashMap tempmap = (HashMap) classTypeList0.get(i);
                        userId = tempmap.get("USER_ID") == null ? "" : tempmap.get("USER_ID").toString();
                        data.put(userIdField, aesEncrypt(userId, Const.ALLENCRYPTCODE));
                        totalMap.put("total", null == tempmap.get("total") ? "" : tempmap.get("total").toString());
                        data.put("score", tempmap.get("SCORE") == null ? "" : tempmap.get("SCORE").toString());
                        data.put("last_time_score", tempmap.get("LAST_TIME_SCORE") == null ? "" : tempmap.get("LAST_TIME_SCORE").toString());
                        data.put("upd_score", tempmap.get("UPD_SCORE") == null ? "" : tempmap.get("UPD_SCORE").toString());
                        String scoreType = tempmap.get("SCORE_TYPE") == null ? "" : tempmap.get("SCORE_TYPE").toString();
                        if (null == scoreType || "" == scoreType) {
                            data.put("score_type", "");
                        } else if (Const.LOGIN_UPDSCORE.equals(scoreType)) {
                            data.put("score_type", Const.DETAIL_LOGIN_UPDSCORE);
                        } else if (Const.LEARN_UPDSCORE.equals(scoreType)) {
                            data.put("score_type", Const.DETAIL_LEARN_UPDSCORE);
                        } else if (Const.COMMENT_UPDSCORE.equals(scoreType)) {
                            data.put("score_type", Const.DETAIL_COMMENT_UPDSCORE + "置为精华");
                        } else if (Const.HOSTILITY_COMMENT_UPDSCORE.equals(scoreType)) {
                            data.put("score_type", Const.DETAIL_HOSTILITY_COMMENT_UPDSCORE);
                        } else if (Const.INVESTIGATION_UPDSCORE.equals(scoreType)) {
                            data.put("score_type", Const.DETAIL_INVESTIGATION_UPDSCORE);
                        } else if (Const.SHARE_UPDSCORE.equals(scoreType)) {
                            data.put("score_type", Const.DETAIL_SHARE_UPDSCORE);
                        } else if (Const.TASK_UPDSCORE.equals(scoreType)) {
                            data.put("score_type", Const.DETAIL_TASK_UPDSCORE);
                        } else if (Const.APPRECIATE_UPDSCORE.equals(scoreType)) {
                            data.put("score_type", Const.DETAIL_APPRECIATE_UPDSCORE);
                        } else if (Const.EXCHANGE_UPDSCORE.equals(scoreType)) {
                            data.put("score_type", Const.DETAIL_EXCHANGE_UPDSCORE);
                        } else if (Const.TRAINING_UPDSCORE.equals(scoreType)) {
                            data.put("score_type", Const.DETAIL_TRAINING_UPDSCORE);
                        } else if (Const.AUTO_UPDSCORE.equals(scoreType)) {
                            data.put("score_type", "系统自动跑批");
                        }
                        data.put("total", tempmap.get("total") == null ? "" : tempmap.get("total").toString());
                        String creationDt = ((Object) tempmap.get("CREATION_DT")).toString();
                        if (null == creationDt || "" == creationDt || creationDt.length() != 8) {
                            data.put("creation_dt", "");
                        } else {
                            data.put("creation_dt", creationDt);
                        }
                        data.put("id", null == tempmap.get("ID") ? "" : tempmap.get("ID").toString());

                        data.put("course_id", tempmap.get("COURSE_ID") == null ? "" : tempmap.get("COURSE_ID").toString());
                        data.put("question_id", tempmap.get("QUESTION_ID") == null ? "" : tempmap.get("QUESTION_ID").toString());
                        data.put("task_id", tempmap.get("TASK_ID") == null ? "" : tempmap.get("TASK_ID").toString());
                        data.put("training_id", tempmap.get("TRAINING_ID") == null ? "" : tempmap.get("TRAINING_ID").toString());
                        data.put("exchange_id", tempmap.get("EXCHANGE_ID") == null ? "" : tempmap.get("EXCHANGE_ID").toString());
                        data.put("exchange_id", tempmap.get("EXCHANGE_ID") == null ? "" : tempmap.get("EXCHANGE_ID").toString());
                        data.put("logname", tempmap.get("LOGNAME") == null ? "" : tempmap.get("LOGNAME").toString());
                        data.put("ip", tempmap.get("IP") == null ? "" : tempmap.get("IP").toString());
                        data.put("creation_tm", tempmap.get("CREATION_TM") == null ? "" : tempmap.get("CREATION_TM").toString());
                        data.put("update_tm", tempmap.get("UPDATE_TM") == null ? "" : tempmap.get("UPDATE_TM").toString());
                        contentlist.add(data);
                    }
                    map.setCode(Const.SUCCESS_CODE);
                    map.setContentlist(contentlist);
                    String totalMapJson = JSON.toJSONString(totalMap).replaceAll("\\{", "").replaceAll("}", ",");
                    // 输出返回结果(不走最终的统一输出结果的原因是，这里的返回报文需要重新拼接一下，把总页数拼接到前边方便用户的使用)
                    String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                    //拼接总页数便于用户使用
                    jsonReturn = jsonReturn.replaceAll("\"code", totalMapJson + "\"code");
                    jsonReturn = jsonReturn.replaceAll("contentlist", "rows");
                    logger.info("queryScore---jsonObject.toString()---" + totalMapJson + "---");
                    logger.info("插入积分查询操作日志");
                    insertSysOperatelog(userId, Const.QUERYSCORE_LOG, logger, request);
                    return jsonReturn;
                } else {
                    map.setCode(Const.NODATA);
                    logger.info("queryScore---returnStatus---1---");
                }
            } else if (decryptBasedDes(qryTypeObject.toString()).equals(Const.QUERYSCORE_COURSE)) {
                //QUERYSCORE_COURSE【20000】查询个人课程积分列表=====本分支仅用于查询个人课程积分列表
                PageData qureyData = new PageData();
                String pageSize = ((Object) reqMap.get("pageSize")).toString();
                String pageNum = ((Object) reqMap.get("pageNumber")).toString();
                //添加分页信息
                qureyData = getPage(qureyData, pageSize, pageNum);
                /*创建时间更新时间初始化*/
                String qryTmBegin = "";
                String qryTmEnd = "";
                String qryTmBeginUpd = "";
                String qryTmEndUpd = "";
                if (StringUtil.isEmpty(reqMap.get("qrytmbegin")) && (10 == ((Object) reqMap.get("qrytmbegin")).toString().length())) {
                    qryTmBegin = ((Object) reqMap.get("qrytmbegin")).toString();
                    qryTmBegin = qryTmBegin + " 00:00:00";
                } else {
                    qryTmBegin = Const.EARLIEST_TIME;
                    //默认最早时间1000-01-01 00:00:01
                }
                if (StringUtil.isEmpty(reqMap.get("qrytmend")) && (10 == ((Object) reqMap.get("qrytmend")).toString().length())) {
                    qryTmEnd = ((Object) reqMap.get("qrytmend")).toString();
                    qryTmEnd = qryTmEnd + " 23:59:59";
                } else {
                    qryTmEnd = Const.LATE_ARRIVAL_TIME;
                    //默认最晚时间"5000-01-01 00:00:01"
                }
                if (StringUtil.isEmpty(reqMap.get("qrytmbeginupd")) && (10 == ((Object) reqMap.get("qrytmbeginupd")).toString().length())) {
                    qryTmBeginUpd = ((Object) reqMap.get("qrytmbeginupd")).toString();
                    qryTmBeginUpd = qryTmBeginUpd + " 00:00:00";
                } else {
                    qryTmBeginUpd = Const.EARLIEST_TIME;
                    //默认最早时间1000-01-01 00:00:01
                }
                if (StringUtil.isEmpty(reqMap.get("qrytmendupd")) && (10 == ((Object) reqMap.get("qrytmendupd")).toString().length())) {
                    qryTmEndUpd = ((Object) reqMap.get("qrytmendupd")).toString();
                    qryTmEndUpd = qryTmEndUpd + " 23:59:59";
                } else {
                    qryTmEndUpd = Const.LATE_ARRIVAL_TIME;
                    //默认最晚时间"5000-01-01 00:00:01"
                }
                qureyData.put("qryTmBegin", qryTmBegin);
                qureyData.put("qryTmEnd", qryTmEnd);
                qureyData.put("qryTmBeginUpd", qryTmBeginUpd);
                qureyData.put("qryTmEndUpd", qryTmEndUpd);
                if (!Const.SPECIAL_USERID.equals(userId)) {
                    qureyData.put("userId", DESUtil.aesDecrypt(userId, Const.ALLENCRYPTCODE));
                }
                page.setPd(qureyData);
                // 查询课程积分信息列表
                List<PageData> classTypeList0 = (List<PageData>) dao.findForList("PerCourseIntegrationMapper.selectByexamplePage", page);
                if (classTypeList0 != null && classTypeList0.size() != 0) {
                    HashMap totalMap = new HashMap();
                    totalMap.put("num", classTypeList0.size() + "");
                    for (int i = 0; i < classTypeList0.size(); i++) {
                        data = new PageData();
                        HashMap tempmap = (HashMap) classTypeList0.get(i);
                        totalMap.put("total", null == tempmap.get("total") ? "0" : tempmap.get("total").toString());
                        data.put(userIdField, tempmap.get("USER_ID") == null ? "" : aesEncrypt(tempmap.get("USER_ID").toString(), Const.ALLENCRYPTCODE));
                        data.put("score", tempmap.get("SCORE") == null ? "" : tempmap.get("SCORE").toString());
                        data.put("comment_num", tempmap.get("COMMENT_NUM") == null ? "" : tempmap.get("COMMENT_NUM").toString());
                        data.put("learning_tm", tempmap.get("LEARNING_TM") == null ? "" : tempmap.get("LEARNING_TM").toString());
                        data.put("total", tempmap.get("total") == null ? "0" : tempmap.get("total").toString());
                        data.put("update_tm", tempmap.get("UPDATE_TM") == null ? "" : tempmap.get("UPDATE_TM").toString());
                        data.put("creation_tm", tempmap.get("CREATION_TM") == null ? "" : tempmap.get("CREATION_TM").toString());
                        data.put("if_share", tempmap.get("IF_SHARE") == null ? "" : tempmap.get("IF_SHARE").toString());
                        data.put("if_learn", tempmap.get("IF_LEARN") == null ? "" : tempmap.get("IF_LEARN").toString());
                        data.put("id", tempmap.get("ID") == null ? "" : tempmap.get("ID").toString());
                        data.put("if_assess", tempmap.get("IF_ASSESS") == null ? "" : tempmap.get("IF_ASSESS").toString());
                        data.put("if_comment", tempmap.get("IF_COMMENT") == null ? "" : tempmap.get("IF_COMMENT").toString());
                        contentlist.add(data);
                    }
                    map.setCode(Const.SUCCESS_CODE);
                    map.setContentlist(contentlist);
                    String totalMapJson = JSON.toJSONString(totalMap).replaceAll("\\{", "").replaceAll("}", ",");
                    // 输出返回结果
                    String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                    //拼接总页数便于用户使用
                    jsonReturn = jsonReturn.replaceAll("\"code", totalMapJson + "\"code");
                    jsonReturn = jsonReturn.replaceAll("contentlist", "rows");
                    logger.info("queryScore---jsonObject.toString()---" + totalMapJson + "---");
                    logger.info("插入积分查询操作日志");
                    insertSysOperatelog(DESUtil.aesDecrypt(userId, Const.ALLENCRYPTCODE), Const.QUERYSCORE_COURSE, logger, request);
                    return jsonReturn;
                } else {
                    map.setCode(Const.NODATA);
                    logger.info("queryScore---returnStatus---1---");
                }
            } else {
                PageData qureyData = new PageData();
                //每页条数
                String pageSize = ((Object) reqMap.get("pageSize")).toString();
                //第几页
                String pageNum = ((Object) reqMap.get("pageNumber")).toString();
                //添加分页信息
                qureyData = getPage(qureyData, pageSize, pageNum);
                //添加查询时间作为条件
                /*创建时间更新时间初始化*/
                String qryTmBegin = "";
                String qryTmEnd = "";
                String qryTmBeginUpd = "";
                String qryTmEndUpd = "";
                if (StringUtil.isEmpty(reqMap.get("qrytmbegin")) && (10 == ((Object) reqMap.get("qrytmbegin")).toString().length())) {
                    qryTmBegin = ((Object) reqMap.get("qrytmbegin")).toString();
                    qryTmBegin = qryTmBegin + " 00:00:00";
                } else {
                    qryTmBegin = Const.EARLIEST_TIME;
                    //默认最早时间1000-01-01 00:00:01
                }
                if (StringUtil.isEmpty(reqMap.get("qrytmend")) && (10 == ((Object) reqMap.get("qrytmend")).toString().length())) {
                    qryTmEnd = ((Object) reqMap.get("qrytmend")).toString();
                    qryTmEnd = qryTmEnd + " 23:59:59";
                } else {
                    qryTmEnd = Const.LATE_ARRIVAL_TIME;
                    //默认最晚时间"5000-01-01 00:00:01"
                }
                if (StringUtil.isEmpty(reqMap.get("qrytmbeginupd")) && (10 == ((Object) reqMap.get("qrytmbeginupd")).toString().length())) {
                    qryTmBeginUpd = ((Object) reqMap.get("qrytmbeginupd")).toString();
                    qryTmBeginUpd = qryTmBeginUpd + " 00:00:00";
                } else {
                    qryTmBeginUpd = Const.EARLIEST_TIME;
                    //默认最早时间1000-01-01 00:00:01
                }
                if (StringUtil.isEmpty(reqMap.get("qrytmendupd")) && (10 == ((Object) reqMap.get("qrytmendupd")).toString().length())) {
                    qryTmEndUpd = ((Object) reqMap.get("qrytmendupd")).toString();
                    qryTmEndUpd = qryTmEndUpd + " 23:59:59";
                } else {
                    qryTmEndUpd = Const.LATE_ARRIVAL_TIME;
                    //默认最晚时间"5000-01-01 00:00:01"
                }
                qureyData.put("qryTmBegin", qryTmBegin);
                qureyData.put("qryTmEnd", qryTmEnd);
                qureyData.put("qryTmBeginUpd", qryTmBeginUpd);
                qureyData.put("qryTmEndUpd", qryTmEndUpd);
                if (Const.SPECIAL_USERID.equals(userId)) {
                    qureyData.put("userId", "");
                } else {
                    qureyData.put("userId", DESUtil.aesDecrypt(userId, Const.ALLENCRYPTCODE));
                }
                page.setPd(qureyData);
                List<PageData> classTypeList0 = null;
                // 查询提出问题（点赞）积分列表...等
                if (decryptBasedDes(perQryType).equals(Const.QUERYSCORE_QUESTION)) {
                    // 表示个人问题积分列表 30000
                    classTypeList0 = (List<PageData>) dao.findForList("PerQuestionIntegrationMapper.selectByexamplePage", page);
                } else if (decryptBasedDes(perQryType).equals(Const.QUERYSCORE_TRAINING)) {
                    // 表示个人培训班积分列表 40000
                    classTypeList0 = (List<PageData>) dao.findForList("PerTrainingIntegrationMapper.selectByexamplePage", page);
                } else if (decryptBasedDes(perQryType).equals(Const.QUERYSCORE_EXCHANGE)) {
                    // 表示查询个人兑换积分列表 50000
                    classTypeList0 = (List<PageData>) dao.findForList("PerExchangeIntegrationMapper.selectByexamplePage", page);
                } else if (decryptBasedDes(perQryType).equals(Const.QUERYSCORE_QUESTIONNAIRE)) {
                    // 表示个人问卷调查积分列表 60000
                    classTypeList0 = (List<PageData>) dao.findForList("PerQuestionnaireIntegrationMapper.selectByexamplePage", page);
                } else if (decryptBasedDes(perQryType).equals(Const.QUERYSCORE_TASK)) {
                    // 表示个人作业积分列表 70000
                    classTypeList0 = (List<PageData>) dao.findForList("PerTaskIntegrationMapper.selectByexamplePage", page);
                } else if (decryptBasedDes(perQryType).equals(Const.QUERYSCORE_DAYEND)) {
                    // 表示日终跑批信息列表 80000
                    classTypeList0 = (List<PageData>) dao.findForList("PerDayendRunbatchMapper.selectByexamplePage", page);
                }
                if (classTypeList0 != null && classTypeList0.size() != 0) {
                    HashMap totalMap = new HashMap();
                    totalMap.put("num", classTypeList0.size() + "");
                    for (int i = 0; i < classTypeList0.size(); i++) {
                        data = new PageData();
                        HashMap tempmap = (HashMap) classTypeList0.get(i);
                        totalMap.put("total", null == tempmap.get("total") ? "0" : tempmap.get("total").toString());
                        data.put("exchange_score", tempmap.get("EXCHANGE_SCORE") == null ? "" : tempmap.get("EXCHANGE_SCORE").toString());
                        data.put("if_graduation", tempmap.get("IF_GRADUATION") == null ? "" : tempmap.get("IF_GRADUATION").toString());
                        data.put("if_score", tempmap.get("IF_SCORE") == null ? "" : tempmap.get("IF_SCORE").toString());
                        if (tempmap.get("BATCH_TYPE") == null || tempmap.get("BATCH_TYPE") == "") {
                            data.put("batch_type", "");
                        } else if (Const.AUTO_UPDSCORE.equals(tempmap.get("BATCH_TYPE").toString())) {
                            data.put("batch_type", Const.DEFAULT_AUTO_UPDSCORE);
                        }
                        data.put(userIdField, tempmap.get("USER_ID") == null ? "" : aesEncrypt(tempmap.get("USER_ID").toString(), Const.ALLENCRYPTCODE));
                        data.put("score", tempmap.get("SCORE") == null ? "" : tempmap.get("SCORE").toString());
                        data.put("id", tempmap.get("ID") == null ? "" : tempmap.get("ID").toString());
                        data.put("total", tempmap.get("total") == null ? "0" : tempmap.get("total").toString());
                        data.put("is_succ", tempmap.get("IS_SUCC") == null ? "0" : tempmap.get("IS_SUCC").toString());
                        data.put("update_tm", tempmap.get("UPDATE_TM") == null ? "" : tempmap.get("UPDATE_TM").toString());
                        data.put("creation_tm", tempmap.get("CREATION_TM") == null ? "" : tempmap.get("CREATION_TM").toString());
                        contentlist.add(data);
                    }
                    map.setCode(Const.SUCCESS_CODE);
                    map.setContentlist(contentlist);
                    String totalMapJson = JSON.toJSONString(totalMap).replaceAll("\\{", "").replaceAll("}", ",");
                    // 输出返回结果
                    String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                    jsonReturn = jsonReturn.replaceAll("\"code", totalMapJson + "\"code");
                    jsonReturn = jsonReturn.replaceAll("contentlist", "rows");
                    logger.info("queryScore---jsonObject.toString()---" + totalMapJson + "---");
                    logger.info("插入积分查询操作日志");
                    insertSysOperatelog(DESUtil.aesDecrypt(userId, Const.ALLENCRYPTCODE), decryptBasedDes(perQryType), logger, request);
                    return jsonReturn;
                } else {
                    map.setCode(Const.NODATA);
                    logger.info("queryScore---returnStatus---1---");
                }
            }
        } catch (Exception e) {
            // 系统错误
            map.setCode(Const.FAILURE_CODE);
        }
        // 统一输出返回结果
        HashMap totalMap = new HashMap();
        if (!Const.SUCCESS_CODE.equals(map.getCode())) {
            totalMap.put("total", "0");
        }
        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        String totalMapJson = JSON.toJSONString(totalMap).replaceAll("\\{", "").replaceAll("}", ",");
        jsonReturn = jsonReturn.replaceAll("\"code", totalMapJson + "\"code");
        logger.info("queryScore---jsonObject.toString()---" + jsonReturn + "---");
        return jsonReturn;
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

    /**
     * Title: 用户操作日志新增:
     *
     * @author wangyanlai
     * @version 2019年1月3日 下午4:29:44
     * wangyanlai@cei.gov.cn
     */
    public void insertSysOperatelog(String userId, String type, Logger logger, HttpServletRequest request) throws Exception {
        SysOperatelog sysOperatelog = new SysOperatelog();
        sysOperatelog.setCreatetime(DateUtil.getTime());
        sysOperatelog.setIp((getIpAddress(request)));
        sysOperatelog.setLogid(UuidUtil.get32UUID());
        if (Const.QUERYSCORE_PER.equals(type)) {
            sysOperatelog.setLogname("学员[" + userId + "]通过调用接口,查询个人积分汇总信息");
        } else if (Const.QUERYSCORE_LOG.equals(type)) {
            sysOperatelog.setLogname("学员[" + userId + "]通过调用接口,查询个人积分变更日志列表");
        } else if (Const.QUERYSCORE_COURSE.equals(type)) {
            sysOperatelog.setLogname("学员[" + userId + "]通过调用接口,查询个人课程积分列表");
        } else if (Const.QUERYSCORE_QUESTION.equals(type)) {
            sysOperatelog.setLogname("学员[" + userId + "]通过调用接口,查询个人问题积分列表");
        } else if (Const.QUERYSCORE_TRAINING.equals(type)) {
            sysOperatelog.setLogname("学员[" + userId + "]通过调用接口,查询个人培训积分列表");
        } else if (Const.QUERYSCORE_EXCHANGE.equals(type)) {
            sysOperatelog.setLogname("学员[" + userId + "]通过调用接口,查询个人兑换积分列表");
        } else if (Const.QUERYSCORE_QUESTIONNAIRE.equals(type)) {
            sysOperatelog.setLogname("学员[" + userId + "]通过调用接口,查询个人问卷调查积分列表");
        } else if (Const.QUERYSCORE_TASK.equals(type)) {
            sysOperatelog.setLogname("学员[" + userId + "]通过调用接口,查询个人作业积分列表");
        } else {
            sysOperatelog.setLogname("学员[" + userId + "]通过调用接口,查询个人积分信息");
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
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


    public PageData findAllOtherData(String userId, Logger logger, PageData data) {
        //当前方法用来查询该用户的各类积分汇总信息
        logger.info("查询该用户的各类积分汇总信息");
        PageData qureyData = new PageData();
        //添加分页信息进入储值域，用于传参
        Page page = new Page();
        qureyData.put("userId", userId);
        qureyData.put("qryTmBegin", Const.EARLIEST_TIME);
        qureyData.put("qryTmEnd", Const.LATE_ARRIVAL_TIME);
        logger.info("查询该用户登陆积分汇总");
        qureyData.put("scoreType", Const.LOGIN_UPDSCORE);
        page.setPd(qureyData);
        if (null != findByScoreType(page, logger)) {
            data.put("loginscore", findByScoreType(page, logger));
        } else {
            return null;
        }
        logger.info("查询该用户课程学习积分汇总");
        qureyData.put("scoreType", Const.LEARN_UPDSCORE);
        page.setPd(qureyData);
        if (null != findByScoreType(page, logger)) {
            data.put("learnscore", findByScoreType(page, logger));
        } else {
            return null;
        }

        logger.info("查询该用户课程评论获得积分汇总");
        qureyData.put("scoreType", Const.COMMENT_UPDSCORE);
        page.setPd(qureyData);
        if (null != findByScoreType(page, logger)) {
            data.put("commentscore", findByScoreType(page, logger));
        } else {
            return null;
        }

        logger.info("查询该用户课程恶意评论损失积分汇总");
        qureyData.put("scoreType", Const.HOSTILITY_COMMENT_UPDSCORE);
        page.setPd(qureyData);
        if (null != findByScoreType(page, logger)) {
            data.put("hostilitycommentscore", findByScoreType(page, logger));
        } else {
            return null;
        }

        logger.info("查询该用户问卷调查获得积分汇总");
        qureyData.put("scoreType", Const.INVESTIGATION_UPDSCORE);
        page.setPd(qureyData);
        if (null != findByScoreType(page, logger)) {
            data.put("questionnairescore", findByScoreType(page, logger));
        } else {
            return null;
        }

        logger.info("查询该用户作业精华获得积分汇总");
        qureyData.put("scoreType", Const.TASK_UPDSCORE);
        page.setPd(qureyData);
        if (null != findByScoreType(page, logger)) {
            data.put("taskscore", findByScoreType(page, logger));
        } else {
            return null;
        }

        logger.info("查询该用户问题点赞获得积分汇总");
        qureyData.put("scoreType", Const.APPRECIATE_UPDSCORE);
        page.setPd(qureyData);
        if (null != findByScoreType(page, logger)) {
            data.put("questionscore", findByScoreType(page, logger));
        } else {
            return null;
        }

        logger.info("查询该用户兑换积分损失积分汇总");
        qureyData.put("scoreType", Const.EXCHANGE_UPDSCORE);
        page.setPd(qureyData);
        if (null != findByScoreType(page, logger)) {
            data.put("exchangescore", findByScoreType(page, logger));
        } else {
            return null;
        }

        logger.info("查询该用户培训班结业获取积分汇总");
        qureyData.put("scoreType", Const.TRAINING_UPDSCORE);
        page.setPd(qureyData);
        if (null != findByScoreType(page, logger)) {
            data.put("trainscore", findByScoreType(page, logger));
        } else {
            return null;
        }

        logger.info("查询该用户日终跑批损失积分汇总");
        qureyData.put("scoreType", Const.AUTO_UPDSCORE);
        page.setPd(qureyData);
        if (null != findByScoreType(page, logger)) {
            data.put("dailytaskscore", findByScoreType(page, logger));
        } else {
            return null;
        }
        return data;
    }

    /*单独的查询个人日志信息表的各个子类别的公共方法[主要目的是对各个类别的积分进行汇总计算，然后返回]*/
    public Integer findByScoreType(Page page, Logger logger) {
        List<PageData> classTypeList = null;
        int score = 0;
        String newScore = "";
        try {
            classTypeList = (List<PageData>) dao.findForList("PerIntegrationLogMapper.selectScoreType", page);
            if (classTypeList != null && classTypeList.size() != 0) {
                HashMap totalMap = new HashMap();
                int classTypeListSize = classTypeList.size();
                for (int i = 0; i < classTypeListSize; i++) {
                    HashMap tempmap = (HashMap) classTypeList.get(i);
                    newScore = tempmap.get("SCORE") == null ? "0" : tempmap.get("SCORE").toString();
                    score = score + Integer.valueOf(newScore);
                }
            }
        } catch (Exception e) {
            return null;
        }
        return score;
    }


}
