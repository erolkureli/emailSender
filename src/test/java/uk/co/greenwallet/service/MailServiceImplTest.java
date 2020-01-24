package uk.co.greenwallet.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.*;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;

import freemarker.template.Configuration;
import uk.co.greenwallet.model.FileInfo;
import uk.co.greenwallet.model.InternalEmail;
import uk.co.greenwallet.repository.AttachmentRepository;
import uk.co.greenwallet.rest.service.MailServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class MailServiceImplTest {

	@Mock
	private JavaMailSender mailSender;

	@Mock
	private Configuration fmConfiguration;

	@Mock
	private AttachmentRepository repository;

	@InjectMocks
	private MailServiceImpl service;

	@Test
	public void whenSendEmailCalledThenMailSenderCalled() throws Exception {
		UUID id1 = UUID.randomUUID();
		UUID id2 = UUID.randomUUID();
		List<UUID> ids = Arrays.asList(id1, id2);
		InternalEmail mail = new InternalEmail("from@from.com", "to@to.com", "to@to.com", "subject", "html body", ids);

		final Properties props = new Properties();
		props.put("mail.smtp.host", "host");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		Session session = Session.getDefaultInstance(props);
		final MimeMessage msg = new MimeMessage(session);

		FileInfo fileInfo = new FileInfo();
		Optional<FileInfo> optionalFileInfo = Optional.of(fileInfo);

		when(mailSender.createMimeMessage()).thenReturn(msg);
		when(repository.findById(any())).thenReturn(optionalFileInfo);

		service.sendEmail(mail);

		verify(mailSender, times(1)).send(any(MimeMessage.class));
	}
}
