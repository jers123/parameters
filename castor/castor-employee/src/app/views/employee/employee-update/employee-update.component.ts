import { CommonModule } from '@angular/common';
import { HttpStatusCode } from '@angular/common/http';
import { ChangeDetectionStrategy, Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialog, MatDialogActions, MatDialogClose, MatDialogContent, MatDialogRef, MatDialogTitle } from '@angular/material/dialog';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { environment } from '../../../../environments/environment';
import { NotificationDialogComponent } from '../../notification-dialog/notification-dialog.component';
import { ReplyMessageList, ReplyMessageSimple } from '../../../models/reply_message.model';
import { EmployeeUpdate, Position } from '../../../models/employee.model';
import { NotificationAction, NotificationStruct, NotificationType } from '../../../models/notification.model';
import { EmployeeService } from '../../../services/employee.service';
import { PositionService } from '../../../services/position.service';

@Component({
  selector: 'app-employee-update',
  standalone: true,
  imports: [CommonModule, FormsModule, MatButtonModule, MatDatepickerModule, MatDialogActions, MatDialogClose, MatDialogContent, MatDialogTitle, MatFormFieldModule, MatInputModule, MatSelectModule],
  templateUrl: './employee-update.component.html',
  styleUrl: './employee-update.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class EmployeeUpdateComponent  implements OnInit {
  readonly data = inject<EmployeeUpdate>(MAT_DIALOG_DATA);
  readonly dialogRef = inject(MatDialogRef<EmployeeUpdateComponent>);

  position: Position = {
    idCargo: 0,
    nombre: 'Scrum'
  };
  positions: Position[] = [];

  employeeUpdate: EmployeeUpdate = {
    idEmpleado: 0,
    cedula: 1,
    nombre: '',
    foto: 'foto',
    fechaIngreso: new Date(),
    idCargo: 0,
    position: this.position
  };
  file: File = new File([new Blob([], { type: 'image/jpeg' })], 'myImage.jpg', { type: 'image/jpeg' });
  
  replyMessageSimple: ReplyMessageSimple<EmployeeUpdate> = {
    uri: environment.paths.globalPath,
    httpStatus: HttpStatusCode.Accepted,
    error: true,
    message: ['Error load'],
    date: new Date(),
    response: this.employeeUpdate
  };

  replyMessageSimplePosition: ReplyMessageSimple<Position> = {
    uri: environment.paths.globalPath,
    httpStatus: HttpStatusCode.Accepted,
    error: true,
    message: ['Error load'],
    date: new Date(),
    response: this.position
  };
  replyMessageListPosition: ReplyMessageList<Position> = {
    uri: environment.paths.globalPath,
    httpStatus: HttpStatusCode.Accepted,
    error: true,
    message: ['Error load'],
    date: new Date(),
    response: []
  };
  
  readonly dialog = inject(MatDialog);
  
  constructor (private service: EmployeeService, private positionService: PositionService) {}

  ngOnInit(): void {
    this.employeeUpdate = {
      idEmpleado: this.data.idEmpleado,
      cedula: this.data.cedula,
      nombre: this.data.nombre,
      foto: this.data.foto,
      fechaIngreso: this.data.fechaIngreso,
      idCargo: this.data.idCargo,
      position: this.position
    };
    this.getAllPosition();
  }

  onFileSelected(event: any) {
    this.file = event.target.files[0];
  }

  openDialogEdit(): void {
    const notification = {
      type: NotificationType.Info,
      messages: ['Esta seguro de editar el empleado: ' + this.employeeUpdate.nombre + '?'],
      notificationAction: NotificationAction.Edit,
      path: '',
      entity: this.employeeUpdate
    };
    this.openDialog(notification);
  }

  openDialogError(message: string[]): void {
    const notification = {
      type: NotificationType.Error,
      messages: message,
      notificationAction: NotificationAction.Edit,
      path: '',
      entity: this.employeeUpdate
    };
    this.openDialog(notification);
  }

  openDialog(notification: NotificationStruct<EmployeeUpdate>): void {
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

  update(): void {
    this.service.getUpdate(this.employeeUpdate, this.file).subscribe({
      next: (value) => {
        this.replyMessageSimple = value;
        if (this.replyMessageSimple.error) {
          this.openDialogError(this.replyMessageSimple.message)
        } else {
          this.onClose(true);
        }
      },
      error: (err) => {
        if(err.status == 0) {
          this.openDialogError([environment.connectionAPI]);
        } else {
          this.replyMessageSimple = err.error;
          this.openDialogError(this.replyMessageSimple.message);
        }
      },
    });
  }

  getAllPosition(): void {
    this.positionService.getFindAll().subscribe({
      next: (value) => {
        this.replyMessageListPosition = value;
        this.positions = this.replyMessageListPosition.response;
        if (this.replyMessageListPosition.error) {
          this.openDialogError(this.replyMessageListPosition.message)
        }
      },
      error: (err) => {
        if(err.status == 0) {
          this.openDialogError([environment.connectionAPI]);
        } else {
          this.replyMessageListPosition = err.error;
          this.openDialogError(this.replyMessageListPosition.message);
        }
      },
    });
  }

  onClose(statusClose: boolean): void {
    this.dialogRef.close(statusClose);
  }
}