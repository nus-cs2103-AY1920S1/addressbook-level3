package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BORROWER_ID;
import static seedu.address.testutil.TypicalLoans.LOAN_7;
import static seedu.address.testutil.TypicalLoans.LOAN_8;
import static seedu.address.testutil.TypicalLoans.LOAN_9;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.BorrowerRecords;
import seedu.address.model.borrower.Borrower;
import seedu.address.model.borrower.BorrowerId;

/**
 * A utility class containing a list of {@code Borrower} objects to be used in tests.
 */
public class TypicalBorrowers {

    public static final BorrowerId ID_FIRST_BORROWER = new BorrowerId("K0001");
    public static final BorrowerId ID_SECOND_BORROWER = new BorrowerId("K0002");

    public static final String ID_DESC_FIRST_BORROWER = " " + PREFIX_BORROWER_ID + "K0001";

    public static final Borrower ALICE = new BorrowerBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withBorrowerId("K0001")
            .build();
    public static final Borrower BENSON = new BorrowerBuilder().withName("Benson Meier")
            .withEmail("johnd@example.com").withPhone("98765432").withBorrowerId("K0002")
            .build();
    public static final Borrower CARL = new BorrowerBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withBorrowerId("K0003").build();
    public static final Borrower DANIEL = new BorrowerBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withBorrowerId("K0004").build();
    public static final Borrower ELLE = new BorrowerBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withBorrowerId("K0005").build();
    public static final Borrower FIONA = new BorrowerBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withBorrowerId("K0006").build();
    public static final Borrower GEORGE = new BorrowerBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withBorrowerId("K0007").build();

    // Manually added
    public static final Borrower HOON = new BorrowerBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withBorrowerId("K0008").build();
    public static final Borrower IDA = new BorrowerBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withBorrowerId("K0009").withCurrentLoan(LOAN_7).build();
    public static final Borrower JANNA = new BorrowerBuilder().withName("Janna Windforce").withPhone("3838438")
            .withEmail("janna@example.com").withBorrowerId("K0010").withReturnedLoan(LOAN_8)
            .withReturnedLoan(LOAN_9).build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Borrower AMY = new BorrowerBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).build();
    public static final Borrower BOB = new BorrowerBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withBorrowerId(VALID_ID_BOB).build();

    private TypicalBorrowers() {} // prevents instantiation

    /**
     * Returns an {@code BorrowerRecords} with all the typical persons.
     */
    public static BorrowerRecords getTypicalBorrowerRecords() {
        BorrowerRecords br = new BorrowerRecords();
        for (Borrower borrower : getTypicalBorrowers()) {
            br.addBorrower(borrower);
        }
        return br;
    }

    public static List<Borrower> getTypicalBorrowers() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, BOB));
    }

}
