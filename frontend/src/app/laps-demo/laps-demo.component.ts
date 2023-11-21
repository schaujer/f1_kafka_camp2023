import {Component, OnDestroy, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import {NgxEchartsDirective} from "ngx-echarts";
import {EChartsOption} from "echarts";
import {LapseStreamService} from "../misc/f1-stream.service";
import {LapseUpdate} from "../types/lapse-update.type";

@Component({
  selector: 'app-laps-demo',
  standalone: true,
  imports: [CommonModule, NgxEchartsDirective],
  templateUrl: './laps-demo.component.html',
  styleUrl: './laps-demo.component.scss'
})
export class LapsDemoComponent implements OnInit, OnDestroy {
  options: EChartsOption = {};
  updateOptions: EChartsOption = {};

  private oneDay = 24 * 3600 * 1000;
  private now: Date = new Date(1997, 9, 3);
  private value: number = Math.random() * 1000;
  private data: DataT[] = [];
  private timer: any;

  constructor(private lapseService: LapseStreamService) {}

  ngOnInit(): void {

    for (let i = 0; i < 1000; i++) {
      this.data.push(this.randomData());
    }

    // initialize chart options:
    this.options = {
      title: {
        text: 'Dynamic Data + Time Axis',
      },
      tooltip: {
        trigger: 'axis',
        formatter: params => {
          let callBackParams;

          if (Array.isArray(params)) {
            // Since params is an array, safely access the first element
            callBackParams = params[0];
          } else {
            // Handle the case where params is not an array
            callBackParams = params; // or some other appropriate handling
          }
          const date = new Date(callBackParams.name);
          // @ts-ignore
            const value = callBackParams.value as OptionDataValue[];
          return (
            date.getDate() +
            '/' +
            (date.getMonth() + 1) +
            '/' +
            date.getFullYear() +
            ' : ' +
            value[1]
          );
        },
        axisPointer: {
          animation: false,
        },
      },
      xAxis: {
        type: 'time',
        splitLine: {
          show: false,
        },
      },
      yAxis: {
        type: 'value',
        boundaryGap: [0, '100%'],
        splitLine: {
          show: false,
        },
      },
      series: [
        {
          name: 'Mocking Data',
          type: 'line',
          showSymbol: false,
          data: this.data,
        },
      ],
    };

    // Mock dynamic data:
    this.timer = setInterval(() => {
      for (let i = 0; i < 5; i++) {
        this.data.shift();
        this.data.push(this.randomData());
      }

      // update series data:
      this.updateOptions = {
        series: [
          {
            data: this.data,
          },
        ],
      };
    }, 1000);
  }

  ngOnDestroy() {
    clearInterval(this.timer);
  }

  randomData(): DataT {
    this.now = new Date(this.now.getTime() + this.oneDay);
    this.value = this.value + Math.random() * 21 - 10;
    return {
      name: this.now.toString(),
      value: [
        [this.now.getFullYear(), this.now.getMonth() + 1, this.now.getDate()].join('/'),
        Math.round(this.value),
      ],
    };
  }
}

type DataT = {
  name: string;
  value: [string, number];
};
