package seedu.moolah.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_FIRST_START_DATE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_MENU;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_MODE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_PERIOD;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_SECOND_START_DATE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_TIMESTAMP;
import static seedu.moolah.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.moolah.commons.core.index.Index;
import seedu.moolah.logic.commands.budget.EditBudgetCommand;
import seedu.moolah.logic.commands.event.EditEventCommand;
import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.logic.commands.expense.EditExpenseCommand;
import seedu.moolah.logic.commands.statistics.StatsCommand;
import seedu.moolah.logic.commands.statistics.StatsDescriptor;
import seedu.moolah.logic.commands.statistics.StatsTrendDescriptor;
import seedu.moolah.model.Model;
import seedu.moolah.model.MooLah;
import seedu.moolah.model.expense.DescriptionContainsKeywordsPredicate;
import seedu.moolah.model.expense.Expense;
import seedu.moolah.model.expense.Timestamp;
import seedu.moolah.testutil.EditBudgetDescriptorBuilder;
import seedu.moolah.testutil.EditEventDescriptorBuilder;
import seedu.moolah.testutil.EditExpenseDescriptorBuilder;
import seedu.moolah.testutil.StatsDescriptorBuilder;
import seedu.moolah.testutil.StatsTrendDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_EXPENSE_DESCRIPTION_CHICKEN = "Chicken Rice";
    public static final String VALID_EXPENSE_DESCRIPTION_TAXI = "Taxi to work";
    public static final String VALID_EXPENSE_PRICE_CHICKEN = "11111.11";
    public static final String VALID_EXPENSE_PRICE_TAXI = "22222222.";
    public static final String VALID_EXPENSE_CATEGORY_CHICKEN = "FoOd";
    public static final String VALID_EXPENSE_CATEGORY_TAXI = "transport";
    public static final String VALID_EXPENSE_TIMESTAMP_CHICKEN = "1 week ago";
    public static final String VALID_EXPENSE_TIMESTAMP_TAXI = "yesterday";
    public static final String VALID_EXPENSE_UNIQUE_IDENTIFIER = "Expense@12341234-1234-1234-1234-123412341234";
    public static final String VALID_EXPENSE_MENU_DESCRIPTION_CHICKEN = "Deck Chicken Rice";
    public static final String VALID_EXPENSE_MENU_PRICE_CHICKEN = "2.50";

    public static final String VALID_EVENT_DESCRIPTION_BUFFET = "Family buffet";
    public static final String VALID_EVENT_DESCRIPTION_BIRTHDAY = "Brian bday";
    public static final String VALID_EVENT_PRICE_BUFFET = "300";
    public static final String VALID_EVENT_PRICE_BIRTHDAY = "25.9";
    public static final String VALID_EVENT_CATEGORY_BUFFET = "FoOd";
    public static final String VALID_EVENT_CATEGORY_BIRTHDAY = "shopping";
    public static final String VALID_EVENT_TIMESTAMP_BUFFET = "1 week later";
    public static final String VALID_EVENT_TIMESTAMP_BIRTHDAY = "tomorrow";

    public static final String VALID_BUDGET_DESCRIPTION_SCHOOL = "school related expenses";
    public static final String VALID_BUDGET_AMOUNT_SCHOOL = "300";
    public static final String VALID_BUDGET_START_DATE_SCHOOL = "01-10-2019";
    public static final String VALID_BUDGET_PERIOD_SCHOOL = "month";
    public static final String VALID_BUDGET_DESCRIPTION_HOLIDAY = "holiday";
    public static final String VALID_BUDGET_AMOUNT_HOLIDAY = "3000";
    public static final String VALID_BUDGET_START_DATE_HOLIDAY = "01-01-2019";
    public static final String VALID_BUDGET_PERIOD_HOLIDAY = "year";

    public static final String EXPENSE_DESCRIPTION_DESC_CHICKEN =
            " " + PREFIX_DESCRIPTION + VALID_EXPENSE_DESCRIPTION_CHICKEN;
    public static final String EXPENSE_DESCRIPTION_DESC_TAXI =
            " " + PREFIX_DESCRIPTION + VALID_EXPENSE_DESCRIPTION_TAXI;
    public static final String EXPENSE_PRICE_DESC_CHICKEN = " " + PREFIX_PRICE + VALID_EXPENSE_PRICE_CHICKEN;
    public static final String EXPENSE_PRICE_DESC_TAXI = " " + PREFIX_PRICE + VALID_EXPENSE_PRICE_TAXI;
    public static final String EXPENSE_TIMESTAMP_DESC_CHICKEN =
            " " + PREFIX_TIMESTAMP + VALID_EXPENSE_TIMESTAMP_CHICKEN;
    public static final String EXPENSE_TIMESTAMP_DESC_TAXI = " " + PREFIX_TIMESTAMP + VALID_EXPENSE_TIMESTAMP_TAXI;
    public static final String EXPENSE_CATEGORY_DESC_CHICKEN = " " + PREFIX_CATEGORY + VALID_EXPENSE_CATEGORY_CHICKEN;
    public static final String EXPENSE_CATEGORY_DESC_TAXI = " " + PREFIX_CATEGORY + VALID_EXPENSE_CATEGORY_TAXI;
    public static final String EXPENSE_MENU_DESC_CHICKEN = " " + PREFIX_MENU + VALID_EXPENSE_MENU_DESCRIPTION_CHICKEN;

    public static final String EVENT_DESCRIPTION_DESC_BUFFET =
            " " + PREFIX_DESCRIPTION + VALID_EVENT_DESCRIPTION_BUFFET;
    public static final String EVENT_DESCRIPTION_DESC_BIRTHDAY =
            " " + PREFIX_DESCRIPTION + VALID_EVENT_DESCRIPTION_BIRTHDAY;
    public static final String EVENT_PRICE_DESC_BUFFET = " " + PREFIX_PRICE + VALID_EVENT_PRICE_BUFFET;
    public static final String EVENT_PRICE_DESC_BIRTHDAY = " " + PREFIX_PRICE + VALID_EVENT_PRICE_BIRTHDAY;
    public static final String EVENT_TIMESTAMP_DESC_BUFFET = " " + PREFIX_TIMESTAMP + VALID_EVENT_TIMESTAMP_BUFFET;
    public static final String EVENT_TIMESTAMP_DESC_BIRTHDAY = " " + PREFIX_TIMESTAMP + VALID_EVENT_TIMESTAMP_BIRTHDAY;
    public static final String EVENT_CATEGORY_DESC_BUFFET = " " + PREFIX_CATEGORY + VALID_EVENT_CATEGORY_BUFFET;
    public static final String EVENT_CATEGORY_DESC_BIRTHDAY = " " + PREFIX_CATEGORY + VALID_EVENT_CATEGORY_BIRTHDAY;

    public static final String BUDGET_DESCRIPTION_DESC_SCHOOL =
            " " + PREFIX_DESCRIPTION + VALID_BUDGET_DESCRIPTION_SCHOOL;
    public static final String BUDGET_AMOUNT_DESC_SCHOOL = " " + PREFIX_PRICE + VALID_BUDGET_AMOUNT_SCHOOL;
    public static final String BUDGET_START_DATE_DESC_SCHOOL = " " + PREFIX_START_DATE + VALID_BUDGET_START_DATE_SCHOOL;
    public static final String BUDGET_PERIOD_DESC_SCHOOL = " " + PREFIX_PERIOD + VALID_BUDGET_PERIOD_SCHOOL;
    public static final String INVALID_BUDGET_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION + "sch@@l";
    public static final String INVALID_BUDGET_AMOUNT_DESC = " " + PREFIX_PRICE + "100x";
    public static final String INVALID_BUDGET_START_DATE_DESC = " " + PREFIX_START_DATE + "xdfa";
    public static final String INVALID_BUDGET_PERIOD_DESC = " " + PREFIX_PERIOD + "xdafd";

    // '&' not allowed in descriptions
    public static final String INVALID_EXPENSE_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION + "James&";
    // 'a' not allowed in prices
    public static final String INVALID_EXPENSE_PRICE_DESC = " " + PREFIX_PRICE + "911a";
    // Non Natty-recognised input not allowed in timestamps
    public static final String INVALID_EXPENSE_TIMESTAMP_DESC = " " + PREFIX_TIMESTAMP + "asdf";
    // Future timestamps not allowed in expense timestamps
    public static final String INVALID_FUTURE_TIMESTAMP_EXPENSE_TIMESTAMP_DESC = " " + PREFIX_TIMESTAMP + "tomorrow";
    // '*' not allowed in categories
    public static final String INVALID_EXPENSE_CATEGORY_DESC = " " + PREFIX_CATEGORY + "hubby*";
    // unsupported menu not allowed in menu items
    public static final String INVALID_EXPENSE_MENU_DESC = " " + PREFIX_MENU + "blah";
    // future time not allowed in timestamps
    public static final String INVALID_EXPENSE_MENU_TIMESTAMP_DESC = " " + PREFIX_TIMESTAMP + "1 minute from now";

    // '&' not allowed in descriptions
    public static final String INVALID_EVENT_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION + "James&";
    // 'a' not allowed in prices
    public static final String INVALID_EVENT_PRICE_DESC = " " + PREFIX_PRICE + "911a";
    // Non Natty-recognised input not allowed in timestamps
    public static final String INVALID_EVENT_TIMESTAMP_DESC = " " + PREFIX_TIMESTAMP + "asdf";
    // Past timestamps not allowed in Event timestamps
    public static final String INVALID_PAST_TIMESTAMP_EVENT_TIMESTAMP_DESC = " " + PREFIX_TIMESTAMP + "yesterday";
    // '*' not allowed in categories
    public static final String INVALID_EVENT_CATEGORY_DESC = " " + PREFIX_CATEGORY + "hubby*";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditExpenseCommand.EditExpenseDescriptor DESC_CHICKEN;
    public static final EditEventCommand.EditEventDescriptor DESC_BUFFET;
    public static final EditBudgetCommand.EditBudgetDescriptor DESC_HOLIDAY;


    public static final String VALID_EARLY_TIMESTAMP = "14-01-2019";
    public static final String VALID_LATE_TIMESTAMP = "16-09-2019";

    public static final String VALID_EARLIER_TIMESTAMP = "13-01-2019";
    public static final String VALID_LATER_TIMESTAMP = "17-09-2019";

    public static final String VALID_MODE_CATEGORY = "Category";
    public static final String VALID_MODE_BUDGET = "budget";

    public static final StatsDescriptor VALID_STATS_DESCRIPTOR = new StatsDescriptorBuilder()
            .withStartDate(VALID_EARLY_TIMESTAMP).withEndDate(VALID_LATE_TIMESTAMP).build();

    public static final StatsTrendDescriptor VALID_STATS_TREND_DESCRIPTOR = new StatsTrendDescriptorBuilder()
            .withStartDate(VALID_EARLY_TIMESTAMP).withEndDate(VALID_LATE_TIMESTAMP)
            .withMode(VALID_MODE_CATEGORY).build();


    public static final String STATS_WITHOUT_PREFIX = " 5";
    public static final String STATS_START_DATE_PREFIX_MISSING_INPUT = String.format(" %s %s01-10-2019",
            PREFIX_START_DATE, PREFIX_END_DATE);
    public static final String STATS_END_DATE_PREFIX_MISSING_INPUT = String.format(" %s %s01-10-2019",
            PREFIX_END_DATE, PREFIX_START_DATE);

    public static final String STATS_FIRST_START_DATE_PREFIX_MISSING_INPUT = String.format(" %s %s01-10-2019",
            PREFIX_FIRST_START_DATE, PREFIX_SECOND_START_DATE);
    public static final String STATS_SECOND_START_DATE_PREFIX_MISSING_INPUT = String.format(" %s %s01-10-2019",
            PREFIX_SECOND_START_DATE, PREFIX_FIRST_START_DATE);

    //may depreciate see how
    public static final String STATS_INVALID_PREFIX = String.format(" %s ", PREFIX_CATEGORY);
    public static final String STATS_HIGHER_END_DATE = String.format(" %s31-10-2019 %s01-10-2019",
            PREFIX_START_DATE, PREFIX_END_DATE);
    public static final String STATS_DUPLICATE_DATE_PREFIX = String.format("%s31-10-2019 %s01-10-2019",
            PREFIX_START_DATE, PREFIX_START_DATE);
    public static final String STATS_DUPLICATE_DATE_PREFIX_WITH_COMMAND = String.format("%s %s31-10-2019 %s01-10-2019",
            StatsCommand.COMMAND_WORD, PREFIX_START_DATE, PREFIX_START_DATE);

    public static final String STATS_TREND_START_DATE_PREFIX_MISSING_INPUT = String.format(" %s %s01-10-2019 %sbudget",
            PREFIX_START_DATE, PREFIX_END_DATE, PREFIX_MODE);
    public static final String STATS_TREND_END_DATE_PREFIX_MISSING_INPUT = String.format(" %s %s01-10-2019 %scategory",
            PREFIX_END_DATE, PREFIX_START_DATE, PREFIX_MODE);
    public static final String STATS_TREND_HIGHER_END_DATE = String.format(" %s31-10-2019 %s01-10-2019 %scategory",
            PREFIX_START_DATE, PREFIX_END_DATE, PREFIX_MODE);

    public static final Timestamp ONE_MINUTE_AGO =
            Timestamp.createTimestampIfValid("1 minute ago").get();
    //may depreciate, see how


    static {
        DESC_CHICKEN = new EditExpenseDescriptorBuilder().withDescription(VALID_EXPENSE_DESCRIPTION_CHICKEN)
                .withPrice(VALID_EXPENSE_PRICE_CHICKEN)
                .withTimestamp(VALID_EXPENSE_TIMESTAMP_CHICKEN)
                .withCategory(VALID_EXPENSE_CATEGORY_TAXI).build();
    }

    static {
        DESC_BUFFET = new EditEventDescriptorBuilder().withDescription(VALID_EVENT_DESCRIPTION_BUFFET)
                .withPrice(VALID_EVENT_PRICE_BUFFET)
                .withTimestamp(VALID_EVENT_TIMESTAMP_BUFFET)
                .withCategory(VALID_EVENT_CATEGORY_BUFFET).build();
    }

    static {
        DESC_HOLIDAY = new EditBudgetDescriptorBuilder().withDescription(VALID_BUDGET_DESCRIPTION_HOLIDAY)
                .withAmount(VALID_BUDGET_AMOUNT_HOLIDAY)
                .withStartDate(VALID_BUDGET_START_DATE_HOLIDAY)
                .withPeriod(VALID_BUDGET_PERIOD_HOLIDAY)
                .build();
    }




    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.run(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the MooLah, filtered expense list and selected expense in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        MooLah expectedMooLah = new MooLah(actualModel.getMooLah());
        List<Expense> expectedFilteredList = new ArrayList<>(actualModel.getFilteredExpenseList());

        assertThrows(CommandException.class, expectedMessage, () -> command.run(actualModel));
        assertEquals(expectedMooLah, actualModel.getMooLah());
        assertEquals(expectedFilteredList, actualModel.getFilteredExpenseList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the expense at the given {@code targetIndex} in the
     * {@code model}'s MooLah.
     */
    public static void showExpenseAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredExpenseList().size());

        Expense expense = model.getFilteredExpenseList().get(targetIndex.getZeroBased());
        final String[] splitDescription = expense.getDescription().fullDescription.split("\\s+");
        model.updateFilteredExpenseList(new DescriptionContainsKeywordsPredicate(Arrays.asList(splitDescription[0])));

        assertEquals(1, model.getFilteredExpenseList().size());
    }

    //    /**
    //     * Updates {@code model}'s filtered list to show only the event at the given {@code targetIndex} in the
    //     * {@code model}'s MooLah.
    //     */
    //    public static void showEventAtIndex(Model model, Index targetIndex) {
    //        assertTrue(targetIndex.getZeroBased() < model.getFilteredEventList().size());
    //
    //        Event event = model.getFilteredEventList().get(targetIndex.getZeroBased());
    //        final String[] splitDescription = event.getDescription().fullDescription.split("\\s+");
    //        model.updateFilteredEventList(
    //                new DescriptionContainsKeywordsPredicate(Arrays.asList(splitDescription[0])));
    //
    //        assertEquals(1, model.getFilteredEventList().size());
    //    }

}
