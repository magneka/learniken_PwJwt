const { validatePassword, registerUser, hashPassword, newUserHashPassword } = require("../../msIdentity/msIdentity")

describe("MS Identity Passwordhash testing", () => {

    test("Check valid password1", () => {

        const hashedPwd = "AQAAAAEAACcQAAAAEHJ76TcZq46JvLgCzCOWf55uupFZesz7ZUg4io6EIrQb5n/2pQM4dPTexgJmeHaZsQ==";
        const password = 'Secret01!'
        const check = validatePassword(password, hashedPwd)

        expect(check).toBe(true);
    });

    test("Check valid password2", () => {

        const hashedPwd = "AQAAAAEAACcQAAAAEFF6kaUfVFtKu8JuTDq5PuuBXLGFIdrLCqUGivmyNiyQfn3WGCWBcr9wtSwJ9A1E7A==";
        const password = 'Test123!'
        const check = validatePassword(password, hashedPwd)

        expect(check).toBe(true);
    });

    test("Check invalid password", () => {

        const hashedPwd = "AQAAAAEAACcQAAAAEFF6kaUfVFtKu8JuTDq5PuuBXLGFIdrLCqUGivmyNiyQfn3WGCWBcr9wtSwJ9A1E7A==";
        const password = 'test123#'
        const check = validatePassword(password, hashedPwd)

        expect(check).toBe(false);
    });


    test("Check invalid password", () => {

        const hashedPwd = "ABAAAAEAACcQAAAAEFF6kaUfVFtKu8JuTDq5PuuBXLGFIdrLCqUGivmyNiyQfn3WGCWBcr9wtSwJ9A1E7A==";
        const password = 'Test123!'
        const check = validatePassword(password, hashedPwd)

        expect(check).toBe(false);
    });

    test("Hash password static hash", () => {
        
        const password = 'NeverGonnaTell!'
        const hashedPwd = hashPassword(password, 'SHA256', 10000, 16, '517A91A51F545B4ABBC26E4C3AB93EEB')

        const check = validatePassword(password, hashedPwd)
        expect(check).toBe(true);
    });

    test("Hash password random hash", () => {
        
        const password = 'NeverGonnaTell!'
        const hashedPwd = newUserHashPassword(password)

        const check = validatePassword(password, hashedPwd)
        expect(check).toBe(true);
    });
})