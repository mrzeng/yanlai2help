package org.spring.springboot.zw.controller;

import base.zw.controller.BaseController;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.spring.springboot.dao.DaoSupport;
import org.spring.springboot.domain.ReturnData;
import org.spring.springboot.service.*;
import org.spring.springboot.zw.util.Const;
import org.spring.springboot.zw.util.FileUtils;
import org.spring.springboot.zw.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

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
@RequestMapping(value = "/webApp")
@EnableRedisHttpSession
public class WebController extends BaseController {
    @Resource(name = "daoSupport")
    private DaoSupport dao;
    @Autowired
    private APPService appService;
    @Autowired
    private UserService userService;
    @Autowired
    private QuestionQryService questionQryService;
    @Autowired
    private QuestionUpdService questionUpdService;

    /**
     * 查询积分信息接口(依据查询类型查询的信息分别如下)
     * QUERY_QUESTION_LIST = "0000"; // 表示查询问题汇总列表
     * QUERY_QUESTION_TYPE_LIST = "10000"; // 表示问题类别列表
     * QUERY_VIDEOS_LIST = "20000"; // 表示视频列表
     * QUERY_BOOKS_LIST = "30000"; // 表示手册列表
     * <p>
     * * @author zhaowei
     * * @version 2018年4月30日 下午4:53:32
     *
     * @param reqMap
     * @return
     * @author 王燕来
     * @version 2018年12月17日 上午10:09:20
     */
    private String userId = "userid";
    private String qryType = "qrytype";
    private String updType = "updtype";
    private String key = "key";


