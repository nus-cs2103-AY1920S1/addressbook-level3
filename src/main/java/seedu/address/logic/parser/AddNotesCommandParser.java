package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import java.util.stream.Stream;

import seedu.address.logic.commands.note.AddNotesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.note.Content;
import seedu.address.model.note.ModuleCode;
import seedu.address.model.note.Notes;

/**
 * Parses input arguments and creates a new AddNotesCommand object
 */
public class AddNotesCommandParser implements Parser<AddNotesCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddNotesCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE_CODE, PREFIX_CONTENT);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE_CODE, PREFIX_CONTENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddNotesCommand.MESSAGE_USAGE));
        }

        ModuleCode module = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE_CODE).get());
        Content content = ParserUtil.parseContent(argMultimap.getValue(PREFIX_CONTENT).get());

        Notes note = new Notes(module, content);

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
