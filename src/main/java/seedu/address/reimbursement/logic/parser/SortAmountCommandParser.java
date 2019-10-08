package seedu.address.reimbursement.logic.parser;

import java.util.stream.Stream;

import seedu.address.reimbursement.commands.SortAmountCommand;
import seedu.address.reimbursement.logic.exception.ParseException;
import seedu.address.reimbursement.ui.ReimbursementMessages;

public class SortAmountCommandParser implements SortParser<SortAmountCommand> {
    private static boolean arePrefixesPresent(ArgumentMultimap argMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argMultimap.getValue(prefix).isPresent());
    }

    public SortAmountCommand parse(String args)
            throws Exception {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args);

        if (!arePrefixesPresent(argMultimap)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(ReimbursementMessages.MESSAGE_INVALID_SORTAMOUNTCOMMAND_FORMAT);
        }
        SortAmountCommand sortAmountCommand = new SortAmountCommand();
        return sortAmountCommand;
    }
}
