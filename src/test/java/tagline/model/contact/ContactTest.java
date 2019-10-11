package tagline.model.contact;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tagline.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static tagline.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static tagline.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static tagline.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static tagline.testutil.TypicalContacts.ALICE;
import static tagline.testutil.TypicalContacts.BOB;

import org.junit.jupiter.api.Test;

import tagline.testutil.ContactBuilder;

public class ContactTest {

    @Test
    public void isSameContact() {
        // same object -> returns true
        assertTrue(ALICE.isSameContact(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameContact(null));

        // different phone and email -> returns false
        Contact editedAlice = new ContactBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSameContact(editedAlice));

        // different name -> returns false
        editedAlice = new ContactBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameContact(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new ContactBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .build();
        assertTrue(ALICE.isSameContact(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new ContactBuilder(ALICE).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                .build();
        assertTrue(ALICE.isSameContact(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new ContactBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertTrue(ALICE.isSameContact(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Contact aliceCopy = new ContactBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different contact -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Contact editedAlice = new ContactBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new ContactBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new ContactBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new ContactBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
