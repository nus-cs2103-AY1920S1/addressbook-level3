package seedu.address.cashier.logic.parser;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.cashier.logic.commands.CheckoutCommand;
import seedu.address.cashier.logic.commands.ClearCommand;
import seedu.address.cashier.logic.commands.Command;
import seedu.address.cashier.logic.commands.DeleteCommand;
import seedu.address.cashier.logic.commands.EditCommand;
import seedu.address.cashier.logic.commands.SetCashierCommand;
import seedu.address.cashier.logic.parser.exception.ParseException;
import seedu.address.cashier.model.Model;
import seedu.address.cashier.ui.CashierMessages;
import seedu.address.person.commons.core.LogsCenter;
import seedu.address.person.logic.commands.AddCommand;

/**
 * Parses user input in the cashier tab.
 */
public class CashierTabParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @param model which the commands operate on
     * @param personModel which the commands use to find a person
     * @return the command based on the user input
     * @throws Exception if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput, Model model,
                                seedu.address.person.model.Model personModel) throws Exception {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(CashierMessages.MESSAGE_INVALID_ADDCOMMAND_FORMAT);
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments, model, personModel);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments, model, personModel);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments, model, personModel);

        case SetCashierCommand.COMMAND_WORD:
            return new SetCashierCommandParser().parse(arguments, model, personModel);

        case CheckoutCommand.COMMAND_WORD:
            return new CheckoutCommandParser().parse(arguments, model, personModel);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommandParser().parse(arguments, model, personModel);

        default:
            logger.info("There is no such command.");
            throw new ParseException(CashierMessages.NO_SUCH_COMMAND);

        }
    }
}
