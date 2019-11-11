package seedu.eatme.logic.parser;

import static seedu.eatme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_TAG_NO_PREFIX_NICE;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_TAG_WITH_PREFIX_NICE;
import static seedu.eatme.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.eatme.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.eatme.testutil.TypicalIndexes.INDEX_FIRST_EATERY;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.eatme.commons.core.index.Index;
import seedu.eatme.logic.commands.RemoveTagCommand;
import seedu.eatme.logic.commands.RemoveTagCommand.EditEateryDescriptor;
import seedu.eatme.model.eatery.Tag;


public class RemoveTagCommandParserTest {
    private RemoveTagCommandParser parser = new RemoveTagCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Index targetIndex = INDEX_FIRST_EATERY;

        RemoveTagCommand.EditEateryDescriptor editEateryDescriptor = new EditEateryDescriptor();
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(VALID_TAG_NO_PREFIX_NICE));
        editEateryDescriptor.addTags(tags);

        assertParseSuccess(parser, "1" + VALID_TAG_WITH_PREFIX_NICE, new RemoveTagCommand(targetIndex,
                editEateryDescriptor));
    }

    @Test
    public void parse_tagFieldsMissing_success() {
        // eatery just remains the same
        Index targetIndex = INDEX_FIRST_EATERY;

        RemoveTagCommand.EditEateryDescriptor editEateryDescriptor = new RemoveTagCommand.EditEateryDescriptor();
        Set<Tag> tags = new HashSet<>();
        editEateryDescriptor.addTags(tags);

        assertParseSuccess(parser, "1", new RemoveTagCommand(targetIndex,
                editEateryDescriptor));
    }

    @Test
    public void parse_compulsoryFieldsMissing_throwsParseException() {
        // missing index
        assertParseFailure(parser, VALID_TAG_WITH_PREFIX_NICE, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemoveTagCommand.MESSAGE_USAGE));
    }
}
