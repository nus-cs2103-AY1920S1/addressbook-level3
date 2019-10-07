package calofit.logic.parser;

import calofit.logic.commands.AddCommand;
import calofit.logic.commands.CommandTestUtil;
import calofit.model.meal.Meal;
import calofit.testutil.MealBuilder;
import calofit.testutil.TypicalMeals;
import org.junit.jupiter.api.Test;
import calofit.model.meal.Name;
import calofit.model.tag.Tag;

import static calofit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Meal expectedMeal = new MealBuilder(TypicalMeals.BOB).withTags(CommandTestUtil.VALID_TAG_FRIEND).build();

        // whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.PREAMBLE_WHITESPACE + CommandTestUtil.NAME_DESC_BOB
                + CommandTestUtil.TAG_DESC_FRIEND, new AddCommand(expectedMeal));

        // multiple names - last name accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_AMY + CommandTestUtil.NAME_DESC_BOB
                + CommandTestUtil.TAG_DESC_FRIEND, new AddCommand(expectedMeal));

        // multiple tags - all accepted
        Meal expectedMealMultipleTags = new MealBuilder(TypicalMeals.BOB).withTags(CommandTestUtil.VALID_TAG_FRIEND, CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_BOB
                + CommandTestUtil.TAG_DESC_HUSBAND + CommandTestUtil.TAG_DESC_FRIEND, new AddCommand(expectedMealMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Meal expectedMeal = new MealBuilder(TypicalMeals.AMY).withTags().build();
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_AMY,
                new AddCommand(expectedMeal));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_NAME_DESC
                + CommandTestUtil.TAG_DESC_HUSBAND + CommandTestUtil.TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid tag
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB
                + CommandTestUtil.INVALID_TAG_DESC + CommandTestUtil.VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.PREAMBLE_NON_EMPTY + CommandTestUtil.NAME_DESC_BOB
                + CommandTestUtil.TAG_DESC_HUSBAND + CommandTestUtil.TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
