import {Component, Input, OnChanges} from '@angular/core';
import { CommonModule } from '@angular/common';
import {NgxEchartsDirective} from "ngx-echarts";
import {EChartsOption} from "echarts";

@Component({
  selector: 'app-position-chart',
  standalone: true,
  imports: [CommonModule, NgxEchartsDirective],
  templateUrl: './position-chart.component.html',
  styleUrl: './position-chart.component.scss'
})
export class PositionChartComponent implements OnChanges {
  @Input() currentPosition: number[] = [];
  pastPositions: number[][] = []
  // @ts-ignore
  updateOptions: EChartsOpti;

  options: EChartsOption = {
    xAxis: {
      show: false,},
    yAxis: {
      show: false,},
    series: [
      {
        name: 'Track',
        type: 'scatter',
        data:  []
      },
      {
        name: 'Driver',
        type: 'scatter',
        data:  []
      },
    ],
  };

  ngOnChanges() {
    if (this.currentPosition.length !== 2) {
      return
    }
    if (this.pastPositions.length > 1800) {
      return
    }
    this.pastPositions.push(this.currentPosition)
    const axisMetrics = this.calculateAxis()
    const minX = axisMetrics[0]
    const minY = axisMetrics[1]
    const maxRange = axisMetrics[2]
    this.updateOptions = {
      xAxis: {
        min: minX,
        max: minX + maxRange
      },
      yAxis: {
        min: minY,
        max: minY + maxRange
      },
      series: [
        {
          name: "Track",
          data: this.pastPositions,
        },{
          name: "Driver",
          data: [this.currentPosition],
        },
      ],
    };
  }

  calculateAxis() {
    let minX = Math.min(...this.pastPositions.map(d => d[0]));
    let maxX = Math.max(...this.pastPositions.map(d => d[0]));
    let minY = Math.min(...this.pastPositions.map(d => d[1]));
    let maxY = Math.max(...this.pastPositions.map(d => d[1]));
    let rangeX = maxX - minX;
    let rangeY = maxY - minY;
    let maxRange = Math.max(rangeX, rangeY);
    return [minX, minY, maxRange]
  }
}
