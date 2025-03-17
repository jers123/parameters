import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, inject, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { HttpStatusCode } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { ReplyMessageList, ReplyMessageSimple } from '../../models/reply_message.model';
import { GenderCreate, GenderOutput, GenderUpdate } from '../../models/gender.model';
import { GenderService } from '../../services/gender.service';
import { NotificationDialogComponent } from '../notification-dialog/notification-dialog.component';
import { NotificationAction, NotificationStruct, NotificationType } from '../../models/notification.model';
import { IBaseComponent } from '../i_base.component';
import { GenderUpdateComponent } from './gender-update/gender-update.component';
import { Support } from '../../services/i_base.service';

@Component({
  selector: 'app-gender',
  standalone: true,
  imports: [CommonModule, MatButtonModule, MatIconModule, MatInputModule, MatTableModule],
  templateUrl: './gender.component.html',
  styleUrl: './gender.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class GenderComponent implements OnInit, IBaseComponent<GenderCreate, GenderUpdate> {
  title = 'Gender';
  genderCreate: GenderCreate = {
    status: true,
    genderName: '',
  };

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
  replyMessageList: ReplyMessageList<GenderOutput> = {
    uri: environment.paths.globalPath,
    httpStatus: HttpStatusCode.Accepted,
    error: true,
    message: ['Error load'],
    date: new Date(),
    response: []
  };
  genders: GenderOutput[] = [];

  //displayedColumns: string[] = ['idGender', 'genderName', 'status', 'creationDate', 'updateDate'];
  displayedColumns: string[] = environment.displayedColumns.gender;
  dataSource: MatTableDataSource<GenderOutput> = new MatTableDataSource(this.genders);

  readonly dialog = inject(MatDialog);

  constructor(private support: Support, private service: GenderService) { }

  ngOnInit(): void {
    this.getAll();
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  openDialogDelete(entity: GenderUpdate): void {
    const notification = {
      type: NotificationType.Info,
      messages: ['Are you sure about deleting the gender: ' + entity.genderName + ''],
      notificationAction: NotificationAction.Delete,
      path: '',
      entity: entity
    };
    this.openDialog(notification);
  }

  openDialogInfo(message: string[]): void {
    const notification = {
      type: NotificationType.Info,
      messages: message,
      notificationAction: NotificationAction.Nothing,
      path: '',
      entity: this.genderUpdate
    };
    this.openDialog(notification);
  }

  openDialogError(message: string[]): void {
    const notification = {
      type: NotificationType.Error,
      messages: message,
      notificationAction: NotificationAction.Delete,
      path: '',
      entity: this.genderUpdate
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
        if (notification.notificationAction = NotificationAction.Edit) {
          console.log('update');
          this.update(notification.entity.idGender);
        } else if (notification.notificationAction = NotificationAction.Delete) {
          console.log('delete');
          this.delete(notification.entity.idGender);
        }
      }
    });
  }

  create(entity: GenderCreate): void {
    throw new Error('Method not implemented.');
  }


  getAll(): void {
    this.service.getFindAll().subscribe({
      next: (value) => {
        this.replyMessageList = value;
        this.genders = this.replyMessageList.response;
        if (this.replyMessageList.error) {
          console.log(this.replyMessageList.httpStatus);
          console.log(this.replyMessageList.message);
          this.openDialogError(this.replyMessageList.message)
        }
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
    this.dataSource = new MatTableDataSource(this.genders);
  }

  getId(id: number): void {
    throw new Error('Method not implemented.');
  }

  update(id: number): void {
    this.service.getFindById(id).subscribe({
      next: (value) => {
        this.replyMessageSimple = value;
        console.log(this.replyMessageSimple.httpStatus);
        console.log(this.replyMessageSimple.message);
        const dialogRef = this.dialog.open(GenderUpdateComponent, {
          disableClose: false,
          data: this.replyMessageSimple.response
        });
        dialogRef.afterClosed().subscribe(statusClose => {
          if (statusClose) {
            this.openDialogInfo(this.replyMessageSimple.message);
            this.support.wait(this.support.secondsNotification).subscribe();
            location.reload();
          }
        });
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

  delete(id: number): void {
    this.service.getDelete(id).subscribe({
      next: (value) => {
        this.replyMessageSimple = value;
        console.log(this.replyMessageSimple.httpStatus);
        console.log(this.replyMessageSimple.message);
        this.openDialogInfo(this.replyMessageSimple.message);
        this.support.wait(this.support.secondsNotification).subscribe();
        location.reload();
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
}