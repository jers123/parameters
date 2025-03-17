import { BaseEntityInput, BaseEntityOutput } from "./base.model";

export interface StateCreate extends BaseEntityInput {
	stateName: string;
	idCountry: number;
	landlinePhoneIdentifier: string;
}

export interface StateUpdate extends StateCreate {
	idState: number;
}

export interface StateOutput extends StateUpdate, BaseEntityOutput {

}