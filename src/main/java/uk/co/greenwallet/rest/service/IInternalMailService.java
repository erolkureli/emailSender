package uk.co.greenwallet.rest.service;

import java.util.List;
import java.util.UUID;

import uk.co.greenwallet.model.InternalEmail;

public interface IInternalMailService {

	List<UUID> sendEmailAndStoreAttachments(InternalEmail sentMail) throws Exception;

}
