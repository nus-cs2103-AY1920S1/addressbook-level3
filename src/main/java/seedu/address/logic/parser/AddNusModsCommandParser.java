package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddNusModsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.NusModsShareLink;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new AddNusModsCommand object.
 */
public class AddNusModsCommandParser implements Parser<AddNusModsCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddNusModsCommand
     * and returns an AddNusModsCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddNusModsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_LINK);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_LINK) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddNusModsCommand.MESSAGE_USAGE));
        }

        NusModsShareLink link = ParserUtil.parseNusModsLink(argMultimap.getValue(PREFIX_LINK).get());
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());

        return new AddNusModsCommand(name, link);
    }
}
