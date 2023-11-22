import {Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-driver-details',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './driver-details.component.html',
  styleUrl: './driver-details.component.scss'
})
export class DriverDetailsComponent implements OnInit {
  id: string | null = null;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    // Subscribe to route parameters
    this.route.paramMap.subscribe(params => {
      this.id = params.get('id');
      console.log(this.id)
    });
  }
}
