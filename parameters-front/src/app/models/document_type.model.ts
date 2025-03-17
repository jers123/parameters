import { BaseEntityInput, BaseEntityOutput } from "./base.model";

export interface DocumentTypeCreate extends BaseEntityInput {
	documentTypeName: string;
	documentTypeAcronym: string;
}

export interface DocumentTypeUpdate extends DocumentTypeCreate {
	idDocumentType: number;
}

export interface DocumentTypeOutput extends DocumentTypeUpdate, BaseEntityOutput {

}