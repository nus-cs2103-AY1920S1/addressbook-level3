package seedu.exercise.logic.parser;

import static seedu.exercise.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.exercise.logic.commands.CommandTestUtil.FULL_NAME_DESC_ENDDATE;
import static seedu.exercise.logic.commands.CommandTestUtil.FULL_NAME_DESC_RATING;
import static seedu.exercise.logic.commands.CommandTestUtil.FULL_NAME_DESC_REMARK;
import static seedu.exercise.logic.commands.CommandTestUtil.INVALID_FULL_NAME_DESC;
import static seedu.exercise.logic.commands.CommandTestUtil.INVALID_PARAMETER_TYPE_DESC;
import static seedu.exercise.logic.commands.CommandTestUtil.INVALID_PREFIX_NAME_DESC;
import static seedu.exercise.logic.commands.CommandTestUtil.PARAMETER_TYPE_DESC_ENDDATE;
import static seedu.exercise.logic.commands.CommandTestUtil.PARAMETER_TYPE_DESC_RATING;
import static seedu.exercise.logic.commands.CommandTestUtil.PARAMETER_TYPE_DESC_REMARK;
import static seedu.exercise.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.exercise.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.exercise.logic.commands.CommandTestUtil.PREFIX_NAME_DESC_ENDDATE;
import static seedu.exercise.logic.commands.CommandTestUtil.PREFIX_NAME_DESC_RATING;
import static seedu.exercise.logic.commands.CommandTestUtil.PREFIX_NAME_DESC_REMARK;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_FULL_NAME_RATING;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_PARAMETER_TYPE_RATING;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_PREFIX_NAME_RATING;
import static seedu.exercise.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.exercise.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.exercise.model.property.CustomProperty.FULL_NAME_CONSTRAINTS;
import static seedu.exercise.model.property.CustomProperty.PREFIX_NAME_CONSTRAINTS;
import static seedu.exercise.model.property.ParameterType.PARAMETER_CONSTRAINTS;
import static seedu.exercise.testutil.TypicalCustomProperties.RATING;

import org.junit.jupiter.api.Test;

import seedu.exercise.logic.commands.CustomCommand;
import seedu.exercise.model.property.CustomProperty;
import seedu.exercise.testutil.CustomPropertyBuilder;

class CustomCommandParserTest {

    private CustomCommandParser parser = new CustomCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        CustomProperty expectedCustomProperty = new CustomPropertyBuilder(RATING).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PREFIX_NAME_DESC_RATING
                        + FULL_NAME_DESC_RATING + PARAMETER_TYPE_DESC_RATING,
                new CustomCommand(expectedCustomProperty));

        // multiple short names - last short name accepted
        assertParseSuccess(parser, PREFIX_NAME_DESC_ENDDATE + PREFIX_NAME_DESC_RATING
                        + FULL_NAME_DESC_RATING + PARAMETER_TYPE_DESC_RATING,
                new CustomCommand(expectedCustomProperty));

        // multiple full names - last full name accepted
        assertParseSuccess(parser, PREFIX_NAME_DESC_RATING + FULL_NAME_DESC_ENDDATE
                        + FULL_NAME_DESC_RATING + PARAMETER_TYPE_DESC_RATING,
                new CustomCommand(expectedCustomProperty));

        // multiple parameter types - last parameter type accepted
        assertParseSuccess(parser, PREFIX_NAME_DESC_RATING + FULL_NAME_DESC_RATING
                        + PARAMETER_TYPE_DESC_ENDDATE + PARAMETER_TYPE_DESC_RATING,
                new CustomCommand(expectedCustomProperty));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CustomCommand.MESSAGE_USAGE);

        // missing short name prefix
        assertParseFailure(parser, VALID_PREFIX_NAME_RATING + FULL_NAME_DESC_RATING
                + PARAMETER_TYPE_DESC_RATING, expectedMessage);

        // missing full name prefix
        assertParseFailure(parser, PREFIX_NAME_DESC_RATING + VALID_FULL_NAME_RATING
                + PARAMETER_TYPE_DESC_RATING, expectedMessage);

        // missing parameter type prefix
        assertParseFailure(parser, PREFIX_NAME_DESC_RATING + FULL_NAME_DESC_RATING
                + VALID_PARAMETER_TYPE_RATING, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_PREFIX_NAME_RATING + VALID_FULL_NAME_RATING
                + VALID_PARAMETER_TYPE_RATING, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid short name
        assertParseFailure(parser, INVALID_PREFIX_NAME_DESC + FULL_NAME_DESC_RATING
                + PARAMETER_TYPE_DESC_RATING, PREFIX_NAME_CONSTRAINTS);

        // invalid full name
        assertParseFailure(parser, PREFIX_NAME_DESC_REMARK + INVALID_FULL_NAME_DESC
                + PARAMETER_TYPE_DESC_REMARK, FULL_NAME_CONSTRAINTS);

        // invalid parameter type
        assertParseFailure(parser, PREFIX_NAME_DESC_REMARK + FULL_NAME_DESC_REMARK
                + INVALID_PARAMETER_TYPE_DESC, PARAMETER_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_PREFIX_NAME_DESC + INVALID_FULL_NAME_DESC
                + PARAMETER_TYPE_DESC_RATING, PREFIX_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + PREFIX_NAME_DESC_RATING
                        + FULL_NAME_DESC_RATING + PARAMETER_TYPE_DESC_RATING,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CustomCommand.MESSAGE_USAGE));
    }
}
