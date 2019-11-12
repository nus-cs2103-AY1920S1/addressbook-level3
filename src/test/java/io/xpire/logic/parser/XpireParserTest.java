package io.xpire.logic.parser;

import static io.xpire.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static io.xpire.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static io.xpire.testutil.Assert.assertThrows;
import static io.xpire.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_APPLE;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_APPLE;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_DRINK;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.xpire.logic.commands.AddCommand;
import io.xpire.logic.commands.CheckCommand;
import io.xpire.logic.commands.ClearCommand;
import io.xpire.logic.commands.DeleteCommand;
import io.xpire.logic.commands.ExitCommand;
import io.xpire.logic.commands.ExportCommand;
import io.xpire.logic.commands.HelpCommand;
import io.xpire.logic.commands.SearchCommand;
import io.xpire.logic.commands.SetReminderCommand;
import io.xpire.logic.commands.SortCommand;
import io.xpire.logic.commands.TagCommand;
import io.xpire.logic.commands.ViewCommand;
import io.xpire.logic.parser.exceptions.ParseException;

//@@author JermyTan
public class XpireParserTest {

    private final XpireParser parser = new XpireParser();

    @Test
    public void parse_add() throws ParseException {
        assertTrue(parser.parse(AddCommand.COMMAND_WORD + "|" + VALID_NAME_APPLE
                + "|" + VALID_EXPIRY_DATE_APPLE) instanceof AddCommand);
        assertTrue(parser.parse(AddCommand.COMMAND_SHORTHAND + "|" + VALID_NAME_APPLE
                + "|" + VALID_EXPIRY_DATE_APPLE) instanceof AddCommand);
    }

    @Test
    public void parse_check() throws ParseException {
        assertTrue(parser.parse(CheckCommand.COMMAND_WORD) instanceof CheckCommand);
        assertTrue(parser.parse(CheckCommand.COMMAND_SHORTHAND) instanceof CheckCommand);
        assertTrue(parser.parse(CheckCommand.COMMAND_WORD + "|5") instanceof CheckCommand);
        assertTrue(parser.parse(CheckCommand.COMMAND_SHORTHAND + "|5") instanceof CheckCommand);
    }

    @Test
    public void parse_clear() throws ParseException {
        assertTrue(parser.parse(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parse(ClearCommand.COMMAND_SHORTHAND) instanceof ClearCommand);
        assertTrue(parser.parse(ClearCommand.COMMAND_WORD + "|any random input") instanceof ClearCommand);
        assertTrue(parser.parse(ClearCommand.COMMAND_SHORTHAND + "|any random") instanceof ClearCommand);
    }

    @Test
    public void parse_delete() throws ParseException {
        //delete item
        assertTrue(parser.parse(DeleteCommand.COMMAND_WORD + "|"
                + INDEX_FIRST_ITEM.getOneBased()) instanceof DeleteCommand);
        assertTrue(parser.parse(DeleteCommand.COMMAND_SHORTHAND + "|"
                + INDEX_FIRST_ITEM.getOneBased()) instanceof DeleteCommand);

        //delete quantity
        assertTrue(parser.parse(DeleteCommand.COMMAND_WORD + "|"
                + INDEX_FIRST_ITEM.getOneBased() + "|1") instanceof DeleteCommand);
        assertTrue(parser.parse(DeleteCommand.COMMAND_SHORTHAND + "|"
                + INDEX_FIRST_ITEM.getOneBased() + "|1") instanceof DeleteCommand);
    }

    @Test
    public void parse_exit() throws ParseException {
        assertTrue(parser.parse(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parse(ExitCommand.COMMAND_SHORTHAND) instanceof ExitCommand);
        assertTrue(parser.parse(ExitCommand.COMMAND_WORD + "|3") instanceof ExitCommand);
        assertTrue(parser.parse(ExitCommand.COMMAND_SHORTHAND + "|3") instanceof ExitCommand);
    }

    @Test
    public void parse_help() throws ParseException {
        assertTrue(parser.parse(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parse(HelpCommand.COMMAND_SHORTHAND) instanceof HelpCommand);
        assertTrue(parser.parse(HelpCommand.COMMAND_WORD + "|3") instanceof HelpCommand);
        assertTrue(parser.parse(HelpCommand.COMMAND_SHORTHAND + "|3") instanceof HelpCommand);

    }

    @Test
    public void parse_search() throws ParseException {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        assertTrue(parser.parse(SearchCommand.COMMAND_WORD + "|"
                + String.join("|", keywords)) instanceof SearchCommand);
        assertTrue(parser.parse(SearchCommand.COMMAND_SHORTHAND + "|"
                + String.join("|", keywords)) instanceof SearchCommand);

    }

    @Test
    public void parse_setReminder() throws ParseException {
        assertTrue(parser.parse(SetReminderCommand.COMMAND_WORD + "|"
                + INDEX_FIRST_ITEM.getOneBased() + "|10") instanceof SetReminderCommand);
        assertTrue(parser.parse(SetReminderCommand.COMMAND_SHORTHAND + "|"
                + INDEX_FIRST_ITEM.getOneBased() + "|10") instanceof SetReminderCommand);

    }

    @Test
    public void parse_sort() throws ParseException {
        assertTrue(parser.parse(SortCommand.COMMAND_WORD + "|name") instanceof SortCommand);
        assertTrue(parser.parse(SortCommand.COMMAND_SHORTHAND + "|name") instanceof SortCommand);
        assertTrue(parser.parse(SortCommand.COMMAND_WORD + "|date") instanceof SortCommand);
        assertTrue(parser.parse(SortCommand.COMMAND_SHORTHAND + "|date") instanceof SortCommand);
    }

    @Test
    public void parse_tag() throws ParseException {
        assertTrue(parser.parse(TagCommand.COMMAND_WORD) instanceof TagCommand);
        assertTrue(parser.parse(TagCommand.COMMAND_SHORTHAND) instanceof TagCommand);
        assertTrue(parser.parse(TagCommand.COMMAND_WORD + "|1|#" + VALID_TAG_DRINK) instanceof TagCommand);
        assertTrue(parser.parse(TagCommand.COMMAND_SHORTHAND + "|1|#" + VALID_TAG_DRINK) instanceof TagCommand);

    }

    @Test
    public void parse_view() throws ParseException {
        assertTrue(parser.parse(ViewCommand.COMMAND_WORD) instanceof ViewCommand);
        assertTrue(parser.parse(ViewCommand.COMMAND_SHORTHAND) instanceof ViewCommand);
        assertTrue(parser.parse(ViewCommand.COMMAND_WORD + "|main") instanceof ViewCommand);
        assertTrue(parser.parse(ViewCommand.COMMAND_SHORTHAND + "|main") instanceof ViewCommand);
        assertTrue(parser.parse(ViewCommand.COMMAND_WORD + "|replenish") instanceof ViewCommand);
        assertTrue(parser.parse(ViewCommand.COMMAND_SHORTHAND + "|replenish") instanceof ViewCommand);
    }

    @Test
    public void parse_export() throws ParseException {
        assertTrue(parser.parse(ExportCommand.COMMAND_WORD) instanceof ExportCommand);
        assertTrue(parser.parse(ExportCommand.COMMAND_SHORTHAND) instanceof ExportCommand);
        assertTrue(parser.parse(ExportCommand.COMMAND_WORD + "|any random input") instanceof ExportCommand);
        assertTrue(parser.parse(ExportCommand.COMMAND_SHORTHAND + "|any random input") instanceof ExportCommand);
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
