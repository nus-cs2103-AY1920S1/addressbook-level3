package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddEventCommand;

class AddEventCommandParserTest {

    private AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    void parse_success() {
        /*assertParseSuccess(parser,
                WHITESPACE + PREFIX_NAME + ALICE.getName().toString() + WHITESPACE + PREFIX_EVENTNAME + EVENTNAME1
                        + WHITESPACE + PREFIX_TIMING + STARTTIMETEXT1 + ENDTIMETEXT1 + VENUE1,
                new AddEventCommand(ALICE.getName(), TypicalEvents.generateTypicalEvent1()));*/
    }

    @Test
    void parse_failure() {
        assertParseFailure(parser,
                "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
    }
}
