/**
 * <p>Description: </p>
 * <p>Title: SendMQModel.java</p>  
 * <p>Copyright: Copyright (c) 2018</p>  
 * <p>Company: cei</p>  
 * <p>@author zhaowei</p>
 * <p>@version 2018年5月9日 上午10:19:53</p>
 */
package org.spring.springboot.domain;

import com.alibaba.fastjson.JSONObject;

/**
 * Title: SendMQModel Description:
 * 
 * @author zhaowei
 * @version 2018年5月9日 上午10:19:53
 */
public class SendMQModel {
    private String id;// 信息id
    private String content;// mq内容
    private String sendTime;// 发送时间
    private String userId;// 发送人
    private String type;// queue类型
    private String status;// 状态 0为未处理，1为处理, 2为发送失败
    private String processTime;// 处理时间

    public SendMQModel() {
	super();
    }

    public SendMQModel(String id, String content, String sendTime, String userId, String type, String status,
	    String processTime) {
	super();
	this.id = id;
	this.content = content;
	this.sendTime = sendTime;
	this.userId = userId;
	this.type = type;
	this.status = status;
	this.processTime = processTime;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public String getProcessTime() {
	return processTime;
    }

    public void setProcessTime(String processTime) {
	this.processTime = processTime;
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getContent() {
	return content;
    }

    public void setContent(String content) {
	this.content = content;
    }

    public String getSendTime() {
	return sendTime;
    }

    public void setSendTime(String sendTime) {
	this.sendTime = sendTime;
    }

    public String getUserId() {
	return userId;
    }

    public void setUserId(String userId) {
	this.userId = userId;
    }
    
    public String toJson() {
	return JSONObject.toJSONString(this);
    }
}
