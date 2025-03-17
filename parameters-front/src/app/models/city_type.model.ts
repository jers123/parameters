import { BaseEntityInput, BaseEntityOutput } from "./base.model";

export interface CityTypeCreate extends BaseEntityInput {
	cityTypeName: string;
}

export interface CityTypeUpdate extends CityTypeCreate {
	idCityType: number;
}

export interface CityTypeOutput extends CityTypeUpdate, BaseEntityOutput {

}