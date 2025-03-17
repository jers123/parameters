import { CommonModule } from '@angular/common';
import { HttpStatusCode } from '@angular/common/http';
import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatDialog, MatDialogActions, MatDialogClose, MatDialogContent, MatDialogRef, MatDialogTitle } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { NotificationDialogComponent } from '../../notification-dialog/notification-dialog.component';
import { environment } from '../../../../environments/environment';
import { Employee, Position } from '../../../models/employee.model';
import { ReplyMessageList, ReplyMessageSimple } from '../../../models/reply_message.model';
import { NotificationAction, NotificationStruct, NotificationType } from '../../../models/notification.model';
import { EmployeeService } from '../../../services/employee.service';
import { PositionService } from '../../../services/position.service';

@Component({
  selector: 'app-employee-create',
  standalone: true,
  imports: [CommonModule, FormsModule, MatButtonModule, MatDatepickerModule, MatDialogActions, MatDialogClose, MatDialogContent, MatDialogTitle, MatFormFieldModule, MatInputModule, MatSelectModule],
  templateUrl: './employee-create.component.html',
  styleUrl: './employee-create.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class EmployeeCreateComponent {
  readonly dialogRef = inject(MatDialogRef<EmployeeCreateComponent>);

  position: Position = {
    idCargo: 0,
    nombre: 'Scrum'
  };
  positions: Position[] = [];

  employee: Employee = {
    cedula: 0,
    nombre: '',
    foto: 'foto',
    fechaIngreso: new Date(),
    idCargo: 0,
    position: this.position
  };
  file: File = new File([new Blob([], { type: 'image/jpeg' })], 'myImage.jpg', { type: 'image/jpeg' });
  
  replyMessageSimple: ReplyMessageSimple<Employee> = {
    uri: environment.paths.globalPath,
    httpStatus: HttpStatusCode.Accepted,
    error: true,
    message: ['Error load'],
    date: new Date(),
    response: this.employee
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
    this.getAllPosition();
  }

  onFileSelected(event: any) {
    this.file = event.target.files[0];
  }

  openDialogCreate(): void {
    const notification = {
      type: NotificationType.Info,
      messages: ['Esta seguro de crear el empleado: ' + this.employee.nombre + '?'],
      notificationAction: NotificationAction.Create,
      path: '',
      entity: this.employee
    };
    this.openDialog(notification);
  }

  openDialogError(message: string[]): void {
    const notification = {
      type: NotificationType.Error,
      messages: message,
      notificationAction: NotificationAction.Create,
      path: '',
      entity: this.employee
    };
    this.openDialog(notification);
  }

  openDialog(notification: NotificationStruct<Employee>): void {
    const dialogRef = this.dialog.open(NotificationDialogComponent, {
      disableClose: false,
      data: notification
    });
    dialogRef.afterClosed().subscribe(statusClose => {
      if (notification.type == NotificationType.Info && statusClose) {
        this.create();
      }
    });
  }

  create(): void {
    this.service.getCreate(this.employee, this.file).subscribe({
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