package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.security.GeneralSecurityException;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TestUtil;

public class EncryptionUtilTest {
    @Test
    public void encryptDecryptBytes_success() {
        String testString = "hello world";
        String password = "password";
        try {
            assertEquals(testString,
                    EncryptionUtil.decryptBytesToString(EncryptionUtil.encryptBytesFromString(testString, password),
                            password));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void encryptDecryptFromFile_success() {
        try {
            String testString = "hello world";
            String password = "password";
            byte[] encryptedByteArray = EncryptionUtil.encryptBytesFromString(testString, password);
            Files.write(TestUtil.getFilePathInSandboxFolder("test.txt"), encryptedByteArray);
            byte[] encryptedByteArrayFromFile =
                    Files.readAllBytes(TestUtil.getFilePathInSandboxFolder("test.txt"));
            assertEquals(encryptedByteArray.length, encryptedByteArrayFromFile.length);
            assertEquals(testString, EncryptionUtil.decryptBytesToString(encryptedByteArrayFromFile, password));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
