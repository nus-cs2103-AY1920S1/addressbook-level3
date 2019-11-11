package seedu.eatme.logic.parser;

import static seedu.eatme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eatme.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.eatme.logic.commands.ReviewCommand;

public class ReviewCommandParserTest {

    public static final String VALID_REVIEW_DESCRIPTION_NO_PREFIX_1 = "good";
    public static final String VALID_REVIEW_DESCRIPTION_WITH_PREFIX_1 = "\\d good";
    public static final String VALID_REVIEW_COST_NO_PREFIX_1 = "3";
    public static final String VALID_REVIEW_COST_WITH_PREFIX_1 = "\\p 3";
    public static final String VALID_REVIEW_RATING_NO_PREFIX_1 = "4";
    public static final String VALID_REVIEW_RATING_WITH_PREFIX_1 = "\\r 4";
    public static final String VALID_REVIEW_DATE_NO_PREFIX_1 = "22/11/2019";
    public static final String VALID_REVIEW_DATE_WITH_PREFIX = "\\w 22/11/2019";

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReviewCommand.MESSAGE_USAGE);
    private ReviewCommandParser parser = new ReviewCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_REVIEW_DESCRIPTION_NO_PREFIX_1, MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // no description
        assertParseFailure(parser, "1" + VALID_REVIEW_DATE_WITH_PREFIX
                + VALID_REVIEW_RATING_WITH_PREFIX_1 + VALID_REVIEW_COST_WITH_PREFIX_1,
                MESSAGE_INVALID_FORMAT);

        // no cost
        assertParseFailure(parser, "1" + VALID_REVIEW_DESCRIPTION_WITH_PREFIX_1
                + VALID_REVIEW_RATING_WITH_PREFIX_1 + VALID_REVIEW_DATE_WITH_PREFIX,
                MESSAGE_INVALID_FORMAT);

        // no rating
        assertParseFailure(parser, "1" + VALID_REVIEW_DATE_WITH_PREFIX
                + VALID_REVIEW_DESCRIPTION_WITH_PREFIX_1 + VALID_REVIEW_COST_WITH_PREFIX_1,
                MESSAGE_INVALID_FORMAT);

        // no date
        assertParseFailure(parser, "1" + VALID_REVIEW_COST_WITH_PREFIX_1
                + VALID_REVIEW_DESCRIPTION_WITH_PREFIX_1 + VALID_REVIEW_RATING_WITH_PREFIX_1,
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_REVIEW_RATING_WITH_PREFIX_1
                + VALID_REVIEW_DESCRIPTION_WITH_PREFIX_1 + VALID_REVIEW_COST_WITH_PREFIX_1
                + VALID_REVIEW_DATE_WITH_PREFIX, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_REVIEW_RATING_WITH_PREFIX_1
                + VALID_REVIEW_DESCRIPTION_WITH_PREFIX_1 + VALID_REVIEW_COST_WITH_PREFIX_1
                + VALID_REVIEW_DATE_WITH_PREFIX, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

}
