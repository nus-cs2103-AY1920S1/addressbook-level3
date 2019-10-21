package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCompCommand;
import seedu.address.logic.commands.EditCompCommand.EditCompetitionDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCompCommand object
 */
public class EditCompCommandParser implements Parser<EditCompCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCompCommand
     * and returns an EditCompCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCompCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_START_DATE, PREFIX_END_DATE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCompCommand.MESSAGE_USAGE), pe);
        }

        EditCompetitionDescriptor editCompetitionDescriptor = new EditCompetitionDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editCompetitionDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_START_DATE).isPresent()) {
            editCompetitionDescriptor.setStartDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_START_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_END_DATE).isPresent()) {
            editCompetitionDescriptor.setEndDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_END_DATE).get()));
        }

        if (!editCompetitionDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCompCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCompCommand(index, editCompetitionDescriptor);
    }
}
