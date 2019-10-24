package seedu.exercise.model.property;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.exercise.logic.commands.CommandTestUtil.INVALID_FULL_NAME_DESC;
import static seedu.exercise.logic.commands.CommandTestUtil.INVALID_PREFIX_NAME_DESC;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_FULL_NAME_RATING;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_FULL_NAME_REMARK;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_PARAMETER_TYPE_RATING;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_PREFIX_NAME_RATING;
import static seedu.exercise.testutil.TypicalCustomProperties.ENDDATE;
import static seedu.exercise.testutil.TypicalCustomProperties.INSTRUCTIONS;
import static seedu.exercise.testutil.TypicalCustomProperties.PRIORITY;

import org.junit.jupiter.api.Test;

import seedu.exercise.testutil.CustomPropertyBuilder;

class CustomPropertyTest {

    @Test
    public void execute_isValidFullName_success() {
        assertTrue(CustomProperty.isValidFullName(VALID_FULL_NAME_REMARK));
    }

    @Test
    public void execute_isValidFullName_failure() {
        assertFalse(CustomProperty.isValidFullName(INVALID_FULL_NAME_DESC));
    }

    @Test
    public void execute_isValidShortName_success() {
        assertTrue(CustomProperty.isValidFullName(VALID_PREFIX_NAME_RATING));
    }

    @Test
    public void execute_isValidShortName_failure() {
        assertFalse(CustomProperty.isValidFullName(INVALID_PREFIX_NAME_DESC));
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(INSTRUCTIONS.equals(INSTRUCTIONS));

        // null -> returns false
        assertFalse(INSTRUCTIONS.equals(null));

        // same values -> returns true
        CustomProperty customPropertyCopy = new CustomPropertyBuilder(INSTRUCTIONS).build();
        assertTrue(INSTRUCTIONS.equals(customPropertyCopy));

        // different type -> returns false
        assertFalse(INSTRUCTIONS.equals(5));

        // different custom property -> returns false
        assertFalse(INSTRUCTIONS.equals(ENDDATE));

        // different prefix -> returns false
        CustomProperty editedPriority = new CustomPropertyBuilder(PRIORITY)
                .withPrefix(VALID_PREFIX_NAME_RATING).build();
        assertFalse(INSTRUCTIONS.equals(editedPriority));

        // different full name -> returns false
        editedPriority = new CustomPropertyBuilder(INSTRUCTIONS).withFullName(VALID_FULL_NAME_RATING).build();
        assertFalse(INSTRUCTIONS.equals(editedPriority));

        // different parameter type -> returns false
        editedPriority = new CustomPropertyBuilder(INSTRUCTIONS).withParameterType(VALID_PARAMETER_TYPE_RATING).build();
        assertFalse(INSTRUCTIONS.equals(editedPriority));

    }

}
