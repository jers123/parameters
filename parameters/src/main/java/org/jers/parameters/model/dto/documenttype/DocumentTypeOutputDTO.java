package org.jers.parameters.model.dto.documenttype;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jers.parameters.annotation.Dto;
import org.jers.parameters.model.dto.BaseEntityOutputDTO;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static org.jers.parameters.utils.SystemConstants.DOCUMENT_TYPE_ACRONYM_LENGTH;
import static org.jers.parameters.utils.SystemConstants.DOCUMENT_TYPE_NAME_LENGTH;

@NoArgsConstructor
@Getter
@Setter
@Dto
@Schema(name = "Document type Output", description = "Document type entity with all properties")
public class DocumentTypeOutputDTO extends BaseEntityOutputDTO {
    @Schema(defaultValue = "1", minimum = "1", nullable = true, requiredMode=NOT_REQUIRED, description = "Document type identification on registries")
    private Integer idDocumentType;

    @Schema(defaultValue = "Citizenship card", minLength = 1, maxLength = DOCUMENT_TYPE_NAME_LENGTH, requiredMode=NOT_REQUIRED, description = "Is the document type's name or title")
    private String documentTypeName;

    @Schema(title = "Document type acronym", defaultValue = "CC", minLength = 1, maxLength = DOCUMENT_TYPE_ACRONYM_LENGTH, requiredMode=NOT_REQUIRED, description = "Is the document type's acronym")
    private String documentTypeAcronym;
}