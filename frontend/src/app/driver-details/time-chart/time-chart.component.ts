import {Component, Inject, Input, LOCALE_ID} from '@angular/core';
import { CommonModule } from '@angular/common';
import {NgxEchartsDirective} from "ngx-echarts";
import {EChartsOption} from "echarts";
import {TelemetryUpdateDTO} from "../../types/telematry-update.type";

@Component({
  selector: 'app-time-chart',
  standalone: true,
    imports: [CommonModule, NgxEchartsDirective],
  templateUrl: './time-chart.component.html',
  styleUrl: './time-chart.component.scss'
})
export class TimeChartComponent {
  @Input() currentValue: number | undefined
  @Input() title = 'not set'
  @Input() telUpdate: TelemetryUpdateDTO | undefined  // required to trigger onChanges even if value does not change
  data: DataT[] = []
  // @ts-ignore
  options: EChartsOption;
  // @ts-ignore
  updateOptions: EChartsOpti;
  constructor(@Inject(LOCALE_ID) private locale: string) {
  }

  ngOnInit() {
    this.setupOptions()
  }

  ngOnChanges() {
    this.addSpeedData()
  }

  addSpeedData() {
    if (this.currentValue === undefined) {
      return
    }
    const now = new Date
    const newEl = {
      name: now.toString(),
      value: [
        now,
        this.currentValue,
      ]
    } as DataT
    while (this.data.length > 300) {
      this.data.shift()
    }
    this.data.push(newEl)
    this.updateOptions = {
      series: [
        {
          data: this.data,
        },
      ],
    };
  }

  private setupOptions() {
    this.options = {
      title: {
        text: this.title,
      },
      tooltip: {
        trigger: 'axis',
        formatter: params => {
          return 'some string'
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
  }
}

type DataT = {
  name: string;
  value: [Date, number];
};
