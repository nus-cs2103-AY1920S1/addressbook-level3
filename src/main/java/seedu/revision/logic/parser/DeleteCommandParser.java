package seedu.revision.logic.parser;

import static seedu.revision.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.revision.commons.core.index.Index;
import seedu.revision.logic.commands.DeleteCommand;
import seedu.revision.logic.parser.exceptions.ParseException;

import java.util.ArrayList;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    private ArrayList<Index> indexArray;

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        try {
            indexArray = new ArrayList<>();
            String[] rawIndices = args.strip().split(" ");
            for (String index : rawIndices) {
                indexArray.add(ParserUtil.parseIndex(index.strip()));
            }
            return new DeleteCommand(indexArray);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}
