//package seedu.address.logic.commands;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.address.logic.commands.CommandTestUtil.DESC_FOOD_EXPENSE;
//import static seedu.address.logic.commands.CommandTestUtil.DESC_CLOTHING_EXPENSE;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_DESC_CLOTHING_EXPENSE;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FOOD;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
//import seedu.address.testutil.EditEntryDescriptorBuilder;
//
//public class EditPersonDescriptorTest {
//
//    @Test
//    public void equals() {
//        // same values -> returns true
//        EditPersonDescriptor descriptorWithSameValues = new EditPersonDescriptor(DESC_FOOD_EXPENSE);
//        assertTrue(DESC_FOOD_EXPENSE.equals(descriptorWithSameValues));
//
//        // same object -> returns true
//        assertTrue(DESC_FOOD_EXPENSE.equals(DESC_FOOD_EXPENSE));
//
//        // null -> returns false
//        assertFalse(DESC_FOOD_EXPENSE.equals(null));
//
//        // different types -> returns false
//        assertFalse(DESC_FOOD_EXPENSE.equals(5));
//
//        // different values -> returns false
//        assertFalse(DESC_FOOD_EXPENSE.equals(DESC_CLOTHING_EXPENSE));
//
//        // different name -> returns false
//        EditPersonDescriptor editedAmy = new EditEntryDescriptorBuilder(DESC_FOOD_EXPENSE)
//        .withDescription(VALID_DESC_CLOTHING_EXPENSE).build();
//        assertFalse(DESC_FOOD_EXPENSE.equals(editedAmy));
//
//        // different phone -> returns false
//        editedAmy = new EditEntryDescriptorBuilder(DESC_FOOD_EXPENSE).withPhone(VALID_PHONE_BOB).build();
//        assertFalse(DESC_FOOD_EXPENSE.equals(editedAmy));
//
//        // different email -> returns false
//        editedAmy = new EditEntryDescriptorBuilder(DESC_FOOD_EXPENSE).withEmail(VALID_EMAIL_BOB).build();
//        assertFalse(DESC_FOOD_EXPENSE.equals(editedAmy));
//
//        // different address -> returns false
//        editedAmy = new EditEntryDescriptorBuilder(DESC_FOOD_EXPENSE).withAddress(VALID_ADDRESS_BOB).build();
//        assertFalse(DESC_FOOD_EXPENSE.equals(editedAmy));
//
//        // different tags -> returns false
//        editedAmy = new EditEntryDescriptorBuilder(DESC_FOOD_EXPENSE).withTags(VALID_TAG_FOOD).build();
//        assertFalse(DESC_FOOD_EXPENSE.equals(editedAmy));
//    }
//}
