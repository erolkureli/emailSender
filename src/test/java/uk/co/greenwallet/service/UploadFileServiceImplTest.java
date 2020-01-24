package uk.co.greenwallet.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.mock.web.MockMultipartFile;

import uk.co.greenwallet.model.FileInfo;

import uk.co.greenwallet.repository.AttachmentRepository;

import uk.co.greenwallet.rest.service.UploadFileServiceImpl;


import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class UploadFileServiceImplTest {
	
	@Mock
	private AttachmentRepository repository;
	


	@InjectMocks
private UploadFileServiceImpl service;
	
	@Test
	public void whenUploadIsCalledThenRepositorySaveCalled() throws Exception {


		MockMultipartFile multipartFile = new MockMultipartFile("file", "london.jpg",
				"image/jpeg", "test image content".getBytes());

		service.upload(multipartFile);
		verify(repository, times(1)).save(any(FileInfo.class));
	}
}
