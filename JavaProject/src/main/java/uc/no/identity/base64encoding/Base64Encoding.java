package uc.no.identity.base64encoding;

import java.util.Base64;

public class Base64Encoding {
    
    public static String encode (String originalInput) {
        String result = "";

        result = Base64.getEncoder().encodeToString(originalInput.getBytes());

        return result;
    }

    public static String decode (String originalInput) {
        String result = "";

        byte[] decodedBytes = Base64.getDecoder().decode(originalInput);
        result = new String(decodedBytes);

        return result;
    }
}
