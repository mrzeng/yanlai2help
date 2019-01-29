/**
 * <p>Description: </p>
 * <p>Title: UserDao.java</p>  
 * <p>Copyright: Copyright (c) 2018</p>  
 * <p>Company: cei</p>  
 * <p>@author zhaowei</p>
 * <p>@version 2018年4月27日 下午6:29:20</p>
 */
package org.spring.springboot.dao;

import java.util.List;

import org.spring.springboot.domain.Page;
import org.spring.springboot.domain.PageData;
import org.spring.springboot.domain.User;

/**
 * Title: UserDao  
 * Description: 
 * @author zhaowei 
 * @version 2018年4月27日 下午6:29:20
 */
public interface UserDao {
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
}
