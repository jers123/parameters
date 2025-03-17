import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Cliente } from '../../models/response-api';

@Component({
  selector: 'app-info-client',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './info-client.component.html',
  styleUrl: './info-client.component.css'
})
export class InfoClientComponent {
  cliente: Cliente;

  constructor(private router: Router) {
    const navigation = this.router.getCurrentNavigation();
    this.cliente = navigation?.extras.state?.['client'];
  }

  volver() {
    this.router.navigate(['/find-client']);
  }
}