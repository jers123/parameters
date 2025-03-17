import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { FindClientComponent } from "./views/find-client/find-client.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, FindClientComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Cliente test';
}
