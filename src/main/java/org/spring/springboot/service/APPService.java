package org.spring.springboot.service;

import org.spring.springboot.domain.City;
import org.spring.springboot.domain.Page;
import org.spring.springboot.domain.PageData;
import org.spring.springboot.domain.SendMQModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 接口通用业务逻辑接口类
 *
 * Created by zhaowei on 20180425.
 */

public interface APPService {

    /**
     * 查询所有的二级分类信息
     */
    public List<PageData> typeLevelONEList(Page page);
    
    /**
     * Description: 查询通知公告
     * @author zhaowei 
     * @version 2018年12月18日 下午6:49:12
     */
    public List<PageData> noticelist(Page page);
}
