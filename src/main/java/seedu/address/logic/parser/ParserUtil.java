package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;

import seedu.address.logic.commands.statisticcommand.StatisticType;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.customer.ContactNumber;
import seedu.address.model.customer.CustomerName;
import seedu.address.model.customer.Email;
import seedu.address.model.order.Price;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.phone.Brand;
import seedu.address.model.phone.Capacity;
import seedu.address.model.phone.Colour;
import seedu.address.model.phone.Cost;
import seedu.address.model.phone.IdentityNumber;
import seedu.address.model.phone.PhoneName;
import seedu.address.model.phone.SerialNumber;
import seedu.address.model.schedule.Venue;
import seedu.address.model.tag.Tag;
import seedu.address.statistic.StatsParseUtil;


/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_DATE = "Date is not in the correct format.";
    public static final String MESSAGE_INVALID_TIME = "Time is not in the correct format.";
    public static final String MESSAGE_INVALID_CALENDAR = "Date and Time are not in the correct format.";

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
     * Parses a {@code String phone} into a {@code phone}.
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
     * Parses a {@code String contactNumber} into a {@code contactNumber}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code contactNumber} is invalid.
     */
    public static ContactNumber parseContactNumber(String contactNumber) throws ParseException {
        requireNonNull(contactNumber);
        String trimmedContactNumber = contactNumber.trim();
        if (!ContactNumber.isValidContactNumber(trimmedContactNumber)) {
            throw new ParseException(ContactNumber.MESSAGE_CONSTRAINTS);
        }
        return new ContactNumber(trimmedContactNumber);
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
     * Parses a {@code String customerName} into a {@code customerName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code customerName} is invalid.
     */
    public static CustomerName parseCustomerName(String customerName) throws ParseException {
        requireNonNull(customerName);
        String trimmedCustomerName = customerName.trim();
        if (!CustomerName.isValidCustomerName(trimmedCustomerName)) {
            throw new ParseException(CustomerName.MESSAGE_CONSTRAINTS);
        }
        return new CustomerName(trimmedCustomerName);
    }

    /**
     * Parses a {@code String brand} into a {@code brand}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code brand} is invalid.
     */
    public static Brand parseBrand(String brand) throws ParseException {
        requireNonNull(brand);
        String trimmedBrand = brand.trim();
        if (!Brand.isValidBrand(trimmedBrand)) {
            throw new ParseException(Brand.MESSAGE_CONSTRAINTS);
        }
        return new Brand(trimmedBrand);
    }

    /**
     * Parses a {@code String capacity} into a {@code capacity}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code capacity} is invalid.
     */
    public static Capacity parseCapacity(String capacity) throws ParseException {
        requireNonNull(capacity);
        String trimmedCapacity = capacity.trim();
        if (!Capacity.isValidCapacity(trimmedCapacity)) {
            throw new ParseException(Capacity.MESSAGE_CONSTRAINTS);
        }

        switch(trimmedCapacity) {
        case "8":
            return Capacity.SIZE_8GB;

        case "16":
            return Capacity.SIZE_16GB;

        case "32":
            return Capacity.SIZE_32GB;

        case "64":
            return Capacity.SIZE_64GB;

        case "128":
            return Capacity.SIZE_128GB;

        case "256":
            return Capacity.SIZE_256GB;

        case "512":
            return Capacity.SIZE_512GB;

        case "1024":
            return Capacity.SIZE_1024GB;

        default:
            throw new ParseException(Capacity.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String colour} into a {@code colour}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code colour} is invalid.
     */
    public static Colour parseColour(String colour) throws ParseException {
        requireNonNull(colour);
        String trimmedColour = colour.trim();
        if (!Colour.isValidColour(trimmedColour)) {
            throw new ParseException(Colour.MESSAGE_CONSTRAINTS);
        }
        return new Colour(trimmedColour);
    }

    /**
     * Parses a {@code String cost} into a {@code cost}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code cost} is invalid.
     */
    public static Cost parseCost(String cost) throws ParseException {
        requireNonNull(cost);
        String trimmedCost = cost.trim();
        if (!Cost.isValidCost(trimmedCost)) {
            throw new ParseException(Cost.MESSAGE_CONSTRAINTS);
        }
        return new Cost(trimmedCost);
    }

    /**
     * Parses a {@code String phoneName} into a {@code phoneName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phoneName} is invalid.
     */
    public static PhoneName parsePhoneName(String phoneName) throws ParseException {
        requireNonNull(phoneName);
        String trimmedPhoneName = phoneName.trim();
        if (!PhoneName.isValidPhoneName(trimmedPhoneName)) {
            throw new ParseException(PhoneName.MESSAGE_CONSTRAINTS);
        }
        return new PhoneName(trimmedPhoneName);
    }

    /**
     * Parses a {@code String identityNumber} into a {@code identityNumber}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phoneName} is invalid.
     */
    public static IdentityNumber parseIdentityNumber(String identityNumber) throws ParseException {
        requireNonNull(identityNumber);
        String trimmedIdentityNumber = identityNumber.trim();
        if (!IdentityNumber.isValidIdentityNumber(trimmedIdentityNumber)) {
            throw new ParseException(IdentityNumber.MESSAGE_CONSTRAINTS);
        }
        return new IdentityNumber(trimmedIdentityNumber);
    }

    /**
     * Parses a {@code String serialNumber} into a {@code serialNumber}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code serialNumber} is invalid.
     */
    public static SerialNumber parseSerialNumber(String serialNumber) throws ParseException {
        requireNonNull(serialNumber);
        String trimmedSerialNumber = serialNumber.trim();
        if (!SerialNumber.isValidSerialNumber(trimmedSerialNumber)) {
            throw new ParseException(SerialNumber.MESSAGE_CONSTRAINTS);
        }
        return new SerialNumber(trimmedSerialNumber);
    }
    /**
     * Parses a {@Code String statsInput} into {@Code statisticType}.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the given {@Code statsInput} is invalid.
     */
    public static StatisticType parseStatsType(String statsInput) throws ParseException {
        requireNonNull(statsInput);
        System.out.println(statsInput);

        String trimmedStatsType = statsInput.trim();
        String[] checkInvalidChara = trimmedStatsType.split(" ");
        if (checkInvalidChara.length > 1) {
            throw new ParseException(Messages.STATS_MESSAGE_CONSTRAINTS_NO_INVALID_CHAR);
        }
        if (!StatsParseUtil.isValidStatType(trimmedStatsType)) {
            throw new ParseException(Messages.STATS_MESSAGE_CONSTRAINTS);
        }
        switch (trimmedStatsType) {
        case "profit":
            return StatisticType.PROFIT;
        case "cost":
            return StatisticType.COST;
        case "revenue":
            return StatisticType.REVENUE;
        default:
            throw new ParseException("Invalid type of Statistics. Only possible ones include:\n"
                    + "profit, cost , revenue");
        }
    }

    //@@author QiuJiaaa -reused
    // reused from the parse Calendar method with minor modifications
    /**
     *Parse a {@Code String calendar} into a {@Code calendar}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Calendar parseDateCalendar(String calendar) throws ParseException {
        requireNonNull(calendar);
        String trimmedDate = calendar.trim();
        String[] stringCalendar = trimmedDate.split("\\.");
        if (stringCalendar.length != 3) {
            throw new ParseException(Messages.DATE_MESSAGE_CONSTRAINTS);
        }
        int[] input = new int[3];
        LocalDateTime localDateTime;
        try {
            for (int index = 0; index < 3; index++) {
                input[index] = Integer.parseInt(stringCalendar[index]);
                if (input[index] <= 0) {
                    throw new ParseException(Messages.DATE_MESSAGE_CONSTRAINTS_LESS_THAN_0);
                }
                //offset for month
            }
            if (input[0] < 1970) {
                throw new ParseException(Messages.DATE_MESSAGE_CONSTRAINTS_LESS_THAN_DEFAULT);
            }

            localDateTime = LocalDateTime.of(input[0], input[1], input[2], 0, 0);
        } catch (NumberFormatException | DateTimeException e) {
            throw new ParseException(Messages.DATE_MESSAGE_CONSTRAINTS);
        }
        //@@author
        //offset month as Calendar takes in 0-based month
        int offsetMonth = input[1] - 1;
        return new Calendar.Builder().setDate(input[0], offsetMonth, input[2]).build();
    }

    /**
     * Parses a {@code String price} into a {@code price}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code price} is invalid.
     */
    public static Price parsePrice(String price) throws ParseException {
        requireNonNull(price);
        String trimmedPrice = price.trim();
        if (!Price.isValidPrice(trimmedPrice)) {
            throw new ParseException(Price.MESSAGE_CONSTRAINTS);
        }
        return new Price(trimmedPrice);
    }

    /**
     * Parses a {@code String date, time} into a {@code Calendar}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code calendar} is invalid.
     */
    public static Calendar parseCalendar(String date, String time) throws ParseException {
        requireNonNull(date);
        requireNonNull(time);

        Calendar calendarDate = parseDateCalendar(date);
        Calendar calendarTime = parseTimeCalendar(time);

        int newYear = calendarDate.get(Calendar.YEAR);
        int newMonth = calendarDate.get(Calendar.MONTH);
        int newDate = calendarDate.get(Calendar.DAY_OF_MONTH);
        int newHour = calendarTime.get(Calendar.HOUR_OF_DAY);
        int newMinute = calendarTime.get(Calendar.MINUTE);

        return new Calendar.Builder().setDate(newYear, newMonth, newDate)
                .setTimeOfDay(newHour, newMinute, 0).build();
    }

    /**
     *Parse a {@Code String time} into a {@Code calendar}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Calendar parseTimeCalendar(String time) throws ParseException {
        requireNonNull(time);
        String trimmedTime = time.trim();

        String[] timeArr = time.split("\\.");
        if (timeArr.length != 2) {
            throw new ParseException(Messages.TIME_MESSAGE_CONSTRAINTS);
        }
        int[] input = new int[3];

        LocalTime localTime;
        try {
            for (int index = 0; index < 2; index++) {
                input[index] = Integer.parseInt(timeArr[index]);
            }
            localTime = LocalTime.of(input[0], input[1]);
        } catch (NumberFormatException | DateTimeException e) {
            throw new ParseException(Messages.TIME_MESSAGE_CONSTRAINTS);
        }

        return new Calendar.Builder().setTimeOfDay(input[0], input[1], 0).build();
    }

    /**
     * Parses a {@code String venue} into a {@code Venue}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code venue} is invalid.
     */
    public static Venue parseVenue(String venue) throws ParseException {
        requireNonNull(venue);
        String trimmedVenue = venue.trim();
        if (!Venue.isValidVenue(trimmedVenue)) {
            throw new ParseException(Venue.MESSAGE_CONSTRAINTS);
        }
        return new Venue(trimmedVenue);
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static String parseFileName(String fileName) throws ParseException {
        requireNonNull(fileName);
        String trimmedName = fileName.trim();
        return trimmedName;
    }
}
