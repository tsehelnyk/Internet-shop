package mate.academy.internetshop.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import mate.academy.internetshop.controller.AddItemController;
import mate.academy.internetshop.exception.DataProcessingException;
import mate.academy.internetshop.exceptions.HashGeneratingException;
import org.apache.log4j.Logger;

public class HashUtil {

    private static final Logger LOGGER = Logger.getLogger(AddItemController.class);

    public static byte[] getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) throws HashGeneratingException {
        StringBuilder hashedPassword = new StringBuilder();

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(salt);
            byte[] bytes = messageDigest.digest(password.getBytes());

            for (byte b : bytes) {
                hashedPassword.append(String.format("%02x", b));
            }

        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Can't generate hash: ", e);
            throw new HashGeneratingException("Failed to get hash: " + e);
        }

        return hashedPassword.toString();
    }
}
