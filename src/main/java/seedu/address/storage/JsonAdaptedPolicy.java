package seedu.address.storage;

import java.security.IdentityScope;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.policy.Coverage;
import seedu.address.model.policy.Description;
import seedu.address.model.policy.EndAge;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyName;
import seedu.address.model.policy.Price;
import seedu.address.model.policy.StartAge;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link seedu.address.model.person.Person}.
 */
class JsonAdaptedPolicy {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String description;
    private final String coverage;
    private final String price;
    private final String startAge;
    private final String endAge;
    private final List<JsonAdaptedTag> criteria = new ArrayList<>();
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPolicy} with the given policy details.
     */
    @JsonCreator
    public JsonAdaptedPolicy(@JsonProperty("name") String name, @JsonProperty("description") String description,
                             @JsonProperty("coverage") String coverage, @JsonProperty("price") String price,
                             @JsonProperty("start age") String startAge, @JsonProperty("end age") String endAge,
                             @JsonProperty("criteria") List<JsonAdaptedTag> criteria,
                             @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.description = description;
        this.coverage = coverage;
        this.price = price;
        this.startAge = startAge;
        this.endAge = endAge;
        if (criteria != null) {
            this.criteria.addAll(criteria);
        }
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Policy} into this class for Jackson use.
     */
    public JsonAdaptedPolicy(Policy source) {
        name = source.getName().policyName;
        description = source.getDescription().description;
        coverage = source.getCoverage().toString();
        price = source.getPrice().price;
        startAge = source.getStartAge().age;
        endAge = source.getEndAge().age;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted policy object into the model's {@code Policy} object.
     *
     * @throws seedu.address.commons.exceptions.IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Policy toModelType() throws IllegalValueException {
        final List<Tag> policyTags = new ArrayList<>();
        final List<Tag> criteriaTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            policyTags.add(tag.toModelType());
        }

        for (JsonAdaptedTag criteria : criteria) {
            criteriaTags.add(criteria.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, PolicyName.class.getSimpleName()));
        }
        if (!PolicyName.isValidName(name)) {
            throw new IllegalValueException(PolicyName.MESSAGE_CONSTRAINTS);
        }
        final PolicyName modelName = new PolicyName(name);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (coverage == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Coverage.class.getSimpleName()));
        }
        if (!Coverage.isValidCoverage(coverage)) {
            throw new IllegalValueException(Coverage.MESSAGE_CONSTRAINTS);
        }
        final Coverage modelCoverage = new Coverage(coverage);

        if (price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName()));
        }
        if (!Price.isValidPrice(price)) {
            throw new IllegalValueException(Price.MESSAGE_CONSTRAINTS);
        }
        final Price modelPrice = new Price(price);

        if (startAge == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, StartAge.class.getSimpleName()));
        }
        if (!StartAge.isValidAge(startAge)) {
            throw new IllegalValueException(StartAge.MESSAGE_CONSTRAINTS);
        }
        final StartAge modelStartAge = new StartAge(startAge);

        if (endAge == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, EndAge.class.getSimpleName()));
        }
        if (!EndAge.isValidAge(endAge)) {
            throw new IllegalValueException(EndAge.MESSAGE_CONSTRAINTS);
        }
        final EndAge modelEndAge = new EndAge(endAge);

        final Set<Tag> modelCriteria = new HashSet<>(criteriaTags);
        final Set<Tag> modelTags = new HashSet<>(policyTags);
        return new Policy(modelName, modelDescription, modelCoverage, modelPrice, modelStartAge, modelEndAge,
                modelCriteria, modelTags);
    }

}