    @RequestMapping(value = "/treeGrid", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
    public String treeGrid(@RequestBody Map<String, Object> reqMap) {
        ReturnData returndata = new ReturnData();
        HttpServletRequest request = getRequest();
        /*如果外部送进来key字段，有值，并且值与免除session校验的特征值一致，就可以跳过session验证。*/
        if (!(!org.springframework.util.StringUtils.isEmpty(reqMap.get(key)) && Const.NO_SESSIONOUTTIMEKEY.equals(((Object) reqMap.get(key)).toString()))) {
            if (!sessionValidate()) {
                returndata.setCode(Const.LOGIN_TIME_OUT);
                return JSON.toJSONString(returndata);
            }
        }else{
            reqMap.put(userId, Const.CONUSER_ID_INTERFACE);
            if (org.springframework.util.StringUtils.isEmpty((reqMap.get(userId)))) {
                returndata.setCode(Const.IRREGULAR_PARAMETERS);
                return JSON.toJSONString(returndata);
            }
        }
        return questionQryService.treeGrid(reqMap, logger, request);
    }

    @RequestMapping(value = "/addTreeGrid", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
    public String addTreeGrid(@RequestBody Map<String, Object> reqMap) {
        ReturnData returndata = new ReturnData();
        HttpServletRequest request = getRequest();
        /*如果外部送进来key字段，有值，并且值与免除session校验的特征值一致，就可以跳过session验证。*/
        if (!(!org.springframework.util.StringUtils.isEmpty(reqMap.get(key)) && Const.NO_SESSIONOUTTIMEKEY.equals(((Object) reqMap.get(key)).toString()))) {
            if (!sessionValidate()) {
                returndata.setCode(Const.LOGIN_TIME_OUT);
                return JSON.toJSONString(returndata);
            }
        }

        if (org.springframework.util.StringUtils.isEmpty((reqMap.get("pid")))) {
            returndata.setCode(Const.IRREGULAR_PARAMETERS);
            return JSON.toJSONString(returndata);
        }
        return questionUpdService.addTreeGrid(reqMap, logger, request);
    }


    @RequestMapping(value = "/updTreeGrid", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
    public String updTreeGrid(@RequestBody Map<String, Object> reqMap) {
        ReturnData returndata = new ReturnData();
        HttpServletRequest request = getRequest();
        /*如果外部送进来key字段，有值，并且值与免除session校验的特征值一致，就可以跳过session验证。*/
        if (!(!org.springframework.util.StringUtils.isEmpty(reqMap.get(key)) && Const.NO_SESSIONOUTTIMEKEY.equals(((Object) reqMap.get(key)).toString()))) {
            if (!sessionValidate()) {
                returndata.setCode(Const.LOGIN_TIME_OUT);
                return JSON.toJSONString(returndata);
            }
        }
        if (org.springframework.util.StringUtils.isEmpty((reqMap.get("id")))) {
            returndata.setCode(Const.IRREGULAR_PARAMETERS);
            return JSON.toJSONString(returndata);
        }
        return questionUpdService.updTreeGrid(reqMap, logger, request);
    }


    @RequestMapping(value = "/delTreeGrid", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
    public String delTreeGrid(@RequestBody Map<String, Object> reqMap) {
        ReturnData returndata = new ReturnData();
        HttpServletRequest request = getRequest();
        /*如果外部送进来key字段，有值，并且值与免除session校验的特征值一致，就可以跳过session验证。*/
        if (!(!org.springframework.util.StringUtils.isEmpty(reqMap.get(key)) && Const.NO_SESSIONOUTTIMEKEY.equals(((Object) reqMap.get(key)).toString()))) {
            if (!sessionValidate()) {
                returndata.setCode(Const.LOGIN_TIME_OUT);
                return JSON.toJSONString(returndata);
            }
        }
        if (org.springframework.util.StringUtils.isEmpty((reqMap.get("id")))) {
            returndata.setCode(Const.IRREGULAR_PARAMETERS);
            return JSON.toJSONString(returndata);
        }
        return questionUpdService.delTreeGrid(reqMap, logger, request);
    }


    //userid 用户编号
    //qrytype 查询类别
    //updtype 变更类别
    /*问题回复信息列表*/
    @RequestMapping(value = "/queryQuestionReply", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
    public String queryQuestionReply(@RequestBody Map<String, Object> reqMap) {
        ReturnData returndata = new ReturnData();
        HttpServletRequest request = getRequest();
        /*如果外部送进来key字段，有值，并且值与免除session校验的特征值一致，就可以跳过session验证。*/
        if (!(!org.springframework.util.StringUtils.isEmpty(reqMap.get(key)) && Const.NO_SESSIONOUTTIMEKEY.equals(((Object) reqMap.get(key)).toString()))) {
            if (!sessionValidate()) {
                returndata.setCode(Const.LOGIN_TIME_OUT);
                return JSON.toJSONString(returndata);
            }
        }else{
            reqMap.put(userId, Const.CONUSER_ID_INTERFACE);
            if (org.springframework.util.StringUtils.isEmpty((reqMap.get(userId)))) {
                returndata.setCode(Const.IRREGULAR_PARAMETERS);
                return JSON.toJSONString(returndata);
            }
        }

        return questionQryService.queryQuestionReply(reqMap, logger, request);
    }


    /**
     * 增加问题回复信息接口
     *
     * @author wangyanlai by 2018.12.21
     * @email wangyanlai@cei.gov.cn
     */
    @RequestMapping(value = "/addQuestionReply", method = RequestMethod.POST)
    public String addQuestionReply(@RequestBody Map<String, Object> reqMap) {
        HttpServletRequest request = getRequest();
        ReturnData returndata = new ReturnData();
        /*如果外部送进来key字段，有值，并且值与免除session校验的特征值一致，就可以跳过session验证。*/
        if (!(!org.springframework.util.StringUtils.isEmpty(reqMap.get(key)) && Const.NO_SESSIONOUTTIMEKEY.equals(((Object) reqMap.get(key)).toString()))) {
            if (!sessionValidate()) {
                returndata.setCode(Const.LOGIN_TIME_OUT);
                return JSON.toJSONString(returndata);
            }
        }else{
            if (org.springframework.util.StringUtils.isEmpty((reqMap.get(userId)))) {
                returndata.setCode(Const.IRREGULAR_PARAMETERS);
                return JSON.toJSONString(returndata);
            }
        }
        returndata.setCode(Const.IRREGULAR_PARAMETERS);
        if (!StringUtil.isEmpty(reqMap.get("questionid"))) {
            return JSON.toJSONString(returndata);
        }
        return questionUpdService.addQuestionReply(reqMap, logger, request);
    }


    /**
     * 变更问题回复信息接口
     *
     * @author wangyanlai by 2018.12.21
     * @email wangyanlai@cei.gov.cn
     */
    @RequestMapping(value = "/updQuestionReply", method = RequestMethod.POST)
    public String updQuestionReply(@RequestBody Map<String, Object> reqMap) {
        HttpServletRequest request = getRequest();
        ReturnData returndata = new ReturnData();
        /*如果外部送进来key字段，有值，并且值与免除session校验的特征值一致，就可以跳过session验证。*/
        if (!(!org.springframework.util.StringUtils.isEmpty(reqMap.get(key)) && Const.NO_SESSIONOUTTIMEKEY.equals(((Object) reqMap.get(key)).toString()))) {
            if (!sessionValidate()) {
                returndata.setCode(Const.LOGIN_TIME_OUT);
                return JSON.toJSONString(returndata);
            }
        }else{
            if (org.springframework.util.StringUtils.isEmpty((reqMap.get(userId)))) {
                returndata.setCode(Const.IRREGULAR_PARAMETERS);
                return JSON.toJSONString(returndata);
            }
        }
        returndata.setCode(Const.IRREGULAR_PARAMETERS);
        if (!StringUtil.isEmpty(reqMap.get("id"))) {
            return JSON.toJSONString(returndata);
        }
        return questionUpdService.updQuestionReply(reqMap, logger, request);
    }

    /**
     * 删除问题回复信息接口
     *
     * @author wangyanlai by 2018.12.21
     * @email wangyanlai@cei.gov.cn
     */
    @RequestMapping(value = "/delQuestionReply", method = RequestMethod.POST)
    public String delQuestionReply(@RequestBody Map<String, Object> reqMap) {
        HttpServletRequest request = getRequest();
        ReturnData returndata = new ReturnData();
        /*如果外部送进来key字段，有值，并且值与免除session校验的特征值一致，就可以跳过session验证。*/
        if (!(!org.springframework.util.StringUtils.isEmpty(reqMap.get(key)) && Const.NO_SESSIONOUTTIMEKEY.equals(((Object) reqMap.get(key)).toString()))) {
            if (!sessionValidate()) {
                returndata.setCode(Const.LOGIN_TIME_OUT);
                return JSON.toJSONString(returndata);
            }
        }else{
            if (org.springframework.util.StringUtils.isEmpty((reqMap.get(userId)))) {
                returndata.setCode(Const.IRREGULAR_PARAMETERS);
                return JSON.toJSONString(returndata);
            }
        }
        returndata.setCode(Const.IRREGULAR_PARAMETERS);
        if (!StringUtil.isEmpty(reqMap.get("id"))) {
            return JSON.toJSONString(returndata);
        }
        return questionUpdService.delQuestionReply(reqMap, logger, request);
    }


    /*问题汇总信息列表*/
    @RequestMapping(value = "/queryQuestion", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
    public String queryQuestion(@RequestBody Map<String, Object> reqMap) {
        ReturnData returndata = new ReturnData();
        HttpServletRequest request = getRequest();
        /*如果外部送进来key字段，有值，并且值与免除session校验的特征值一致，就可以跳过session验证。*/
        if (!(!org.springframework.util.StringUtils.isEmpty(reqMap.get(key)) && Const.NO_SESSIONOUTTIMEKEY.equals(((Object) reqMap.get(key)).toString()))) {
            if (!sessionValidate()) {
                returndata.setCode(Const.LOGIN_TIME_OUT);
                return JSON.toJSONString(returndata);
            }
        }else{
            //如果是通过调用接口访问后台，用户号是必须传输的条件
            if (org.springframework.util.StringUtils.isEmpty((reqMap.get(userId)))) {
                returndata.setCode(Const.IRREGULAR_PARAMETERS);
                return JSON.toJSONString(returndata);
            }
        }

        if (org.springframework.util.StringUtils.isEmpty((reqMap.get(qryType)))) {
            returndata.setCode(Const.IRREGULAR_PARAMETERS);
            return JSON.toJSONString(returndata);
        }

        return questionQryService.qryQuestion(reqMap, logger, request);
    }


    /**
     * 变更问题信息接口
     *
     * @author wangyanlai by 2018.12.21
     * @email wangyanlai@cei.gov.cn
     */
    @RequestMapping(value = "/updQuestion", method = RequestMethod.POST)
    public String updQuestion(@RequestBody Map<String, Object> reqMap) {
        HttpServletRequest request = getRequest();
        ReturnData returndata = new ReturnData();
        returndata.setCode(Const.EMPTY_CODE);
        /*如果外部送进来key字段，有值，并且值与免除session校验的特征值一致，就可以跳过session验证。*/
        if (!(!org.springframework.util.StringUtils.isEmpty(reqMap.get(key)) && Const.NO_SESSIONOUTTIMEKEY.equals(((Object) reqMap.get(key)).toString()))) {
            if (!sessionValidate()) {
                returndata.setCode(Const.LOGIN_TIME_OUT);
                return JSON.toJSONString(returndata);
            }
        }else{
            //如果是通过调用接口访问后台，用户号是必须传输的条件
            if (org.springframework.util.StringUtils.isEmpty((reqMap.get(userId)))) {
                returndata.setCode(Const.IRREGULAR_PARAMETERS);
                return JSON.toJSONString(returndata);
            }
        }
        returndata.setCode(Const.IRREGULAR_PARAMETERS);
        if (!StringUtil.isEmpty(reqMap.get("id"))) {
            return JSON.toJSONString(returndata);
        }
        return questionUpdService.updQuestion(reqMap, logger, request);
    }


    /**
     * 新增问题信息接口
     *
     * @author wangyanlai by 2019.1.21
     * @email wangyanlai@cei.gov.cn
     */
    @RequestMapping(value = "/addQuestion", method = RequestMethod.POST)
    public String addQuestion(@RequestBody Map<String, Object> reqMap) {
        HttpServletRequest request = getRequest();
        ReturnData returndata = new ReturnData();
        /*如果外部送进来key字段，有值，并且值与免除session校验的特征值一致，就可以跳过session验证。*/
        if (!(!org.springframework.util.StringUtils.isEmpty(reqMap.get(key)) && Const.NO_SESSIONOUTTIMEKEY.equals(((Object) reqMap.get(key)).toString()))) {
            if (!sessionValidate()) {
                returndata.setCode(Const.LOGIN_TIME_OUT);
                return JSON.toJSONString(returndata);
            }
        }else{
            //如果是通过调用接口访问后台，用户号是必须传输的条件
            if (org.springframework.util.StringUtils.isEmpty((reqMap.get(userId)))) {
                returndata.setCode(Const.IRREGULAR_PARAMETERS);
                return JSON.toJSONString(returndata);
            }
        }
        returndata.setCode(Const.IRREGULAR_PARAMETERS);
        return questionUpdService.addQuestion(reqMap, logger, request);
    }

    /**
     * 新增问题信息接口
     *
     * @author wangyanlai by 2019.1.21
     * @email wangyanlai@cei.gov.cn
     */
    @RequestMapping(value = "/updQuestionOther", method = RequestMethod.POST)
    public String updQuestionOther(@RequestBody Map<String, Object> reqMap) {
        HttpServletRequest request = getRequest();
        ReturnData returndata = new ReturnData();
        /*如果外部送进来key字段，有值，并且值与免除session校验的特征值一致，就可以跳过session验证。*/
        if (!(!org.springframework.util.StringUtils.isEmpty(reqMap.get(key)) && Const.NO_SESSIONOUTTIMEKEY.equals(((Object) reqMap.get(key)).toString()))) {
            if (!sessionValidate()) {
                returndata.setCode(Const.LOGIN_TIME_OUT);
                return JSON.toJSONString(returndata);
            }
        }else{
            //如果是通过调用接口访问后台，用户号是必须传输的条件
            //如果是通过调用接口访问后台，用户号是必须传输的条件
            reqMap.put(userId, Const.CONUSER_ID_INTERFACE);
            if (org.springframework.util.StringUtils.isEmpty((reqMap.get(userId)))) {
                returndata.setCode(Const.IRREGULAR_PARAMETERS);
                return JSON.toJSONString(returndata);
            }
            //问题id必传
            if (org.springframework.util.StringUtils.isEmpty((reqMap.get("questionid")))) {
                returndata.setCode(Const.IRREGULAR_PARAMETERS);
                return JSON.toJSONString(returndata);
            }
            //点赞，点击，拍砖类型必传
            if (org.springframework.util.StringUtils.isEmpty((reqMap.get("updtype")))) {
                returndata.setCode(Const.IRREGULAR_PARAMETERS);
                return JSON.toJSONString(returndata);
            }
        }
        returndata.setCode(Const.IRREGULAR_PARAMETERS);
        return questionUpdService.updQuestionOther(reqMap, logger, request);
    }


    /**
     * 删除问题信息接口
     *
     * @author wangyanlai by 2018.12.21
     * @email wangyanlai@cei.gov.cn
     */
    @RequestMapping(value = "/delQuestion", method = RequestMethod.POST)
    public String delQuestion(@RequestBody Map<String, Object> reqMap) {
        HttpServletRequest request = getRequest();
        ReturnData returndata = new ReturnData();
        /*如果外部送进来key字段，有值，并且值与免除session校验的特征值一致，就可以跳过session验证。*/
        if (!(!org.springframework.util.StringUtils.isEmpty(reqMap.get(key)) && Const.NO_SESSIONOUTTIMEKEY.equals(((Object) reqMap.get(key)).toString()))) {
            if (!sessionValidate()) {
                returndata.setCode(Const.LOGIN_TIME_OUT);
                return JSON.toJSONString(returndata);
            }
        }else{
            //如果是通过调用接口访问后台，用户号是必须传输的条件
            if (org.springframework.util.StringUtils.isEmpty((reqMap.get(userId)))) {
                returndata.setCode(Const.IRREGULAR_PARAMETERS);
                return JSON.toJSONString(returndata);
            }
        }
        returndata.setCode(Const.IRREGULAR_PARAMETERS);
        if (StringUtils.isBlank(((Object) reqMap.get("id")).toString()) || StringUtils.isBlank(((Object) reqMap.get("id")).toString())) {
            return JSON.toJSONString(returndata);
        }
        return questionUpdService.delQuestion(reqMap, logger, request);
    }


    /*帮助视频汇总信息列表*/
    @RequestMapping(value = "/queryVideos", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
    public String queryVideos(@RequestBody Map<String, Object> reqMap) {
        ReturnData returndata = new ReturnData();
        HttpServletRequest request = getRequest();
        /*如果外部送进来key字段，有值，并且值与免除session校验的特征值一致，就可以跳过session验证。*/
        if (!(!org.springframework.util.StringUtils.isEmpty(reqMap.get(key)) && Const.NO_SESSIONOUTTIMEKEY.equals(((Object) reqMap.get(key)).toString()))) {
            if (!sessionValidate()) {
                returndata.setCode(Const.LOGIN_TIME_OUT);
                return JSON.toJSONString(returndata);
            }
        }else{
            //如果是通过调用接口访问后台，用户号是必须传输的条件
            reqMap.put(userId, Const.CONUSER_ID_INTERFACE);
            if (org.springframework.util.StringUtils.isEmpty((reqMap.get(userId)))) {
                returndata.setCode(Const.IRREGULAR_PARAMETERS);
                return JSON.toJSONString(returndata);
            }
        }


        if (org.springframework.util.StringUtils.isEmpty((reqMap.get(qryType)))) {
            returndata.setCode(Const.IRREGULAR_PARAMETERS);
            return JSON.toJSONString(returndata);
        }

        return questionQryService.queryVideos(reqMap, logger, request);
    }


    /**
     * 变更入门视频信息接口
     *
     * @author wangyanlai by 2018.12.21
     * @email wangyanlai@cei.gov.cn
     */
    @RequestMapping(value = "/updVideos", method = RequestMethod.POST)
    public String updVideos(@RequestBody Map<String, Object> reqMap) {
        HttpServletRequest request = getRequest();
        ReturnData returndata = new ReturnData();
        /*如果外部送进来key字段，有值，并且值与免除session校验的特征值一致，就可以跳过session验证。*/
        if (!(!org.springframework.util.StringUtils.isEmpty(reqMap.get(key)) && Const.NO_SESSIONOUTTIMEKEY.equals(((Object) reqMap.get(key)).toString()))) {
            if (!sessionValidate()) {
                returndata.setCode(Const.LOGIN_TIME_OUT);
                return JSON.toJSONString(returndata);
            }
        }
        returndata.setCode(Const.IRREGULAR_PARAMETERS);
        if (!StringUtil.isEmpty(reqMap.get("id"))) {
            return JSON.toJSONString(returndata);
        }
        return questionUpdService.updVideos(reqMap, logger, request);
    }


    /**
     * 上传附件接口
     *
     * @author wangyanlai by 2028.12.21
     * @email wangyanlai@cei.gov.cn
     */
    @RequestMapping(value = "/addFile", method = RequestMethod.POST)
    @ResponseBody
    public Object addFile(HttpServletRequest request) {
        Map<String, Object> json = new HashMap<String, Object>();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        boolean fileFlag = false;
        logger.info(request.getParameter("type"));
        if (StringUtil.isEmpty(request.getParameter("type"))) {
            String filePath = multipartRequest.getParameter("file_path");
            File deleteFile = new File(filePath);
            if (deleteFile.exists() && deleteFile.isFile()
                    && deleteFile.delete() == true) {
                fileFlag = true;
            }
        }else{
            //新增文件直接就是true
            fileFlag = true;
        }
        if (!fileFlag) {
            json.put("code", Const.EMPTY_CODE);
            json.put("message", "更新失败");
            json.put("result", "error");
            json.put("file_path", "");
            return json;
        }
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        String path = this.getClass().getResource("/").getPath();
//        String os = System.getProperty("os.name");
//        String ctxPath = path.substring(1,path.length())+Const.FILE_PATH;
//        真实的物理地址
        String ctxPath = request.getSession().getServletContext().getRealPath("/") + Const.FILE_PATH; //文件上传存储路径
//        ctxPath += File.separator; //后缀
        // 创建文件夹
        ctxPath = ctxPath.replaceAll("//", "\\") + "\\";
        logger.info("ctxPath===" + ctxPath);
        File file = new File(ctxPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String fileName = null;
        String newName = null;
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            // 上传文件
            MultipartFile mf = entity.getValue();
            fileName = mf.getOriginalFilename();//获取原文件名
            //获得当前时间的最小精度
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            newName = format.format(new Date());
            //获得三位随机数
            Random random = new Random();
            if(StringUtil.isEmpty(request.getParameter("type"))){
                newName = multipartRequest.getParameter("id");
            }else{
                for (int i = 0; i < 3; i++) {
                    newName = newName + random.nextInt(9);
                }
            }


            File uploadFile = new File(ctxPath + newName + fileName.substring(fileName.lastIndexOf(".")));
            try {
                FileCopyUtils.copy(mf.getBytes(), uploadFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            String importFile = ctxPath + newName + fileName.substring(fileName.lastIndexOf("."));
            importFile = importFile.replaceAll("\\\\", "/");
        } catch (Exception e) {
            logger.error("保存失败", e);
        }
        json.put("id", newName);
        json.put("code", Const.SUCCESS_CODE);
        json.put("message", "上传成功");
        json.put("result", "success");
        json.put("file_path", ctxPath);
        return json;
    }


    /**
     * 新增入门视频信息接口
     *
     * @author wangyanlai by 2019.1.21
     * @email wangyanlai@cei.gov.cn
     */
    @RequestMapping(value = "/addVideos", method = RequestMethod.POST)
    public String addVideos(@RequestBody Map<String, Object> reqMap) {
        HttpServletRequest request = getRequest();
        ReturnData returndata = new ReturnData();
        /*如果外部送进来key字段，有值，并且值与免除session校验的特征值一致，就可以跳过session验证。*/
        if (!(!org.springframework.util.StringUtils.isEmpty(reqMap.get(key)) && Const.NO_SESSIONOUTTIMEKEY.equals(((Object) reqMap.get(key)).toString()))) {
            if (!sessionValidate()) {
                returndata.setCode(Const.LOGIN_TIME_OUT);
                return JSON.toJSONString(returndata);
            }
        }
        returndata.setCode(Const.IRREGULAR_PARAMETERS);
        if (!StringUtil.isEmpty(reqMap.get("preliminaryid"))) {
            return JSON.toJSONString(returndata);
        }
        return questionUpdService.addVideos(reqMap, logger, request);
    }


    /**
     * 删除入门视频信息接口
     *
     * @author wangyanlai by 2018.12.21
     * @email wangyanlai@cei.gov.cn
     */
    @RequestMapping(value = "/delVideos", method = RequestMethod.POST)
    public String delVideos(@RequestBody Map<String, Object> reqMap) {
        HttpServletRequest request = getRequest();
        ReturnData returndata = new ReturnData();
        /*如果外部送进来key字段，有值，并且值与免除session校验的特征值一致，就可以跳过session验证。*/
        if (!(!org.springframework.util.StringUtils.isEmpty(reqMap.get(key)) && Const.NO_SESSIONOUTTIMEKEY.equals(((Object) reqMap.get(key)).toString()))) {
            if (!sessionValidate()) {
                returndata.setCode(Const.LOGIN_TIME_OUT);
                return JSON.toJSONString(returndata);
            }
        }
        returndata.setCode(Const.IRREGULAR_PARAMETERS);
        if (StringUtils.isBlank(((Object) reqMap.get("id")).toString()) || StringUtils.isBlank(((Object) reqMap.get("id")).toString())) {
            return JSON.toJSONString(returndata);
        }

        if (StringUtils.isBlank(((Object) reqMap.get("preliminaryid")).toString()) || StringUtils.isBlank(((Object) reqMap.get("preliminaryid")).toString())) {
            return JSON.toJSONString(returndata);
        }

        return questionUpdService.delVideos(reqMap, logger, request);
    }


    /*帮助手册汇总信息列表*/
    @RequestMapping(value = "/queryBooks", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
    public String queryBooks(@RequestBody Map<String, Object> reqMap) {
        ReturnData returndata = new ReturnData();
        HttpServletRequest request = getRequest();
        /*如果外部送进来key字段，有值，并且值与免除session校验的特征值一致，就可以跳过session验证。*/
        if (!(!org.springframework.util.StringUtils.isEmpty(reqMap.get(key)) && Const.NO_SESSIONOUTTIMEKEY.equals(((Object) reqMap.get(key)).toString()))) {
            if (!sessionValidate()) {
                returndata.setCode(Const.LOGIN_TIME_OUT);
                return JSON.toJSONString(returndata);
            }
        }else{
            //如果是通过调用接口访问后台，用户号是必须传输的条件
            reqMap.put(userId, Const.CONUSER_ID_INTERFACE);
            if (org.springframework.util.StringUtils.isEmpty((reqMap.get(userId)))) {
                returndata.setCode(Const.IRREGULAR_PARAMETERS);
                return JSON.toJSONString(returndata);
            }
        }

        if (org.springframework.util.StringUtils.isEmpty((reqMap.get(qryType)))) {
            returndata.setCode(Const.IRREGULAR_PARAMETERS);
            return JSON.toJSONString(returndata);
        }

        return questionQryService.queryBooks(reqMap, logger, request);
    }


    /**
     * 变更手册信息接口
     *
     * @author wangyanlai by 2018.12.21
     * @email wangyanlai@cei.gov.cn
     */
    @RequestMapping(value = "/updBooks", method = RequestMethod.POST)
    public String updBooks(@RequestBody Map<String, Object> reqMap) {
        HttpServletRequest request = getRequest();
        ReturnData returndata = new ReturnData();
        /*如果外部送进来key字段，有值，并且值与免除session校验的特征值一致，就可以跳过session验证。*/
        if (!(!org.springframework.util.StringUtils.isEmpty(reqMap.get(key)) && Const.NO_SESSIONOUTTIMEKEY.equals(((Object) reqMap.get(key)).toString()))) {
            if (!sessionValidate()) {
                returndata.setCode(Const.LOGIN_TIME_OUT);
                return JSON.toJSONString(returndata);
            }
        }
        returndata.setCode(Const.IRREGULAR_PARAMETERS);
        if (!StringUtil.isEmpty(reqMap.get("id"))) {
            return JSON.toJSONString(returndata);
        }
        return questionUpdService.updBooks(reqMap, logger, request);
    }


    /**
     * 新增手册信息接口
     *
     * @author wangyanlai by 2019.1.21
     * @email wangyanlai@cei.gov.cn
     */
    @RequestMapping(value = "/addBooks", method = RequestMethod.POST)
    public String addBooks(@RequestBody Map<String, Object> reqMap) {
        HttpServletRequest request = getRequest();
        ReturnData returndata = new ReturnData();
        /*如果外部送进来key字段，有值，并且值与免除session校验的特征值一致，就可以跳过session验证。*/
        if (!(!org.springframework.util.StringUtils.isEmpty(reqMap.get(key)) && Const.NO_SESSIONOUTTIMEKEY.equals(((Object) reqMap.get(key)).toString()))) {
            if (!sessionValidate()) {
                returndata.setCode(Const.LOGIN_TIME_OUT);
                return JSON.toJSONString(returndata);
            }
        }
        returndata.setCode(Const.IRREGULAR_PARAMETERS);
        if (!StringUtil.isEmpty(reqMap.get("preliminaryid"))) {
            return JSON.toJSONString(returndata);
        }

        return questionUpdService.addBooks(reqMap, logger, request);
    }


    /**
     * 删除手册信息接口
     *
     * @author wangyanlai by 2018.12.21
     * @email wangyanlai@cei.gov.cn
     */
    @RequestMapping(value = "/delBooks", method = RequestMethod.POST)
    public String delBooks(@RequestBody Map<String, Object> reqMap) {
        HttpServletRequest request = getRequest();
        ReturnData returndata = new ReturnData();
        /*如果外部送进来key字段，有值，并且值与免除session校验的特征值一致，就可以跳过session验证。*/
        if (!(!org.springframework.util.StringUtils.isEmpty(reqMap.get(key)) && Const.NO_SESSIONOUTTIMEKEY.equals(((Object) reqMap.get(key)).toString()))) {
            if (!sessionValidate()) {
                returndata.setCode(Const.LOGIN_TIME_OUT);
                return JSON.toJSONString(returndata);
            }
        }
        returndata.setCode(Const.IRREGULAR_PARAMETERS);
        if (StringUtils.isBlank(((Object) reqMap.get("id")).toString()) || StringUtils.isBlank(((Object) reqMap.get("id")).toString())) {
            return JSON.toJSONString(returndata);
        }
        if (StringUtils.isBlank(((Object) reqMap.get("preliminaryid")).toString()) || StringUtils.isBlank(((Object) reqMap.get("preliminaryid")).toString())) {
            return JSON.toJSONString(returndata);
        }
        return questionUpdService.delBooks(reqMap, logger, request);
    }


    /*帮助手册汇总信息列表*/
    @RequestMapping(value = "/queryPreliminary", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
    public String queryPreliminary(@RequestBody Map<String, Object> reqMap) {
        ReturnData returndata = new ReturnData();
        HttpServletRequest request = getRequest();
        /*如果外部送进来key字段，有值，并且值与免除session校验的特征值一致，就可以跳过session验证。*/
        if (!(!org.springframework.util.StringUtils.isEmpty(reqMap.get(key)) && Const.NO_SESSIONOUTTIMEKEY.equals(((Object) reqMap.get(key)).toString()))) {
            if (!sessionValidate()) {
                returndata.setCode(Const.LOGIN_TIME_OUT);
                return JSON.toJSONString(returndata);
            }
        }else{
            reqMap.put(userId, Const.CONUSER_ID_INTERFACE);
            if (org.springframework.util.StringUtils.isEmpty((reqMap.get(userId)))) {
                returndata.setCode(Const.IRREGULAR_PARAMETERS);
                return JSON.toJSONString(returndata);
            }
        }


        return questionQryService.queryPreliminary(reqMap, logger, request);
    }


    /**
     * 变更手册信息接口
     *
     * @author wangyanlai by 2018.12.21
     * @email wangyanlai@cei.gov.cn
     */
    @RequestMapping(value = "/updPreliminary", method = RequestMethod.POST)
    public String updPreliminary(@RequestBody Map<String, Object> reqMap) {
        HttpServletRequest request = getRequest();
        ReturnData returndata = new ReturnData();
        /*如果外部送进来key字段，有值，并且值与免除session校验的特征值一致，就可以跳过session验证。*/
        if (!(!org.springframework.util.StringUtils.isEmpty(reqMap.get(key)) && Const.NO_SESSIONOUTTIMEKEY.equals(((Object) reqMap.get(key)).toString()))) {
            if (!sessionValidate()) {
                returndata.setCode(Const.LOGIN_TIME_OUT);
                return JSON.toJSONString(returndata);
            }
        }
        returndata.setCode(Const.IRREGULAR_PARAMETERS);
        if (!StringUtil.isEmpty(reqMap.get("id"))) {
            return JSON.toJSONString(returndata);
        }
        return questionUpdService.updPreliminary(reqMap, logger, request);
    }


    /**
     * 新增手册信息接口
     *
     * @author wangyanlai by 2019.1.21
     * @email wangyanlai@cei.gov.cn
     */
    @RequestMapping(value = "/addPreliminary", method = RequestMethod.POST)
    public String addPreliminary(@RequestBody Map<String, Object> reqMap) {
        HttpServletRequest request = getRequest();
        ReturnData returndata = new ReturnData();
        /*如果外部送进来key字段，有值，并且值与免除session校验的特征值一致，就可以跳过session验证。*/
        if (!(!org.springframework.util.StringUtils.isEmpty(reqMap.get(key)) && Const.NO_SESSIONOUTTIMEKEY.equals(((Object) reqMap.get(key)).toString()))) {
            if (!sessionValidate()) {
                returndata.setCode(Const.LOGIN_TIME_OUT);
                return JSON.toJSONString(returndata);
            }
        }
        returndata.setCode(Const.IRREGULAR_PARAMETERS);
        return questionUpdService.addPreliminary(reqMap, logger, request);
    }


    /**
     * 删除手册信息接口
     *
     * @author wangyanlai by 2018.12.21
     * @email wangyanlai@cei.gov.cn
     */
    @RequestMapping(value = "/delPreliminary", method = RequestMethod.POST)
    public String delPreliminary(@RequestBody Map<String, Object> reqMap) {
        HttpServletRequest request = getRequest();
        ReturnData returndata = new ReturnData();
        /*如果外部送进来key字段，有值，并且值与免除session校验的特征值一致，就可以跳过session验证。*/
        if (!(!org.springframework.util.StringUtils.isEmpty(reqMap.get(key)) && Const.NO_SESSIONOUTTIMEKEY.equals(((Object) reqMap.get(key)).toString()))) {
            if (!sessionValidate()) {
                returndata.setCode(Const.LOGIN_TIME_OUT);
                return JSON.toJSONString(returndata);
            }
        }
        returndata.setCode(Const.IRREGULAR_PARAMETERS);
        if (StringUtils.isBlank(((Object) reqMap.get("id")).toString()) || StringUtils.isBlank(((Object) reqMap.get("id")).toString())) {
            return JSON.toJSONString(returndata);
        }
        return questionUpdService.delPreliminary(reqMap, logger, request);
    }


    /*检验session是否超时*/
    public boolean sessionValidate() {
        HttpServletRequest request = getRequest();
        String userId = (String) request.getSession().getAttribute("sessionUserId");
        logger.info("sessionUserId==" + userId);
        if (null == userId || "".equals(userId)) {
            logger.info("session超时退出");
            return false;
        }
        return true;
    }

}
