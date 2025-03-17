import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TelephoneTypeComponent } from './telephone-type.component';

describe('TelephoneTypeComponent', () => {
  let component: TelephoneTypeComponent;
  let fixture: ComponentFixture<TelephoneTypeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TelephoneTypeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TelephoneTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
