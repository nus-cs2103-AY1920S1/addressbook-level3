package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TRANSACTION;
import static seedu.address.testutil.TypicalTypes.TYPE_TRANSACTION;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DisplayCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.InCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.OutCommand;
import seedu.address.logic.commands.ProjectCommand;
import seedu.address.logic.commands.ReceiveCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SetCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.SplitCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.category.Category;
import seedu.address.model.transaction.TransactionPredicate;

// TODO: ADD ALL THE COMMANDS
public class MainParserTest {

    private final MainParser parser = new MainParser();

    @Test
    public void parseCommand_split() throws Exception {
        assertTrue(parser.parseCommand(SplitCommand.COMMAND_WORD + " $/69 n/John a/desc") instanceof SplitCommand);
        assertTrue(parser.parseCommand(
            SplitCommand.COMMAND_WORD + " $/69 n/John n/Lisa s/2 s/3 a/desc") instanceof SplitCommand);
        assertTrue(parser.parseCommand(
            SplitCommand.COMMAND_WORD + " $/69 n/John n/Lisa s/1 s/2 s/3 a/desc") instanceof SplitCommand);
    }

    @Test
    public void parseCommand_receive() throws Exception {
        assertTrue(parser.parseCommand(ReceiveCommand.COMMAND_WORD + " $/69 n/John") instanceof ReceiveCommand);
    }

    @Test
    public void parseCommand_in() throws Exception {
        assertTrue(parser.parseCommand(
            InCommand.COMMAND_WORD + " $/69 n/John d/19112019") instanceof InCommand);
        assertTrue(parser.parseCommand(
            InCommand.COMMAND_WORD + " $/69 n/John d/19112019 c/Food") instanceof InCommand);
        assertTrue(parser.parseCommand(
            InCommand.COMMAND_WORD + " $/69 n/John d/19112019 c/Food c/Drink") instanceof InCommand);
    }

    @Test
    public void parseCommand_out() throws Exception {
        assertTrue(parser.parseCommand(
            OutCommand.COMMAND_WORD + " $/69 n/John d/21032019") instanceof OutCommand);
        assertTrue(parser.parseCommand(
            OutCommand.COMMAND_WORD + " $/69 n/John d/21032019 c/Food") instanceof OutCommand);
        assertTrue(parser.parseCommand(
            OutCommand.COMMAND_WORD + " $/69 n/John d/21032019 c/Drink") instanceof OutCommand);
    }

    @Test
    public void parseCommand_set() throws Exception {
        assertTrue(parser.parseCommand(
            SetCommand.COMMAND_WORD + " $/69 d/31122019") instanceof SetCommand);
        assertTrue(parser.parseCommand(
            SetCommand.COMMAND_WORD + " $/69 d/31122019 c/Food") instanceof SetCommand);
    }

    @Test
    public void parseCommand_project() throws Exception {
        assertTrue(parser.parseCommand(
            ProjectCommand.COMMAND_WORD + " d/31122019") instanceof ProjectCommand);
    }

    @Test
    public void parseCommand_filter() throws Exception {
        assertTrue(parser.parseCommand(FilterCommand.COMMAND_WORD + " c/food") instanceof FilterCommand);

        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        Optional<Set<Category>> categories = Optional.of(new HashSet<>(Arrays.asList(
            new Category("foo"), new Category("bar"), new Category("baz"))));
        FilterCommand command = (FilterCommand) parser.parseCommand(
            FilterCommand.COMMAND_WORD + " "
                + keywords.stream().map(cat -> "c/" + cat).collect(Collectors.joining(" ")));
        assertEquals(new FilterCommand(new TransactionPredicate(categories,
            Optional.empty(), Optional.empty(), Optional.empty())), command);
    }

    @Test
    public void parseCommand_sort() throws Exception {
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " amount/a") instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " date/a") instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " amount/d") instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " date/d") instanceof SortCommand);
    }

    @Test
    public void parseCommand_undo() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD + " tmr") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_redo() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD + " later") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_view() throws Exception {
        assertTrue(parser.parseCommand(ViewCommand.COMMAND_WORD + " transaction") instanceof ViewCommand);
        assertTrue(parser.parseCommand(ViewCommand.COMMAND_WORD + " budget") instanceof ViewCommand);
        assertTrue(parser.parseCommand(ViewCommand.COMMAND_WORD + " ledger") instanceof ViewCommand);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_update() throws Exception {
        assertTrue(parser.parseCommand(UpdateCommand.COMMAND_WORD + " t1 $/69") instanceof UpdateCommand);
        assertTrue(parser.parseCommand(UpdateCommand.COMMAND_WORD + " b1 $/69") instanceof UpdateCommand);
        assertTrue(parser.parseCommand(
            UpdateCommand.COMMAND_WORD + " t1 $/69 c/Food") instanceof UpdateCommand);
        assertTrue(parser.parseCommand(
            UpdateCommand.COMMAND_WORD + " t1 $/69 c/Food d/19112019") instanceof UpdateCommand);
        assertTrue(parser.parseCommand(
            UpdateCommand.COMMAND_WORD + " t1 $/69 c/Food d/19112019 n/KFC") instanceof UpdateCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
            DeleteCommand.COMMAND_WORD + " " + TYPE_TRANSACTION + INDEX_FIRST_TRANSACTION.getOneBased());
        assertEquals(new DeleteCommand(TYPE_TRANSACTION, INDEX_FIRST_TRANSACTION), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_display() throws Exception {
        assertTrue(parser.parseCommand(DisplayCommand.COMMAND_WORD + " p1") instanceof DisplayCommand);
        assertTrue(parser.parseCommand(DisplayCommand.COMMAND_WORD + " b1") instanceof DisplayCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
