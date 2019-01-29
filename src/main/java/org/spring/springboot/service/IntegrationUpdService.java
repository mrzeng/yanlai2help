package org.spring.springboot.service;

import org.slf4j.Logger;
import org.spring.springboot.domain.*;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 接口通用业务逻辑接口类(该service专用于用户积分变更接口)
 *
 * Created by wangyanlai on 20180425.
 * @author wangyanlai
 */
public interface IntegrationUpdService {

    /**
     * 个人积分信息变更service统一入口
     * 王燕来 2018.12。21
     * Created by wangyanlai on 20180425.
     * @author wangyanlai
     * wangyanlai@cei.gov.cn
     */
    String updIntegration(Map<String, Object> reqMap, Logger logger, HttpServletRequest request);

    /**
     * 个人积分配置信息删除service统一入口
     * 王燕来 2019.1.7
     * Created by wangyanlai on 20180425.
     * @author wangyanlai
     */
    String deleteConfigure(Map<String, Object> reqMap, Logger logger, HttpServletRequest request);
    /**
     * 个人积分配置信息修改service统一入口
     * 王燕来 2019.1.7
     * Created by wangyanlai on 20180425.
     * @author wangyanlai
     */
    String updConfigure(Map<String, Object> reqMap, Logger logger, HttpServletRequest request);

    /**
     * 个人积分配置信息新增service统一入口
     * 王燕来 2019.1.7
     * Created by wangyanlai on 20180425.
     * @author wangyanlai
     */
    String addConfigure(Map<String, Object> reqMap, Logger logger, HttpServletRequest request);

}
