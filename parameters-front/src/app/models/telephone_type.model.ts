import { BaseEntityInput, BaseEntityOutput } from "./base.model";

export interface TelephoneTypeCreate extends BaseEntityInput {
	telephoneTypeName: string;
}

export interface TelephoneTypeUpdate extends TelephoneTypeCreate {
	idTelephoneType: number;
}

export interface TelephoneTypeOutput extends TelephoneTypeUpdate, BaseEntityOutput {

}