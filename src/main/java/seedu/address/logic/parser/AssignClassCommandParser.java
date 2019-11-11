package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASSID;

import java.util.ArrayList;

import seedu.address.commons.core.index.Index;

import seedu.address.logic.commands.AssignClassCommand;
import seedu.address.logic.commands.util.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new AssignClassCommandParser object
 */
public class AssignClassCommandParser implements Parser<AssignClassCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AssignClassCommand
     * and returns an AssignClassCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AssignClassCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CLASSID);

        ArrayList<Index> indexes = new ArrayList<>();

        try {
            String allIndexes = argMultimap.getPreamble();
            String[] splitIndex = allIndexes.split(",");
            for (String index : splitIndex) {
                assert Integer.parseInt(index.trim()) > 0 : "the index should be greater than 0";
                Index parsedIndex = ParserUtil.parseIndex(index);
                indexes.add(parsedIndex);
            }
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AssignClassCommand.MESSAGE_USAGE), pe);
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        if (argMultimap.getValue(PREFIX_CLASSID).isPresent()) {
            editPersonDescriptor.setClassId(ParserUtil.parseClassId(argMultimap.getValue(PREFIX_CLASSID).get()));
        } else {
            throw new ParseException(AssignClassCommand.MESSAGE_NOT_EDITED);
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(AssignClassCommand.MESSAGE_NOT_EDITED);
        }

        return new AssignClassCommand(indexes, editPersonDescriptor);
    }

}
