import { BaseEntityInput, BaseEntityOutput } from "./base.model";

export interface CityCreate extends BaseEntityInput {
	cityName: string;
	idState: number;
	idCityType: number;
	landlinePhoneIdentifier:string;
	area: number;
	minimumTemperature: number;
	maximumTemperature: number;
	heightAboveSeaLevel: number;
}

export interface CityUpdate extends CityCreate {
	idCity: number;
}

export interface CityOutput extends CityUpdate, BaseEntityOutput {
	
}