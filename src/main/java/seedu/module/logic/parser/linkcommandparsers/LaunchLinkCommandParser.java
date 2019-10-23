package seedu.module.logic.parser.linkcommandparsers;

import static seedu.module.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.module.commons.core.index.Index;
import seedu.module.logic.commands.linkcommands.LaunchLinkCommand;
import seedu.module.logic.parser.ArgumentMultimap;
import seedu.module.logic.parser.ParserUtil;
import seedu.module.logic.parser.exceptions.ParseException;
import seedu.module.model.module.Link;

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
        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String title = argMultimap.getValue(PREFIX_NAME).get();
            return new LaunchLinkCommand(index, title);
        } else {
            throw new ParseException(Link.MESSAGE_CONSTRAINTS);
        }
    }
}
