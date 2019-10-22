package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_COVERAGE_CAR_INSURANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_DELETED_CAR_INSURANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_DELETED_DAVID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_DELETED_KASSANDRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_DELETED_LINCOLN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CAR_INSURANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_AGE_CAR_INSURANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPIRY_DATE_CAR_INSURANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPIRY_DATE_DAVID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPIRY_DATE_KASSANDRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPIRY_DATE_LINCOLN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CAR_INSURANCE;
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

    public static final BinItem BIN_DAVID = new BinItemBuilder().withItem(new PersonBuilder().withName("David Georgia")
        .withNric("S0000010T").withPhone("94820001").withEmail("davegeorge@example.com")
        .withAddress("10th Fly street").withDateOfBirth("5.3.1980").withGender("Male").build())
        .withDateDeleted(VALID_DATE_DELETED_DAVID).withExpiryDate(VALID_EXPIRY_DATE_DAVID).build();

    public static final BinItem BIN_LINCOLN = new BinItemBuilder().withItem(new PersonBuilder()
        .withName("Lincoln Feathers").withNric("S0000011T").withPhone("94820002").withEmail("lincfeats@example.com")
        .withAddress("11th Boxer street").withDateOfBirth("1.3.1990").withGender("Male").build())
        .withDateDeleted(VALID_DATE_DELETED_LINCOLN).withExpiryDate(VALID_EXPIRY_DATE_LINCOLN).build();

    public static final BinItem BIN_KASSANDRA = new BinItemBuilder().withItem(new PersonBuilder()
        .withName("Kassandra Pearl").withNric("S0000012T").withPhone("94820002").withEmail("kpearl@example.com")
        .withAddress("13th Jewel street").withDateOfBirth("9.10.2000").withGender("Female").build())
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
