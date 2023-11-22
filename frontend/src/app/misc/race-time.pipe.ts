import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'raceTime',
  standalone: true
})
export class RaceTimePipe implements PipeTransform {

  transform(value: number | string): string {
    if (typeof value === 'string' || value === null) {
      return '-'
    }
    const minutes: number = Math.floor(value / 60);
    const seconds: number = Math.floor(value % 60);
    const milliseconds: number = Math.floor((value - Math.floor(value)) * 1000);

    return `${minutes}m ${seconds}s ${milliseconds}ms`;
  }

}
