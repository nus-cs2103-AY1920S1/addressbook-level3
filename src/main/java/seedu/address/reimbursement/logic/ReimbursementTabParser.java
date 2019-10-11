package seedu.address.reimbursement.logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import seedu.address.person.model.Model;
import seedu.address.reimbursement.commands.Command;
import seedu.address.reimbursement.commands.DeadlineCommand;
import seedu.address.reimbursement.commands.DoneCommand;
import seedu.address.reimbursement.commands.FindCommand;
import seedu.address.reimbursement.commands.SortAmountCommand;
import seedu.address.reimbursement.commands.SortDeadlineCommand;
import seedu.address.reimbursement.commands.SortNameCommand;
import seedu.address.reimbursement.logic.exception.ParseException;
import seedu.address.reimbursement.logic.parser.DeadlineCommandParser;
import seedu.address.reimbursement.logic.parser.DoneCommandParser;
import seedu.address.reimbursement.logic.parser.FindCommandParser;
import seedu.address.reimbursement.logic.parser.SortAmountCommandParser;
import seedu.address.reimbursement.logic.parser.SortDeadlineCommandParser;
import seedu.address.reimbursement.logic.parser.SortNameCommandParser;
import seedu.address.reimbursement.ui.ReimbursementMessages;

/**
 * Parser for the Reimbursement tab.
 */
public class ReimbursementTabParser {
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");


    /**
     * Routes the command to the appropriate parser.
     * @param userInput the command input by the user.
     * @param personModel the person involved in the command.
     * @return a command representing the user's desired action.
     * @throws Exception if the command syntax is incorrect.
     */
    public Command parseCommand(String userInput, Model personModel) throws Exception {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(ReimbursementMessages.MESSAGE_INVALID_COMMAND_FORMAT);
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case DeadlineCommand.COMMAND_WORD:
            return new DeadlineCommandParser().parse(arguments, personModel);
        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments, personModel);
        case DoneCommand.COMMAND_WORD:
            return new DoneCommandParser().parse(arguments, personModel);
        case SortNameCommand.COMMAND_WORD:
            return new SortNameCommandParser().parse(arguments);
        case SortAmountCommand.COMMAND_WORD:
            return new SortAmountCommandParser().parse(arguments);
        case SortDeadlineCommand.COMMAND_WORD:
            return new SortDeadlineCommandParser().parse(arguments);

        default:
            throw new ParseException(ReimbursementMessages.NO_SUCH_COMMAND);

        }
    }
}
