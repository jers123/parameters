import { BaseEntityInput, BaseEntityOutput } from "./base.model";

export interface CityToCityCreate extends BaseEntityInput {
	idOriginCity: number;
	idDestinationCity: number;
	distance: number;
	routeNumbers: number;
}

export interface CityToCityUpdate extends CityToCityCreate {
	idCityToCity: number;
}

export interface CityToCityOutput extends CityToCityUpdate, BaseEntityOutput {
	
}