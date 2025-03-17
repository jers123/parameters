import { BaseEntityInput } from "../models/base.model";
import { NotificationStruct } from "../models/notification.model";

export declare interface IBaseComponent<BC extends BaseEntityInput, BU extends BaseEntityInput> {
    openDialogDelete(entity: BU): void;
    openDialogInfo(message: string[]): void;
    openDialogError(message: string[]): void;
    openDialog(notification: NotificationStruct<BU>): void
    create(entity: BC): void;
    getAll(): void;
    getId(id: number): void;
    update(id: number): void;
    delete(id: number): void;
}