package seedu.guilttrip.logic.commands;

import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.guilttrip.logic.parser.GuiltTripParser.BASIC_COMMAND_FORMAT;
import static seedu.guilttrip.logic.parser.GuiltTripParser.COMMANDS_SET;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import seedu.guilttrip.logic.commands.util.EditDistanceComparator;
import seedu.guilttrip.logic.parser.ArgumentMultimap;
import seedu.guilttrip.logic.parser.ArgumentTokenizer;
import seedu.guilttrip.logic.parser.exceptions.ParseException;

/**
 * Implements one main function suggest: to return a suggestion string
 * to be displayed at the {@link seedu.guilttrip.ui.ResultDisplay} given user's
 * current text input for AutoSuggestion effect.
 */
public final class GuiltTripCommandSuggester {

    public static final int COMMAND_RECOMMENDATION_COUNT = 5;

    /**
     * Takes in textInput from the {@link seedu.guilttrip.ui.CommandBox} and returns
     * a {@link String} to be displayed in the {@link seedu.guilttrip.ui.ResultDisplay}.
     *
     * @param textInput String to be processed
     * @return Appropriate helping string.
     */
    public static String getSuggestionString(String textInput) {
        try {
            String commandWord = getCommandWord(textInput);

            if (commandIsIncomplete(commandWord)) {
                return getClosestCommands(commandWord, COMMAND_RECOMMENDATION_COUNT);
            } else {
                return getCommandHelpMessage(commandWord);
            }
        } catch (ParseException pe) {
            return pe.getMessage();
        }

        // ArgumentMultimap argumentMultimap = getArgumentMultimap(textInput);
    }

    // TODO
    private static String getCommandHelpMessage(String commandWord) {
        return "placeholder";
    }

    private static String getCommandWord(String textInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(textInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        return commandWord;
    }

    private static ArgumentMultimap getArgumentMultimap(String textInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(textInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String arguments = matcher.group("arguments");
        return ArgumentTokenizer.tokenize(arguments);
    }

    private static boolean commandIsIncomplete(String commandWord) {
        return !COMMANDS_SET.contains(commandWord);
    }

    // TODO: Save a set of all commands. Either at model or guiltTrip
    private static String getClosestCommands(String commandWord, int count) {
        Queue<String> commandsHeap = new PriorityQueue<>(new EditDistanceComparator(commandWord));
        commandsHeap.addAll(COMMANDS_SET);

        return IntStream.rangeClosed(1, 10).mapToObj(i -> commandsHeap.poll()).collect(Collectors.joining("\n"));
    }
}
