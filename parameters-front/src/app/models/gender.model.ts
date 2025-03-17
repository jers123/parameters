import { BaseEntityInput, BaseEntityOutput } from "./base.model";

export interface GenderCreate extends BaseEntityInput {
    genderName: string;
}

export interface GenderUpdate extends GenderCreate {
    idGender: number;
}

export interface GenderOutput extends GenderUpdate, BaseEntityOutput {
    
}