package seedu.module.logic.parser.linkcommandparsers;

import static seedu.module.logic.parser.CliSyntax.PREFIX_LINK;
import static seedu.module.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.module.logic.commands.linkcommands.AddLinkCommand;
import seedu.module.logic.parser.ArgumentMultimap;
import seedu.module.logic.parser.ParserUtil;
import seedu.module.logic.parser.exceptions.ParseException;
import seedu.module.model.module.Link;

/**
 * Parser specific AddLinkCommand
 */
public class AddLinkCommandParser {
    /**
     * Parses arguments in the given ArgumentMultimap and returns a AddLinkCommand
     * @param argMultimap
     * @return
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddLinkCommand parse(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_LINK).isPresent() && argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String title = argMultimap.getValue(PREFIX_NAME).get();
            String link = ParserUtil.checkScheme(argMultimap.getValue(PREFIX_LINK).get());
            if (!ParserUtil.isValidUrl(link)) {
                throw new ParseException("Not a valid URL");
            }
            Link addedLink = new Link(title.trim(), link.trim());
            return new AddLinkCommand(addedLink);
        } else {
            throw new ParseException("Missing link title or link url");
        }
    }
}
