package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
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
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String NRIC} into a {@code Nric}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code nric} is invalid.
     */
    public static Nric parseNric(String nric) throws ParseException {
        requireNonNull(nric);
        String trimmedNric = nric.trim().toUpperCase();
        if (!Nric.isValidNric(trimmedNric)) {
            throw new ParseException(Nric.MESSAGE_CONSTRAINTS);
        }
        return new Nric(nric);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String dateOfBirth} into an {@code DateOfBirth}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dateOfBirth} is invalid.
     */
    public static DateOfBirth parseDateOfBirth(String dateOfBirth) throws ParseException {
        requireNonNull(dateOfBirth);
        String trimmedDateOfBirth = dateOfBirth.trim();
        if (!DateOfBirth.isValidDateOfBirth(dateOfBirth)) {
            throw new ParseException(DateOfBirth.MESSAGE_CONSTRAINTS);
        }
        return new DateOfBirth(dateOfBirth);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses {@code Collection<String> criteria} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseCriteria(Collection<String> criteria) throws ParseException {
        requireNonNull(criteria);
        final Set<Tag> criteriaSet = new HashSet<>();
        for (String criteriaName : criteria) {
            criteriaSet.add(parseTag(criteriaName));
        }
        return criteriaSet;
    }

    /**
     * Parses a {@code String name} into a {@code PolicyName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static PolicyName parsePolicyName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!PolicyName.isValidName(trimmedName)) {
            throw new ParseException(PolicyName.MESSAGE_CONSTRAINTS);
        }
        return new PolicyName(trimmedName);
    }

    /**
     * Parses a {@code String description} into a {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static Description parseDescription (String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Description.isValidDescription(trimmedDescription)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(description);
    }

    /**
     * Parses a {@code String coverage} into a {@code Coverage}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static Coverage parseCoverage (String coverage) throws ParseException {
        requireNonNull(coverage);
        String trimmedCoverage = coverage.trim();
        System.out.println(trimmedCoverage);
        if (!Coverage.isValidCoverage(trimmedCoverage)) {
            throw new ParseException(Coverage.MESSAGE_CONSTRAINTS);
        }
        return new Coverage(trimmedCoverage);
    }

    /**
     * Parses a {@code String price} into a {@code Price}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static Price parsePrice (String price) throws ParseException {
        requireNonNull(price);
        String trimmedPrice = price.trim();
        if (!Price.isValidPrice(trimmedPrice)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Price(price);
    }

    /**
     * Parses a {@code String startAge} into a {@code StartAge}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code startAge} is invalid.
     */
    public static StartAge parseStartAge (String startAge) throws ParseException {
        requireNonNull(startAge);
        String trimmedAge = startAge.trim();
        if (!StartAge.isValidAge(trimmedAge)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new StartAge(trimmedAge);
    }

    /**
     * Parses a {@code String endAge} into a {@code EndAge}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code startAge} is invalid.
     */
    public static EndAge parseEndAge (String endAge) throws ParseException {
        requireNonNull(endAge);
        String trimmedAge = endAge.trim();
        if (!EndAge.isValidAge(trimmedAge)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new EndAge(trimmedAge);
    }

    /**
     * Parses a {@code String policy} into a {@code Policy}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code policy} is invalid.
     */
    public static Policy parsePolicy(String policy) throws ParseException {
        requireNonNull(policy);
        String trimmedPolicy = policy.trim();
        if (!Policy.isAvailable(trimmedPolicy)) {
            throw new ParseException(Policy.MESSAGE_CONSTRAINTS);
        }
        return Policy.getPolicy(policy);
    }

    /**
     * Parses {@code Collection<String> policiesNames} into a {@code Set<Policy>}.
     */
    public static Set<Policy> parsePolicies(Collection<String> policyNames) throws ParseException {
        requireNonNull(policyNames);
        final Set<Policy> policySet = new HashSet<>();
        for (String name : policyNames) {
            policySet.add(parsePolicy(name));
        }
        return policySet;
    }
}
