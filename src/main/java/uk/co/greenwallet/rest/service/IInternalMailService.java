package uk.co.greenwallet.rest.service;

import uk.co.greenwallet.model.Mail;

public interface IInternalMailService {

	void persist(Mail sentMail);

}
