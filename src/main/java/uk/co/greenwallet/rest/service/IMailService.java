package uk.co.greenwallet.rest.service;

import uk.co.greenwallet.model.InternalEmail;

public interface IMailService {
	public void sendEmail(InternalEmail mail) throws Exception;
}
