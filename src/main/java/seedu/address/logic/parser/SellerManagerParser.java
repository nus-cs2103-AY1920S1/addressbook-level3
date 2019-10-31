package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.CancelOrderCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CompleteOrderCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.addcommand.AddCustomerCommand;
import seedu.address.logic.commands.addcommand.AddOrderCommand;
import seedu.address.logic.commands.addcommand.AddPhoneCommand;
import seedu.address.logic.commands.addcommand.AddScheduleCommand;
import seedu.address.logic.commands.clearcommand.ClearCustomerCommand;
import seedu.address.logic.commands.clearcommand.ClearOrderCommand;
import seedu.address.logic.commands.clearcommand.ClearPhoneCommand;
import seedu.address.logic.commands.clearcommand.ClearScheduleCommand;
import seedu.address.logic.commands.copycommand.CopyCustomerCommand;
import seedu.address.logic.commands.copycommand.CopyOrderCommand;
import seedu.address.logic.commands.copycommand.CopyPhoneCommand;
import seedu.address.logic.commands.deletecommand.DeleteCustomerCommand;
import seedu.address.logic.commands.deletecommand.DeletePhoneCommand;
import seedu.address.logic.commands.deletecommand.DeleteScheduleCommand;
import seedu.address.logic.commands.editcommand.EditCustomerCommand;
import seedu.address.logic.commands.editcommand.EditOrderCommand;
import seedu.address.logic.commands.editcommand.EditPhoneCommand;
import seedu.address.logic.commands.editcommand.EditScheduleCommand;
import seedu.address.logic.commands.findcommand.FindCustomerCommand;
import seedu.address.logic.commands.findcommand.FindOrderCommand;
import seedu.address.logic.commands.findcommand.FindPhoneCommand;
import seedu.address.logic.commands.listcommand.ListCustomerCommand;
import seedu.address.logic.commands.listcommand.ListOrderCommand;
import seedu.address.logic.commands.listcommand.ListPhoneCommand;
import seedu.address.logic.commands.statisticcommand.StatsCommand;
import seedu.address.logic.commands.switchcommand.SwitchArchivedOrderPanelCommand;
import seedu.address.logic.commands.switchcommand.SwitchCustomerPanelCommand;
import seedu.address.logic.commands.switchcommand.SwitchOrderPanelCommand;
import seedu.address.logic.commands.switchcommand.SwitchPhonePanelCommand;
import seedu.address.logic.commands.switchcommand.SwitchSchedulePanelCommand;

import seedu.address.logic.parser.addcommandparser.AddCustomerCommandParser;
import seedu.address.logic.parser.addcommandparser.AddOrderCommandParser;
import seedu.address.logic.parser.addcommandparser.AddPhoneCommandParser;
import seedu.address.logic.parser.addcommandparser.AddScheduleCommandParser;
import seedu.address.logic.parser.copycommandparser.CopyCustomerCommandParser;
import seedu.address.logic.parser.copycommandparser.CopyOrderCommandParser;
import seedu.address.logic.parser.copycommandparser.CopyPhoneCommandParser;
import seedu.address.logic.parser.deletecommandparser.DeleteCustomerCommandParser;
import seedu.address.logic.parser.deletecommandparser.DeletePhoneCommandParser;
import seedu.address.logic.parser.deletecommandparser.DeleteScheduleCommandParser;
import seedu.address.logic.parser.editcommandparser.EditCustomerCommandParser;
import seedu.address.logic.parser.editcommandparser.EditOrderCommandParser;
import seedu.address.logic.parser.editcommandparser.EditPhoneCommandParser;
import seedu.address.logic.parser.editcommandparser.EditScheduleCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.findcommandparser.FindCustomerCommandParser;
import seedu.address.logic.parser.findcommandparser.FindOrderCommandParser;
import seedu.address.logic.parser.findcommandparser.FindPhoneCommandParser;

/**
 * Parses user input.
 */
public class SellerManagerParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCustomerCommand.COMMAND_WORD:
            return new AddCustomerCommandParser().parse(arguments);

        case AddPhoneCommand.COMMAND_WORD:
            return new AddPhoneCommandParser().parse(arguments);

        case AddOrderCommand.COMMAND_WORD:
            return new AddOrderCommandParser().parse(arguments);

        case AddScheduleCommand.COMMAND_WORD:
            return new AddScheduleCommandParser().parse(arguments);

        case EditCustomerCommand.COMMAND_WORD:
            return new EditCustomerCommandParser().parse(arguments);

        case EditScheduleCommand.COMMAND_WORD:
            return new EditScheduleCommandParser().parse(arguments);

        case EditPhoneCommand.COMMAND_WORD:
            return new EditPhoneCommandParser().parse(arguments);

        case EditOrderCommand.COMMAND_WORD:
            return new EditOrderCommandParser().parse(arguments);

        case DeleteCustomerCommand.COMMAND_WORD:
            return new DeleteCustomerCommandParser().parse(arguments);

        case DeletePhoneCommand.COMMAND_WORD:
            return new DeletePhoneCommandParser().parse(arguments);

        case DeleteScheduleCommand.COMMAND_WORD:
            return new DeleteScheduleCommandParser().parse(arguments);

        case CancelOrderCommand.COMMAND_WORD:
            return new CancelOrderCommandParser().parse(arguments);

        case CompleteOrderCommand.COMMAND_WORD:
            return new CompleteOrderCommandParser().parse(arguments);

        case FindCustomerCommand.COMMAND_WORD:
            return new FindCustomerCommandParser().parse(arguments);

        case FindPhoneCommand.COMMAND_WORD:
            return new FindPhoneCommandParser().parse(arguments);

        case FindOrderCommand.COMMAND_WORD:
            return new FindOrderCommandParser().parse(arguments);

        case ListCustomerCommand.COMMAND_WORD:
            return new ListCustomerCommand();

        case ListPhoneCommand.COMMAND_WORD:
            return new ListPhoneCommand();

        case ListOrderCommand.COMMAND_WORD:
            return new ListOrderCommand();

        case ClearCustomerCommand.COMMAND_WORD:
            return new ClearCustomerCommand();

        case ClearPhoneCommand.COMMAND_WORD:
            return new ClearPhoneCommand();

        case ClearOrderCommand.COMMAND_WORD:
            return new ClearOrderCommand();

        case ClearScheduleCommand.COMMAND_WORD:
            return new ClearScheduleCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case CopyCustomerCommand.COMMAND_WORD:
            return new CopyCustomerCommandParser().parse(arguments);


        case CopyPhoneCommand.COMMAND_WORD:
            return new CopyPhoneCommandParser().parse(arguments);


        case CopyOrderCommand.COMMAND_WORD:
            return new CopyOrderCommandParser().parse(arguments);

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case StatsCommand.COMMAND_WORD:
            return new StatsCommandParser().parse(arguments);

        case SwitchCustomerPanelCommand.COMMAND_WORD:
            return new SwitchCustomerPanelCommand();

        case SwitchPhonePanelCommand.COMMAND_WORD:
            return new SwitchPhonePanelCommand();

        case SwitchOrderPanelCommand.COMMAND_WORD:
            return new SwitchOrderPanelCommand();

        case SwitchSchedulePanelCommand.COMMAND_WORD:
            return new SwitchSchedulePanelCommand();

        case SwitchArchivedOrderPanelCommand.COMMAND_WORD:
            return new SwitchArchivedOrderPanelCommand();

        case ScheduleCommand.COMMAND_WORD:
            return new ScheduleCommandParser().parse(arguments);

        case ExportCommand.COMMAND_WORD:
            return new ExportCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
