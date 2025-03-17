import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { ReplyMessageList, ReplyMessageSimple } from '../models/reply_message.model';
import { Position } from '../models/employee.model';

@Injectable({
  providedIn: 'root'
})
export class PositionService {
  private urlApi: String = environment.paths.globalPath + environment.paths.subPath.cargoPath;

  constructor(private http: HttpClient) { }

  getFindAll(): Observable<ReplyMessageList<Position>> {
    return this.http.get<ReplyMessageList<Position>>(this.urlApi + environment.paths.functionPath.getAllPath);
  }
  
  getFindById(id: number): Observable<ReplyMessageSimple<Position>> {
    return this.http.get<ReplyMessageSimple<Position>>(this.urlApi + environment.paths.functionPath.getIdPath + id);
  }
}