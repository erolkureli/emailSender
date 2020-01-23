package uk.co.greenwallet.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import uk.co.greenwallet.model.Attachment;

@Repository
public interface AttachmentRepository extends CrudRepository<Attachment, UUID>{

}
