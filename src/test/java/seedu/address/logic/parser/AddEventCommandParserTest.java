package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENTNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMING;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.WHITESPACE;
import static seedu.address.testutil.scheduleutil.TypicalEvents.EVENTNAME1;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.ENDTIMETEXT1;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.STARTTIMETEXT1;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.VENUE1;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.testutil.scheduleutil.TypicalEvents;

class AddEventCommandParserTest {

    private AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    void parse_success() {
        assertParseSuccess(parser,
                WHITESPACE + PREFIX_NAME + ALICE.getName().toString() + WHITESPACE + PREFIX_EVENTNAME + EVENTNAME1
                        + WHITESPACE + PREFIX_TIMING + STARTTIMETEXT1 + ENDTIMETEXT1 + VENUE1,
                new AddEventCommand(ALICE.getName(), TypicalEvents.generateTypicalEvent1()));
    }

    @Test
    void parse_failure() {
        assertParseFailure(parser,
                "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
    }
}
