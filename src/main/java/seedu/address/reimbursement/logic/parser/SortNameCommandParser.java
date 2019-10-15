package seedu.address.reimbursement.logic.parser;

import java.util.stream.Stream;

import seedu.address.reimbursement.commands.SortNameCommand;
import seedu.address.reimbursement.logic.exception.ParseException;
import seedu.address.reimbursement.ui.ReimbursementMessages;

/**
 * Parser for command to sort by name.
 */
public class SortNameCommandParser implements SortParser<SortNameCommand> {
    private static boolean arePrefixesPresent(ArgumentMultimap argMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the command to sort by name.
     *
     * @param args the given command.
     * @return a command representing the user's desired action
     * @throws Exception if the command syntax is incorrect.
     */
    public SortNameCommand parse(String args)
            throws Exception {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args);

        if (!arePrefixesPresent(argMultimap)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(ReimbursementMessages.MESSAGE_INVALID_SORTNAMECOMMAND_FORMAT);
        }
        SortNameCommand sortNameCommand = new SortNameCommand();
        return sortNameCommand;
    }
}
