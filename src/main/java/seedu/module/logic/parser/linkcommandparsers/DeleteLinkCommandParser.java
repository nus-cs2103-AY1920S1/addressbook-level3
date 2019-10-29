package seedu.module.logic.parser.linkcommandparsers;

import static seedu.module.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.module.logic.commands.linkcommands.DeleteLinkCommand;
import seedu.module.logic.parser.ArgumentMultimap;
import seedu.module.logic.parser.exceptions.ParseException;
import seedu.module.model.module.Link;

/**
 * Parser specific to DeleteLinkCommand
 */
public class DeleteLinkCommandParser {
    /**
     * Parses arguments in the given ArgumentMultimap and returns a DeleteLinkCommand
     * @param argMultimap
     * @return
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DeleteLinkCommand parse(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String title = argMultimap.getValue(PREFIX_NAME).get();
            return new DeleteLinkCommand(title);
        } else {
            throw new ParseException(Link.MESSAGE_CONSTRAINTS);
        }
    }

}
