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
import seedu.moneygowhere.model.reminder.Reminder;
import seedu.moneygowhere.model.spending.Spending;

/**
 * A utility class containing a list of {@code Spending} objects to be used in tests.
 */
public class TypicalSpendings {

    public static final Spending ALICE = new SpendingBuilder().withName("Alice Pauline")
            .withCost("123").withRemark("Likes to watch movies")
            .withDate("1/1/2019")
            .withTags("friends").build();
    public static final Spending BENSON = new SpendingBuilder().withName("Benson Meier")
            .withCost("311")
            .withRemark("Likes to play basketball").withDate("2/1/2019")
            .withTags("owesMoney", "friends").build();
    public static final Spending CARL = new SpendingBuilder().withName("Carl Kurz").withDate("3/1/2019")
            .withRemark("Likes to eat").withCost("1.50").build();
    public static final Spending DANIEL = new SpendingBuilder().withName("Daniel Meier").withDate("4/1/2019")
            .withRemark("Likes to travel").withCost("10.10").withTags("friends").build();
    public static final Spending ELLE = new SpendingBuilder().withName("Elle Meyer").withDate("5/1/2019")
            .withRemark("Likes to sleep").withCost("134.70").build();
    public static final Spending FIONA = new SpendingBuilder().withName("Fiona Kunz").withDate("6/1/2019")
            .withRemark("Likes to cook").withCost("52").build();
    public static final Spending GEORGE = new SpendingBuilder().withName("George Best").withDate("7/1/2019")
            .withRemark("Likes to play golf").withCost("27.10").build();

    // Manually added
    public static final Spending HOON = new SpendingBuilder().withName("Hoon Meier").withDate("8/1/2019")
            .withRemark("Likes to go camping").withCost("1.70").build();
    public static final Spending IDA = new SpendingBuilder().withName("Ida Mueller").withDate("9/1/2019")
            .withRemark("Likes to climb mountains").withCost("2000").build();

    // Manually added - Spending's details found in {@code CommandTestUtil}
    public static final Spending AMY = new SpendingBuilder().withName(VALID_NAME_AMY).withDate(VALID_DATE_AMY)
            .withRemark(VALID_REMARK_AMY).withCost(VALID_COST_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Spending BOB = new SpendingBuilder().withName(VALID_NAME_BOB).withDate(VALID_DATE_BOB)
            .withRemark(VALID_REMARK_BOB).withCost(VALID_COST_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();
    public static final Reminder BILL_REMINDER = new ReminderBuilder().build();

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
        ab.addReminder(BILL_REMINDER);
        ab.setBudget(getTypicalBudget());
        return ab;
    }

    public static List<Spending> getTypicalSpendings() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static Budget getTypicalBudget() {
        return new Budget(10000);
    }
}
