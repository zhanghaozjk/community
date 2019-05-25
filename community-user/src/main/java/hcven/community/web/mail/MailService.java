package com.hcven.community.web.mail;

import com.hcven.system.entity.Mail;

/**
 * @author zhanghao
 * @since 2019-03-13
 */
public interface MailService {
    /**
     * 发送简单邮件
     * @param mail
     */
    void sendSimpleMail(Mail mail);

    /**
     * 发送静态资源  一张照片
     * @param mail
     * @throws Exception
     */
    void sendInlineMail(Mail mail) throws Exception;

    /**
     * 发送模板邮件
     * @param mail
     */
    void sendTemplateMail(Mail mail);
}
