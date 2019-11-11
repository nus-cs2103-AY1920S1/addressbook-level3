package seedu.sugarmummy.logic.parser.recmf;

import static seedu.sugarmummy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_FOOD_NAME;
import static seedu.sugarmummy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.sugarmummy.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.sugarmummy.testutil.recmf.TypicalFoods.CALORIE_DESC_CHICKEN;
import static seedu.sugarmummy.testutil.recmf.TypicalFoods.FAT_DESC_CHICKEN;
import static seedu.sugarmummy.testutil.recmf.TypicalFoods.GI_DESC_CHICKEN;
import static seedu.sugarmummy.testutil.recmf.TypicalFoods.NAME_DESC_CHICKEN;
import static seedu.sugarmummy.testutil.recmf.TypicalFoods.NAME_DESC_CORN;
import static seedu.sugarmummy.testutil.recmf.TypicalFoods.SUGAR_DESC_CHICKEN;
import static seedu.sugarmummy.testutil.recmf.TypicalFoods.SUGAR_DESC_CORN;
import static seedu.sugarmummy.testutil.recmf.TypicalFoods.TYPE_DESC_CHICKEN;
import static seedu.sugarmummy.testutil.recmf.TypicalFoods.TYPE_DESC_CORN;
import static seedu.sugarmummy.testutil.recmf.TypicalFoods.WHITESPACE;

import org.junit.jupiter.api.Test;

import seedu.sugarmummy.logic.commands.recmf.AddFoodCommand;
import seedu.sugarmummy.model.recmf.Food;
import seedu.sugarmummy.testutil.recmf.FoodBuilder;

class AddFoodCommandParserTest {

    private AddFoodCommandParser parser = new AddFoodCommandParser();

    @Test
    void parse_allFieldsPresent_succeeds() {
        Food expectedFood = new FoodBuilder().withFoodName("Chicken").withFoodType("p").build();

        assertParseSuccess(parser, WHITESPACE + NAME_DESC_CHICKEN + TYPE_DESC_CHICKEN + CALORIE_DESC_CHICKEN
                + GI_DESC_CHICKEN + SUGAR_DESC_CHICKEN + FAT_DESC_CHICKEN, new AddFoodCommand(expectedFood));
        assertParseSuccess(parser, WHITESPACE + NAME_DESC_CORN + TYPE_DESC_CORN + SUGAR_DESC_CORN
                + CALORIE_DESC_CHICKEN + NAME_DESC_CHICKEN + GI_DESC_CHICKEN + TYPE_DESC_CORN + TYPE_DESC_CHICKEN
                + SUGAR_DESC_CHICKEN + FAT_DESC_CHICKEN, new AddFoodCommand(expectedFood));
    }

    @Test
    void parse_missingFields_fails() {
        Food expectedFood = new FoodBuilder().withFoodName("Chicken").withFoodType("p").build();

        assertParseFailure(parser, WHITESPACE + NAME_DESC_CHICKEN + CALORIE_DESC_CHICKEN + SUGAR_DESC_CHICKEN
                + FAT_DESC_CHICKEN, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFoodCommand.MESSAGE_USAGE));

        assertParseFailure(parser, WHITESPACE + PREFIX_FOOD_NAME + TYPE_DESC_CHICKEN + CALORIE_DESC_CHICKEN
                        + GI_DESC_CHICKEN + SUGAR_DESC_CHICKEN + FAT_DESC_CHICKEN,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFoodCommand.MESSAGE_USAGE));
        assertParseFailure(parser, WHITESPACE + NAME_DESC_CHICKEN + PREFIX_FOOD_NAME + TYPE_DESC_CHICKEN
                        + CALORIE_DESC_CHICKEN + GI_DESC_CHICKEN + SUGAR_DESC_CHICKEN + FAT_DESC_CHICKEN,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFoodCommand.MESSAGE_USAGE));
    }
}
