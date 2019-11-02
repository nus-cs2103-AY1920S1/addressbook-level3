package seedu.ichifund.testutil;

import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_AMOUNT_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_AMOUNT_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_CATEGORY_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_CATEGORY_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_DAY_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_DAY_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_DESCRIPTION_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_MONTH_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_MONTH_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_TRANSACTION_TYPE_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_TRANSACTION_TYPE_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_YEAR_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_YEAR_BUS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.ichifund.model.FundBook;
import seedu.ichifund.model.budget.Budget;
import seedu.ichifund.model.date.Date;
import seedu.ichifund.model.person.Person;
import seedu.ichifund.model.transaction.Transaction;

/**
 * A utility class containing a list of {@code FundBook} related objects to be used in tests.
 */
public class TypicalFundBook {


    public static final Person PERSON_ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Person PERSON_BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Person PERSON_CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").build();
    public static final Person PERSON_DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends").build();
    public static final Person PERSON_ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Person PERSON_FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Person PERSON_GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final Person PERSON_HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Person PERSON_IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person PERSON_AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Person PERSON_BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final Date DATE_ALLOWANCE = new DateBuilder().withDay(VALID_DAY_ALLOWANCE)
            .withMonth(VALID_MONTH_ALLOWANCE).withYear(VALID_YEAR_ALLOWANCE).build();
    public static final Date DATE_BUS = new DateBuilder().withDay(VALID_DAY_BUS)
            .withMonth(VALID_MONTH_BUS).withYear(VALID_YEAR_BUS).build();

    public static final Transaction TRANSACTION_ALLOWANCE = new TransactionBuilder()
            .withDescription(VALID_DESCRIPTION_ALLOWANCE).withAmount(VALID_AMOUNT_ALLOWANCE)
            .withDate(DATE_ALLOWANCE).withCategory(VALID_CATEGORY_ALLOWANCE)
            .withTransactionType(VALID_TRANSACTION_TYPE_ALLOWANCE).build();
    public static final Transaction TRANSACTION_BUS = new TransactionBuilder()
            .withDescription(VALID_DESCRIPTION_BUS).withAmount(VALID_AMOUNT_BUS)
            .withDate(DATE_BUS).withCategory(VALID_CATEGORY_BUS)
            .withTransactionType(VALID_TRANSACTION_TYPE_BUS).build();

    public static final Budget BUDGET_OVERALL = new BudgetBuilder().withDescription("Overall budget")
            .withAmount("1337").withMonthAndYear("12", "2012").build();
    public static final Budget BUDGET_FOOD = new BudgetBuilder().withDescription("Budget for food")
            .withAmount("420.69").withMonthAndYear("12", "2012")
            .withCategory("food").build();
    public static final Budget BUDGET_ANIME = new BudgetBuilder().withDescription("Budget for anime")
            .withAmount("300.00").withMonthAndYear("12", "2012")
            .withCategory("entertainment").build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalFundBook() {} // prevents instantiation

    /**
     * Returns an {@code FundBok} with all the typical persons.
     */
    public static FundBook getTypicalFundBook() {
        FundBook fb = new FundBook();
        for (Person person : getTypicalPersons()) {
            fb.addPerson(person);
        }
        for (Budget budget : getTypicalBudgets()) {
            fb.addBudget(budget);
        }
        return fb;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(PERSON_ALICE, PERSON_BENSON, PERSON_CARL, PERSON_DANIEL,
                PERSON_ELLE, PERSON_FIONA, PERSON_GEORGE));
    }

    public static List<Budget> getTypicalBudgets() {
        return new ArrayList<>(Arrays.asList(BUDGET_OVERALL, BUDGET_FOOD, BUDGET_ANIME));
    }
}
