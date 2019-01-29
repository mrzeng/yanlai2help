/**
 * <p>Description: </p>
 * <p>Title: ExamMQModel.java</p>  
 * <p>Copyright: Copyright (c) 2018</p>  
 * <p>Company: cei</p>  
 * <p>@author zhaowei</p>
 * <p>@version 2018年5月22日 下午3:04:50</p>
 */
package org.spring.springboot.domain;

import net.sf.json.JSONObject;

/**
 * Title: ExamMQModel  
 * Description: 
 * @author zhaowei 
 * @version 2018年5月22日 下午3:04:50
 */
public class ExamMQModel {
    private String answerStr;// 答案
	private String ID;// 试卷id
	private String usedTime;// 用时
	private String USERID;// 用户id

	public ExamMQModel() {
		super();
	}

	public ExamMQModel(String answerStr, String iD, String usedTime,
			String uSERID) {
		super();
		this.answerStr = answerStr;
		ID = iD;
		this.usedTime = usedTime;
		USERID = uSERID;
	}

	public String getAnswerStr() {
		return answerStr;
	}

	public void setAnswerStr(String answerStr) {
		this.answerStr = answerStr;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(String usedTime) {
		this.usedTime = usedTime;
	}

	public String getUSERID() {
		return USERID;
	}

	public void setUSERID(String uSERID) {
		USERID = uSERID;
	}

	/**
	 * 将json转换成字符串
	 * @返回值：String
	 * @创建时间：2018年1月31日 上午11:32:20
	 */
	public String toJson() {
		return JSONObject.fromObject(this).toString();
	}

	/**
	 * 将object转换成PageData
	 * @返回值：PageData
	 * @创建时间：2018年1月31日 下午12:06:07
	 */
	public PageData getPd() {
		PageData pd = new PageData();
		pd.put("ID", this.ID);
		pd.put("answerStr", this.answerStr);
		pd.put("usedTime", this.usedTime);
		pd.put("USERID", this.USERID);
		return pd;
	}
}
