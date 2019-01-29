/**
 * <p>Description: </p>
 * <p>Title: APPServiceImpl.java</p>  
 * <p>Copyright: Copyright (c) 2018</p>  
 * <p>Company: cei</p>  
 * <p>@author zhaowei</p>
 * <p>@version 2018年4月26日 上午8:31:56</p>
 */
package org.spring.springboot.service.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.font.TextAttribute;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.spring.springboot.dao.APPDao;
import org.spring.springboot.dao.DaoSupport;
import org.spring.springboot.domain.CertificateTypeEnum;
import org.spring.springboot.domain.City;
import org.spring.springboot.domain.ExamMQModel;
import org.spring.springboot.domain.Page;
import org.spring.springboot.domain.PageData;
import org.spring.springboot.domain.RecommendResourceTypeEnum;
import org.spring.springboot.domain.SendMQModel;
import org.spring.springboot.service.APPService;
import org.spring.springboot.zw.service.activemq.Producer;
import org.spring.springboot.zw.util.Const;
import org.spring.springboot.zw.util.DateUtil;
import org.spring.springboot.zw.util.MapUtil;
import org.spring.springboot.zw.util.PathUtil;
import org.spring.springboot.zw.util.StringUtil;
import org.spring.springboot.zw.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Qualifier;
import javax.jms.Destination;
import javax.swing.ImageIcon;

/**
 * Title: APPServiceImpl Description:
 * 
 * @author zhaowei
 * @version 2018年4月26日 上午8:31:56
 */
@Service
public class APPServiceImpl implements APPService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;
    //20181201注释掉mq相关配置
    //@Resource(name = "producer")
    private Producer producer;

    /**
     * 查询所有的二级分类信息
     */
    @Override
    public List<PageData> typeLevelONEList(Page page) {
	List<PageData> pd = new ArrayList<PageData>();
	try {
	    pd = (List<PageData>) dao.findForList("CoursewareTypeMapper.typeLevelONEListPage", page);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return pd;
    }
    /* 
     * 通知公告列表
     * time 2018年12月18日 下午6:49:12
     */
	@Override
    public List<PageData> noticelist(Page page) {
	List<PageData> pd = new ArrayList<PageData>();
	try {
	    pd = (List<PageData>) dao.findForList("NoticeMapper.noticeList", page);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return pd;
    }
}
