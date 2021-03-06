package com.bounter.captcha.aliyun.mns;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudTopic;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.model.BatchSmsAttributes;
import com.aliyun.mns.model.MessageAttributes;
import com.aliyun.mns.model.RawTopicMessage;

@Component
public class SMSUtil {
	
	//注入MNS配置类
	@Autowired
	private MNSProperties mnsProps;
	
	private static SMSUtil smsUtil;
	
	@PostConstruct
	public void init() {
		smsUtil = this;
		smsUtil.mnsProps= this.mnsProps;
	}
	
	private static void sendMsg() {
		/**
		 * Step 1. 获取主题引用
		 */
		CloudAccount account = new CloudAccount(smsUtil.mnsProps.getAccessId(), smsUtil.mnsProps.getAccessKey(),
				smsUtil.mnsProps.getEndPoint());
		MNSClient client = account.getMNSClient();
		CloudTopic topic = client.getTopicRef(smsUtil.mnsProps.getTopic());
		/**
		 * Step 2. 设置SMS消息体（必须）
		 *
		 * 注：目前暂时不支持消息内容为空，需要指定消息内容，不为空即可。
		 */
		RawTopicMessage msg = new RawTopicMessage();
		msg.setMessageBody("sms-message");
		/**
		 * Step 3. 生成SMS消息属性
		 */
		MessageAttributes messageAttributes = new MessageAttributes();
		BatchSmsAttributes batchSmsAttributes = new BatchSmsAttributes();
		// 3.1 设置发送短信的签名（SMSSignName）
		batchSmsAttributes.setFreeSignName(smsUtil.mnsProps.getSignName());
		// 3.2 设置发送短信使用的模板（SMSTempateCode）
		batchSmsAttributes.setTemplateCode(smsUtil.mnsProps.getTemplateCode());
		// 3.3 设置发送短信所使用的模板中参数对应的值（在短信模板中定义的，没有可以不用设置）
		BatchSmsAttributes.SmsReceiverParams smsReceiverParams = new BatchSmsAttributes.SmsReceiverParams();
		smsReceiverParams.setParam("code", "6379");
		// 3.4 增加接收短信的号码
		batchSmsAttributes.addSmsReceiver("15017550910", smsReceiverParams);
		messageAttributes.setBatchSmsAttributes(batchSmsAttributes);
		try {
			/**
			 * Step 4. 发布SMS消息
			 */
			topic.publishMessage(msg, messageAttributes);
		} catch (ServiceException se) {
			System.out.println(se.getErrorCode() + se.getRequestId());
			System.out.println(se.getMessage());
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		client.close();
	}

	public static void main(String[] args) {
		sendMsg();
	}
}
