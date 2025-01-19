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

describe('DetailComponent integration test suite', () => {
  let component: DetailComponent;
  let fixture: ComponentFixture<DetailComponent>; // ComponentFixture used to create an instance of the component under test.

  let mockSessionService = { // Mocked SessionService
    sessionInformation: {
      admin: true,
      id: 1,
    },
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({ // Configuring the module with the necessary imports, declarations, and providers.
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
    fixture = TestBed.createComponent(DetailComponent); // Creating an instance of the component under test.
    component = fixture.componentInstance; // Getting the component instance.

    component.session = { // Mocked session object.
      id: 1,
      name: 'Session 1',
      description: 'Description 1',
      date: new Date(),
      teacher_id: 1,
      users: [1, 2],
    };

    fixture.detectChanges(); // Triggering change detection.
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display session information correctly', () => {
    fixture.detectChanges();
    const compiled = fixture.nativeElement; // Getting the compiled HTML.
    expect(compiled.querySelector('h1').textContent).toContain('Session 1'); // Checking if the session name is displayed.
    expect(compiled.querySelector('.description').textContent).toContain( // Checking if the session description is displayed.
      'Description 1'
    );
  });

  it('should display delete button but not the participate button if user is admin', () => {

    const deleteButton = fixture.nativeElement.querySelector('#deleteButton'); // Getting the delete button.
    expect(deleteButton).toBeTruthy(); // Checking if the delete button is displayed.

    const participateButton = fixture.nativeElement.querySelector('#participateButton'); // Getting the participate button.
    expect(participateButton).toBeFalsy(); // Checking if the participate button is not displayed.
  });

  it('should display participate button but not the delete button if user is not admin', () => {
    component.isAdmin = false; // Setting the isAdmin property to false.

    fixture.detectChanges();

    const deleteButton = fixture.nativeElement.querySelector('#deleteButton');
    expect(deleteButton).toBeFalsy();

    const participateButton = fixture.nativeElement.querySelector('#participateButton');
    expect(participateButton).toBeTruthy();
  });

  it('should delete session and display the delete message', () => {
    const deleteSpy = jest.spyOn(component, 'delete'); // Spying on the delete method.

    fixture.detectChanges();

    const deleteButton = fixture.nativeElement.querySelector('#deleteButton');
    deleteButton.click(); // Triggering the click event on the delete button.

    expect(deleteSpy).toHaveBeenCalled(); // Checking if the delete method has been called.
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
