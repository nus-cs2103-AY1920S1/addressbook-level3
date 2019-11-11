package seedu.module.logic.parser.linkcommandparsers;

import static seedu.module.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.module.logic.commands.linkcommands.MarkLinkCommand;
import seedu.module.logic.parser.ArgumentMultimap;
import seedu.module.logic.parser.exceptions.ParseException;

/**
 * Parser specific to MarkLinkCommand
 */
public class MarkLinkCommandParser {
    /**
     * Parses arguments in the given ArgumentMultimap and returns a MarkLinkCommand
     * @param argMultimap
     * @return
     * @throws ParseException if the user input does not conform to the expected format
     */
    public MarkLinkCommand parse(ArgumentMultimap argMultimap, boolean mark) throws ParseException {
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String title = argMultimap.getValue(PREFIX_NAME).get();
            return new MarkLinkCommand(title, mark);
        } else {
            throw new ParseException("No mark target specified");
        }
    }

}
