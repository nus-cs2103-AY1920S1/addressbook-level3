package seedu.address.logic.parser.cheatsheet;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_MODULE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.cheatsheet.FilterCheatSheetByTagCommand;
import seedu.address.model.cheatsheet.CheatSheetContainsTagPredicate;
import seedu.address.model.tag.Tag;

class FilterCheatSheetByTagCommandParserTest {
    private FilterCheatSheetByTagCommandParser parser = new FilterCheatSheetByTagCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCheatSheetByTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCheatSheetByTagCommand() {
        Tag tagOne = new Tag("cs2100");
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(tagOne);
        ArrayList<String> tagList = new ArrayList<>();
        tagList.add("cs2100");
        // no leading and trailing whitespaces
        FilterCheatSheetByTagCommand expectedFilterCheatSheetByTagCommand =
                new FilterCheatSheetByTagCommand(new CheatSheetContainsTagPredicate(tagSet), tagList);
        assertParseSuccess(parser, TAG_DESC_MODULE, expectedFilterCheatSheetByTagCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n " + TAG_DESC_MODULE + " \n \t", expectedFilterCheatSheetByTagCommand);
    }
}
