import { HttpStatusCode } from "@angular/common/http";

export interface Cliente {
    tipoDocumento: string;
	numeroDocumento: number;
	primerNombre: string;
    segundoNombre: string;
    primerApellido: string;
    segundoApellido: string;
    telefono: string;
    direccion: string;
    ciudadResidencia: string;
}

export interface ReplyMessage {
	uri: string;
	httpStatus: HttpStatusCode;
	error: boolean;
	message: string[];
	date: Date;
    response: Cliente;
}