package org.spring.springboot.service;

import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 接口通用业务逻辑接口类(该service专用于用户积分查询)
 *
 * Created by wangyanlai on 20180425.
 * @author wangyanlai
 */
public interface QuestionQryService {

    /**
     * 查询入门手册信息列表
     * 王燕来 2019.1。21
     * Created by wangyanlai on 20190425.
     * @author wangyanlai
     */
    String queryBooks(Map<String, Object> reqMap, Logger logger, HttpServletRequest request);


    /**
     * 查询入门视频信息列表
     * 王燕来 2019.1。21
     * Created by wangyanlai on 20190425.
     * @author wangyanlai
     */
    String queryVideos(Map<String, Object> reqMap, Logger logger, HttpServletRequest request);


    /**
     * 查询平台问题列表service统一入口
     * 王燕来 2019.1。21
     * Created by wangyanlai on 20190425.
     * @author wangyanlai
     */
    String qryQuestion(Map<String, Object> reqMap, Logger logger, HttpServletRequest request);


    /**
     * 查询平台问题类别树
     * 王燕来 2019.1。21
     * Created by wangyanlai on 20190425.
     * @author wangyanlai
     */
    String treeGrid(Map<String, Object> reqMap, Logger logger, HttpServletRequest request);

    /**
     * 问题回复信息查询
     * 王燕来 2019.1。21
     * Created by wangyanlai on 20190425.
     * @author wangyanlai
     */
    String queryQuestionReply(Map<String, Object> reqMap, Logger logger, HttpServletRequest request);

    /**
     * 入门基本信息查询
     * 王燕来 2019.1。21
     * Created by wangyanlai on 20190425.
     * @author wangyanlai
     */
    String queryPreliminary(Map<String, Object> reqMap, Logger logger, HttpServletRequest request);

}
