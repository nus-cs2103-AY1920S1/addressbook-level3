package seedu.sugarmummy.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_INVALID_PARAMETER;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_POSSIBLE_AVERAGE_TYPE;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_POSSIBLE_RECORD_TYPE;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_UNABLE_TO_LOAD_IMAGE;
import static seedu.sugarmummy.logic.commands.statistics.AverageCommand.MESSAGE_INVALID_AVGTYPE;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.imageio.ImageIO;

import seedu.sugarmummy.commons.core.index.Index;
import seedu.sugarmummy.commons.util.StringUtil;
import seedu.sugarmummy.logic.commands.aesthetics.BackgroundCommand;
import seedu.sugarmummy.logic.commands.statistics.AverageCommand;
import seedu.sugarmummy.logic.parser.exceptions.ParseException;
import seedu.sugarmummy.model.aesthetics.Background;
import seedu.sugarmummy.model.aesthetics.Colour;
import seedu.sugarmummy.model.biography.Address;
import seedu.sugarmummy.model.biography.DateOfBirth;
import seedu.sugarmummy.model.biography.DisplayPicPath;
import seedu.sugarmummy.model.biography.Gender;
import seedu.sugarmummy.model.biography.Goal;
import seedu.sugarmummy.model.biography.MedicalCondition;
import seedu.sugarmummy.model.biography.Name;
import seedu.sugarmummy.model.biography.Nric;
import seedu.sugarmummy.model.biography.OtherBioInfo;
import seedu.sugarmummy.model.biography.Phone;
import seedu.sugarmummy.model.biography.ProfileDesc;
import seedu.sugarmummy.model.calendar.Description;
import seedu.sugarmummy.model.calendar.Repetition;
import seedu.sugarmummy.model.records.Concentration;
import seedu.sugarmummy.model.records.Height;
import seedu.sugarmummy.model.records.RecordType;
import seedu.sugarmummy.model.records.Weight;
import seedu.sugarmummy.model.statistics.AverageType;
import seedu.sugarmummy.model.time.DateTime;
import seedu.sugarmummy.model.time.TimeDuration;
import seedu.sugarmummy.model.time.YearMonth;
import seedu.sugarmummy.model.time.YearMonthDay;

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
            throw new ParseException(String.format(MESSAGE_INVALID_PARAMETER,
                    AverageCommand.MESSAGE_INVALID_RECORD_TYPE, MESSAGE_POSSIBLE_RECORD_TYPE));
        }
    }

    /**
     * Parses a {@code String averageType} into a {@code AverageType}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code averageType} does not match any of the enums.
     */
    public static AverageType parseAverageType(String averageType) throws ParseException {
        requireNonNull(averageType);
        String trimmedAverageType = averageType.trim().toUpperCase();
        try {
            return AverageType.valueOf(trimmedAverageType);
        } catch (IllegalArgumentException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_PARAMETER,
                    MESSAGE_INVALID_AVGTYPE, MESSAGE_POSSIBLE_AVERAGE_TYPE));
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

    //=========== User List =============================================================

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
     * Parses a {@code String profileDesc} into a {@code ProfileDesc}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code profileDesc} is invalid.
     */
    public static ProfileDesc parseProfileDesc(Optional<String> profileDesc) throws ParseException {
        requireNonNull(profileDesc);
        if (profileDesc.isPresent()) {
            String trimmedProfileDesc = profileDesc.get().trim();
            if (!ProfileDesc.isValidProfileDesc(trimmedProfileDesc)) {
                throw new ParseException(ProfileDesc.MESSAGE_CONSTRAINTS);
            }
            return new ProfileDesc(trimmedProfileDesc);
        } else {
            return new ProfileDesc("");
        }
    }

    /**
     * Parses a {@code String displayPicPath} into a {@code DisplayPicPath}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code displayPicPath} is invalid.
     */
    public static DisplayPicPath parseDpPath(Optional<String> dpPath) throws ParseException {
        requireNonNull(dpPath);
        if (dpPath.isPresent()) {
            String trimmedDisplayPic = dpPath.get().trim();
            if (!DisplayPicPath.isValidDisplayPicPath(trimmedDisplayPic)) {
                throw new ParseException(DisplayPicPath.MESSAGE_CONSTRAINTS);
            } else if (!trimmedDisplayPic.isEmpty()) {
                try {
                    Image image = ImageIO.read(new File(trimmedDisplayPic));
                    if (image == null) {
                        throw new ParseException(MESSAGE_UNABLE_TO_LOAD_IMAGE);
                    }
                } catch (IOException e) {
                    throw new ParseException(MESSAGE_UNABLE_TO_LOAD_IMAGE);
                }
            }
            return new DisplayPicPath(trimmedDisplayPic);
        } else {
            return new DisplayPicPath("");
        }
    }

    /**
     * Parses a {@code String nric} into a {@code Nric}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code nric} is invalid.
     */
    public static Nric parseNric(Optional<String> nric) throws ParseException {
        requireNonNull(nric);
        if (nric.isPresent()) {
            String trimmedNric = nric.get().trim();
            if (!Nric.isValidNric(trimmedNric)) {
                throw new ParseException(Nric.MESSAGE_CONSTRAINTS);
            }
            return new Nric(trimmedNric);
        } else {
            return new Nric("");
        }
    }

    /**
     * Parses a {@code String gender} into a {@code Gender}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code gender} is invalid.
     */
    public static Gender parseGender(Optional<String> gender) throws ParseException {
        requireNonNull(gender);
        if (gender.isPresent()) {
            String trimmedGender = gender.get().trim();
            if (!Gender.isValidGender(trimmedGender)) {
                throw new ParseException(Gender.MESSAGE_CONSTRAINTS);
            }
            return new Gender(trimmedGender);
        } else {
            return new Gender("");
        }
    }

    /**
     * Parses a {@code String dateOfBirth} into a {@code DateOfBirth}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code dateOfBirth} is invalid.
     */
    public static DateOfBirth parseDateOfBirth(Optional<String> dateOfBirth) throws ParseException {
        requireNonNull(dateOfBirth);
        if (dateOfBirth.isPresent()) {
            String trimmedDateOfBirth = dateOfBirth.get().trim();
            if (!DateOfBirth.isValidDateOfBirth(trimmedDateOfBirth)) {
                throw new ParseException(DateOfBirth.MESSAGE_CONSTRAINTS);
            }
            return new DateOfBirth(trimmedDateOfBirth);
        } else {
            return new DateOfBirth("");
        }
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
     * Parses {@code Collection<String> phones} into a {@code List<Phone>}.
     */
    public static List<Phone> parsePhones(Collection<String> phones) throws ParseException {
        requireNonNull(phones);
        if (phones.isEmpty()) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }

        final List<Phone> phoneList = new ArrayList<>();
        for (String phoneNumber : phones) {
            phoneList.add(parsePhone(phoneNumber));
        }

        Set<Phone> phoneSet = new HashSet<>(phoneList);
        if (phoneSet.size() != phoneList.size()) {
            throw new ParseException(Phone.MESSAGE_DUPLICATE_INPUTS);
        }

        return phoneList;
    }

    /**
     * Parses a {@code String medicalCondition} into a {@code MedicalCondition}. Leading and trailing whitespaces will
     * be trimmed.
     *
     * @throws ParseException if the given {@code medicalCondition} is invalid.
     */
    public static MedicalCondition parseMedicalCondition(String medicalCondition) throws ParseException {
        requireNonNull(medicalCondition);
        String trimmedMedicalCondition = medicalCondition.trim();
        if (!MedicalCondition.isValidMedicalCondition(trimmedMedicalCondition)) {
            throw new ParseException(MedicalCondition.MESSAGE_CONSTRAINTS);
        }
        return new MedicalCondition(trimmedMedicalCondition);
    }

    /**
     * Parses {@code Collection<String> medicalConditions} into a {@code List<MedicalCondition>}.
     */
    public static List<MedicalCondition> parseMedicalConditions(Collection<String> medicalConditions)
            throws ParseException {
        requireNonNull(medicalConditions);
        if (medicalConditions.isEmpty()) {
            throw new ParseException(MedicalCondition.MESSAGE_CONSTRAINTS);
        }
        final List<MedicalCondition> medicalConditionList = new ArrayList<>();
        for (String medicalConditionName : medicalConditions) {
            medicalConditionList.add(parseMedicalCondition(medicalConditionName));
        }

        Set<MedicalCondition> medicalConditionSet = new HashSet<>(medicalConditionList);
        if (medicalConditionSet.size() != medicalConditionList.size()) {
            throw new ParseException(MedicalCondition.MESSAGE_DUPLICATE_INPUTS);
        }

        return medicalConditionList;
    }

    /**
     * Parses a {@code String address} into a {@code Address}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(Optional<String> address) throws ParseException {
        requireNonNull(address);
        if (address.isPresent()) {
            String trimmedAddress = address.get().trim();
            if (!Address.isValidAddress(trimmedAddress)) {
                throw new ParseException(Address.MESSAGE_CONSTRAINTS);
            }
            return new Address(trimmedAddress);
        } else {
            return new Address("");
        }
    }

    /**
     * Parses a {@code String goal} into a {@code Goal}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code goal} is invalid.
     */
    public static Goal parseGoal(String goal) throws ParseException {
        requireNonNull(goal);
        String trimmedGoal = goal.trim();
        if (!Goal.isValidGoal(trimmedGoal)) {
            throw new ParseException(Goal.MESSAGE_CONSTRAINTS);
        }
        return new Goal(trimmedGoal);
    }

    /**
     * Parses {@code Collection<String> goals} into a {@code List<Goal>}.
     */
    public static List<Goal> parseGoals(Collection<String> goals) throws ParseException {
        requireNonNull(goals);
        final List<Goal> goalList = new ArrayList<>();
        for (String goalName : goals) {
            goalList.add(parseGoal(goalName));
        }

        Set<Goal> goalSet = new HashSet<>(goalList);
        if (goalSet.size() != goalList.size()) {
            throw new ParseException(Goal.MESSAGE_DUPLICATE_INPUTS);
        }

        return goalList;
    }

    /**
     * Parses a {@code String otherInfo} into a {@code OtherBioInfo}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code otherBioInfo} is invalid.
     */
    public static OtherBioInfo parseOtherBioInfo(Optional<String> otherInfo) throws ParseException {
        if (otherInfo.isPresent()) {
            requireNonNull(otherInfo);
            String trimmedOtherInfo = otherInfo.get().trim();
            if (!OtherBioInfo.isValidOtherBioInfo(trimmedOtherInfo)) {
                throw new ParseException(OtherBioInfo.MESSAGE_CONSTRAINTS);
            }
            return new OtherBioInfo(trimmedOtherInfo);
        } else {
            return new OtherBioInfo("");
        }
    }

    //=========== Aesthetics =============================================================

    /**
     * Parses a {@code String colour} into a {@code Colour}. Leading and trailing whitespaces will be trimmed.
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
     * Parses a {@code String background} into a {@code Background}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code background} is invalid.
     */
    public static Background parseBackground(String background) throws ParseException {
        requireNonNull(background);
        String trimmedBackground = background.trim();
        if (Colour.isValidColour(trimmedBackground)) {
            return new Background(trimmedBackground);
        } else if (!Background.isValidBackgroundPicPath(trimmedBackground)) {
            throw new ParseException(Background.MESSAGE_CONSTRAINTS + "\n" + BackgroundCommand.MESSAGE_USAGE);
        } else if (trimmedBackground.isEmpty()) {
            return new Background("");
        } else {
            try {
                Image image = ImageIO.read(new File(trimmedBackground));
                if (image == null) {
                    throw new ParseException(MESSAGE_UNABLE_TO_LOAD_IMAGE + "\n" + BackgroundCommand.MESSAGE_USAGE);
                }
            } catch (IOException e) {
                throw new ParseException(MESSAGE_UNABLE_TO_LOAD_IMAGE + "\n" + BackgroundCommand.MESSAGE_USAGE);
            }
            return new Background(trimmedBackground);
        }
    }

    //=========== Reminder =============================================================

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
        return new DateTime(dateTime);
    }

    /**
     * Parses a {@code String dateTime} into an {@code DateTime}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dateTime} is invalid or is in the future.
     */
    public static DateTime parsePastDateTime(String dateTime) throws ParseException {
        DateTime dt = parseDateTime(dateTime);
        if (dt.isAfterDateTime(new DateTime(LocalDate.now(), LocalTime.now()))) {
            throw new ParseException(DateTime.MESSAGE_FUTURE_CONSTRAINTS);
        }
        return dt;
    }

    /**
     * Parses a {@code String description} into a {@code Description}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static Description parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Description.isValidDescription(trimmedDescription)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(trimmedDescription);
    }

    /**
     * Parses a {@code String repetition} into a {@code Repetition}.
     *
     * @throws ParseException if the given {@code repetition} is invalid.
     */
    public static Repetition parseRepetition(String repetition) throws ParseException {
        requireNonNull(repetition);
        String trimmedRepetition = repetition.trim();
        if (!Repetition.isValidRepetition(trimmedRepetition)) {
            throw new ParseException(Repetition.MESSAGE_CONSTRAINTS);
        }
        return Repetition.of(trimmedRepetition);
    }

    /**
     * Parses a {@code String timeDuration} into a {@code TimeDuration}.
     *
     * @throws ParseException if the given {@code timeDuration} is invalid.
     */
    public static TimeDuration parseTimeDuration(String timeDuration) throws ParseException {
        requireNonNull(timeDuration);
        String trimmedTimeDuration = timeDuration.trim();
        if (!TimeDuration.isValidTimeDuration(trimmedTimeDuration)) {
            throw new ParseException(TimeDuration.MESSAGE_CONSTRAINTS);
        }
        return new TimeDuration(trimmedTimeDuration);
    }

    /**
     * Parses a {@code String yearMonth} into a {@code YearMonth}.
     *
     * @throws ParseException if the given {@code yearMonth} is invalid.
     */
    public static YearMonth parseYearMonth(String yearMonth) throws ParseException {
        requireNonNull(yearMonth);
        String trimmedYearMonth = yearMonth.trim();
        if (!YearMonth.isValidYearMonth(trimmedYearMonth)) {
            throw new ParseException(YearMonth.MESSAGE_CONSTRAINTS);
        }
        return new YearMonth(yearMonth);
    }

    /**
     * Parses a {@code String yearMonthDay} into a {@code YearMonthDay}.
     *
     * @throws ParseException if the given {@code yearMonthDay} is invalid.
     */
    public static YearMonthDay parseYearMonthDay(String yearMonthDay) throws ParseException {
        requireNonNull(yearMonthDay);
        String trimmedYearMonthDay = yearMonthDay.trim();
        if (!YearMonthDay.isValidYearMonthDay(trimmedYearMonthDay)) {
            throw new ParseException(YearMonthDay.MESSAGE_CONSTRAINTS);
        }
        return new YearMonthDay(yearMonthDay);
    }

    //=========== Statistics =============================================================

    /**
     * Parses a {@code String concentration} into an {@code Concentration}. Leading and trailing whitespaces will be
     * trimmed.
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
