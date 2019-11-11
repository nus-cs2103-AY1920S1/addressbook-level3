package seedu.billboard.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.billboard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.billboard.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.billboard.logic.commands.AddRecurrenceCommand;
import seedu.billboard.logic.commands.ListRecurrenceCommand;
import seedu.billboard.logic.commands.RecurrenceCommand;
import seedu.billboard.logic.parser.exceptions.ParseException;
import seedu.billboard.model.recurrence.Recurrence;
import seedu.billboard.testutil.RecurrenceBuilder;

public class RecurrenceCommandParserTest {
    private final RecurrenceCommandParser parser = new RecurrenceCommandParser();

    @Test
    public void parseCommand_listRecurrenceNames_success() throws Exception {
        ListRecurrenceCommand expectedCommand = new ListRecurrenceCommand();
        assertEquals(expectedCommand, parser.parse(ListRecurrenceCommand.COMMAND_WORD));
    }

    @Test
    public void parseCommand_addRecurrence_success() throws Exception {
        Recurrence recurrence = new RecurrenceBuilder().withName("bill").withDescription("pay bill")
                .withAmount("20").withDate("1/1/2019").withInterval("week").withIterations(5).build();
        AddRecurrenceCommand expectedCommand = new AddRecurrenceCommand(recurrence.getName(),
                recurrence.getDescription(), recurrence.getAmount(), recurrence.getCreated(),
                recurrence.getTagNames(), recurrence.getInterval(), recurrence.getIterations());

        String actualInput = AddRecurrenceCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_NAME + "bill "
                + CliSyntax.PREFIX_DESCRIPTION + "pay bill " + CliSyntax.PREFIX_AMOUNT + "20 "
                + CliSyntax.PREFIX_START_DATE + "1/1/2019 " + CliSyntax.PREFIX_END_DATE + "1/2/2019 "
                + CliSyntax.PREFIX_INTERVAL + "week";
        RecurrenceCommand actualCommand = parser.parse(actualInput);
        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecurrenceCommand.MESSAGE_USAGE), () ->
                        parser.parse(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecurrenceCommand.MESSAGE_USAGE), () ->
                        parser.parse("unknownCommand"));
    }
}
