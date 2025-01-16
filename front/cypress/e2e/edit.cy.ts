describe('Edit', () => {
    let sessions = [
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
    beforeEach(() => {
        cy.intercept('GET', '/api/teacher', {
            body: [
                { id: 1, firstName: 'Margot', lastName: 'DELAHAYE' },
                { id: 2, firstName: 'H├®l├¿ne', lastName: 'THIERCELIN' }
            ]
        }).as('getTeachers');

        cy.intercept('GET', '/api/teacher/1', {
            id: 1, firstName: 'Margot', lastName: 'DELAHAYE'
        }).as('getTeacher');
        cy.intercept('GET', '/api/teacher/2', {
            id: 2, firstName: 'H├®l├¿ne', lastName: 'THIERCELIN'
        }).as('getTeacher');
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

        cy.visit('/login')
        cy.get('input[formControlName=email]').type("yoga@studio.com")
        cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    })
    it('should create button then display creation form', () => {
        sessions.push({
            "id": 3,
            "name": "New session",
            "date": "2027-01-01T00:00:00.000+00:00",
            "teacher_id": 1,
            "description": "NEW session",
            "users": [],
            "createdAt": "2025-01-07T00:00:00.00",
            "updatedAt": "2025-01-07T00:00:00.00"
        });
        cy.contains('button', 'Create').click();
        cy.get('form').should('be.visible');

        cy.url().should('include', '/sessions/create')

        cy.intercept('POST', '/api/session', {
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
        }).as('createSession')

        cy.get('input[formControlName=name]').type("New session")
        cy.get('input[formControlName=date]').type(`2027-01-01`)
        cy.get('mat-select[formControlName=teacher_id]').click()
        cy.get('mat-option').contains('Margot DELAHAYE').click();
        cy.get('textarea[formControlName=description]').type("NEW session")

        cy.intercept('GET', '/api/session', sessions);
        cy.contains('button', 'Save').click();

        cy.url().should('include', '/sessions')
        cy.get('.mat-snack-bar-container').should('contain', 'Session created !');
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
        cy.intercept('GET', '/api/session/1', sessions[0]).as('sessionDetail');
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
        }).as('updatedSessions')
        cy.intercept('PUT', '/api/session/1', {
            statusCode: 200,
            body: [sessionUpdated]
        })

        cy.intercept('GET', '/api/session/1', sessions[0]).as('sessionDetail');

        cy.get('button').contains('Edit').click()

        cy.get('input[formControlName=name]').type(" - session updated")
        cy.get('input[formControlName=date]').type(`2027-01-01`)
        cy.get('mat-select[formControlName=teacher_id]').click()
        cy.get('mat-option').contains('Margot DELAHAYE').click();
        cy.get('textarea[formControlName=description]').type(" - updated description")
        cy.get('button').contains('Save').click()




    })
})