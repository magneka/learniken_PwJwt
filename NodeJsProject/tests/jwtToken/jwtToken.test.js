const { createJwtToken, validateJwtToken } = require("../../jwtToken/jwtToken");
const jwt = require('jsonwebtoken');

describe("Testing jwt tokens", () => {

    beforeEach(() => { })

    afterEach(() => { });

    test("Test create JWT", () => {
        user = {
            Id: "8ec2430e-c343-4403-9c9d-892f50a3e7f5",
            UserName: "Anna78@gmail.com",
			FullName: "Anna Jørgensen"
        }
        let token = createJwtToken(user, null, '10h')
        expect(token.length > 100).toBe(true);
    })

    test("Test create JWT w claims", () => {
        user = {
            Id: "8ec2430e-c343-4403-9c9d-892f50a3e7f5",
            UserName: "Anna78@gmail.com",
			FullName: "Anna Jørgensen"
        }
        const claims = {
            "role": 'User',
            "client1": "32445",
            "client2": "23423",
        };
        let token = createJwtToken(user, claims)
        expect(token.length > 100).toBe(true);
    })

    test("Verify JWT token", () => {
        user = {
            Id: "8ec2430e-c343-4403-9c9d-892f50a3e7f5",
            UserName: "Anna78@gmail.com",
			FullName: "Anna Jørgensen"
        }
        let token = createJwtToken(user, null)
        console.log(token)
        let result = validateJwtToken(token)
        expect(result.email).toBe('Anna78@gmail.com');
    })

    test("Verify invalid JWT token 1", () => {
        const token = jwt.sign(
            {
                email: "Dimling@user.com"
            },
            'SHITSTORM',
            {},
        )
        let result = validateJwtToken(token)
        expect(result).toBe(false);
    })

    test("Verify invalid JWT token 2", () => {
        const token = jwt.sign(
            {
                email: "magnea@uc.no",
                iss: "faggruppe.uc.no",
                aud: "uc.no",
            },
            'SHITSTORM',
            {
                algorithm: "HS256",
                expiresIn: '10 days'
            },
        )
        let result = validateJwtToken(token)
        expect(result).toBe(false);
    })

    test("Verify a valid remote created JWT token", () => {
        const token = jwt.sign(
            {
                email: "admin@uc.no",
                iss: "learniken.frontend.uc.no",
                aud: "uc.no",
            },
            'REVEN_OVERRASKER_GRISEN',
            {
                algorithm: "HS256",
                expiresIn: '10 days'
            },
        )
        let result = validateJwtToken(token)
        expect(result.email).toBe('admin@uc.no');
    })

    test("Verify a expired JWT token", () => {
        const token = jwt.sign(
            {
                email: "admin@uc.no",
                iss: "learniken.frontend.uc.no",
                aud: "uc.no",
            },
            'REVEN_OVERRASKER_GRISEN',
            {
                algorithm: "HS256",
                expiresIn: '-2h'
            },
        )
        let result = validateJwtToken(token)
        expect(result).toBe(false);
    })
})

