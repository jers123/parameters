import { HttpHeaders } from "@angular/common/http";

export const environment = {
    name: 'Employee',
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
        globalPath: 'http://localhost:8080/employee',
        subPath: {
            cargoPath: '/cargo',
            employeePath: '/empleado'
        },
        functionPath: {
            createPath: '/create',
            deletePath: '/delete/',
            filePath: '/file/',
            getAllPath: '/get-all',
            getIdPath: '/get-id/',
            updatePath: '/update'
        }
    },
    displayedColumns: {
        cargo: ['idCargo', 'nombre', 'edit', 'delete'],
        employee: ['idEmpleado', 'foto', 'cedula', 'nombre', 'fechaIngreso', 'idCargo', 'edit', 'delete']
    }
};