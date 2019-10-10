package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EncryptionUtilTest {
    @Test
    public void encrypt_decrypt_success() {
        String testString = "hello world";
        String password = "password";
        System.out.println(EncryptionUtil.decryptBytesToString(EncryptionUtil.encryptBytesFromString(testString, password),
                password));
        assertEquals(testString,
                EncryptionUtil.decryptBytesToString(EncryptionUtil.encryptBytesFromString(testString, password),
                        password));
    }

//    @Test
//    public void encrypt_decrypt_string_success() {
//        String testString = "hello world";
//        String password = "password";
//        System.out.println(EncryptionUtil.decryptString(EncryptionUtil.encryptString(testString, password),
//                password));
//        assertEquals(testString,
//                EncryptionUtil.decryptString(EncryptionUtil.encryptString(testString, password),
//                        password));
//    }

    @Test
    public void encrypt_decrypt_file_success() {
        try {
            String testString = "hello world";
            String password = "password";
            byte[] encryptedByteArray = EncryptionUtil.encryptBytesFromString(testString, password);
            Files.write(Paths.get("test.txt"), encryptedByteArray);
            byte[] encryptedByteArrayFromFile = Files.readAllBytes(Paths.get("test.txt"));
            assertEquals(encryptedByteArray.length, encryptedByteArrayFromFile.length);
            assertEquals(testString, EncryptionUtil.decryptBytesToString(encryptedByteArrayFromFile, password));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
