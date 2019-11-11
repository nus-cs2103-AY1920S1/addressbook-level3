package seedu.sugarmummy.logic.parser.recmf;

import static seedu.sugarmummy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.sugarmummy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.sugarmummy.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.sugarmummy.model.recmf.FoodComparator.DEFAULT_SORT_ORDER_STRING;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.sugarmummy.logic.commands.recmf.RecmFoodCommand;
import seedu.sugarmummy.model.recmf.FoodComparator;
import seedu.sugarmummy.model.recmf.FoodType;
import seedu.sugarmummy.model.recmf.predicates.FoodNameContainsKeywordsPredicate;
import seedu.sugarmummy.model.recmf.predicates.FoodTypeIsWantedPredicate;

public class RecmFoodCommandParserTest {

    private RecmFoodCommandParser parser = new RecmFoodCommandParser();
    private Set<FoodType> types = new HashSet<>(Arrays.asList(FoodType.PROTEIN, FoodType.FRUIT, FoodType.MEAL));
    private List<String> keywords = Arrays.asList("Cherry", "Chicken", "Pumpkin");
    private FoodComparator foodComparator = new FoodComparator(DEFAULT_SORT_ORDER_STRING);

    @Test
    public void parse_noFilters_returnsRecmFoodCommand() {

        RecmFoodCommand expectedRecmFoodCommand =
                new RecmFoodCommand(new FoodTypeIsWantedPredicate(new HashSet<>()),
                        new FoodNameContainsKeywordsPredicate(new ArrayList<>()), foodComparator);

        assertParseSuccess(parser, "", expectedRecmFoodCommand);
        assertParseSuccess(parser, "   \t  \n", expectedRecmFoodCommand);
    }

    @Test
    public void parse_onlyValidFlags_returnsRecmFoodCommand() {

        RecmFoodCommand expectedRecmFoodCommand =
                new RecmFoodCommand(new FoodTypeIsWantedPredicate(types),
                        new FoodNameContainsKeywordsPredicate(new ArrayList<>()), foodComparator);

        assertParseSuccess(parser, "-p -f -m", expectedRecmFoodCommand);
        assertParseSuccess(parser, "-p -m -f", expectedRecmFoodCommand);

        // duplicate valid flags
        assertParseSuccess(parser, "-p -f -m -f -f", expectedRecmFoodCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "\n   -p   -f \t-m\t", expectedRecmFoodCommand);
    }

    @Test
    public void parse_onlyValidKeyWords_returnsRecmFoodCommand() {

        RecmFoodCommand expectedRecmFoodCommand =
                new RecmFoodCommand(new FoodTypeIsWantedPredicate(new HashSet<>()),
                        new FoodNameContainsKeywordsPredicate(keywords), foodComparator);

        assertParseSuccess(parser, " fn/Cherry Chicken Pumpkin", expectedRecmFoodCommand);
        assertParseSuccess(parser, " fn/\n   Cherry \tChicken Pumpkin\t", expectedRecmFoodCommand);
    }

    @Test
    public void parse_bothValidFlagsAndKeyWords_returnsRecmFoodCommand() {
        RecmFoodCommand expectedRecmFoodCommand =
                new RecmFoodCommand(new FoodTypeIsWantedPredicate(types),
                        new FoodNameContainsKeywordsPredicate(keywords), foodComparator);

        assertParseSuccess(parser, "-p -f -m fn/Cherry Chicken Pumpkin", expectedRecmFoodCommand);
        assertParseSuccess(parser, "-p -f -m -f -f\n fn/ Cherry \tChicken Pumpkin\t", expectedRecmFoodCommand);
    }

    @Test
    public void parse_inValidFilters_throwsParserException() {
        assertParseFailure(parser, "-f p m", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RecmFoodCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-f -pm", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RecmFoodCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " ft/p m", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RecmFoodCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-f -p m fn/Cherry", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RecmFoodCommand.MESSAGE_USAGE));
    }

}
