package calofit.logic.parser;

import static calofit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import calofit.logic.commands.AddCommand;
import calofit.logic.commands.CommandTestUtil;
import calofit.model.dish.Dish;
import calofit.model.dish.Name;
import calofit.model.tag.Tag;
import calofit.testutil.DishBuilder;
import calofit.testutil.TypicalDishes;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Dish expectedDish = new DishBuilder(TypicalDishes.MACARONI)
                .withTags(CommandTestUtil.VALID_TAG_EXPENSIVE).build();

        // whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.PREAMBLE_WHITESPACE
                + CommandTestUtil.NAME_DESC_MACARONI
                + CommandTestUtil.CALORIE_DESC_1000
                + CommandTestUtil.TAG_DESC_EXPENSIVE, new AddCommand(expectedDish));

        // multiple names - last name accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_DUCK_RICE
                + CommandTestUtil.NAME_DESC_MACARONI
                + CommandTestUtil.CALORIE_DESC_1000
                + CommandTestUtil.TAG_DESC_EXPENSIVE, new AddCommand(expectedDish));

        // multiple tags - all accepted
        Dish expectedDishMultipleTags = new DishBuilder(TypicalDishes.MACARONI)
                .withTags(CommandTestUtil.VALID_TAG_EXPENSIVE, CommandTestUtil.VALID_TAG_SALTY)
                .build();
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_MACARONI
                + CommandTestUtil.CALORIE_DESC_1000
                + CommandTestUtil.TAG_DESC_SALTY + CommandTestUtil.TAG_DESC_EXPENSIVE,
                new AddCommand(expectedDishMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Dish expectedDish = new DishBuilder(TypicalDishes.DUCK_RICE).withTags().build();
        CommandParserTestUtil.assertParseSuccess(parser,
                CommandTestUtil.NAME_DESC_DUCK_RICE + CommandTestUtil.CALORIE_DESC_1000,
                new AddCommand(expectedDish));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_MACARONI,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_NAME_DESC
                + CommandTestUtil.CALORIE_DESC_1000
                + CommandTestUtil.TAG_DESC_SALTY + CommandTestUtil.TAG_DESC_EXPENSIVE, Name.MESSAGE_CONSTRAINTS);

        // invalid tag
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_MACARONI
                + CommandTestUtil.CALORIE_DESC_1000
                + CommandTestUtil.INVALID_TAG_DESC + CommandTestUtil.VALID_TAG_EXPENSIVE, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.PREAMBLE_NON_EMPTY
                        + CommandTestUtil.NAME_DESC_MACARONI
                        + CommandTestUtil.CALORIE_DESC_1000
                        + CommandTestUtil.TAG_DESC_SALTY
                        + CommandTestUtil.TAG_DESC_EXPENSIVE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
