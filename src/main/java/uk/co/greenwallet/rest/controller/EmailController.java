package uk.co.greenwallet.rest.controller;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import uk.co.greenwallet.model.InternalEmail;
import uk.co.greenwallet.rest.service.IMailService;
import uk.co.greenwallet.rest.service.IUploadFileService;

@RestController
@RequestMapping("/email")
public class EmailController {

	@Autowired
	private IMailService mailService;

	@Autowired
	private IUploadFileService uploadFileService;

	@PostMapping("/sendEmail")
	@ResponseStatus(HttpStatus.OK)
	public void send(@RequestBody InternalEmail mail) throws Exception {
		mailService.sendEmail(mail);
	}

	@PostMapping("/upload")
	@ResponseStatus(HttpStatus.CREATED)
	public UUID upload(@RequestParam MultipartFile file) throws IOException {
		return uploadFileService.upload(file);
	}


}
