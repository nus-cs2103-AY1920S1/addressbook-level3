package io.xpire.logic.parser;

import static io.xpire.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static io.xpire.commons.core.Messages.MESSAGE_SUGGESTIONS;
import static io.xpire.logic.parser.CommandParserTestUtil.assertEqualsParseSuccess;
import static io.xpire.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import io.xpire.logic.commands.SortCommand;
import io.xpire.model.item.sort.XpireMethodOfSorting;


public class SortCommandParserTest {
    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validArgs_returnsSortCommand() {
        assertEqualsParseSuccess(parser, "name", new SortCommand(new XpireMethodOfSorting("name")));
        assertEqualsParseSuccess(parser, "date", new SortCommand(new XpireMethodOfSorting("date")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String suggestName = String.format(MESSAGE_SUGGESTIONS, "[name]");
        String suggestDate = String.format(MESSAGE_SUGGESTIONS, "[date]");

        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE)); // empty args);
        assertParseFailure(parser, " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE)); // empty args);

        assertParseFailure(parser, "abc", XpireMethodOfSorting.MESSAGE_CONSTRAINTS); // only name or date
        assertParseFailure(parser, "-3", XpireMethodOfSorting.MESSAGE_CONSTRAINTS); // no numbers
        assertParseFailure(parser, "1.5", XpireMethodOfSorting.MESSAGE_CONSTRAINTS); // no numbers

        // only lowercase date
        assertParseFailure(parser, "Date", XpireMethodOfSorting.MESSAGE_CONSTRAINTS + suggestDate);
        assertParseFailure(parser, "DATE", XpireMethodOfSorting.MESSAGE_CONSTRAINTS + suggestDate);

        // only lowercase name
        assertParseFailure(parser, "Name", XpireMethodOfSorting.MESSAGE_CONSTRAINTS + suggestName);
        assertParseFailure(parser, "NAME", XpireMethodOfSorting.MESSAGE_CONSTRAINTS + suggestName);
    }
}
