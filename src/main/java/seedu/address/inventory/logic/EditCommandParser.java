package seedu.address.inventory.logic;

import seedu.address.inventory.commands.EditCommand;
import seedu.address.inventory.logic.exception.ParseException;
import seedu.address.transaction.ui.TransactionUi;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static seedu.address.transaction.logic.CliSyntax.*;

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
            throw new ParseException(TransactionUi.MESSAGE_INVALID_COMMAND_FORMAT);
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
            throw new ParseException(TransactionUi.MESSAGE_NOT_EDITED);
        }
        return new EditCommand(index, editPersonDescriptor);
    }
}
