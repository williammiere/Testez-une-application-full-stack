describe('Register spec', () => {
    beforeEach(() => {
      cy.visit('/register');
    });
  
    it('should display register form', () => {
      cy.get('form').should('be.visible');
    });
  
    it('should display an error when register form fails', () => {
      cy.get('input[formControlName=firstName]').type("test");
      cy.get('input[formControlName=lastName]').type("TEST");
      cy.get('input[formControlName=email]').type("test@test.com");
      cy.get('input[formControlName=password]').type("AB{enter}{enter}");
  
      cy.get('.error').should('contain', 'An error occurred');
    });
  
    it('should register successfully', () => {
      cy.intercept('POST', '/api/auth/register', {
        body: {
          message: "User registered successfully!"
        }
      });
  
      cy.intercept('POST', '/api/auth/login', {
        body: {
          id: 1,
          username: 'test',
          firstName: 'test',
          lastName: 'TEST',
          admin: true
        }
      });
  
      cy.get('input[formControlName=firstName]').type("test");
      cy.get('input[formControlName=lastName]').type("TEST");
      cy.get('input[formControlName=email]').type("test@test.com");
      cy.get('input[formControlName=password]').type("test!32{enter}{enter}");
  
      cy.url().should('include', '/login');
    });
  });