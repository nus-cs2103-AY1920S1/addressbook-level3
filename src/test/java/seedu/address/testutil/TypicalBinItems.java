package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_DAVID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_KASSANDRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_LINCOLN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COVERAGE_CAR_INSURANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_DELETED_CAR_INSURANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_DELETED_DAVID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_DELETED_KASSANDRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_DELETED_LINCOLN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_OF_BIRTH_DAVID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_OF_BIRTH_KASSANDRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_OF_BIRTH_LINCOLN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CAR_INSURANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_DAVID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_KASSANDRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_LINCOLN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_AGE_CAR_INSURANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPIRY_DATE_CAR_INSURANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPIRY_DATE_DAVID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPIRY_DATE_KASSANDRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPIRY_DATE_LINCOLN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_DAVID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_KASSANDRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_LINCOLN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CAR_INSURANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DAVID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_KASSANDRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_LINCOLN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_DAVID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_KASSANDRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_LINCOLN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_DAVID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_KASSANDRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_LINCOLN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_CAR_INSURANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_AGE_CAR_INSURANCE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.util.BinItemBuilder;
import seedu.address.commons.util.PersonBuilder;
import seedu.address.commons.util.PolicyBuilder;
import seedu.address.model.binitem.BinItem;

/**
 * A utility class containing a list of {@code BinItem} objects to be used in tests.
 */
public class TypicalBinItems {

    public static final BinItem BIN_DAVID = new BinItemBuilder().withItem(new PersonBuilder().withName(VALID_NAME_DAVID)
        .withNric(VALID_NRIC_DAVID).withPhone(VALID_PHONE_DAVID).withEmail(VALID_EMAIL_DAVID)
        .withAddress(VALID_ADDRESS_DAVID).withDateOfBirth(VALID_DATE_OF_BIRTH_DAVID).withGender(VALID_GENDER_DAVID)
        .build()).withDateDeleted(VALID_DATE_DELETED_DAVID).withExpiryDate(VALID_EXPIRY_DATE_DAVID).build();

    public static final BinItem BIN_LINCOLN = new BinItemBuilder().withItem(new PersonBuilder()
        .withName(VALID_NAME_LINCOLN).withNric(VALID_NRIC_LINCOLN).withPhone(VALID_PHONE_LINCOLN)
        .withEmail(VALID_EMAIL_LINCOLN).withAddress(VALID_ADDRESS_LINCOLN).withDateOfBirth(VALID_DATE_OF_BIRTH_LINCOLN)
        .withGender(VALID_GENDER_LINCOLN).build()).withDateDeleted(VALID_DATE_DELETED_LINCOLN)
        .withExpiryDate(VALID_EXPIRY_DATE_LINCOLN).build();

    public static final BinItem BIN_KASSANDRA = new BinItemBuilder().withItem(new PersonBuilder()
        .withName(VALID_NAME_KASSANDRA).withNric(VALID_NRIC_KASSANDRA).withPhone(VALID_PHONE_KASSANDRA)
        .withEmail(VALID_EMAIL_KASSANDRA).withAddress(VALID_ADDRESS_KASSANDRA)
        .withDateOfBirth(VALID_DATE_OF_BIRTH_KASSANDRA).withGender(VALID_GENDER_KASSANDRA).build())
        .withDateDeleted(VALID_DATE_DELETED_KASSANDRA).withExpiryDate(VALID_EXPIRY_DATE_KASSANDRA).build();

    public static final BinItem BIN_CAR_INSURANCE = new BinItemBuilder().withItem(new PolicyBuilder()
        .withName(VALID_NAME_CAR_INSURANCE).withDescription(VALID_DESCRIPTION_CAR_INSURANCE)
        .withCoverage(VALID_COVERAGE_CAR_INSURANCE).withStartAge(VALID_START_AGE_CAR_INSURANCE)
        .withEndAge(VALID_END_AGE_CAR_INSURANCE).withPrice(VALID_PRICE_CAR_INSURANCE).withCriteria().withTags().build())
        .withDateDeleted(VALID_DATE_DELETED_CAR_INSURANCE).withExpiryDate(VALID_EXPIRY_DATE_CAR_INSURANCE).build();

    private TypicalBinItems() {}

    public static List<BinItem> getTypicalBinItems() {
        return new ArrayList<>(Arrays.asList(BIN_DAVID, BIN_LINCOLN, BIN_KASSANDRA, BIN_CAR_INSURANCE));
    }

}
