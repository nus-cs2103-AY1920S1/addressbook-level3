package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.cli.AddModuleCommand;
import seedu.address.logic.commands.cli.BlockCurrentSemesterCommand;
import seedu.address.logic.commands.cli.DeleteModuleCommand;
import seedu.address.logic.commands.cli.NameUEFromSemesterCommand;
import seedu.address.logic.commands.cli.SetCurrentSemesterCommand;
import seedu.address.logic.commands.datamanagement.FindCommand;
import seedu.address.logic.commands.gui.HelpCommand;
import seedu.address.logic.commands.storage.CreateStudyPlanCommand;
import seedu.address.logic.commands.verification.CheckCommand;
import seedu.address.logic.parser.cli.AddModuleParser;
import seedu.address.logic.parser.cli.BlockCurrentSemesterParser;
import seedu.address.logic.parser.cli.DeleteModuleFromSemesterParser;
import seedu.address.logic.parser.cli.NameUEFromSemesterParser;
import seedu.address.logic.parser.cli.SetCurrentSemesterParser;
import seedu.address.logic.parser.datamanagement.FindCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.storage.CreateStudyPlanCommandParser;
import seedu.address.logic.parser.verification.CheckCommandParser;

/**
 * Parses user input.
 */
public class ModulePlannerParser {

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

            case AddModuleCommand.COMMAND_WORD:
                return new AddModuleParser().parse(arguments);

            case BlockCurrentSemesterCommand.COMMAND_WORD:
                return new BlockCurrentSemesterParser().parse(arguments);

            case DeleteModuleCommand.COMMAND_WORD:
                return new DeleteModuleFromSemesterParser().parse(arguments);

            case NameUEFromSemesterCommand.COMMAND_WORD:
                return new NameUEFromSemesterParser().parse(arguments);

            case SetCurrentSemesterCommand.COMMAND_WORD:
                return new SetCurrentSemesterParser().parse(arguments);

            case FindCommand.COMMAND_WORD:
                return new FindCommandParser().parse(arguments);

            case CreateStudyPlanCommand.COMMAND_WORD:
                return new CreateStudyPlanCommandParser().parse(arguments);

            case CheckCommand.COMMAND_WORD:
                return new CheckCommandParser().parse(arguments);

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
