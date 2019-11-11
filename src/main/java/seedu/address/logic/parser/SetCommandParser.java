package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FINE_INCREMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOAN_PERIOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_RENEWS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENEW_PERIOD;

import seedu.address.logic.commands.SetCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SetCommand object
 */
public class SetCommandParser implements Parser<SetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetCommand
     * and returns an SetCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_LOAN_PERIOD, PREFIX_RENEW_PERIOD,
                PREFIX_FINE_INCREMENT, PREFIX_MAX_RENEWS);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCommand.MESSAGE_USAGE));
        }

        SetCommand.SetUserSettingsDescriptor setUserSettingsDescriptor = new SetCommand.SetUserSettingsDescriptor();

        if (argMultimap.getValue(PREFIX_LOAN_PERIOD).isPresent()) {
            setUserSettingsDescriptor.setLoanPeriod(ParserUtil
                    .parseLoanPeriod(argMultimap.getValue(PREFIX_LOAN_PERIOD).get()));
        }

        if (argMultimap.getValue(PREFIX_RENEW_PERIOD).isPresent()) {
            setUserSettingsDescriptor.setRenewPeriod(ParserUtil
                    .parseRenewPeriod(argMultimap.getValue(PREFIX_RENEW_PERIOD).get()));
        }

        if (argMultimap.getValue(PREFIX_FINE_INCREMENT).isPresent()) {
            setUserSettingsDescriptor.setFineIncrement(ParserUtil
                    .parseFineIncrement(argMultimap.getValue(PREFIX_FINE_INCREMENT).get()));
        }

        if (argMultimap.getValue(PREFIX_MAX_RENEWS).isPresent()) {
            setUserSettingsDescriptor.setMaxRenews(ParserUtil
                    .parseMaxRenews(argMultimap.getValue(PREFIX_MAX_RENEWS).get()));
        }

        if (!setUserSettingsDescriptor.isAnyFieldEdited()) {
            throw new ParseException(SetCommand.MESSAGE_NOT_EDITED);
        }

        return new SetCommand(setUserSettingsDescriptor);
    }

}

