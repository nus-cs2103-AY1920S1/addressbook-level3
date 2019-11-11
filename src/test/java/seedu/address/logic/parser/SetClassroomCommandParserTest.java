package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASSROOM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalClassrooms.CLASSROOM_ONE_NAME;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SetClassroomCommand;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 *  {@code FindStudentCommandParser}.
 */
public class SetClassroomCommandParserTest {
    private SetClassroomCommandParser parser = new SetClassroomCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SetClassroomCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noPrefix_throwsParseException() {
        assertParseFailure(parser, "class", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SetClassroomCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSetCommand() {
        // no leading and trailing whitespaces
        String validInput = " " + PREFIX_CLASSROOM + CLASSROOM_ONE_NAME;
        SetClassroomCommand expectedSetClassroomCommand = new SetClassroomCommand(CLASSROOM_ONE_NAME);
        assertParseSuccess(parser, validInput, expectedSetClassroomCommand);
        // multiple whitespaces between keywords
        String userInputMultipleSpaces = " c/ \n \t \n" + CLASSROOM_ONE_NAME;
        assertParseSuccess(parser, userInputMultipleSpaces, expectedSetClassroomCommand);
    }
}
