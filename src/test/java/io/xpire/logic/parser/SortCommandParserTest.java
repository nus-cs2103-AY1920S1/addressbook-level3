package io.xpire.logic.parser;

import static io.xpire.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static io.xpire.commons.core.Messages.MESSAGE_SUGGESTIONS;
import static io.xpire.logic.parser.CommandParserTestUtil.assertEqualsParseSuccess;
import static io.xpire.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import io.xpire.logic.commands.SortCommand;
import io.xpire.model.item.sort.MethodOfSorting;

public class SortCommandParserTest {
    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validArgs_returnsSortCommand() {
        assertEqualsParseSuccess(parser, "name", new SortCommand(new MethodOfSorting("name")));
        assertEqualsParseSuccess(parser, "date", new SortCommand(new MethodOfSorting("date")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String suggestName = String.format(MESSAGE_SUGGESTIONS, "[name]");
        String suggestDate = String.format(MESSAGE_SUGGESTIONS, "[date]");

        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE)); // empty args);
        assertParseFailure(parser, " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE)); // empty args);

        assertParseFailure(parser, "abc", MethodOfSorting.MESSAGE_CONSTRAINTS); // only name or date
        assertParseFailure(parser, "-3", MethodOfSorting.MESSAGE_CONSTRAINTS); // no numbers
        assertParseFailure(parser, "1.5", MethodOfSorting.MESSAGE_CONSTRAINTS); // no numbers

        // only lowercase date
        assertParseFailure(parser, "Date", MethodOfSorting.MESSAGE_CONSTRAINTS + suggestDate);
        assertParseFailure(parser, "DATE", MethodOfSorting.MESSAGE_CONSTRAINTS + suggestDate);

        // only lowercase name
        assertParseFailure(parser, "Name", MethodOfSorting.MESSAGE_CONSTRAINTS + suggestName);
        assertParseFailure(parser, "NAME", MethodOfSorting.MESSAGE_CONSTRAINTS + suggestName);
    }
}
