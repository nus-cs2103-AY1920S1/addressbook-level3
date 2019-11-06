package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CancelCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListClassCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.NewCommand;
import seedu.address.logic.commands.UnknownCommand;
import seedu.address.logic.commands.calendar.DeleteTaskCommand;
import seedu.address.logic.commands.note.ListNotesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ClassIdContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;

public class TutorAidParserTest {

    private static final String UNKNOWN_COMMAND = "unknown";
    private static final String SAMPLE_ARGUMENTS = " arguments";
    private final TutorAidParser parser = new TutorAidParser();

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_deleteTask() throws Exception {
        DeleteTaskCommand command = (DeleteTaskCommand) parser.parseCommand(
                DeleteTaskCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeleteTaskCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_listClass() throws Exception {
        List<String> keywords = Arrays.asList("CS2030", "CS2040", "CS1231");
        ListClassCommand command = (ListClassCommand) parser.parseCommand(
                ListClassCommand.COMMAND_WORD + " "
                        + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new ListClassCommand(new ClassIdContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertThrows(ParseException.class, () -> parser.parseCommand(ListCommand.COMMAND_WORD
                + SAMPLE_ARGUMENTS));
    }

    @Test
    public void parseCommand_listNotes() throws Exception {
        assertTrue(parser.parseCommand(ListNotesCommand.COMMAND_WORD) instanceof ListNotesCommand);
        assertTrue(parser.parseCommand(ListNotesCommand.COMMAND_WORD) instanceof ListNotesCommand);
        assertThrows(ParseException.class, () -> parser.parseCommand(ListNotesCommand.COMMAND_WORD
                + SAMPLE_ARGUMENTS));
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unrecognisedValidInput() throws Exception {
        assertTrue(parser.parseCommand(UNKNOWN_COMMAND) instanceof UnknownCommand);
    }

    @Test
    public void parseCommandWithoutLoggingIn_exit() throws Exception {
        assertTrue(parser.parseCommandWithoutLoggingIn(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
    }

    @Test
    public void parseCommandWithoutLoggingIn_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommandWithoutLoggingIn_unknown() throws Exception {
        assertThrows(ParseException.class, Messages.MESSAGE_UNKNOWN_COMMAND, () ->
                parser.parseCommandWithoutLoggingIn(UNKNOWN_COMMAND));
    }

    @Test
    public void checkCommand_cancel() {
        assertTrue(parser.checkCommand(CancelCommand.COMMAND_WORD, UNKNOWN_COMMAND) instanceof CancelCommand);
    }

    @Test
    public void checkCommand_valid() {
        assertTrue(parser.checkCommand(AddCommand.COMMAND_WORD, UNKNOWN_COMMAND) instanceof NewCommand);
    }

    @Test
    public void checkCommand_unknown() {
        assertTrue(parser.checkCommand(UNKNOWN_COMMAND, UNKNOWN_COMMAND) instanceof UnknownCommand);
    }


}
