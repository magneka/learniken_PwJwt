const jwt = require('jsonwebtoken');

const TOKENSECRET = 'REVEN_OVERRASKER_GRISEN'
const ISSUER = "learniken.frontend.uc.no"
const AUDIENCE = "uc.no"
const DURATION = '10h'

exports.createJwtToken = createJwtToken = (user, claims) => {

    const tokenPayload = {
        Id: user.Id,
        email: user.UserName,
        fullname: user.FullName,        
        iss: ISSUER,
        aud: AUDIENCE,
    };

    if (claims) {
        for (const key in claims) {
            console.log(`${key}: ${claims[key]}`);
            tokenPayload[key] = claims[key]
        }        
    }

    const accessToken = jwt.sign(
        tokenPayload,
        TOKENSECRET,
        {
            algorithm: "HS256",
            expiresIn: DURATION,
        }
    )

    return accessToken
}

exports.validateJwtToken = validateJwtToken = (accessToken) => {

    verifyOptions = {
        algorithms: ["HS256"],
        audience: AUDIENCE,
        issuer: ISSUER 
    }
    /*
        OPSJONER for validering
        algorithms?: Algorithm[] | undefined;
        audience?: string | RegExp | Array<string | RegExp> | undefined;
        clockTimestamp?: number | undefined;
        clockTolerance?: number | undefined;
        issuer?: string | string[] | undefined;
        ignoreExpiration?: boolean | undefined;
        ignoreNotBefore?: boolean | undefined;
        jwtid?: string | undefined;    
        nonce?: string | undefined;
        subject?: string | undefined;
        maxAge?: string | number | undefined;
        allowInvalidAsymmetricKeyTypes?: boolean | undefined;
     */

    try {
        const result = jwt.verify(
            accessToken, 
            TOKENSECRET,
            verifyOptions
        );  
        return result    
    } catch (error) {
        console.log(error)
        return false   
    }
    
        
    return result
}