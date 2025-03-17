package org.jers.parameters.utils.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jers.parameters.model.dto.BaseEntityOutputDTO;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@Component
@Schema(name = "Reply message list", description = "Show the api response with the entity list processed")
public class ReplyMessageList<BO extends BaseEntityOutputDTO> extends ReplyMessage {
    @Schema(description = "Entities list created, find or updated", defaultValue = "{}", requiredMode=REQUIRED)
    private List<BO> response;
}