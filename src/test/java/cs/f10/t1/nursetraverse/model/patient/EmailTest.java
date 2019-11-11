package cs.f10.t1.nursetraverse.model.patient;

import static cs.f10.t1.nursetraverse.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EmailTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Email(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "";
        assertThrows(IllegalArgumentException.class, () -> new Email(invalidEmail));
    }

    @Test
    public void isValidEmail() {
        // null email
        assertThrows(NullPointerException.class, () -> Email.isValidEmail(null));

        // blank email
        assertFalse(Email.isValidEmail("")); // empty string
        assertFalse(Email.isValidEmail(" ")); // spaces only

        // missing parts
        assertFalse(Email.isValidEmail("@example.com")); // missing local part
        assertFalse(Email.isValidEmail("peterjackexample.com")); // missing '@' symbol
        assertFalse(Email.isValidEmail("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Email.isValidEmail("peterjack@-")); // invalid domain name
        assertFalse(Email.isValidEmail("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(Email.isValidEmail("peter jack@example.com")); // spaces in local part
        assertFalse(Email.isValidEmail("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(Email.isValidEmail(" peterjack@example.com")); // leading space
        assertFalse(Email.isValidEmail("peterjack@example.com ")); // trailing space
        assertFalse(Email.isValidEmail("peterjack@@example.com")); // double '@' symbol
        assertFalse(Email.isValidEmail("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(Email.isValidEmail("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(Email.isValidEmail("peterjack@.example.com")); // domain name starts with a period
        assertFalse(Email.isValidEmail("peterjack@example.com.")); // domain name ends with a period
        assertFalse(Email.isValidEmail("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(Email.isValidEmail("peterjack@example.com-")); // domain name ends with a hyphen

        // valid email
        assertTrue(Email.isValidEmail("PeterJack_1190@example.com"));
        assertTrue(Email.isValidEmail("a@bc")); // minimal
        assertTrue(Email.isValidEmail("test@localhost")); // alphabets only
        assertTrue(Email.isValidEmail("!#$%&'*+/=?`{|}~^.-@example.org")); // special characters local part
        assertTrue(Email.isValidEmail("123@145")); // numeric local part and domain name
        assertTrue(Email.isValidEmail("a1+be!@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(Email.isValidEmail("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(Email.isValidEmail("if.you.dream.it_you.can.do.it@example.com")); // long local part
    }

    @Test
    public void equals() {
        assertNotEquals(new Email("PeterJack_1190@example.com"), new Email("PeterJack_1190@example.co"));
        assertNotEquals(new Email("a@bc"), new Email("a@cc"));
        assertNotEquals(new Email("test@localhost"), new Email("test@localhos"));
        assertNotEquals(new Email("!#$%&'*+/=?`{|}~^.-@example.org"), new Email("!#$%&'*+/=?`{|}~^.-@example.or"));
        assertNotEquals(new Email("123@145"), new Email("123@14"));
        assertNotEquals(new Email("a1+be!@example1.com"), new Email("a1+be!@example1.co"));
        assertNotEquals(new Email("peter_jack@very-very-very-long-example.com"),
                new Email("peter_jack@very-very-very-long-example.co"));
        assertNotEquals(new Email("if.you.dream.it_you.can.do.it@example.com"),
                new Email("if.you.dream.it_you.can.do.it@example.co"));

        assertEquals(new Email("PeterJack_1190@example.com"), new Email("PeterJack_1190@example.com"));
        assertEquals(new Email("a@bc"), new Email("a@bc"));
        assertEquals(new Email("test@localhost"), new Email("test@localhost"));
        assertEquals(new Email("!#$%&'*+/=?`{|}~^.-@example.org"), new Email("!#$%&'*+/=?`{|}~^.-@example.org"));
        assertEquals(new Email("123@145"), new Email("123@145"));
        assertEquals(new Email("a1+be!@example1.com"), new Email("a1+be!@example1.com"));
        assertEquals(new Email("peter_jack@very-very-very-long-example.com"),
                new Email("peter_jack@very-very-very-long-example.com"));
        assertEquals(new Email("if.you.dream.it_you.can.do.it@example.com"),
                new Email("if.you.dream.it_you.can.do.it@example.com"));
    }
}
