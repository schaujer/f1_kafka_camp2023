import {Component, Input, OnChanges, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import { EChartsOption } from 'echarts';
import {NgxEchartsDirective} from "ngx-echarts";

@Component({
  selector: 'app-speed-gauge',
  standalone: true,
  imports: [CommonModule, NgxEchartsDirective],
  templateUrl: './speed-gauge.component.html',
  styleUrl: './speed-gauge.component.scss'
})
export class SpeedGaugeComponent implements OnChanges, OnInit{
  @Input() speed: number | undefined
   // @ts-ignore
   options: EChartsOption;
   // @ts-ignore
   updateOptions: EChartsOpti;

   ngOnInit() {
     this.setupOptions()
   }

   ngOnChanges() {
     this.addSpeedData()
   }

   addSpeedData() {
     if (this.speed === null) {
       return
     }
     this.updateOptions = {
       series: [
         {
           data:[
               {
                 value: this.speed
               }
               ]
         },
       ],
     };
   }

   private setupOptions() {
     this.options = {
       series: [
         {
           type: 'gauge',
           max: 340,
           axisLine: {
             lineStyle: {
               width: 30,
               color: [
                 [0.3, '#67e0e3'],
                 [0.7, '#37a2da'],
                 [1, '#fd666d']
               ]
             }
           },
           pointer: {
             itemStyle: {
               color: 'auto'
             }
           },
           axisTick: {
             distance: -30,
             length: 8,
             lineStyle: {
               color: '#fff',
               width: 2
             }
           },
           splitLine: {
             distance: -30,
             length: 30,
             lineStyle: {
               color: '#fff',
               width: 4
             }
           },
           axisLabel: {
             color: 'inherit',
             distance: 40,
             fontSize: 20
           },
           detail: {
             valueAnimation: true,
             formatter: '{value} km/h',
             color: 'inherit'
           },
           data: [
             {
               value: 0
             }
           ]
         }
         ]
    }
   }
}
