import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';
import { LoginComponent } from './login.component';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { DebugElement } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { By } from '@angular/platform-browser';

describe('LoginComponent test suite', () => {
  let component: LoginComponent;
  let fixtureLogin: ComponentFixture<LoginComponent>;
  let router: Router;
  let sessionService: SessionService;
  let authService: AuthService;
  let email: HTMLInputElement;
  let password: HTMLInputElement;
  let loginForm: DebugElement;
  let httpTestingController: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [SessionService, AuthService],
      imports: [
        RouterTestingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        HttpClientTestingModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule
      ]
    }).compileComponents();

    router = TestBed.inject(Router);
    sessionService = TestBed.inject(SessionService);
    authService = TestBed.inject(AuthService);
    httpTestingController = TestBed.inject(HttpTestingController);

    fixtureLogin = TestBed.createComponent(LoginComponent);
    component = fixtureLogin.componentInstance;
    fixtureLogin.detectChanges();

    email = fixtureLogin.nativeElement.querySelector('#email');
    password = fixtureLogin.nativeElement.querySelector('#password');
    loginForm = fixtureLogin.debugElement.query(By.css('.login-form'));
  });


  it('should create', () => {
    expect(component).toBeTruthy();
  });


  it('should log and allow an user to navigate on the sessions list', async () => {
    jest.spyOn(router, 'navigate').mockImplementation(); // We mock the router navigate method

    email.value = 'yoga@studio.com';
    email.dispatchEvent(new Event('input'));
    password.value = 'test!1234';
    password.dispatchEvent(new Event('input'));

    loginForm.triggerEventHandler('ngSubmit', null); // TriggerEventHandler is a utility function that triggers an event on a DebugElement, 'ngSubmit' is the event name and null is the event data
    await fixtureLogin.whenStable(); // We wait for the promise to resolve
    fixtureLogin.detectChanges(); // We detect the changes

    const req = httpTestingController.expectOne('api/auth/login'); // We expect a request to the login endpoint

    req.flush({ // Flush is a function that sends a response to the request
      token: 'mockedToken',
      type: 'type',
      id: 1,
      username: "yoga@studio.com",
      firstName: "admin",
      lastName: "ADMIN",
      admin: true
    });

    expect(component.form.valid).toBeTruthy();
    expect(sessionService.isLogged).toBeTruthy();
    expect(sessionService.sessionInformation?.token).toBe('mockedToken');
    expect(component.onError).toBeFalsy();
    expect(router.navigate).toHaveBeenCalledWith(['/sessions']);


    httpTestingController.verify(); // Verifies that all the requests have been handled
  });


  it('should reject login when wrong input', async () => {
    email.value = 'yoga.com';
    email.dispatchEvent(new Event('input'));
    password.value = '';
    password.dispatchEvent(new Event('input'));
    loginForm.triggerEventHandler('ngSubmit', null);
    await fixtureLogin.whenStable();
    fixtureLogin.detectChanges();
    expect(component.form.controls['email'].valid).toBeFalsy();
    expect(component.form.controls['password'].valid).toBeFalsy();
  });


  it('should throw an error if empty field', () => {
    component.form.setValue({ email: 'yoga@studio.com', password: 'test!1234' });
    component.submit();
    fixtureLogin.detectChanges();
    const req = httpTestingController.expectOne('api/auth/login');
    req.error(new ProgressEvent('Login error'));
    expect(component.onError).toBeTruthy();
    httpTestingController.verify();
  });
});
