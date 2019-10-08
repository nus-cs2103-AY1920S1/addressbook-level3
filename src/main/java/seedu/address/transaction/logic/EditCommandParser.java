package seedu.address.transaction.logic;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import seedu.address.transaction.commands.EditCommand;
import seedu.address.transaction.logic.exception.ParseException;
import seedu.address.transaction.ui.TransactionMessages;

import static seedu.address.transaction.logic.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.transaction.logic.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.transaction.logic.CliSyntax.PREFIX_DATETIME;
import static seedu.address.transaction.logic.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.transaction.logic.CliSyntax.PREFIX_PERSON;

public class EditCommandParser {
    private final DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);

    public EditCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATETIME, PREFIX_DESCRIPTION,
                        PREFIX_CATEGORY, PREFIX_AMOUNT, PREFIX_PERSON);

        int index;

        try {
            index = Integer.parseInt(argMultimap.getPreamble());
        } catch (Exception pe) {
            throw new ParseException(TransactionMessages.MESSAGE_INVALID_EDIT_COMMAND_FORMAT);
        }

        EditCommand.EditTransactionDescriptor editPersonDescriptor = new EditCommand.EditTransactionDescriptor();
        if (argMultimap.getValue(PREFIX_DATETIME).isPresent()) {
            editPersonDescriptor.setDate(argMultimap.getValue(PREFIX_DATETIME).get());
        }
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editPersonDescriptor.setDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        }
        if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()) {
            editPersonDescriptor.setCategory(argMultimap.getValue(PREFIX_CATEGORY).get());
        }
        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            editPersonDescriptor.setAmount(Double.parseDouble(argMultimap.getValue(PREFIX_AMOUNT).get()));
        }
        if (argMultimap.getValue(PREFIX_PERSON).isPresent()) {
            editPersonDescriptor.setName(argMultimap.getValue(PREFIX_PERSON).get());
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(TransactionMessages.MESSAGE_NOT_EDITED);
        }
        return new EditCommand(index, editPersonDescriptor);
    }
}
