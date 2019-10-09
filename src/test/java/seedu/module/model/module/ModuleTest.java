// package seedu.module.model.module;

// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static seedu.module.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
// import static seedu.module.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
// import static seedu.module.logic.commands.CommandTestUtil.VALID_NAME_BOB;
// import static seedu.module.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
// import static seedu.module.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
// import static seedu.module.testutil.Assert.assertThrows;
// import static seedu.module.testutil.TypicalPersons.ALICE;
// import static seedu.module.testutil.TypicalPersons.BOB;

// import org.junit.jupiter.api.Test;

// import seedu.module.testutil.PersonBuilder;

// public class ModuleTest {

//     @Test
//     public void asObservableList_modifyList_throwsUnsupportedOperationException() {
//         Module module = new PersonBuilder().build();
//         assertThrows(UnsupportedOperationException.class, () -> module.getTags().remove(0));
//     }

//     @Test
//     public void isSamePerson() {
//         // same object -> returns true
//         assertTrue(ALICE.isSameModule(ALICE));

//         // null -> returns false
//         assertFalse(ALICE.isSameModule(null));

//         // different phone and email -> returns false
//         Module editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
//         assertFalse(ALICE.isSameModule(editedAlice));

//         // different name -> returns false
//         editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
//         assertFalse(ALICE.isSameModule(editedAlice));

//         // same name, same phone, different attributes -> returns true
//         editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
//                 .withTags(VALID_TAG_HUSBAND).build();
//         assertTrue(ALICE.isSameModule(editedAlice));

//         // same name, same email, different attributes -> returns true
//         editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
//                 .withTags(VALID_TAG_HUSBAND).build();
//         assertTrue(ALICE.isSameModule(editedAlice));

//         // same name, same phone, same email, different attributes -> returns true
//         editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
//         assertTrue(ALICE.isSameModule(editedAlice));
//     }

//     @Test
//     public void equals() {
//         // same values -> returns true
//         Module aliceCopy = new PersonBuilder(ALICE).build();
//         assertTrue(ALICE.equals(aliceCopy));

//         // same object -> returns true
//         assertTrue(ALICE.equals(ALICE));

//         // null -> returns false
//         assertFalse(ALICE.equals(null));

//         // different type -> returns false
//         assertFalse(ALICE.equals(5));

//         // different person -> returns false
//         assertFalse(ALICE.equals(BOB));

//         // different name -> returns false
//         Module editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
//         assertFalse(ALICE.equals(editedAlice));

//         // different phone -> returns false
//         editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
//         assertFalse(ALICE.equals(editedAlice));

//         // different email -> returns false
//         editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
//         assertFalse(ALICE.equals(editedAlice));

//         // different address -> returns false
//         editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
//         assertFalse(ALICE.equals(editedAlice));

//         // different tags -> returns false
//         editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
//         assertFalse(ALICE.equals(editedAlice));
//     }
// }
