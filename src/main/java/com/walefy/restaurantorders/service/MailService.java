package com.walefy.restaurantorders.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class MailService {
  private final JavaMailSender mailSender;
  private final TemplateEngine templateEngine;

  @Value("${spring.mail.username}")
  private String fromMail;

  @Autowired
  public MailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
    this.mailSender = mailSender;
    this.templateEngine = templateEngine;
  }

  public void sendResetPasswordMail(String name, String targetMail) throws MessagingException {
    Map<String, String> variables = Map.of("name", name);
    String resetPasswordTemplate = prepareTemplate("resetPasswordTemplate",variables);

    sendMail(targetMail, "recuperação de senha", resetPasswordTemplate);
  }

  @Async
  private void sendMail(String targetMail, String subject, String content) throws MessagingException {
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

    mimeMessageHelper.setFrom(fromMail);
    mimeMessageHelper.setTo(targetMail);
    mimeMessageHelper.setSubject(subject);

    Context context = new Context();
    context.setVariable("content", content);

    String processedString = templateEngine.process("base", context);
    mimeMessageHelper.setText(processedString);
  }

  private String prepareTemplate(String templateName, Map<String, String> templateVariables) {
    Context context = new Context();

    for (String variable : templateVariables.keySet()) {
      String value = templateVariables.get(variable);
      context.setVariable(variable, value);
    }

    return templateEngine.process(templateName, context);
  }
}
