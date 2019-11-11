package seedu.address.reimbursement.logic.parser;

import java.util.stream.Stream;

import seedu.address.reimbursement.logic.commands.BackCommand;
import seedu.address.reimbursement.logic.parser.exception.ParseException;
import seedu.address.reimbursement.ui.ReimbursementMessages;
import seedu.address.util.ArgumentMultimap;
import seedu.address.util.ArgumentTokenizer;
import seedu.address.util.Prefix;

/**
 * Parses List commands.
 */
public class BackCommandParser implements IndependentCommandParser<BackCommand> {
    private static boolean arePrefixesPresent(ArgumentMultimap argMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the command to sort by deadline.
     *
     * @param args the given command.
     * @return a command representing the user's desired action
     * @throws Exception if the command syntax is incorrect.
     */
    public BackCommand parse(String args)
            throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args);

        if (!arePrefixesPresent(argMultimap)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(ReimbursementMessages.MESSAGE_INVALID_BACKCOMMAND_FORMAT);
        }
        BackCommand backCommand = new BackCommand();
        return backCommand;
    }
}

