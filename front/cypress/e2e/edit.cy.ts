describe('Edit', () => {
    let sessions = [ // Preparing the sessions data to mock the API's response
        {
            "id": 1,
            "name": "Test",
            "date": "2025-01-07T00:00:00.000000",
            "teacher_id": 1,
            "description": "Test",
            "users": [],
            "createdAt": "2025-01-07T00:00:00.00",
            "updatedAt": "2025-01-07T00:00:00.00"
        },
        {
            "id": 2,
            "name": "Autre test",
            "date": "2026-01-07T00:00:00.000000",
            "teacher_id": 2,
            "description": "Autre TEST",
            "users": [],
            "createdAt": "2025-01-07T00:00:00.00",
            "updatedAt": "2025-01-07T00:00:00.00"
        }
    ]
    beforeEach(() => { // Before each test, we intercept the API's requests and mock the responses
        cy.intercept('GET', '/api/teacher', {
            body: [
                { id: 1, firstName: 'Margot', lastName: 'DELAHAYE' },
                { id: 2, firstName: 'H├®l├¿ne', lastName: 'THIERCELIN' }
            ]
        });

        cy.intercept('GET', '/api/teacher/1', {
            id: 1, firstName: 'Margot', lastName: 'DELAHAYE'
        });
        cy.intercept('GET', '/api/teacher/2', {
            id: 2, firstName: 'H├®l├¿ne', lastName: 'THIERCELIN'
        });
        cy.intercept('GET', '/api/session', sessions);

        cy.intercept('POST', '/api/auth/login', {
            body: {
                id: 1,
                username: 'test',
                firstName: 'test',
                lastName: 'TEST',
                admin: true
            }
        });

        cy.visit('/login') // Visit allows us to go to a specific URL
        cy.get('input[formControlName=email]').type("yoga@studio.com") // We select object in the DOM and type in it as if we were a user
        cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    })
    it('should create button then display creation form', () => {
        sessions.push({ // We add a new session to the sessions array
            "id": 3,
            "name": "New session",
            "date": "2027-01-01T00:00:00.000+00:00",
            "teacher_id": 1,
            "description": "NEW session",
            "users": [],
            "createdAt": "2025-01-07T00:00:00.00",
            "updatedAt": "2025-01-07T00:00:00.00"
        });
        cy.contains('button', 'Create').click(); // "Contains" allows us to select an object by its text content
        cy.get('form').should('be.visible'); // We check that the form is visible

        cy.url().should('include', '/sessions/create') // We check that the URL contains '/sessions/create'

        cy.intercept('POST', '/api/session', { // We intercept the POST request to the API and mock the response
            body: {
                id: 3,
                name: "New session",
                date: "2027-01-01T00:00:00.000+00:00",
                teacher_id: 1,
                description: "NEW session",
                users: [],
                createdAt: "2025-01-07T00:00:00.00",
                updatedAt: "2025-01-07T00:00:00.00"
            },
        });

        cy.get('input[formControlName=name]').type("New session") // We fill the form with the new session's data
        cy.get('input[formControlName=date]').type(`2027-01-01`)
        cy.get('mat-select[formControlName=teacher_id]').click()
        cy.get('mat-option').contains('Margot DELAHAYE').click();
        cy.get('textarea[formControlName=description]').type("NEW session")

        cy.intercept('GET', '/api/session', sessions); // We intercept the GET request to the API and mock the response
        cy.contains('button', 'Save').click(); // We click on the "Save" button

        cy.url().should('include', '/sessions')
        cy.get('.mat-snack-bar-container').should('contain', 'Session created !'); // We check that the snackbar displays the success message
    })

    it('should let the admin delete a session', () => {
        cy.intercept('DELETE', '/api/session/1', {
            statusCode: 200
        })
        cy.intercept('GET', '/api/session', {
            body: [{
                "id": 2,
                "name": "Autre test",
                "date": "2026-01-07T00:00:00.000000",
                "teacher_id": 2,
                "description": "Autre TEST",
                "users": [],
                "createdAt": "2025-01-07T00:00:00.00",
                "updatedAt": "2025-01-07T00:00:00.00"
            }, {
                "id": 3,
                "name": "New session",
                "date": "2027-01-01T00:00:00.000+00:00",
                "teacher_id": 1,
                "description": "NEW session",
                "users": [],
                "createdAt": "2025-01-07T00:00:00.00",
                "updatedAt": "2025-01-07T00:00:00.00"
            }]
        })
        cy.intercept('GET', '/api/session/1', sessions[0]);
        cy.get('button').contains('Detail').click()
        cy.get('button').contains('Delete').click()

        cy.get('.mat-snack-bar-container').should('contain', 'Session deleted !');

        cy.url().should('include', '/session');
    })
    it('should let the admin update a session', () => {
        const sessionUpdated = {
            id: 1,
            name: "Test",
            date: "2025-01-07T00:00:00.000000",
            teacher_id: 1,
            description: "Test",
            users: [],
            createdAt: "2025-01-07T00:00:00.00",
            updatedAt: "2025-01-07T00:00:00.00"
        }

        cy.intercept('GET', '/api/session', {
            body: [sessionUpdated,
                {
                    "id": 2,
                    "name": "Autre test",
                    "date": "2026-01-07T00:00:00.000000",
                    "teacher_id": 2,
                    "description": "Autre TEST",
                    "users": [],
                    "createdAt": "2025-01-07T00:00:00.00",
                    "updatedAt": "2025-01-07T00:00:00.00"
                }, {
                    "id": 3,
                    "name": "New session",
                    "date": "2027-01-01T00:00:00.000+00:00",
                    "teacher_id": 1,
                    "description": "NEW session",
                    "users": [],
                    "createdAt": "2025-01-07T00:00:00.00",
                    "updatedAt": "2025-01-07T00:00:00.00"
                }]
        });
        cy.intercept('PUT', '/api/session/1', {
            statusCode: 200,
            body: [sessionUpdated]
        })

        cy.intercept('GET', '/api/session/1', sessions[0]);

        cy.get('button').contains('Edit').click()

        cy.get('input[formControlName=name]').type(" - session updated")
        cy.get('input[formControlName=date]').type(`2027-01-01`)
        cy.get('mat-select[formControlName=teacher_id]').click()
        cy.get('mat-option').contains('Margot DELAHAYE').click();
        cy.get('textarea[formControlName=description]').type(" - updated description")
        cy.get('button').contains('Save').click()




    })
})