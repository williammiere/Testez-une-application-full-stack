describe('User information', () => {
    beforeEach(() => {
        cy.intercept('POST', '/api/auth/login', {
            body: {
              id: 1,
              username: 'test',
              firstName: 'test',
              lastName: 'TEST',
              admin: false
            }
          });

        cy.visit('/login')
    
        cy.get('input[formControlName=email]').type("yoga@studio.com")
        cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)
    
    });

    it('should display user information correctly', () => {
        cy.intercept('GET', '/api/user/1', {
            body: {
                id: 1,
                email: "test@test.com",
                lastName: "TEST",
                firstName: "test",
                admin: false,
                createdAt: "2025-01-07T00:00:00.000000",
                updatedAt: "2025-01-07T00:00:00.000000"
            }
        });

        cy.get('span.link').contains('Account').click();
        cy.get('p').should('contain', 'Name: test TEST');
        cy.get('p').should('contain', 'Email: test@test.com');
        cy.get('p').contains('Create at: January 7, 2025');
        cy.get('p').contains('Last update: January 7, 2025');
    });

    it('should go back with history', () => {
        cy.intercept('GET', '/api/user/1', {
            body: {
                id: 1,
                email: "test@test.com",
                lastName: "test",
                firstName: "TEST",
                admin: false,
                createdAt: "2025-01-07T00:00:00.000000",
                updatedAt: "2025-01-07T00:00:00.000000"
            }
        });

        cy.get('span.link').contains('Account').click();
        cy.get('button.mat-icon-button').click();

        cy.url().should('include', '/sessions');
    });

    it('should delete user account successfully', () => {
        cy.intercept('GET', '/api/user/1', {
            body: {
                id: 1,
                email: "test@test.com",
                lastName: "test",
                firstName: "TEST",
                admin: false,
                createdAt: "2025-01-07T00:00:00.000000",
                updatedAt: "2025-01-07T00:00:00.000000"
            }
        });

        cy.intercept('DELETE', '/api/user/1', {
            statusCode: 200,
            body: {}
        });

        cy.get('span.link').contains('Account').click();
        cy.get('button.mat-raised-button').click();

        cy.url().should('include', '/');
        cy.get('.mat-snack-bar-container').should('contain', 'Your account has been deleted !');
    });
});
