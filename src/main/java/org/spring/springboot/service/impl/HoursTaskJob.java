package org.spring.springboot.service.impl;

import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import base.zw.controller.BaseController;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.spring.springboot.dao.DaoSupport;
import org.spring.springboot.domain.*;
import org.spring.springboot.zw.util.Const;
import org.spring.springboot.zw.util.DateUtil;
import org.spring.springboot.zw.util.StringUtil;
import org.spring.springboot.zw.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.concurrent.*;

import static org.apache.commons.lang3.StringUtils.INDEX_NOT_FOUND;
import static org.apache.commons.lang3.StringUtils.isNumeric;
import static org.spring.springboot.zw.util.Const.PAGE;
import static org.spring.springboot.zw.util.DESUtil.aesEncrypt;
import static org.spring.springboot.zw.util.DESUtil.decryptBasedDes;
import static org.spring.springboot.zw.util.DESUtil.encryptBasedDes;

/**
 * Title: BaseController
 * Description:
 *
 * @author 王燕来
 * @version 2018年12月26日 上午11:31:41
 */

@Component
@Configurable
@EnableScheduling
//@PropertySource(value = "classpath:application.properties")
public class HoursTaskJob extends BaseController {
    @Resource(name = "daoSupport")
    private DaoSupport dao;
    @Autowired
    private DataSourceTransactionManager transactionManager;
    int userNum = 0;
    int nowPage = 0;
    long begintime = System.currentTimeMillis();
    private static Jedis jedis;

    @Scheduled(cron = "${hoursEndTime}")
    public void reportCurrentByCron() throws Exception {
        begintime = System.currentTimeMillis();
        String nowDate = DateUtil.getDay();
        logger.info("系统后台自动获取公共问题开始——Scheduling Tasks Examples By Cron: The time is now " + DateUtil.getTime());
        PageData qureyData = new PageData();
        Page page = new Page();
        String pageSize = Const.MAX_PAGESIZE; /*每次查询多少条作为一个批次进行处理*/

        //默认使用常量配置的指定天数，如果积分配置信息表已经配置了，就使用积分配置信息表里面的天数
        String qryTmBeginUpd = "";
        String qryTmEndUpd = "";
        qryTmBeginUpd = Const.EARLIEST_TIME;
        //默认最早时间1000-01-01 00:00:01
        qryTmEndUpd = Const.LATE_ARRIVAL_TIME;
        //默认最晚时间"5000-01-01 00:00:01"
        qureyData.put("qryTmBeginUpd", qryTmBeginUpd);
        qureyData.put("qryTmEndUpd", qryTmEndUpd);
        qureyData = getPage(qureyData, pageSize, String.valueOf(nowPage));
        page.setPd(qureyData);
        Jedis jedis = new Jedis(Const.REDIS_PATH); // 设置地址
        jedis.auth(Const.REDIS_PASSWD); // 设置密码
        List<String> list = jedis.lrange("commonQuestionList", 0, 1);
        if (!"0".equals(String.valueOf(list.size()))) {
            System.out.println("系统中删除redis，commonQuestionList节点数据: " + jedis.del("commonQuestionList"));
        }
        List<PageData> classTypeList0 = (List<PageData>) dao.findForList("HelpQuestionInformationMapper.selectByexampleAllIdPage", page);
        if (classTypeList0 != null && classTypeList0.size() != 0) {
            int classTypeListSize = classTypeList0.size();
            if (classTypeListSize > Const.REDIS_MAX_QUESTIONLIST) {
                classTypeListSize = Const.REDIS_MAX_QUESTIONLIST;
            }
            HashMap totalMap = new HashMap();
            totalMap.put("total", classTypeListSize);
            List<PageData> contentlist = new ArrayList<PageData>();
            PageData data = new PageData();
            ReturnData map = new ReturnData();
            for (int i = 0; i < classTypeListSize; i++) {
                HashMap tempmap = (HashMap) classTypeList0.get(i);
                data = new PageData();
                String questionTypeName = tempmap.get("QUESTION_TYPE_NAME") == null ? "" : (String) tempmap.get("QUESTION_TYPE_NAME");
                data.put("question_type_name", questionTypeName);
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
            map.setCode(Const.SUCCESS_CODE);
            map.setContentlist(contentlist);
            String totalMapJson = JSON.toJSONString(totalMap).replaceAll("\\{", "").replaceAll("}", ",");
            // 输出返回结果(不走最终的统一输出结果的原因是，这里的返回报文需要重新拼接一下，把总页数拼接到前边方便用户的使用)
            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            //拼接总页数便于用户使用
            jsonReturn = jsonReturn.replaceAll("\"code", totalMapJson + "\"code");
            jsonReturn = jsonReturn.replaceAll("contentlist", "rows");
            logger.info("updConfig---jsonObject.toString()---" + jsonReturn + "---");
            jedis.lpush("commonQuestionList", jsonReturn);
        } else {
            logger.info("没有符合条件的查询记录");
            jedis.lpush("commonQuestionList", "");
        }
        logger.info("插入个人操作日志表");
        insertSysOperatelog(logger);
        logger.info("跑批结束");

    }


    /*
     * 添加分页信息公共方法
     *
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
    public void insertSysOperatelog(Logger logger) throws Exception {
        SysOperatelog sysOperatelog = new SysOperatelog();
        sysOperatelog.setCreatetime(DateUtil.getTime());
        sysOperatelog.setIp("");
        sysOperatelog.setLogid(UuidUtil.get32UUID());
        String endDt = DateUtil.subDateOnToday("1");
        endDt = endDt.substring(0, 4) + endDt.substring(5, 7) + endDt.substring(8, 10);
        sysOperatelog.setLogname("系统针对公共问题进行批量redis存放结束。");
        sysOperatelog.setLogtype("0");
        sysOperatelog.setOperatetype("SYSTEM");
        sysOperatelog.setModuletype("INTEGRATION");
        sysOperatelog.setOperater("");
        sysOperatelog.setLoggertype("SYSTEM");
        Object updOperaLogNum = null;
        try {
            updOperaLogNum = dao.update("SysOperatelogMapper.insert", sysOperatelog);
        } catch (Exception e) {
            logger.info("插入操作日志表失败");
        }
        if (updOperaLogNum == null) {
            logger.info("插入操作日志表失败");
        } else {
            logger.info("插入操作日志表成功");
        }
    }

    /*
     * 获取登录ip
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

}
