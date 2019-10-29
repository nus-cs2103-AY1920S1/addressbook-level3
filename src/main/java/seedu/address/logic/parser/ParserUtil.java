package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INCORRECT_NOTE_FRAGMENT_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_FRAGMENT_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_FRAGMENT_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_FRAGMENT_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_FRAGMENT_TAG;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.flashcard.Answer;
import seedu.address.model.flashcard.Question;
import seedu.address.model.note.Content;
import seedu.address.model.note.Note;
import seedu.address.model.note.NoteFragment;
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
     * Parses a {@code String title} into a {@code Content}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code title} is invalid.
     */
    public static seedu.address.model.note.Title parseTitle(String title) throws ParseException {
        requireNonNull(title);
        String trimmedContent = title.trim();
        if (!Content.isValidContent(trimmedContent)) {
            throw new ParseException(Title.MESSAGE_CONSTRAINTS);
        }
        return new seedu.address.model.note.Title(trimmedContent);
    }

    /**
     * Parses a {@code String name} into a {@code Content}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code content} is invalid.
     */
    public static Content parseContent(String content) throws ParseException {
        requireNonNull(content);
        String trimmedContent = content.trim();
        if (!Content.isValidContent(trimmedContent)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Content(trimmedContent);
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
     * Parses a {@code String question} into a {@code Question}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code question} is invalid.
     */
    public static Question parseQuestion(String question) throws ParseException {
        requireNonNull(question);
        String trimmedQuestion = question.trim();
        if (!Question.isValidQuestion(trimmedQuestion)) {
            throw new ParseException(Question.MESSAGE_CONSTRAINTS);
        }
        return new Question(trimmedQuestion);
    }

    /**
     * Parses a {@code String answer} into a {@code Answer}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code answer} is invalid.
     */
    public static Answer parseAnswer(String answer) throws ParseException {
        requireNonNull(answer);
        String trimmedAnswer = answer.trim();
        if (!Answer.isValidAnswer(trimmedAnswer)) {
            throw new ParseException(Answer.MESSAGE_CONSTRAINTS);
        }
        return new Answer(trimmedAnswer);
    }

    /**
     * Parses a {@code String title} into a {@code Title}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code title} is invalid.
     */
    public static seedu.address.model.flashcard.Title parseFlashcardTitle(String title) throws ParseException {
        requireNonNull(title);
        String trimmedTitle = title.trim();
        if (!seedu.address.model.flashcard.Title.isValidTitle(trimmedTitle)) {
            throw new ParseException(Title.MESSAGE_CONSTRAINTS);
        }
        return new seedu.address.model.flashcard.Title(trimmedTitle);
    }

    /**
     * Parses a {@code String title} into a {@code Title}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code title} is invalid.
     */
    public static seedu.address.model.cheatsheet.Title parseCheatSheetTitle(String title) throws ParseException {
        requireNonNull(title);
        String trimmedTitle = title.trim();
        if (!seedu.address.model.cheatsheet.Title.isValidTitle(trimmedTitle)) {
            throw new ParseException(Title.MESSAGE_CONSTRAINTS);
        }
        return new seedu.address.model.cheatsheet.Title(trimmedTitle);
    }

    /**
     * Parses a {@code String content} into a {@code content}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code content} is invalid.
     */
    public static seedu.address.model.cheatsheet.Content parseCheatSheetContent(String content) throws ParseException {
        requireNonNull(content);
        String trimmedContent = content.trim();
        if (!seedu.address.model.cheatsheet.Content.isValidContent(trimmedContent)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new seedu.address.model.cheatsheet.Content(trimmedContent);
    }

    /**
     * Parses {@code Collection<String> contents} into a {@code Set<Content>}.
     */
    public static Set<seedu.address.model.cheatsheet.Content> parseCheatSheetContents(Collection<String> contents)
            throws ParseException {
        requireNonNull(contents);
        final Set<seedu.address.model.cheatsheet.Content> contentSet = new HashSet<>();
        for (String contentName : contents) {
            contentSet.add(parseCheatSheetContent(contentName));
        }
        return contentSet;
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
     * Parses a {@code Note note} into a {@code List<NoteFragment>}.
     * @param note The Note to parse.
     * @return Returns a List of {@code NoteFragments}.
     */
    public static List<NoteFragment> parseNoteFragmentsFromNote(Note note) {
        try {
            Title title = note.getTitle();
            List<String> noteFragmentMatches = parseNoteFragmentMatches(note.getContent().toString());
            return parseNoteFragmentsFromString(title, noteFragmentMatches);
        } catch (ParseException e) {
            return Collections.emptyList();
        }
    }

    /**
     * Parses all {@code NoteFragment} patterns from a String.
     * @param noteContent The content of the Note, as a String.
     * @return Returns a List of matching Strings.
     */
    public static List<String> parseNoteFragmentMatches(String noteContent) {
        noteContent = " " + noteContent;
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(noteContent, PREFIX_NOTE_FRAGMENT_START,
                PREFIX_NOTE_FRAGMENT_END);

        List<String> noteFragmentList = argMultimap.getAllValues(PREFIX_NOTE_FRAGMENT_START);
        return noteFragmentList;
    }

    /**
     * Parses a {@code List<String>} into a {@code List<NoteFragment>}.
     * @param title The {@code Title} of the {@code Note} this {@code List<String>} was derived from.
     * @param stringList A List of matching pattern Strings from a {@code Note}.
     * @return Returns a {@code List<NoteFragment>} containing the {@code NoteFragments} of a {@code Note}.
     * @throws ParseException A custom Exception.
     */
    public static List<NoteFragment> parseNoteFragmentsFromString(Title title, List<String> stringList)
            throws ParseException {
        List<NoteFragment> noteFragmentList = new ArrayList<>();
        for (String string : stringList) {
            string = " " + string;
            ArgumentMultimap argMultimapCheck = ArgumentTokenizer.tokenize(string, PREFIX_NOTE_FRAGMENT_CONTENT,
                    PREFIX_NOTE_FRAGMENT_TAG);

            if (!arePrefixesPresent(argMultimapCheck, PREFIX_NOTE_FRAGMENT_CONTENT, PREFIX_NOTE_FRAGMENT_TAG)) {
                throw new ParseException(MESSAGE_INCORRECT_NOTE_FRAGMENT_FORMAT);
            }

            noteFragmentList.add(new NoteFragment(title, parseContentFromNoteFragment(string),
                    parseTagsFromNoteFragment(string)));
        }
        return noteFragmentList;

    }

    /**
     * Parses a {@code Content} from a {@code NoteFragment}.
     * @param noteFragment The input {@code NoteFragment} object to parse.
     * @return Returns a {@code Content} object.
     * @throws ParseException A custom exception.
     */
    public static Content parseContentFromNoteFragment(String noteFragment) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(noteFragment, PREFIX_NOTE_FRAGMENT_CONTENT,
                PREFIX_NOTE_FRAGMENT_TAG);
        try {
            return parseContent(argMultimap.getValue(PREFIX_NOTE_FRAGMENT_CONTENT).get());
        } catch (ParseException e) {
            throw new ParseException(Content.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses {@code Set<Tag> tags} from a {@code NoteFragment}.
     * @param noteFragment The input {@code NoteFragment} object to parse.
     * @return Returns a {@code Set<Tag>} set of {@code tags}.
     * @throws ParseException A custom exception.
     */
    public static Set<Tag> parseTagsFromNoteFragment(String noteFragment) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(noteFragment, PREFIX_NOTE_FRAGMENT_CONTENT,
                PREFIX_NOTE_FRAGMENT_TAG);
        try {
            return parseTags(argMultimap.getAllValues(PREFIX_NOTE_FRAGMENT_TAG));
        } catch (ParseException e) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
