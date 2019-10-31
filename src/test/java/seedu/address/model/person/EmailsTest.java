package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

class EmailsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Emails(null));
    }

    @Test
    public void constructor_validInput_returnsInstance() {
        assertSame(new Emails(new HashMap<>()).getClass(), Emails.class);
    }

    @Test
    public void getEmailsOfType() {
        Email nusEmail = new Email("alice@u.nus.edu");
        Email personalEmail = new Email("alice@gmail.com");
        Emails emails = new Emails().addNusEmail(nusEmail).addPersonalEmail(personalEmail);

        assertIterableEquals(emails.getEmailsOfType(EmailType.NUS), Arrays.asList(nusEmail));
        assertIterableEquals(emails.getEmailsOfType(EmailType.PERSONAL), Arrays.asList(personalEmail));
    }
}
