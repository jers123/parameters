import { CommonModule } from '@angular/common';
import { HttpStatusCode } from '@angular/common/http';
import { ChangeDetectionStrategy, Component, inject, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { environment } from '../../../environments/environment';
import { NotificationDialogComponent } from '../notification-dialog/notification-dialog.component';
import { EmployeeCreateComponent } from './employee-create/employee-create.component';
import { EmployeeUpdateComponent } from './employee-update/employee-update.component';
import { Employee, EmployeeUpdate, Position } from '../../models/employee.model';
import { ReplyMessageList, ReplyMessageSimple } from '../../models/reply_message.model';
import { NotificationAction, NotificationStruct, NotificationType } from '../../models/notification.model';
import { EmployeeService } from '../../services/employee.service';
import { PositionService } from '../../services/position.service';

@Component({
  selector: 'app-employee',
  standalone: true,
  imports: [CommonModule, MatButtonModule, MatIconModule, MatInputModule, MatTableModule],
  templateUrl: './employee.component.html',
  styleUrl: './employee.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class EmployeeComponent implements OnInit {
  position: Position = {
    idCargo: 0,
    nombre: 'Scrum'
  };
  positions: Position[] = [];

  employee: Employee = {
    cedula: 1,
    nombre: '',
    foto: 'foto',
    fechaIngreso: new Date(),
    idCargo: 0,
    position: this.position
  };
  employeeUpdate: EmployeeUpdate = {
    idEmpleado: 0,
    cedula: 1,
    nombre: '',
    foto: 'foto',
    fechaIngreso: new Date(),
    idCargo: 0,
    position: this.position
  };
  employees: EmployeeUpdate[] = [];

  route: string = environment.paths.globalPath + environment.paths.subPath.employeePath + environment.paths.functionPath.filePath;

  replyMessageSimple: ReplyMessageSimple<EmployeeUpdate> = {
    uri: environment.paths.globalPath,
    httpStatus: HttpStatusCode.Accepted,
    error: true,
    message: ['Error load'],
    date: new Date(),
    response: this.employeeUpdate
  };
  replyMessageList: ReplyMessageList<EmployeeUpdate> = {
    uri: environment.paths.globalPath,
    httpStatus: HttpStatusCode.Accepted,
    error: true,
    message: ['Error load'],
    date: new Date(),
    response: []
  };

  replyMessageSimplePosition: ReplyMessageSimple<Position> = {
    uri: environment.paths.globalPath,
    httpStatus: HttpStatusCode.Accepted,
    error: true,
    message: ['Error load'],
    date: new Date(),
    response: this.employeeUpdate.position
  };

  readonly dialog = inject(MatDialog);

  displayedColumns: string[] = environment.displayedColumns.employee;
  dataSource: MatTableDataSource<EmployeeUpdate> = new MatTableDataSource(this.employees);

  constructor(private service: EmployeeService, private positionService: PositionService) {}
  
  ngOnInit(): void {
    this.getAll();
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  openDialogDelete(entity: EmployeeUpdate): void {
    const notification = {
      type: NotificationType.Info,
      messages: ['Esta seguro de eliminar el empleado: ' + entity.nombre + ''],
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
      entity: this.employeeUpdate
    };
    this.openDialog(notification);
  }

  openDialogError(message: string[]): void {
    const notification = {
      type: NotificationType.Error,
      messages: message,
      notificationAction: NotificationAction.Delete,
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
        if (notification.notificationAction = NotificationAction.Edit) {
          this.update(notification.entity.idEmpleado);
        } else if (notification.notificationAction = NotificationAction.Delete) {
          this.delete(notification.entity.idEmpleado);
        }
      }
    });
  }

  create(): void {
    const dialogRef = this.dialog.open(EmployeeCreateComponent, {
      disableClose: false
    });
    dialogRef.afterClosed().subscribe(statusClose => {
      if (statusClose) {
        this.openDialogInfo(this.replyMessageSimple.message);
        location.reload();
      }
    }); 
  }


  getAll(): void {
    this.service.getFindAll().subscribe({
      next: (value) => {
        this.replyMessageList = value;
        this.employees = this.replyMessageList.response;
        if (this.replyMessageList.error) {
          this.openDialogError(this.replyMessageList.message)
        } else {
          for(let i = 0; i < this.employees.length; i++) {
            this.positionService.getFindById(this.employees[i].idCargo).subscribe({
              next: (value) => {
                this.replyMessageSimplePosition = value;
                this.employees[i].position = this.replyMessageSimplePosition.response;
                this.employees[i].foto = this.employees[i].foto.replace(/ /g, "%20");
                if (!this.employees[i].foto.startsWith('http')) {
                  this.employees[i].foto = this.route + this.employees[i].idEmpleado + '/' + this.employees[i].foto;
                }
                if (this.replyMessageSimplePosition.error) {
                  this.openDialogError(this.replyMessageSimplePosition.message)
                }
              },
              error: (err) => {
                if(err.status == 0) {
                  this.openDialogError([environment.connectionAPI]);
                } else {
                  this.replyMessageSimplePosition = err.error;
                  this.openDialogError(this.replyMessageSimplePosition.message);
                }
              },
            });
          }
        }
      },
      error: (err) => {
        if(err.status == 0) {
          this.openDialogError([environment.connectionAPI]);
        } else {
          this.replyMessageList = err.error;
          this.openDialogError(this.replyMessageList.message);
        }
      },
    });
    this.dataSource = new MatTableDataSource(this.employees);
  }

  getId(id: number): void {
    throw new Error('Method not implemented.');
  }

  update(id: number): void {
    this.service.getFindById(id).subscribe({
      next: (value) => {
        this.replyMessageSimple = value;
        if (this.replyMessageList.error) {
          this.openDialogError(this.replyMessageList.message)
        } else {
          const dialogRef = this.dialog.open(EmployeeUpdateComponent, {
            disableClose: false,
            data: this.replyMessageSimple.response
          });
          dialogRef.afterClosed().subscribe(statusClose => {
            if (statusClose) {
              this.openDialogInfo(this.replyMessageSimple.message);
              location.reload();
            }
          });
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

  delete(id: number): void {
    this.service.getDelete(id).subscribe({
      next: (value) => {
        this.replyMessageSimple = value;
        this.openDialogInfo(this.replyMessageSimple.message);
        //this.support.wait(this.support.secondsNotification).subscribe();
        location.reload();
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
}