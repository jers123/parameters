import { Routes } from '@angular/router';
import { FindClientComponent } from './views/find-client/find-client.component';
import { InfoClientComponent } from './views/info-client/info-client.component';

export const routes: Routes = [
    { path: '', redirectTo: 'find-client', pathMatch: 'full' },
    { path: 'find-client', component: FindClientComponent },
    { path: 'info-client', component: InfoClientComponent },
    { path: '**', redirectTo: 'find-client' }
];