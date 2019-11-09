package seedu.system.logic.parser.outofsession;

import static seedu.system.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.system.logic.parser.CliSyntax.PREFIX_COMP;

import seedu.system.logic.commands.outofsession.ListParticipationCommand;
import seedu.system.logic.parser.ArgumentMultimap;
import seedu.system.logic.parser.ArgumentTokenizer;
import seedu.system.logic.parser.Parser;
import seedu.system.logic.parser.ParserUtil;
import seedu.system.logic.parser.exceptions.ParseException;
import seedu.system.model.person.Name;

/**
 * Parses argument input and creates new ListParticipationCommand.
 */
public class ListParticipationCommandParser implements Parser<ListParticipationCommand> {

    /**
     * Parses the given String {@code userInput} into a ListParticipationCommand and returns it for execution.
     *
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    public ListParticipationCommand parse(String userInput) throws ParseException {
        String trimmedArgs = userInput.trim();
        if (trimmedArgs.isEmpty()) {
            return new ListParticipationCommand();
        }

        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_COMP);
        if (!ParserUtil.arePrefixesPresent(argumentMultimap, PREFIX_COMP)
                || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListParticipationCommand.MESSAGE_USAGE));
        }
        Name compName = ParserUtil.parseName(argumentMultimap.getValue(PREFIX_COMP).get());

        return new ListParticipationCommand(compName);
    }
}
