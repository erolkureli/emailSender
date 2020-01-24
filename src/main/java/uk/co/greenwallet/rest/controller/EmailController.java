package uk.co.greenwallet.rest.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import uk.co.greenwallet.model.InternalEmail;
import uk.co.greenwallet.rest.service.IInternalMailService;

@RestController
@RequestMapping("/email")
public class EmailController {

	@Autowired
	private IInternalMailService internalMailService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public List<UUID> send(@RequestBody InternalEmail mail) throws Exception {
		return internalMailService.sendEmailAndStoreAttachments(mail);
	}
}
