package seedu.module.logic.parser;

import static seedu.module.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.module.logic.parser.CliSyntax.PREFIX_ACTION;
import static seedu.module.logic.parser.CliSyntax.PREFIX_LINK;
import static seedu.module.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.module.logic.commands.linkcommands.LinkCommand;
import seedu.module.logic.parser.exceptions.ParseException;
import seedu.module.logic.parser.linkcommandparsers.AddLinkCommandParser;
import seedu.module.logic.parser.linkcommandparsers.LaunchLinkCommandParser;


/**
 * Parses input arguments and creates a new LinkCommand object
 */
public class LinkCommandParser implements Parser<LinkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LinkCommand
     * and returns a LinkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LinkCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE));
        }
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ACTION, PREFIX_LINK, PREFIX_NAME);
        try {
            if (!argMultimap.getValue(PREFIX_ACTION).isPresent()) {
                throw new ParseException("Input format error. a/ not found");
            }
            if (argMultimap.getValue(PREFIX_ACTION).get().equals("add")) {
                return new AddLinkCommandParser().parse(argMultimap);
            } else if (argMultimap.getValue(PREFIX_ACTION).get().equals("go")) {
                return new LaunchLinkCommandParser().parse(argMultimap);
            } else {
                throw new ParseException("Command not recognized");
            }
        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE), e);
        }
    }

}
