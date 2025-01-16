import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { SessionService } from './session.service';
import { SessionInformation } from '../interfaces/sessionInformation.interface';

describe('SessionService', () => {
  let service: SessionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SessionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should initialize with isLogged set to false', () => {
    expect(service.isLogged).toBe(false);
  });

  it('should initialize with sessionInformation set to undefined', () => {
    expect(service.sessionInformation).toBeUndefined();
  });

  it('should emit isLogged as false initially', () => {
    service.$isLogged().subscribe(isLogged => {
      expect(isLogged).toBe(false);
    });
  });

  it('should log in a user and update isLogged and sessionInformation', () => {
    const user: SessionInformation = { token: "token", type: "type",
      id: 1,
      username: "test",
      firstName: "test",
      lastName: "TEST",
      admin: false };

    service.logIn(user);
    expect(service.isLogged).toBe(true);
    expect(service.sessionInformation).toEqual(user);
  });

  it('should log out a user and update isLogged and sessionInformation', () => {
    const user: SessionInformation = { token: "token", type: "type",
      id: 1,
      username: "test",
      firstName: "test",
      lastName: "TEST",
      admin: false };
      
    service.logIn(user);
    service.logOut();
    expect(service.isLogged).toBe(false);
    expect(service.sessionInformation).toBeUndefined();
  });

});