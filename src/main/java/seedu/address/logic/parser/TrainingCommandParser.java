package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.LinkedList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TrainingCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new TrainingCommand object
 */
public class TrainingCommandParser implements Parser<TrainingCommand> {
    @Override
    public TrainingCommand parse(String userInput) throws ParseException {
        try {
            List<Index> indexList = new LinkedList<>();
            String[] indexes = userInput.trim().split("\\s+");
            for (String arguments: indexes) {
                Index index = ParserUtil.parseIndex(arguments);
                indexList.add(index);
            }
            return new TrainingCommand(indexList);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TrainingCommand.MESSAGE_USAGE), pe);
        }
    }
}
