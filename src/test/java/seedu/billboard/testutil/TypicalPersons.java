package seedu.billboard.testutil;

import static seedu.billboard.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.billboard.model.Billboard;
import seedu.billboard.model.person.Expense;

/**
 * A utility class containing a list of {@code Expense} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Expense BILLS = new ExpenseBuilder().withName("monthly bills")
            .withDescription("pay phone company")
            .withAmount("350.25")
            .withTags("bills").build();
    public static final Expense FOOD = new ExpenseBuilder().withName("monday breakfast")
            .withDescription("toast with frens")
            .withAmount("4.20")
            .withTags("owes money", "friends").build();
    public static final Expense GROCERIES = new ExpenseBuilder().withName("groceries")
            .withDescription("bought from fairprice")
            .withAmount("23.50")
            .build();
    public static final Expense MOVIE = new ExpenseBuilder().withName("movie tickets")
            .withDescription("tickets to joker")
            .withAmount("10.00").withTags("leisure").build();


    // Manually added
    public static final Expense HOON = new ExpenseBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Expense IDA = new ExpenseBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Expense's details found in {@code CommandTestUtil}
    public static final Expense AMY = new ExpenseBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Expense BOB = new ExpenseBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code Billboard} with all the typical persons.
     */
    public static Billboard getTypicalAddressBook() {
        Billboard ab = new Billboard();
        for (Expense expense : getTypicalPersons()) {
            ab.addPerson(expense);
        }
        return ab;
    }

    public static List<Expense> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(BILLS, FOOD, GROCERIES, MOVIE));
    }
}
