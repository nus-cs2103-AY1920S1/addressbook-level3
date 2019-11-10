package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_VEHICLE_NUMBER;
import static seedu.address.commons.core.Messages.MESSAGE_NO_SUCH_VTYPE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.incident.CallerNumber;
import seedu.address.model.incident.Description;
import seedu.address.model.incident.IncidentDateTime;
import seedu.address.model.incident.IncidentId;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Password;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Username;
import seedu.address.model.tag.Tag;
import seedu.address.model.vehicle.Availability;
import seedu.address.model.vehicle.District;
import seedu.address.model.vehicle.VehicleNumber;
import seedu.address.model.vehicle.VehicleType;

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
        if (trimmedIndex.contains("/")) {
            throw new ParseException(Messages.MESSAGE_IRRELEVANT_PREFIXES);
        }
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
     * Parses a {@code String username} into a {@code Username}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code username} is invalid.
     */
    public static Username parseUsername(String username) throws ParseException {
        requireNonNull(username);
        String trimmedUsername = username.trim();
        if (!Username.isValidUsername(trimmedUsername)) {
            throw new ParseException(Username.MESSAGE_CONSTRAINTS);
        }
        return new Username(trimmedUsername);
    }

    /**
     * Parses a {@code String password} into a {@code Password}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code password} is invalid.
     */
    public static Password parsePassword(String password) throws ParseException {
        requireNonNull(password);
        String trimmedPassword = password.trim();
        if (!Password.isValidPassword(trimmedPassword)) {
            throw new ParseException(Password.MESSAGE_CONSTRAINTS);
        }
        return new Password(trimmedPassword);
    }

    /**
     * Parses a {@code String caller} into a {@code CallerNumber}.
     * Leading and trailing white spaces will be trimmed.
     *
     * throws ParseException if the given {@code caller} is invalid.
     */
    public static CallerNumber parseCallerNumber(String caller) throws ParseException {
        requireNonNull(caller);
        String trimmedCaller = caller.trim();
        if (trimmedCaller.contains("/")) {
            throw new ParseException(Messages.MESSAGE_IRRELEVANT_PREFIXES);
        }
        if (!CallerNumber.isValidCaller(trimmedCaller)) {
            throw new ParseException(CallerNumber.MESSAGE_CONSTRAINTS);
        }
        return new CallerNumber(trimmedCaller);
    }

    /**
     * Used only when creating incidents, as this is the only situation where only
     * 1 input is allowed for district input.
     * @param district
     * @return
     * @throws ParseException
     */
    public static District parseDistrict(String district) throws ParseException {
        requireNonNull(district);
        String trimmedDistrict = district.trim();
        if (trimmedDistrict.contains("/")) {
            throw new ParseException(Messages.MESSAGE_IRRELEVANT_PREFIXES);
        }
        try {
            int dist = Integer.parseInt(trimmedDistrict);
            if (!District.isValidDistrict(dist)) {
                throw new ParseException(District.MESSAGE_CONSTRAINTS);
            }
            return new District(dist);
        } catch (NumberFormatException e) {
            throw new ParseException(District.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String district} into a list of {@code Districts}.
     * Leading and trailing white spaces will be trimmed.
     * User input should be a number.
     *
     * throws ParseException if the given {@code district} is invalid.
     */
    public static List<District> parseDistricts(String district) throws ParseException {
        requireNonNull(district);
        if (district.contains("/")) {
            throw new ParseException(Messages.MESSAGE_IRRELEVANT_PREFIXES);
        }
        try {
            List<String> splittedD = Arrays.asList(district.trim().split("\\s"));
            List<District> districts = new ArrayList<>();
            for (String d: splittedD) {
                int dist = Integer.parseInt(d);
                if (!District.isValidDistrict(dist)) {
                    throw new ParseException(District.MESSAGE_CONSTRAINTS);
                }
                districts.add(new District(dist));
            }
            return districts;
        } catch (NumberFormatException e) {
            throw new ParseException(District.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String vType} into a {@code VehicleType}.
     * @param vType
     * @return VehicleType
     * @throws ParseException when vType entered is not in static list in VehicleType class.
     */
    public static VehicleType parseVType(String vType) throws ParseException {
        requireNonNull(vType);
        String parsedVType = vType.trim().toLowerCase();

        if (parsedVType.contains("/")) {
            throw new ParseException(Messages.MESSAGE_IRRELEVANT_PREFIXES);
        }
        String[] vehicleTypes = VehicleType.VEHICLE_TYPES;
        for (String type: vehicleTypes) {
            if (parsedVType.equalsIgnoreCase(type)) {
                return new VehicleType(type);
            }
        }
        throw new ParseException(MESSAGE_NO_SUCH_VTYPE);
    }

    /**
     * Parses a {@code String vNum} into a {@code VehicleNumber}.
     * @param vNum
     * @return VehicleNumber
     * @throws ParseException if VNum does not follow the format ABC1234D
     */
    public static VehicleNumber parseVNum(String vNum) throws ParseException {
        requireNonNull(vNum);
        String parsedVNum = vNum.trim().toUpperCase();

        if (parsedVNum.contains("/")) {
            throw new ParseException(Messages.MESSAGE_IRRELEVANT_PREFIXES);
        } else if (!VehicleNumber.isValidVehicleNumber(parsedVNum)) {
            throw new ParseException(MESSAGE_INVALID_VEHICLE_NUMBER);
        }
        return new VehicleNumber(parsedVNum);
    }

    /**
     * Parses a {@code String avail} into a {@code Availability}.
     * Input will be checked for validity.
     * Leading and trailing white spaces will be trimmed.
     */
    public static Availability parseAvailability(String avail) throws ParseException {
        requireNonNull(avail);
        String trimmedAvail = avail.trim();
        if (trimmedAvail.contains("/")) {
            throw new ParseException(Messages.MESSAGE_IRRELEVANT_PREFIXES);
        }
        if (!trimmedAvail.equalsIgnoreCase("available") && !trimmedAvail.equalsIgnoreCase("busy")) {
            throw new ParseException(Availability.MESSAGE_CONSTRAINTS);
        }
        return new Availability(trimmedAvail);
    }
    /**
     * Parses a {@code String description} into a {@code Description}.
     * Leading and trailing white spaces will be trimmed.
     * Will not be checked for validity as Description can vary widely
     * and does not have a fixed input format.
     */
    public static Description parseDescription(String description) {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        return new Description(trimmedDescription);
    }

    /**
     * Parses a {@code String dateTime} into a {@code IncidentDateTime}.
     * Leading and trailing white spaces will be trimmed.
     *
     * throws ParseException if the give {@code dateTime} is not valid.
     */
    public static IncidentDateTime parseDateTime(String dateTime) {
        requireNonNull(dateTime);
        String trimmedDateTime = dateTime.trim();
        return new IncidentDateTime(trimmedDateTime);

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
     * Parses a {@code String incident id keyword} into an {@code IncidentId}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code incident id keyword} is invalid.
     */
    public static IncidentId parseId(String id) throws ParseException {
        requireNonNull(id);
        String trimmedId = id.trim();
        return new IncidentId(trimmedId);
    }

    /**
     * Parses a {@code String auto y/n} into a {@boolean isAuto}.
     *
     * @param auto
     * @return isAuto
     * @throws ParseException if the {@code auto command} is invalid.
     */
    public static boolean parseAuto(String auto) throws ParseException {
        boolean isAuto;
        requireNonNull(auto);
        String parsedAuto = auto.toLowerCase();
        if (parsedAuto.equals("y")) {
            isAuto = true;
        } else if (parsedAuto.equals("n")) {
            isAuto = false;
        } else if (parsedAuto.contains("/")) {
            throw new ParseException(Messages.MESSAGE_IRRELEVANT_PREFIXES);
        } else {
            throw new ParseException(Messages.MESSAGE_AUTO_ONLY_Y_N);
        }

        return isAuto;
    }

}
