package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.AddNusModsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.NusModsShareLink;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new AddNusModsCommand object.
 */
public class AddNusModsCommandParser implements Parser<AddNusModsCommand> {
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

        if (!Parser.arePrefixesPresent(argMultimap, PREFIX_LINK)
                || Parser.areMultiplePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_LINK)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddNusModsCommand.MESSAGE_USAGE));
        }

        Name name;
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        } else {
            name = null;
        }

        NusModsShareLink link = ParserUtil.parseNusModsLink(argMultimap.getValue(PREFIX_LINK).get());

        return new AddNusModsCommand(name, link);
    }
}
