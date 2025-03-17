import { HttpStatusCode } from "@angular/common/http";
import { BaseEntityOutput } from "./base.model";

export interface ReplyMessage {
	uri: string;
	httpStatus: HttpStatusCode;
	error: boolean;
	message: string[];
	date: Date;
}

export interface ReplyMessageSimple<BO extends BaseEntityOutput> extends ReplyMessage {
	response: BO;
}

export interface ReplyMessageList<BO extends BaseEntityOutput> extends ReplyMessage {
	response: BO[];
}