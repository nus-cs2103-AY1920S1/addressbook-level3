package io.xpire.logic.parser;

import static io.xpire.commons.core.Messages.MESSAGE_SUGGESTIONS;
import static io.xpire.logic.parser.CommandParserTestUtil.assertEqualsParseSuccess;
import static io.xpire.logic.parser.CommandParserTestUtil.assertParseFailure;
import static io.xpire.logic.parser.ViewCommandParser.MESSAGE_VIEW_OPTIONS;

import org.junit.jupiter.api.Test;

import io.xpire.logic.commands.ViewCommand;
import io.xpire.model.ListType;

public class ViewCommandParserTest {
    private ViewCommandParser xpireParser = new ViewCommandParser(ListType.XPIRE);
    private ViewCommandParser replenishParser = new ViewCommandParser(ListType.REPLENISH);

    @Test
    public void parse_validArgs_returnsViewCommand() {
        //viewing current list from Xpire
        assertEqualsParseSuccess(xpireParser, "", new ViewCommand(ListType.XPIRE));

        //viewing current list from replenish list
        assertEqualsParseSuccess(replenishParser, "", new ViewCommand(ListType.REPLENISH));

        //viewing Xpire from replenish list
        assertEqualsParseSuccess(replenishParser, "main", new ViewCommand(ListType.XPIRE));

        //viewing replenish list from Xpire
        assertEqualsParseSuccess(xpireParser, "replenish", new ViewCommand(ListType.REPLENISH));

        //viewing Xpire from Xpire
        assertEqualsParseSuccess(xpireParser, "main", new ViewCommand(ListType.XPIRE));

        //viewing replenish list from replenish list
        assertEqualsParseSuccess(replenishParser, "replenish", new ViewCommand(ListType.REPLENISH));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String suggestMain = String.format(MESSAGE_SUGGESTIONS, "[main]");
        String suggestReplenish = String.format(MESSAGE_SUGGESTIONS, "[replenish]");

        assertParseFailure(xpireParser, "abc", MESSAGE_VIEW_OPTIONS); // only name or date
        assertParseFailure(xpireParser, "-3", MESSAGE_VIEW_OPTIONS); // no numbers
        assertParseFailure(xpireParser, "1.5", MESSAGE_VIEW_OPTIONS); // no numbers
        assertParseFailure(replenishParser, "abc", MESSAGE_VIEW_OPTIONS); // only name or date
        assertParseFailure(replenishParser, "-3", MESSAGE_VIEW_OPTIONS); // no numbers
        assertParseFailure(replenishParser, "1.5", MESSAGE_VIEW_OPTIONS); // no numbers

        // only lowercase date
        assertParseFailure(xpireParser, "Replenish", MESSAGE_VIEW_OPTIONS + suggestReplenish);
        assertParseFailure(xpireParser, "REPLENISH", MESSAGE_VIEW_OPTIONS + suggestReplenish);
        assertParseFailure(replenishParser, "Replenish", MESSAGE_VIEW_OPTIONS + suggestReplenish);
        assertParseFailure(replenishParser, "REPLENISH", MESSAGE_VIEW_OPTIONS + suggestReplenish);

        // only lowercase name
        assertParseFailure(xpireParser, "Main", MESSAGE_VIEW_OPTIONS + suggestMain);
        assertParseFailure(xpireParser, "MAIN", MESSAGE_VIEW_OPTIONS + suggestMain);
        assertParseFailure(replenishParser, "Main", MESSAGE_VIEW_OPTIONS + suggestMain);
        assertParseFailure(replenishParser, "MAIN", MESSAGE_VIEW_OPTIONS + suggestMain);
    }
}
