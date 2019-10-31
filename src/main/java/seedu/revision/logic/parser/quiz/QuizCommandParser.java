package seedu.revision.logic.parser.quiz;

import static seedu.revision.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.revision.logic.commands.Command;
import seedu.revision.logic.commands.main.ExitCommand;
import seedu.revision.logic.commands.main.HelpCommand;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.Mcq;
import seedu.revision.model.answerable.TrueFalse;

/**
 * In-charge of parsing commands during quiz session.
 */
public class QuizCommandParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)");

    /**
     * Checks if user input during quiz is valid.
     * It should be either an exit command, help command, or an answer to the current question.
     * @param userInput user response or command
     * @param currentAnswerable the current question
     * @return exit command or help command or MCQ input command parser
     * @throws ParseException
     */
    public Command parseCommand(String userInput, Answerable currentAnswerable) throws ParseException {
        if (currentAnswerable instanceof Mcq || currentAnswerable instanceof TrueFalse) {
            final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
            if (!matcher.matches()) {
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }

            final String commandWord = matcher.group("commandWord");

            switch (commandWord) {
            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();
            case HelpCommand.COMMAND_WORD:
                return new HelpCommand();
            default:
                break;
            }

            if (currentAnswerable instanceof Mcq) {
                return new McqInputCommandParser().parse(userInput, currentAnswerable);
            } else if (currentAnswerable instanceof TrueFalse) {
                return new TfInputCommandParser().parse(userInput, currentAnswerable);
            } else {
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }
        } else {
            if (userInput.isBlank()) {
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }
            return new SaqInputCommandParser().parse(userInput, currentAnswerable);
        }

    }

}
