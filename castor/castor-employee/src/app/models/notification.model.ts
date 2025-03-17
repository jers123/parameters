import { BaseEntityInput } from "./base.model";

export enum NotificationType {
    Info = 0,
    Error = 1
}

export enum NotificationAction {
    Create = 0,
    Edit = 1,
    Delete = 2,
    Nothing = 3
}

export interface NotificationStruct<BI extends BaseEntityInput> {
    type: NotificationType,
    messages: string[],
    notificationAction: NotificationAction,
    path: string,
    entity: BI
}