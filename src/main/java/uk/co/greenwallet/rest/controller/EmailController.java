package uk.co.greenwallet.rest.controller;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import freemarker.template.TemplateException;
import uk.co.greenwallet.model.Mail;
import uk.co.greenwallet.rest.service.IInternalMailService;
import uk.co.greenwallet.rest.service.IMailService;

@RestController
@RequestMapping("/email")
public class EmailController {
	
	@Autowired
	private IMailService emailSender;
	
	@Autowired
	private IInternalMailService internalMailService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void send(@RequestBody Mail mail) throws MessagingException, IOException, TemplateException {
		Mail sentMail = emailSender.sendEmail(mail);
		
		internalMailService.persist(sentMail);
		
	}
}
