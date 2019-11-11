package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENTNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMING;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.BENSON;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.WHITESPACE;
import static seedu.address.testutil.scheduleutil.TypicalEvents.EVENT_NAME1;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.TIMING1;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.scheduleutil.TypicalEvents;

class AddEventCommandParserTest {

    private AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    void parse_success() throws ParseException {

        AddEventCommand actualCommand =
                new AddEventCommandParser().parse(
                        WHITESPACE + PREFIX_NAME + ALICE.getName().toString() + WHITESPACE
                                + PREFIX_EVENTNAME + EVENT_NAME1 + WHITESPACE
                                + PREFIX_TIMING + TIMING1
                );

        AddEventCommand expectedCommand =
                new AddEventCommand(ALICE.getName(), TypicalEvents.generateTypicalEvent1());

        assertTrue(actualCommand.equals(expectedCommand));
    }

    @Test
    void parse_invalidCommand() {
        // no event name tag
        assertThrows(ParseException.class, () ->
            new AddEventCommandParser().parse(
                    WHITESPACE + PREFIX_NAME + ALICE.getName().toString() + WHITESPACE
                            + PREFIX_TIMING + TIMING1
            )
        );

        // more than 1 name given
        assertThrows(ParseException.class, () ->
                new AddEventCommandParser().parse(
                        WHITESPACE + PREFIX_NAME + ALICE.getName().toString() + WHITESPACE
                                + PREFIX_NAME + BENSON.getName().toString() + WHITESPACE
                                + PREFIX_EVENTNAME + EVENT_NAME1 + WHITESPACE
                                + PREFIX_TIMING + TIMING1
                )
        );
    }

    @Test
    void parse_moreThanOneName() {
        assertParseFailure(parser,
                WHITESPACE + PREFIX_NAME + ALICE.getName().toString() + WHITESPACE
                        + PREFIX_NAME + BENSON.getName().toString() + WHITESPACE
                        + PREFIX_EVENTNAME + EVENT_NAME1 + WHITESPACE
                        + PREFIX_TIMING + TIMING1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_missingEventName() {
        assertParseFailure(parser,
                WHITESPACE + PREFIX_NAME + ALICE.getName().toString() + WHITESPACE
                        + PREFIX_TIMING + TIMING1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
    }
}
