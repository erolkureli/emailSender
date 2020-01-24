package uk.co.greenwallet.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Matchers.isA;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import uk.co.greenwallet.model.FileInfo;
import uk.co.greenwallet.model.InternalEmail;
import uk.co.greenwallet.repository.AttachmentRepository;
import uk.co.greenwallet.rest.service.IMailService;
import uk.co.greenwallet.rest.service.InternalMailServiceImpl;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class InternalMailServiceImplTest {
	
	@Mock
	private AttachmentRepository repository;
	
	@Mock
	private IMailService emailSender;

	@InjectMocks
	private InternalMailServiceImpl service;
	
	@Test
	public void whenSendEmailAndStoreAttachmentsCalledThenRepositorySaveCalled() throws Exception {
		UUID id1 = UUID.randomUUID();
		UUID id2 = UUID.randomUUID();
		List<UUID> ids = Arrays.asList(id1, id2);
		InternalEmail mail = new InternalEmail("from@from.com","to@to.com","to@to.com", "subject","html body", ids);
		
		doNothing().when(emailSender).sendEmail(isA(InternalEmail.class));
		
		service.sendEmailAndStoreAttachments(mail);
		verify(repository, times(2)).save(any(FileInfo.class));
	}
}
