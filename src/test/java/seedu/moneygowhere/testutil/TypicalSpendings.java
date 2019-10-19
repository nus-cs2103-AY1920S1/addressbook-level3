package seedu.moneygowhere.testutil;

import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_COST_AMY;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_COST_BOB;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.moneygowhere.model.SpendingBook;
import seedu.moneygowhere.model.budget.Budget;
import seedu.moneygowhere.model.spending.Spending;

/**
 * A utility class containing a list of {@code Spending} objects to be used in tests.
 */
public class TypicalSpendings {

    public static final Spending APPLE = new SpendingBuilder().withName("Apple")
            .withCost("1.00").withRemark("An apple a day keeps the doctor away")
            .withDate("1/1/2019")
            .withTags("fruit").build();
    public static final Spending BANANA = new SpendingBuilder().withName("Banana")
            .withCost("1.00")
            .withRemark("The store ran out of apples").withDate("2/1/2019")
            .withTags("fruit").build();
    public static final Spending CATFOOD = new SpendingBuilder().withName("Cat food").withDate("3/1/2019")
            .withRemark("For my pet cat").withCost("5.50").build();
    public static final Spending DESSERT = new SpendingBuilder().withName("Dessert").withDate("4/1/2019")
            .withRemark("Supper").withCost("15").withTags("supper").build();
    public static final Spending ENCYCLOPEDIA = new SpendingBuilder().withName("Encyclopedia").withDate("5/1/2019")
            .withRemark("Hobby").withCost("80").build();
    public static final Spending FLIGHTTICKET = new SpendingBuilder().withName("Flight ticket").withDate("6/1/2019")
            .withRemark("To Hong Kong").withCost("400").build();
    public static final Spending GLASSES = new SpendingBuilder().withName("Glasses").withDate("7/1/2019")
            .withRemark("New spectacles").withCost("270").build();

    // Manually added
    public static final Spending HAT = new SpendingBuilder().withName("Hat").withDate("8/1/2019")
            .withRemark("Snapback").withCost("17").build();
    public static final Spending ICECREAM = new SpendingBuilder().withName("Ice Cream").withDate("9/1/2019")
            .withRemark("Weather was hot").withCost("1.50").build();

    // Manually added - Spending's details found in {@code CommandTestUtil}
    public static final Spending AMY = new SpendingBuilder().withName(VALID_NAME_AMY).withDate(VALID_DATE_AMY)
            .withRemark(VALID_REMARK_AMY).withCost(VALID_COST_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Spending BOB = new SpendingBuilder().withName(VALID_NAME_BOB).withDate(VALID_DATE_BOB)
            .withRemark(VALID_REMARK_BOB).withCost(VALID_COST_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalSpendings() {} // prevents instantiation

    /**
     * Returns an {@code SpendingBook} with all the typical spendings.
     */
    public static SpendingBook getTypicalSpendingBook() {
        SpendingBook ab = new SpendingBook();
        for (Spending spending : getTypicalSpendings()) {
            ab.addSpending(spending);
        }
        ab.setBudget(getTypicalBudget());
        return ab;
    }

    public static List<Spending> getTypicalSpendings() {
        return new ArrayList<>(Arrays.asList(APPLE, BANANA, CATFOOD, DESSERT, ENCYCLOPEDIA, FLIGHTTICKET, GLASSES));
    }

    public static Budget getTypicalBudget() {
        return new Budget(10000);
    }
}
