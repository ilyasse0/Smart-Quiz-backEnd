package net.bensitel.smartquiz.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bensitel.smartquiz.util.EmailTemplateName;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;


    @Async
    public void sendEmail(
            String to, String userName, EmailTemplateName emailTemplate, String confirmationUrl, String activationCode, String subject) throws MessagingException {
        log.debug("Starting email sending process...");
        String templateName;
        if (emailTemplate == null) {
            templateName = "confirm-email";
        } else {
            templateName = emailTemplate.getName();
        }
        log.debug("Using template: {}", templateName);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name()
        );
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", userName);
        properties.put("confirmationUrl", confirmationUrl);
        properties.put("activation_code", activationCode);
        log.debug("Email properties set: {}", properties);

        Context context = new Context();
        context.setVariables(properties);

        helper.setFrom("noreply@bensitel.com");
        helper.setTo(to);
        helper.setSubject(subject);
        String template = templateEngine.process(templateName, context);
        log.debug("Template processed, length: {}", template.length());
        helper.setText(template, true);
        try {
            mailSender.send(mimeMessage);
            log.info("Email sent successfully to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send email to: {}", to, e);
            throw e;
        }
    }

}
