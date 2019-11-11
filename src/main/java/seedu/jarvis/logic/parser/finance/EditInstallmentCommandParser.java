package seedu.jarvis.logic.parser.finance;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CliSyntax.FinanceSyntax.PREFIX_DESCRIPTION;
import static seedu.jarvis.logic.parser.CliSyntax.FinanceSyntax.PREFIX_MONEY;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.finance.EditInstallmentCommand;
import seedu.jarvis.logic.commands.finance.EditInstallmentCommand.EditInstallmentDescriptor;
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

        EditInstallmentDescriptor editInstallmentDescriptor =
                new EditInstallmentDescriptor();
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            validateDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get(), editInstallmentDescriptor);
        }
        if (argMultimap.getValue(PREFIX_MONEY).isPresent()) {
            validateSubscriptionFee(argMultimap.getValue(PREFIX_MONEY).get(), editInstallmentDescriptor);
        }

        if (!editInstallmentDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditInstallmentCommand.MESSAGE_NOT_EDITED);
        }

        return new EditInstallmentCommand(index, editInstallmentDescriptor);
    }

    /**
     * Validates the new description to be edited into an existing installment.
     * @param description to be changed
     * @param descriptor of the installment to be changed
     * @throws ParseException is thrown if description is invalid
     */
    private void validateDescription(String description, EditInstallmentDescriptor descriptor) throws ParseException {
        try {
            descriptor.setDescription(FinanceParserUtil.parseInstallmentDescription(description));
        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditInstallmentCommand.MESSAGE_DESCRIPTION_ERROR));
        }
    }

    /**
     * Validates the new subscription fee to be edited into an existing installment.
     * @param money to be changed
     * @param descriptor of the installment to be changed
     * @throws ParseException is thrown if description is invalid
     */
    private void validateSubscriptionFee(String money, EditInstallmentDescriptor descriptor) throws ParseException {
        try {
            descriptor.setMoneyPaid(FinanceParserUtil.parseInstallmentMoneySpent(money));
        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditInstallmentCommand.MESSAGE_MONEY_ERROR));
        }
    }
}
