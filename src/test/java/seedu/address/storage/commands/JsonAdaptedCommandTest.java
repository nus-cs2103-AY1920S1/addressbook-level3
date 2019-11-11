package seedu.address.storage.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.commands.JsonAdaptedCommand.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTutorAid.ADDER;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.commands.CommandAction;
import seedu.address.model.commands.CommandWord;

public class JsonAdaptedCommandTest {

    private static final String INVALID_COMMAND_WORD = " ";
    private static final String INVALID_COMMAND_ACTION = "  ";

    private static final String VALID_COMMAND_WORD = ADDER.getCommandWord().toString();
    private static final String VALID_COMMAND_ACTION = ADDER.getCommandAction().toString();

    @Test
    public void toModelType_validCommandObjectDetails_returnsCommandObject() throws Exception {
        JsonAdaptedCommand command = new JsonAdaptedCommand(ADDER);
        assertEquals(ADDER, command.toModelType());
    }

    @Test
    public void toModelType_invalidCommandWord_throwsIllegalValueException() {
        JsonAdaptedCommand command =
                new JsonAdaptedCommand(INVALID_COMMAND_WORD, VALID_COMMAND_ACTION);
        String expectedMessage = CommandWord.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, command::toModelType);
    }

    @Test
    public void toModelType_nullCommandWord_throwsIllegalValueException() {
        JsonAdaptedCommand command = new JsonAdaptedCommand(null, VALID_COMMAND_ACTION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, JsonAdaptedCommand.COMMAND_WORD);
        assertThrows(IllegalValueException.class, expectedMessage, command::toModelType);
    }

    @Test
    public void toModelType_nullCommandAction_throwsIllegalValueException() {
        JsonAdaptedCommand command = new JsonAdaptedCommand(VALID_COMMAND_WORD, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, JsonAdaptedCommand.COMMAND_ACTION);
        assertThrows(IllegalValueException.class, expectedMessage, command::toModelType);
    }

    @Test
    public void toModelType_invalidCommandAction_throwsIllegalValueException() {
        JsonAdaptedCommand command =
                new JsonAdaptedCommand(VALID_COMMAND_WORD, INVALID_COMMAND_ACTION);
        String expectedMessage = CommandAction.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, command::toModelType);
    }

}
