package seedu.address.reimbursement.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.person.model.Model;
import seedu.address.reimbursement.logic.commands.BackCommand;
import seedu.address.reimbursement.logic.commands.Command;
import seedu.address.reimbursement.logic.commands.DeadlineCommand;
import seedu.address.reimbursement.logic.commands.DoneCommand;
import seedu.address.reimbursement.logic.commands.ExitCommand;
import seedu.address.reimbursement.logic.commands.FindCommand;
import seedu.address.reimbursement.logic.commands.SortCommand;
import seedu.address.reimbursement.logic.parser.exception.ParseException;
import seedu.address.reimbursement.model.exception.NoSuchPersonReimbursementException;
import seedu.address.reimbursement.ui.ReimbursementMessages;


/**
 * Parser for the Reimbursement tab.
 */
public class ReimbursementTabParser {
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Routes the command to the appropriate parser.
     *
     * @param userInput   the command input by the user.
     * @param personModel the person involved in the command.
     * @return a command representing the user's desired action.
     * @throws Exception if the command syntax is incorrect.
     */
    public Command parseCommand(String userInput, Model personModel)
            throws ParseException, NoSuchPersonReimbursementException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(ReimbursementMessages.MESSAGE_NO_COMMAND);
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
        case BackCommand.COMMAND_WORD:
            return new BackCommandParser().parse(arguments);
        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);
        case ExitCommand.COMMAND_WORD:
            return new ExitCommandParser().parse(arguments);

        default:
            throw new ParseException(ReimbursementMessages.MESSAGE_NO_COMMAND);

        }
    }
}
