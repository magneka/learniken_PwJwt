package uc.no.identity.msIdentity;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

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

public class MsIdentityService {

        private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
        public static String bytesToHex(byte[] bytes) {
            char[] hexChars = new char[bytes.length * 2];
            for ( int j = 0; j < bytes.length; j++ ) {
                int v = bytes[j] & 0xFF;
                hexChars[j * 2] = hexArray[v >>> 4];
                hexChars[j * 2 + 1] = hexArray[v & 0x0F];
            }
            return new String(hexChars);
        }
    


    public static boolean validatePassword (String password, String hashedString) {
        
        //String result = "";

        byte[] decodedBytes = Base64.getDecoder().decode(hashedString);
        String hexHashedPassword = bytesToHex(decodedBytes);

        byte[] p1_1 =  Arrays.copyOfRange(decodedBytes, 0, 1);   
        byte[] p2_5 =  Arrays.copyOfRange(decodedBytes, 1, 5);   
        byte[] p6_9 =  Arrays.copyOfRange(decodedBytes, 5, 9);  
        byte[] p10_13 =  Arrays.copyOfRange(decodedBytes, 9, 13);  
        byte[] p14_29 =  Arrays.copyOfRange(decodedBytes, 13, 29);  
        byte[] p30_61 =  Arrays.copyOfRange(decodedBytes, 29, 61);          

        int _identityVersion = new BigInteger(p1_1).intValue();        
        int _hashAlgoIndex = new BigInteger(p2_5).intValue();     
        int _iterations = new BigInteger(p6_9).intValue();     
        int _saltSize = new BigInteger(p10_13).intValue();     
        String _saltString = bytesToHex(p14_29);
        String _hashedPassword = bytesToHex(p30_61);

        String _algAsString = getHashAlgorithmAsString(_hashAlgoIndex);

        byte[] encodedhash;
        String derivedHashedPassword = "";

        if (_identityVersion != 1)
            return false;
        
        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), p14_29, _iterations, 256);        
            
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            encodedhash = factory.generateSecret(spec).getEncoded();
            derivedHashedPassword = bytesToHex(encodedhash);

            // Er det likhet, er passordet korrekt!
            return _hashedPassword.equals(derivedHashedPassword);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static String newUserPassword (String password) {

        try {
            byte[] saltAsBytes = new byte[16];
            SecureRandom.getInstanceStrong().nextBytes(saltAsBytes);

            String passwordHash = hashPassword(password, "PBKDF2WithHmacSHA256", 10000, 16, saltAsBytes);
            return passwordHash;
        } catch (NoSuchAlgorithmException e) {
           e.printStackTrace();
        }
        return "";

    }

    public static String hashPassword (String password, String algorithm, int iterations, int saltSize, byte[] saltAsBytes) {
                
        try {

            KeySpec spec = new PBEKeySpec(password.toCharArray(), saltAsBytes, iterations, 256);        
            
            SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm);
            byte[] encodedhash = factory.generateSecret(spec).getEncoded();
            String derivedHashedPassword = bytesToHex(encodedhash);
            
            byte[] p1_1 = new byte[1];  
            p1_1[0] = 1; 
            byte[] p2_5 = ByteBuffer.allocate(4).putInt(1).array();
            byte[] p6_9 = ByteBuffer.allocate(4).putInt(iterations).array();        
            byte[] p10_13 =  ByteBuffer.allocate(4).putInt(saltSize).array();
            byte[] p14_29 = saltAsBytes;
            byte[] p30_61  = encodedhash;

            // Lag en ny komplett record
            byte[] combined = new byte[61];
            ByteBuffer buffer = ByteBuffer.wrap(combined);
            buffer.put(p1_1);
            buffer.put(p2_5);
            buffer.put(p6_9);
            buffer.put(p10_13);
            buffer.put(p14_29);
            buffer.put(p30_61);
            combined = buffer.array();

            String passwordHash = Base64.getEncoder().encodeToString(combined);

            return passwordHash;

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String getHashAlgorithmAsString (int i) {

        if (i == 0)
            return "SHA1";
        else if (i == 1)
            return "PBKDF2WithHmacSHA256";
        else if (i == 2)
            return "SHA512";
    
        return "";
    }
}
