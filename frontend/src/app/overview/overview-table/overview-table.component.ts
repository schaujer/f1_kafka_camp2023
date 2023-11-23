import {Component, OnDestroy, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatTableModule} from "@angular/material/table";
import {LapsUpdateDTO} from "../../types/lapse-update.type";
import {RaceTimePipe} from "../../misc/race-time.pipe";
import {Router} from "@angular/router";
import {Subscription} from "rxjs";
import {LapseStreamService} from "../../misc/f1-stream.service";

const drivers = ["Hamilton", "Verstappen", "Leclerc", "Ricciardo", "Alonso", "Vettel", "Bottas", "Sainz", "Perez", "Norris"];

@Component({
  selector: 'app-overview-table',
  standalone: true,
  imports: [CommonModule, MatTableModule, RaceTimePipe],
  templateUrl: './overview-table.component.html',
  styleUrl: './overview-table.component.scss'
})
export class OverviewTableComponent implements OnInit, OnDestroy {

  displayedColumns: string[] = ['position' , 'driver', 'lapNumber', 'sector1Time', 'sector2Time', 'sector3Time', 'lapTime'];
  dataSource: LapsUpdateDTO[] = [];
  changedData = new Map<string, boolean>();
  // @ts-ignore
  private topicSubscription: Subscription;
  constructor(private router: Router, private lapseStreamService: LapseStreamService) {
  }

  ngOnInit() {
    this.topicSubscription = this.lapseStreamService.subscribeToTopic('laptimes').subscribe(data => {
      if (data) {
        this.updateLapData(data)
      }
    });
  }
  updateLapData(newLapUpdate: LapsUpdateDTO) {
    //const newLapUpdate = this.generateRandomLapUpdate();
    const existingItem = this.dataSource.find(item => item.driver === newLapUpdate.driver);

    if (newLapUpdate.driver === 'VER') {
      console.log(newLapUpdate)
    }

    if (existingItem && existingItem.sector1Time !== newLapUpdate.sector1Time) {
      this.changedData.set(newLapUpdate.driver + 'sector1Time', true);
    }
    if (existingItem && existingItem.sector2Time !== newLapUpdate.sector2Time) {
      this.changedData.set(newLapUpdate.driver + 'sector2Time', true);
    }
    if (existingItem && existingItem.sector3Time !== newLapUpdate.sector3Time) {
      this.changedData.set(newLapUpdate.driver + 'sector3Time', true);
    }
    if (existingItem && existingItem.lapTime !== newLapUpdate.lapTime) {
      this.changedData.set(newLapUpdate.driver + 'lapTime', true);
    }
    if (existingItem && existingItem.position !== newLapUpdate.position) {
      this.changedData.set(newLapUpdate.driver + 'position', true);
    }

    const filteredData = this.dataSource.filter(lap => lap.driver !== newLapUpdate.driver);
    filteredData.push(newLapUpdate);
    filteredData.sort((a, b) => a.position - b.position);
    this.dataSource = filteredData
  }

  ngOnDestroy() {
    if (this.topicSubscription) {
      this.topicSubscription.unsubscribe();
      this.lapseStreamService.unsubscribeFromTopic('laptimes');
    }
  }

  private generateRandomLapUpdate(): LapsUpdateDTO {
    return {
      position: Math.floor(Math.random() * 2),
      driver: drivers[Math.floor(Math.random() * drivers.length)],
      lapNumber: Math.random() * 100,
      sector1Time: Math.random() * 100,
      sector2Time: Math.random() * 100,
      sector3Time: Math.random() * 100,
      lapTime: Math.random() * 100,
      timestamp: Date.now()
    };
  }

  isChanged(driver: any, colName: string) {
    return this.changedData.get(driver + colName) || this.changedData.get(driver + 'position');
  }
  onRowClicked(row: any) {
    this.router.navigate(['/driver-details', row.driver]);
  }
}
