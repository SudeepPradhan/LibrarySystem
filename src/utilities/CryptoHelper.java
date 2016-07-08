package utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
 
public  class CryptoHelper {
    
    private static String SHA1_HASH = "SHA1";
    
    public static String getSha1Hash(String data){  
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(SHA1_HASH);
            byte[] digest = messageDigest.digest(data.getBytes()); 
            return Base64.getEncoder().encodeToString(digest);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CryptoHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
