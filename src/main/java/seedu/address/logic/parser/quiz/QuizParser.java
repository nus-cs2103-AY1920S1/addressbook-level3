package seedu.address.logic.parser.quiz;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.quiz.QuitQuizModeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class QuizParser {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    public Command parseCommand(String userInput) throws ParseException {
        final Matcher commandMatcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!commandMatcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = commandMatcher.group("commandWord");
        final String arguments = commandMatcher.group("arguments");
        switch(commandWord) {

        case QuitQuizModeCommand.COMMAND_WORD:
            return new QuitQuizModeCommand();

        default:
            return new QuizAnswerParser().parse(userInput);
        }
    }
}
