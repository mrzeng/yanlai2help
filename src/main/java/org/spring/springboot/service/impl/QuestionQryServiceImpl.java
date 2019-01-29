
package org.spring.springboot.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.spring.springboot.dao.DaoSupport;
import org.spring.springboot.domain.*;
import org.spring.springboot.service.IntegrationQryService;
import org.spring.springboot.service.QuestionQryService;
import org.spring.springboot.zw.util.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.spring.springboot.zw.util.Const.PAGE;
import static org.spring.springboot.zw.util.DESUtil.aesDecrypt;
import static org.spring.springboot.zw.util.DESUtil.aesEncrypt;
import static org.spring.springboot.zw.util.DESUtil.decryptBasedDes;

/**
 * Title: APPServiceImpl Description:
 *
 * @author wangyanlai
 * @version 2019年2月26日 上午8:31:56
 */
@Service
public class QuestionQryServiceImpl implements QuestionQryService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;
    private String qryTypeField = "qrytype";
    private String userIdField = "userid";


    /**
     * Title: APPServiceImpl Description:
     * 查询问题分类信息
     *
     * @author wangyanlai
     * @version 2019年1月26日 上午8:31:56
     */
    @Override
    public String treeGrid(Map<String, Object> reqMap, Logger logger, HttpServletRequest request) {
        ReturnData map = new ReturnData();
        map.setCode(Const.EMPTY_CODE);
        PageData data = new PageData();
        Page page = new Page();
        Object userId = reqMap.get("userid");
        //用于作为返回数据的二级节点
        List<PageData> contentlist = new ArrayList<PageData>();
        Object qryType = reqMap.get("qrytype");
        PageData qureyData = new PageData();
        //添加分页信息//因为是分类列表所以直接展示2万个以内的类别，防止出现展示不全现象
        String pageSizeStr = Const.MAX_PAGESIZE;
        String pageNumStr = "1";
        qureyData = getPage(qureyData, pageSizeStr, pageNumStr);
        Object status = reqMap.get("status");
        if (null != status) {
            qureyData.put("status", status.toString());
        }
        Object id = reqMap.get("id");
        if (null != id) {
            qureyData.put("id", id.toString());
        }

        /*创建时间更新时间初始化*/
        qureyData.put("qryTmBegin", Const.EARLIEST_TIME);
        qureyData.put("qryTmEnd", Const.LATE_ARRIVAL_TIME);
        qureyData.put("qryTmBeginUpd", Const.EARLIEST_TIME);
        qureyData.put("qryTmEndUpd", Const.LATE_ARRIVAL_TIME);
        //最终赋值
        page.setPd(qureyData);
        logger.info("查询系统问题分类信息开始");
        logger.info("查询条件===" + JSON.toJSONString(page));
        try {
            /*查询问题类别信息表*/
            List<PageData> classTypeList0 = (List<PageData>) dao.findForList("QuestionTypeInfoMapper.selectByexamplePage", page);
            if (classTypeList0 != null && classTypeList0.size() != 0) {
                HashMap totalMap = new HashMap();
                totalMap.put("total", String.valueOf(classTypeList0.size()));
                for (int i = 0; i < classTypeList0.size(); i++) {
                    data = new PageData();
                    HashMap tempmap = (HashMap) classTypeList0.get(i);
                    data.put("id", tempmap.get("ID") == null ? "" : tempmap.get("ID"));
                    data.put("name", tempmap.get("QUESTION_TYPE_NAME") == null ? "" : (String) tempmap.get("QUESTION_TYPE_NAME"));
                    data.put("parentId", tempmap.get("FATHER_ID") == null ? "" : (String) tempmap.get("FATHER_ID"));
                    data.put("status", tempmap.get("STATUS") == null ? "" : (String) tempmap.get("STATUS"));
                    data.put("update_tm", tempmap.get("UPDATE_TM") == null ? "" : (String) tempmap.get("UPDATE_TM"));
                    contentlist.add(data);
                }
                map.setCode(Const.SUCCESS_CODE);
                String newUserId = null;
                Object useridObj = reqMap.get("userid");
                if (StringUtil.isEmpty(useridObj)) {
                    newUserId = aesDecrypt(useridObj.toString(), Const.ALLENCRYPTCODE);
                } else {
                    newUserId = aesDecrypt((String) request.getSession().getAttribute("sessionUserId"), Const.ALLENCRYPTCODE);
                }
                insertSysOperatelog(newUserId, Const.QUERY_QUESTION_TYPE_LIST, logger, request);
                map.setContentlist(contentlist);
                String totalMapJson = JSON.toJSONString(totalMap).replaceAll("\\{", "").replaceAll("}", ",");
                // 输出返回结果(不走最终的统一输出结果的原因是，这里的返回报文需要重新拼接一下，把总页数拼接到前边方便用户的使用)
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                //拼接总页数便于用户使用,把节点名称换成rows支持bootstrap
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
     * Title: APPServiceImpl Description:
     * <p>
     * 查询问题回复信息
     *
     * @author wangyanlai
     * @version 2019年1月26日 上午8:31:56
     */
    @Override
    public String queryQuestionReply(Map<String, Object> reqMap, Logger logger, HttpServletRequest request) {
        ReturnData map = new ReturnData();
        map.setCode(Const.EMPTY_CODE);
        PageData data = new PageData();
        Page page = new Page();
        //用于作为返回数据的二级节点
        List<PageData> contentlist = new ArrayList<PageData>();
        PageData qureyData = new PageData();
        //添加分页信息
        Object pageSize = reqMap.get("pageSize");
        Object pageNum = reqMap.get("pageNumber");
        String pageSizeStr;
        if (null != pageSize) {
            pageSizeStr = pageSize.toString();
        } else {
            pageSizeStr = Const.PAGE;
        }
        String pageNumStr;
        if (null != pageNum) {
            pageNumStr = pageNum.toString();
        } else {
            pageNumStr = "1";
        }
        qureyData = getPage(qureyData, pageSizeStr, pageNumStr);
        Object status = reqMap.get("status");
        if (null != status) {
            qureyData.put("status", status.toString());
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
        //最终赋值
        page.setPd(qureyData);
        logger.info("查询问题回复列表信息开始");
        logger.info("查询条件===" + JSON.toJSONString(page));
        try {
            Object id = reqMap.get("id");
            if (StringUtil.isEmpty(id)) {
                qureyData.put("id", aesDecrypt(id.toString(), Const.ALLENCRYPTCODE));
            }
            //问题id
            Object questionId = reqMap.get("questionid");
            if (null != questionId) {
                qureyData.put("questionId", questionId.toString());
            }

            /*查询问题汇总信息表*/
            List<PageData> classTypeList0 = (List<PageData>) dao.findForList("HelpQuestionReplyInformationMapper.selectByexamplePage", page);
            if (classTypeList0 != null && classTypeList0.size() != 0) {
                for (int i = 0; i < classTypeList0.size(); i++) {
                    data = new PageData();
                    HashMap tempmap = (HashMap) classTypeList0.get(i);
                    data.put("id", tempmap.get("ID") == null ? "" : aesEncrypt((String) tempmap.get("ID"), Const.ALLENCRYPTCODE));
                    data.put("user_id", tempmap.get("USER_ID") == null ? "" : aesEncrypt((String) tempmap.get("USER_ID"), Const.ALLENCRYPTCODE));
                    data.put("question_content", tempmap.get("QUESTION_CONTENT") == null ? "" : (String) tempmap.get("QUESTION_CONTENT"));
                    data.put("question_status", tempmap.get("QUESTION_STATUS") == null ? "" : (String) tempmap.get("QUESTION_STATUS"));
                    String contentShort = tempmap.get("QUESTION_CONTENT") == null ? "" : (String) tempmap.get("QUESTION_CONTENT");
                    if (contentShort.length() >= Const.CONTENTSHORT_NUM) {
                        data.put("question_content_short", contentShort.substring(0, Const.CONTENTSHORT_NUM) + "...");
                    } else {
                        data.put("question_content_short", contentShort);
                    }
                    data.put("question_id", tempmap.get("QUESTION_ID") == null ? "" : aesEncrypt((String) tempmap.get("QUESTION_ID"), Const.ALLENCRYPTCODE));
                    data.put("question_keywords", tempmap.get("QUESTION_KEYWORDS") == null ? "" : (String) tempmap.get("QUESTION_KEYWORDS"));
                    data.put("reply_status", tempmap.get("STATUS") == null ? "" : (String) tempmap.get("STATUS"));
                    data.put("question_type_id", tempmap.get("QUESTION_TYPE_ID") == null ? "" : (String) tempmap.get("QUESTION_TYPE_ID"));
                    data.put("question_type_name", tempmap.get("QUESTION_TYPE_NAME") == null ? "" : (String) tempmap.get("QUESTION_TYPE_NAME"));
                    data.put("reply_content", tempmap.get("REPLY_CONTENT") == null ? "" : (String) tempmap.get("REPLY_CONTENT"));
                    String replyContentShort = tempmap.get("REPLY_CONTENT") == null ? "" : (String) tempmap.get("REPLY_CONTENT");
                    if (replyContentShort.length() >= Const.CONTENTSHORT_NUM) {
                        data.put("reply_content_short", replyContentShort.substring(0, Const.CONTENTSHORT_NUM) + "...");
                    } else {
                        data.put("reply_content_short", replyContentShort);
                    }
                    data.put("ip", tempmap.get("IP") == null ? "" : (String) tempmap.get("IP"));
                    data.put("creation_dt", tempmap.get("CREATION_DT") == null ? "" : (String) tempmap.get("CREATION_DT"));
                    data.put("creation_tm", tempmap.get("CREATION_TM") == null ? "" : (String) tempmap.get("CREATION_TM"));
                    data.put("update_tm", tempmap.get("UPDATE_TM") == null ? "" : (String) tempmap.get("UPDATE_TM"));
                    data.put("tm_smp", tempmap.get("TM_SMP") == null ? "" : (String) tempmap.get("TM_SMP"));
                    contentlist.add(data);
                }
                map.setCode(Const.SUCCESS_CODE);
                String newUserId = null;
                Object useridObj = reqMap.get("userid");
                if (StringUtil.isEmpty(useridObj)) {
                    newUserId = aesDecrypt(useridObj.toString(), Const.ALLENCRYPTCODE);
                } else {
                    newUserId = aesDecrypt((String) request.getSession().getAttribute("sessionUserId"), Const.ALLENCRYPTCODE);
                }
                insertSysOperatelog(newUserId, Const.QUERY_QUESTION_REPLY_LIST, logger, request);
                map.setContentlist(contentlist);

                HashMap totalMap = new HashMap();
                totalMap.put("total", "0");
                List<PageData> totalList = (List<PageData>) dao.findForList("HelpQuestionReplyInformationMapper.selectTotal", page);
                if (totalList != null && totalList.size() != 0) {
                    totalMap.put("total", String.valueOf(totalList.size()));
                }


                String totalMapJson = JSON.toJSONString(totalMap).replaceAll("\\{", "").replaceAll("}", ",");
                // 输出返回结果(不走最终的统一输出结果的原因是，这里的返回报文需要重新拼接一下，把总页数拼接到前边方便用户的使用)
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                //拼接总页数便于用户使用,把节点名称换成rows支持bootstrap
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
            logger.info("queryQuestion---returnStatus---1---");
        }
        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        logger.info("queryQuestion---jsonObject.toString()---" + jsonReturn + "---");
        return jsonReturn;
    }


    /**
     * Title: 查询问题手册信息列表
     *
     * @author wangyanlai
     * @version 2019年3月1日 上午8:31:56
     */
    @Override
    public String queryBooks(Map<String, Object> reqMap, Logger logger, HttpServletRequest request) {
        ReturnData map = new ReturnData();
        map.setCode(Const.EMPTY_CODE);
        PageData data = new PageData();
        Page page = new Page();
        //用于作为返回数据的二级节点
        List<PageData> contentlist = new ArrayList<PageData>();
        PageData qureyData = new PageData();
        //添加分页信息
        Object pageSize = reqMap.get("pageSize");
        Object pageNum = reqMap.get("pageNumber");
        String pageSizeStr;
        if (null != pageSize) {
            pageSizeStr = pageSize.toString();
        } else {
            pageSizeStr = Const.PAGE;
        }
        String pageNumStr;
        if (null != pageNum) {
            pageNumStr = pageNum.toString();
        } else {
            pageNumStr = "1";
        }
        qureyData = getPage(qureyData, pageSizeStr, pageNumStr);
        Object status = reqMap.get("status");
        if (null != status) {
            qureyData.put("status", status.toString());
        }

        //注意接口前文已经做过必输项校验，所以这里直接做判断
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
        //最终赋值
        page.setPd(qureyData);
        logger.info("查询入门手册列表信息开始");
        logger.info("查询条件===" + JSON.toJSONString(page));
        try {
            Object id = reqMap.get("id");
            if (null != id) {
                qureyData.put("id", DESUtil.aesDecrypt(id.toString(),
                        Const.ALLENCRYPTCODE));
            }

            //添加入门id
            Object preliminaryId = reqMap.get("preliminaryid");
            if (null != preliminaryId) {
                qureyData.put("preliminaryId", DESUtil.aesDecrypt(preliminaryId.toString(),
                        Const.ALLENCRYPTCODE));
            }
            /*查询入门手册列表信息开始*/
            List<PageData> classTypeList0 = (List<PageData>) dao.findForList("ManualPreliminaryRelationMapper.selectByexamplePage", page);
            if (classTypeList0 != null && classTypeList0.size() != 0) {
                for (int i = 0; i < classTypeList0.size(); i++) {
                    data = new PageData();
                    HashMap tempmap = (HashMap) classTypeList0.get(i);
                    //preliminary(入门)；manual(手册)
                    data.put("preliminary_name", tempmap.get("PRELIMINARY_NAME") == null ? "" : (String) tempmap.get("PRELIMINARY_NAME"));
                    data.put("id", tempmap.get("ID") == null ? "" : aesEncrypt((String) tempmap.get("ID"), Const.ALLENCRYPTCODE));
                    data.put("preliminary_id", tempmap.get("PRELIMINARY_ID") == null ? "" : aesEncrypt((String) tempmap.get("PRELIMINARY_ID"), Const.ALLENCRYPTCODE));
                    data.put("name", tempmap.get("REMARK1") == null ? "" : (String) tempmap.get("REMARK1"));
                    data.put("manual_path", tempmap.get("MANUAL_PATH") == null ? "" : (String) tempmap.get("MANUAL_PATH"));
                    data.put("manual_type", tempmap.get("MANUAL_TYPE") == null ? "" : (String) tempmap.get("MANUAL_TYPE"));
                    data.put("manual_size", tempmap.get("MANUAL_SIZE") == null ? "" : (String) tempmap.get("MANUAL_SIZE"));
                    data.put("status", tempmap.get("STATUS") == null ? "" : (String) tempmap.get("STATUS"));
                    data.put("ip", tempmap.get("IP") == null ? "" : (String) tempmap.get("IP"));
                    data.put("creation_dt", tempmap.get("CREATION_DT") == null ? "" : (String) tempmap.get("CREATION_DT"));
                    data.put("creation_tm", tempmap.get("CREATION_TM") == null ? "" : (String) tempmap.get("CREATION_TM"));
                    data.put("update_tm", tempmap.get("UPDATE_TM") == null ? "" : (String) tempmap.get("UPDATE_TM"));
                    data.put("tm_smp", tempmap.get("TM_SMP") == null ? "" : (String) tempmap.get("TM_SMP"));
                    contentlist.add(data);
                }
                map.setCode(Const.SUCCESS_CODE);
                String newUserId = null;
                Object useridObj = reqMap.get("userid");
                if (StringUtil.isEmpty(useridObj)) {
                    newUserId = aesDecrypt(useridObj.toString(), Const.ALLENCRYPTCODE);
                } else {
                    newUserId = aesDecrypt((String) request.getSession().getAttribute("sessionUserId"), Const.ALLENCRYPTCODE);
                }
                insertSysOperatelog(newUserId, Const.QUERY_BOOKS_LIST, logger, request);
                map.setContentlist(contentlist);

                HashMap totalMap = new HashMap();
                totalMap.put("total", "0");
                List<PageData> totalList = (List<PageData>) dao.findForList("ManualPreliminaryRelationMapper.selectTotal", page);
                if (totalList != null && totalList.size() != 0) {
                    totalMap.put("total", String.valueOf(totalList.size()));
                }

                String totalMapJson = JSON.toJSONString(totalMap).replaceAll("\\{", "").replaceAll("}", ",");
                // 输出返回结果(不走最终的统一输出结果的原因是，这里的返回报文需要重新拼接一下，把总页数拼接到前边方便用户的使用)
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                //拼接总页数便于用户使用,把节点名称换成rows支持bootstrap
                jsonReturn = jsonReturn.replaceAll("\"code", totalMapJson + "\"code");
                jsonReturn = jsonReturn.replaceAll("contentlist", "rows");
                logger.info("queryBooks---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            } else {
                map.setCode(Const.NODATA);
                logger.info("queryBooks---jsonObject.toString()---" + 1 + "---");
            }
        } catch (Exception e) {
            map.setCode(Const.FAILURE_CODE);
            logger.info("queryQuestion---returnStatus---1---");
        }
        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        logger.info("queryBooks---jsonObject.toString()---" + jsonReturn + "---");
        return jsonReturn;
    }

    /**
     * Title: 查询问题视频信息列表
     *
     * @author wangyanlai
     * @version 2019年2月22日 上午8:31:56
     */
    @Override
    public String queryVideos(Map<String, Object> reqMap, Logger logger, HttpServletRequest request) {
        ReturnData map = new ReturnData();
        map.setCode(Const.EMPTY_CODE);
        PageData data = new PageData();
        Page page = new Page();
        //用于作为返回数据的二级节点
        List<PageData> contentlist = new ArrayList<PageData>();
        Object qryType = reqMap.get("qrytype");
        PageData qureyData = new PageData();
        //添加分页信息
        Object pageSize = reqMap.get("pageSize");
        Object pageNum = reqMap.get("pageNumber");
        String pageSizeStr;
        if (null != pageSize) {
            pageSizeStr = pageSize.toString();
        } else {
            pageSizeStr = Const.PAGE;
        }
        String pageNumStr;
        if (null != pageNum) {
            pageNumStr = pageNum.toString();
        } else {
            pageNumStr = "1";
        }
        qureyData = getPage(qureyData, pageSizeStr, pageNumStr);
        Object status = reqMap.get("status");
        if (null != status) {
            qureyData.put("status", status.toString());
        }

        Object ifRelease = reqMap.get("if_release");
        if (null != ifRelease) {
            qureyData.put("ifRelease", ifRelease.toString());
        }
        //注意接口前文已经做过必输项校验，所以这里直接做判断
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
        //最终赋值
        page.setPd(qureyData);
        logger.info("查询入门视频列表信息开始");
        try {
            Object id = reqMap.get("id");
            if (null != id) {
                qureyData.put("id", aesDecrypt(id.toString(), Const.ALLENCRYPTCODE));
            }
            Object userId = reqMap.get("userid");
            if (null != userId && !Const.SPECIAL_USERID.equals(userId)) {
                qureyData.put("userId", aesDecrypt(userId.toString(), Const.ALLENCRYPTCODE));
            }
            //添加入门id
            Object preliminaryId = reqMap.get("preliminaryid");
            if (null != preliminaryId) {
                qureyData.put("preliminaryId", aesDecrypt(preliminaryId.toString(), Const.ALLENCRYPTCODE));
            }
            logger.info("查询条件===" + JSON.toJSONString(page));
            /*查询问题汇总信息表*/
            List<PageData> classTypeList0 = (List<PageData>) dao.findForList("VideoPreliminaryRelationMapper.selectByexamplePage", page);
            if (classTypeList0 != null && classTypeList0.size() != 0) {
                for (int i = 0; i < classTypeList0.size(); i++) {
                    data = new PageData();
                    HashMap tempmap = (HashMap) classTypeList0.get(i);
                    //preliminary(入门)；videos(视频)
                    data.put("preliminary_name", tempmap.get("PRELIMINARY_NAME") == null ? "" : (String) tempmap.get("PRELIMINARY_NAME"));
                    data.put("id", tempmap.get("ID") == null ? "" : aesEncrypt((String) tempmap.get("ID"), Const.ALLENCRYPTCODE));
                    data.put("preliminary_id", tempmap.get("PRELIMINARY_ID") == null ? "" : aesEncrypt((String) tempmap.get("PRELIMINARY_ID"), Const.ALLENCRYPTCODE));
                    data.put("video_path", tempmap.get("VIDEO_PATH") == null ? "" : (String) tempmap.get("VIDEO_PATH"));
                    data.put("name", tempmap.get("REMARK1") == null ? "" : (String) tempmap.get("REMARK1"));
                    data.put("video_type", tempmap.get("VIDEO_TYPE") == null ? "" : (String) tempmap.get("VIDEO_TYPE"));
                    data.put("video_size", tempmap.get("VIDEO_SIZE") == null ? "" : (String) tempmap.get("VIDEO_SIZE"));
                    data.put("status", tempmap.get("STATUS") == null ? "" : (String) tempmap.get("STATUS"));
                    data.put("ip", tempmap.get("IP") == null ? "" : (String) tempmap.get("IP"));
                    data.put("creation_dt", tempmap.get("CREATION_DT") == null ? "" : (String) tempmap.get("CREATION_DT"));
                    data.put("creation_tm", tempmap.get("CREATION_TM") == null ? "" : (String) tempmap.get("CREATION_TM"));
                    data.put("update_tm", tempmap.get("UPDATE_TM") == null ? "" : (String) tempmap.get("UPDATE_TM"));
                    data.put("tm_smp", tempmap.get("TM_SMP") == null ? "" : (String) tempmap.get("TM_SMP"));
                    contentlist.add(data);
                }
                map.setCode(Const.SUCCESS_CODE);
                String newUserId = null;
                Object useridObj = reqMap.get("userid");
                if (StringUtil.isEmpty(useridObj)) {
                    newUserId = aesDecrypt(useridObj.toString(), Const.ALLENCRYPTCODE);
                } else {
                    newUserId = aesDecrypt((String) request.getSession().getAttribute("sessionUserId"), Const.ALLENCRYPTCODE);
                }
                insertSysOperatelog(newUserId, Const.QUERY_VIDEOS_LIST, logger, request);
                map.setContentlist(contentlist);

                HashMap totalMap = new HashMap();
                totalMap.put("total", "0");
                List<PageData> totalList = (List<PageData>) dao.findForList("VideoPreliminaryRelationMapper.selectTotal", page);
                if (totalList != null && totalList.size() != 0) {
                    totalMap.put("total", String.valueOf(totalList.size()));
                }

                String totalMapJson = JSON.toJSONString(totalMap).replaceAll("\\{", "").replaceAll("}", ",");
                // 输出返回结果(不走最终的统一输出结果的原因是，这里的返回报文需要重新拼接一下，把总页数拼接到前边方便用户的使用)
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                //拼接总页数便于用户使用,把节点名称换成rows支持bootstrap
                jsonReturn = jsonReturn.replaceAll("\"code", totalMapJson + "\"code");
                jsonReturn = jsonReturn.replaceAll("contentlist", "rows");
                logger.info("queryVideos---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            } else {
                map.setCode(Const.NODATA);
                logger.info("queryVideos---jsonObject.toString()---" + 1 + "---");
            }
        } catch (Exception e) {
            map.setCode(Const.FAILURE_CODE);
            logger.info("queryVideos---returnStatus---1---");
        }
        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        logger.info("queryVideos---jsonObject.toString()---" + jsonReturn + "---");
        return jsonReturn;
    }

    /**
     * Title: APPServiceImpl Description:
     * <p>
     * 查询问题相关列表或其他信息
     *
     * @author wangyanlai
     * @version 2019年1月26日 上午8:31:56
     */
    @Override
    public String qryQuestion(Map<String, Object> reqMap, Logger logger, HttpServletRequest request) {
        ReturnData map = new ReturnData();
        map.setCode(Const.EMPTY_CODE);
        PageData data = new PageData();
        Page page = new Page();
        //用于作为返回数据的二级节点
        List<PageData> contentlist = new ArrayList<PageData>();
        Object qryType = reqMap.get("qrytype");
        PageData qureyData = new PageData();
        //添加分页信息
        Object pageSize = reqMap.get("pageSize");
        Object pageNum = reqMap.get("pageNumber");
        String pageSizeStr;
        if (null != pageSize) {
            pageSizeStr = pageSize.toString();
        } else {
            pageSizeStr = Const.PAGE;
        }
        String pageNumStr;
        if (null != pageNum) {
            pageNumStr = pageNum.toString();
        } else {
            pageNumStr = "1";
        }
        qureyData = getPage(qureyData, pageSizeStr, pageNumStr);
        Object status = reqMap.get("status");
        if (null != status) {
            qureyData.put("status", status.toString());
        }
        Object id = reqMap.get("id");
        if (null != id) {
            qureyData.put("id", id.toString());
        }
        if (org.springframework.util.StringUtils.isEmpty(qryType)) {
            qureyData.put("qryType", Const.QUERY_QUESTION_LIST);
        } else {
            qureyData.put("qryType", decryptBasedDes(qryType.toString()));
        }
        //注意接口前文已经做过必输项校验，所以这里直接做判断
        if (Const.QUERY_QUESTION_LIST.equals(decryptBasedDes(qryType.toString()))) {
            //添加类别id
            Object questionTypeId = reqMap.get("questiontypeid");
            if (null != questionTypeId) {
                qureyData.put("questionTypeId", questionTypeId.toString());
            }

            Object ifCommon = reqMap.get("if_common");
            if (null != ifCommon) {
                qureyData.put("ifCommon", ifCommon.toString());
            }
            Object ifRelease = reqMap.get("if_release");
            if (null != ifRelease) {
                qureyData.put("ifRelease", ifRelease.toString());
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
            //最终赋值
            page.setPd(qureyData);
            logger.info("查询系统问题列表信息开始");
            logger.info("查询条件===" + JSON.toJSONString(page));
            try {
                Object userId = reqMap.get("userid");
                if (null != userId && !Const.SPECIAL_USERID.equals(userId)) {
                    qureyData.put("userId", aesDecrypt(userId.toString(), Const.ALLENCRYPTCODE));
                }
                /*查询问题汇总信息表*/
                List<PageData> classTypeList0 = (List<PageData>) dao.findForList("HelpQuestionInformationMapper.selectByexamplePage", page);
                if (classTypeList0 != null && classTypeList0.size() != 0) {
                    for (int i = 0; i < classTypeList0.size(); i++) {
                        data = new PageData();
                        HashMap tempmap = (HashMap) classTypeList0.get(i);
                        data.put("id", tempmap.get("ID") == null ? "" : aesEncrypt((String) tempmap.get("ID"), Const.ALLENCRYPTCODE));
                        data.put("user_id", tempmap.get("USER_ID") == null ? "" : aesEncrypt((String) tempmap.get("USER_ID"), Const.ALLENCRYPTCODE));
                        data.put("question_content", tempmap.get("QUESTION_CONTENT") == null ? "" : (String) tempmap.get("QUESTION_CONTENT"));
                        String contentShort = tempmap.get("QUESTION_CONTENT") == null ? "" : (String) tempmap.get("QUESTION_CONTENT");
                        if (contentShort.length() >= Const.CONTENTSHORT_NUM) {
                            data.put("question_content_short", contentShort.substring(0, Const.CONTENTSHORT_NUM) + "...");
                        } else {
                            data.put("question_content_short", contentShort);
                        }
                        data.put("question_content", tempmap.get("QUESTION_CONTENT") == null ? "" : (String) tempmap.get("QUESTION_CONTENT"));
                        data.put("question_keywords", tempmap.get("QUESTION_KEYWORDS") == null ? "" : (String) tempmap.get("QUESTION_KEYWORDS"));
                        data.put("status", tempmap.get("STATUS") == null ? "" : (String) tempmap.get("STATUS"));
                        data.put("question_type_id", tempmap.get("QUESTION_TYPE_ID") == null ? "" : (String) tempmap.get("QUESTION_TYPE_ID"));
                        data.put("question_type_name", tempmap.get("QUESTION_TYPE_NAME") == null ? "" : (String) tempmap.get("QUESTION_TYPE_NAME"));
                        data.put("if_release", tempmap.get("IF_RELEASE") == null ? "" : (String) tempmap.get("IF_RELEASE"));
                        data.put("if_common", tempmap.get("IF_COMMON") == null ? "" : (String) tempmap.get("IF_COMMON"));
                        data.put("visual_range", tempmap.get("VISUAL_RANGE") == null ? "" : (String) tempmap.get("VISUAL_RANGE"));
                        data.put("click_number", tempmap.get("CLICK_NUMBER") == null ? "" : (String) tempmap.get("CLICK_NUMBER"));
                        data.put("number_points", tempmap.get("NUMBER_POINTS") == null ? "" : (String) tempmap.get("NUMBER_POINTS"));
                        data.put("number_bricks", tempmap.get("NUMBER_BRICKS") == null ? "" : (String) tempmap.get("NUMBER_BRICKS"));
                        data.put("creation_per", tempmap.get("CREATION_PER") == null ? "" : aesEncrypt((String) tempmap.get("CREATION_PER"), Const.ALLENCRYPTCODE));
                        data.put("ip", tempmap.get("IP") == null ? "" : (String) tempmap.get("IP"));
                        data.put("creation_dt", tempmap.get("CREATION_DT") == null ? "" : (String) tempmap.get("CREATION_DT"));
                        data.put("creation_tm", tempmap.get("CREATION_TM") == null ? "" : (String) tempmap.get("CREATION_TM"));
                        data.put("update_tm", tempmap.get("UPDATE_TM") == null ? "" : (String) tempmap.get("UPDATE_TM"));
                        data.put("tm_smp", tempmap.get("TM_SMP") == null ? "" : (String) tempmap.get("TM_SMP"));
                        contentlist.add(data);
                    }
                    HashMap totalMap = new HashMap();
                    totalMap.put("total", "0");
                    List<PageData> totalList = (List<PageData>) dao.findForList("HelpQuestionInformationMapper.selectTotal", page);
                    if (totalList != null && totalList.size() != 0) {
                        totalMap.put("total", String.valueOf(totalList.size()));
                    }
                    map.setCode(Const.SUCCESS_CODE);
                    String newUserId = null;
                    Object useridObj = reqMap.get("userid");
                    if (StringUtil.isEmpty(useridObj)) {
                        newUserId = aesDecrypt(useridObj.toString(), Const.ALLENCRYPTCODE);
                    } else {
                        newUserId = aesDecrypt((String) request.getSession().getAttribute("sessionUserId"), Const.ALLENCRYPTCODE);
                    }
                    insertSysOperatelog(newUserId, Const.QUERY_QUESTION_LIST, logger, request);
                    map.setContentlist(contentlist);
                    String totalMapJson = JSON.toJSONString(totalMap).replaceAll("\\{", "").replaceAll("}", ",");
                    // 输出返回结果(不走最终的统一输出结果的原因是，这里的返回报文需要重新拼接一下，把总页数拼接到前边方便用户的使用)
                    String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                    //拼接总页数便于用户使用,把节点名称换成rows支持bootstrap
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
                logger.info("queryQuestion---returnStatus---1---");
            }
        }
        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        logger.info("queryQuestion---jsonObject.toString()---" + jsonReturn + "---");
        return jsonReturn;
    }


    /**
     * Title: 查询入门基本信息列表
     *
     * @author wangyanlai
     * @version 2019年2月22日 上午8:31:56
     */
    @Override
    public String queryPreliminary(Map<String, Object> reqMap, Logger logger, HttpServletRequest request) {
        ReturnData map = new ReturnData();
        map.setCode(Const.EMPTY_CODE);
        PageData data = new PageData();
        Page page = new Page();
        //用于作为返回数据的二级节点
        List<PageData> contentlist = new ArrayList<PageData>();
        PageData qureyData = new PageData();
        //添加分页信息
        Object pageSize = reqMap.get("pageSize");
        Object pageNum = reqMap.get("pageNumber");
        String pageSizeStr;
        if (null != pageSize) {
            pageSizeStr = pageSize.toString();
        } else {
            pageSizeStr = Const.PAGE;
        }
        String pageNumStr;
        if (null != pageNum) {
            pageNumStr = pageNum.toString();
        } else {
            pageNumStr = "1";
        }
        qureyData = getPage(qureyData, pageSizeStr, pageNumStr);
        Object status = reqMap.get("status");
        if (null != status) {
            qureyData.put("status", status.toString());
        }


        //注意接口前文已经做过必输项校验，所以这里直接做判断
        //添加类别id

        Object ifRelease = reqMap.get("if_release");
        if (null != ifRelease) {
            qureyData.put("ifRelease", ifRelease.toString());
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
        //最终赋值
        page.setPd(qureyData);
        logger.info("查询入门汇总列表信息开始");
        logger.info("查询条件===" + JSON.toJSONString(page));
        try {
//添加入门id
            Object id = reqMap.get("id");
            if (null != id) {
                qureyData.put("id", aesDecrypt(id.toString(), Const.ALLENCRYPTCODE));
            }
            /*查询入门信息汇总信息表*/
            List<PageData> classTypeList0 = (List<PageData>) dao.findForList("PreliminaryInformationMapper.selectByexamplePage", page);
            if (classTypeList0 != null && classTypeList0.size() != 0) {
                for (int i = 0; i < classTypeList0.size(); i++) {
                    data = new PageData();
                    HashMap tempmap = (HashMap) classTypeList0.get(i);
                    //preliminary(入门)；
                    data.put("preliminary_name", tempmap.get("PRELIMINARY_NAME") == null ? "" : (String) tempmap.get("PRELIMINARY_NAME"));
                    data.put("id", tempmap.get("ID") == null ? "" : aesEncrypt((String) tempmap.get("ID"), Const.ALLENCRYPTCODE));
                    data.put("video_num", tempmap.get("VIDEO_NUM") == null ? "" : (String) tempmap.get("VIDEO_NUM"));
                    data.put("manual_num", tempmap.get("MANUAL_NUM") == null ? "" : (String) tempmap.get("MANUAL_NUM"));
                    data.put("status", tempmap.get("STATUS") == null ? "" : (String) tempmap.get("STATUS"));
                    data.put("ip", tempmap.get("IP") == null ? "" : (String) tempmap.get("IP"));
                    data.put("file_path", Const.FILE_PATH);
                    data.put("remark1", tempmap.get("REMARK1") == null ? "" : (String) tempmap.get("REMARK1"));
                    data.put("creation_per", tempmap.get("CREATION_PER") == null ? "" : aesEncrypt((String) tempmap.get("CREATION_PER"), Const.ALLENCRYPTCODE));
                    data.put("creation_dt", tempmap.get("CREATION_DT") == null ? "" : (String) tempmap.get("CREATION_DT"));
                    data.put("creation_tm", tempmap.get("CREATION_TM") == null ? "" : (String) tempmap.get("CREATION_TM"));
                    data.put("update_tm", tempmap.get("UPDATE_TM") == null ? "" : (String) tempmap.get("UPDATE_TM"));
                    data.put("tm_smp", tempmap.get("TM_SMP") == null ? "" : (String) tempmap.get("TM_SMP"));
                    contentlist.add(data);
                }
                map.setCode(Const.SUCCESS_CODE);
                String newUserId = null;
                Object useridObj = reqMap.get("userid");
                if (StringUtil.isEmpty(useridObj)) {
                    newUserId = aesDecrypt(useridObj.toString(), Const.ALLENCRYPTCODE);
                } else {
                    newUserId = aesDecrypt((String) request.getSession().getAttribute("sessionUserId"), Const.ALLENCRYPTCODE);
                }
                insertSysOperatelog(newUserId, Const.QUERY_PRELIMINARY, logger, request);
                map.setContentlist(contentlist);

                HashMap totalMap = new HashMap();
                totalMap.put("total", "0");
                List<PageData> totalList = (List<PageData>) dao.findForList("PreliminaryInformationMapper.selectTotal", page);
                if (totalList != null && totalList.size() != 0) {
                    totalMap.put("total", String.valueOf(totalList.size()));
                }

                String totalMapJson = JSON.toJSONString(totalMap).replaceAll("\\{", "").replaceAll("}", ",");
                // 输出返回结果(不走最终的统一输出结果的原因是，这里的返回报文需要重新拼接一下，把总页数拼接到前边方便用户的使用)
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                //拼接总页数便于用户使用,把节点名称换成rows支持bootstrap
                jsonReturn = jsonReturn.replaceAll("\"code", totalMapJson + "\"code");
                jsonReturn = jsonReturn.replaceAll("contentlist", "rows");
                logger.info("queryPreliminary---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            } else {
                map.setCode(Const.NODATA);
                logger.info("queryPreliminary---jsonObject.toString()---" + 1 + "---");
            }
        } catch (Exception e) {
            map.setCode(Const.FAILURE_CODE);
            logger.info("queryPreliminary---returnStatus---1---");
        }
        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        logger.info("queryPreliminary---jsonObject.toString()---" + jsonReturn + "---");
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
