package seedu.address.diaryfeature.logic.parser;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.JsonUtil;
import seedu.address.diaryfeature.logic.commands.AddCommand;
import seedu.address.diaryfeature.logic.commands.DeleteCommand;
import seedu.address.diaryfeature.logic.commands.ErrorCommand;
import seedu.address.diaryfeature.logic.commands.ExitCommand;
import seedu.address.diaryfeature.logic.commands.FindCommand;
import seedu.address.diaryfeature.logic.commands.FindSpecificCommand;
import seedu.address.diaryfeature.logic.commands.ListCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.GoToCommand;
import seedu.address.logic.parser.GoToParser;
import seedu.address.logic.parser.exceptions.ParseException;
import java.util.logging.Logger;

/**
 * Parses user input.
 */
public class DiaryBookParser {


    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    public static final String MESSAGE_UNKNOWN_COMMAND = "Diary does not have such a command :(";

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    private static final Logger logger = LogsCenter.getLogger(JsonUtil.class);

    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            logger.info("Couldn't match the command word");
            logger.info(matcher.toString());
            return new ErrorCommand(new ParseException(matcher.toString()));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        try {
            switch (commandWord) {
                case AddCommand.COMMAND_WORD:
                    return new AddCommandParser().parse(arguments);

                case DeleteCommand.COMMAND_WORD:
                    return new DeleteCommandParser().parse(arguments);

                case GoToCommand.COMMAND_WORD:
                    return new GoToParser().parse(arguments);

                case ExitCommand.COMMAND_WORD:
                    return new ExitCommand();

                case ListCommand.COMMAND_WORD:
                    return new ListCommand();

                case FindCommand.COMMAND_WORD:
                    return new FindCommandParser().parse(arguments);

                case FindSpecificCommand.COMMAND_WORD:
                    return new FindSpecificCommandParser().parse(arguments);

                default:
                    return new ErrorCommand(new Exception(MESSAGE_UNKNOWN_COMMAND));
            }
        } catch (Exception error) {
            return new ErrorCommand(error);
        }
    }

}
