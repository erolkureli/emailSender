package uk.co.greenwallet.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import uk.co.greenwallet.model.InternalEmail;
import uk.co.greenwallet.rest.service.IInternalMailService;

import java.util.UUID;

@RunWith(SpringRunner.class)
@WebMvcTest()
public class EmailControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IInternalMailService internalMailService;

	@Test
	public void getAccountTest() throws Exception {
		// Arrange
		ObjectMapper mapper = new ObjectMapper();
		List<UUID> ids = new ArrayList<>();
		InternalEmail mail = new InternalEmail("from@from.com","to@to.com","to@to.com", "subject","html body", ids);
		String requestJson = mapper.writeValueAsString(mail);

		// Assert
		mockMvc.perform(post("/email").contentType("application/json").content(requestJson)).andExpect(status().isCreated());

		// Assert
		verify(internalMailService, times(1)).sendEmailAndStoreAttachments(any(InternalEmail.class));
	}

}
