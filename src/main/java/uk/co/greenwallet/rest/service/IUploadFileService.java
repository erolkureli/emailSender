package uk.co.greenwallet.rest.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface IUploadFileService {
    UUID upload (MultipartFile file) throws IOException;
}
