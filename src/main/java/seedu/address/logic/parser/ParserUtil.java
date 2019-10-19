package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.calendar.DateTime;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.record.Concentration;
import seedu.address.model.record.Height;
import seedu.address.model.record.RecordType;
import seedu.address.model.record.Weight;
import seedu.address.model.tag.Tag;
import seedu.sgm.model.food.Calorie;
import seedu.sgm.model.food.Fat;
import seedu.sgm.model.food.FoodName;
import seedu.sgm.model.food.Gi;
import seedu.sgm.model.food.NutritionValue;
import seedu.sgm.model.food.Sugar;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses a {@code String recordType} into a {@code RecordType}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code recordType} does not match any of the enums.
     */
    public static RecordType parseRecordType(String recordType) throws ParseException {
        requireNonNull(recordType);
        String trimmedRType = recordType.split(" ")[0].trim();
        try {
            return RecordType.valueOf(trimmedRType);
        } catch (IllegalArgumentException e) {
            throw new ParseException("System does not accommodate such a record type.");
        }
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
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
     * Parses a {@code String name} into a {@code Name}. Leading and trailing whitespaces will be trimmed.
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
     * Parses a {@code String name} into a {@code FoodName}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static FoodName parseFoodName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!FoodName.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new FoodName(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}. Leading and trailing whitespaces will be trimmed.
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
     * Parses a {@code String address} into an {@code Address}. Leading and trailing whitespaces will be trimmed.
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
     * Parses a {@code String email} into an {@code Email}. Leading and trailing whitespaces will be trimmed.
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
     * Parses a {@code String tag} into a {@code Tag}. Leading and trailing whitespaces will be trimmed.
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
     * Checks whether {@code String value} is valid.
     *
     * @throws ParseException if the given {@code nutritionValue} is invalid
     */
    private static String verifyNutritionValue(String nutritionValue) throws ParseException {
        requireNonNull(nutritionValue);
        String trimmedValue = nutritionValue.trim();
        if (!NutritionValue.isValidValue(nutritionValue)) {
            throw new ParseException(NutritionValue.MESSAGE_CONSTRAINTS);
        }
        return trimmedValue;
    }

    /**
     * Parses a {@code String value} into a {@code Calorie}.
     */
    public static Calorie parseCalorieValue(String value) throws ParseException {
        return new Calorie(verifyNutritionValue(value));
    }

    /**
     * Parses a {@code String value} into a {@code Gi}.
     */
    public static Gi parseGiValue(String value) throws ParseException {
        return new Gi(verifyNutritionValue(value));
    }

    /**
     * Parses a {@code String value} into a {@code Sugar}.
     */
    public static Sugar parseSugarValue(String value) throws ParseException {
        return new Sugar(verifyNutritionValue(value));
    }

    /**
     * Parses a {@code String value} into a {@code Fat}.
     */
    public static Fat parseFatValue(String value) throws ParseException {
        return new Fat(verifyNutritionValue(value));
    }

    /**
     * Parses a {@code String dateTime} into an {@code DateTime}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dateTime} is invalid.
     */
    public static DateTime parseDateTime(String dateTime) throws ParseException {
        requireNonNull(dateTime);
        String trimmedDateTime = dateTime.trim();
        if (!DateTime.isValidDateTime(trimmedDateTime)) {
            throw new ParseException(DateTime.MESSAGE_CONSTRAINTS);
        }
        LocalDate ld = LocalDate.parse(dateTime, DateTimeFormatter.ofPattern(DateTime.VALIDATION_REGEX_STRING));
        LocalTime lt = LocalTime.parse(dateTime, DateTimeFormatter.ofPattern(DateTime.VALIDATION_REGEX_STRING));
        return new DateTime(ld, lt);
    }

    /**
     * Parses a {@code String concentration} into an {@code Concentration}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code concentration} is invalid.
     */
    public static Concentration parseConcentration(String concentration) throws ParseException {
        String trimmedConcentration = concentration.trim();
        if (!Concentration.isValidConcentration(trimmedConcentration)) {
            throw new ParseException(Concentration.MESSAGE_CONSTRAINTS);
        }
        return new Concentration(trimmedConcentration);
    }

    /**
     * Parses a {@code String height} into an {@code Height}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code height} is invalid.
     */
    public static Height parseHeight(String height) throws ParseException {
        String trimmedHeight = height.trim();
        if (!Height.isValidHeight(trimmedHeight)) {
            throw new ParseException(Height.MESSAGE_CONSTRAINTS);
        }
        return new Height(trimmedHeight);
    }

    /**
     * Parses a {@code String weight} into an {@code Weight}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code weight} is invalid.
     */
    public static Weight parseWeight(String weight) throws ParseException {
        String trimmedWeight = weight.trim();
        if (!Weight.isValidWeight(trimmedWeight)) {
            throw new ParseException(Weight.MESSAGE_CONSTRAINTS);
        }
        return new Weight(trimmedWeight);
    }
}
