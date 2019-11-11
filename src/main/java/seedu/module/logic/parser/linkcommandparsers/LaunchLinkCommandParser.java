package seedu.module.logic.parser.linkcommandparsers;

import static seedu.module.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.module.logic.commands.linkcommands.LaunchLinkCommand;
import seedu.module.logic.parser.ArgumentMultimap;
import seedu.module.logic.parser.exceptions.ParseException;

/**
 * Parser specific to LaunchLinkCommand
 */
public class LaunchLinkCommandParser {
    /**
     * Parses arguments in the given ArgumentMultimap and returns a LaunchLinkCommand
     * @param argMultimap
     * @return
     * @throws ParseException if the user input does not conform to the expected format
     */
    public LaunchLinkCommand parse(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String title = argMultimap.getValue(PREFIX_NAME).get();
            return new LaunchLinkCommand(title);
        } else {
            throw new ParseException("No launch target specified");
        }
    }
}
