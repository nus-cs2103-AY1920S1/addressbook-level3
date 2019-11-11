package seedu.address.commons.util;

import static seedu.address.model.util.SampleDataUtil.getCriteriaSet;
import static seedu.address.model.util.SampleDataUtil.getTagSet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.policy.Coverage;
import seedu.address.model.policy.Description;
import seedu.address.model.policy.EndAge;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyName;
import seedu.address.model.policy.Price;
import seedu.address.model.policy.StartAge;
import seedu.address.model.tag.Tag;

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
    public static final Set<Tag> DEFAULT_CRITERIA = getCriteriaSet("diabetic");
    public static final Set<Tag> DEFAULT_TAG = getTagSet("diabetic");

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
        criteria = DEFAULT_CRITERIA;
        tags = DEFAULT_TAG;
    }

    /**
     * Initializes the Policy with the data of {@code policyToCopy}.
     */
    public PolicyBuilder(Policy policyToCopy) {
        name = policyToCopy.getName();
        description = policyToCopy.getDescription();
        coverage = policyToCopy.getCoverage();
        price = policyToCopy.getPrice();
        startAge = policyToCopy.getStartAge();
        endAge = policyToCopy.getEndAge();
        criteria = new HashSet<>(policyToCopy.getCriteria());
        tags = new HashSet<>(policyToCopy.getTags());
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
        this.criteria = new HashSet<>(criteria);
        this.tags = new HashSet<>(tags);
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
    public PolicyBuilder withTags(String... tags) {
        this.tags = getTagSet(tags);
        return this;
    }

    /**
     * Adds the {@code tags} to the {@code Policy} that we are building.
     */
    public PolicyBuilder addTags(List<Tag> tags) {
        this.tags.addAll(tags);
        return this;
    }

    /**
     * Removes the {@code tags} from the {@code Policy} that we are building.
     */
    public PolicyBuilder removeTags(List<Tag> tags) {
        this.tags.removeAll(tags);
        return this;
    }

    /**
     * Parses the {@code criteria} into a {@code Set<Tag>} and set it to the {@code Policy} that we are building.
     */
    public PolicyBuilder withCriteria(String... criteria) {
        this.criteria = getCriteriaSet(criteria);
        return this;
    }

    /**
     * Adds the {@code criteria} to the {@code Policy} that we are building.
     */
    public PolicyBuilder addCriteria(List<Tag> criteria) {
        this.criteria.addAll(criteria);
        return this;
    }

    /**
     * Removes the {@code criteria} from the {@code Policy} that we are building.
     */
    public PolicyBuilder removeCriteria(List<Tag> criteria) {
        this.criteria.removeAll(criteria);
        return this;
    }

    public Policy build() {
        return new Policy(name, description, coverage, price, startAge, endAge, criteria, tags);
    }

}
