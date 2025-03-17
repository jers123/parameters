import { BaseEntityInput } from "./base.model";
export interface Position extends BaseEntityInput {
	idCargo: number;
	nombre: string;
}

export interface Employee extends BaseEntityInput {
	cedula: number;
	nombre: string;
	foto: string;
	fechaIngreso: Date;
	idCargo: number;
	position: Position;
}

export interface EmployeeUpdate extends Employee {
	idEmpleado: number;
}