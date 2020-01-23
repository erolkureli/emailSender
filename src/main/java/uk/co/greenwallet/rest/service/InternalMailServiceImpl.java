package uk.co.greenwallet.rest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.greenwallet.model.Attachment;
import uk.co.greenwallet.model.Mail;
import uk.co.greenwallet.repository.AttachmentRepository;

@Service("storeService")
public class InternalMailServiceImpl implements IInternalMailService{

	@Autowired
	private AttachmentRepository repository;
	
	@Override
	public void persist(Mail sentMail) {
		List<UUID> attachmentIds = new ArrayList<UUID>();
		List<Attachment> attachments = new ArrayList<Attachment>();
		sentMail.getAttachments().stream().forEach((a)-> {UUID uuid = UUID.randomUUID(); Attachment attachment = new Attachment(); attachment.setId(uuid); attachments.add(attachment);} );
	//	InternalEmail internalEmail = new InternalEmail(sentMail.getFrom(), sentMail.getTo(), null, sentMail.getSubject(), sentMail.getHtmlBody(), attachmentIds);
		attachments.stream().forEach((u)->{repository.save(u);});
	}

}
