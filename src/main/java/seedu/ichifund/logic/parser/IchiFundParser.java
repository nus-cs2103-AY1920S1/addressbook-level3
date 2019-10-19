package seedu.ichifund.logic.parser;

import static seedu.ichifund.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import seedu.ichifund.logic.commands.AddCommand;
import seedu.ichifund.logic.commands.ClearCommand;
import seedu.ichifund.logic.commands.Command;
import seedu.ichifund.logic.commands.DeleteCommand;
import seedu.ichifund.logic.commands.EditCommand;
import seedu.ichifund.logic.commands.EmptyCommand;
import seedu.ichifund.logic.commands.ExitCommand;
import seedu.ichifund.logic.commands.FindCommand;
import seedu.ichifund.logic.commands.HelpCommand;
import seedu.ichifund.logic.commands.ListCommand;
import seedu.ichifund.logic.parser.analytics.AnalyticsParserManager;
import seedu.ichifund.logic.parser.budget.BudgetParserManager;
import seedu.ichifund.logic.parser.exceptions.ParseException;
import seedu.ichifund.logic.parser.loans.LoansParserManager;
import seedu.ichifund.logic.parser.repeater.RepeaterParserManager;
import seedu.ichifund.logic.parser.transaction.TransactionParserManager;


/**
 * Parses user input.
 */
public class IchiFundParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private final ArrayList<ParserManager> parserManagers;
    private SimpleObjectProperty<ParserManager> currentParserManager;

    public IchiFundParser() {
        parserManagers = new ArrayList<>();
        parserManagers.add(new TransactionParserManager());
        parserManagers.add(new RepeaterParserManager());
        parserManagers.add(new BudgetParserManager());
        parserManagers.add(new LoansParserManager());
        parserManagers.add(new AnalyticsParserManager());
        currentParserManager = new SimpleObjectProperty<>(parserManagers.get(0));
    }

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

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            return handleFeatureCommand(commandWord, arguments);
        }
    }

    /**
     * Checks if user input is for tab switching and performs tab switching.
     * Otherwise, passes user input into the current {@code ParserManager} for parsing.
     *
     * @return The command based on the user input. EmptyCommand for tab switching.
     * @throws ParseException If the user input does not conform the expected format for the {@code ParserManager}
     */
    private Command handleFeatureCommand(String commandWord, String arguments) throws ParseException {
        boolean isTabSwitchCommand = false;

        for (ParserManager parserManager : parserManagers) {
            if (parserManager.getTabSwitchCommandWord().equals(commandWord)) {
                isTabSwitchCommand = true;
                setParserManager(parserManager.getTabIndex());
            }
        }

        if (!isTabSwitchCommand) {
            return currentParserManager.getValue().parseCommand(commandWord, arguments);
        }

        return new EmptyCommand();
    }

    public void setParserManager(int index) {
        ParserManager parserManager = parserManagers.get(index);
        assert(parserManager.getTabIndex() == index);
        currentParserManager.setValue(parserManager);
    }

    public ObservableValue<ParserManager> getCurrentParserManager() {
        return currentParserManager;
    }
}
