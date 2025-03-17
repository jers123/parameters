import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Employee, EmployeeUpdate } from '../models/employee.model';
import { ReplyMessageList, ReplyMessageSimple } from '../models/reply_message.model';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private urlApi: String = environment.paths.globalPath + environment.paths.subPath.employeePath;

  constructor(private http: HttpClient) { }
  
  getCreate(entity: Employee, file: File): Observable<ReplyMessageSimple<EmployeeUpdate>> {
    if (typeof entity.fechaIngreso === 'string') {
      entity.fechaIngreso = new Date(entity.fechaIngreso);
    }
    const formData = new FormData();
    formData.append('cedula', entity.cedula.toString());
    formData.append('nombre', entity.nombre);
    formData.append('foto', entity.foto);
    formData.append('fechaIngreso', this.dateToString(entity.fechaIngreso));
    formData.append('idCargo', entity.idCargo.toString());
    if (file) {
      formData.append('file', file, file.name);
    }
    return this.http.post<ReplyMessageSimple<EmployeeUpdate>>(this.urlApi + environment.paths.functionPath.createPath, formData, environment.httpOptions);
  }
  
  getFindAll(): Observable<ReplyMessageList<EmployeeUpdate>> {
    return this.http.get<ReplyMessageList<EmployeeUpdate>>(this.urlApi + environment.paths.functionPath.getAllPath);
  }
  
  getFindById(id: number): Observable<ReplyMessageSimple<EmployeeUpdate>> {
    return this.http.get<ReplyMessageSimple<EmployeeUpdate>>(this.urlApi + environment.paths.functionPath.getIdPath + id);
  }
  
  getUpdate(entity: EmployeeUpdate, file: File): Observable<ReplyMessageSimple<EmployeeUpdate>> {
    if (typeof entity.fechaIngreso === 'string') {
      entity.fechaIngreso = new Date(entity.fechaIngreso);
    }
    const formData = new FormData();
    formData.append('idEmpleado', entity.idEmpleado.toString());
    formData.append('cedula', entity.cedula.toString());
    formData.append('nombre', entity.nombre);
    formData.append('foto', entity.foto);
    formData.append('fechaIngreso', this.dateToString(entity.fechaIngreso));
    formData.append('idCargo', entity.idCargo.toString());
    if (file) {
      formData.append('file', file, file.name);
    }
    console.log('formData:');
    console.log(formData);
    return this.http.put<ReplyMessageSimple<EmployeeUpdate>>(this.urlApi + environment.paths.functionPath.updatePath, formData, environment.httpOptions);
  }
  
  getDelete(id: number): Observable<ReplyMessageSimple<EmployeeUpdate>> {
    return this.http.delete<ReplyMessageSimple<EmployeeUpdate>>(this.urlApi + environment.paths.functionPath.deletePath + id);
  }

  private dateToString(date: Date): string {
    let dateString = '' + date.getFullYear() + "-" + this.getZero(date.getMonth() + 1) + "-" + this.getZero(date.getDate());
    return dateString;
  }

  private getZero(dm: number): string {
    let dmString = '';
    if(dm < 10) {
      dmString = '0'
    }
    dmString = dmString + dm + '';
    return dmString;
  }
}