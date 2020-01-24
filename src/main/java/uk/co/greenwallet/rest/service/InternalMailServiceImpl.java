package uk.co.greenwallet.rest.service;

import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.greenwallet.model.FileInfo;
import uk.co.greenwallet.model.InternalEmail;
import uk.co.greenwallet.repository.AttachmentRepository;
import uk.co.greenwallet.util.UuidConverter;

@Service("storeService")
public class InternalMailServiceImpl implements IInternalMailService{

	@Autowired
	private AttachmentRepository repository;
	
	@Autowired
	private IMailService emailSender;
	
	@Override
	public List<UUID> sendEmailAndStoreAttachments(InternalEmail sentMail) throws Exception {
		final List<UUID> attachmentUUIDList = new ArrayList<>();
		emailSender.sendEmail(sentMail);
		
		List<FileInfo> files = new ArrayList<FileInfo>();
		sentMail.getAttachments().stream().forEach((attachment)-> {
			attachmentUUIDList.add(attachment);
			
			FileInfo fileInfo = new FileInfo(); 
			fileInfo.setId(attachment);
			fileInfo.setData(UuidConverter.getByteArrayFormUuid(attachment));
			files.add(fileInfo);
			repository.save(fileInfo);
		});
		return attachmentUUIDList;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(UUID.nameUUIDFromBytes(recoverImageFromUrl("http://upload.wikimedia.org/wikipedia/commons/thumb/8/87/Palace_of_Westminster_from_the_dome_on_Methodist_Central_Hall.jpg/1000px-Palace_of_Westminster_from_the_dome_on_Methodist_Central_Hall.jpg")));
	}
	
	public static byte[] recoverImageFromUrl(String urlText) throws Exception {
        URL url = new URL(urlText);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
          
        try (InputStream inputStream = url.openStream()) {
            int n = 0;
            byte [] buffer = new byte[ 1024 ];
            while (-1 != (n = inputStream.read(buffer))) {
                output.write(buffer, 0, n);
            }
        }
      
        return output.toByteArray();
    }

}
