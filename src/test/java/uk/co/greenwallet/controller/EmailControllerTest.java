package uk.co.greenwallet.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.multipart.MultipartFile;
import uk.co.greenwallet.model.InternalEmail;
import uk.co.greenwallet.rest.service.IMailService;
import uk.co.greenwallet.rest.service.IUploadFileService;

import java.util.UUID;

@RunWith(SpringRunner.class)
@WebMvcTest()
public class EmailControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IMailService mailService;

	@MockBean
	private IUploadFileService uploadFileService;

	@Test
	public void testSendEmail() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		List<UUID> ids = new ArrayList<>();
		InternalEmail mail = new InternalEmail("from@from.com","to@to.com","to@to.com", "subject","html body", ids);
		String requestJson = mapper.writeValueAsString(mail);
		mockMvc.perform(post("/email/sendEmail").contentType("application/json").content(requestJson)).andExpect(status().isOk());
		verify(mailService, times(1)).sendEmail(any(InternalEmail.class));
	}

	@Test
	public void testUpload() throws Exception {
		String fileName = "test.txt";
		File file = new File(fileName);
		//delete if exits
		file.delete();
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file",fileName,
				"text/plain", "test data".getBytes());
		MockHttpServletRequestBuilder builder =
				MockMvcRequestBuilders.multipart("/email/upload")
						.file(mockMultipartFile);

		this.mockMvc.perform(builder).andExpect(status().isCreated())
				.andDo(MockMvcResultHandlers.print());
		verify(uploadFileService, times(1)).upload(any(MultipartFile.class));
	}

}
