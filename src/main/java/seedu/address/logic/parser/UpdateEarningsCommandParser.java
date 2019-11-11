package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASSID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UpdateEarningsCommand;
import seedu.address.logic.commands.UpdateEarningsCommand.EditEarningsDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.earnings.Amount;
import seedu.address.model.earnings.Earnings;

/**
 * Parses input arguments and creates a new UpdateEarningsCommandParser object
 */
public class UpdateEarningsCommandParser implements Parser<UpdateEarningsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UpdateEarningsCommandParser
     * and returns an UpdateEarningsCommandParser object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdateEarningsCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_CLASSID, PREFIX_AMOUNT, PREFIX_TYPE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UpdateEarningsCommand.MESSAGE_USAGE), pe);
        }

        EditEarningsDescriptor editEarningsDescriptor = new EditEarningsDescriptor();
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editEarningsDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_CLASSID).isPresent()) {
            editEarningsDescriptor.setClassId(ParserUtil.parseClassId(argMultimap.getValue(PREFIX_CLASSID).get()));
        }
        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            String amt = argMultimap.getValue(PREFIX_AMOUNT).get();
            Amount amount = ParserUtil.parseAmount(amt);
            editEarningsDescriptor.setAmount(amount);
            if (Amount.moreThanMaxValue(amt)) {
                throw new ParseException(Amount.MESSAGE_CONSTRAINTS);
            }
            Earnings.replacePreviousEarnings(index, amount);
        }
        if (argMultimap.getValue(PREFIX_TYPE).isPresent()) {
            editEarningsDescriptor.setType(ParserUtil.parseType(argMultimap.getValue(PREFIX_TYPE).get()));
        }

        if (!editEarningsDescriptor.isAnyFieldEdited()) {
            throw new ParseException(UpdateEarningsCommand.MESSAGE_NOT_EDITED);
        }

        return new UpdateEarningsCommand(index, editEarningsDescriptor);
    }

}
