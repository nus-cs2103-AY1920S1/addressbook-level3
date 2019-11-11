package seedu.address.calendar.logic.parser;

import seedu.address.calendar.logic.commands.AddCommand;
import seedu.address.calendar.logic.commands.CheckCommand;
import seedu.address.calendar.logic.commands.DeleteCommand;
import seedu.address.calendar.logic.commands.ListCommand;
import seedu.address.calendar.logic.commands.ShowCommand;
import seedu.address.calendar.logic.commands.SuggestCommand;
import seedu.address.calendar.model.Calendar;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.GoToCommand;
import seedu.address.logic.parser.GoToParser;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

public class CalendarParser extends Parser {

    @Override
    protected Command<Calendar> parseCommand(String commandWord, String arguments) throws ParseException {
        switch(commandWord) {

        case ShowCommand.COMMAND_WORD:
            return new ShowCommandParser().parse(arguments);

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case SuggestCommand.COMMAND_WORD:
            return new SuggestCommandParser().parse(arguments);

        case CheckCommand.COMMAND_WORD:
            return new CheckCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

        case GoToCommand.COMMAND_WORD:
            return new GoToParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
