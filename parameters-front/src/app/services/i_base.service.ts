import { Injectable } from "@angular/core";
import { delay, Observable, of } from "rxjs";
import { BaseEntityInput, BaseEntityOutput } from "../models/base.model";
import { ReplyMessageList, ReplyMessageSimple } from "../models/reply_message.model";

@Injectable({
    providedIn: 'root',
})
export class Support {
    
    secondsNotification: number = 5;
    connectionAPI: string= 'No connect with the API'; 

    constructor() {}

    wait(seconds: number) {
        return of(null).pipe(delay(seconds * 1000));
    }
}

export declare interface IBaseService<BC extends BaseEntityInput, BU extends BaseEntityInput, BO extends BaseEntityOutput> {
    getCreate(entity: BC): Observable<ReplyMessageSimple<BO>>;
    getFindAll(): Observable<ReplyMessageList<BO>>;
    getFindById(id: number): Observable<ReplyMessageSimple<BO>>;
    getUpdate(entity: BU): Observable<ReplyMessageSimple<BO>>;
    getDelete(id: number): Observable<ReplyMessageSimple<BO>>;
}