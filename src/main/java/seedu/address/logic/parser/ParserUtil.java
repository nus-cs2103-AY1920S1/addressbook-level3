package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.AddNusModCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.GroupDescription;
import seedu.address.model.group.GroupName;
import seedu.address.model.mapping.Role;
import seedu.address.model.module.LessonNo;
import seedu.address.model.module.LessonType;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.NusModsShareLink;
import seedu.address.model.module.exceptions.LessonTypeNotFoundException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.schedule.Timeslot;
import seedu.address.model.person.schedule.Venue;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    //private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("ddMMyyyy:HHmm");

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("ddMMuuuu").withResolverStyle(ResolverStyle.STRICT);

    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("HHmm");


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
     * Parses a {@code String name} into a {@code GroupName}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static GroupName parseGroupName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!GroupName.isValid(trimmedName)) {
            throw new ParseException(GroupName.MESSAGE_CONSTRAINTS);
        }
        return new GroupName(trimmedName);
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
     * Parses a String moduleCode, and trims the String.
     *
     * @param moduleCode String to be trimmed
     * @return Trimmed String
     * @throws ParseException null
     */
    public static ModuleCode parseModuleCode(String moduleCode) throws ParseException {
        requireNonNull(moduleCode);
        String trimmedModuleCode = moduleCode.trim();
        if (!ModuleCode.isValidModuleCode(trimmedModuleCode)) {
            throw new ParseException(ModuleCode.MESSAGE_CONSTRAINTS);
        }
        return new ModuleCode(trimmedModuleCode);
    }

    /**
     * Parses a String of lesson type-number pairings.
     *
     * @param lessonTypeNumString String to be parsed
     * @return Trimmed String
     * @throws ParseException null
     */
    public static Map<LessonType, LessonNo> parseLessonTypeNumMap(String lessonTypeNumString) throws ParseException {
        requireNonNull(lessonTypeNumString);
        String trimmedLessonTypeNumString = lessonTypeNumString.trim();
        Map<LessonType, LessonNo> lessonTypeNoMap = new LinkedHashMap<>();

        String[] typeNumPairs = trimmedLessonTypeNumString.split(",");
        for (String token : typeNumPairs) {
            String[] pair = token.split(":");
            if (pair.length != 2) { // not properly formatted into TYPE:NUMBER pair.
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddNusModCommand.MESSAGE_USAGE));
            }
            LessonType lessonType;
            try {
                lessonType = LessonType.findLessonType(pair[0].trim());
            } catch (LessonTypeNotFoundException e) { // invalid lesson type provided.
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddNusModCommand.MESSAGE_USAGE));
            }
            LessonNo lessonNo = new LessonNo(pair[1].trim());
            lessonTypeNoMap.put(lessonType, lessonNo);
        }

        return lessonTypeNoMap;
    }

    /**
     * Parses a String nusModsLink to an {@code NusModsShareLink}.
     *
     * @param nusModsLink String to be parsed
     * @return NusModsShareLink
     * @throws ParseException null
     */
    public static NusModsShareLink parseNusModsLink(String nusModsLink) throws ParseException {
        requireNonNull(nusModsLink);
        String trimmedNusModsLink = nusModsLink.trim();
        return NusModsShareLink.parseLink(trimmedNusModsLink);
    }

    /**
     * Parse a String remark, and trims the String.
     *
     * @param remark String to be trimmed
     * @return Trimmed String
     * @throws ParseException null
     */
    public static Remark parseRemark(String remark) throws ParseException {
        requireNonNull(remark);
        String trimmedRemark = remark.trim();

        if (!Remark.isValid(trimmedRemark)) {
            throw new ParseException(Remark.MESSAGE_CONSTRAINTS);
        }

        return new Remark(trimmedRemark);
    }

    /**
     * Parse a String role, and trims the String.
     *
     * @param role String to be trimmed
     * @return Trimmed String
     * @throws ParseException null
     */
    public static Role parseRole(String role) throws ParseException {
        requireNonNull(role);
        String trimmedRole = role.trim();

        if (!Role.isValid(trimmedRole)) {
            throw new ParseException(Role.MESSAGE_CONSTRAINTS);
        }

        return new Role(trimmedRole);
    }

    /**
     * Parse a String description, and trims the String.
     *
     * @param description String to be trimmed
     * @return Trimmed String
     * @throws ParseException null
     */
    public static GroupDescription parseGroupDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!GroupDescription.isValid(trimmedDescription)) {
            throw new ParseException(GroupDescription.MESSAGE_CONSTRAINTS);
        }
        return new GroupDescription(trimmedDescription);
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
     * Parses a String into a Timeslot object.
     *
     * @param timeslot to be parsed
     * @return Timeslot object
     */
    public static Timeslot parseTimeslot(String timeslot) throws ParseException {
        try {
            String[] tokens = timeslot.split("-", 4);

            if (tokens.length != 3 && tokens.length != 4) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
            }

            LocalDate date = LocalDate.parse(tokens[0].trim(), DATE_FORMATTER);
            LocalTime startTime = LocalTime.parse(tokens[1].trim(), TIME_FORMATTER);
            LocalTime endTime = LocalTime.parse(tokens[2].trim(), TIME_FORMATTER);

            if (endTime.isBefore(startTime) || endTime.compareTo(startTime) == 0) {
                throw new ParseException("End time cannot be before start time.");
            }

            Venue venue;
            if (tokens.length == 4) {
                venue = new Venue(tokens[3].trim());
            } else {
                venue = Venue.emptyVenue();
            }

            return new Timeslot(LocalDateTime.of(date, startTime), LocalDateTime.of(date, endTime), venue);
        } catch (DateTimeParseException e) {
            throw new ParseException(AddEventCommand.MESSAGE_WRONG_TIMINGS);
        }
    }

    /**
     * Parses a String into a ArrayList of locations.
     *
     * @param locationsString to be parsed
     * @return ArrayList of locations
     */
    public static ArrayList<String> parseLocations(String locationsString) {
        String[] locationsArr = locationsString.split(" ");
        ArrayList<String> locations = new ArrayList<>(Arrays.asList(locationsArr));
        return locations;
    }

    /**
     * Parses a String into an eventName.
     *
     * @param eventName to be parsed
     * @return String
     * @throws ParseException
     */
    public static String parserEventName(String eventName) throws ParseException {
        String regex = "[\\p{Alnum}][\\p{Alnum} ]*";
        String msg =
                "An event name should only contain alphanumeric characters and spaces, and it should not be blank";

        String trimmedEventName = eventName.trim();
        if (!trimmedEventName.matches(regex)) {
            throw new ParseException(msg);
        }
        return trimmedEventName;
    }

    /**
     * Parses a String into an integer representing a week.
     *
     * @param weekString to be parsed
     * @return int
     * @throws ParseException
     */
    public static int parseWeek(String weekString) throws ParseException {
        try {
            int week = Integer.parseInt(weekString.trim());
            if (week < 1 || week > 4) {
                throw new ParseException("A week has to be an integer from 1 to 4.");
            }
            return week - 1;
        } catch (NumberFormatException nfe) {
            throw new ParseException("A week has to be an integer from 1 to 4.");
        }
    }

    /**
     * Parses a String into an integer representing a day.
     *
     * @param dayString
     * @return int
     * @throws ParseException
     */
    public static int parseDay(String dayString) throws ParseException {
        try {
            int day = Integer.parseInt(dayString.trim());
            if (day < 1 || day > 7) {
                throw new ParseException("A day has to be an integer between 1 and 7");
            }
            return day;
        } catch (NumberFormatException nfe) {
            throw new ParseException("A day has to be an integer between 1 and 7");
        }
    }

    /**
     * Parses a String into an integer representing an ID.
     *
     * @param idString to be parsed
     * @return int
     * @throws ParseException
     */
    public static int parseId(String idString) throws ParseException {
        try {
            int id = Integer.parseInt(idString.trim());
            return id;
        } catch (NumberFormatException nfe) {
            throw new ParseException("An id has to be an integer");
        }
    }

}


