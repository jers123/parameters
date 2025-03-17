import { TestBed } from '@angular/core/testing';

import { TelephoneTypeService } from './telephone-type.service';

describe('TelephoneTypeService', () => {
  let service: TelephoneTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TelephoneTypeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
