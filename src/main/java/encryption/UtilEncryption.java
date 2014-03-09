package encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.core.internal.preferences.Base64;

public class UtilEncryption {
    private static final Logger logger = Logger.getLogger(UtilEncryption.class.getName());
    protected static final byte[] hex = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
            'f' };

    public static void main(String[] args) {
        String pwd = "holger";
        // System.out.println("pwdMD5 plain" + UtilEncryption.md5(pwd));
        // System.out.println("pwdMD5 Base64:" + UtilEncryption.md5Base64(pwd));
        System.out.println("pwdMD5 Hex:" + UtilEncryption.md5Hex(pwd));
        // System.out.println("pwdSHA plain:" + UtilEncryption.sha(pwd));
        // System.out.println("pwdSHA Base64:" + UtilEncryption.shaBase64(pwd));
        // System.out.println("pwdSHA Hex:" + UtilEncryption.shaHex(pwd));
    }

    private static String toHex(byte[] b) {
        StringBuilder s = new StringBuilder(2 * b.length);
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xff;
            s.append((char) hex[v >> 4]);
            s.append((char) hex[v & 0xf]);
        }
        return s.toString();
    }

    private static byte[] md5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(s.getBytes());
            return md.digest();
        } catch (NoSuchAlgorithmException ex) {
            logger.log(Level.ERROR, null, ex);
        }
        return null;
    }

    @SuppressWarnings("unused")
    private static String md5Base64(String s) {
        return new String(Base64.encode(UtilEncryption.md5(s)));
    }

    private static String md5Hex(String s) {
        byte[] buf = UtilEncryption.md5(s);
        return UtilEncryption.toHex(buf);
    }

    private static byte[] sha(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.reset();
            md.update(s.getBytes());
            return md.digest();
        } catch (NoSuchAlgorithmException ex) {
            logger.log(Level.ERROR, null, ex);
        }
        return null;
    }

    @SuppressWarnings("unused")
    private static String shaBase64(String s) {
        return new String(Base64.encode(UtilEncryption.sha(s)));
    }

    @SuppressWarnings("unused")
    private static String shaHex(String s) {
        byte[] buf = UtilEncryption.sha(s);
        return UtilEncryption.toHex(buf);
    }

    public static String encryptPassword(String newPassword) {
        return UtilEncryption.md5Hex(newPassword);
    }

}
