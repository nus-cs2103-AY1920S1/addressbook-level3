package seedu.module.logic.parser.linkcommandparsers;

import static seedu.module.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.module.logic.parser.CliSyntax.PREFIX_NEW_LINK;
import static seedu.module.logic.parser.CliSyntax.PREFIX_NEW_NAME;

import java.util.Optional;

import seedu.module.logic.commands.linkcommands.EditLinkCommand;
import seedu.module.logic.parser.ArgumentMultimap;
import seedu.module.logic.parser.exceptions.ParseException;
import seedu.module.model.module.Link;

/**
 * Parser specific to EditLinkCommand
 */
public class EditLinkCommandParser {
    /**
     * Parses arguments in the given ArgumentMultimap and returns a EditLinkCommand
     * @param argMultimap
     * @return
     * @throws ParseException if the user input does not conform to the expected format
     */
    public EditLinkCommand parse(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String title = argMultimap.getValue(PREFIX_NAME).get();
            Optional<String> newTitle = argMultimap.getValue(PREFIX_NEW_NAME);
            Optional<String> newLink = argMultimap.getValue(PREFIX_NEW_LINK);
            return new EditLinkCommand(title, newTitle, newLink);
        } else {
            throw new ParseException(Link.MESSAGE_CONSTRAINTS);
        }
    }

}
