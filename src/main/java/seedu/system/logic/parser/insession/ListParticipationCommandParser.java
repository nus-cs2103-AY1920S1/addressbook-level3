package seedu.system.logic.parser.insession;

import seedu.system.logic.commands.insession.ListParticipationCommand;
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

        Name compName = ParserUtil.parseName(trimmedArgs);

        return new ListParticipationCommand(compName);
    }
}
