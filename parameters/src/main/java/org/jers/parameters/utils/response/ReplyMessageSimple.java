package org.jers.parameters.utils.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jers.parameters.model.dto.BaseEntityOutputDTO;
import org.springframework.stereotype.Component;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@Component
@Schema(name = "Reply message simple", description = "Show the api response with the entity processed")
public class ReplyMessageSimple<BO extends BaseEntityOutputDTO> extends ReplyMessage {
    @Schema(description = "Entity created, find or updated", requiredMode=REQUIRED)
    private BO response;
}