package org.jers.parameters.model.dto.documenttype;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jers.parameters.annotation.Dto;
import org.jers.parameters.model.dto.BaseEntityInputDTO;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;
import static org.jers.parameters.utils.Constants.DOCUMENT_TYPE_ACRONYM_NOT_BLANK;
import static org.jers.parameters.utils.Constants.DOCUMENT_TYPE_ACRONYM_NOT_NULL;
import static org.jers.parameters.utils.Constants.DOCUMENT_TYPE_ACRONYM_SIZE;
import static org.jers.parameters.utils.Constants.DOCUMENT_TYPE_NAME_NOT_BLANK;
import static org.jers.parameters.utils.Constants.DOCUMENT_TYPE_NAME_NOT_NULL;
import static org.jers.parameters.utils.Constants.DOCUMENT_TYPE_NAME_SIZE;
import static org.jers.parameters.utils.SystemConstants.DOCUMENT_TYPE_ACRONYM_LENGTH;
import static org.jers.parameters.utils.SystemConstants.DOCUMENT_TYPE_NAME_LENGTH;

@NoArgsConstructor
@Getter
@Setter
@Dto
@Schema(name = "Document type create", description = "Document type entity with all new properties to save")
public class DocumentTypeCreateDTO extends BaseEntityInputDTO {
    @NotNull(message = DOCUMENT_TYPE_NAME_NOT_NULL)
    @NotBlank(message = DOCUMENT_TYPE_NAME_NOT_BLANK)
    @Size(min = 1, max = DOCUMENT_TYPE_NAME_LENGTH, message = DOCUMENT_TYPE_NAME_SIZE)
    @Schema(defaultValue = "Citizenship card", minLength = 1, maxLength = DOCUMENT_TYPE_NAME_LENGTH, requiredMode=REQUIRED, description = "Is the document type's name or title")
    private String documentTypeName;

    @NotNull(message = DOCUMENT_TYPE_ACRONYM_NOT_NULL)
    @NotBlank(message = DOCUMENT_TYPE_ACRONYM_NOT_BLANK)
    @Size(min = 1, max = DOCUMENT_TYPE_ACRONYM_LENGTH, message = DOCUMENT_TYPE_ACRONYM_SIZE)
    @Schema(defaultValue = "CC", minLength = 1, maxLength = DOCUMENT_TYPE_ACRONYM_LENGTH, requiredMode=REQUIRED, description = "Is the document type's acronym")
    private String documentTypeAcronym;
}