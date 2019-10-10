package seedu.savenus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.logic.commands.CommandTestUtil.DESC_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.DESC_NASI_LEMAK;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_DESCRIPTION_NASI_LEMAK;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_NAME_NASI_LEMAK;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_PRICE_NASI_LEMAK;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_TAG_CHICKEN;

import org.junit.jupiter.api.Test;

import seedu.savenus.logic.commands.EditCommand.EditFoodDescriptor;
import seedu.savenus.testutil.EditFoodDescriptorBuilder;

public class EditFoodDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditFoodDescriptor descriptorWithSameValues = new EditFoodDescriptor(DESC_CHICKEN_RICE);
        assertTrue(DESC_CHICKEN_RICE.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_CHICKEN_RICE.equals(DESC_CHICKEN_RICE));

        // null -> returns false
        assertFalse(DESC_CHICKEN_RICE.equals(null));

        // different types -> returns false
        assertFalse(DESC_CHICKEN_RICE.equals(5));

        // different values -> returns false
        assertFalse(DESC_CHICKEN_RICE.equals(DESC_NASI_LEMAK));

        // different name -> returns false
        EditFoodDescriptor editedChickenRice = new EditFoodDescriptorBuilder(DESC_CHICKEN_RICE)
                                                    .withName(VALID_NAME_NASI_LEMAK).build();
        assertFalse(DESC_CHICKEN_RICE.equals(editedChickenRice));

        // different price -> returns false
        editedChickenRice = new EditFoodDescriptorBuilder(DESC_CHICKEN_RICE).withPrice(VALID_PRICE_NASI_LEMAK).build();
        assertFalse(DESC_CHICKEN_RICE.equals(editedChickenRice));

        // different description -> returns false
        editedChickenRice = new EditFoodDescriptorBuilder(DESC_CHICKEN_RICE)
                                .withDescription(VALID_DESCRIPTION_NASI_LEMAK).build();
        assertFalse(DESC_CHICKEN_RICE.equals(editedChickenRice));

        // different tags -> returns false
        editedChickenRice = new EditFoodDescriptorBuilder(DESC_CHICKEN_RICE).withTags(VALID_TAG_CHICKEN).build();
        assertFalse(DESC_CHICKEN_RICE.equals(editedChickenRice));
    }
}
