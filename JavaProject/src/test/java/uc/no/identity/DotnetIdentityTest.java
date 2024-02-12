package uc.no.identity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import uc.no.identity.msIdentity.MsIdentityService;

public class DotnetIdentityTest {
    
    @Test
    void validatePassword01() {

        String hashedPwd = "AQAAAAEAACcQAAAAEHJ76TcZq46JvLgCzCOWf55uupFZesz7ZUg4io6EIrQb5n/2pQM4dPTexgJmeHaZsQ==";
        String password = "Secret01!";
        boolean res = MsIdentityService.validatePassword(password, hashedPwd);

        assertTrue(res, "Password OK");        
    }

    @Test
    void validatePassword02() {

        String hashedPwd = "AQAAAAEAACcQAAAAEFF6kaUfVFtKu8JuTDq5PuuBXLGFIdrLCqUGivmyNiyQfn3WGCWBcr9wtSwJ9A1E7A==";
        String password = "Test123!";
        boolean res = MsIdentityService.validatePassword(password, hashedPwd);

        assertTrue(res, "Password OK");        
    }  

    @Test
    void checkInvalidPassword01() {

        String hashedPwd = "AQAAAAEAACcQAAAAEFF6kaUfVFtKu8JuTDq5PuuBXLGFIdrLCqUGivmyNiyQfn3WGCWBcr9wtSwJ9A1E7A==";
        String password = "Test123#";
        boolean res = MsIdentityService.validatePassword(password, hashedPwd);

        assertFalse(res, "Password OK");        
    }

    @Test
    void SelfHashedPassword01() {
        String password = "H3mmeligt!";
        String passwordHash = MsIdentityService.newUserPassword(password);

        boolean res = MsIdentityService.validatePassword(password, passwordHash);

        assertTrue(res, "Hashed password checkes out OK");        
    }

    @Test
    void SelfHashedPasswordInvalid01() {
        String password = "H3mmeligt!";
        String passwordHash = MsIdentityService.newUserPassword(password);

        boolean res = MsIdentityService.validatePassword("H3mmeligt#", passwordHash);

        assertFalse(res, "Hashed password checkes out OK");        
    }
}
