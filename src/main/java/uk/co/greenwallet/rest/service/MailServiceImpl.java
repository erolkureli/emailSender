package uk.co.greenwallet.rest.service;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import uk.co.greenwallet.model.FileInfo;
import uk.co.greenwallet.model.Image;
import uk.co.greenwallet.model.InternalEmail;
import uk.co.greenwallet.repository.AttachmentRepository;

@Service("mailService")
public class MailServiceImpl implements IMailService {

	@Autowired
	JavaMailSender mailSender;

	@Autowired
	private AttachmentRepository repository;

	@Autowired
	Configuration fmConfiguration;

	@Value("${spring.mail.template}")
	private String templateFileName;

	public void sendEmail(InternalEmail mail) throws Exception {
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		try {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("body", mail.getHtmlBody());

			List<Image> images = new ArrayList<Image>();
			mail.getAttachments().stream().forEach((attachmentId) -> {

			try {
				FileInfo fileInfo = repository.findById(attachmentId).get();
				byte[] byteArray = fileInfo.getData();
				ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
				BufferedImage finalBufferedImage = ImageIO.read(inputStream);
				File attachedFile = new File(attachmentId + ".jpg");
				ImageIO.write(finalBufferedImage, "jpg", attachedFile);

				byte[] fileContent = FileUtils.readFileToByteArray(attachedFile);
				String encodedString = Base64.getEncoder().encodeToString(fileContent);
				
				String img = "data:image/jpg;base64," + encodedString;
				images.add(new Image(img));
			
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