package seedu.system.logic.parser.outofsession;

import static java.util.Objects.requireNonNull;
import static seedu.system.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.system.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.system.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.system.logic.parser.CliSyntax.PREFIX_START_DATE;

import seedu.system.commons.core.index.Index;

import seedu.system.logic.commands.outofsession.EditCompetitionCommand;
import seedu.system.logic.commands.outofsession.EditCompetitionCommand.EditCompetitionDescriptor;
import seedu.system.logic.parser.ArgumentMultimap;
import seedu.system.logic.parser.ArgumentTokenizer;
import seedu.system.logic.parser.Parser;
import seedu.system.logic.parser.ParserUtil;
import seedu.system.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCompetitionCommand object.
 */
public class EditCompetitionCommandParser implements Parser<EditCompetitionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCompetitionCommand
     * and returns an EditCompetitionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCompetitionCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_START_DATE, PREFIX_END_DATE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {

            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditCompetitionCommand.MESSAGE_USAGE), pe);
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

            throw new ParseException(EditCompetitionCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCompetitionCommand(index, editCompetitionDescriptor);
    }
}
