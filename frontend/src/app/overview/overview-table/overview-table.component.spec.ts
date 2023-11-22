import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OverviewTableComponent } from './overview-table.component';

describe('OverviewTableComponent', () => {
  let component: OverviewTableComponent;
  let fixture: ComponentFixture<OverviewTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OverviewTableComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(OverviewTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
