package seedu.address.logic.parser.note;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.note.NoteAddCommand;
import seedu.address.logic.commands.note.NoteCommand;
import seedu.address.logic.commands.note.NoteDeleteCommand;
import seedu.address.logic.commands.note.NoteEditCommand;
import seedu.address.logic.commands.note.NoteListCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.HashMap;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

public class NoteCommandParser implements Parser<NoteCommand> {

    public NoteCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer
                .tokenize(args, PREFIX_NOTE, PREFIX_DESCRIPTION, PREFIX_LIST,
                        PREFIX_DELETE);

        boolean isEdit = false;
        Index index = Index.fromZeroBased(0);
        try {
            String preamble = argMultimap.getPreamble();

            if (!preamble.isBlank()) {
                index = ParserUtil.parseIndex(preamble);
                isEdit = true;
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteEditCommand.MESSAGE_USAGE),
                    pe);
        }

        if (argMultimap.getValue(PREFIX_LIST).isPresent()) { // List command
            return new NoteListCommand();
        } else if (argMultimap.getValue(PREFIX_DELETE).isPresent()) { // Delete command
            try {
                int indexToDelete = Integer
                        .parseInt(argMultimap.getValue(PREFIX_DELETE).orElse("0"));

                if (indexToDelete <= 0) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                    NoteDeleteCommand.MESSAGE_USAGE));
                }
                index.fromOneBased(indexToDelete);
            } catch (NumberFormatException e) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                NoteDeleteCommand.MESSAGE_USAGE));
            }

            return new NoteDeleteCommand(index);
        } else if (isEdit) { // Edit command
            // Add parameters to be edited. Note: the fields are optional
            HashMap<String, String> fields = new HashMap<>();
            fields.put("note", argMultimap.getValue(PREFIX_NOTE).orElse(""));
            fields.put("description", argMultimap.getValue(PREFIX_DESCRIPTION).orElse(""));
            return new NoteEditCommand(index, fields);
        } else { // Create command
            if (!arePrefixesPresent(argMultimap, PREFIX_NOTE, PREFIX_DESCRIPTION)
                    || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(
                        String
                                .format(MESSAGE_INVALID_COMMAND_FORMAT, NoteAddCommand.MESSAGE_USAGE));
            }

            String note = argMultimap.getValue(PREFIX_NOTE).orElse("");
            String description = argMultimap.getValue(PREFIX_DESCRIPTION).orElse("");

            return new NoteAddCommand(note, description);
        }
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap,
                                              Prefix... prefixes) {
        return Stream.of(prefixes)
                .allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
