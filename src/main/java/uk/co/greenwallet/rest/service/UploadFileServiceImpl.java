package uk.co.greenwallet.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uk.co.greenwallet.model.FileInfo;
import uk.co.greenwallet.repository.AttachmentRepository;
import uk.co.greenwallet.util.UuidConverter;

import java.io.IOException;
import java.util.UUID;

@Service("uploadService")
public class UploadFileServiceImpl implements  IUploadFileService{

    @Autowired
    private AttachmentRepository repository;

    @Override
    public UUID upload(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        FileInfo fileInfo = new FileInfo();
        UUID id = UuidConverter.getGuidFromByteArray(bytes);
        fileInfo.setId(id);
        fileInfo.setData(bytes);
        repository.save(fileInfo);
        return id;
    }
}
