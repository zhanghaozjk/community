package com.hcven.system.mail;

import com.hcven.core.constant.MailConstant;
import com.hcven.core.ret.RetResponse;
import com.hcven.core.ret.RetResult;
import com.hcven.system.entity.Mail;
import com.hcven.utils.ApplicationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhanghao
 * @since 2019-03-13
 */
@RestController
@RequestMapping(value = "/register")
public class MailController {

    @Resource
    private MailService mailService;

    /**
     * 发送注册验证码
     * @param mailTo
     * @return 验证码
     * @throws Exception
     */
    @GetMapping(value = "/sendTemplateMail")
    public RetResult<String> sendTemplateMail(String mailTo) throws Exception {
        Mail mail = new Mail();
        String[] to = {mailTo};
        String identifyCode = ApplicationUtils.getNumStringRandom(6);
        mail.setSubject(MailConstant.TEMPLATE_SUBJECT);
        mail.setTemplateName(MailConstant.REGISTER_TEMPLATE);
        Map<String,String> map = new HashMap<>(16);
        map.put("identifyCode",identifyCode);
        map.put("to", mailTo);
        mail.setTemplateModel(map);
        mail.setTo(to);
        mailService.sendTemplateMail(mail);

        return RetResponse.makeOKRsp(identifyCode);
    }

    @PostMapping("/sendAttachmentsMail")
    public RetResult<String> sendAttachmentsMail(Mail mail, HttpServletRequest request) throws Exception {
        mail.setSubject("测试附件");
        mailService.sendAttachmentsMail(mail, request);
        return RetResponse.makeOKRsp();
    }
}
