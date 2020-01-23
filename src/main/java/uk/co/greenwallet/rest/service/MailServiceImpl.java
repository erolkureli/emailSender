package uk.co.greenwallet.rest.service;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import uk.co.greenwallet.model.Mail;

@Service("mailService")
public class MailServiceImpl implements IMailService {

	@Autowired
	JavaMailSender mailSender;

	@Autowired
	Configuration fmConfiguration;

	public Mail sendEmail(Mail mail) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		try {

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("to", mail.getTo());
			model.put("from", mail.getFrom());

			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			mimeMessageHelper.setSubject(mail.getSubject());
			mimeMessageHelper.setFrom(mail.getFrom());
			mimeMessageHelper.setTo(mail.getTo());
			mail.setHtmlBody(geContentFromTemplate(model));
			mimeMessageHelper.setText(mail.getHtmlBody(), true);

			mail.getAttachments().stream().forEach((f) -> {
				FileSystemResource file = new FileSystemResource(f);
				try {
					mimeMessageHelper.addAttachment(file.getFilename(), file);
				} catch (MessagingException e) {
				}
			});

			mailSender.send(mimeMessageHelper.getMimeMessage());
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return mail;
	}

	public String geContentFromTemplate(Map<String, Object> model) {
		StringBuffer content = new StringBuffer();

		try {
			Template t = fmConfiguration.getTemplate("email-template.txt");
			content.append(FreeMarkerTemplateUtils.processTemplateIntoString(t, model));
		} catch (Exception e) {

		}
		return content.toString();
	}

}