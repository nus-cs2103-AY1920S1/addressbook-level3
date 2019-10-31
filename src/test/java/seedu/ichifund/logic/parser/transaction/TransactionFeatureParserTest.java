package seedu.ichifund.logic.parser.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ichifund.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ichifund.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.ichifund.testutil.Assert.assertThrows;
import static seedu.ichifund.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.ichifund.logic.commands.AddCommand;
import seedu.ichifund.logic.commands.transaction.AddTransactionCommand;
import seedu.ichifund.logic.parser.exceptions.ParseException;
import seedu.ichifund.model.person.Person;
import seedu.ichifund.testutil.PersonBuilder;
import seedu.ichifund.testutil.PersonUtil;

public class TransactionFeatureParserTest {

    private final TransactionFeatureParser parser = new TransactionFeatureParser();

    @Test
    public void parseCommand_addTransaction() throws Exception {
        // AddTransactionCommand command = (AddTransactionCommand) parser.parseCommand(AddTransactionCommand.COMMAND_WORD,
                // PREFIX_DESCRIPTION);
        // assertEquals(new AddTransactionCommand(person), command);
    }

    @Test
    public void parseCommand_filterTransaction() throws Exception {
        Person person = new PersonBuilder().build();
        // AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        // assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand", ""));
    }
}
