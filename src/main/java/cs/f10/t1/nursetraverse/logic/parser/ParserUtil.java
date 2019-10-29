package cs.f10.t1.nursetraverse.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import cs.f10.t1.nursetraverse.commons.core.index.Index;
import cs.f10.t1.nursetraverse.commons.util.StringUtil;
import cs.f10.t1.nursetraverse.logic.parser.exceptions.ParseException;
import cs.f10.t1.nursetraverse.model.datetime.EndDateTime;
import cs.f10.t1.nursetraverse.model.datetime.RecurringDateTime;
import cs.f10.t1.nursetraverse.model.datetime.StartDateTime;
import cs.f10.t1.nursetraverse.model.patient.Address;
import cs.f10.t1.nursetraverse.model.patient.Email;
import cs.f10.t1.nursetraverse.model.patient.Name;
import cs.f10.t1.nursetraverse.model.patient.Phone;
import cs.f10.t1.nursetraverse.model.tag.Tag;
import cs.f10.t1.nursetraverse.model.visit.Remark;
import cs.f10.t1.nursetraverse.model.visittodo.VisitTodo;

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
     * Parses a {@code Collection<String> indexes} into a {@code Set<Index>}.
     */
    public static Set<Index> parseIndexes(Collection<String> indexes) throws ParseException {
        requireNonNull(indexes);
        final Set<Index> indexSet = new HashSet<>();
        for (String index : indexes) {
            indexSet.add(parseIndex(index));
        }
        return indexSet;
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
     * Parses a {@code String visitTodo} into a {@code VisitTodo}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code visitTodo} is invalid.
     */
    public static VisitTodo parseVisitTodo(String visitTodo) throws ParseException {
        requireNonNull(visitTodo);
        String trimmedVisitTodo = visitTodo.trim();
        if (!VisitTodo.isValidVisitTodoDescription(trimmedVisitTodo)) {
            throw new ParseException(VisitTodo.MESSAGE_CONSTRAINTS);
        }
        return new VisitTodo(trimmedVisitTodo);
    }

    /**
     * Parses a {@code String remark} into a {@code Remark}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Remark parseRemark(String remark) {
        requireNonNull(remark);
        String trimmedRemark = remark.trim();
        return new Remark(trimmedRemark);
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
     * Parses {@code Collection<String> visitTodos} into a {@code Collection<VisitTodo>}.
     */
    public static Collection<VisitTodo> parseVisitTodos(Collection<String> visitTodos) throws ParseException {
        requireNonNull(visitTodos);
        final Collection<VisitTodo> visitTodoCollection = new LinkedHashSet<>();
        for (String visitTodoName : visitTodos) {
            visitTodoCollection.add(parseVisitTodo(visitTodoName));
        }
        return visitTodoCollection;
    }

    /**
     * Parses a {@code String startDateTime} into a {@code StartDateTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code startDateTime} is invalid.
     */
    public static StartDateTime parseStartDateTime(String startDateTime) throws ParseException {
        requireNonNull(startDateTime);
        String trimmedStartDateTime = startDateTime.trim();
        if (!StartDateTime.isValidStartDateTime(trimmedStartDateTime)) {
            throw new ParseException(StartDateTime.MESSAGE_CONSTRAINTS);
        }
        return new StartDateTime(trimmedStartDateTime);
    }

    /**
     * Parses a {@code String endDateTime} into a {@code EndDateTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code endDateTime} is invalid.
     */
    public static EndDateTime parseEndDateTime(String endDateTime, String startDateTime) throws ParseException {
        requireNonNull(endDateTime, startDateTime);
        String trimmedEndDateTime = endDateTime.trim();
        String trimmedStartDateTime = startDateTime.trim();
        if (!EndDateTime.isValidEndDateTime(trimmedEndDateTime, trimmedStartDateTime)) {
            throw new ParseException(EndDateTime.MESSAGE_CONSTRAINTS);
        }
        return new EndDateTime(trimmedEndDateTime);
    }

    /**
     * Parses a {@code String frequency} into a {@code Long}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code frequency} is invalid.
     */
    public static Long parseFrequency(String frequency) throws ParseException {
        String trimmedFrequency = frequency.trim();
        if (!RecurringDateTime.isValidFrequency(trimmedFrequency)) {
            throw new ParseException(RecurringDateTime.MESSAGE_CONSTRAINTS);
        }

        return RecurringDateTime.getSingleFrequencyAsLong(trimmedFrequency);
    }

    /**
     * Parses a {@code String description} into a {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static String parseDescription(String description) throws ParseException {
        String trimmedDescription = description.trim();

        return trimmedDescription;
    }

}
