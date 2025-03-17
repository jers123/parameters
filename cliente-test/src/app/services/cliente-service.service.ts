import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { ReplyMessage } from '../models/response-api';

@Injectable({
  providedIn: 'root'
})
export class ClienteServiceService {
  private urlApi: String = environment.paths.globalPath;

  constructor(private http: HttpClient) { }

  findCliente(type: string, numberId: number): Observable<ReplyMessage> {
    return this.http.get<ReplyMessage>(this.urlApi + '?tipo=' + type + '&numero=' + numberId, environment.httpOptions);
  }
}