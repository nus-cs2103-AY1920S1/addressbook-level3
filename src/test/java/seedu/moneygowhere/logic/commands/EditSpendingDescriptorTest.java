package seedu.moneygowhere.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_COST_BOB;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.logic.commands.EditCommand.EditSpendingDescriptor;
import seedu.moneygowhere.testutil.EditSpendingDescriptorBuilder;

public class EditSpendingDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditSpendingDescriptor descriptorWithSameValues = new EditSpendingDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditSpendingDescriptor editedAmy = new EditSpendingDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different date -> returns false
        editedAmy = new EditSpendingDescriptorBuilder(DESC_AMY).withDate(VALID_DATE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different remark -> returns false
        editedAmy = new EditSpendingDescriptorBuilder(DESC_AMY).withRemark(VALID_REMARK_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different cost -> returns false
        editedAmy = new EditSpendingDescriptorBuilder(DESC_AMY).withCost(VALID_COST_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditSpendingDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }
}
