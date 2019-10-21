package budgetbuddy.logic.parser.commandparsers.loancommandparsers;

import static budgetbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DATE;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static java.util.Objects.requireNonNull;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.loancommands.LoanEditCommand;
import budgetbuddy.logic.commands.loancommands.LoanEditCommand.LoanEditDescriptor;
import budgetbuddy.logic.parser.ArgumentMultimap;
import budgetbuddy.logic.parser.ArgumentTokenizer;
import budgetbuddy.logic.parser.CommandParser;
import budgetbuddy.logic.parser.CommandParserUtil;
import budgetbuddy.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new LoanEditCommand object.
 */
public class LoanEditCommandParser implements CommandParser<LoanEditCommand> {
    @Override
    public String name() {
        return LoanEditCommand.COMMAND_WORD;
    }

    @Override
    public LoanEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultiMap =
                ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT, PREFIX_DESCRIPTION, PREFIX_DATE);

        Index personIndex;
        Index loanIndex;
        try {
            String[] personLoanIndicesArr = argMultiMap.getPreamble().split("\\.");
            personIndex = CommandParserUtil.parseIndex(personLoanIndicesArr[0]);
            loanIndex = CommandParserUtil.parseIndex(personLoanIndicesArr[1]);
        } catch (ParseException | IndexOutOfBoundsException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoanEditCommand.MESSAGE_USAGE), e);
        }

        LoanEditDescriptor loanEditDescriptor = new LoanEditDescriptor();
        if (argMultiMap.getValue(PREFIX_AMOUNT).isPresent()) {
            loanEditDescriptor.setAmount(
                    CommandParserUtil.parseAmount(argMultiMap.getValue(PREFIX_AMOUNT).get()));
        }
        if (argMultiMap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            loanEditDescriptor.setDescription(
                    CommandParserUtil.parseDescription(argMultiMap.getValue(PREFIX_DESCRIPTION).get()));
        }
        if (argMultiMap.getValue(PREFIX_DATE).isPresent()) {
            loanEditDescriptor.setDate(
                    CommandParserUtil.parseDate(argMultiMap.getValue(PREFIX_DATE).get()));
        }

        if (!loanEditDescriptor.isAnyFieldEdited()) {
            throw new ParseException(LoanEditCommand.MESSAGE_UNEDITED);
        }

        return new LoanEditCommand(personIndex, loanIndex, loanEditDescriptor);
    }
}
