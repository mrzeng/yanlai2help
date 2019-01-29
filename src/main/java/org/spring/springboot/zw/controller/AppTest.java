package org.spring.springboot.zw.controller;

import base.zw.controller.BaseController;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.spring.springboot.dao.DaoSupport;
import org.spring.springboot.domain.Page;
import org.spring.springboot.domain.PageData;
import org.spring.springboot.domain.PerIntegrationBasis;
import org.spring.springboot.domain.ReturnData;
import org.spring.springboot.service.APPService;
import org.spring.springboot.service.IntegrationQryService;
import org.spring.springboot.service.IntegrationUpdService;
import org.spring.springboot.service.UserService;
import org.spring.springboot.zw.util.Const;
import org.spring.springboot.zw.util.DESUtil;
import org.spring.springboot.zw.util.DateUtil;
import org.spring.springboot.zw.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 接口 Controller 实现 Restful HTTP 服务
 * <p>
 * Created by zhaowei on 20180425.
 */

/**
 * Title: AppController Description:
 *
 * @author zhaowei
 * @version 2018年4月30日 下午4:53:32
 */
@RestController
@RequestMapping(value = "/test")
public class AppTest extends BaseController {
    @Resource(name = "daoSupport")
    private DaoSupport dao;
    @Autowired
    private APPService appService;
    @Autowired
    private UserService userService;
    @Autowired
    private IntegrationQryService integrationQryService;
    @Autowired
    private IntegrationUpdService integrationUpdService;
    @Autowired
    private DataSourceTransactionManager transactionManager;

    /**
     * 查询年份或者课程分类信息
     *
     * @param reqMap
     * @return
     * @author zhaowei
     * @version 2018年4月30日 上午10:09:20
     */
    @Transactional
    @RequestMapping(value = "/testOne", method = RequestMethod.POST)
    public String testOne(@RequestBody Map<String, Object> reqMap) {
        //初始化事务参数配置
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //隔离级别为 PROPAGATION_NESTED--如果当前存在事务，则在嵌套事务内执行。
        //如果当前没有事务，则进行与PROPAGATION_REQUIRED(新创事务)类似的操作。
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
        TransactionStatus status = transactionManager.getTransaction(def);
        ReturnData map = new ReturnData();
        PageData pd = new PageData();
        String id = null;
        PerIntegrationBasis perIntegrationBasis = new PerIntegrationBasis();

        for (int idd = 1280000; idd < 2560000; idd++) {
            perIntegrationBasis = new PerIntegrationBasis();
            perIntegrationBasis.setUpdateTm(DateUtil.getTime());
            perIntegrationBasis.setLastTimeScore(DateUtil.getTime());
            perIntegrationBasis.setTmSmp(DateUtil.getTime());
            perIntegrationBasis.setCreationTm(DateUtil.getTime());
            perIntegrationBasis.setId(String.valueOf(idd));
            perIntegrationBasis.setOriginalScore("50");
            perIntegrationBasis.setYesterdayScore("11");
            perIntegrationBasis.setScore("999");
            List<PerIntegrationBasis> aaaaa = null;
            try {
                dao.update("PerIntegrationBasisMapper.insertOne", perIntegrationBasis);
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("插入完成");
        }
        transactionManager.commit(status);

        // 输出返回结果
        String jsonReturn = JSON.toJSONString(map);
        return jsonReturn;
    }

    @RequestMapping(value = "/testTwo", method = RequestMethod.POST)
    public String testTwo(@RequestBody Map<String, Object> reqMap) {
        HttpServletRequest request = getRequest();
        Object o = request.getSession().getAttribute("springboot");
        logger.info("spring boot===" + o);
        if (o == null) {
            logger.info("spring boot===" + o);
            o = "spring boot 牛逼了!!!有端口" + request.getLocalPort() + "生成";
            request.getSession().setAttribute("springboot", o);
            logger.info("spring boot===" + o);
            logger.info("spring boot getSession getId===" + request.getSession().getId());
            request.getSession().setAttribute("userId", "111");
            logger.info("spring boot getSession getuserId===" + request.getSession().getAttribute("userId"));
        }
        ReturnData map = new ReturnData();
        String jsonReturn = JSON.toJSONString(map);
        return jsonReturn;
    }
}