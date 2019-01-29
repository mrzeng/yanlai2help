package org.spring.springboot.service;

import org.slf4j.Logger;
import org.spring.springboot.domain.Page;
import org.spring.springboot.domain.PageData;
import org.spring.springboot.domain.PerIntegrationBasis;
import org.spring.springboot.domain.PerIntegrationLog;
import sun.security.krb5.internal.PAData;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 接口通用业务逻辑接口类(该service专用于用户积分查询)
 *
 * Created by wangyanlai on 20180425.
 * @author wangyanlai
 */
public interface IntegrationQryService {

    /**
     * 个人积分信息查询service统一入口
     * 王燕来 2018.12。21
     * Created by wangyanlai on 20180425.
     * @author wangyanlai
     */
    String qryIntegration(Map<String, Object> reqMap, Logger logger, HttpServletRequest request);

    /**
     * 个人积分配置信息查询service统一入口
     * 王燕来 2019.1.7
     * Created by wangyanlai on 20180425.
     * @author wangyanlai
     */
    String queryConfigure(Map<String, Object> reqMap, Logger logger, HttpServletRequest request);



}
