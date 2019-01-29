/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.spring.springboot.zw.service.activemq;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.spring.springboot.domain.MQTypeEnum;
import org.spring.springboot.domain.SendMQModel;
import org.spring.springboot.service.APPService;
import org.spring.springboot.zw.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

//@Component
public class Consumer {
    @Autowired
    private APPService appService;
    /*@JmsListener(destination = "sample.queue")*/
    public void receiveQueue(String text) {
	SendMQModel sendMQModel = new SendMQModel();
	try {
	    // 接收message
	    String msg = text;
	    if (StringUtils.isNotEmpty(msg) && (!"zw".equals(msg))) {
		// 非空情况下进行处理
		JSONObject jsonobject = JSONObject.fromObject(msg);
		sendMQModel = (SendMQModel) JSONObject.toBean(jsonobject, SendMQModel.class);
		String type = sendMQModel.getType();
		if (StringUtils.isNotEmpty(type)) {
		    // QUEUE类型
		    MQTypeEnum typeEnum = MQTypeEnum.valueOf(MQTypeEnum.class, type);
		    switch (typeEnum) {
		    case EXMA:
			// 处理考试的QUEUE
			//appService.processMQData(sendMQModel);
			break;
		    default:
			break;
		    }
		}
		// 更新--处理成功，已处理
		sendMQModel.setStatus("1");
	    }
	}catch (Exception e) {
	    e.printStackTrace();
	    // 处理失败
	    sendMQModel.setStatus("3");
	} finally {
	    System.out.println("here exception");
	    // 处理时间
	    sendMQModel.setProcessTime(DateUtil.getTime());
	    try {
		if(sendMQModel.getId()!=null &&(!"".equals(sendMQModel.getId()))){
		    //appService.updateMQ(sendMQModel);
		}
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }

}
