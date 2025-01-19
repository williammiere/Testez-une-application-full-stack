import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { SessionService } from 'src/app/services/session.service';

import { MeComponent } from './me.component';
import { of } from 'rxjs';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';

describe('MeComponent integration test', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;

  const mockSessionService = {
    sessionInformation: {
      token: "mockedToken",
      type: "type",
      id: 1,
      username: "yoga@studio.com",
      firstName: "notAdmin",
      lastName: "NOTADMIN",
      admin: false
    },
    logOut: jest.fn()
  }

  const mockUser = {
    id: 1,
    email: "yoga@studio.com",
    firstName: "notAdmin",
    lastName: "NOTADMIN",
    password: "test!1234",
    admin: false,
    createdAt: new Date(),
    updatedAt: new Date()
  }
    

  const mockUserService = {
    getById: jest.fn().mockReturnValue(of(mockUser)),
    delete: jest.fn().mockReturnValue(of({}))
  }

  const matSnackBarMock = {
    open: jest.fn()
  }

  const routerMock = {
    navigate: jest.fn()
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MeComponent],
      imports: [
        MatSnackBarModule,
        HttpClientModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule
      ],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        { provide: UserService, useValue: mockUserService },
        { provide: MatSnackBar, useValue: matSnackBarMock },
        { provide: Router, useValue: routerMock }
      ],
    })
      .compileComponents();

    fixture = TestBed.createComponent(MeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize by getting the user and his id and then delete it', () => {

    component.ngOnInit();

    expect(mockUserService.getById).toHaveBeenCalledWith('1');
    expect(component.user).toEqual(mockUser);

    const deleteSpy = jest.spyOn(component, 'delete');
    const deleteButton = fixture.debugElement.nativeElement.querySelector('#deleteButton');
    deleteButton.click();
    fixture.detectChanges();

    expect(deleteSpy).toHaveBeenCalled();
    expect(mockUserService.delete).toHaveBeenCalledWith('1');
    expect(matSnackBarMock.open).toHaveBeenCalledWith('Your account has been deleted !', 'Close', { duration: 3000 });
    expect(mockSessionService.logOut).toHaveBeenCalled();
    expect(routerMock.navigate).toHaveBeenCalledWith(['/']);
  });

  it('should not show the delete button if admin', () => {
    mockUser.admin = true;
    fixture.detectChanges();
    component.ngOnInit();
    expect(fixture.debugElement.nativeElement.querySelector('#deleteButton')).toBeFalsy();
  });

});
