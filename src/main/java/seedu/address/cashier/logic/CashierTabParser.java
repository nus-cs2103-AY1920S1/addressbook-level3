package seedu.address.cashier.logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.cashier.commands.CheckoutCommand;
import seedu.address.cashier.commands.ClearCommand;
import seedu.address.cashier.commands.Command;
import seedu.address.cashier.commands.DeleteCommand;
import seedu.address.cashier.commands.EditCommand;
import seedu.address.cashier.commands.SetCashierCommand;
import seedu.address.cashier.logic.exception.ParseException;
import seedu.address.cashier.model.ModelManager;
import seedu.address.cashier.ui.CashierMessages;

import seedu.address.person.logic.commands.AddCommand;
import seedu.address.person.model.Model;

/**
 * Parses user input in the cashier tab.
 */
public class CashierTabParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @param modelManager which the commands operate on
     * @param personModel which the commands use to find a person
     * @return the command based on the user input
     * @throws Exception if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput, ModelManager modelManager,
                                Model personModel) throws Exception {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(CashierMessages.MESSAGE_INVALID_ADDCOMMAND_FORMAT);
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments, modelManager);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments, modelManager);

        case SetCashierCommand.COMMAND_WORD:
            return new SetCashierCommandParser().parse(arguments, modelManager, personModel);

        case CheckoutCommand.COMMAND_WORD:
            return new CheckoutCommandParser().parse(arguments, modelManager);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommandParser().parse();

        default:
            throw new ParseException(CashierMessages.NO_SUCH_COMMAND);

        }
    }
}
