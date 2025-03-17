import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { GenderComponent } from "./views/gender/gender.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, GenderComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  
}
