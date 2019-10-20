package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRIVER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOODS;

import seedu.address.logic.commands.EditTaskCommand;
import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditTaskCommand object
 */
public class EditTaskCommandParser implements Parser<EditTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditTaskCommand
     * and returns an EditTaskCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GOODS, PREFIX_CUSTOMER,
                                            PREFIX_DATETIME, PREFIX_DRIVER, PREFIX_DURATION);

        int id;

        try {
            id = ParserUtil.parseId(argMultimap.getPreamble());
        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE));
        }

        //Create a temp task to track what fields needs to be edited
        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();

        //set description into temp task if any
        if (argMultimap.getValue(PREFIX_GOODS).isPresent()) {
            editTaskDescriptor.setDescription(ParserUtil.parseDescription(argMultimap.getValue(PREFIX_GOODS).get()));
        }

        //set Customer into temp task if any
        if (argMultimap.getValue(PREFIX_CUSTOMER).isPresent()) {
            editTaskDescriptor.setCustomer(ParserUtil.parseId(argMultimap.getValue(PREFIX_CUSTOMER).get()));
        }

        //set date for delivery into temp task if any
        if (argMultimap.getValue(PREFIX_DATETIME).isPresent()) {
            editTaskDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATETIME).get()));
        }

        //set Driver into temp task if any
        if (argMultimap.getValue(PREFIX_DRIVER).isPresent()) {
            editTaskDescriptor.setDriver(ParserUtil.parseId(argMultimap.getValue(PREFIX_DRIVER).get()));
        }

        //set duration of delivery task into temp task if any
        if (argMultimap.getValue(PREFIX_DURATION).isPresent()) {
            editTaskDescriptor.setEventTime(ParserUtil.parseEventTime(argMultimap.getValue(PREFIX_DURATION).get()));
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditTaskCommand.MESSAGE_NOT_EDITED + "\n" + EditTaskCommand.MESSAGE_USAGE);
        }

        return new EditTaskCommand(id, editTaskDescriptor);
    }
}
