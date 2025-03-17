import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GenderCreateComponent } from './gender-create.component';

describe('GenderCreateComponent', () => {
  let component: GenderCreateComponent;
  let fixture: ComponentFixture<GenderCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GenderCreateComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GenderCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
