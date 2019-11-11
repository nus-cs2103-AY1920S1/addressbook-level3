package seedu.ichifund.logic.parser;

import static seedu.ichifund.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import seedu.ichifund.logic.commands.ClearCommand;
import seedu.ichifund.logic.commands.Command;
import seedu.ichifund.logic.commands.EmptyCommand;
import seedu.ichifund.logic.commands.ExitCommand;
import seedu.ichifund.logic.commands.HelpCommand;
import seedu.ichifund.logic.parser.analytics.AnalyticsFeatureParser;
import seedu.ichifund.logic.parser.budget.BudgetFeatureParser;
import seedu.ichifund.logic.parser.exceptions.ParseException;
import seedu.ichifund.logic.parser.loan.LoanFeatureParser;
import seedu.ichifund.logic.parser.repeater.RepeaterFeatureParser;
import seedu.ichifund.logic.parser.transaction.TransactionFeatureParser;

/**
 * Parses user input.
 */
public class IchiFundParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private final ArrayList<FeatureParser> featureParsers;
    private FeatureParser currentFeatureParser;
    private SimpleObjectProperty<Integer> currentFeatureParserIndex;

    public IchiFundParser() {
        featureParsers = new ArrayList<>();
        featureParsers.add(new TransactionFeatureParser());
        featureParsers.add(new RepeaterFeatureParser());
        featureParsers.add(new BudgetFeatureParser());
        featureParsers.add(new LoanFeatureParser());
        featureParsers.add(new AnalyticsFeatureParser());
        currentFeatureParser = featureParsers.get(0);
        currentFeatureParserIndex = new SimpleObjectProperty<>(currentFeatureParser.getTabIndex());
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
        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

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
     * Otherwise, passes user input into the current {@code FeatureParser} for parsing.
     *
     * @return The command based on the user input. EmptyCommand for tab switching.
     * @throws ParseException If the user input does not conform the expected format for the {@code FeatureParser}
     */
    private Command handleFeatureCommand(String commandWord, String arguments) throws ParseException {
        boolean isTabSwitchCommand = false;

        for (FeatureParser featureParser : featureParsers) {
            if (featureParser.getTabSwitchCommandWord().equals(commandWord)) {
                isTabSwitchCommand = true;
                setFeatureParser(featureParser.getTabIndex());
            }
        }

        if (!isTabSwitchCommand) {
            return currentFeatureParser.parseCommand(commandWord, arguments);
        }

        return new EmptyCommand();
    }

    public void setFeatureParser(int index) {
        FeatureParser featureParser = featureParsers.get(index);
        assert featureParser.getTabIndex() == index;
        currentFeatureParser = featureParser;
        currentFeatureParserIndex.setValue(currentFeatureParser.getTabIndex());
    }

    public ObservableValue<Integer> getCurrentFeatureParserIndex() {
        return currentFeatureParserIndex;
    }
}
