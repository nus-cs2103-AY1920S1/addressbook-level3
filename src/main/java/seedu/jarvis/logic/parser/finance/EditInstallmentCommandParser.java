package seedu.jarvis.logic.parser.finance;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CliSyntax.FinanceSyntax.PREFIX_DESCRIPTION;
import static seedu.jarvis.logic.parser.CliSyntax.FinanceSyntax.PREFIX_MONEY;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.finance.EditInstallmentCommand;
import seedu.jarvis.logic.parser.ArgumentMultimap;
import seedu.jarvis.logic.parser.ArgumentTokenizer;
import seedu.jarvis.logic.parser.Parser;
import seedu.jarvis.logic.parser.ParserUtil;
import seedu.jarvis.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditInstallmentCommand object
 */
public class EditInstallmentCommandParser implements Parser<EditInstallmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditInstallmentCommand
     * and returns an EditInstallmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public EditInstallmentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_MONEY);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditInstallmentCommand.MESSAGE_USAGE), pe);
        }

        EditInstallmentCommand.EditInstallmentDescriptor editInstallmentDescriptor =
                new EditInstallmentCommand.EditInstallmentDescriptor();
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editInstallmentDescriptor
                    .setDescription(FinanceParserUtil
                            .parseInstallmentDescription(argMultimap
                                    .getValue(PREFIX_DESCRIPTION)
                                    .get()));
        }
        if (argMultimap.getValue(PREFIX_MONEY).isPresent()) {
            editInstallmentDescriptor
                    .setMoneyPaid(FinanceParserUtil
                            .parseInstallmentMoneySpent(argMultimap
                                    .getValue(PREFIX_MONEY)
                                    .get()));
        }

        if (!editInstallmentDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditInstallmentCommand.MESSAGE_NOT_EDITED);
        }

        return new EditInstallmentCommand(index, editInstallmentDescriptor);
    }
}
