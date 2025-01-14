import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PositionChartComponent } from './position-chart.component';

describe('PositionChartComponent', () => {
  let component: PositionChartComponent;
  let fixture: ComponentFixture<PositionChartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PositionChartComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PositionChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
