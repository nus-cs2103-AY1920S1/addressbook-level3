package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CS1231;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CS2100;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_EARNINGS_CS1231_T05;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASSID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EARNINGS_CS1231_T05;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_EARNINGS_CS1231_T05;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UpdateEarningsCommand.EditEarningsDescriptor;
import seedu.address.testutil.UpdateEarningsDescriptorBuilder;

public class UpdateEarningsDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditEarningsDescriptor descriptorWithSameValues = new EditEarningsDescriptor(DESC_CS2100);
        assertTrue(DESC_CS2100.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_CS2100.equals(DESC_CS2100));

        // null -> returns false
        assertFalse(DESC_CS2100.equals(null));

        // different types -> returns false
        assertFalse(DESC_CS2100.equals(5));

        // different values -> returns false
        assertFalse(DESC_CS2100.equals(DESC_CS1231));

        // different date -> returns false
        EditEarningsDescriptor editedCS2100 = new UpdateEarningsDescriptorBuilder(DESC_CS2100)
                .withDate(VALID_DATE_EARNINGS_CS1231_T05).build();
        assertFalse(DESC_CS2100.equals(editedCS2100));

        // different type -> returns false
        editedCS2100 = new UpdateEarningsDescriptorBuilder(DESC_CS2100)
                .withType(VALID_TYPE_EARNINGS_CS1231_T05).build();
        assertFalse(DESC_CS2100.equals(editedCS2100));

        // different amount -> returns false
        editedCS2100 = new UpdateEarningsDescriptorBuilder(DESC_CS2100)
                .withAmount(VALID_AMOUNT_EARNINGS_CS1231_T05).build();
        assertFalse(DESC_CS2100.equals(editedCS2100));

        // different class -> returns false
        editedCS2100 = new UpdateEarningsDescriptorBuilder(DESC_CS2100).withClassId(VALID_CLASSID_BOB).build();
        assertFalse(DESC_CS2100.equals(editedCS2100));

    }
}
