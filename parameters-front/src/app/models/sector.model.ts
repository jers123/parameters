import { BaseEntityInput, BaseEntityOutput } from "./base.model";

export interface SectorCreate extends BaseEntityInput {
	sectorName: string;
}

export interface SectorUpdate extends SectorCreate {
	idSector: number;
}

export interface SectorOutput extends SectorUpdate, BaseEntityOutput {

}