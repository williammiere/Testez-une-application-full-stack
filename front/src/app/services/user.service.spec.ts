import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { UserService } from './user.service';

describe('UserService', () => {
  let service: UserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[
        HttpClientModule
      ]
    });
    service = TestBed.inject(UserService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get info about the user', () => {
    service.getById('1').subscribe(user => {
      expect(user.id).toBe('1');
    });
  });

  it('should delete the user', () => {
    service.delete('1').subscribe(response => {
      expect(response).toBe('1');
    });
  });
  
});