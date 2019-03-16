package com.hcven.system.mail;

import com.hcven.system.entity.Mail;

import javax.servlet.http.HttpServletRequest;

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
     * 发送带附件的邮件
     * @param mail
     * @param request
     */
    void sendAttachmentsMail(Mail mail, HttpServletRequest request);

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
