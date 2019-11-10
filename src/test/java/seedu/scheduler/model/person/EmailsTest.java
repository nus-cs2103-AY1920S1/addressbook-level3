package seedu.scheduler.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_NUS_WORK_EMAIL_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_NUS_WORK_EMAIL_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_PERSONAL_EMAIL_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_PERSONAL_EMAIL_BOB;
import static seedu.scheduler.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
    public void addPersonalEmail_validEmail_success() {
        HashMap<EmailType, List<Email>> hashMap = new HashMap<>();
        hashMap.put(EmailType.PERSONAL, Arrays.asList(new Email(VALID_PERSONAL_EMAIL_AMY),
                new Email(VALID_PERSONAL_EMAIL_BOB)));
        Emails expected = new Emails(hashMap);

        Emails emails = new Emails();
        emails.addPersonalEmail(new Email(VALID_PERSONAL_EMAIL_AMY));
        emails.addPersonalEmail(new Email(VALID_PERSONAL_EMAIL_BOB));

        assertEquals(expected, emails);
    }

    @Test
    public void addNusEmail_validEmail_success() {
        HashMap<EmailType, List<Email>> hashMap = new HashMap<>();
        hashMap.put(EmailType.NUS, Arrays.asList(new Email(VALID_NUS_WORK_EMAIL_AMY),
                new Email(VALID_NUS_WORK_EMAIL_BOB)));
        Emails expected = new Emails(hashMap);

        Emails emails = new Emails();
        emails.addNusEmail(new Email(VALID_NUS_WORK_EMAIL_AMY));
        emails.addNusEmail(new Email(VALID_NUS_WORK_EMAIL_BOB));

        assertEquals(expected, emails);
    }

    @Test
    public void addAll_validEmail_success() {
        List<Email> personalEmails = Arrays.asList(new Email(VALID_PERSONAL_EMAIL_AMY),
                new Email(VALID_PERSONAL_EMAIL_BOB));
        List<Email> nusEmails = Arrays.asList(new Email(VALID_NUS_WORK_EMAIL_AMY),
                new Email(VALID_NUS_WORK_EMAIL_BOB));

        HashMap<EmailType, List<Email>> hashMap = new HashMap<>();
        hashMap.put(EmailType.PERSONAL, personalEmails);
        hashMap.put(EmailType.NUS, nusEmails);
        Emails expected = new Emails(hashMap);

        Emails emails = new Emails();
        emails.addAll(nusEmails, personalEmails);

        assertEquals(expected, emails);
    }

    @Test
    public void getEmailsOfType_validEmail_success() {
        Email nusEmail = new Email(VALID_NUS_WORK_EMAIL_AMY);
        Email personalEmail = new Email(VALID_PERSONAL_EMAIL_AMY);
        Emails emails = new Emails().addNusEmail(nusEmail).addPersonalEmail(personalEmail);

        assertIterableEquals(emails.getEmailsOfType(EmailType.NUS), Arrays.asList(nusEmail));
        assertIterableEquals(emails.getEmailsOfType(EmailType.PERSONAL), Arrays.asList(personalEmail));
    }

    @Test
    public void hasEmailsOfType_hasPersonalEmail_returnsTrue() {
        Email personalEmail = new Email(VALID_PERSONAL_EMAIL_AMY);
        Emails emails = new Emails().addPersonalEmail(personalEmail);
        assertTrue(emails.hasEmailsOfType(EmailType.PERSONAL));
    }

    @Test
    public void hasEmailsOfType_hasNusEmail_returnsTrue() {
        Email nusEmail = new Email(VALID_NUS_WORK_EMAIL_AMY);
        Emails emails = new Emails().addNusEmail(nusEmail);
        assertTrue(emails.hasEmailsOfType(EmailType.NUS));
    }

    @Test
    public void hasEmailsOfType_noEmails_returnsFalse() {
        Emails emails = new Emails();
        assertFalse(emails.hasEmailsOfType(EmailType.PERSONAL));
        assertFalse(emails.hasEmailsOfType(EmailType.NUS));
    }

    @Test
    public void hasEmailsOfType_noPersonalEmail_returnsFalse() {
        Email nusEmail = new Email(VALID_NUS_WORK_EMAIL_AMY);
        Emails emails = new Emails().addNusEmail(nusEmail);
        assertFalse(emails.hasEmailsOfType(EmailType.PERSONAL));
    }

    @Test
    public void hasEmailsOfType_noNusEmail_returnsFalse() {
        Email personalEmail = new Email(VALID_PERSONAL_EMAIL_AMY);
        Emails emails = new Emails().addPersonalEmail(personalEmail);
        assertFalse(emails.hasEmailsOfType(EmailType.NUS));
    }

    @Test
    public void getAllEmails_validEmails_success() {
        Emails emails = new Emails()
                .addNusEmail(new Email(VALID_NUS_WORK_EMAIL_AMY))
                .addNusEmail(new Email(VALID_NUS_WORK_EMAIL_BOB))
                .addPersonalEmail(new Email(VALID_PERSONAL_EMAIL_AMY))
                .addPersonalEmail(new Email(VALID_PERSONAL_EMAIL_BOB));

        List<Email> personalEmails = Arrays.asList(new Email(VALID_PERSONAL_EMAIL_AMY),
                new Email(VALID_PERSONAL_EMAIL_BOB));
        List<Email> nusEmails = Arrays.asList(new Email(VALID_NUS_WORK_EMAIL_AMY),
                new Email(VALID_NUS_WORK_EMAIL_BOB));
        HashMap<EmailType, List<Email>> hashMap = new HashMap<>();
        hashMap.put(EmailType.PERSONAL, personalEmails);
        hashMap.put(EmailType.NUS, nusEmails);

        assertEquals(emails.getAllEmails(), hashMap);
    }

    @Test
    public void toString_noEmails_returnsEmptyString() {
        Emails emails = new Emails();
        assertEquals("", emails.toString());
    }

    @Test
    public void toString_validNusEmail_returnsString() {
        Emails emails = new Emails().addNusEmail(new Email(VALID_NUS_WORK_EMAIL_AMY))
                .addNusEmail(new Email(VALID_NUS_WORK_EMAIL_BOB));
        String expected = EmailType.NUS.toString() + " [" + VALID_NUS_WORK_EMAIL_AMY + ", "
                + VALID_NUS_WORK_EMAIL_BOB + "]";
        assertEquals(expected, emails.toString());
    }

    @Test
    public void toString_validPersonalEmail_returnsString() {
        Emails emails = new Emails().addPersonalEmail(new Email(VALID_PERSONAL_EMAIL_AMY))
                .addPersonalEmail(new Email(VALID_PERSONAL_EMAIL_BOB));
        String expected = EmailType.PERSONAL.toString() + " [" + VALID_PERSONAL_EMAIL_AMY + ", "
                + VALID_PERSONAL_EMAIL_BOB + "]";
        assertEquals(expected, emails.toString());
    }

    @Test
    public void toString_validNusAndPersonalEmails_returnsString() {
        Emails emails = new Emails()
                .addNusEmail(new Email(VALID_NUS_WORK_EMAIL_AMY))
                .addNusEmail(new Email(VALID_NUS_WORK_EMAIL_BOB))
                .addPersonalEmail(new Email(VALID_PERSONAL_EMAIL_AMY))
                .addPersonalEmail(new Email(VALID_PERSONAL_EMAIL_BOB));
        String expected = EmailType.PERSONAL.toString() + " [" + VALID_PERSONAL_EMAIL_AMY + ", "
                + VALID_PERSONAL_EMAIL_BOB + "] " + EmailType.NUS.toString() + " [" + VALID_NUS_WORK_EMAIL_AMY
                + ", " + VALID_NUS_WORK_EMAIL_BOB + "]";
        assertEquals(expected, emails.toString());
    }

    @Test
    public void hashCode_validEmails_success() {
        Emails emails = new Emails()
                .addNusEmail(new Email(VALID_NUS_WORK_EMAIL_AMY))
                .addNusEmail(new Email(VALID_NUS_WORK_EMAIL_BOB))
                .addPersonalEmail(new Email(VALID_PERSONAL_EMAIL_AMY))
                .addPersonalEmail(new Email(VALID_PERSONAL_EMAIL_BOB));

        List<Email> personalEmails = Arrays.asList(new Email(VALID_PERSONAL_EMAIL_AMY),
                new Email(VALID_PERSONAL_EMAIL_BOB));
        List<Email> nusEmails = Arrays.asList(new Email(VALID_NUS_WORK_EMAIL_AMY),
                new Email(VALID_NUS_WORK_EMAIL_BOB));
        HashMap<EmailType, List<Email>> hashMap = new HashMap<>();
        hashMap.put(EmailType.PERSONAL, personalEmails);
        hashMap.put(EmailType.NUS, nusEmails);

        assertEquals(hashMap.hashCode(), emails.hashCode());
    }
}
