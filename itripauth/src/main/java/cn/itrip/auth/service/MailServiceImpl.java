package cn.itrip.auth.service;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("mailService")
public class MailServiceImpl implements  MailService {
    @Resource
    private SimpleMailMessage simpleMailMessage;
    @Resource
    private MailSender mailSender;
    @Override
    public void sendActivationMail(String mailTo, String activationCode) {
        simpleMailMessage.setTo(mailTo);
        simpleMailMessage.setText("您的激活码是：" + activationCode);
        mailSender.send(simpleMailMessage);

    }
}
