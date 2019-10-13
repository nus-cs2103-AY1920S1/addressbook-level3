package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FLAG;

import seedu.address.logic.commands.ListBodyCommand;
import seedu.address.logic.commands.ListCommand;
//import seedu.address.logic.commands.ListFridgeCommand;
import seedu.address.logic.commands.ListWorkerCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author bernicechio
/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns an ListCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FLAG);
        String flag = argMultimap.getValue(PREFIX_FLAG).orElse("");
        switch (flag) {
        case "w":
            return new ListWorkerCommand();
        case "b":
            return new ListBodyCommand();
        //case "f":
            //return new ListFridgeCommand();
        default:
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
    }
}
