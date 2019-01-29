package org.spring.springboot.service;

import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 接口通用业务逻辑接口类(该service专用于用户积分变更接口)
 *
 * Created by wangyanlai on 20180425.
 * @author wangyanlai
 */
public interface QuestionUpdService {

    /**
     * 删除问题类别信息
     * 王燕来 2019.1.7
     * Created by wangyanlai on 20180425.
     * @author wangyanlai
     */
    String delTreeGrid(Map<String, Object> reqMap, Logger logger, HttpServletRequest request);
    /**
     * 修改问题类别信息
     * 王燕来 2019.1.7
     * Created by wangyanlai on 20180425.
     * @author wangyanlai
     */
    String updTreeGrid(Map<String, Object> reqMap, Logger logger, HttpServletRequest request);

    /**增加问题类别信息
     * 王燕来 2019.1.7
     * Created by wangyanlai on 20180425.
     * @author wangyanlai
     */
    String addTreeGrid(Map<String, Object> reqMap, Logger logger, HttpServletRequest request);


    /**删除问题信息
     * 王燕来 2019.1.7
     * Created by wangyanlai on 20180425.
     * @author wangyanlai
     */
    String delQuestion(Map<String, Object> reqMap, Logger logger, HttpServletRequest request);

    /**更新问题信息
     * 王燕来 2019.1.7
     * Created by wangyanlai on 20180425.
     * @author wangyanlai
     */
    String updQuestion(Map<String, Object> reqMap, Logger logger, HttpServletRequest request);

    /**更新问题信息(点击，点赞，拍砖)
     * 王燕来 2019.1.7
     * Created by wangyanlai on 20180425.
     * @author wangyanlai
     */
    String updQuestionOther(Map<String, Object> reqMap, Logger logger, HttpServletRequest request);




    /**增加问题信息
     * 王燕来 2019.1.7
     * Created by wangyanlai on 20180425.
     * @author wangyanlai
     */
    String addQuestion(Map<String, Object> reqMap, Logger logger, HttpServletRequest request);


    /**变更问题回复信息接口
     * 王燕来 2019.1.7
     * Created by wangyanlai on 20180425.
     * @author wangyanlai
     */
    String updQuestionReply(Map<String, Object> reqMap, Logger logger, HttpServletRequest request);

    /**删除问题回复信息接口
     * 王燕来 2019.1.7
     * Created by wangyanlai on 20180425.
     * @author wangyanlai
     */
    String delQuestionReply(Map<String, Object> reqMap, Logger logger, HttpServletRequest request);


    /**增加问题回复信息接口
     * 王燕来 2019.1.7
     * Created by wangyanlai on 20180425.
     * @author wangyanlai
     */
    String addQuestionReply(Map<String, Object> reqMap, Logger logger, HttpServletRequest request);



    /**变更入门视频信息接口
     * 王燕来 2019.1.7
     * Created by wangyanlai on 20180425.
     * @author wangyanlai
     */
    String updVideos(Map<String, Object> reqMap, Logger logger, HttpServletRequest request);

    /**删除入门视频息接口
     * 个人积分配置信息新增service统一入口
     * 王燕来 2019.1.7
     * Created by wangyanlai on 20180425.
     * @author wangyanlai
     */
    String delVideos(Map<String, Object> reqMap, Logger logger, HttpServletRequest request);


    /**增加入门视频信息接口
     * 王燕来 2019.1.7
     * Created by wangyanlai on 20180425.
     * @author wangyanlai
     */
    String addVideos(Map<String, Object> reqMap, Logger logger, HttpServletRequest request);



    /**变更入门手册信息接口
     * 王燕来 2019.1.7
     * Created by wangyanlai on 20180425.
     * @author wangyanlai
     */
    String updBooks(Map<String, Object> reqMap, Logger logger, HttpServletRequest request);

    /**删除入门手册信息接口
     * 王燕来 2019.1.7
     * Created by wangyanlai on 20180425.
     * @author wangyanlai
     */
    String delBooks(Map<String, Object> reqMap, Logger logger, HttpServletRequest request);


    /**增加入门手册信息接口
     * 王燕来 2019.2.7
     * Created by wangyanlai on 20180425.
     * @author wangyanlai
     */
    String addBooks(Map<String, Object> reqMap, Logger logger, HttpServletRequest request);





    /**变更入门信息接口
     * 王燕来 2019.1.7
     * Created by wangyanlai on 20180425.
     * @author wangyanlai
     */
    String updPreliminary(Map<String, Object> reqMap, Logger logger, HttpServletRequest request);

    /**删除入门信息接口
     * 王燕来 2019.1.7
     * Created by wangyanlai on 20180425.
     * @author wangyanlai
     */
    String delPreliminary(Map<String, Object> reqMap, Logger logger, HttpServletRequest request);


    /**增加入门信息接口
     * 王燕来 2019.2.7
     * Created by wangyanlai on 20180425.
     * @author wangyanlai
     */
    String addPreliminary(Map<String, Object> reqMap, Logger logger, HttpServletRequest request);




}
