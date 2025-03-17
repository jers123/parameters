import { HttpHeaders } from "@angular/common/http";

export const environment = {
    name: 'Client test',
    connectionAPI: 'No hay conexión con la API',
    httpOptions: {
        headers: new HttpHeaders(
            {
                //'Content-Type': 'multipart/form-data; boundary=<calculated when request is sent>',
                'Accept': 'application/json'
            }
        )
    },
    paths: {
        globalPath: 'http://localhost:8090/cliente'
    },
    displayedColumns: {
        cargo: ['idCargo', 'nombre', 'edit', 'delete'],
        employee: ['idEmpleado', 'foto', 'cedula', 'nombre', 'fechaIngreso', 'idCargo', 'edit', 'delete']
    }
};