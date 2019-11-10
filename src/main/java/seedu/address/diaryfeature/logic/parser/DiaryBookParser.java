package seedu.address.diaryfeature.logic.parser;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.util.JsonUtil;
import seedu.address.diaryfeature.logic.commands.AddCommand;
import seedu.address.diaryfeature.logic.commands.DeleteCommand;
import seedu.address.diaryfeature.logic.commands.ErrorCommand;
import seedu.address.diaryfeature.logic.commands.ExitCommand;
import seedu.address.diaryfeature.logic.commands.FindCommand;
import seedu.address.diaryfeature.logic.commands.FindSpecificCommand;
import seedu.address.diaryfeature.logic.commands.HelpCommand;
import seedu.address.diaryfeature.logic.commands.ListCommand;
import seedu.address.diaryfeature.logic.commands.PrivateCommand;
import seedu.address.diaryfeature.logic.commands.SetDetailsCommand;
import seedu.address.diaryfeature.logic.commands.UnLockCommand;
import seedu.address.diaryfeature.logic.commands.UnPrivateCommand;
import seedu.address.diaryfeature.logic.parser.exceptions.DiaryUnknownException;
import seedu.address.diaryfeature.logic.parser.exceptions.EmptyArgumentException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.GoToCommand;
import seedu.address.logic.parser.GoToParser;
import seedu.address.logic.parser.exceptions.ParseException;
import java.util.logging.Logger;

/**
 * Overall user input parser.
 */
public class DiaryBookParser {
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(JsonUtil.class);
    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) throws ParseException, EmptyArgumentException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            logger.info("Couldn't match the command word");
            logger.info(matcher.toString());
            throw new EmptyArgumentException("");
        }
        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
     //  try {
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

                case HelpCommand.COMMAND_WORD:
                    return new HelpCommand();

                case PrivateCommand.COMMAND_WORD:
                    return new PrivateCommandParser().parse(arguments);

                case UnPrivateCommand.COMMAND_WORD:
                    return new UnPrivateCommandParser().parse(arguments);

                case UnLockCommand.COMMAND_WORD:
                    return new UnLockCommandParser().parse(arguments);

                case SetDetailsCommand.COMMAND_WORD:
                    return new SetDetailsCommandParser().parse(arguments);

                default:
                    return new ErrorCommand(new DiaryUnknownException());
            }

    }

}
