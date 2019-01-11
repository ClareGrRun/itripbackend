package cn.itrip.auth.service;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import org.springframework.stereotype.Service;

import java.util.HashMap;
@Service("smsService")
public class SmsServiceImpl implements SmsService{
    @Override
    public void send(String to, String templateId, String[] datas) throws Exception {
        CCPRestSmsSDK sdk = new CCPRestSmsSDK();
        sdk.init("app.cloopen.com","8883");
        sdk.setAccount("8a216da868125936016825acf48207cf","5e0468a79b3f498abf74414da3176a93");
        sdk.setAppId("8a216da868125936016825acf4cd07d5");
        HashMap result = sdk.sendTemplateSMS(to,templateId,datas);
        if("000000".equals(result.get("statusCode"))){
            System.out.println("短信发送成功!");
        }else{
            throw new  Exception(result.get("statusCode").toString()+":"+result.get("statusMessage").toString());
        }
    }
}
