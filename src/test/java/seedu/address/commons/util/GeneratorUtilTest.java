package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GeneratorUtilTest {

    @Test
    void generateRandomPassword_withLengthSpecifications() {
        int expectedLength = 10;
        String password = GeneratorUtil.generateRandomPassword(expectedLength, true, true, true, true);
        assertTrue(expectedLength == password.length());
    }

    @Test
    void generateRandomPassword_withLowerSpecifications() {
        boolean hasLower = false;
        String password = GeneratorUtil.generateRandomPassword(8, hasLower, true, true, true);
        assertTrue(!password.matches("(?=.*[a-z]).*"));
    }

    @Test
    void generateRandomPassword_withUpperSpecifications() {
        boolean hasUpper = false;
        String password = GeneratorUtil.generateRandomPassword(8, true, hasUpper, true, true);
        assertTrue(!password.matches("(?=.*[A-Z]).*"));
    }

    @Test
    void generateRandomPassword_numSpecifications() {
        boolean hasNum = false;
        String password = GeneratorUtil.generateRandomPassword(8, true, true, hasNum, true);
        assertTrue(!password.matches("(?=.*[0-9]).*"));
    }

    @Test
    void generateRandomPassword_specialSpecifications() {
        boolean hasSpecial = false;
        String password = GeneratorUtil.generateRandomPassword(8, true, true, true, hasSpecial);
        assertTrue(!password.matches("(?=.*[~!@#$%^&*()_-]).*"));
    }
}
