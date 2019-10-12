package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.policy.Policy;

import static seedu.address.logic.commands.CommandTestUtil.*;

/**
 * A utility class containing a list of {@code Policy} objects to be used in tests.
 */
public class TypicalPolicy {

    public static final Policy HEALTH_INSURANCE = new PolicyBuilder().withName(VALID_NAME_HEALTH_INSURANCE)
            .withDescription(VALID_DESCRIPTION_HEALTH_INSURANCE).withCoverage(VALID_COVERAGE_HEALTH_INSURANCE)
            .withStartAge(VALID_START_AGE_HEALTH_INSURANCE).withEndAge(VALID_END_AGE_HEALTH_INSURANCE)
            .withPrice(VALID_PRICE_HEALTH_INSURANCE).withCriteria(VALID_CRITERIA_HEALTH_INSURANCE)
            .withTags(VALID_TAG_HEALTH_INSURANCE).build();

    public static final Policy LIFE_INSURANCE = new PolicyBuilder().withName(VALID_NAME_LIFE_INSURANCE)
            .withDescription(VALID_DESCRIPTION_LIFE_INSURANCE).withCoverage(VALID_COVERAGE_LIFE_INSURANCE)
            .withStartAge(VALID_START_AGE_LIFE_INSURANCE).withEndAge(VALID_END_AGE_LIFE_INSURANCE)
            .withPrice(VALID_PRICE_LIFE_INSURANCE).withCriteria(VALID_CRITERIA_LIFE_INSURANCE)
            .withTags(VALID_TAG_LIFE_INSURANCE).build();

    public static final Policy FIRE_INSURANCE = new PolicyBuilder().withName(VALID_NAME_FIRE_INSURANCE)
            .withDescription(VALID_DESCRIPTION_FIRE_INSURANCE).withCoverage(VALID_COVERAGE_FIRE_INSURANCE)
            .withStartAge(VALID_START_AGE_FIRE_INSURANCE).withEndAge(VALID_END_AGE_FIRE_INSURANCE)
            .withPrice(VALID_PRICE_FIRE_INSURANCE).withCriteria(VALID_CRITERIA_FIRE_INSURANCE)
            .withTags(VALID_TAG_FIRE_INSURANCE).build();

    private TypicalPolicy() {} // prevents instantiation

    public static List<Policy> getTypicalPolicy() {
        return new ArrayList<>(Arrays.asList(HEALTH_INSURANCE, LIFE_INSURANCE, FIRE_INSURANCE));
    }
}

