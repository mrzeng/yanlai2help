/**
 * <p>Description: </p>
 * <p>Title: BaseController.java</p>  
 * <p>Copyright: Copyright (c) 2018</p>  
 * <p>Company: cei</p>  
 * <p>@author zhaowei</p>
 * <p>@version 2018年4月26日 上午11:31:41</p>
 */
package base.zw.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.springboot.domain.PageData;
import org.spring.springboot.domain.ReturnData;
import org.spring.springboot.zw.util.UuidUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

/**
 * Title: BaseController  
 * Description: 
 * @author zhaowei 
 * @version 2018年4月26日 上午11:31:41
 */
public class BaseController {
protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final long serialVersionUID = 6357869213649815390L;
	
	/**
	 * 得到PageData
	 */
	public PageData getPageData(){
		return new PageData(this.getRequest());
	}
	
	/**
	 * 得到ModelAndView
	 */
	public ModelAndView getModelAndView(){
		return new ModelAndView();
	}
	
	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		
		return request;
	}

	/**
	 * 得到32位的uuid
	 * @return
	 */
	public String get32UUID(){
		
		return UuidUtil.get32UUID();
	}
	
	
	public static void logBefore(Logger logger, String interfaceName){
		logger.info("");
		logger.info("start");
		logger.info(interfaceName);
	}
	
	public static void logAfter(Logger logger){
		logger.info("end");
		logger.info("");
	}
	//map转Json格式的String
	public String mapToJsonString(Map returnMap){
		String jsonReturn ="";
		//JSONObject jsonObject = JSONObject.toJSONString(returnMap).fromObject(returnMap);
		jsonReturn = JSONObject.toJSONString(returnMap);
		return jsonReturn;
	}
	/**
	 * 获取登录用户的IP
	 * @throws Exception 
	 */
	public String getRemortIP() throws Exception {  
		HttpServletRequest request = this.getRequest();
		String ip = request.getHeader("x-forwarded-for"); 
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			  ip = request.getHeader("Proxy-Client-IP");  
			}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			  ip = request.getHeader("WL-Proxy-Client-IP"); 
		}  
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			 ip = request.getRemoteAddr();
		 } 
		return ip;
	}  	
	//utf-8解码
	public String urlDecode(String old,String charset){
		if(old==null){
			return "";
		}
		if(charset!=null && !"".equals(charset)){
		try {
		return URLDecoder.decode(old,charset);
		} catch (UnsupportedEncodingException e) {
		logger.error("Exception:", e);
		}
		}
		 return URLDecoder.decode(old);
	}
	/**
	 * @param map
	 * @author zhaowei
	 * @version 2018年4月30日 上午8:19:29
	 */
	public static void mapToList(Map map) {
	  List listKey = new ArrayList();
	  List listValue = new ArrayList();
	  Iterator it = map.keySet().iterator();
	  while (it.hasNext()) {
	  String key = it.next().toString();
	  listKey.add(key);
	  listValue.add(map.get(key));
	  }
	  for(int i =0 ;i<listKey.size();i++){
	  System.out.print("Key :"+listKey.get(i));
	  System.out.println(" Value :"+listValue.get(i));
	  }
	  }
	
}
