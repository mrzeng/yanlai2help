package org.spring.springboot.zw.controller;

import base.zw.controller.BaseController;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.springboot.dao.DaoSupport;
import org.spring.springboot.domain.Page;
import org.spring.springboot.domain.PageData;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * Title: AppController Description:
 *
 * @author wangyanlai
 * @version 2019年1月20日 下午4:53:32
 * 专用于前台页面的返回
 */
@org.springframework.stereotype.Controller
public class Controller extends BaseController {
    //系统主页
    @RequestMapping(value = "/")
    public String index(ModelMap map) {
        if(!sessionValidate()){
            return "/login/login";
        }
        return "/login/index";
    }

    @RequestMapping(value = "/index")
    public String welcome(ModelMap map) {
        if(!sessionValidate()){
            return "/login/login";
        }
        return "/login/index";
    }

    //系统页眉
    @RequestMapping(value = "/menu")
    public String menu(ModelMap map) {
        if(!sessionValidate()){
            return "/login/login";
        }
        return "/login/menu";
    }


    //修改密码页面
    @RequestMapping(value = "/updpass")
    public String eyebrow(ModelMap map) {
        if(!sessionValidate()){
            return "/login/login";
        }
        return "/login/updpass";
    }

    //积分汇总列表
    @RequestMapping(value = "/integrationBasis")
    public String configureBasis(ModelMap map) {
        if(!sessionValidate()){
            return "/login/login";
        }
        return "/login/integral_basis";
    }

    //积分课程列表
    @RequestMapping(value = "/integrationCourse")
    public String integrationCourse(ModelMap map) {
        if(!sessionValidate()){
            return "/login/login";
        }
        return "/login/integral_course";
    }

    //积分培训列表
    @RequestMapping(value = "/integrationTraining")
    public String integrationTraining(ModelMap map) {
        if(!sessionValidate()){
            return "/login/login";
        }
        return "/login/integral_training";
    }

    //积分积分兑换列表
    @RequestMapping(value = "/integrationExchange")
    public String integrationExchange(ModelMap map) {
        if(!sessionValidate()){
            return "/login/login";
        }
        return "/login/integral_exchange";
    }

    //积分作业列表
    @RequestMapping(value = "/integrationTask")
    public String integrationTask(ModelMap map) {
        if(!sessionValidate()){
            return "/login/login";
        }
        return "/login/integral_task";
    }

    //积分跑批列表
    @RequestMapping(value = "/integrationDayend")
    public String integrationDayend(ModelMap map) {

        if(!sessionValidate()){
            return "/login/login";
        }
        return "/login/integral_dayend";
    }

    //提交问卷调查积分列表
    @RequestMapping(value = "/integrationQuestionnaire")
    public String integrationQuestionnaire(ModelMap map) {
        if(!sessionValidate()){
            return "/login/login";
        }
        return "/login/integral_questionnaire";
    }

    //提交问题积分列表
    @RequestMapping(value = "/integrationQuestion")
    public String integrationQuestion(ModelMap map) {
        if(!sessionValidate()){
            return "/login/login";
        }
        return "/login/integral_question";
    }


    /*检验session是否超时*/
    public boolean sessionValidate() {
        HttpServletRequest request = getRequest();
        String userId = (String) request.getSession().getAttribute("sessionUserId");
        logger.info("sessionUserId=="+userId);
        if (null == userId || "".equals(userId)) {
            logger.info("session超时退出");
            return false;
        }
        return true;
    }

    //手册管理页面
    @RequestMapping(value = "/booksControl")
    public String booksControl(ModelMap map) {
        if(!sessionValidate()){
            return "/login/login";
        }
        return "/login/books_control";
    }

    //视频管理页面
    @RequestMapping(value = "/videosControl")
    public String videosControl(ModelMap map) {
        if(!sessionValidate()){
            return "/login/login";
        }
        return "/login/videos_control";
    }

    @RequestMapping(value = "/preliminaryBasis")
    public String preliminaryBasis(ModelMap map) {
        if(!sessionValidate()){
            return "/login/login";
        }
        return "/login/preliminary_basis";
    }

    //问题列表管理页面
    @RequestMapping(value = "/questionBasis")
    public String questionBasis(ModelMap map) {
        if(!sessionValidate()){
            return "/login/login";
        }
        return "/login/question_basis";
    }


    //问题回复页面
    @RequestMapping(value = "/questionBasisReply")
    public String commonQuestionBasis(ModelMap map) {
        if(!sessionValidate()){
            return "/login/login";
        }
        return "/login/question_basis_reply";
    }
}
