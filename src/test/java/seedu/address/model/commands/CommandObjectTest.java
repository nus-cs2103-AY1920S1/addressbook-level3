package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalTutorAid.ADDER;
import static seedu.address.testutil.TypicalTutorAid.DELETER;

import org.junit.jupiter.api.Test;

import seedu.address.model.commands.CommandObject;
import seedu.address.testutil.CommandObjectBuilder;

public class CommandObjectTest {

    public static final String VALID_COMMAND_ACTION_MUL = "multiply";
    public static final String VALID_WORD_MUL = "mul";

    @Test
    public void isSameCommandObject() {
        // same object -> returns true
        assertTrue(ADDER.isSameCommand(ADDER));

        // null -> returns false
        assertFalse(ADDER.isSameCommand(null));
    }

    @Test
    public void equals() {
        // same values -> returns true
        CommandObject adderCopy = new CommandObjectBuilder(ADDER).build();
        assertTrue(ADDER.equals(adderCopy));

        // same object -> returns true
        assertTrue(ADDER.equals(ADDER));

        // null -> returns false
        assertFalse(ADDER.equals(null));

        // different type -> returns false
        assertFalse(ADDER.equals(5));

        // different command -> returns false
        assertFalse(ADDER.equals(DELETER));

        // different action -> returns false
        CommandObject editedAdder = new CommandObjectBuilder(ADDER)
                .withCommandAction(VALID_COMMAND_ACTION_MUL).build();
        assertFalse(ADDER.equals(editedAdder));

        // different commandword -> returns false
        editedAdder = new CommandObjectBuilder(ADDER).withCommandWord(VALID_WORD_MUL).build();
        assertFalse(ADDER.equals(editedAdder));
    }

}
