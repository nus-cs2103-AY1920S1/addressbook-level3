package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RECORD;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.achvm.AchvmCommand;
import seedu.address.logic.commands.bio.BioCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.DateTime;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.record.BloodSugar;
import seedu.address.model.record.Concentration;

public class SugarMummyParserTest {

    private final SugarMummyParser parser = new SugarMummyParser();

    @Test
    public void parseCommand_add() throws Exception {

        LocalDate ld = LocalDate.of(1970, Month.JANUARY, 1);
        LocalTime lt = LocalTime.of(8, 0, 0);
        DateTime dt = new DateTime(ld, lt);
        BloodSugar bs = new BloodSugar(new Concentration("12.34"), dt);

        AddCommand command = (AddCommand) parser.parseCommand("add rt/BLOODSUGAR con/12.34 dt/1970-01-01 08:00");

        assertEquals(new AddCommand(bs), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
            DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_RECORD.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_RECORD), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertThrows(ParseException.class, () -> parser.parseCommand(ExitCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
            FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertThrows(ParseException.class, () -> parser.parseCommand(HelpCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_bio() throws Exception {
        assertTrue(parser.parseCommand(BioCommand.COMMAND_WORD) instanceof BioCommand);
        assertThrows(ParseException.class, () -> parser.parseCommand(BioCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_achvm() throws Exception {
        assertTrue(parser.parseCommand(AchvmCommand.COMMAND_WORD) instanceof AchvmCommand);
        assertThrows(ParseException.class, () -> parser.parseCommand(AchvmCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
