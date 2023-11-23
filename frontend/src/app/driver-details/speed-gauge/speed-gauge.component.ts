import {Component, Inject, Input, LOCALE_ID, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {CommonModule, formatDate} from '@angular/common';
import { EChartsOption } from 'echarts';
import {NgxEchartsDirective} from "ngx-echarts";
import * as moment from 'moment';

@Component({
  selector: 'app-speed-gauge',
  standalone: true,
  imports: [CommonModule, NgxEchartsDirective],
  templateUrl: './speed-gauge.component.html',
  styleUrl: './speed-gauge.component.scss'
})
export class SpeedGaugeComponent implements OnChanges, OnInit{
  @Input() speed: number | undefined
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
     if (this.speed === undefined) {
       return
     }
     const now = new Date
     const newEl = {
       name: now.toString(),
       value: [
         now,
         this.speed,
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
         text: 'Dynamic Data + Time Axis',
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
