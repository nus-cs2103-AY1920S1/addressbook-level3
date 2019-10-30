package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALLER_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISTRICT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditIncidentCommand;
import seedu.address.logic.commands.EditIncidentCommand.EditIncident;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditIncidentCommand object
 */
public class EditIncidentCommandParser implements Parser<EditIncidentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditIncidentCommand
     * and returns an EditIncidentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditIncidentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CALLER_NUMBER, PREFIX_DISTRICT, PREFIX_DESCRIPTION);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditIncidentCommand.MESSAGE_USAGE), pe);
        }

        EditIncident editIncident = new EditIncident();

        if (argMultimap.getValue(PREFIX_CALLER_NUMBER).isPresent()) {
            editIncident.setCaller(ParserUtil.parseCallerNumber(argMultimap.getValue(PREFIX_CALLER_NUMBER).get()));
        }
        if (argMultimap.getValue(PREFIX_DISTRICT).isPresent()) {
            editIncident.setDistrict(ParserUtil.parseDistrict(argMultimap.getValue(PREFIX_DISTRICT).get()));
        }
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editIncident.setDesc(ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }


        return new EditIncidentCommand(index, editIncident);
    }
}
