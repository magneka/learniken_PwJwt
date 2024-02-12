package uc.no.identity.jwtTokenService;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jwt.*;

@Component
public class JwtTokenService implements IJwtTokenService {

    // IKKE GJØR SÅNN SOM DETTE, DA HAVNER SECRETS I GIT, IKKE BRA!!
    // HMAC 512 krever 128 tegn avhengig av encoding utf8/ascii etc
    public static final String JWT_SECRET = "REVEN_OVERRASKER_GRISEN_DU_MÅ_HA nok bytes til å ikke få exception";
    public static final String JWT_ISSUER = "learniken.frontend.uc.no";
    public static final String JWT_AUDIENCE = "uc.no";
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60; // milliseks

    @Override
    public String generateToken(Map<String, Object> claims, String subject) {
       
        try {

            // Prepare some default claims
            var claimsSet = new JWTClaimsSet.Builder();
            claimsSet.subject("Learniken");
            claimsSet.issuer(JWT_ISSUER);
            claimsSet.audience(JWT_AUDIENCE);
            claimsSet.expirationTime(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000));
            claimsSet.issueTime(new Date(System.currentTimeMillis()));

            // Prepare all custom claims
            for (Map.Entry<String, Object> claim : claims.entrySet()) {
                System.out.println(claim.getKey() + ":" + claim.getValue());
                if (claim.getKey().toUpperCase().equals("ID"))
                    claimsSet.jwtID(claim.getValue().toString());
                else
                    claimsSet.claim(claim.getKey(), claim.getValue());
            }

            // Sett opp header med algoritme og type
            JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512, JOSEObjectType.JWT, 
                    null, null, null, null, null,
                    null, null, null, null, null, null);
            SignedJWT signedJWT = new SignedJWT(jwsHeader, claimsSet.build());

            // Apply the HMAC protection
            String inputString = JWT_SECRET;
            byte[] byteSecret = inputString.getBytes();
            JWSSigner signer = new MACSigner(byteSecret);
            signedJWT.sign(signer);

            // Serialiser så vi får en base64 encoded streng vi kan bruke i html sammenheng
            // eyJhbGciOiJIUzI1NiJ9.SGVsbG8sIHdvcmxkIQ.onO9Ihudz3WkiauDO2Uhyuz0Y18UASXlSc1eS0NkWyA
            String s = signedJWT.serialize();

            System.out.println(s);

            return s;

        } catch (JOSEException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public String generateInvalidToken(Map<String, Object> claims, String subject) {

        try {

            // Prepare some default claims
            var claimsSet = new JWTClaimsSet.Builder();
            claimsSet.subject("Learniken");
            claimsSet.issuer(JWT_ISSUER);
            claimsSet.audience(JWT_AUDIENCE);
            claimsSet.expirationTime(new Date(System.currentTimeMillis() - JWT_TOKEN_VALIDITY * 1000));
            claimsSet.issueTime(new Date(System.currentTimeMillis()));

            // Prepare all custom claims
            for (Map.Entry<String, Object> claim : claims.entrySet()) {
                System.out.println(claim.getKey() + ":" + claim.getValue());
                if (claim.getKey().toUpperCase().equals("ID"))
                    claimsSet.jwtID(claim.getValue().toString());
                else
                    claimsSet.claim(claim.getKey(), claim.getValue());
            }

            // Sett opp header med algoritme og type
            JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256, JOSEObjectType.JWT, 
                    null, null, null, null, null,
                    null, null, null, null, null, null);
            SignedJWT signedJWT = new SignedJWT(jwsHeader, claimsSet.build());

            // Apply the HMAC protection
            String inputString = JWT_SECRET;
            byte[] byteSecret = inputString.getBytes();
            JWSSigner signer = new MACSigner(byteSecret);
            signedJWT.sign(signer);

            // Serialiser så vi får en base64 encoded streng vi kan bruke i html sammenheng
            // eyJhbGciOiJIUzI1NiJ9.SGVsbG8sIHdvcmxkIQ.onO9Ihudz3WkiauDO2Uhyuz0Y18UASXlSc1eS0NkWyA
            String s = signedJWT.serialize();

            System.out.println(s);

            return s;

        } catch (JOSEException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public String generateKfToken(Map<String, Object> claims, String subject) {

        /*
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .setIssuer("http://localhost")
                .setAudience("http://localhost")
                .signWith(SignatureAlgorithm.HS512, "0123456789ABCDEF")
                .setHeaderParam("typ", "jwt")
                .compact();
        */

        try {

            // Prepare some default claims
            var claimsSet = new JWTClaimsSet.Builder();
            claimsSet.subject("Learniken");
            claimsSet.issuer(JWT_ISSUER);
            claimsSet.audience(JWT_AUDIENCE);
            claimsSet.expirationTime(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000));
            claimsSet.issueTime(new Date(System.currentTimeMillis()));

            // Prepare all custom claims
            for (Map.Entry<String, Object> claim : claims.entrySet()) {
                System.out.println(claim.getKey() + ":" + claim.getValue());
                if (claim.getKey().toUpperCase().equals("ID"))
                    claimsSet.jwtID(claim.getValue().toString());
                else
                    claimsSet.claim(claim.getKey(), claim.getValue());
            }

            // Sett opp header med algoritme og type
            JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512, JOSEObjectType.JWT, 
                    null, null, null, null, null,
                    null, null, null, null, null, null);
            SignedJWT signedJWT = new SignedJWT(jwsHeader, claimsSet.build());

            // Apply the HMAC protection
            String inputString = "0123456789ABCDEF-0123456789ABCDEF-0123456789ABCDEF-0123456789ABCDEF";
            byte[] byteSecret = inputString.getBytes();
            JWSSigner signer = new MACSigner(byteSecret);
            signedJWT.sign(signer);

            // Serialiser så vi får en base64 encoded streng vi kan bruke i html sammenheng
            // eyJhbGciOiJIUzI1NiJ9.SGVsbG8sIHdvcmxkIQ.onO9Ihudz3WkiauDO2Uhyuz0Y18UASXlSc1eS0NkWyA
            String s = signedJWT.serialize();

            System.out.println(s);

            return s;

        } catch (JOSEException e) {
            e.printStackTrace();
            return "";
        }   
    }

    @Override
    public JWTClaimsSet parseJwt(String jwtString) {

        JWTClaimsSet result = null;

        try {

            SignedJWT signedJWT;
            signedJWT = SignedJWT.parse(jwtString);

            JWSVerifier verifier = new MACVerifier(JWT_SECRET);

            if (!signedJWT.verify(verifier))
                return null;

            var algorithm = signedJWT.getHeader().getAlgorithm();
            if (!algorithm.getName().equals("HS512"))
                return null;

            var claims = signedJWT.getJWTClaimsSet();

            if (new Date().after(signedJWT.getJWTClaimsSet().getExpirationTime()))
                return null;

            if (!claims.getAudience().contains(JWT_AUDIENCE))
                return null;

            if (!claims.getIssuer().equals(JWT_ISSUER))
                return null;

            result = claims;
    
        } catch (ParseException | JOSEException e) {            
            e.printStackTrace();
            return null;
        }

        return result;     
    }
}
