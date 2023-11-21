import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LapsDemoComponent } from './laps-demo.component';

describe('LapsDemoComponent', () => {
  let component: LapsDemoComponent;
  let fixture: ComponentFixture<LapsDemoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LapsDemoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LapsDemoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
