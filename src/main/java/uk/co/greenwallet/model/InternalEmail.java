package uk.co.greenwallet.model;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public final class InternalEmail {

	@NotNull
	@Email
	@ApiModelProperty(notes = "The e-mail address of the sender")
	@JsonProperty("from")
	private final String from;

	@NotNull
	@Email
	@ApiModelProperty(notes = "The e-mail address of the receiver")
	@JsonProperty("to")
	private final String to;

	@Email
	@ApiModelProperty(notes = "The e-mail address that should receive replies")
	@JsonProperty("replyTo")
	private final String replyTo;

	@NotNull
	@ApiModelProperty(notes = "The e-mail subject")
	@JsonProperty("subject")
	private final String subject;

	@ApiModelProperty(notes = "The e-mail HTML content")
	@JsonProperty("htmlBody")
	private final String htmlBody;

	@ApiModelProperty(notes = "The list of e-mail file attachment identifiers")
	@JsonProperty("attachments")
	private final List<UUID> attachments;

	@JsonCreator
	public InternalEmail(@JsonProperty("from") String from, @JsonProperty("to") String to,
			@JsonProperty("replyTo") String replyTo, @JsonProperty("subject") String subject,
			@JsonProperty("htmlBody") String htmlBody, @JsonProperty("attachments") List<UUID> attachments) {
		this.from = from;
		this.to = to;
		this.replyTo = replyTo;
		this.subject = subject;
		this.htmlBody = htmlBody;
		this.attachments = attachments;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public String getSubject() {
		return subject;
	}

	public String getHtmlBody() {
		return htmlBody;
	}

	public List<UUID> getAttachments() {
		return attachments;
	}

}
