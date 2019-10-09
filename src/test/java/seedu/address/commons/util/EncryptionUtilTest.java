package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class EncryptionUtilTest {
    @Test
    public void encrypt_decrypt_success() {
        String testString = "hello world";
        String password = "password";
        assertEquals(testString,
                EncryptionUtil.decryptBytesToString(EncryptionUtil.encryptBytesFromString(testString, password),
                        password));
    }
}
