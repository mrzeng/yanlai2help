/**
 * <p>Description: </p>
 * <p>Title: UserService.java</p>  
 * <p>Copyright: Copyright (c) 2018</p>  
 * <p>Company: cei</p>  
 * <p>@author zhaowei</p>
 * <p>@version 2018年4月27日 下午5:30:50</p>
 */
package org.spring.springboot.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.spring.springboot.domain.PageData;
import org.spring.springboot.domain.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Title: UserService  
 * Description: 
 * @author zhaowei 
 * @version 2018年4月27日 下午5:30:50
 */
public interface UserService{
    	/*
	* 通过id获取数据
	*/
	PageData findByUiId(PageData pd);
	/*
	* 通过loginname获取数据
	*/
	PageData findByUId(PageData pd);
	/*
	* 登录判断
	*/
	PageData getUserByNameAndPwd(PageData pd);
	/*
	* 保存用户登陆信息
	*/
	public void saveULogin(PageData pd);
	/**
	 * 修改用户信息
	 * @throws Exception
	 * @返回值：void
	 * @创建时间：2018年3月20日 下午1:22:09
	 */
	public void editUserPro(PageData pd);

	/**
	 * 登录积分系统后台管理系统
	 * @throws Exception
	 * @返回值：void
	 * @创建时间：2019年3月20日 下午1:22:09
	 */
	String loginIn(Map<String, Object> reqMap, Logger logger, HttpServletRequest request);

	/**
	 * 退出积分系统后台管理系统
	 * @throws Exception
	 * @返回值：void
	 * @创建时间：2019年3月20日 下午1:22:09
	 */
	String loginOut(Map<String, Object> reqMap, Logger logger, HttpServletRequest request);

	/**
	 * 修改积分系统后台管理系统用户信息
	 * @throws Exception
	 * @返回值：void
	 * @创建时间：2019年1月20日 下午1:22:09
	 */
	String updUser(Map<String, Object> reqMap, Logger logger, HttpServletRequest request);

	/**
	 * 增加积分系统后台管理系统用户信息
	 * @throws Exception
	 * @返回值：void
	 * @创建时间：2019年3月20日 下午1:22:09
	 */
	String addUser(Map<String, Object> reqMap, Logger logger, HttpServletRequest request);

	/**
	 * 删除积分系统后台管理系统用户信息
	 * @throws Exception
	 * @返回值：void
	 * @创建时间：2019年3月20日 下午1:22:09
	 */
	String deleteUser(Map<String, Object> reqMap, Logger logger, HttpServletRequest request);
}
