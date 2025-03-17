import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogActions, MatDialogClose, MatDialogContent, MatDialogRef, MatDialogTitle } from '@angular/material/dialog';
import { NotificationAction, NotificationStruct, NotificationType } from '../../models/notification.model';
import { BaseEntityInput } from '../../models/base.model';


@Component({
  selector: 'app-notification-dialog',
  standalone: true,
  imports: [MatButtonModule, MatDialogActions, MatDialogClose, MatDialogContent, MatDialogTitle],
  templateUrl: './notification-dialog.component.html',
  styleUrl: './notification-dialog.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NotificationDialogComponent {
  
  readonly typeInfo: NotificationType = NotificationType.Info;
  readonly typeError: NotificationType = NotificationType.Error;
  readonly actionEdit: NotificationAction = NotificationAction.Edit;
  readonly actionDelete: NotificationAction = NotificationAction.Delete;
  readonly actionNothing: NotificationAction = NotificationAction.Nothing;
  
  readonly dialogRef = inject(MatDialogRef<NotificationDialogComponent>);
  readonly data = inject<NotificationStruct<BaseEntityInput>>(MAT_DIALOG_DATA);
  //readonly animal = model(this.data.animal);

  onNoClick(statusClose: boolean): void {
    this.dialogRef.close(statusClose);
  }
}