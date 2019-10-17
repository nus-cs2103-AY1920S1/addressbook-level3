package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.card.CardNumber;
import seedu.address.model.card.Cvc;
import seedu.address.model.card.Description;
import seedu.address.model.file.FileName;
import seedu.address.model.file.FilePath;
import seedu.address.model.note.Content;
import seedu.address.model.note.Title;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
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
     * Parses a {@code String description} into a {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static seedu.address.model.card.Description parseCardDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!seedu.address.model.card.Description.isValidDescription(trimmedDescription)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new seedu.address.model.card.Description(trimmedDescription);
    }

    /**
     * Parses a {@code String cardNumber} into a {@code CardNumber}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code cardNumber} is invalid.
     */
    public static CardNumber parseCardNumber(String cardNumber) throws ParseException {
        requireNonNull(cardNumber);
        String trimmedCardNumber = cardNumber.trim();
        if (!CardNumber.isValidCardNumber(trimmedCardNumber)) {
            throw new ParseException(CardNumber.MESSAGE_CONSTRAINTS);
        }
        return new CardNumber(trimmedCardNumber);
    }

    /**
     * Parses a {@code String cvc} into a {@code Cvc}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code cvc} is invalid.
     */
    public static Cvc parseCvc(String cvc) throws ParseException {
        requireNonNull(cvc);
        String trimmedCvc = cvc.trim();
        if (!Cvc.isValidCvc(trimmedCvc)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Cvc(trimmedCvc);
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
     * Parses a {@code String fullPath} into a {@code FileName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code fullPath} is invalid.
     */
    public static FileName parseFileName(String fullPath) throws ParseException {
        requireNonNull(fullPath);
        String trimmedFullPath = fullPath.trim();
        String fileName = Path.of(trimmedFullPath).getFileName().toString();
        if (!FileName.isValidFileName(fileName)) {
            throw new ParseException(FileName.MESSAGE_CONSTRAINTS);
        }
        return new FileName(fileName);
    }

    /**
     * Parses a {@code String fullPath} into a {@code FilePath}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code fullPath} is invalid.
     */
    public static FilePath parseFilePath(String fullPath) throws ParseException {
        requireNonNull(fullPath);
        String trimmedFullPath = fullPath.trim();
        String filePath = Path.of(trimmedFullPath).getParent().toString();
        if (!FilePath.isValidFilePath(filePath)) {
            throw new ParseException(FilePath.MESSAGE_CONSTRAINTS);
        }
        return new FilePath(filePath);
    }
    //Notes ====================================================================================================
    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Title parseTitle(String title) throws ParseException {
        requireNonNull(title);
        String trimmedTitle = title.trim();
        if (!Title.isValidTitle(trimmedTitle)) {
            throw new ParseException(Title.MESSAGE_CONSTRAINTS);
        }
        return new Title(trimmedTitle);
    }

    /**
     * Parses a {@code String description} into a {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static seedu.address.model.note.Description parseNoteDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!seedu.address.model.note.Description.isValidDescription(trimmedDescription)) {
            throw new ParseException(Title.MESSAGE_CONSTRAINTS);
        }
        return new seedu.address.model.note.Description(trimmedDescription);
    }
    /**
     * Parses a {@code String content} into a {@code Content}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code content} is invalid.
     */
    public static Content parseContent(String content) throws ParseException {
        requireNonNull(content);
        String trimmedContent = content.trim();
        if (!Content.isValidContent(trimmedContent)) {
            throw new ParseException(Title.MESSAGE_CONSTRAINTS);
        }
        return new Content(trimmedContent);
    }
}
