import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { RouterTestingModule, } from '@angular/router/testing';
import { expect } from '@jest/globals'; 
import { SessionService } from '../../../../services/session.service';

import { DetailComponent } from './detail.component';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';


describe('DetailComponent', () => {
  let component: DetailComponent;
  let fixture: ComponentFixture<DetailComponent>;
  let service: SessionService;

  let mockSessionService = {
    sessionInformation: {
      admin: true,
      id: 1,
    },
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatSnackBarModule,
        ReactiveFormsModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatSelectModule,
        MatInputModule,
      ],
      declarations: [DetailComponent],
      providers: [{ provide: SessionService, useValue: mockSessionService }],
    }).compileComponents();
    fixture = TestBed.createComponent(DetailComponent);
    component = fixture.componentInstance;

    component.session = {
      id: 1,
      name: 'Session 1',
      description: 'Description 1',
      date: new Date(),
      teacher_id: 1,
      users: [1, 2],
    };

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display session information correctly', () => {
    fixture.detectChanges();
    const compiled = fixture.nativeElement;
    expect(compiled.querySelector('h1').textContent).toContain('Session 1');
    expect(compiled.querySelector('.description').textContent).toContain(
      'Description 1'
    );
  });

  it('should display delete button but not participate button if user is admin', () => {

    const deleteButton = fixture.nativeElement.querySelector('#deleteButton');
    expect(deleteButton).toBeTruthy();

    const participateButton = fixture.nativeElement.querySelector('#participateButton');
    expect(participateButton).toBeFalsy();
  });

  it('should display participate button but not delete button if user is not admin', () => {
    component.isAdmin = false;

    fixture.detectChanges();

    const deleteButton = fixture.nativeElement.querySelector('#deleteButton');
    expect(deleteButton).toBeFalsy();

    const participateButton = fixture.nativeElement.querySelector('#participateButton');
    expect(participateButton).toBeTruthy();
  });

  it('should delete session and display the delete message', () => {
    const deleteSpy = jest.spyOn(component, 'delete');

    fixture.detectChanges();

    const deleteButton = fixture.nativeElement.querySelector('#deleteButton');
    deleteButton.click();

    expect(deleteSpy).toHaveBeenCalled();
  });

  it('should participate in session', () => {
    const participateSpy = jest.spyOn(component, 'participate');

    component.isAdmin = false;

    fixture.detectChanges();

    const participateButton = fixture.nativeElement.querySelector('#participateButton');
    participateButton.click();

    expect(participateSpy).toHaveBeenCalled();
  });

  it('should unparticipate in session', () => {
    const unParticipateSpy = jest.spyOn(component, 'unParticipate');

    component.isAdmin = false;
    component.isParticipate = true;

    fixture.detectChanges();

    const participateButton = fixture.nativeElement.querySelector('#cancelParticipationButton');
    participateButton.click();

    expect(unParticipateSpy).toHaveBeenCalled();
  });

  it('should go back', () => {
    const backSpy = jest.spyOn(component, 'back');

    fixture.detectChanges();

    const backButton = fixture.nativeElement.querySelector('#backButton');
    backButton.click();

    expect(backSpy).toHaveBeenCalled();
  });
});