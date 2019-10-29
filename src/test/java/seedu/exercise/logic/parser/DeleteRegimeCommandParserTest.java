package seedu.exercise.logic.parser;

import static seedu.exercise.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.exercise.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.exercise.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.exercise.logic.commands.DeleteRegimeCommand;
import seedu.exercise.model.property.Name;

public class DeleteRegimeCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnDeleteCommand() {
        assertParseSuccess(parser, " t/regime n/test", new DeleteRegimeCommand(new Name("test"), null));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteRegimeCommand.MESSAGE_USAGE));
    }
}
