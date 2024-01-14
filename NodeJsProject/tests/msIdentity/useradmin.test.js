//const { validatePassword, registerUser, hashPassword } = require("../../msIdentity/msIdentity")
const { loadDatabase, getUser, createUser, validateUser, initializeDatabase } = require("../../msIdentity/useradmin")

describe("Testing useramin layer", () => {

    beforeEach(() => {
        initializeDatabase();
        registerUser('magnea@uc.no', 'Magne Alvheim', 'Secret01!')
    });

    afterEach(() => {
        initializeDatabase();
        registerUser('magnea@uc.no', 'Magne Alvheim', 'Secret01!')
    });

    test("LoadUserDatabase", () => {

        let db = loadDatabase();
        expect(db.aspnetusers.length).toBe(1);
    })

    test("GetUser OK", () => {
        let user = getUser('magnea@uc.no');
        expect(user.FullName).toBe('Magne Alvheim');
    })

    test("RegisterUser with PW", () => {
        let result = registerUser('superman@krypton.uni', 'Clark Kent', 'N0Cryptonite4Me')
        expect(result).toBe('OK')
    })

    test("Validate password", () => {
        let result = validateUser('magnea@uc.no', 'Secret01!')
        expect(result).toBe(true)
    })

    test("RegisterUser and Validate", () => {
        let result1 = registerUser('admin1@uc.no', 'Administrator', 'NeverGonnaTell!')
        let result = validateUser('admin1@uc.no', 'NeverGonnaTell!')
        expect(result).toBe(true)
    })
})
