package uk.co.greenwallet.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import uk.co.greenwallet.model.FileInfo;
import uk.co.greenwallet.repository.AttachmentRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service("uploadService")
public class UploadFileServiceImpl implements  IUploadFileService{

    @Autowired
    private AttachmentRepository repository;

    @Override
    public UUID upload(MultipartFile file) throws IOException {
    	Path copyLocation = Paths.get("./" + StringUtils.cleanPath(file.getOriginalFilename()));
    	Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
    	File uploadedFile = copyLocation.toFile();
    	byte[] bytes = new byte[(int) uploadedFile.length()];

		FileInputStream fis = new FileInputStream(uploadedFile);
		fis.read(bytes);
		fis.close();
    	
        FileInfo fileInfo = new FileInfo();
        UUID id = UUID.randomUUID();
        fileInfo.setId(id);
        fileInfo.setData(bytes);
        repository.save(fileInfo);
        return id;
    }
}
