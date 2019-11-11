package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.EditAssignmentCommand.EditAssignmentDescriptor;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditAssignmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditAssignmentCommand object
 */
public class EditAssignmentCommandParser implements Parser<EditAssignmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditAssignmentCommand
     * and returns an EditAssignmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditAssignmentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ASSIGNMENT, PREFIX_DEADLINE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditAssignmentCommand.MESSAGE_USAGE), pe);
        }

        EditAssignmentDescriptor editAssignmentDescriptor = new EditAssignmentDescriptor();
        if (argMultimap.getValue(PREFIX_ASSIGNMENT).isPresent()) {
            editAssignmentDescriptor.setAssignmentName(ParserUtil.parseAssignmentName(argMultimap
                    .getValue(PREFIX_ASSIGNMENT).get()));
        }
        if (argMultimap.getValue(PREFIX_DEADLINE).isPresent()) {
            editAssignmentDescriptor.setAssignmentDeadline(ParserUtil.parseAssignmentDeadline(argMultimap
                    .getValue(PREFIX_DEADLINE).get()));
        }
        if (!editAssignmentDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditAssignmentCommand.MESSAGE_NOT_EDITED);
        }

        return new EditAssignmentCommand(index, editAssignmentDescriptor);
    }

}
