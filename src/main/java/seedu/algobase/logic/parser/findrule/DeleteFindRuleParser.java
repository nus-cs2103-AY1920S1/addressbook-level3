package seedu.algobase.logic.parser.findrule;

import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.findrule.DeleteFindRuleCommand;
import seedu.algobase.logic.parser.Parser;
import seedu.algobase.logic.parser.ParserUtil;
import seedu.algobase.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteFindRuleCommand object
 */
public class DeleteFindRuleParser implements Parser<DeleteFindRuleCommand> {

    @Override
    public DeleteFindRuleCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteFindRuleCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteFindRuleCommand.MESSAGE_USAGE), pe);
        }
    }

}
