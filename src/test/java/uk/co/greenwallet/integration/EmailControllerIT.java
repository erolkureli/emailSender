package uk.co.greenwallet.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import uk.co.greenwallet.model.FileInfo;
import uk.co.greenwallet.model.InternalEmail;
import uk.co.greenwallet.repository.AttachmentRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class EmailControllerIT {
	
	UUID id;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
    private AttachmentRepository repository;
	
	@Before
	public void initialize() throws IOException {
		File uploadedFile = new File("london.jpg");
		byte[] bytes = new byte[(int) uploadedFile.length()];
		FileInputStream fis = new FileInputStream(uploadedFile);
		fis.read(bytes);
		fis.close();
		
		FileInfo fileInfo = new FileInfo();
        UUID id = UUID.randomUUID();
        fileInfo.setId(id);
        fileInfo.setData(bytes);
        repository.save(fileInfo);
        this.id = id;
	}
	
	@After
	public void reset() {
		repository.deleteById(this.id);
	}
	
	@Test
	public void givenAccountExist_WhenGetAcount_ThenSuccess() {

		// Arrange
		UUID id1 = UUID.randomUUID();
		UUID id2 = UUID.randomUUID();
		List<UUID> ids = Arrays.asList(id1, id2);
		InternalEmail mail = new InternalEmail("from@from.com", "to@to.com", "to@to.com", "subject", "html body", ids);
		// Act
		ResponseEntity<Void> response = restTemplate.postForEntity("/email/sendEmail", mail ,Void.class);

		// Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

}
