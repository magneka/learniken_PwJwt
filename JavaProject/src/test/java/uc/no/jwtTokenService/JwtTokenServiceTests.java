package uc.no.jwtTokenService;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.nimbusds.jwt.JWTClaimsSet;

import uc.no.identity.jwtTokenService.IJwtTokenService;
import uc.no.identity.jwtTokenService.JwtTokenService;


public class JwtTokenServiceTests {

    @Test
    void generateToken() {

        Map<String, Object> claims = new HashMap<String, Object>() {{
            put("Id", "8ec2430e-c343-4403-9c9d-892f50a3e7f5");
            put("UserName", "Anna78@gmail.com");
            put("FullName", "Anna Jørgensen");
            put("Role", "User");
            put("Client1", "123123");
            put("Client2", "456456");
        }};
        String subject = "Test";

        String token = new JwtTokenService().generateToken(claims, subject);

        assertTrue(token.length() > 1, "Token generated OK");        
       
    }
     
    @Test
    void parseToken() {

        Map<String, Object> claims = new HashMap<String, Object>() {{
            put("Id", "8ec2430e-c343-4403-9c9d-892f50a3e7f5");
            put("UserName", "Anna78@gmail.com");
            put("FullName", "Anna Jørgensen");
            put("Role", "User");
            put("Client1", "123123");
            put("Client2", "456456");
        }};
        String subject = "Test";

        IJwtTokenService tokenUtil = new JwtTokenService();

        String token = tokenUtil.generateToken(claims, subject);

        JWTClaimsSet tokenOk = tokenUtil.parseJwt(token);

        System.out.println(token);

        assertTrue(tokenOk != null, "Token was valid");        
    
    }

    @Test
    void parseTokenErr() {

        Map<String, Object> claims = new HashMap<String, Object>() {{
            put("Id", "8ec2430e-c343-4403-9c9d-892f50a3e7f5");            
            put("jti", "8ec2430e-c343-4403-9c9d-892f50a3e7f5");            
            put("email", "ws_test_nte_elektro@kred.no"); 
            put("PasswordUser", "ws_test_nte_elektro@kred.no"); 
            put("PasswordUserId", "008a8533-6c2c-4975-8437-8a231eebc436");           
        }};
        String subject = "Test";

        IJwtTokenService tokenUtil = new JwtTokenService();

        String token = tokenUtil.generateInvalidToken(claims, subject);

        JWTClaimsSet tokenOk = tokenUtil.parseJwt(token);

        System.out.println(token); 

        assertFalse(tokenOk != null, "Token vas NOT valid");        
    }
    
    @Test
    void generateKfToken2() {

        Map<String, Object> claims = new HashMap<String, Object>() {{
            put("Id", "8ec2430e-c343-4403-9c9d-892f50a3e7f5");            
            put("jti", "8ec2430e-c343-4403-9c9d-892f50a3e7f5");            
            put("email", "ws_test_nte_elektro@kred.no"); 
            put("PasswordUser", "ws_test_nte_elektro@kred.no"); 
            put("PasswordUserId", "008a8533-6c2c-4975-8437-8a231eebc436");           
        }};
        String subject = "Test";

        IJwtTokenService tokenUtil = new JwtTokenService();

        String token = tokenUtil.generateKfToken(claims, subject);

        System.out.println(token);

        assertTrue(token.length() > 1, "Token for Kred.no generated OK");    
       
    }
}
