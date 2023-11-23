import {Component, OnDestroy, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import {ActivatedRoute} from "@angular/router";
import {SpeedGaugeComponent} from "./speed-gauge/speed-gauge.component";
import {LapseStreamService} from "../misc/f1-stream.service";
import {TelemetryUpdateDTO} from "../types/telematry-update.type";
import {MatGridListModule} from "@angular/material/grid-list";
import {PositionChartComponent} from "./position-chart/position-chart.component";
import {TimeChartComponent} from "./time-chart/time-chart.component";

@Component({
  selector: 'app-driver-details',
  standalone: true,
  imports: [CommonModule, SpeedGaugeComponent, MatGridListModule, PositionChartComponent, TimeChartComponent],
  templateUrl: './driver-details.component.html',
  styleUrl: './driver-details.component.scss'
})
export class DriverDetailsComponent implements OnInit, OnDestroy {
  id: string | null = null;
  currentPosition = []
  currentTelemetryUpdate: TelemetryUpdateDTO | undefined;
  // @ts-ignore
  private topicSubscription: Subscription;

  constructor(private route: ActivatedRoute, private lapseStreamService: LapseStreamService) {}

  ngOnInit(): void {
    // Subscribe to route parameters
    this.route.paramMap.subscribe(params => {
      this.id = params.get('id');
      console.log(this.id)
      if (this.id) {
        this.setupTelemetry(this.id)
      }
      else {
        console.log('error in id receiving')
      }
    });
  }


  setupTelemetry(id: string) {
    this.topicSubscription = this.lapseStreamService.subscribeToTopic(`telemetry/${id}`).subscribe(data => {
      if (data) {
        this.currentTelemetryUpdate = data
        // @ts-ignore
        this.currentPosition = [data.x, data.y]
      }
    });
  }
  ngOnDestroy() {
    if (this.topicSubscription) {
      this.topicSubscription.unsubscribe();
      this.lapseStreamService.unsubscribeFromTopic('laptimes');
    }
  }
}
