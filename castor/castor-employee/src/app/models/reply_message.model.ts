import { HttpStatusCode } from "@angular/common/http";
import { BaseEntityInput } from "./base.model";

export interface ReplyMessage {
	uri: string;
	httpStatus: HttpStatusCode;
	error: boolean;
	message: string[];
	date: Date;
}

export interface ReplyMessageSimple<BO extends BaseEntityInput> extends ReplyMessage {
	response: BO;
}

export interface ReplyMessageList<BO extends BaseEntityInput> extends ReplyMessage {
	response: BO[];
}