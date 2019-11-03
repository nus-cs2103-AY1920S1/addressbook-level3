package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.FinSec;
import seedu.address.model.claim.Claim;
import seedu.address.model.contact.Contact;
import seedu.address.model.income.Income;

import static seedu.address.logic.commands.CommandTestUtil.*;

/**
 * A utility class containing a list of {@code FinSec} objects to be used in tests.
 */
public class TypicalObjects {

    public static final Contact ALICE = new ContactBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com")
            .withPhone("94351253").withTags("friends").build();
    public static final Contact BENSON = new ContactBuilder().withName("Benson Meier")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").withClaims("1", "2").build();
    public static final Contact CARL = new ContactBuilder().withName("Carl Kurz")
            .withPhone("95352563").withEmail("heinz@example.com").build();
    public static final Contact DANIEL = new ContactBuilder().withName("Daniel Meier")
            .withPhone("87652533").withEmail("cornelia@example.com").withTags("friends")
            .build();
    public static final Contact ELLE = new ContactBuilder().withName("Elle Meyer")
            .withPhone("94822241").withEmail("werner@example.com").build();
    public static final Contact FIONA = new ContactBuilder().withName("Fiona Kunz")
            .withPhone("94824271").withEmail("lydia@example.com").build();
    public static final Contact GEORGE = new ContactBuilder().withName("George Best")
            .withPhone("94824421").withEmail("anna@example.com").build();

    // Manually added
    public static final Contact HOON = new ContactBuilder().withName("Hoon Meier")
            .withPhone("84824241").withEmail("stefan@example.com").build();
    public static final Contact IDA = new ContactBuilder().withName("Ida Mueller")
            .withPhone("84821311")
            .withEmail("hans@example.com").build();

    // Manually added - FinSec's details found in {@code CommandTestUtil}
    public static final Contact AMY = new ContactBuilder().withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Contact BOB = new ContactBuilder().withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final Income INCOME_1 = new IncomeBuilder().withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY).withDescription(VALID_DESCRIPTION_FUNDRAISING)
            .withAmount(VALID_AMOUNT_FUNDRAISING).withDate(VALID_DATE_FUNDRAISING).build();

    public static final Income INCOME_2 = new IncomeBuilder().withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB).withDescription(VALID_DESCRIPTION_SHIRTSALES)
            .withAmount(VALID_AMOUNT_SHIRTSALES).withDate(VALID_DATE_SHIRTSALES).build();

    public static final Claim CLAIM_1 = new ClaimBuilder().withName(VALID_NAME_AMY)
            .withDescription(VALID_DESCRIPTION_FUNDRAISING).withAmount(VALID_AMOUNT_CORPORATECLAIMS)
            .withDate(VALID_DATE_DURINGMONTH).build();

    public static final Claim CLAIM_2 = new ClaimBuilder().withName(VALID_NAME_BOB)
            .withDescription(VALID_DESCRIPTION_SHIRTSALES).withAmount(VALID_AMOUNT_SCHOOLCLAIMS)
            .withDate(VALID_DATE_FIRSTDAYOFMONTH).build();

    public static final Claim CLAIM_3 = new ClaimBuilder().withName(VALID_NAME_BOB)
            .withDescription(VALID_DESCRIPTION_LOGISTICS).withAmount(VALID_AMOUNT_PROJECTCLAIMS)
            .withDate(VALID_DATE_BEFOREMONTH).buildRejected();

    public static final Claim CLAIM_4 = new ClaimBuilder().withName(VALID_NAME_AMY)
            .withDescription(VALID_DESCRIPTION_TEST1).withAmount(VALID_AMOUNT_CORPORATECLAIMS)
            .withDate(VALID_DATE_DURINGMONTH).buildApproved();

    public static final Claim CLAIM_5 = new ClaimBuilder().withName(VALID_NAME_BOB)
            .withDescription(VALID_DESCRIPTION_TEST2).withAmount(VALID_AMOUNT_SCHOOLCLAIMS)
            .withDate(VALID_DATE_FIRSTDAYOFMONTH).buildApproved();

    public static final Claim CLAIM_6 = new ClaimBuilder().withName(VALID_NAME_BOB)
            .withDescription(VALID_DESCRIPTION_TEST3).withAmount(VALID_AMOUNT_PROJECTCLAIMS)
            .withDate(VALID_DATE_BEFOREMONTH).buildApproved();

    public static final Claim CLAIM_7 = new ClaimBuilder().withName(VALID_NAME_AMY)
            .withDescription(VALID_DESCRIPTION_TEST4).withAmount(VALID_AMOUNT_LOGISTICCLAIMS)
            .withDate(VALID_DATE_AFTERMONTH).buildApproved();

    public static final Claim CLAIM_8 = new ClaimBuilder().withName(VALID_NAME_BOB)
            .withDescription(VALID_DESCRIPTION_TEST5).withAmount(VALID_AMOUNT_SCHOOLCLAIMS)
            .withDate(VALID_DATE_DURINGMONTH2).buildApproved();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalObjects() {} // prevents instantiation

    /**
     * Returns an {@code FinSec} with all the typical persons.
     */
    public static FinSec getTypicalFinSec() {
        FinSec fs = new FinSec();

        //adding contacts into a test finsec
        for (Contact contact : getTypicalContacts()) {
            fs.addContact(contact);
        }

        //adding the incomes into a test finsec
        for (Income income : getTypicalIncomes()) {
            fs.addIncome(income);
        }

        return fs;
    }

    /**
     * Returns an {@code FinSec} with all the typical persons, incomes and non-approved claims.
     */
    public static FinSec getTypicalFinSecWithNoApprovedClaims() {
        FinSec fs = new FinSec();

        //adding contacts into a test finsec
        for (Contact contact : getTypicalContacts()) {
            fs.addContact(contact);
        }

        //adding the incomes into a test finsec
        for (Income income : getTypicalIncomes()) {
            fs.addIncome(income);
        }

        //adding the claims into a test finsec
        for (Claim claim : getTypicalNonApprovedClaims()) {
            fs.addClaim(claim);
        }

        return fs;
    }

    /**
     * Returns an {@code FinSec} with all the typical persons, incomes and approved claims.
     */
    public static FinSec getTypicalFinSecWithApprovedClaims() {
        FinSec fs = new FinSec();

        //adding contacts into a test finsec
        for (Contact contact : getTypicalContacts()) {
            fs.addContact(contact);
        }

        //adding the incomes into a test finsec
        for (Income income : getTypicalIncomes()) {
            fs.addIncome(income);
        }

        //adding the claims into a test finsec
        for (Claim claim : getTypicalApprovedClaims()) {
            fs.addClaim(claim);
        }

        return fs;
    }


    public static List<Contact> getTypicalContacts() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, AMY, BOB));
    }

    public static List<Income> getTypicalIncomes() {
        return new ArrayList<>(Arrays.asList(INCOME_1, INCOME_2));
    }

    public static List<Claim> getTypicalNonApprovedClaims() {
        return new ArrayList<>(Arrays.asList(CLAIM_1, CLAIM_2, CLAIM_3));
    }

    public static List<Claim> getTypicalApprovedClaims() {
        return new ArrayList<>(Arrays.asList(CLAIM_4, CLAIM_5, CLAIM_6, CLAIM_7, CLAIM_8));
    }
}
