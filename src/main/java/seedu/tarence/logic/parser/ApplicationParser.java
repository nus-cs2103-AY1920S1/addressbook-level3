package seedu.tarence.logic.parser;

import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tarence.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.tarence.logic.commands.AddModuleCommand;
import seedu.tarence.logic.commands.AddStudentCommand;
import seedu.tarence.logic.commands.AddTutorialCommand;
import seedu.tarence.logic.commands.Command;
import seedu.tarence.logic.commands.DeleteModuleCommand;
import seedu.tarence.logic.commands.DeleteStudentCommand;
import seedu.tarence.logic.commands.DeleteTutorialCommand;
import seedu.tarence.logic.commands.EditCommand;
import seedu.tarence.logic.commands.ExitCommand;
import seedu.tarence.logic.commands.FindCommand;
import seedu.tarence.logic.commands.HelpCommand;
import seedu.tarence.logic.commands.ListCommand;
import seedu.tarence.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class ApplicationParser {

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

        if (AddStudentCommand.isMatchingCommandWord(commandWord)) {
            return new AddStudentCommandParser().parse(arguments);
        } else if (EditCommand.isMatchingCommandWord(commandWord)) {
            return new EditCommandParser().parse(arguments);
        } else if (DeleteStudentCommand.isMatchingCommandWord(commandWord)) {
            return new DeleteStudentCommandParser().parse(arguments);
        } else if (DeleteModuleCommand.isMatchingCommandWord(commandWord)) {
            return new DeleteModuleCommandParser().parse(arguments);
        } else if (DeleteTutorialCommand.isMatchingCommandWord(commandWord)) {
            return new DeleteTutorialCommandParser().parse(arguments);
        } else if (FindCommand.isMatchingCommandWord(commandWord)) {
            return new FindCommandParser().parse(arguments);
        } else if (ListCommand.isMatchingCommandWord(commandWord)) {
            return new ListCommandParser().parse(arguments);
        } else if (ExitCommand.isMatchingCommandWord(commandWord)) {
            return new ExitCommand();
        } else if (HelpCommand.isMatchingCommandWord(commandWord)) {
            return new HelpCommand();
        } else if (AddModuleCommand.isMatchingCommandWord(commandWord)) {
            return new AddModuleCommandParser().parse(arguments);
        } else if (AddTutorialCommand.isMatchingCommandWord(commandWord)) {
            return new AddTutorialCommandParser().parse(arguments);
        } else {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }

    }

}
