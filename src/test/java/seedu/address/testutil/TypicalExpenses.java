package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.expense.Expense;

/**
 * A utility class containing a list of {@code Expense} objects to be used in tests.
 */
public class TypicalExpenses {

    public static final Expense ALICE = new ExpenseBuilder().withDescription("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPrice("94351253")
            .withTags("friends").build();
    public static final Expense BENSON = new ExpenseBuilder().withDescription("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPrice("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Expense CARL = new ExpenseBuilder().withDescription("Carl Kurz").withPrice("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").build();
    public static final Expense DANIEL = new ExpenseBuilder().withDescription("Daniel Meier").withPrice("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends").build();
    public static final Expense ELLE = new ExpenseBuilder().withDescription("Elle Meyer").withPrice("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Expense FIONA = new ExpenseBuilder().withDescription("Fiona Kunz").withPrice("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Expense GEORGE = new ExpenseBuilder().withDescription("George Best").withPrice("9482442")
            .withEmail("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final Expense HOON = new ExpenseBuilder().withDescription("Hoon Meier").withPrice("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Expense IDA = new ExpenseBuilder().withDescription("Ida Mueller").withPrice("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Expense's details found in {@code CommandTestUtil}
    public static final Expense AMY = new ExpenseBuilder().withDescription(VALID_DESCRIPTION_AMY).withPrice(VALID_PRICE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Expense BOB = new ExpenseBuilder().withDescription(VALID_DESCRIPTION_BOB).withPrice(VALID_PRICE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalExpenses() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical expenses.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Expense expense : getTypicalExpenses()) {
            ab.addExpense(expense);
        }
        return ab;
    }

    public static List<Expense> getTypicalExpenses() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
