package seedu.exercise.logic.parser;

import static seedu.exercise.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.exercise.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.exercise.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.exercise.model.property.custom.CustomProperty.FULL_NAME_CONSTRAINTS;
import static seedu.exercise.model.property.custom.CustomProperty.PREFIX_NAME_CONSTRAINTS;
import static seedu.exercise.model.property.custom.ParameterType.PARAMETER_CONSTRAINTS;
import static seedu.exercise.testutil.CommonTestData.FULL_NAME_DESC_END_DATE;
import static seedu.exercise.testutil.CommonTestData.FULL_NAME_DESC_RATING;
import static seedu.exercise.testutil.CommonTestData.FULL_NAME_DESC_REMARK;
import static seedu.exercise.testutil.CommonTestData.INVALID_FULL_NAME_DESC;
import static seedu.exercise.testutil.CommonTestData.INVALID_PARAMETER_TYPE_DESC;
import static seedu.exercise.testutil.CommonTestData.INVALID_PREFIX_NAME_DESC;
import static seedu.exercise.testutil.CommonTestData.PARAMETER_TYPE_DESC_END_DATE;
import static seedu.exercise.testutil.CommonTestData.PARAMETER_TYPE_DESC_RATING;
import static seedu.exercise.testutil.CommonTestData.PARAMETER_TYPE_DESC_REMARK;
import static seedu.exercise.testutil.CommonTestData.PREAMBLE_NON_EMPTY;
import static seedu.exercise.testutil.CommonTestData.PREAMBLE_WHITESPACE;
import static seedu.exercise.testutil.CommonTestData.PREFIX_NAME_DESC_END_DATE;
import static seedu.exercise.testutil.CommonTestData.PREFIX_NAME_DESC_RATING;
import static seedu.exercise.testutil.CommonTestData.PREFIX_NAME_DESC_REMARK;
import static seedu.exercise.testutil.CommonTestData.VALID_FULL_NAME_RATING;
import static seedu.exercise.testutil.CommonTestData.VALID_PARAMETER_TYPE_RATING;
import static seedu.exercise.testutil.CommonTestData.VALID_PREFIX_NAME_RATING;
import static seedu.exercise.testutil.CommonTestData.VALID_PREFIX_REMOVE_CUSTOM_PROPERTY;
import static seedu.exercise.testutil.typicalutil.TypicalCustomProperties.RATING;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.exercise.logic.commands.CustomAddCommand;
import seedu.exercise.logic.commands.CustomCommand;
import seedu.exercise.logic.commands.CustomRemoveCommand;
import seedu.exercise.model.property.custom.CustomProperty;
import seedu.exercise.testutil.builder.CustomPropertyBuilder;

class CustomCommandParserTest {

    private CustomCommandParser parser = new CustomCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        CustomProperty expectedCustomProperty = new CustomPropertyBuilder(RATING).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PREFIX_NAME_DESC_RATING
            + FULL_NAME_DESC_RATING + PARAMETER_TYPE_DESC_RATING,
            new CustomAddCommand(expectedCustomProperty));

        // multiple short names - last short name accepted
        assertParseSuccess(parser, PREFIX_NAME_DESC_END_DATE + PREFIX_NAME_DESC_RATING
            + FULL_NAME_DESC_RATING + PARAMETER_TYPE_DESC_RATING,
            new CustomAddCommand(expectedCustomProperty));

        // multiple full names - last full name accepted
        assertParseSuccess(parser, PREFIX_NAME_DESC_RATING + FULL_NAME_DESC_END_DATE
            + FULL_NAME_DESC_RATING + PARAMETER_TYPE_DESC_RATING,
            new CustomAddCommand(expectedCustomProperty));

        // multiple parameter types - last parameter type accepted
        assertParseSuccess(parser, PREFIX_NAME_DESC_RATING + FULL_NAME_DESC_RATING
            + PARAMETER_TYPE_DESC_END_DATE + PARAMETER_TYPE_DESC_RATING,
            new CustomAddCommand(expectedCustomProperty));

        //prefix for remove custom property
        assertParseSuccess(parser, VALID_PREFIX_REMOVE_CUSTOM_PROPERTY + VALID_FULL_NAME_RATING,
            new CustomRemoveCommand(VALID_FULL_NAME_RATING, Optional.empty()));
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

        //valid add custom values but prefix for remove custom present
        assertParseFailure(parser, PREFIX_NAME_DESC_RATING + FULL_NAME_DESC_RATING + PARAMETER_TYPE_DESC_RATING
            + VALID_PREFIX_REMOVE_CUSTOM_PROPERTY + VALID_FULL_NAME_RATING,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, CustomCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPrefixRemoveCustom_failure() {
        //non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + VALID_PREFIX_REMOVE_CUSTOM_PROPERTY + VALID_FULL_NAME_RATING,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, CustomCommand.MESSAGE_USAGE));

        //prefix for custom name
        assertParseFailure(parser, VALID_PREFIX_REMOVE_CUSTOM_PROPERTY + VALID_FULL_NAME_RATING
            + PREFIX_NAME_DESC_RATING, String.format(MESSAGE_INVALID_COMMAND_FORMAT, CustomCommand.MESSAGE_USAGE));

        //prefix for full name
        assertParseFailure(parser, VALID_PREFIX_REMOVE_CUSTOM_PROPERTY + VALID_FULL_NAME_RATING
            + FULL_NAME_DESC_RATING, String.format(MESSAGE_INVALID_COMMAND_FORMAT, CustomCommand.MESSAGE_USAGE));

        //prefix for parameter type
        assertParseFailure(parser, VALID_PREFIX_REMOVE_CUSTOM_PROPERTY + VALID_FULL_NAME_RATING
            + PARAMETER_TYPE_DESC_RATING,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, CustomCommand.MESSAGE_USAGE));
    }
}
