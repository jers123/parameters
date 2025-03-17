import { BaseEntityInput, BaseEntityOutput } from "./base.model";

export interface CountryCreate extends BaseEntityInput {
	countryName: string;
	phoneIdentifier: string;
}

export interface CountryUpdate extends CountryCreate {
	idCountry: number;
}

export interface CountryOutput extends CountryUpdate, BaseEntityOutput {
	
}