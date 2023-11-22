import { Routes } from '@angular/router';
import {DriverDetailsComponent} from "./driver-details/driver-details.component";
import {OverviewComponent} from "./overview/overview.component";

export const routes: Routes = [
    { path: '', component:  OverviewComponent, title: 'Overview',},
    { path: 'driver-details/:id', component: DriverDetailsComponent },
];
