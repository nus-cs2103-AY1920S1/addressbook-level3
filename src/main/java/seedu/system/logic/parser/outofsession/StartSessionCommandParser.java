package seedu.system.logic.parser.outofsession;

import static seedu.system.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.system.logic.parser.CliSyntax.PREFIX_COMP;

import seedu.system.logic.commands.outofsession.StartSessionCommand;
import seedu.system.logic.parser.ArgumentMultimap;
import seedu.system.logic.parser.ArgumentTokenizer;
import seedu.system.logic.parser.Parser;
import seedu.system.logic.parser.ParserUtil;
import seedu.system.logic.parser.exceptions.ParseException;
import seedu.system.model.person.Name;

/**
 * Parses user input and returns StartSessionCommand.
 */
public class StartSessionCommandParser implements Parser<StartSessionCommand> {
    /**
     * Parses the given string {@code userInput} into a StartSessionCommand and returns it.
     *
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    public StartSessionCommand parse(String userInput) throws ParseException {
        String trimmedInput = userInput.trim();
        if (trimmedInput.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, StartSessionCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_COMP);
        if (!ParserUtil.arePrefixesPresent(argumentMultimap, PREFIX_COMP)
                || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    StartSessionCommand.MESSAGE_USAGE));
        }

        Name compName = ParserUtil.parseName(argumentMultimap.getValue(PREFIX_COMP).get());
        return new StartSessionCommand(compName);
    }
}
