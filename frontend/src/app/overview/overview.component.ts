import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {OverviewTableComponent} from "./overview-table/overview-table.component";

@Component({
  selector: 'app-overview',
  standalone: true,
    imports: [CommonModule, OverviewTableComponent],
  templateUrl: './overview.component.html',
  styleUrl: './overview.component.scss'
})
export class OverviewComponent {

}
