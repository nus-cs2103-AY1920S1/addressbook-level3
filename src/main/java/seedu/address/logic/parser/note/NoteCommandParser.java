package seedu.address.logic.parser.note;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_NOTE_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_NOTE_PREAMBLE;

import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.note.NoteAddCommand;
import seedu.address.logic.commands.note.NoteCommand;
import seedu.address.logic.commands.note.NoteDeleteCommand;
import seedu.address.logic.commands.note.NoteEditCommand;
import seedu.address.logic.commands.note.NoteEditCommand.EditNoteDescriptor;
import seedu.address.logic.commands.note.NoteListCommand;
import seedu.address.logic.commands.note.NoteSortCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.note.Note;
import seedu.address.model.note.Priority;

/**
 * Parses input arguments and creates a new {@code NoteCommand} object
 */
public class NoteCommandParser implements Parser<NoteCommand> {

    private static final Logger logger = LogsCenter.getLogger(NoteCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the note commands
     * and returns an NoteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public NoteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer
                .tokenize(args, CliSyntax.PREFIX_NOTE, CliSyntax.PREFIX_DESCRIPTION, CliSyntax.PREFIX_LIST,
                        CliSyntax.PREFIX_SORT, CliSyntax.PREFIX_DELETE, CliSyntax.PREFIX_PRIORITY);

        if (isEditCommand(argMultimap)) {
            return getNoteEditCommand(argMultimap);
        } else if (isListCommand(argMultimap)) {
            return getNoteListCommand(args);
        } else if (isSortCommand(argMultimap)) {
            return getNoteSortCommand(args);
        } else if (isDeleteCommand(argMultimap)) {
            return getNoteDeleteCommand(argMultimap);
        } else if (isAddCommand(argMultimap)) {
            return getNoteAddCommand(argMultimap);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteAddCommand.MESSAGE_USAGE));
        }
    }

    private static NoteEditCommand getNoteEditCommand(ArgumentMultimap argMultimap) throws ParseException {
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            logger.info("Note preamble index provided is invalid.");
            throw new ParseException(String.format(MESSAGE_INVALID_NOTE_PREAMBLE, NoteEditCommand.MESSAGE_USAGE), pe);
        }
        EditNoteDescriptor editNoteDescriptor = new EditNoteDescriptor();
        Optional<String> note = argMultimap.getValue(CliSyntax.PREFIX_NOTE);
        Optional<String> desc = argMultimap.getValue(CliSyntax.PREFIX_DESCRIPTION);
        if ((note.isPresent() && note.get().isEmpty())
                || (desc.isPresent() && desc.get().isEmpty())) {
            logger.info("Note fields provided for editing is invalid.");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteEditCommand.MESSAGE_USAGE));
        }
        editNoteDescriptor.setNote(note);
        editNoteDescriptor.setDescription(desc);
        Optional<Priority> priority = argMultimap.getValue(CliSyntax.PREFIX_PRIORITY).isPresent()
                ? Optional.of(Priority.getPriority(argMultimap.getValue(CliSyntax.PREFIX_PRIORITY).get()))
                : Optional.empty();
        editNoteDescriptor.setPriority(priority);
        return new NoteEditCommand(index, editNoteDescriptor);
    }

    private static NoteListCommand getNoteListCommand(String args) throws ParseException {
        if (args.trim().equals("list")) {
            return new NoteListCommand();
        } else {
            logger.info("note list command error, no additional fields are allowed");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteListCommand.MESSAGE_USAGE));
        }
    }

    private static NoteSortCommand getNoteSortCommand(String args) throws ParseException {
        if (args.trim().equals("sort")) {
            return new NoteSortCommand();
        } else {
            logger.info("note sort command error, no additional fields are allowed");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteSortCommand.MESSAGE_USAGE));
        }
    }

    private static NoteDeleteCommand getNoteDeleteCommand(ArgumentMultimap argMultimap) throws ParseException {
        Index index;
        try {
            int indexToDelete = Integer.parseInt(argMultimap.getValue(CliSyntax.PREFIX_DELETE).orElse("0"));
            if (indexToDelete <= 0) {
                logger.info("index of note to delete must be a positive integer");
                throw new ParseException(MESSAGE_INVALID_NOTE_DISPLAYED_INDEX + "\n" + NoteDeleteCommand.MESSAGE_USAGE);
            }
            index = Index.fromOneBased(indexToDelete);
        } catch (NumberFormatException e) {
            logger.info("error parsing index of note to delete");
            throw new ParseException(MESSAGE_INVALID_NOTE_DISPLAYED_INDEX + "\n" + NoteDeleteCommand.MESSAGE_USAGE);
        }
        return new NoteDeleteCommand(index);
    }

    private static NoteAddCommand getNoteAddCommand(ArgumentMultimap argMultimap) throws ParseException {
        String note = argMultimap.getValue(CliSyntax.PREFIX_NOTE).orElse("").trim();
        String description = argMultimap.getValue(CliSyntax.PREFIX_DESCRIPTION).orElse("").trim();
        if (note.isEmpty() || description.isEmpty()) {
            logger.info("note add command has missing fields, command error");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteAddCommand.MESSAGE_USAGE));
        }
        Optional<Priority> priority = argMultimap.getValue(CliSyntax.PREFIX_PRIORITY).isPresent()
                ? Optional.of(Priority.getPriority(argMultimap.getValue(CliSyntax.PREFIX_PRIORITY).get()))
                : Optional.empty();
        return new NoteAddCommand(new Note(note, description, priority.orElse(Priority.UNMARKED)));
    }

    /**
     * Checks if NoteCommand is of type edit.
     */
    private static boolean isEditCommand(ArgumentMultimap argMultimap) {
        String preamble = argMultimap.getPreamble();
        if (!preamble.isBlank()) {
            return true;
        }
        return false;
    }

    /**
     * Checks if NoteCommand is of type list.
     */
    private static boolean isListCommand(ArgumentMultimap argMultimap) {
        return argMultimap.getValue(CliSyntax.PREFIX_LIST).isPresent();
    }

    /**
     * Checks if NoteCommand is of type sort.
     */
    private static boolean isSortCommand(ArgumentMultimap argMultimap) {
        return argMultimap.getValue(CliSyntax.PREFIX_SORT).isPresent();
    }

    /**
     * Checks if NoteCommand is of type delete.
     */
    private static boolean isDeleteCommand(ArgumentMultimap argMultimap) {
        return argMultimap.getValue(CliSyntax.PREFIX_DELETE).isPresent();
    }

    /**
     * Checks if NoteCommand is of type create.
     */
    private static boolean isAddCommand(ArgumentMultimap argMultimap) {
        return arePrefixesPresent(argMultimap, CliSyntax.PREFIX_NOTE, CliSyntax.PREFIX_DESCRIPTION)
                || !argMultimap.getPreamble().isEmpty();
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes)
                .allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
