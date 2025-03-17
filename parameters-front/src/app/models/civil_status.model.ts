import { BaseEntityInput, BaseEntityOutput } from "./base.model";

export interface CivilStatusCreate extends BaseEntityInput {
	civilStatusName: string;
}

export interface CivilStatusUpdate extends CivilStatusCreate {
	idCivilStatus: number;
}

export interface CivilStatusOutput extends CivilStatusUpdate, BaseEntityOutput {

}