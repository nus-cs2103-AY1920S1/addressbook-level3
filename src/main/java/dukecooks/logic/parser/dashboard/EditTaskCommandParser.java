package dukecooks.logic.parser.dashboard;

import static java.util.Objects.requireNonNull;

import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.dashboard.EditTaskCommand;
import dukecooks.logic.commands.dashboard.EditTaskCommand.EditTaskDescriptor;
import dukecooks.logic.parser.ArgumentMultimap;
import dukecooks.logic.parser.ArgumentTokenizer;
import dukecooks.logic.parser.CliSyntax;
import dukecooks.logic.parser.Parser;
import dukecooks.logic.parser.ParserUtil;
import dukecooks.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditRecipeCommand object
 */
public class EditTaskCommandParser implements Parser<EditTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditTaskCommand
     * and returns an EditTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_TASKNAME, CliSyntax.PREFIX_TASKDATE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    EditTaskCommand.MESSAGE_USAGE), pe);
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        if (argMultimap.getValue(CliSyntax.PREFIX_TASKNAME).isPresent()) {
            editTaskDescriptor.setDashboardName(ParserUtil.parseDashboardName
                    (argMultimap.getValue(CliSyntax.PREFIX_TASKNAME).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_TASKDATE).isPresent()) {
            editTaskDescriptor.setTaskDate(ParserUtil.parseTaskDate(argMultimap.getValue(CliSyntax
                    .PREFIX_TASKDATE).get()));
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditTaskCommand.MESSAGE_NOT_EDITED);
        }

        return new EditTaskCommand(index, editTaskDescriptor);
    }

}
