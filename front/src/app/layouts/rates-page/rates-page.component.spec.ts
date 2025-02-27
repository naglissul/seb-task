import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RatesPageComponent } from './rates-page.component';

describe('RatesPageComponent', () => {
  let component: RatesPageComponent;
  let fixture: ComponentFixture<RatesPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RatesPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RatesPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
