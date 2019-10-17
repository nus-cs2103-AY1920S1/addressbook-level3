package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddNotesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.note.Content;
import seedu.address.model.note.Description;
import seedu.address.model.note.Note;
import seedu.address.model.note.Title;
import seedu.address.model.tag.Tag;



/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddNoteCommandParser implements Parser<AddNotesCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddNotesCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddNotesCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_DESCRIPTION, PREFIX_TAG, PREFIX_CONTENT);

        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_DESCRIPTION, PREFIX_TAG, PREFIX_CONTENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddNotesCommand.MESSAGE_USAGE));
        }

        Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get());
        Description description = ParserUtil.parseNoteDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        Content content = ParserUtil.parseContent(argMultimap.getValue(PREFIX_CONTENT).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Note note = new Note(title, description, tagList, content);
        return new AddNotesCommand(note);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
