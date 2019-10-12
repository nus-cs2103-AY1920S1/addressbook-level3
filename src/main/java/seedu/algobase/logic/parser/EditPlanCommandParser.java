package seedu.algobase.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_PLAN_DESCRIPTION;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_PLAN_NAME;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_START_DATE;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.EditPlanCommand;
import seedu.algobase.logic.commands.EditPlanCommand.EditPlanDescriptor;
import seedu.algobase.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new EditPlanCommand object
 */
public class EditPlanCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the EditPlanCommand
     * and returns an EditPlanCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditPlanCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PLAN_NAME, PREFIX_PLAN_DESCRIPTION,
                        PREFIX_START_DATE, PREFIX_END_DATE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPlanCommand.MESSAGE_USAGE), pe);
        }

        EditPlanDescriptor editPlanDescriptor = new EditPlanDescriptor();
        if (argMultimap.getValue(PREFIX_PLAN_NAME).isPresent()) {
            editPlanDescriptor.setPlanName(ParserUtil.parsePlanName(argMultimap.getValue(PREFIX_PLAN_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PLAN_DESCRIPTION).isPresent()) {
            editPlanDescriptor.setPlanDescription(ParserUtil.parsePlanDescription(
                    argMultimap.getValue(PREFIX_PLAN_DESCRIPTION).get()));
        }
        if (argMultimap.getValue(PREFIX_START_DATE).isPresent()) {
            editPlanDescriptor.setStartDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_START_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_END_DATE).isPresent()) {
            editPlanDescriptor.setEndDate(
                    ParserUtil.parseDate(argMultimap.getValue(PREFIX_END_DATE).get()));
        }

        if (!editPlanDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditPlanCommand.MESSAGE_NOT_EDITED);
        }

        return new EditPlanCommand(index, editPlanDescriptor);
    }

    // TODO: edit problems in a plan

}
