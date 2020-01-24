package uk.co.greenwallet.rest.service;

import uk.co.greenwallet.model.InternalEmail;

public interface IMailService {
	void sendEmail(InternalEmail mail) throws Exception;
}
