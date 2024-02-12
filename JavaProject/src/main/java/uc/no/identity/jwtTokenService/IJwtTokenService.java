package uc.no.identity.jwtTokenService;        

import java.util.Map;

import com.nimbusds.jwt.JWTClaimsSet;

public interface IJwtTokenService {

    String generateToken(Map<String, Object> claims, String subject);

    String generateInvalidToken(Map<String, Object> claims, String subject);

    String generateKfToken(Map<String, Object> claims, String subject);

    JWTClaimsSet parseJwt(String jwtString);

}