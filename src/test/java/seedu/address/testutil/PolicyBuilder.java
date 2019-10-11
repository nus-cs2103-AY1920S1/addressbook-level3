package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.policy.*;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

// TODO: test for PolicyBuilder
/**
 * A utility class to help with building Policy objects.
 */
public class PolicyBuilder {

    public static final String DEFAULT_NAME = "Senior Care";
    public static final String DEFAULT_DESCRIPTION = "Life insurance for elderly";
    public static final String DEFAULT_COVERAGE = "days/10 months/11 years/12";
    public static final String DEFAULT_PRICE = "$5000";
    public static final String DEFAULT_START_AGE = "65";
    public static final String DEFAULT_END_AGE = "95";
    // TODO: add criteria and tags

    private PolicyName name;
    private Description description;
    private Coverage coverage;
    private Price price;
    private StartAge startAge;
    private EndAge endAge;
    private Set<Tag> criteria;
    private Set<Tag> tags;

    public PolicyBuilder() {
        name = new PolicyName(DEFAULT_NAME);
        description = new Description(DEFAULT_DESCRIPTION);
        coverage = new Coverage(DEFAULT_COVERAGE);
        price = new Price(DEFAULT_PRICE);
        startAge = new StartAge(DEFAULT_START_AGE);
        endAge = new EndAge(DEFAULT_END_AGE);
        // TODO: update
        criteria = new HashSet<>();
        tags = new HashSet<>();
    }

    /**
     * Initializes the Policy with the data of {@code personToCopy}.
     */
    public PolicyBuilder(Policy policy) {
        name = policy.getName();
        description = policy.getDescription();
        coverage = policy.getCoverage();
        price = policy.getPrice();
        startAge = policy.getStartAge();
        endAge = policy.getEndAge();
        criteria = policy.getCriteria();
        tags = policy.getTags();
    }

    /**
     * Initializes the PolicyBuilder with the data of {@code policies and tags}.
     */
    public PolicyBuilder(Set<Tag> criteria, Set<Tag> tags) {
        name = new PolicyName(DEFAULT_NAME);
        description = new Description(DEFAULT_DESCRIPTION);
        coverage = new Coverage(DEFAULT_COVERAGE);
        price = new Price(DEFAULT_PRICE);
        startAge = new StartAge(DEFAULT_START_AGE);
        endAge = new EndAge(DEFAULT_END_AGE);
        // TODO: update
        criteria = new HashSet<>(criteria);
        tags = new HashSet<>(tags);
    }

    /**
     * Sets the {@code Name} of the {@code Policy} that we are building.
     */
    public PolicyBuilder withName(String name) {
        this.name = new PolicyName(name);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Policy} that we are building.
     */
    public PolicyBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Coverage} of the {@code Policy} that we are building.
     */
    public PolicyBuilder withCoverage(String coverage) {
        this.coverage = new Coverage(coverage);
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Policy} that we are building.
     */
    public PolicyBuilder withPrice(String price) {
        this.price = new Price(price);
        return this;
    }

    /**
     * Sets the {@code StartAge} of the {@code Policy} that we are building.
     */
    public PolicyBuilder withStartAge(String startAge) {
        this.startAge = new StartAge(startAge);
        return this;
    }

    /**
     * Sets the {@code EndAge} of the {@code Policy} that we are building.
     */
    public PolicyBuilder withEndAge(String endAge) {
        this.endAge = new EndAge(endAge);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Policy} that we are building.
     */
    public PolicyBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Policy} that we are building.
     */
    public PolicyBuilder withCriteria(String ... criteria) {
        this.criteria = SampleDataUtil.getTagSet(criteria);
        return this;
    }

    public Policy build() {
        return new Policy(name, description, coverage, price, startAge, endAge, criteria, tags);
    }

}
