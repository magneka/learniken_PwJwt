package uc.no.identity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import uc.no.identity.base64encoding.Base64Encoding;

@SpringBootTest
public class Base64EncodingTest {

    @Test
	void enCode01() {
        String originalInput = "test input";
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());

        assertEquals("dGVzdCBpbnB1dA==", encodedString,  "Encoding seems to work");
	}

    @Test
    void deCode01() {
        byte[] decodedBytes = Base64.getDecoder().decode("dGVzdCBpbnB1dA==");
        String decodedString = new String(decodedBytes);

        assertEquals("test input", decodedString,  "Decoding seems to work");
	}

    @Test
	void enCode02() {
        String originalInput = "test input";
        String encodedString = Base64Encoding.encode(originalInput);

        assertEquals("dGVzdCBpbnB1dA==", encodedString,  "Encoding seems to work");
	}

    @Test
    void deCode02() {
        String decodedString = Base64Encoding.decode("dGVzdCBpbnB1dA==");
        
        assertEquals("test input", decodedString,  "Decoding seems to work");
	}
    
}
