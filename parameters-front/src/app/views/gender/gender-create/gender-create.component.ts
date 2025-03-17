import { HttpStatusCode } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog, MatDialogActions, MatDialogClose, MatDialogContent, MatDialogRef, MatDialogTitle } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { NotificationAction, NotificationStruct, NotificationType } from '../../../models/notification.model';
import { GenderCreate, GenderOutput } from '../../../models/gender.model';
import { ReplyMessageSimple } from '../../../models/reply_message.model';
import { environment } from '../../../../environments/environment';
import { GenderService } from '../../../services/gender.service';
import { Support } from '../../../services/i_base.service';
import { NotificationDialogComponent } from '../../notification-dialog/notification-dialog.component';

@Component({
  selector: 'app-gender-create',
  standalone: true,
  imports: [MatButtonModule, MatDialogActions, MatDialogClose, MatDialogContent, MatDialogTitle, MatFormFieldModule, MatInputModule, MatSelectModule],
  templateUrl: './gender-create.component.html',
  styleUrl: './gender-create.component.css'
})
export class GenderCreateComponent {
  
  readonly dialogRef = inject(MatDialogRef<GenderCreateComponent>);
  readonly dialog = inject(MatDialog);

  genderCreate: GenderCreate = {
    status: true,
    genderName: ''
  };

  genderOutput: GenderOutput = {
    status: true,
    genderName: '',
    idGender: 0,
    creationDate: new Date,
    updateDate: new Date
  };

  replyMessageSimple: ReplyMessageSimple<GenderOutput> = {
    uri: environment.paths.globalPath,
    httpStatus: HttpStatusCode.Accepted,
    error: true,
    message: ['Error load'],
    date: new Date(),
    response: this.genderOutput
  };

  constructor (private support: Support, private service: GenderService) {
  }

  openDialogError(message: string[]): void {
    const notification = {
      type: NotificationType.Error,
      messages: message,
      notificationAction: NotificationAction.Edit,
      path: '',
      entity: this.genderOutput
    };
    this.openDialog(notification);
  }

  openDialog(notification: NotificationStruct<GenderCreate>): void {
    const dialogRef = this.dialog.open(NotificationDialogComponent, {
      disableClose: false,
      data: notification
    });
    dialogRef.afterClosed().subscribe(statusClose => {
      if (notification.type == NotificationType.Info && statusClose) {
        this.update();
      }
    });
  }

  create(): void {
    this.setCreate();
    this.service.getCreate(this.genderCreate).subscribe({
      next: (value) => {
        console.log(value);
        this.replyMessageSimple = value;
        console.log(this.replyMessageSimple.httpStatus);
        console.log(this.replyMessageSimple.message);
        this.onClose(true);
      },
      error: (err) => {
        console.log(err);
        if(err.status == 0) {
          this.openDialogError([this.support.connectionAPI]);
        } else {
          this.replyMessageSimple = err.error;
          this.openDialogError(this.replyMessageSimple.message);
        }
      },
    });
  }

  onClose(statusClose: boolean): void {
    this.dialogRef.close(statusClose);
  }

  setCreate(): void {
    this.genderCreate = {
      status: this.genderOutput.status,
      genderName: this.genderOutput.genderName
    };
  }
}