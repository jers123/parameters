import { BaseEntityInput } from "./base.model";

export enum NotificationType {
    Info = 0,
    Error = 1
}

export enum NotificationAction {
    Edit = 0,
    Delete = 1,
    Nothing = 3
}

export interface NotificationStruct<BI extends BaseEntityInput> {
    type: NotificationType,
    messages: string[],
    notificationAction: NotificationAction,
    path: string,
    entity: BI
}