package org.castor.employee.utils.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public abstract class ReplyMessage {
	private String uri;
	private HttpStatus httpStatus;
	private Boolean error;
	private List<String> message;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime date;
}