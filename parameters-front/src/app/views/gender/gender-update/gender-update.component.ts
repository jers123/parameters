import { Component, inject, model, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialog, MatDialogActions, MatDialogClose, MatDialogContent, MatDialogRef, MatDialogTitle } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { GenderService } from '../../../services/gender.service';
import { GenderOutput, GenderUpdate } from '../../../models/gender.model';
import { ReplyMessageSimple } from '../../../models/reply_message.model';
import { environment } from '../../../../environments/environment';
import { HttpStatusCode } from '@angular/common/http';
import { NotificationAction, NotificationStruct, NotificationType } from '../../../models/notification.model';
import { NotificationDialogComponent } from '../../notification-dialog/notification-dialog.component';
import { CommonModule } from '@angular/common';
import { Support } from '../../../services/i_base.service';

@Component({
  selector: 'app-gender-update',
  standalone: true,
  imports: [CommonModule, FormsModule, MatButtonModule, MatDialogActions, MatDialogClose, MatDialogContent, MatDialogTitle, MatFormFieldModule, MatInputModule, MatSelectModule],
  templateUrl: './gender-update.component.html',
  styleUrl: './gender-update.component.css'
})
export class GenderUpdateComponent implements OnInit {
  
  readonly data = inject<GenderOutput>(MAT_DIALOG_DATA);
  readonly dialogRef = inject(MatDialogRef<GenderUpdateComponent>);
  
  genderUpdate: GenderUpdate = {
    status: true,
    genderName: '',
    idGender: 0
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

  readonly dialog = inject(MatDialog);
  
  constructor (private support: Support, private service: GenderService) {
  }

  openDialogEdit(): void {
    const notification = {
      type: NotificationType.Info,
      messages: ['Are you sure about editing the gender: ' + this.genderOutput.genderName + '?'],
      notificationAction: NotificationAction.Edit,
      path: '',
      entity: this.genderOutput
    };
    this.openDialog(notification);
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

  openDialog(notification: NotificationStruct<GenderUpdate>): void {
    const dialogRef = this.dialog.open(NotificationDialogComponent, {
      disableClose: false,
      data: notification
    });

    console.log(notification);

    dialogRef.afterClosed().subscribe(statusClose => {
      if (notification.type == NotificationType.Info && statusClose) {
        this.update();
      }
    });
  }
  
  ngOnInit(): void {
    this.genderOutput = {
      status: this.data.status,
      genderName: this.data.genderName,
      idGender: this.data.idGender,
      creationDate: this.data.creationDate,
      updateDate: this.data.updateDate
    };
  }

  update(): void {
    this.setUpdate();
    this.service.getUpdate(this.genderUpdate).subscribe({
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

  setUpdate(): void {
    this.genderUpdate = {
      status: this.genderOutput.status,
      genderName: this.genderOutput.genderName,
      idGender: this.genderOutput.idGender
    };
  }
}