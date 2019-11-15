//package seedu.guilttrip.model.entry;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.guilttrip.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
//import static seedu.guilttrip.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
//import static seedu.guilttrip.logic.commands.CommandTestUtil.VALID_DESC_CLOTHING_EXPENSE;
//import static seedu.guilttrip.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
//import static seedu.guilttrip.logic.commands.CommandTestUtil.VALID_TAG_FOOD;
//import static seedu.guilttrip.testutil.Assert.assertThrows;
//import static seedu.guilttrip.testutil.TypicalEntries.ALICE;
//import static seedu.guilttrip.testutil.TypicalEntries.BOB;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.guilttrip.testutil.EntryBuilder;
//
//public class PersonTest {
//
//    @Test
//    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
//        Person entry = new EntryBuilder().build();
//        assertThrows(UnsupportedOperationException.class, () -> entry.getTags().remove(0));
//    }
//
//    @Test
//    public void isSamePerson() {
//        // same object -> returns true
//        assertTrue(ALICE.isSamePerson(ALICE));
//
//        // null -> returns false
//        assertFalse(ALICE.isSamePerson(null));
//
//        // different phone and email -> returns false
//        Person editedAlice = new EntryBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
//        assertFalse(ALICE.isSamePerson(editedAlice));
//
//        // different name -> returns false
//        editedAlice = new EntryBuilder(ALICE).withDesc(VALID_DESC_CLOTHING_EXPENSE).build();
//        assertFalse(ALICE.isSamePerson(editedAlice));
//
//        // same name, same phone, different attributes -> returns true
//        editedAlice = new EntryBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
//                .withTags(VALID_TAG_FOOD).build();
//        assertTrue(ALICE.isSamePerson(editedAlice));
//
//        // same name, same email, different attributes -> returns true
//        editedAlice = new EntryBuilder(ALICE).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
//                .withTags(VALID_TAG_FOOD).build();
//        assertTrue(ALICE.isSamePerson(editedAlice));
//
//        // same name, same phone, same email, different attributes -> returns true
//        editedAlice = new EntryBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FOOD).build();
//        assertTrue(ALICE.isSamePerson(editedAlice));
//    }
//
//    @Test
//    public void equals() {
//        // same values -> returns true
//        Person aliceCopy = new EntryBuilder(ALICE).build();
//        assertTrue(ALICE.equals(aliceCopy));
//
//        // same object -> returns true
//        assertTrue(ALICE.equals(ALICE));
//
//        // null -> returns false
//        assertFalse(ALICE.equals(null));
//
//        // different type -> returns false
//        assertFalse(ALICE.equals(5));
//
//        // different entry -> returns false
//        assertFalse(ALICE.equals(BOB));
//
//        // different name -> returns false
//        Person editedAlice = new EntryBuilder(ALICE).withDesc(VALID_DESC_CLOTHING_EXPENSE).build();
//        assertFalse(ALICE.equals(editedAlice));
//
//        // different phone -> returns false
//        editedAlice = new EntryBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
//        assertFalse(ALICE.equals(editedAlice));
//
//        // different email -> returns false
//        editedAlice = new EntryBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
//        assertFalse(ALICE.equals(editedAlice));
//
//        // different guilttrip -> returns false
//        editedAlice = new EntryBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
//        assertFalse(ALICE.equals(editedAlice));
//
//        // different tags -> returns false
//        editedAlice = new EntryBuilder(ALICE).withTags(VALID_TAG_FOOD).build();
//        assertFalse(ALICE.equals(editedAlice));
//    }
//}
