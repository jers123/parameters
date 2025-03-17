import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IBaseService } from './i_base.service';
import { GenderCreate, GenderOutput, GenderUpdate } from '../models/gender.model';
import { ReplyMessageSimple, ReplyMessageList } from '../models/reply_message.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class GenderService implements IBaseService<GenderCreate, GenderUpdate, GenderOutput> {

  private urlApi: String = environment.paths.globalPath + environment.paths.subPath.genderPath;

  /*httpOptions = {
    headers: new HttpHeaders(
      {
        'Content-Type': 'application/json',
        'Authorization': 'Basic ' + environment.credentials
      }
    )
  };*/

  constructor(private http: HttpClient) { }
  
  getCreate(entity: GenderCreate): Observable<ReplyMessageSimple<GenderOutput>> {
    return this.http.post<ReplyMessageSimple<GenderOutput>>(this.urlApi + environment.paths.functionPath.createPath, entity, environment.httpOptions);
  }
  
  getFindAll(): Observable<ReplyMessageList<GenderOutput>> {
    return this.http.get<ReplyMessageList<GenderOutput>>(this.urlApi + environment.paths.functionPath.getAllPath);
  }
  
  getFindById(id: number): Observable<ReplyMessageSimple<GenderOutput>> {
    return this.http.get<ReplyMessageSimple<GenderOutput>>(this.urlApi + environment.paths.functionPath.getIdPath + id);
  }
  
  getUpdate(entity: GenderUpdate): Observable<ReplyMessageSimple<GenderOutput>> {
    return this.http.put<ReplyMessageSimple<GenderOutput>>(this.urlApi + environment.paths.functionPath.updatePath, entity, environment.httpOptions);
  }
  
  getDelete(id: number): Observable<ReplyMessageSimple<GenderOutput>> {
    return this.http.delete<ReplyMessageSimple<GenderOutput>>(this.urlApi + environment.paths.functionPath.deletePath + id, environment.httpOptions);
  }
}