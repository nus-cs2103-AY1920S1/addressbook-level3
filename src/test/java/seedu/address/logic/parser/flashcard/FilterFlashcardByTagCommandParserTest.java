package seedu.address.logic.parser.flashcard;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_MODULE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.flashcard.FilterFlashcardByTagCommand;
import seedu.address.model.flashcard.FlashcardContainsTagPredicate;
import seedu.address.model.tag.Tag;

class FilterFlashcardByTagCommandParserTest {
    private FilterFlashcardByTagCommandParser parser = new FilterFlashcardByTagCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterFlashcardByTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterFlashcardByTagCommand() {
        Tag tagOne = new Tag("cs2100");
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(tagOne);
        ArrayList<String> tagList = new ArrayList<>();
        tagList.add("cs2100");
        // no leading and trailing whitespaces
        FilterFlashcardByTagCommand expectedFilterFlashcardByTagCommand =
                new FilterFlashcardByTagCommand(new FlashcardContainsTagPredicate(tagSet), tagList);
        assertParseSuccess(parser, TAG_DESC_MODULE, expectedFilterFlashcardByTagCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n " + TAG_DESC_MODULE + " \n \t", expectedFilterFlashcardByTagCommand);
    }
}
