package uk.co.greenwallet.rest.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import uk.co.greenwallet.model.Image;
import uk.co.greenwallet.model.InternalEmail;
import uk.co.greenwallet.util.FileProducer;
import uk.co.greenwallet.util.UuidConverter;

@Service("mailService")
public class MailServiceImpl implements IMailService {

	@Autowired
	JavaMailSender mailSender;

	@Autowired
	Configuration fmConfiguration;

	@Value("${spring.mail.template}")
	private String templateFileName;

	public void sendEmail(InternalEmail mail) throws Exception {
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		try {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("to", mail.getTo());
			model.put("from", mail.getFrom());
			model.put("body", mail.getHtmlBody());

			List<Image> images = new ArrayList<Image>();
			mail.getAttachments().stream().forEach((attachment) -> {
				try {
					File file = new File(attachment + ".jpg");
					byte[] imgAsByteArray = UuidConverter.getByteArrayFormUuid(attachment);

					OutputStream os = new FileOutputStream(file);
					os.write(imgAsByteArray);

					FileProducer.printContent(file);
					os.close();
					images.add(new Image(file.getAbsolutePath()));
				} catch (Exception e) {
				}
			});
			model.put("images", images);

			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			mimeMessageHelper.setSubject(mail.getSubject());
			mimeMessageHelper.setFrom(mail.getFrom());
			mimeMessageHelper.setTo(mail.getTo());
			String createdHtmlBodyByTemplate = geContentFromTemplate(model);
			mimeMessageHelper.setText(createdHtmlBodyByTemplate, true);

			mailSender.send(mimeMessageHelper.getMimeMessage());
		} catch (Exception e) {

		}
	}

	public String geContentFromTemplate(Map<String, Object> model) {
		StringBuffer content = new StringBuffer();

		try {
			Template t = fmConfiguration.getTemplate(templateFileName);
			content.append(FreeMarkerTemplateUtils.processTemplateIntoString(t, model));
		} catch (Exception e) {

		}
		return content.toString();
	}

}