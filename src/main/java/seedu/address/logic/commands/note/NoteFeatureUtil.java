package seedu.address.logic.commands.note;

import static seedu.address.commons.core.Messages.MESSAGE_INCORRECT_NOTE_FRAGMENT_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_FRAGMENT_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_FRAGMENT_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_FRAGMENT_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_FRAGMENT_TAG;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.note.Content;
import seedu.address.model.note.Note;
import seedu.address.model.note.NoteFragment;
import seedu.address.model.note.Title;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing content strings in the Note classes. Parsed content is then used to
 * create NoteFragment objects, if necessary.
 */
public class NoteFeatureUtil {
    public static final String NOTE_FRAGMENT_CONTENT_DETECTION_REGEX = "\\s+" + PREFIX_NOTE_FRAGMENT_CONTENT;
    public static final String NOTE_FRAGMENT_END_DETECTION_REGEX = "\\s+" + PREFIX_NOTE_FRAGMENT_END.toString()
            .replaceAll("\\*", "\\\\*");
    public static final String NOTE_FRAGMENT_START_DETECTION_REGEX = "\\s*" + PREFIX_NOTE_FRAGMENT_START.toString()
            .replaceAll("\\*", "\\\\*");
    public static final String NOTE_FRAGMENT_TAG_DETECTION_REGEX = "\\s+" + PREFIX_NOTE_FRAGMENT_TAG + "\\S+";

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

            if (!NoteFeatureUtil.arePrefixesPresent(argMultimapCheck, PREFIX_NOTE_FRAGMENT_CONTENT,
                    PREFIX_NOTE_FRAGMENT_TAG)) {
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
            if (argMultimap.getValue(PREFIX_NOTE_FRAGMENT_CONTENT).isPresent()) {
                return ParserUtil.parseContent(argMultimap.getValue(PREFIX_NOTE_FRAGMENT_CONTENT).get());
            } else {
                assert false;
                return new Content("");
            }
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
            if (argMultimap.getValue(PREFIX_NOTE_FRAGMENT_CONTENT).isPresent()) {
                return ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_NOTE_FRAGMENT_TAG));
            } else {
                assert false;
                return new HashSet<>();
            }
        } catch (ParseException e) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
