import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NavigationExtras, Router } from '@angular/router';
import { ClienteServiceService } from '../../services/cliente-service.service';
import { HttpStatusCode } from '@angular/common/http';
import { Cliente, ReplyMessage } from '../../models/response-api';
import { environment } from '../../../environments/environment';
import { routes } from '../../app.routes';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-find-client',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './find-client.component.html',
  styleUrl: './find-client.component.css'
})
export class FindClientComponent {
  
  replyMessage: ReplyMessage = {
    uri: environment.paths.globalPath,
    httpStatus: HttpStatusCode.InternalServerError,
    error: true,
    message: ['Error load'],
    date: new Date(),
    response: {
      tipoDocumento: '',
      numeroDocumento: 0,
      primerNombre: '',
      segundoNombre: '',
      primerApellido: '',
      segundoApellido: '',
      telefono: '',
      direccion: '',
      ciudadResidencia: ''
    }
  };
  
  
  form: FormGroup;
  errorMessage: string | null = null;

  constructor(private fb: FormBuilder, private router: Router, private service: ClienteServiceService) {
    this.form = this.fb.group({
      tipoDocumento: ['', Validators.required],
      numeroDocumento: [
        '',
        [Validators.required, Validators.minLength(10), Validators.maxLength(13)]
      ]
    });
  }

  allowOnlyNumbers(event: KeyboardEvent) {
    const charCode = event.charCode;
    if (charCode < 48 || charCode > 57) {
      event.preventDefault();
    }
  }
  
  formatNumber(event: Event) {
    const input = event.target as HTMLInputElement;
    let value = input.value.replace(/\D/g, '');
    value = value.replace(/\B(?=(\d{3})+(?!\d))/g, '.');
    input.value = value;
  }

  buscarCliente(): void {
    let { tipoDocumento, numeroDocumento } = this.form.value;
    numeroDocumento = numeroDocumento.replace(/\D/g, '');
    this.service.findCliente(tipoDocumento, numeroDocumento).subscribe({
      next: (value) => {
        this.replyMessage = value;
        const client: Cliente = this.replyMessage.response;
        if (!this.replyMessage.error) {
          const navigationExtras: NavigationExtras = { state: { client } };
          this.router.navigate(['/info-client'], navigationExtras);
        } else {
          this.errorMessage = this.replyMessage.httpStatus + ": " + this.replyMessage.message[0];
        }
      },
      error: (err) => {
        if(err.status == 0) {
          this.errorMessage = environment.connectionAPI
        } else {
          this.replyMessage = err.error;
          this.errorMessage = this.replyMessage.httpStatus + ": " + this.replyMessage.message[0];
        }
      },
    });
  }
}