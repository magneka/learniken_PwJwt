const crypto = require('crypto');
const { getBytes, hexStringToString, stringToHexString } = require('./base64Util')

/*
1st byte
--------
The value 0 to indicate a password hash from Identity Version 2 
the value 1 to indicate a password hash from Identity Version 3

For the latest version of Identity (3) (when the first byte is 1) you get the following:

2nd to 5th byte
---------------
An unsigned int that will contain a value from the enumeration Microsoft.AspNetCore.Cryptography.KeyDerivation.KeyDerviationPrf
0 for HMACSHA1
1 for HMACSHA256
2 for HMACSHA512

6th to 9th byte
---------------
An unsigned int that stores the number of iterations to perform when generating the hash (if you don’t know what this means go check out Things you wanted to know about storing passwords but were afraid to ask)

10th to 13th
------------
An unsigned int that stores the salt size (it’s always 16)

14th to 29th
------------
16 bytes where the random salt is stored

30th to 61th
------------
32 bytes that contain the hashed password

*/



exports.validatePassword = validatePassword = (password, hashedRecord) => {

    try {

        // La oss se på hexverdiene i fra hash posten i databasen
        const hexHashedPwd = stringToHexString(hashedRecord)

        // La oss plukke ut hexverdiene fra identityhashen
        let p1_1 = getBytes(hexHashedPwd, 1, 1) // 1st byte - identityversjon
        let p2_5 = getBytes(hexHashedPwd, 2, 5) // 2nd to 5th byte - hashalg
        let p6_9 = getBytes(hexHashedPwd, 6, 9) // 6th to 9th byte - kostnad/iterasjoner
        let p10_13 = getBytes(hexHashedPwd, 10, 13) // 10th to 13th - salt lengde
        let p14_29 = getBytes(hexHashedPwd, 14, 29) //14th to 29th - salt strengen
        let p30_61 = getBytes(hexHashedPwd, 30, 61) // 30th to 61th - resultat fra hashfunksjonen

        // Så konverterer vi dem til respektive datatyper (string og int)
        let _identityVersion = parseInt(p1_1, 16)
        let _hashAlgoIndex = parseInt(p2_5, 16)
        let _iterations = parseInt(p6_9, 16)
        let _saltSize = parseInt(p10_13, 16)
        let _saltString = p14_29
        let _hashedPassword = p30_61

        console.log(_saltString)

        // Så finner shortname på hashalgorigmen
        let _algAsString = getHashAlgorithmAsString(_hashAlgoIndex)

        // Kjør det vi har gjennom hashfunksjonen
        var nodeCrypto = crypto.pbkdf2Sync(
            Buffer.from(password),
            Buffer.from(_saltString, 'hex'),
            _iterations,
            _saltSize*2,
            _algAsString)

        // Strengen vi nå har skal være samme som lå i databasen!
        var derivedKeyOctets = nodeCrypto.toString('hex').toUpperCase()

        if (_identityVersion !== 1)
            return false

        if (derivedKeyOctets.indexOf(_hashedPassword) === 0) {
            return true;
        } else {
            return false;
        }

    } catch (error) {
        return false
    }
}

exports.newUserHashPassword = newUserHashPassword = (password) => {
    var saltString = crypto.randomBytes(16).toString('hex').toUpperCase();
    console.log(saltString)
    return hashPassword(password, 'SHA256', 10000, 16, saltString)
}

exports.hashPassword = hashPassword = (password, algorithm = 'SHA256', iterations = 10000, saltSize = 16, salt_string) => {

    try {

        // Først kjører vi passordet og salt gjennom hashfunksjonen
        var nodeCrypto = crypto.pbkdf2Sync(
            Buffer.from(password),
            Buffer.from(salt_string, 'hex'),
            iterations,
            saltSize*2,
            algorithm);

        // Så lager vi en hex string av resultatet
        var derivedKeyOctets = nodeCrypto.toString('hex').toUpperCase();

        // Lag hex av identityversjonen
        let _identityVersion = (1).toString(16).padStart(2, '0') // verdi 1

        // Lag hex av hashalgoritme index
        let _krypteringsalgo = hashAltorithmToIndex(algorithm).toString(16).padStart(8, '0')

        // Lag hex at antall iterasjoner (kostnaden)
        let _iterations = (iterations).toString(16).padStart(8, '0') //'00002710'

        // lag hex av lengden på salt strengen
        let _hexsaltsize = (saltSize).toString(16).padStart(8, '0') //'00000010'

        // Slå sammen alle hex strengene til en ny
        let BufferString = _identityVersion + _krypteringsalgo + _iterations + _hexsaltsize + salt_string + derivedKeyOctets

        // Så konverterer vi denne til en encoded string, som vi kan legge i databasen
        let result = hexStringToString(BufferString)

        return result

    } catch (error) {
        return ""
    }
}

const getHashAlgorithmAsString = (i) => {

    if (i == 0)
        return "SHA1"
    else if (i == 1)
        return "SHA256"
    else if (i == 2)
        return "SHA512"

    return ""
}

const hashAltorithmToIndex = (shortName) => {

    if (shortName === "SHA1")
        return 0
    else if (shortName === "SHA256")
        return 1
    else if (shortName == "SHA512")
        return 2

    return -1
}