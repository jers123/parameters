import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IBaseService } from './i_base.service';
import { TelephoneTypeCreate, TelephoneTypeOutput, TelephoneTypeUpdate } from '../models/telephone_type.model';
import { ReplyMessageSimple, ReplyMessageList } from '../models/reply_message.model';

@Injectable({
  providedIn: 'root'
})
export class TelephoneTypeService implements IBaseService<TelephoneTypeCreate, TelephoneTypeUpdate, TelephoneTypeOutput> {

  constructor(private http: HttpClient) { }
  
  getCreate(entity: TelephoneTypeCreate): Observable<ReplyMessageSimple<TelephoneTypeOutput>> {
    throw new Error('Method not implemented.');
  }
  
  getFindAll(): Observable<ReplyMessageList<TelephoneTypeOutput>> {
    throw new Error('Method not implemented.');
  }
  
  getFindById(id: number): Observable<ReplyMessageSimple<TelephoneTypeOutput>> {
    throw new Error('Method not implemented.');
  }
  
  getUpdate(entity: TelephoneTypeUpdate): Observable<ReplyMessageSimple<TelephoneTypeOutput>> {
    throw new Error('Method not implemented.');
  }
  
  getDelete(id: number): Observable<ReplyMessageSimple<TelephoneTypeOutput>> {
    throw new Error('Method not implemented.');
  }
}
