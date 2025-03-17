package org.jers.parameters.utils.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;
import static org.jers.parameters.utils.SystemConstants.PARAMETERS_PATH;

@Getter
@Setter
@Schema(hidden = true)
public abstract class ReplyMessage {
    @Schema(description = "Is the full path of request", defaultValue = PARAMETERS_PATH, requiredMode=REQUIRED)
    private String uri;

    @Schema(description = "Is the http status of request", defaultValue = "400", requiredMode=REQUIRED)
    private HttpStatus httpStatus;

    @Schema(description = "Show if the response is error", defaultValue = "true", requiredMode=REQUIRED)
    private Boolean error;

    @Schema(description = "Is a list of messages with happen", defaultValue = "{response1, response2}", requiredMode=REQUIRED)
    private List<String> message;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Schema(description = "Is the full date time of request", defaultValue = "2024-06-16T13:40:50.661", requiredMode=REQUIRED)
    private LocalDateTime date;
}