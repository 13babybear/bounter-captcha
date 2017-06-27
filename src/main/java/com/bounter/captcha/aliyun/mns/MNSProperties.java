package com.bounter.captcha.aliyun.mns;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 阿里云短信MNS
 * @author Administrator
 *
 */
@Component
public class MNSProperties {
	@Value("${mns.accessid}")
	private String accessId;
	
	@Value("${mns.accesskey}")
	private String accessKey;
	
	@Value("${mns.endpoint}")
	private String endPoint;
	
	@Value("${mns.topic}")
	private String topic;
	
	@Value("${mns.templatecode}")
	private String templateCode;
	
	@Value("${mns.signname}")
	private String signName;

	public String getAccessId() {
		return accessId;
	}

	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}
	
}
