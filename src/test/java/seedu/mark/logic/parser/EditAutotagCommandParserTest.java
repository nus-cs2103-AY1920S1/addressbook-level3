package seedu.mark.logic.parser;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_FOLDER;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_NOT_FOLDER;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_NOT_NAME;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_NOT_URL;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_URL;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.mark.logic.commands.AutotagCommand;
import seedu.mark.logic.commands.EditAutotagCommand;
import seedu.mark.model.autotag.SelectiveBookmarkTagger;
import seedu.mark.model.predicates.BookmarkPredicate;
import seedu.mark.model.tag.Tag;

public class EditAutotagCommandParserTest {

    private static final String VALID_TAG = "myTag";
    private static final String VALID_TAG_2 = "myTag2";
    private static final String VALID_NAME_1 = "website";
    private static final String VALID_NAME_2 = "question";
    private static final String VALID_URL = "facebook.com";
    private static final String VALID_FOLDER = "Quiz";

    private static final String TAG_DESC = " " + PREFIX_TAG + VALID_TAG_2;
    private static final String NAME_DESC_1 = " " + PREFIX_NAME + VALID_NAME_1;
    private static final String NAME_DESC_2 = " " + PREFIX_NAME + VALID_NAME_2;
    private static final String NOT_NAME_DESC = " " + PREFIX_NOT_NAME + VALID_NAME_1;
    private static final String URL_DESC = " " + PREFIX_URL + VALID_URL;
    private static final String NOT_URL_DESC = " " + PREFIX_NOT_URL + VALID_URL;
    private static final String FOLDER_DESC = " " + PREFIX_FOLDER + VALID_FOLDER;
    private static final String NOT_FOLDER_DESC = " " + PREFIX_NOT_FOLDER + VALID_FOLDER;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditAutotagCommand.MESSAGE_USAGE);

    private final EditAutotagCommandParser parser = new EditAutotagCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no tag name specified
        assertParseFailure(parser, NAME_DESC_1, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, VALID_TAG, EditAutotagCommand.MESSAGE_NO_FIELD_EDITED);

        // empty condition
        assertParseFailure(parser, VALID_TAG + " " + PREFIX_URL, AutotagCommand.MESSAGE_CONDITION_EMPTY);

        // empty input
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = VALID_TAG + TAG_DESC + NAME_DESC_1 + URL_DESC;

        EditAutotagCommand expectedCommand = new EditAutotagCommand(VALID_TAG,
                new SelectiveBookmarkTagger(new Tag(VALID_TAG_2), new BookmarkPredicate()
                        .withNameKeywords(Collections.singletonList(VALID_NAME_1))
                        .withUrlKeywords(Collections.singletonList(VALID_URL))));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        // tag name
        String userInput = VALID_TAG + TAG_DESC;
        EditAutotagCommand expectedCommand = new EditAutotagCommand(VALID_TAG,
                new SelectiveBookmarkTagger(new Tag(VALID_TAG_2), new BookmarkPredicate()));
        assertParseSuccess(parser, userInput, expectedCommand);

        // name + url + folder
        userInput = VALID_TAG + NAME_DESC_1 + URL_DESC + FOLDER_DESC;
        expectedCommand = new EditAutotagCommand(VALID_TAG,
                new SelectiveBookmarkTagger(new Tag(VALID_TAG), new BookmarkPredicate()
                        .withNameKeywords(Collections.singletonList(VALID_NAME_1))
                        .withUrlKeywords(Collections.singletonList(VALID_URL))
                        .withFolder(Collections.singletonList(VALID_FOLDER))));

        assertParseSuccess(parser, userInput, expectedCommand);

        // not name + not url + not folder
        userInput = VALID_TAG + NOT_NAME_DESC + NOT_URL_DESC + NOT_FOLDER_DESC;
        expectedCommand = new EditAutotagCommand(VALID_TAG,
                new SelectiveBookmarkTagger(new Tag(VALID_TAG), new BookmarkPredicate()
                        .withoutNameKeywords(Collections.singletonList(VALID_NAME_1))
                        .withoutUrlKeywords(Collections.singletonList(VALID_URL))
                        .withoutFolder(Collections.singletonList(VALID_FOLDER))));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_repeatedFields_success() {
        String userInput = VALID_TAG + NAME_DESC_1 + NAME_DESC_2;

        EditAutotagCommand expectedCommand = new EditAutotagCommand(VALID_TAG,
                new SelectiveBookmarkTagger(new Tag(VALID_TAG),
                        new BookmarkPredicate().withNameKeywords(Arrays.asList(VALID_NAME_1, VALID_NAME_2))));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_repeatedTagNames_throwsException() {
        String userInput = VALID_TAG + TAG_DESC + TAG_DESC;

        assertParseFailure(parser, userInput, EditAutotagCommand.MESSAGE_MULTIPLE_TAG_NAMES);
    }
}
