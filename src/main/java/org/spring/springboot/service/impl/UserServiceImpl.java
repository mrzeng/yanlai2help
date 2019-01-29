/**
 * <p>Description: </p>
 * <p>Title: UserServiceImpl.java</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: cei</p>
 * <p>@author zhaowei</p>
 * <p>@version 2018年4月27日 下午6:25:34</p>
 */
package org.spring.springboot.service.impl;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.spring.springboot.dao.APPDao;
import org.spring.springboot.dao.DaoSupport;
import org.spring.springboot.domain.*;
import org.spring.springboot.service.UserService;
import org.spring.springboot.zw.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import static org.spring.springboot.zw.util.Const.PAGE;
import static org.spring.springboot.zw.util.DESUtil.aesDecrypt;
import static org.spring.springboot.zw.util.DESUtil.aesEncrypt;
import static org.spring.springboot.zw.util.DESUtil.decryptBasedDes;

/**
 * Title: UserServiceImpl
 * Description:
 *
 * @author zhaowei
 * @version 2018年4月27日 下午6:25:34
 */
//@Repository
@Service
public class UserServiceImpl implements UserService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;
    @Autowired
    private DataSourceTransactionManager transactionManager;

    /*
     * 通过id获取数据
     */
    @Override
    public PageData findByUiId(PageData pd) {
        PageData temppd = new PageData();
        try {
            temppd = (PageData) dao.findForObject("UserXMapper.findByUiId", pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temppd;
    }

    /*
     * 通过loginname获取数据
     */
    @Override
    public PageData findByUId(PageData pd) {
        PageData temppd = new PageData();
        try {
            temppd = (PageData) dao.findForObject("UserXMapper.findByUId", pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temppd;
    }

    /*
     * 登录判断
     */
    @Override
    public PageData getUserByNameAndPwd(PageData pd) {
        PageData temppd = new PageData();
        try {
            temppd = (PageData) dao.findForObject("UserXMapper.getUserInfo", pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temppd;
    }

    /*
     * 保存用户登陆信息
     */
    @Override
    public void saveULogin(PageData pd) {
        try {
            dao.save("UserXMapper.saveULogin", pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改页面--前端使用
     *
     * @throws Exception
     * @返回值：void
     * @创建时间：2018年3月20日 下午1:22:09
     */
    @Override
    public void editUserPro(PageData pd) {
        try {
            dao.update("UserXAdminMapper.editUserPro", pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 登录积分管理系统验证
     * 王燕来 2019.1。12
     * Created by wangyanlai on 20180425.
     *
     * @author wangyanlai
     * wangyanlai@cei.gov.cn
     */
    @Override
    @Transactional()
    public String loginOut(Map<String, Object> reqMap, Logger logger, HttpServletRequest request) {
        ReturnData map = new ReturnData();
        //初始化事务参数配置
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //隔离级别为 PROPAGATION_NESTED--如果当前存在事务，则在嵌套事务内执行。
        //如果当前没有事务，则进行与PROPAGATION_REQUIRED(新创事务)类似的操作。
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
        TransactionStatus transStatus = transactionManager.getTransaction(def);
        //初始化返回码为错误
        map.setCode(Const.FAILURE_CODE);
        //允许进行下面几种类型的积分信息查询
        String userId = (String) request.getSession().getAttribute("sessionUserId");
        logger.info("userId====" + userId);
        //初始化sql送值实体
        Page page = new Page();
        try {
            //登记操作记录
            insertSysOperatelog(userId, "loginOut", logger, request);
        } catch (Exception e) {
            logger.info("登出失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(transStatus);
            //json格式没有被打乱不需要格式化
            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            logger.info("loginOut---jsonObject.toString()---" + jsonReturn + "---");
            return jsonReturn;
        }
        request.getSession().removeAttribute("sessionUserId");
        request.getSession().removeAttribute("sessionUserPwd");
        // 统一输出返回结果
        HashMap totalMap = new HashMap();
        if (!Const.SUCCESS_CODE.equals(map.getCode())) {
            totalMap.put("total", "0");
        }
        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        String totalMapJson = JSON.toJSONString(totalMap).replaceAll("\\{", "").replaceAll("}", ",");
        jsonReturn = jsonReturn.replaceAll("\"code", totalMapJson + "\"code");
        logger.info("loginIn---jsonObject.toString()---" + jsonReturn + "---");
        return jsonReturn;
    }


    /**
     * 登录积分管理系统验证
     * 王燕来 2019.1。12
     * Created by wangyanlai on 20180425.
     *
     * @author wangyanlai
     * wangyanlai@cei.gov.cn
     */
    @Override
    @Transactional()
    public String loginIn(Map<String, Object> reqMap, Logger logger, HttpServletRequest request) {
        ReturnData map = new ReturnData();
        //初始化事务参数配置
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //隔离级别为 PROPAGATION_NESTED--如果当前存在事务，则在嵌套事务内执行。
        //如果当前没有事务，则进行与PROPAGATION_REQUIRED(新创事务)类似的操作。
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
        TransactionStatus transStatus = transactionManager.getTransaction(def);
        //初始化返回码为错误
        map.setCode(Const.FAILURE_CODE);
        //允许进行下面几种类型的积分信息查询
        String passWd = ((Object) reqMap.get("password")).toString();
        String userNm = ((Object) reqMap.get("username")).toString();
        //用户id用于用户操作记录登记
        String userId = "";
        //初始化sql送值实体
        Page page = new Page();
        try {
            map.setCode(Const.LOGINFAILURE_CODE);
            PageData qureyData = new PageData();
            String pageSizeStr = Const.PAGE;
            //添加分页信息
            qureyData = getPage(qureyData, pageSizeStr, "1");
            qureyData.put("passWd", passWd);
            qureyData.put("userNm", userNm);
            //添加分页信息进入储值域，用于传参
            page.setPd(qureyData);
            List<PageData> classTypeListBasis = (List<PageData>) dao.findForList("PerIntegrationUserMapper.selectByexamplePage", page);
            if (classTypeListBasis != null && classTypeListBasis.size() != 0) {
                map.setCode(Const.FAILURE_CODE);
                PerIntegrationUser perIntegrationUser = new PerIntegrationUser();
                HashMap tempmap = (HashMap) classTypeListBasis.get(0);
                userId = tempmap.get("USER_ID") == null ? "" : (String) tempmap.get("USER_ID");
                perIntegrationUser.setUserId(userId);
                perIntegrationUser.setIp(getIpAddress(request));
                perIntegrationUser.setLastLogin(DateUtil.getTime());
                Object updNum = null;
                try {
                    updNum = dao.update("PerIntegrationUserMapper.updateByPrimaryKeySelective", perIntegrationUser);
                    if (null == updNum || "0".equals(updNum.toString())) {
                        //json格式没有被打乱不需要格式化
                        logger.info("更新用户基本信息表失败");
                        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                        logger.info("queryScore---jsonObject.toString()---" + jsonReturn + "---");
                        return jsonReturn;
                    }
                } catch (Exception e) {
                    logger.info("更新用户基本信息表失败");
                    //事务回滚
                    logger.info("=====================事务回滚======================");
                    transactionManager.rollback(transStatus);
                    //json格式没有被打乱不需要格式化
                    String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                    logger.info("loginIn---jsonObject.toString()---" + jsonReturn + "---");
                    return jsonReturn;
                }
                // 输出返回结果(不走最终的统一输出结果的原因是，这里的返回报文需要重新拼接一下，把总页数拼接到前边方便用户的使用)
                map.setCode(Const.SUCCESS_CODE);
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                //拼接总页数便于用户使用
                logger.info("插入操作日志，操作记录");
                insertSysOperatelog(userId, "login", logger, request);
            } else {
                logger.info("登录失败");
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("loginIn---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
        } catch (Exception e) {
            logger.info("验证登录信息失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(transStatus);
            //json格式没有被打乱不需要格式化
            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            logger.info("loginIn---jsonObject.toString()---" + jsonReturn + "---");
            return jsonReturn;
        }
        // 统一输出返回结果
        HashMap totalMap = new HashMap();
        if (!Const.SUCCESS_CODE.equals(map.getCode())) {
            totalMap.put("total", "0");
        }
        try {
            userId = aesEncrypt(userId, Const.ALLENCRYPTCODE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getSession().setAttribute("sessionUserId", userId); //保存用户id
        request.getSession().setAttribute("sessionUserPwd", passWd); //保存密码

        //30分钟之后失效
        request.getSession().setMaxInactiveInterval(60*1*10);
        String sessionId = request.getSession().getId();
        Cookie cookie = new Cookie("JSESSIONID", sessionId);
        cookie.setPath(request.getContextPath());
        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        logger.info("userId" + userId);
        logger.info("passWd" + passWd);
        logger.info("SESSION.GEIuserId" + request.getSession().getAttribute("sessionUserId"));
        logger.info("loginIn---jsonObject.toString()---" + jsonReturn + "---");
        return jsonReturn;
    }

    /**
     * 新增积分管理系统用户信息
     * 王燕来 2019.1。12
     * Created by wangyanlai on 20180425.
     *
     * @author wangyanlai
     * wangyanlai@cei.gov.cn
     */
    @Override
    @Transactional()
    public String addUser(Map<String, Object> reqMap, Logger logger, HttpServletRequest request) {
        ReturnData map = new ReturnData();
        //初始化事务参数配置
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //隔离级别为 PROPAGATION_NESTED--如果当前存在事务，则在嵌套事务内执行。
        //如果当前没有事务，则进行与PROPAGATION_REQUIRED(新创事务)类似的操作。
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
        TransactionStatus transStatus = transactionManager.getTransaction(def);
        //初始化返回码为错误
        map.setCode(Const.FAILURE_CODE);
        //允许进行下面几种类型的积分信息查询
        Object passWd = reqMap.get("password");
        Object userNm = reqMap.get("username");
        //初始化sql送值实体
        Page page = new Page();
        try {
            map.setCode(Const.LOGINFAILURE_CODE);
            PageData qureyData = new PageData();
            String pageSizeStr = ((Object) reqMap.get("pageSize")).toString();
            //添加分页信息
            qureyData = getPage(qureyData, pageSizeStr, "1");
            qureyData.put("userNm", userNm);
            qureyData.put("passWd", passWd);
            //添加分页信息进入储值域，用于传参
            page.setPd(qureyData);
            // 查询个人积分基本信息表
            List<PageData> classTypeListBasis = (List<PageData>) dao.findForList("PerIntegrationUserMapper.selectByexamplePage", page);
            if (classTypeListBasis != null && classTypeListBasis.size() != 0) {
                map.setCode(Const.FAILURE_CODE);
                PerIntegrationUser perIntegrationUser = new PerIntegrationUser();
                HashMap tempmap = (HashMap) classTypeListBasis.get(0);
                perIntegrationUser.setUserId(tempmap.get("USER_ID") == null ? "" : (String) tempmap.get("USER_ID"));
                perIntegrationUser.setIp(getIpAddress(request));
                perIntegrationUser.setLastLogin(DateUtil.getTime());
                Object updNum = null;
                try {
                    updNum = dao.update("PerIntegrationUserMapper.updateByPrimaryKey", perIntegrationUser);
                    if (null == updNum || "0".equals(updNum.toString())) {
                        //json格式没有被打乱不需要格式化
                        logger.info("更新用户基本信息表失败");
                        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                        logger.info("queryScore---jsonObject.toString()---" + jsonReturn + "---");
                        return jsonReturn;
                    }
                } catch (Exception e) {
                    logger.info("更新用户基本信息表失败");
                    //事务回滚
                    logger.info("=====================事务回滚======================");
                    transactionManager.rollback(transStatus);
                    //json格式没有被打乱不需要格式化
                    String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                    logger.info("loginIn---jsonObject.toString()---" + jsonReturn + "---");
                    return jsonReturn;
                }
                // 输出返回结果(不走最终的统一输出结果的原因是，这里的返回报文需要重新拼接一下，把总页数拼接到前边方便用户的使用)
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                //拼接总页数便于用户使用
                jsonReturn = jsonReturn.replaceAll("contentlist", "rows");
                logger.info("插入操作日志，操作记录");
                insertSysOperatelog(DESUtil.aesDecrypt(userNm.toString(), Const.ALLENCRYPTCODE), "login", logger, request);
                map.setCode(Const.SUCCESS_CODE);

            }
        } catch (Exception e) {
            logger.info("验证登录信息失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(transStatus);
            //json格式没有被打乱不需要格式化
            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            logger.info("loginIn---jsonObject.toString()---" + jsonReturn + "---");
            return jsonReturn;
        }
        // 统一输出返回结果
        HashMap totalMap = new HashMap();
        if (!Const.SUCCESS_CODE.equals(map.getCode())) {
            totalMap.put("total", "0");
        }
        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        String totalMapJson = JSON.toJSONString(totalMap).replaceAll("\\{", "").replaceAll("}", ",");
        jsonReturn = jsonReturn.replaceAll("\"code", totalMapJson + "\"code");
        logger.info("loginIn---jsonObject.toString()---" + jsonReturn + "---");
        return jsonReturn;
    }

    /**
     * 修改积分管理系统用户信息
     * 王燕来 2019.1。12
     * Created by wangyanlai on 20180425.
     *
     * @author wangyanlai
     * wangyanlai@cei.gov.cn
     */
    @Override
    @Transactional()
    public String updUser(Map<String, Object> reqMap, Logger logger, HttpServletRequest request) {
        ReturnData map = new ReturnData();
        //初始化事务参数配置
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //隔离级别为 PROPAGATION_NESTED--如果当前存在事务，则在嵌套事务内执行。
        //如果当前没有事务，则进行与PROPAGATION_REQUIRED(新创事务)类似的操作。
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
        TransactionStatus transStatus = transactionManager.getTransaction(def);
        //验证session超时
        String userId = (String) request.getSession().getAttribute("sessionUserId");
        logger.info("sessionUserId==" + userId);
        if (null == userId || "".equals(userId)) {
            logger.info("session超时退出");
            map.setCode(Const.LOGIN_TIME_OUT);
            return JSON.toJSONString(map);
        }

        //初始化返回码为错误
        map.setCode(Const.FAILURE_CODE);
        //允许进行下面几种类型的积分信息查询
        Object passWd = reqMap.get("password");
        Object newpassWd = reqMap.get("newpassword");
        //初始化sql送值实体
        Page page = new Page();
        map.setCode(Const.FAILURE_CODE);
        //添加分页信息
        PerIntegrationUser perIntegrationUser = new PerIntegrationUser();
        perIntegrationUser.setPassword(passWd.toString());
        perIntegrationUser.setIp(newpassWd.toString());
        // 查询个人积分基本信息表
        Object updNum = null;
        try {
            userId = aesDecrypt(userId, Const.ALLENCRYPTCODE);
            perIntegrationUser.setUserId(userId);
            updNum = dao.update("PerIntegrationUserMapper.updatePassWord", perIntegrationUser);
            if (null == updNum || "0".equals(updNum.toString())) {
                logger.info("更新用户基本信息表失败");
                //事务回滚
                logger.info("=====================事务回滚======================");
                transactionManager.rollback(transStatus);
                //json格式没有被打乱不需要格式化
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("loginIn---jsonObject.toString()---" + jsonReturn + "---");
                return jsonReturn;
            }
            insertSysOperatelog(userId, "loginUpd", logger, request);
        } catch (Exception e1) {
            //json格式没有被打乱不需要格式化
            logger.info("更新用户基本信息表失败");
            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            logger.info("queryScore---jsonObject.toString()---" + jsonReturn + "---");
            return jsonReturn;
        }
        request.getSession().removeAttribute("sessionUserId");
        request.getSession().removeAttribute("sessionUserPwd");
        map.setCode(Const.SUCCESS_CODE);
        logger.info("修改用户信息成功");
        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        logger.info("loginIn---jsonObject.toString()---" + jsonReturn + "---");
        return jsonReturn;
    }

    /**
     * 删除积分管理系统用户信息
     * 王燕来 2019.1。12
     * Created by wangyanlai on 20180425.
     *
     * @author wangyanlai
     * wangyanlai@cei.gov.cn
     */
    @Override
    @Transactional()
    public String deleteUser(Map<String, Object> reqMap, Logger logger, HttpServletRequest request) {
        ReturnData map = new ReturnData();
        //初始化事务参数配置
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //隔离级别为 PROPAGATION_NESTED--如果当前存在事务，则在嵌套事务内执行。
        //如果当前没有事务，则进行与PROPAGATION_REQUIRED(新创事务)类似的操作。
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
        TransactionStatus transStatus = transactionManager.getTransaction(def);
        //初始化返回码为错误
        map.setCode(Const.FAILURE_CODE);
        //允许进行下面几种类型的积分信息查询
        Object passWd = reqMap.get("password");
        Object userNm = reqMap.get("username");
        //初始化sql送值实体
        Page page = new Page();
        try {
            map.setCode(Const.LOGINFAILURE_CODE);
            PageData qureyData = new PageData();
            String pageSizeStr = ((Object) reqMap.get("pageSize")).toString();
            //添加分页信息
            qureyData = getPage(qureyData, pageSizeStr, "1");
            qureyData.put("userNm", userNm);
            qureyData.put("passWd", passWd);
            //添加分页信息进入储值域，用于传参
            page.setPd(qureyData);
            // 查询个人积分基本信息表
            List<PageData> classTypeListBasis = (List<PageData>) dao.findForList("PerIntegrationUserMapper.selectByexamplePage", page);
            if (classTypeListBasis != null && classTypeListBasis.size() != 0) {
                map.setCode(Const.FAILURE_CODE);
                PerIntegrationUser perIntegrationUser = new PerIntegrationUser();
                HashMap tempmap = (HashMap) classTypeListBasis.get(0);
                perIntegrationUser.setUserId(tempmap.get("USER_ID") == null ? "" : (String) tempmap.get("USER_ID"));
                perIntegrationUser.setIp(getIpAddress(request));
                perIntegrationUser.setLastLogin(DateUtil.getTime());
                Object updNum = null;
                try {
                    updNum = dao.update("PerIntegrationUserMapper.updateByPrimaryKey", perIntegrationUser);
                    if (null == updNum || "0".equals(updNum.toString())) {
                        //json格式没有被打乱不需要格式化
                        logger.info("更新用户基本信息表失败");
                        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                        logger.info("queryScore---jsonObject.toString()---" + jsonReturn + "---");
                        return jsonReturn;
                    }
                } catch (Exception e) {
                    logger.info("更新用户基本信息表失败");
                    //事务回滚
                    logger.info("=====================事务回滚======================");
                    transactionManager.rollback(transStatus);
                    //json格式没有被打乱不需要格式化
                    String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                    logger.info("loginIn---jsonObject.toString()---" + jsonReturn + "---");
                    return jsonReturn;
                }
                // 输出返回结果(不走最终的统一输出结果的原因是，这里的返回报文需要重新拼接一下，把总页数拼接到前边方便用户的使用)
                String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
                //拼接总页数便于用户使用
                jsonReturn = jsonReturn.replaceAll("contentlist", "rows");
                logger.info("插入操作日志");
                insertSysOperatelog(DESUtil.aesDecrypt(userNm.toString(), Const.ALLENCRYPTCODE), "login", logger, request);
                map.setCode(Const.SUCCESS_CODE);

            }
        } catch (Exception e) {
            logger.info("验证登录信息失败");
            //事务回滚
            logger.info("=====================事务回滚======================");
            transactionManager.rollback(transStatus);
            //json格式没有被打乱不需要格式化
            String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            logger.info("loginIn---jsonObject.toString()---" + jsonReturn + "---");
            return jsonReturn;
        }
        // 统一输出返回结果
        HashMap totalMap = new HashMap();
        if (!Const.SUCCESS_CODE.equals(map.getCode())) {
            totalMap.put("total", "0");
        }
        String jsonReturn = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        String totalMapJson = JSON.toJSONString(totalMap).replaceAll("\\{", "").replaceAll("}", ",");
        jsonReturn = jsonReturn.replaceAll("\"code", totalMapJson + "\"code");
        logger.info("loginIn---jsonObject.toString()---" + jsonReturn + "---");
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
    public void insertSysOperatelog(String userId, String type, Logger logger, HttpServletRequest request) throws
            Exception {
        SysOperatelog sysOperatelog = new SysOperatelog();
        sysOperatelog.setCreatetime(DateUtil.getTime());
        sysOperatelog.setIp(getIpAddress(request));
        sysOperatelog.setLogid(UuidUtil.get32UUID());
        if ("login".equals(type)) {
            sysOperatelog.setLogname("用户通过ip:" + getIpAddress(request) + "进行系统登录");
        } else if ("loginOut".equals(type)) {
            sysOperatelog.setLogname("用户通过ip:" + getIpAddress(request) + "进行系统登出");
        } else if ("loginUpd".equals(type)) {
            sysOperatelog.setLogname("用户通过ip:" + getIpAddress(request) + "进行账户密码修改");
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
}
