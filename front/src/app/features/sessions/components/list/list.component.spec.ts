import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';

import { ListComponent } from './list.component';

describe('ListComponent', () => {
  let component: ListComponent;
  let fixture: ComponentFixture<ListComponent>;

  const mockSessionService = {
    sessionInformation: {
      token: "mockedToken",
      type: "type",
      id: 1,
      username: "yoga@studio.com",
      firstName: "admin",
      lastName: "ADMIN",
      admin: true
    }
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ListComponent],
      imports: [HttpClientModule, MatCardModule, MatIconModule],
      providers: [{ provide: SessionService, useValue: mockSessionService }]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get the user\'s infos from the session service', () => {
    expect(component.user?.token).toEqual("mockedToken");
    expect(component.user?.type).toEqual("type");
    expect(component.user?.id).toEqual(1);
    expect(component.user?.username).toEqual("yoga@studio.com");
    expect(component.user?.firstName).toEqual("admin");
    expect(component.user?.lastName).toEqual("ADMIN");
    expect(component.user?.admin).toEqual(true);
  });
});
