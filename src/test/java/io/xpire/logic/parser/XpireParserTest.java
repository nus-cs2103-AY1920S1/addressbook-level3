package io.xpire.logic.parser;

import static io.xpire.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static io.xpire.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static io.xpire.testutil.Assert.assertThrows;
import static io.xpire.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_DRINK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.xpire.logic.commands.AddCommand;
import io.xpire.logic.commands.CheckCommand;
import io.xpire.logic.commands.ClearCommand;
import io.xpire.logic.commands.DeleteCommand;
import io.xpire.logic.commands.ExitCommand;
import io.xpire.logic.commands.HelpCommand;
import io.xpire.logic.commands.SearchCommand;
import io.xpire.logic.commands.SetReminderCommand;
import io.xpire.logic.commands.SortCommand;
import io.xpire.logic.commands.TagCommand;
import io.xpire.logic.commands.ViewCommand;
import io.xpire.logic.parser.exceptions.ParseException;
import io.xpire.model.item.ContainsKeywordsPredicate;
import io.xpire.model.item.XpireItem;
import io.xpire.testutil.ItemUtil;
import io.xpire.testutil.XpireItemBuilder;

public class XpireParserTest {

    private final XpireParser parser = new XpireParser();

    @Test
    public void parse_add() throws Exception {
        XpireItem xpireItem = new XpireItemBuilder().build();
        AddCommand command = (AddCommand) parser.parse(ItemUtil.getAddCommand(xpireItem));
        assertEquals(new AddCommand(xpireItem), command);
    }

    @Test
    public void parse_check() throws Exception {
        assertTrue(parser.parse(CheckCommand.COMMAND_WORD) instanceof CheckCommand);
        assertTrue(parser.parse(CheckCommand.COMMAND_WORD + "|5") instanceof CheckCommand);
    }

    @Test
    public void parse_clear() throws Exception {
        assertTrue(parser.parse(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parse(ClearCommand.COMMAND_WORD + "|3") instanceof ClearCommand);
    }

    @Test
    public void parse_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parse(
                DeleteCommand.COMMAND_WORD + "|" + INDEX_FIRST_ITEM.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_ITEM), command);
    }

    @Test
    public void parse_exit() throws Exception {
        assertTrue(parser.parse(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parse(ExitCommand.COMMAND_WORD + "|3") instanceof ExitCommand);
    }

    @Test
    public void parse_help() throws Exception {
        assertTrue(parser.parse(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parse(HelpCommand.COMMAND_WORD + "|3") instanceof HelpCommand);
    }

    @Test
    public void parse_search() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        SearchCommand command = (SearchCommand) parser.parse(
                SearchCommand.COMMAND_WORD + "|" + String.join("|", keywords));
        assertEquals(new SearchCommand(new ContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parse_setReminder() throws Exception {
        assertTrue(parser.parse(SetReminderCommand.COMMAND_WORD
                + "|" + INDEX_FIRST_ITEM.getOneBased()
                + "|10") instanceof SetReminderCommand);
    }

    @Test
    public void parse_sort() throws Exception {
        assertTrue(parser.parse(SortCommand.COMMAND_WORD
                + "|name") instanceof SortCommand);
        assertTrue(parser.parse(SortCommand.COMMAND_WORD
                + "|date") instanceof SortCommand);
    }

    @Test
    public void parse_tag() throws Exception {
        assertTrue(parser.parse(TagCommand.COMMAND_WORD) instanceof TagCommand);
        assertTrue(parser.parse(TagCommand.COMMAND_WORD + "|1|#"
                + VALID_TAG_DRINK) instanceof TagCommand);
    }

    @Test
    public void parse_view() throws Exception {
        assertTrue(parser.parse(ViewCommand.COMMAND_WORD) instanceof ViewCommand);
        assertTrue(parser.parse(ViewCommand.COMMAND_WORD + "|replenish") instanceof ViewCommand);
    }

    @Test
    public void parse_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parse(""));
    }

    @Test
    public void parse_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parse("unknownCommand"));
    }
}
