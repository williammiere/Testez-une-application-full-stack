describe('Creation session test', () => {
    it('Creates a session', () => {
      cy.visit('/login');
  
      cy.intercept('POST', '/api/auth/login', {
        body: {
          id: 1,
          username: 'userName',
          firstName: 'firstName',
          lastName: 'lastName',
          admin: true
        },
      });
  
      cy.intercept(
        {
          method: 'GET',
          url: '/api/session',
        },
        [{
            "id": 2,
            "name": "Autre session test",
            "date": "2026-01-01T00:00:00.000+00:00",
            "teacher_id": 2,
            "description": "Autre TEST",
            "users": [],
            "createdAt": "2026-01-01T00:00:00.000+00:00",
            "updatedAt": "2026-01-01T00:00:00.000+00:00"
          }]).as('session');
  
      cy.intercept('GET', '/api/teacher', {
        body: [
          {
            id: 1,
            lastName: "TEST",
            firstName: "Test",
            createdAt: "2001-10-10T10:10:10",
            updatedAt: "2001-01-01T10:10:10"
          },
          {
            id: 2,
            lastName: "TEST2",
            firstName: "Test2",
            createdAt: "2001-10-10T10:10:10",
            updatedAt: "2001-10-10T10:10:10"
          }
        ]
      });
  
      cy.intercept('POST', '/api/session', {
        statusCode: 200,
        body: {
          message: "Session created !"
        }
      });
  
      cy.get('input[formControlName=email]').type("yoga@studio.com");
  
      cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`);
  
      cy.url().should('include', '/sessions');
  
      cy.get('button').contains('Create').click();
  
      cy.url().should('include', '/sessions/create');
  
      cy.get('input[formControlName=name]').type("Test cypress");
  
      cy.get('input[formControlName=date]').type("2025-01-01");
  
      cy.get('mat-select').click();
  
      cy.get('mat-option').first().click();
  
      cy.get('textarea').type("TEST");

      cy.get('button').contains('Save').click();
  
      cy.url().should('include', '/sessions');
    });
  });