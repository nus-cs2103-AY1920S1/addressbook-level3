package seedu.moolah.logic.parser.budget;

import static java.util.Objects.requireNonNull;
import static seedu.moolah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_PERIOD;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.util.Collections;
import java.util.List;

import seedu.moolah.commons.core.index.Index;
import seedu.moolah.logic.commands.budget.EditBudgetCommand;
import seedu.moolah.logic.commands.budget.EditBudgetCommand.EditBudgetDescriptor;
import seedu.moolah.logic.parser.ArgumentMultimap;
import seedu.moolah.logic.parser.ArgumentTokenizer;
import seedu.moolah.logic.parser.Parser;
import seedu.moolah.logic.parser.ParserUtil;
import seedu.moolah.logic.parser.Prefix;
import seedu.moolah.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditBudgetCommand object.
 */
public class EditBudgetCommandParser implements Parser<EditBudgetCommand> {

    public static final List<Prefix> REQUIRED_PREFIXES = Collections.unmodifiableList(List.of());
    public static final List<Prefix> OPTIONAL_PREFIXES = Collections.unmodifiableList(List.of(
            PREFIX_DESCRIPTION, PREFIX_PRICE, PREFIX_START_DATE, PREFIX_PERIOD
    ));

    /**
     * Parses the given {@code String} of arguments in the context of the EditBudgetCommand
     * and returns an EditBudgetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditBudgetCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_PRICE, PREFIX_START_DATE, PREFIX_PERIOD);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditBudgetCommand.MESSAGE_USAGE), pe);
        }

        EditBudgetDescriptor editBudgetDescriptor = new EditBudgetDescriptor();
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editBudgetDescriptor.setDescription(
                    ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }
        if (argMultimap.getValue(PREFIX_PRICE).isPresent()) {
            editBudgetDescriptor.setAmount(
                    ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE).get()));
        }

        if (argMultimap.getValue(PREFIX_START_DATE).isPresent()) {
            editBudgetDescriptor.setStartDate(
                    ParserUtil.parseTimestamp(argMultimap.getValue(PREFIX_START_DATE).get()));
        }

        if (argMultimap.getValue(PREFIX_PERIOD).isPresent()) {
            editBudgetDescriptor.setPeriod(
                    ParserUtil.parsePeriod(argMultimap.getValue(PREFIX_PERIOD).get()));
        }

        if (!editBudgetDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditBudgetCommand.MESSAGE_NOT_EDITED);
        }

        return new EditBudgetCommand(index, editBudgetDescriptor);
    }
}
