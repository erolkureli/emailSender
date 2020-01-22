package uk.co.greenwallet.model;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public final class InternalEmail {
	@NotNull
	@Email
	@ApiModelProperty(notes = "The e-mail address of the sender")
	private final String from;
	@NotNull
	@Email
	@ApiModelProperty(notes = "The e-mail address of the receiver")
	private final String to;
	@Email
	@ApiModelProperty(notes = "The e-mail address that should receive replies")
	private final String replyTo;
	@NotNull
	@ApiModelProperty(notes = "The e-mail subject")
	private final String subject;
	@ApiModelProperty(notes = "The e-mail HTML content")
	private final String htmlBody;
	@ApiModelProperty(notes = "The list of e-mail file attachment identifiers")
	private final List<UUID> attachments;

	public InternalEmail(String from, String to, String replyTo, String subject, String htmlBody,
			List<UUID> attachments) {
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

	public String getReplyTo() {
		return replyTo;
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
